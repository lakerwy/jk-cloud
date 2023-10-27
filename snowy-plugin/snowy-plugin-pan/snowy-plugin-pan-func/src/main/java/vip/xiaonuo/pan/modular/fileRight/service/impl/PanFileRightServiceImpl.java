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
import org.bytedeco.opencv.presets.opencv_core;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.SysBuildInEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.pan.modular.fileRight.constant.FileRight;
import vip.xiaonuo.pan.modular.fileRight.entity.PanFileRight;
import vip.xiaonuo.pan.modular.fileRight.mapper.PanFileRightMapper;
import vip.xiaonuo.pan.modular.fileRight.param.*;
import vip.xiaonuo.pan.modular.fileRight.service.PanFileRightService;
import vip.xiaonuo.pan.modular.fileRight.vo.PanFileRightVo;
import vip.xiaonuo.pan.modular.fileRight.vo.UserFileRightVo;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanFileRightAddParam panFileRightAddParam) {
        List<PanFileRight> panFileRightList = BeanUtil.copyToList(panFileRightAddParam.getObjectRightList(),PanFileRight.class);
        panFileRightList.forEach(x-> x.setUserFileId(panFileRightAddParam.getUserFileId()));
        this.saveBatch(panFileRightList);
        //处理父级递归默认权限
        initParentFileRight(panFileRightAddParam.getUserFileId(), panFileRightAddParam.getObjectRightList(), FileRight.PENETRATE);
        //处理当前文件权限问题
        initCurrentFileRight(panFileRightAddParam.getUserFileId(),FileRight.PENETRATE);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanFileRightEditParam panFileRightEditParam) {
        //最新设置
        List<PanFileRight> newPanFileRights = BeanUtil.copyToList(panFileRightEditParam.getObjectRightList(),PanFileRight.class);
        newPanFileRights.forEach(x->{
            x.setUserFileId(panFileRightEditParam.getUserFileId());
        });

        //根据文件Id获取历史权限设置
        List<PanFileRight> oldFileRights = this.list(new QueryWrapper<PanFileRight>().lambda().eq(PanFileRight::getUserFileId,panFileRightEditParam.getUserFileId()));
        List<String> oldIds = oldFileRights.stream().map(PanFileRight::getId).collect(Collectors.toList());

        //新增 | 更新 | 删除
        //新增
        List<PanFileRight> addList = newPanFileRights.stream().filter(x -> StrUtil.isEmpty(x.getId())).collect(Collectors.toList());
        //更新
        List<PanFileRight> updateList = newPanFileRights.stream().filter(x -> StrUtil.isNotEmpty(x.getId())).collect(Collectors.toList());
        //删除
        List<String> updateIds = updateList.stream().map(PanFileRight::getId).collect(Collectors.toList());
        oldIds.removeAll(updateIds);
        if(CollUtil.isNotEmpty(addList)) this.saveBatch(addList);
        if(CollUtil.isNotEmpty(updateList)) this.updateBatchById(updateList);
        if(CollUtil.isNotEmpty(oldIds)) this.removeByIds(oldIds);

        //处理父级递归默认权限
        List<ObjectRight> objIds = BeanUtil.copyToList(panFileRightEditParam.getObjectRightList(),ObjectRight.class);
        initParentFileRight(panFileRightEditParam.getUserFileId(), objIds,FileRight.PENETRATE);
        //处理当前文件权限问题
        initCurrentFileRight(panFileRightEditParam.getUserFileId(),FileRight.PENETRATE);
    }


    @Override
    public List<PanFileRightVo> detail(PanFileRightIdParam panFileRightIdParam) {
        return panFileRightMapper.getFileRightByFileId(panFileRightIdParam.getUserFileId());
    }

    @Override
    public PanFileRight queryEntity(String id) {
        PanFileRight panFileRight = this.getById(id);
        if(ObjectUtil.isEmpty(panFileRight)) {
            throw new CommonException("网盘权限配置表不存在，id值为：{}", id);
        }
        return panFileRight;
    }


    /***
     * 根据 用户文件Id，校验当前用户是否有对应操作权限
     * @param userFileId 用户文件Id(UserFile.Id)
     * @param checkRight 操作权限(FileRight)
     *
     *  check 逻辑说明：
     *                1. 若当前用户为userFileId文件所有者，拥有全部操作权限
     *                2. 若当前用户不为userFileId文件所有者，根据权限配置校验权限
     *                   2.1 若当前userFileId，当前用户，存在权限配置，直接根据配置校验
     *                   2.2 若当前userFileId，当前用户，不存在权限配置，递归父级权限（就近原则），校验权限
     */
    @Override
    public boolean checkFileRight(String userFileId, long checkRight) {
        //1. 获取当前用户
        String userId = StpLoginUserUtil.getLoginUser().getId();

        //2. 获取文件相关信息
        UserFile userFile = userFileService.getById(userFileId);

        //超管，拥有所有权限
        if(StpLoginUserUtil.getLoginUser().getRoleCodeList().contains(SysBuildInEnum.BUILD_IN_ROLE_CODE.getValue()))
            return true;
        //文件管理者，拥有所有权限
        if(userId.equals(getFileManager(userFileId)))
            return true;

        //3. check 1. 若当前用户为userFileId文件所有者，拥有全部操作权限
        if(ObjectUtil.isNotEmpty(userFile) && userFile.getUserId().equals(userId))
            return true;

        //4. check 2. 若当前用户不为userFileId文件所有者，根据权限配置校验权限
        PanFileRight fileRight = this.getOne(new QueryWrapper<PanFileRight>().lambda()
                .eq(PanFileRight::getUserFileId,userFileId).eq(PanFileRight::getObjectId,userId));

        //5. check 2.1 若当前userFileId，当前用户，存在权限配置，直接根据配置校验
        if(ObjectUtil.isNotEmpty(fileRight)) {
            if(ObjectUtil.isNotEmpty(fileRight.getRightValue()) &&
                    FileRight.hasRight(fileRight.getRightValue(),checkRight))
                return true;
            else
                throw new CommonException("无操作权限");
        }
        //5. check 2.2 若当前userFileId，当前用户，不存在权限配置，递归父级权限（就近原则），校验权限
        else{
            UserFileRightVo rightVo = getParentRight(userFile.getParentId(),userId);
            if(ObjectUtil.isNotEmpty(rightVo) &&
                    ObjectUtil.isNotEmpty(rightVo.getRightValue()) &&
                    FileRight.hasRight(rightVo.getRightValue(),checkRight))
                return true;
            else
                throw new CommonException("无操作权限");
        }
    }


    /***
     * 递归，获取当前文件的 文件管理者
     * @param userFileId
     * @return
     */
    public String getFileManager(String userFileId){
        UserFile userFile = userFileService.getById(userFileId);
        if(ObjectUtil.isEmpty(userFile)) return "";
        if(StrUtil.isNotEmpty(userFile.getFileManager())) return userFile.getFileManager();
        return getFileManager(userFile.getParentId());
    }


    /***
     * 递归获取，就近权限
     * @param userFileId
     * @param userId
     * @return
     */
    public UserFileRightVo getParentRight(String userFileId,String userId){
        //获取某个userFile及权限信息
        UserFileRightVo rightVo = panFileRightMapper.getFileRight(userFileId,userId);
        if(ObjectUtil.isEmpty(rightVo)) return null;

        //就近，若有权限配置信息，直接返回，否则递归
        if(ObjectUtil.isNotEmpty(rightVo.getRightId()))
            return rightVo;
        else
            return getParentRight(rightVo.getParentId(),userId);
    }


    /***
     * 根据userFileId，获取所有父级文件信息
     * @param userFileId 用户文件Id
     * @return 所有的父级文件信息
     */
    public List<UserFile> getParents(String userFileId){
        List<UserFile> userFileList = new ArrayList<>();
        UserFile userFile = userFileService.getById(userFileId);
        if(ObjectUtil.isEmpty(userFile)) return userFileList;
        if(userFile.getParentId().equals("0")) return userFileList;
        //父级文件
        String parentId = userFile.getParentId();
        while (!parentId.equals("0")){
            UserFile parentFile = userFileService.getById(parentId);
            if(ObjectUtil.isNotEmpty(parentFile)) {
                userFileList.add(parentFile);
                parentId = parentFile.getParentId();
            }
            if(ObjectUtil.isEmpty(parentFile)) break;
        }
        return userFileList;
    }

    /***
     * 当为userFileId设置权限时，反向递归，为其父级（父级没有权限）赋予默认权限
     * @param userFileId 当前设置权限的用户文件Id
     * @param objects 被赋予权限的个人集合
     * @param right 初试化的权限值（穿透权限）
     *
     * 注意：在确定是否插入初试权限记录前，先要判断是否存在权限记录，即同一个userFileId，同一个userId,只能存在一条有效记录
     */
    public void initParentFileRight(String userFileId,List<ObjectRight> objects,long right){
        //1. 获取父级文件
        List<UserFile> parentFiles = getParents(userFileId);
        if(CollUtil.isEmpty(parentFiles)) return;
        List<String> userFileIds = parentFiles.stream().map(UserFile::getId).collect(Collectors.toList());

        //2. 判断处理赋值初试权限
        List<PanFileRight> addList = new ArrayList<>();
        List<PanFileRight> updateList = new ArrayList<>();
        for (ObjectRight obj:objects) {
            List<PanFileRight> oldFileRights = this.list(new QueryWrapper<PanFileRight>().lambda()
                    .eq(PanFileRight::getObjectId,obj.getObjectId())
                    .in(PanFileRight::getUserFileId,userFileIds));

            for (String fileId:userFileIds) {
                PanFileRight temp = oldFileRights.stream()
                        .filter(x->x.getUserFileId().equals(fileId)).findFirst().orElse(null);
                if(ObjectUtil.isEmpty(temp)){
                    PanFileRight newTemp = new PanFileRight();
                    newTemp.setUserFileId(fileId);
                    newTemp.setObjectId(obj.getObjectId());
                    newTemp.setRightValue(right);
                    addList.add(newTemp);
                }else{
                    assert temp != null;
                    if(temp.getRightValue() == FileRight.RIGHT_NULL){
                        temp.setRightValue(temp.getRightValue() | right);
                        updateList.add(temp);
                    }
                }
            }
        }
        if(CollUtil.isNotEmpty(addList)) this.saveBatch(addList);
        if(CollUtil.isNotEmpty(updateList)) this.updateBatchById(updateList);
    }


    /**
     * 处理当前userFileId
     * 原因：在给当前userFileId设置权限的时候，需要校验子集（儿子）是否被其他人有权限操作
     *      若（儿子）被设置操作，则需要处理当前文件权限（有权限不处理，无权限者需要穿透）
     * @param userFileId
     * @param right
     */
    public void initCurrentFileRight(String userFileId,Long right){
        //根据userFileId，获取其直接子集（儿子）
        List<UserFile> sonFiles = userFileService.list(new QueryWrapper<UserFile>().lambda().eq(UserFile::getParentId,userFileId));
        List<String> sonUserFileIds = sonFiles.stream().map(UserFile::getId).collect(Collectors.toList());

        //这些儿子文件，在FileRight中是否已有人使用[有权限]
        List<PanFileRight> sonFileRights = this.list(new QueryWrapper<PanFileRight>().lambda().in(PanFileRight::getUserFileId,sonUserFileIds));
        List<String> objectIds = sonFileRights.stream()
                .filter(x-> x.getRightValue() > FileRight.RIGHT_NULL)
                .map(PanFileRight::getObjectId).distinct().collect(Collectors.toList());

        //获取objectIds这些人中，是否存在当前文件权限
        List<PanFileRight> currentFileRights = this.list(new QueryWrapper<PanFileRight>().lambda()
                .eq(PanFileRight::getUserFileId,userFileId)
                .in(PanFileRight::getObjectId,objectIds));

        //2. 判断处理赋值初试权限
        List<PanFileRight> addList = new ArrayList<>();
        List<PanFileRight> updateList = new ArrayList<>();
        for (String objectId:objectIds) {
            PanFileRight temp = currentFileRights.stream()
                    .filter(x->x.getObjectId().equals(objectId)).findFirst().orElse(null);
            if(ObjectUtil.isEmpty(temp)){
                PanFileRight newTemp = new PanFileRight();
                newTemp.setUserFileId(userFileId);
                newTemp.setObjectId(objectId);
                newTemp.setRightValue(right);
                addList.add(newTemp);
            }else {
                if(temp.getRightValue() == FileRight.RIGHT_NULL) {
                    temp.setRightValue(temp.getRightValue() | right);
                    updateList.add(temp);
                }
            }
        }
        if(CollUtil.isNotEmpty(addList)) this.saveBatch(addList);
        if(CollUtil.isNotEmpty(updateList)) this.updateBatchById(updateList);
    }
}
