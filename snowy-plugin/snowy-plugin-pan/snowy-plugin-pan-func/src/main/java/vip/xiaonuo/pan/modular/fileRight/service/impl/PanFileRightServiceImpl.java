/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.pan.modular.fileRight.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.SysBuildInEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.pan.modular.fileRight.constant.FileRight;
import vip.xiaonuo.pan.modular.fileRight.entity.PanFileRight;
import vip.xiaonuo.pan.modular.fileRight.enums.PanFileRightTypeEnum;
import vip.xiaonuo.pan.modular.fileRight.mapper.PanFileRightMapper;
import vip.xiaonuo.pan.modular.fileRight.param.*;
import vip.xiaonuo.pan.modular.fileRight.service.PanFileRightService;
import vip.xiaonuo.pan.modular.fileRight.vo.PanFileRightVo;
import vip.xiaonuo.pan.modular.fileRight.vo.UserInfoVo;
import vip.xiaonuo.pan.modular.fileRight.vo.UserRightVo;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;
import vip.xiaonuo.pan.modular.userFile.mapper.UserFileMapper;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;
import vip.xiaonuo.sys.api.SysOrgApi;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 网盘权限配置表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/10/25 14:32
 **/
@Service
public class PanFileRightServiceImpl extends ServiceImpl<PanFileRightMapper, PanFileRight> implements PanFileRightService {

    @Resource
    private UserFileService userFileService;
    @Resource
    private PanFileRightMapper panFileRightMapper;

    @Resource
    private SysOrgApi sysOrgApi;

    @Resource
    private UserFileMapper userFileMapper;

    /***
     * 仅获取当前文件的权限配置（明确设置的记录）
     * @param panFileRightIdParam
     * @return
     */
    @Override
    public List<PanFileRightVo> detail(PanFileRightIdParam panFileRightIdParam) {
        return panFileRightMapper.getFileRightByFileId(panFileRightIdParam.getUserFileId());
    }

    /***
     * 权限配置保存（整合新增|修改）
     * @param panFileRightAddParam
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(PanFileRightAddParam panFileRightAddParam) {
        //最新设置
        List<PanFileRight> newFileRights = BeanUtil.copyToList(panFileRightAddParam.getObjectRightList(),PanFileRight.class);
        newFileRights.forEach(x->x.setUserFileId(panFileRightAddParam.getUserFileId()));

        //根据文件Id获取历史权限设置
        List<PanFileRight> oldFileRights = this.list(new QueryWrapper<PanFileRight>().lambda().eq(PanFileRight::getUserFileId,panFileRightAddParam.getUserFileId()));
        //历史权限配置【拥有权限的用户】
        List<String> oldObjectIds = oldFileRights.stream().map(PanFileRight::getObjectId).collect(Collectors.toList());

        //最新权限配置的用户
        List<String> newObjectIds = newFileRights.stream().map(PanFileRight::getObjectId).collect(Collectors.toList());

        //新增
        List<PanFileRight> addList = newFileRights.stream().filter(x->!oldObjectIds.contains(x.getObjectId())).collect(Collectors.toList());
        //更新
        List<PanFileRight> updateList = oldFileRights.stream().filter(x->newObjectIds.contains(x.getObjectId())).collect(Collectors.toList());
        //删除
        oldFileRights.removeAll(updateList);
        //处理更新
        updateList.forEach(x->x.setRightValue(Objects.requireNonNull(newFileRights.stream().filter(e -> e.getObjectId().equals(x.getObjectId())).findFirst().orElse(null)).getRightValue()));

        //新增 | 更新 | 删除
        if(CollUtil.isNotEmpty(addList)) this.saveBatch(addList);
        if(CollUtil.isNotEmpty(updateList)) this.updateBatchById(updateList);
        if(CollUtil.isNotEmpty(oldFileRights)) this.removeByIds(oldFileRights);
    }

    /***
     * 权限配置保存（整合新增|修改）
     * @param panFileRightAddParam
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRight(PanFileRightAddParam panFileRightAddParam) {
        //最新设置
        List<PanFileRight> newFileRights = BeanUtil.copyToList(panFileRightAddParam.getObjectRightList(),PanFileRight.class);
        newFileRights.forEach(x->x.setUserFileId(panFileRightAddParam.getUserFileId()));

        //根据文件Id获取历史权限设置
        QueryWrapper<PanFileRight> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PanFileRight::getUserFileId,panFileRightAddParam.getUserFileId());
        if(CollUtil.isNotEmpty(newFileRights)){
            queryWrapper.lambda().in(PanFileRight::getObjectId,newFileRights.stream().map(x->x.getObjectId()).collect(Collectors.toList()));
        }
        List<PanFileRight> oldFileRights = this.list(queryWrapper);

        //若用户历史有配置，则更新，否则新增
        List<PanFileRight> addList = new ArrayList<>();
        List<PanFileRight> updateList = new ArrayList<>();
        for (PanFileRight item:newFileRights) {
            PanFileRight oldFileRight = oldFileRights.stream().filter(x->x.getObjectId().equals(item.getObjectId())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(oldFileRight)){
                oldFileRight.setRightValue(item.getRightValue());
                updateList.add(oldFileRight);
            }else{
                addList.add(item);
            }
        }
        //新增 | 更新 | 删除
        if(CollUtil.isNotEmpty(addList)) this.saveBatch(addList);
        if(CollUtil.isNotEmpty(updateList)) this.updateBatchById(updateList);
    }

    @Override
    public void removeRight(RemoveRightParam removeRightParam) {
        List<PanFileRight> panFileRights = this.list(new QueryWrapper<PanFileRight>().lambda()
                .eq(PanFileRight::getUserFileId,removeRightParam.getUserFileId()).eq(PanFileRight::getObjectId,removeRightParam.getObjectId()));
        this.removeBatchByIds(panFileRights);
    }

    @Override
    public void clearRight(ClearRightParam clearRightParam) {
        //找到当前文件及其子文件
        List<UserFile> childrenList = userFileMapper.getChildren(clearRightParam.getUserFileId());
        if(CollUtil.isNotEmpty(childrenList)){
            QueryWrapper<PanFileRight> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(PanFileRight::getUserFileId,childrenList.stream().map(x->x.getId()).collect(Collectors.toList()));
            if(CollUtil.isNotEmpty(clearRightParam.getObjectIds())){
                queryWrapper.lambda().in(PanFileRight::getObjectId,clearRightParam.getObjectIds());
            }
            this.remove(queryWrapper);
        }
    }

    /**
     * 给文件设置管理者
     * @param managerParam
     */
    @Override
    public void setManager(ManagerParam managerParam) {
        UserFile userFile = userFileService.getById(managerParam.getUserFileId());
        if(ObjectUtil.isNotEmpty(userFile)){
            userFile.setFileManager(managerParam.getFileManager());
            userFileService.updateById(userFile);
        }
    }

    /**
     * 根据 userFileId,当前用户信息，获取当前用户对该文件所拥有的权限
     * @param fileRightParam
     * @return
     */
    @Override
    public Long getRightById(FileRightParam fileRightParam) {
        if(CollUtil.isEmpty(fileRightParam.getUserFileIds())) throw new CommonException("请选择文件");
        Long right = FileRight.ALL_RIGHT;
        for (String userFileId:fileRightParam.getUserFileIds()) {
            right = right & getRightByUserFile(userFileId,StpLoginUserUtil.getLoginUser().getId(), StpLoginUserUtil.getLoginUser().getOrgId(),StpLoginUserUtil.getLoginUser().getRoleCodeList());
        }
        return right;
    }

    /**
     * 根据文件Id，获取权限列表 【全部范围内的人员权限列表】
     * 1. 明确设置的，就是当前设置权限记录
     * 2. 没有明确设置的，展示为继承的权限
     * @param panFileRightIdParam
     * @return
     */
    @Override
    public List<UserRightVo> rightDetail(PanFileRightIdParam panFileRightIdParam) {
        //1. 获取顶层父级文件夹群组范围配置
        UserFile topUserFile = getTopUserFile(panFileRightIdParam.getUserFileId());
        if(ObjectUtil.isEmpty(topUserFile)) return null;
        List<PanFileRight> fileRightList = this.list(new QueryWrapper<PanFileRight>().lambda().eq(PanFileRight::getUserFileId,topUserFile.getId()));
        if(CollUtil.isEmpty(fileRightList)) return null;

        //2. 获取 群组范围 配置objectId
        List<String> objectIds = fileRightList.stream().map(PanFileRight::getObjectId).collect(Collectors.toList());
        //由于配置的可能是 组织|用户 ，需要获取子部门
        List<String> objIds = new ArrayList<>();
        for (String objectId:objectIds) {
            List<String> childrenOrgIds = sysOrgApi.getChildrenIds(objectId);
            if(CollUtil.isNotEmpty(childrenOrgIds)) objIds.addAll(childrenOrgIds);
            if(!objIds.contains(objectId)) objIds.add(objectId);
        }
        //获取 群组范围 内的所有用户
        List<UserInfoVo> userInfoVoList = panFileRightMapper.getUserInfos(objIds);

        //3. 获取每个用户的具体权限值
        List<String> userIdList = userInfoVoList.stream().map(UserInfoVo::getUserId).distinct().collect(Collectors.toList());
        List<UserRightVo> result = new ArrayList<>();
        for (String userId:userIdList) {
            UserInfoVo tmp = userInfoVoList.stream().filter(x->x.getUserId().equals(userId)).findFirst().orElse(null);
            UserRightVo userRightVo = new UserRightVo();
            userRightVo.setUserFileId(panFileRightIdParam.getUserFileId());
            userRightVo.setObjectId(userId);
            userRightVo.setObjName(tmp.getUserName());
            userRightVo.setOrgId(tmp.getOrgId());
            userRightVo.setOrgName(tmp.getOrgName());
            //获取当前用户的角色集合
            List<String> roleCodes = userInfoVoList.stream().filter(x->x.getUserId().equals(userId) && StrUtil.isNotEmpty(x.getRoleCode())).map(UserInfoVo::getRoleCode).collect(Collectors.toList());
            userRightVo.setRightValue(getRightByUserFile(panFileRightIdParam.getUserFileId(),userId,tmp.getOrgId(),roleCodes));
            result.add(userRightVo);
        }
        return result;
    }


    /***
     * 获取指定用户，对指定文件拥有哪些操作权限
     * @param userFileId 指定的文件Id
     * @param userId   指定的用户Id
     * @param orgId    指定的用户所属组织
     * @param roleCodes 指定用户所拥有的角色code集合
     * @return
     */
    public Long getRightByUserFile(String userFileId,String userId,String orgId,List<String> roleCodes){
        //系统管理员对顶级目录拥有全部操作权限
        if("0".equals(userFileId) && roleCodes.contains(SysBuildInEnum.BUILD_IN_ROLE_CODE.getValue()) )
        {
            return FileRight.ALL_RIGHT;
        }
        //获取文件信息
        UserFile userFile = userFileService.getById(userFileId);
        if(ObjectUtil.isEmpty(userFile)) return FileRight.RIGHT_NULL;

        //2. 超管、文件管理者、文件所有者 拥有全部权限
        if(roleCodes.contains(SysBuildInEnum.BUILD_IN_ROLE_CODE.getValue())
           || userFile.getUserId().equals(userId)
           || getFileManager(userFile.getId()).contains(userId))
            return FileRight.ALL_RIGHT;

        //3. 当前用户 >> 所属组织  对当前文件userFileId是否有权限配置  指定的用户所属组织及其父组织集合
        List<String> orgIds = sysOrgApi.getParentIds(orgId,true);
        PanFileRight currentRight = getRightByUser(userFile.getId(),userId,orgId,orgIds);
        if(ObjectUtil.isNotEmpty(currentRight)) return currentRight.getRightValue();

        //4. 当前用户 >> 所属组织 对当前文件userFileId无权限配置，递归看其上级文件
        PanFileRight parentRight = getParentRight(userFile.getParentId(),userId,orgId,orgIds);
        if(ObjectUtil.isNotEmpty(parentRight)) return parentRight.getRightValue();

        return FileRight.RIGHT_NULL;
    }

    /***
     * 获取当前用户，是否存在权限配置
     *
     * 规则：当前用户 >> 所属部门（一次向上） 是否有权限配置 [暂不支持 角色]
     *
     * @return
     */
    public PanFileRight getRightByUser(String userFileId,String userId,String orgId,List<String> orgIds){
        QueryWrapper<PanFileRight> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PanFileRight::getUserFileId,userFileId);
        queryWrapper.and(q-> {
            q.lambda().eq(PanFileRight::getObjectId, userId);
            if(CollUtil.isNotEmpty(orgIds)) q.or().lambda().in(PanFileRight::getObjectId,orgIds);
        });
        List<PanFileRight> fileRights = this.list(queryWrapper);
        //先取用户
        PanFileRight userObj = fileRights.stream().filter(x -> x.getObjectId().equals(userId)).findFirst().orElse(null);
        if(ObjectUtil.isNotEmpty(userObj)) return userObj;
        //再递归取组织
        if(CollUtil.isNotEmpty(orgIds)){
            return getRightByOrg(fileRights,orgId);
        }
        return null;
    }

    /**
     * 根据当前员工所属的部门，逐级查询权限配置
     * @param fileRights
     * @param orgId
     * @return
     */
    public PanFileRight getRightByOrg(List<PanFileRight> fileRights,String orgId){
        if(StrUtil.isEmpty(orgId)) return null;
        String finalOrgId = orgId;
        PanFileRight obj = fileRights.stream().filter(x -> x.getObjectId().equals(finalOrgId)).findFirst().orElse(null);
        if (ObjectUtil.isNotEmpty(obj)) return obj;
        return getRightByOrg(fileRights,sysOrgApi.getParentId(orgId));
    }


    /***
     * 递归，获取当前文件的  所有 文件管理者
     * 比如 ：   A -- B -- C -- d.doc
     * 管理者：  a     b         c
     * 则 d.doc 的 管理者 a,b,c 均可操作该文件
     *
     * @param userFileId 当前文件实体信息
     * @return
     */
    public List<String> getFileManager(String userFileId){
        List<UserFile> parents = getParents(userFileId);
        //获取自身及所有的父级管理者
        List<String> tmpManagers = new ArrayList<>();
        if(CollUtil.isNotEmpty(parents)) {
            List<String> managers = parents.stream().filter(x -> StrUtil.isNotEmpty(x.getFileManager()))
                    .map(UserFile::getFileManager).distinct().collect(Collectors.toList());
            managers.forEach(x-> tmpManagers.addAll(Arrays.stream(x.split(",")).distinct().collect(Collectors.toList())));
        }
        return tmpManagers;
    }


    /***
     * 递归获取，就近权限 [暂不支持 角色]
     * @param parentFileId 父级文件userFileId
     * @param userId 用户Id
     * @return
     */
    public PanFileRight getParentRight(String parentFileId,String userId,String orgId,List<String> orgIds){
        if(ObjectUtil.isEmpty(parentFileId)) return null;
        PanFileRight obj = getRightByUser(parentFileId,userId,orgId,orgIds);
        if(ObjectUtil.isNotEmpty(obj)) return obj;
        UserFile parentUserFile = userFileService.getById(parentFileId);
        parentFileId = ObjectUtil.isNotEmpty(parentUserFile)?parentUserFile.getParentId():"";
        return getParentRight(parentFileId,userId,orgId,orgIds);
    }


    /***
     * 根据userFileId，获取自身及所有父级文件信息
     * @param userFileId 用户文件Id
     * @return 所有的父级文件信息
     */
    public List<UserFile> getParents(String userFileId){
        return userFileMapper.getParent(userFileId);
    }


    /***
     * 根据userFileId，获取最顶级文件夹
     * @param userFileId 用户文件Id
     * @return 顶级文件信息
     */
    public UserFile getTopUserFile(String userFileId){
        return userFileMapper.getTopParent(userFileId);
    }


    /***
     * 根据 用户文件Id，校验当前用户是否有对应操作权限(支持批量，批量就是交集权限)
     * @param userFileIds 用户文件Id(UserFile.Id)
     * @param checkRight 操作权限(FileRight)
     */
    @Override
    public void checkFileRight(List<String> userFileIds, long checkRight,String type) {
        if(StrUtil.isEmpty(type)) return;
        if(CollUtil.isEmpty(userFileIds)) return;
        if(type.equals(PanFileRightTypeEnum.GROUP.getValue())) {
            if(userFileIds.size() == 1) {
                Long right = getRightByUserFile(userFileIds.get(0), StpLoginUserUtil.getLoginUser().getId(), StpLoginUserUtil.getLoginUser().getOrgId(), StpLoginUserUtil.getLoginUser().getRoleCodeList());
                if (!FileRight.hasRight(right, checkRight)) throw new CommonException("无操作权限");
            }else{
                FileRightParam fileRightParam = new FileRightParam();
                fileRightParam.setUserFileIds(userFileIds);
                Long right = getRightById(fileRightParam);
                if (!FileRight.hasRight(right, checkRight)) throw new CommonException("无操作权限");
            }
        }
    }


    /***
     * 根据文件id集合，过滤没有权限的文件
     *
     * 主要针对文件夹中包含文件|文件夹
     *
     * 例如：上层文件夹A有下载权限，子文件夹|文件 无下载权限，需要过滤
     *
     * @param userFileIds
     * @param checkRight
     * @return
     */
    public List<String> filterUserFileIds(List<String> userFileIds,long checkRight,String type){
        if(StrUtil.isEmpty(type)) return userFileIds;
        if(CollUtil.isEmpty(userFileIds)) return null;
        if(type.equals(PanFileRightTypeEnum.GROUP.getValue())) {
            String userId = StpLoginUserUtil.getLoginUser().getId();
            String orgId = StpLoginUserUtil.getLoginUser().getOrgId();
            List<String> roleCodes = StpLoginUserUtil.getLoginUser().getRoleCodeList();
            List<UserFile> delUserFileList = new ArrayList<>();
            for (String userFileId : userFileIds) {
                Long right = getRightByUserFile(userFileId, userId, orgId, roleCodes);
                if (!FileRight.hasRight(right, checkRight)) {
                    List<UserFile> tmp = userFileMapper.getChildren(userFileId);
                    delUserFileList.addAll(tmp);
                }
            }
            List<String> delUserFileIds = delUserFileList.stream().map(UserFile::getId).collect(Collectors.toList());
            userFileIds.removeAll(delUserFileIds);
        }
        return userFileIds;
    }


    /***
     * 在列表页过滤的时候，需要排除不可见的权限设置
     * 登录用户 A （属于数字院）
     * 比如：文件A 对于数字院可见  但对 员工A 不可见  需要排除   员工A不可见A文件
     *      文件A 对于数字院不可见  但特定设置对 员工A 可见  不排除  员工A可见A文件
     *      文件A 对于数字院不可见  无其他设置  需要排除   员工A不可见A文件
     *
     *  数字院(权限设置)    可见    可见    不可见   不可见    不可见    可见    X       X       可见      不可见
     *  软件中心(权限设置)   可见    可见   不可见   不可见    不可见    可见    X       X       不可见     可见
     *  员工A (权限设置)    可见   不可见   可见     不可见      X       X    可见    不可见      X        X
     *  员工A（结果）       可见   不可见   可见     不可见    不可见    可见   可见    不可见    不可见     可见
     *
     *  结论： 若针对员工设置了权限，优先看员工配置，无员工配置看组织设置（组织递归）
     *
     * @param userId
     * @param orgIds
     * @return
     */
    public List<String> notInUserFileIds(String userId,String orgId,List<String> orgIds){
        long checkRight = FileRight.LIST;
        //获取当前用户、部门的权限配置记录
        List<PanFileRight> fileRightList = panFileRightMapper.getFileRightList(userId,orgIds,checkRight);

        //情况1：先过滤有用户userId权限配置的（userid都是不可见数据）
        List<PanFileRight> userFileRightList = fileRightList.stream().filter(x->x.getObjectId().equals(userId)).collect(Collectors.toList());
        List<String> notInIds = userFileRightList.stream().map(PanFileRight::getUserFileId).distinct().collect(Collectors.toList());

        //移除 已处理数据
        fileRightList.removeAll(userFileRightList);

        String tmpOrgId = orgId;
        //情况2：存在 仅 设置了部门权限 情况
        while(CollUtil.isNotEmpty(fileRightList)) {
            //先找自己组织的配置数据
            String finalTmpOrgId = tmpOrgId;
            List<PanFileRight> tmpOrgFileRightList = fileRightList.stream().filter(x -> x.getObjectId().equals(finalTmpOrgId)).collect(Collectors.toList());

            //本级组织不可见
            List<String> tmpNotInIds = tmpOrgFileRightList.stream().filter(x -> !FileRight.hasRight(x.getRightValue(), checkRight)).map(PanFileRight::getUserFileId).distinct().collect(Collectors.toList());
            if (CollUtil.isNotEmpty(tmpNotInIds)) notInIds.addAll(tmpNotInIds);

            //获取其涉及到的userFileId（本级组织控制是否可见）
            List<String> tmpUserFileIds = tmpOrgFileRightList.stream().map(PanFileRight::getUserFileId).distinct().collect(Collectors.toList());

            //移除已处理的userFileId
            if(CollUtil.isNotEmpty(tmpUserFileIds)) {
                fileRightList.removeAll(fileRightList.stream().filter(x -> tmpUserFileIds.contains(x.getUserFileId())).collect(Collectors.toList()));
            }

            //找父级
            tmpOrgId = sysOrgApi.getParentId(tmpOrgId);
            if(StrUtil.isEmpty(tmpOrgId) || !orgIds.contains(tmpOrgId)) break;
        }
        return notInIds;
    }
}
