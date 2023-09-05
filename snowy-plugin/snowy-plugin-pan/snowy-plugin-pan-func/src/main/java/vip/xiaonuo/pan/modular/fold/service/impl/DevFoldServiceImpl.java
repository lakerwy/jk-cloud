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
package vip.xiaonuo.pan.modular.fold.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.prop.CommonProperties;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.dev.modular.file.service.DevFileService;
import vip.xiaonuo.pan.core.ExceptionEnum;
import vip.xiaonuo.pan.modular.fold.entity.DevFold;
import vip.xiaonuo.pan.modular.fold.entity.NodeFold;
import vip.xiaonuo.pan.modular.fold.enums.DevFoldEnum;
import vip.xiaonuo.pan.modular.fold.mapper.DevFoldMapper;
import vip.xiaonuo.pan.modular.fold.param.*;
import vip.xiaonuo.pan.modular.fold.service.DevFoldService;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 文件主表Service接口实现类
 *
 * @author mayike
 * @date  2023/08/28 10:09
 **/
@Service
public class DevFoldServiceImpl extends ServiceImpl<DevFoldMapper, DevFold> implements DevFoldService {

    /**顶级父目录id*/
    private final static  String ROOT_FOLD_ID = "0";

    private final static String DEFAULT_BUCKET_NAME = "jk";

    private final static  Integer IS_DIR = 1;
    @Resource
    private DevFileService devFileService;

    @Resource
    private DevFoldMapper devFoldMapper;

    @Resource
    private CommonProperties commonProperties;

    @Override
    public Page<DevFold> page(DevFoldPageParam devFoldPageParam) {
        QueryWrapper<DevFold> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(devFoldPageParam.getFileName())) {
            queryWrapper.lambda().like(DevFold::getFileName, devFoldPageParam.getFileName());
        }
        if(ObjectUtil.isAllNotEmpty(devFoldPageParam.getSortField(), devFoldPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(devFoldPageParam.getSortOrder());
            queryWrapper.orderBy(true, devFoldPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(devFoldPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(DevFold::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(DevFoldAddParam devFoldAddParam) {
        DevFold devFold = BeanUtil.toBean(devFoldAddParam, DevFold.class);
        devFold.setIsDir(IS_DIR);
        devFold.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
        this.save(devFold);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(DevFoldEditParam devFoldEditParam) {
        DevFold devFold = this.queryEntity(devFoldEditParam.getId());
        BeanUtil.copyProperties(devFoldEditParam, devFold);
        this.updateById(devFold);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<DevFoldIdParam> devFoldIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(devFoldIdParamList, DevFoldIdParam::getId));
    }

    @Override
    public DevFold detail(DevFoldIdParam devFoldIdParam) {
        return this.queryEntity(devFoldIdParam.getId());
    }

    @Override
    public DevFold queryEntity(String id) {
        DevFold devFold = this.getById(id);
        if(ObjectUtil.isEmpty(devFold)) {
            throw new CommonException("文件主表不存在，id值为：{}", id);
        }
        return devFold;
    }

    @Override
    public Boolean isDirExist(DevFold  devFold) {
        List<DevFold> devFiles = getFolds(devFold);
        if(CollUtil.isEmpty(devFiles)){
            return  Boolean.TRUE;
        }
        return  Boolean.FALSE;
    }

    @Override
    public List<DevFold> getFolds(DevFold devFold) {
        if(ObjectUtil.isEmpty(devFold.getParentId())){
            devFold.setParentId(ROOT_FOLD_ID);
        }
        LambdaQueryWrapper<DevFold> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DevFold::getParentId, devFold.getParentId())
                .eq(DevFold::getFileName, devFold.getFileName())
                .eq(DevFold::getCreateUser, StpLoginUserUtil.getLoginUser().getId())
                .eq(DevFold::getDeleteFlag,"NOT_DELETE");
        return list(lambdaQueryWrapper);
    }

    @Override
    @Transactional
    public List<DevFile> uploadReturnBean(String engine, MultipartFile[] files, String parentId) {
        if(StrUtil.isBlank(parentId)){
            //如果父目录id为空，就上传到该用户默认创建的一级目录下
            parentId = createSelfFold();
        }
        List<DevFile> devFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            DevFile devFile = devFileService.uploadReturnBean(engine, file);
            DevFold devFold = new DevFold();
            devFold.setFileId(devFile.getId());
            devFold.setIsDir(0);
            devFold.setParentId(parentId);
            devFold.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
            devFold.setFileName(devFile.getName());
            save(devFold);
            devFiles.add(devFile);
        }
        return devFiles;
    }

    @Override
    public String createSelfFold(){
        LambdaQueryWrapper<DevFold> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DevFold::getParentId,ROOT_FOLD_ID).eq(DevFold::getIsDir,IS_DIR).eq(DevFold::getRootDir,DevFoldEnum.SELF_ROOT.getLevel())
                .eq(DevFold::getCreateUser,StpLoginUserUtil.getLoginUser().getId());
        DevFold one = getOne(queryWrapper);
        if(ObjectUtil.isNotEmpty(one)){
            return  one.getId();
        }
        DevFold devFold = new DevFold();
        devFold.setParentId(ROOT_FOLD_ID);
        devFold.setIsDir(IS_DIR);
        devFold.setRootDir(DevFoldEnum.SELF_ROOT.getLevel());
        devFold.setFoldType(DevFoldEnum.SELF_FOLD_TYPE.getLevel());
        devFold.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
        //个人根目录文件夹名不允许修改、移动和删除
        String name = StpLoginUserUtil.getLoginUser().getName();
        devFold.setFileName(name+"的个人文件夹");
        save(devFold);
        return  devFold.getId();
    }

    @Override
    public String createGroupFold() {
        LambdaQueryWrapper<DevFold> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DevFold::getParentId,ROOT_FOLD_ID).eq(DevFold::getIsDir,IS_DIR).eq(DevFold::getRootDir,DevFoldEnum.GROUP_ROOT.getLevel())
                .eq(DevFold::getOrgId,StpLoginUserUtil.getLoginUser().getOrgId());
        DevFold one = getOne(queryWrapper);
        if(ObjectUtil.isNotEmpty(one)){
            return  one.getId();
        }
        DevFold devFold = new DevFold();
        devFold.setParentId(ROOT_FOLD_ID);
        devFold.setIsDir(IS_DIR);
        devFold.setRootDir(DevFoldEnum.GROUP_ROOT.getLevel());
        devFold.setFoldType(DevFoldEnum.GROUP_FOLD_TYPE.getLevel());
        devFold.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
        //部门根目录文件夹名不允许修改、移动和删除
        String name = StpLoginUserUtil.getLoginUser().getOrgName();
        devFold.setFileName(name+"部门文件夹");
        save(devFold);
        return  devFold.getId();
    }

    @Override
    public void checkMove(DevFoldOperateParam devFoldOperateParam) {
        DevFold sourceDevFold = getOne(new LambdaQueryWrapper<DevFold>().eq(DevFold::getId, devFoldOperateParam.getSourceFoldId()));
        if(ObjectUtil.isEmpty(sourceDevFold)){
            throw  new CommonException(ExceptionEnum.SOURCE_FOLD_IS_NOT_EXIT.getValue());
        }
        DevFold targetDevFold = getOne(new LambdaQueryWrapper<DevFold>().eq(DevFold::getId, devFoldOperateParam.getTargetFoldId()));
        if(ObjectUtil.isEmpty(targetDevFold)){
            throw  new CommonException(ExceptionEnum.TARGET_FOLD_IS_NOT_EXIT.getValue());
        }
        if(Objects.equals(DevFoldEnum.GROUP_FOLD_TYPE.getLevel(), sourceDevFold.getFoldType()) &&
                Objects.equals(DevFoldEnum.SELF_FOLD_TYPE.getLevel(), targetDevFold.getFoldType())){
            throw  new CommonException(ExceptionEnum.FOLD_MOVE_NOT_SUPPORT.getValue());
        }
        if(checkFoldLock(devFoldOperateParam.getSourceFoldId())){
            throw new CommonException(ExceptionEnum.FOLD_MOVE_NOT_SUPPORT.getValue());
        }
    }

    /**
     * 判断某个目录下的文件夹是否被锁定
     * @param foldId
     * @return
     */
    @Override
    public Boolean checkFoldLock(String foldId){
        //todo
        return  false;
    }

    @Override
    public void moveFold(DevFoldOperateParam devFoldOperateParam) {
        DevFold devFold = queryEntity(devFoldOperateParam.getSourceFoldId());
        DevFold targetFold = getById(devFoldOperateParam.getTargetFoldId());
        devFold.setParentId(devFoldOperateParam.getTargetFoldId());
        devFold.setFoldType(targetFold.getFoldType());
        this.updateById(devFold);
    }

    /**
     * 展示个人文件夹和部门文件
     * @param devFoldPageParam
     * @return
     */

    @Override
    public Page<DevFold> pageList(DevFoldPageParam devFoldPageParam) {
        QueryWrapper<DevFold> queryWrapper = new QueryWrapper<>();
//        if(ObjectUtil.isEmpty(devFoldPageParam.getFoldType())){
//            devFoldPageParam.setFoldType(DevFoldEnum.SELF_FOLD_TYPE.getLevel());
//        }
        if(ObjectUtil.isEmpty(devFoldPageParam.getId())){
            devFoldPageParam.setId(ROOT_FOLD_ID);
        }
        if(ObjectUtil.isNotEmpty(devFoldPageParam.getFoldType())){
            queryWrapper.lambda().in(DevFold::getFoldType,devFoldPageParam.getFoldType());
        }
        queryWrapper.lambda().eq(DevFold::getOrgId,StpLoginUserUtil.getLoginUser().getOrgId());
        queryWrapper.lambda().eq(DevFold::getParentId,devFoldPageParam.getId());
        if(ObjectUtil.isNotEmpty(devFoldPageParam.getFileName())) {
            queryWrapper.lambda().like(DevFold::getFileName, devFoldPageParam.getFileName());
        }
        if(ObjectUtil.isAllNotEmpty(devFoldPageParam.getSortField(), devFoldPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(devFoldPageParam.getSortOrder());
            queryWrapper.orderBy(true, devFoldPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(devFoldPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(DevFold::getIsDir).orderByAsc(DevFold::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public Page<DevFold> pageListByParam(DevFoldPageParam devFoldPageParam) {
        //个人文件夹，默认展示一级目录下的
        QueryWrapper<DevFold> queryWrapper = new QueryWrapper<>();
//        if(ObjectUtil.isEmpty(devFoldPageParam.getFoldType())){
//            devFoldPageParam.setFoldType(DevFoldEnum.SELF_FOLD_TYPE.getLevel());
//        }
        if(ObjectUtil.isEmpty(devFoldPageParam.getId())){
            devFoldPageParam.setId(ROOT_FOLD_ID);
        }
        queryWrapper.lambda().eq(DevFold::getFoldType,devFoldPageParam.getFoldType());
        queryWrapper.lambda().eq(DevFold::getParentId,devFoldPageParam.getId());
        if(ObjectUtil.isNotEmpty(devFoldPageParam.getFileName())) {
            queryWrapper.lambda().like(DevFold::getFileName, devFoldPageParam.getFileName());
        }
        if(ObjectUtil.isAllNotEmpty(devFoldPageParam.getSortField(), devFoldPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(devFoldPageParam.getSortOrder());
            queryWrapper.orderBy(true, devFoldPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(devFoldPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(DevFold::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public Page<DevFold> groupPageList(DevFoldPageParam devFoldPageParam) {
        if(ObjectUtil.isNotEmpty(devFoldPageParam.getId())){
            QueryWrapper<DevFold> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(DevFold::getRootDir,DevFoldEnum.GROUP_ROOT.getLevel()).eq(DevFold::getOrgId,StpLoginUserUtil.getLoginUser().getOrgId());
            DevFold one = getOne(queryWrapper);
            if(ObjectUtil.isNotEmpty(one)){
                devFoldPageParam.setId(one.getId());
                return pageList(devFoldPageParam);
            }else {
                return new Page<>();
            }
        }else {
            return pageList(devFoldPageParam);
        }
    }

    @Override
    public Page<DevFold> selfPageList(DevFoldPageParam devFoldPageParam) {
        if(ObjectUtil.isEmpty(devFoldPageParam.getId())){
            QueryWrapper<DevFold> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(DevFold::getRootDir,DevFoldEnum.SELF_ROOT.getLevel()).eq(DevFold::getOrgId,StpLoginUserUtil.getLoginUser().getOrgId())
                    .eq(DevFold::getCreateUser,StpLoginUserUtil.getLoginUser().getId());
            DevFold one = getOne(queryWrapper);
            if(ObjectUtil.isNotEmpty(one)){
                devFoldPageParam.setId(one.getId());
                return pageList(devFoldPageParam);
            }else {
                return new Page<>();
            }
        }else {
            return pageList(devFoldPageParam);
        }
    }

    @Override
    public List<NodeFold> buildTree(String id) {
        List<NodeFold> nodeFolds = devFoldMapper.nodeList(id);
        return buildDeptTreeByStream(nodeFolds,id);
       // return buildTree(nodeFolds,NodeFold::getId,NodeFold::getParentId,NodeFold::getCreateTime,NodeFold::setChildren);
    }


    public static List<NodeFold> buildDeptTreeByStream(List<NodeFold> trees,String parentId){
        //获取parentId = 0的根节点
        List<NodeFold> list = trees.stream().filter(item -> item.getParentId().equals(parentId)).collect(Collectors.toList());
        //根据parentId进行分组
        Map<String, List<NodeFold>> map = trees.stream().collect(Collectors.groupingBy(NodeFold::getParentId));
        recursionFnTree(list, map);
        return list;
    }

    /**
     * 递归遍历节点
     * @param list
     * @param map
     */
    public static void recursionFnTree(List<NodeFold> list, Map<String, List<NodeFold>> map){
        for (NodeFold treeSelect : list) {
            List<NodeFold> childList = map.get(treeSelect.getId());
            treeSelect.setChildren(childList);
            if (null != childList && !childList.isEmpty()){
                recursionFnTree(childList,map);
            }
        }
    }

}
