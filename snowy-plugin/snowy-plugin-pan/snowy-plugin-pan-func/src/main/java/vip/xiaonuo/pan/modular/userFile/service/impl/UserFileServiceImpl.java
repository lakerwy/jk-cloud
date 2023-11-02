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
package vip.xiaonuo.pan.modular.userFile.service.impl;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.api.DevConfigApi;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.dev.modular.file.service.DevFileService;
import vip.xiaonuo.pan.modular.file.entity.PanFile;
import vip.xiaonuo.pan.modular.file.param.MultipartFileParam;
import vip.xiaonuo.pan.modular.file.param.PanRootFoldParam;
import vip.xiaonuo.pan.modular.file.param.UploadFileParam;
import vip.xiaonuo.pan.modular.fileRight.constant.FileRight;
import vip.xiaonuo.pan.modular.fileRight.service.PanFileRightService;
import vip.xiaonuo.pan.modular.userFile.param.UserFileTreeParam;
import vip.xiaonuo.pan.modular.file.service.PanFileService;
import vip.xiaonuo.pan.modular.fileCenter.component.FileDealComp;
import vip.xiaonuo.pan.modular.fileCenter.factory.FileFactory;
import vip.xiaonuo.pan.modular.fileCenter.operation.copy.Copier;
import vip.xiaonuo.pan.modular.fileCenter.operation.copy.domain.CopyFile;
import vip.xiaonuo.pan.modular.fileCenter.service.PanFileStorageService;
import vip.xiaonuo.pan.modular.fileCenter.util.CommonFile;
import vip.xiaonuo.pan.modular.recoveryfile.entity.PanRecoveryFile;
import vip.xiaonuo.pan.modular.recoveryfile.service.PanRecoveryFileService;
import vip.xiaonuo.pan.modular.userFile.entity.NodeFold;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;
import vip.xiaonuo.pan.modular.userFile.enums.UserFileEnum;
import vip.xiaonuo.pan.modular.userFile.mapper.UserFileMapper;
import vip.xiaonuo.pan.modular.userFile.param.*;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;
import vip.xiaonuo.sys.api.SysOrgApi;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static vip.xiaonuo.pan.modular.fileCenter.factory.FileFactory.storageType;

/**
 * 文件主表Service接口实现类
 *
 * @author mayike
 * @date  2023/08/28 10:09
 **/
@Service
@Slf4j
public class UserFileServiceImpl extends ServiceImpl<UserFileMapper, UserFile> implements UserFileService {

    /**顶级父目录id*/
    private final static  String ROOT_FOLD_ID = "0";

    private final static  String ROOT_FOLD_PATH= "/";

    public final static  Integer IS_DIR = 1;
    @Resource
    private DevFileService devFileService;

    @Resource
    private UserFileMapper userFileMapper;

    @Resource
    private  FileFactory fileFactory;

    @Resource
    private PanFileService panFileService;

    @Resource
    private PanRecoveryFileService panRecoveryFileService;

    @Resource
    FileDealComp fileDealComp;

    @Resource
    private DevConfigApi devConfigApi;

    @Resource
    private PanFileStorageService panFileStorageService;

    @Resource
    private SysOrgApi sysOrgApi;
    public static Executor executor = Executors.newFixedThreadPool(20);

    private static final String TEMPLATE_FOLD = "TEMPLATE_FOLD";

    @Resource
    private PanFileRightService panFileRightService;


    @Override
    public Page<UserFile> page(UserFilePageParam userFilePageParam) {
        QueryWrapper<UserFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(userFilePageParam.getFileName())) {
            queryWrapper.lambda().like(UserFile::getFileName, userFilePageParam.getFileName());
        }
        if(ObjectUtil.isAllNotEmpty(userFilePageParam.getSortField(), userFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(userFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, userFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(userFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(UserFile::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(UserFileAddParam userFileAddParam) {
        //todo 暂时不创建目录
        //devFileService.panStorageFold(SNOWY_SYS_DEFAULT_FILE_ENGINE_KEY,devFoldAddParam.getFilePath(),devFoldAddParam.getFileName());
        UserFile userFile = BeanUtil.toBean(userFileAddParam, UserFile.class);
        userFile.setIsDir(IS_DIR);
        userFile.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
        userFile.setUserId(StpLoginUserUtil.getLoginUser().getId());
        UserFile parentUserFile = getParentIdByPath(userFileAddParam.getFilePath());
        if(ObjectUtil.isNotEmpty(parentUserFile)){
            userFile.setParentId(parentUserFile.getId());
            userFile.setFoldType(parentUserFile.getFoldType());
        }
        userFile.setFileSize(0L);
        this.save(userFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(UserFileEditParam userFileEditParam) {
        UserFile userFile = this.queryEntity(userFileEditParam.getId());
        BeanUtil.copyProperties(userFileEditParam, userFile);
        this.updateById(userFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<UserFileIdParam> userFileIdParamList) {
        // 执行删除
        //this.removeByIds(CollStreamUtil.toList(userFileIdParamList, UserFileIdParam::getId));
        //1、如果是文件夹，则文件夹下所有文件都应该被标记为删除，如果不被标记的话，文件夹下分享出去的文件还是会被展示，就有问题
        List<UserFile> userFiles = listByIds(CollStreamUtil.toList(userFileIdParamList, UserFileIdParam::getId));
        String userId = StpLoginUserUtil.getLoginUser().getId();
        if(CollUtil.isNotEmpty(userFiles)){
            for (UserFile userFile : userFiles) {
                String uuid = UUID.randomUUID().toString();
                if(userFile.isDirectory()){
                    String filePath = new CommonFile(userFile.getFilePath(), userFile.getFileName(), true).getPath();
                    updateFileDeleteStateByFilePath(filePath,uuid ,userId);
                }
                executor.execute(()->{
                    updateDelete(uuid,userId, Collections.singletonList(userFile.getId()));
                });
                //回收站
                PanRecoveryFile recoveryFile = new PanRecoveryFile();
                recoveryFile.setUserFileId(userFile.getId());
                recoveryFile.setDeleteTime(DateUtil.date());
                recoveryFile.setDeleteBatchNum(uuid);
                panRecoveryFileService.save(recoveryFile);
            }
        }
    }

    @Override
    public UserFile detail(UserFileIdParam userFileIdParam) {
        return this.queryEntity(userFileIdParam.getId());
    }

    @Override
    public UserFile queryEntity(String id) {
        UserFile userFile = this.getById(id);
        if(ObjectUtil.isEmpty(userFile)) {
            throw new CommonException("文件主表不存在，id值为：{}", id);
        }
        return userFile;
    }

    @Override
    public Boolean isDirExist(UserFile userFile) {
        List<UserFile> devFiles = getFolds(userFile);
        if(CollUtil.isEmpty(devFiles)){
            return  Boolean.TRUE;
        }
        return  Boolean.FALSE;
    }

    @Override
    public List<UserFile> getFolds(UserFile userFile) {
        LambdaQueryWrapper<UserFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(userFile.getParentId())){
            lambdaQueryWrapper.eq(UserFile::getParentId,userFile.getParentId());
        }
        lambdaQueryWrapper.eq(UserFile::getFileName, userFile.getFileName())
                .eq(UserFile::getFilePath,userFile.getFilePath())
                .eq(UserFile::getUserId, StpLoginUserUtil.getLoginUser().getId())
                .eq(UserFile::getDeleteFlag,"NOT_DELETE");
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
            UserFile userFile = new UserFile();
            userFile.setFileId(devFile.getId());
            userFile.setIsDir(0);
            userFile.setParentId(parentId);
            userFile.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
            userFile.setFileName(devFile.getName());
            save(userFile);
            devFiles.add(devFile);
        }
        return devFiles;
    }

    @Override
    public String createSelfFold(){
        panFileStorageService.checkStorage(0L,0);
        LambdaQueryWrapper<UserFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFile::getParentId,ROOT_FOLD_ID).eq(UserFile::getIsDir,IS_DIR).eq(UserFile::getRootDir, UserFileEnum.SELF_ROOT.getLevel())
                .eq(UserFile::getCreateUser,StpLoginUserUtil.getLoginUser().getId());
        UserFile one = getOne(queryWrapper);
        if(ObjectUtil.isNotEmpty(one)){
            return  one.getId();
        }
        UserFile userFile = new UserFile();
        userFile.setParentId(ROOT_FOLD_ID);
        userFile.setIsDir(IS_DIR);
        userFile.setFilePath("/");
        userFile.setRootDir(UserFileEnum.SELF_ROOT.getLevel());
        userFile.setFoldType(UserFileEnum.SELF_FOLD_TYPE.getLevel());
        userFile.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
        userFile.setUserId(StpLoginUserUtil.getLoginUser().getId());
        //个人根目录文件夹名不允许修改、移动和删除
        String name = StpLoginUserUtil.getLoginUser().getName();
        userFile.setFileName(name+"的个人文件夹");
        save(userFile);
        return  userFile.getId();
    }

    @Override
    public String createGroupFold() {
        panFileStorageService.checkStorage(0L,1);
        LambdaQueryWrapper<UserFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFile::getParentId,ROOT_FOLD_ID).eq(UserFile::getIsDir,IS_DIR).eq(UserFile::getRootDir, UserFileEnum.GROUP_ROOT.getLevel())
                .eq(UserFile::getOrgId,StpLoginUserUtil.getLoginUser().getOrgId());
        UserFile one = getOne(queryWrapper);
        if(ObjectUtil.isNotEmpty(one)){
            return  one.getId();
        }
        UserFile userFile = new UserFile();
        userFile.setParentId(ROOT_FOLD_ID);
        userFile.setIsDir(IS_DIR);
        userFile.setFilePath("/");
        userFile.setRootDir(UserFileEnum.GROUP_ROOT.getLevel());
        userFile.setFoldType(UserFileEnum.GROUP_FOLD_TYPE.getLevel());
        userFile.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
        userFile.setUserId(StpLoginUserUtil.getLoginUser().getId());
        //部门根目录文件夹名不允许修改、移动和删除
        String name = StpLoginUserUtil.getLoginUser().getOrgName();
        userFile.setFileName(name+"部门文件夹");
        save(userFile);
        return  userFile.getId();
    }

    @Override
    public void checkMove(UserFileOperateParam userFileOperateParam) {
//        List<UserFile> sourceUserFile = list(new LambdaQueryWrapper<UserFile>().in(UserFile::getId, Arrays.stream(userFileOperateParam.getSourceFoldId())));
//        if(ObjectUtil.isEmpty(sourceUserFile)){
//            throw  new CommonException(ExceptionEnum.SOURCE_FOLD_IS_NOT_EXIT.getValue());
//        }
//        UserFile targetUserFile = getOne(new LambdaQueryWrapper<UserFile>().eq(UserFile::getId, userFileOperateParam.getTargetFoldId()));
//        if(ObjectUtil.isEmpty(targetUserFile)){
//            throw  new CommonException(ExceptionEnum.TARGET_FOLD_IS_NOT_EXIT.getValue());
//        }
//        if(Objects.equals(UserFileEnum.GROUP_FOLD_TYPE.getLevel(), sourceUserFile.getFoldType()) &&
//                Objects.equals(UserFileEnum.SELF_FOLD_TYPE.getLevel(), targetUserFile.getFoldType())){
//            throw  new CommonException(ExceptionEnum.FOLD_MOVE_NOT_SUPPORT.getValue());
//        }
//        if(checkFoldLock(userFileOperateParam.getSourceFoldId())){
//            throw new CommonException(ExceptionEnum.FOLD_MOVE_NOT_SUPPORT.getValue());
//        }
    }

    /**
     * 判断某个目录下的文件夹是否被锁定
     * @param foldId
     * @return
     */
    @Override
    public Boolean checkFoldLock(List<String> foldId){
        //todo
        return  false;
    }

    @Override
    public void moveFold(UserFileOperateParam userFileOperateParam) {
        UserFile parentUserFile = getParentIdByPath(userFileOperateParam.getTargetFoldPath());

        if(ObjectUtil.isEmpty(parentUserFile)){
            throw  new CommonException("目标文件夹不存在");
        }
        String targetFoldPath = userFileOperateParam.getTargetFoldPath();
//        if(ObjectUtil.isEmpty(targetFoldPath)){
//            targetFoldPath = destFile.getFilePath()+FileUtil.FILE_SEPARATOR+destFile.getFileName()+FileUtil.FILE_SEPARATOR;
//        }
        String fianlTargetFoldPath = targetFoldPath;
        for (String s : userFileOperateParam.getSourceFoldId()) {
            UserFile userFile = getById(s);
            if(ObjectUtil.isEmpty(userFile)){
                throw new CommonException("源文件夹不存在");
            }
            if(StrUtil.isEmpty(userFile.getExtendName())){
                CommonFile commonFile = new CommonFile(userFile.getFilePath(),userFile.getFileName(),Boolean.TRUE);
                if(targetFoldPath.startsWith(commonFile.getPath()+FileUtil.FILE_SEPARATOR) || targetFoldPath.equals(commonFile.getPath())){
                    throw new CommonException("原路径与目标路径冲突，不能复制");
                }
            }
            String oldfilePath = userFile.getFilePath();
            String oldUserId = userFile.getUserId();
            String fileName = userFile.getFileName();

            if (userFile.getIsDir() == 0) {
                String repeatFileName = fileDealComp.getRepeatFileName(userFile, userFileOperateParam.getTargetFoldPath());
                userFile.setFileName(repeatFileName);
            }

            oldfilePath = new CommonFile(oldfilePath, fileName, true).getPath();
            targetFoldPath = new CommonFile(targetFoldPath, fileName, true).getPath();


            if (userFile.isDirectory()) {
                List<UserFile> subUserFileList = selectUserFileByLikeRightFilePath(oldfilePath, oldUserId);

                for (UserFile newUserFile : subUserFileList) {
                    newUserFile.setFilePath(newUserFile.getFilePath().replaceFirst(oldfilePath, targetFoldPath));
                    if (newUserFile.isDirectory()) {
                        String repeatFileName = fileDealComp.getRepeatFileName(newUserFile, newUserFile.getFilePath());
                        newUserFile.setFileName(repeatFileName);
                    }
                    newUserFile.setUserId(StpLoginUserUtil.getLoginUser().getId());
                    try {
                        updateById(newUserFile);
                    } catch (Exception e) {
                        log.warn(e.getMessage());
                    }
                }
            }
            userFile.setFilePath(fianlTargetFoldPath);
            userFile.setParentId(parentUserFile.getId());
            userFile.setFoldType(parentUserFile.getFoldType());
            updateById(userFile);
            fileDealComp.deleteRepeatSubDirFile(fianlTargetFoldPath,StpLoginUserUtil.getLoginUser().getId());
        }
    }

    /**
     * 展示个人文件夹和部门文件
     * @param userFilePageParam
     * @return
     */

    @Override
    public Page<UserFile> pageList(UserFilePageParam userFilePageParam) {
        QueryWrapper<UserFile> queryWrapper = new QueryWrapper<>();
//        if(ObjectUtil.isEmpty(devFoldPageParam.getFoldType())){
//            devFoldPageParam.setFoldType(DevFoldEnum.SELF_FOLD_TYPE.getLevel());
//        }
        if(ObjectUtil.isEmpty(userFilePageParam.getFilePath())){
            UserFile one = null;
            if(Objects.equals(userFilePageParam.getRootDir(), UserFileEnum.GROUP_ROOT.getLevel())){
               one = buildGroupPageParam(userFilePageParam);
            }else  if(Objects.equals(userFilePageParam.getRootDir(), UserFileEnum.SELF_ROOT.getLevel())){
               one = buildSelfPageParam(userFilePageParam);
            }else {
                one = new UserFile();
                one.setFilePath(ROOT_FOLD_PATH);
                one.setId(ROOT_FOLD_ID);
            }
            if(null != one){
                userFilePageParam.setId(one.getId());
                userFilePageParam.setFilePath(one.getId().equals(ROOT_FOLD_ID)?ROOT_FOLD_PATH: (one.getFilePath()+FileUtil.FILE_SEPARATOR+one.getFileName()));
            }else {
                throw  new CommonException("父目录文件不存在");
            }
        }
        queryWrapper.lambda().eq(UserFile::getFilePath,userFilePageParam.getFilePath());
        if(ObjectUtil.isNotEmpty(userFilePageParam.getFoldType())){
            queryWrapper.lambda().in(UserFile::getFoldType, userFilePageParam.getFoldType());
        }
        if(ObjectUtil.isNotEmpty(userFilePageParam.getAllFlag()) && !userFilePageParam.getAllFlag()){
            queryWrapper.lambda().eq(UserFile::getOrgId,StpLoginUserUtil.getLoginUser().getOrgId());
        }
        if(ObjectUtil.isNotEmpty(userFilePageParam.getFileName())) {
            queryWrapper.lambda().like(UserFile::getFileName, userFilePageParam.getFileName());
        }
        if(ObjectUtil.isAllNotEmpty(userFilePageParam.getSortField(), userFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(userFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, userFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(userFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(UserFile::getFoldType).orderByDesc(UserFile::getIsDir).orderByAsc(UserFile::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public Page<UserFile> pageListByParam(UserFilePageParam userFilePageParam) {
        //个人文件夹，默认展示一级目录下的
        QueryWrapper<UserFile> queryWrapper = new QueryWrapper<>();
//        if(ObjectUtil.isEmpty(devFoldPageParam.getFoldType())){
//            devFoldPageParam.setFoldType(DevFoldEnum.SELF_FOLD_TYPE.getLevel());
//        }
        if(ObjectUtil.isEmpty(userFilePageParam.getId())){
            userFilePageParam.setId(ROOT_FOLD_ID);
        }
        queryWrapper.lambda().eq(UserFile::getFoldType, userFilePageParam.getFoldType());
        queryWrapper.lambda().eq(UserFile::getParentId, userFilePageParam.getId());
        if(ObjectUtil.isNotEmpty(userFilePageParam.getFileName())) {
            queryWrapper.lambda().like(UserFile::getFileName, userFilePageParam.getFileName());
        }
        if(ObjectUtil.isAllNotEmpty(userFilePageParam.getSortField(), userFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(userFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, userFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(userFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(UserFile::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public Page<UserFile> groupPageList(UserFilePageParam userFilePageParam) {
        if(ObjectUtil.isNotEmpty(userFilePageParam.getId())){
            QueryWrapper<UserFile> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(UserFile::getRootDir, UserFileEnum.GROUP_ROOT.getLevel()).eq(UserFile::getOrgId,StpLoginUserUtil.getLoginUser().getOrgId());
            UserFile one = getOne(queryWrapper);
            if(ObjectUtil.isNotEmpty(one)){
                userFilePageParam.setId(one.getId());
                return pageList(userFilePageParam);
            }else {
                return new Page<>();
            }
        }else {
            return pageList(userFilePageParam);
        }
    }

    private UserFile  buildGroupPageParam(UserFilePageParam userFilePageParam){
        QueryWrapper<UserFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserFile::getRootDir, UserFileEnum.GROUP_ROOT.getLevel()).eq(UserFile::getOrgId,StpLoginUserUtil.getLoginUser().getOrgId());
        UserFile one = getOne(queryWrapper);
        if(ObjectUtil.isNotEmpty(one)){
            return one;
//            userFilePageParam.setId(one.getId());
//            userFilePageParam.setFilePath(one.getFilePath()+FileUtil.FILE_SEPARATOR+one.getFileName());
        }
        return null;
    }

    private UserFile  buildSelfPageParam(UserFilePageParam userFilePageParam){
        QueryWrapper<UserFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserFile::getRootDir, UserFileEnum.SELF_ROOT.getLevel()).eq(UserFile::getOrgId,StpLoginUserUtil.getLoginUser().getOrgId())
                .eq(UserFile::getCreateUser,StpLoginUserUtil.getLoginUser().getId());
        UserFile one = getOne(queryWrapper);
        if(ObjectUtil.isNotEmpty(one)){
            return one;
//            userFilePageParam.setId(one.getId());
//            userFilePageParam.setFilePath(one.getFilePath()+FileUtil.FILE_SEPARATOR+one.getFileName());
        }
        return null;

    }
    @Override
    public Page<UserFile> selfPageList(UserFilePageParam userFilePageParam) {
        if(ObjectUtil.isEmpty(userFilePageParam.getId())){
            QueryWrapper<UserFile> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(UserFile::getRootDir, UserFileEnum.SELF_ROOT.getLevel()).eq(UserFile::getOrgId,StpLoginUserUtil.getLoginUser().getOrgId())
                    .eq(UserFile::getCreateUser,StpLoginUserUtil.getLoginUser().getId());
            UserFile one = getOne(queryWrapper);
            if(ObjectUtil.isNotEmpty(one)){
                userFilePageParam.setId(one.getId());
                return pageList(userFilePageParam);
            }else {
                return new Page<>();
            }
        }else {
            return pageList(userFilePageParam);
        }
    }

    @Override
    public List<NodeFold> buildTree(UserFileTreeParam userFileTreeParam) {
        Integer foldType = userFileTreeParam.getFoldType();
        if(0==foldType){
            userFileTreeParam.setUserId(StpLoginUserUtil.getLoginUser().getId());
            userFileTreeParam.setFoldTypes(new ArrayList<>(Collections.singletonList(0)));
        } else if (1==foldType) {
            String orgId = StpLoginUserUtil.getLoginUser().getOrgId();
//            List<String> childrenIds = sysOrgApi.getChildrenIds(orgId);
            //暂时只能看自己部门的
            userFileTreeParam.setOrgIds(new ArrayList<>(Collections.singletonList(orgId)));
            userFileTreeParam.setFoldTypes(new ArrayList<>(Collections.singletonList(1)));
        }else{
            userFileTreeParam.setFoldTypes(new ArrayList<>(Arrays.asList(1,2)));
        }
        List<NodeFold> nodeFolds = userFileMapper.nodeList(userFileTreeParam);
        return buildDeptTreeByStream(nodeFolds,userFileTreeParam.getId());
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
            if(treeSelect.getFilePath().equals(ROOT_FOLD_PATH)){
                treeSelect.setCurrentPath(treeSelect.getFilePath()+treeSelect.getFileName());
            }else {
                treeSelect.setCurrentPath(treeSelect.getFilePath()+ROOT_FOLD_PATH+treeSelect.getFileName());
            }
            List<NodeFold> childList = map.get(treeSelect.getId());
            treeSelect.setChildren(childList);
            if (null != childList && !childList.isEmpty()){
                recursionFnTree(childList,map);
            }
        }
    }


    @Override
    @Transactional
    public List<DevFile> uploadReturnBean(String engine, MultipartFileParam param) {
        String parentId = param.getParentId();
        if(StrUtil.isBlank(parentId)){
            //如果父目录id为空，就上传到该用户默认创建的一级目录下
            parentId = createSelfFold();
        }
        List<DevFile> devFiles = new ArrayList<>();
        for (MultipartFile file : param.getFiles()) {
            DevFile devFile = devFileService.uploadReturnBean(engine, file);
            UserFile userFile = new UserFile();
            userFile.setFileId(devFile.getId());
            userFile.setIsDir(0);
            userFile.setParentId(parentId);
            userFile.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
            userFile.setFileName(devFile.getName());
            save(userFile);
            devFiles.add(devFile);
        }
        return devFiles;
    }

    @Override
    public Long getTotalStorageSize() {
        return userFileMapper.getTotalStorageSize(StpLoginUserUtil.getLoginUser().getId());
    }

    @Override
    public List<UserFile> selectUserFileByLikeRightFilePath(String filePath, String userId) {
        return userFileMapper.selectUserFileByLikeRightFilePath(filePath,userId);
    }

    @Transactional
    @Override
    public void createFile(UserFileAddParam userFileAddParam) {
        String extendName = userFileAddParam.getExtendName();
        String templateFilePath = "";
        if ("docx".equals(extendName)) {
            templateFilePath = "Word.docx";
        } else if ("xlsx".equals(extendName)) {
            templateFilePath = "Excel.xlsx";
        } else if ("pptx".equals(extendName)) {
            templateFilePath = "PowerPoint.pptx";
        } else if ("txt".equals(extendName)) {
            templateFilePath = "Text.txt";
        } else if ("drawio".equals(extendName)) {
            templateFilePath = "Drawio.drawio";
        }
        InputStream inputStream = POICacheManager.getFile(templateFilePath);
        Copier copier = fileFactory.getCopier();
        CopyFile copyFile = new CopyFile();
        copyFile.setExtendName(extendName);
        String fileUrl = copier.copy(inputStream, copyFile);
        PanFile panFile = new PanFile();
        panFile.setFileUrl(fileUrl);
        panFile.setFileStatus(1);
        panFile.setFileSize(0L);
        panFile.setStorageType(storageType);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        panFile.setIdentifier(uuid);
        panFileService.save(panFile);

        UserFile userFile = new UserFile();
        userFile.setFilePath(userFileAddParam.getFilePath());
        userFile.setUserId(StpLoginUserUtil.getLoginUser().getId());
        userFile.setFileName(userFileAddParam.getFileName());
        userFile.setFileId(panFile.getId());
        userFile.setIsDir(0);
        userFile.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
        userFile.setParentId(userFileAddParam.getParentId());
        userFile.setExtendName(userFileAddParam.getExtendName());
        userFile.setFoldType(userFileAddParam.getFoldType());
        save(userFile);

    }

    @Override
    public void userFileCopy(UserFileOperateParam userFileOperateParam) {
        UserFile parentUserFile = getParentIdByPath(userFileOperateParam.getTargetFoldPath());

        if(ObjectUtil.isEmpty(parentUserFile)){
            throw  new CommonException("目标文件夹不存在");
        }
        String targetFoldPath = userFileOperateParam.getTargetFoldPath();
//        if(ObjectUtil.isEmpty(targetFoldPath)){
//            targetFoldPath = destFile.getFilePath()+FileUtil.FILE_SEPARATOR+destFile.getFileName()+FileUtil.FILE_SEPARATOR;
//        }
        String fianlTargetFoldPath = targetFoldPath;
        for (String s : userFileOperateParam.getSourceFoldId()) {
            UserFile userFile = getById(s);
            if(ObjectUtil.isEmpty(userFile)){
                throw new CommonException("源文件夹不存在");
            }
            if(userFile.isDirectory()){
                CommonFile commonFile = new CommonFile(userFile.getFilePath(),userFile.getFileName(),Boolean.TRUE);
                if(targetFoldPath.startsWith(commonFile.getPath()+FileUtil.FILE_SEPARATOR) || targetFoldPath.equals(commonFile.getPath())){
                    throw new CommonException("原路径与目标路径冲突，不能复制");
                }
            }
            String oldfilePath = userFile.getFilePath();
            String oldUserId = userFile.getUserId();
            String fileName = userFile.getFileName();
//            UserFile saveFile = new UserFile();
            //可能有问题
            userFile.setFilePath(targetFoldPath);
            userFile.setUserId(StpLoginUserUtil.getLoginUser().getId());
            if (userFile.getIsDir() == 0) {
                String repeatFileName = fileDealComp.getRepeatFileName(userFile, userFile.getFilePath());
                userFile.setFileName(repeatFileName);
            }
            userFile.setParentId(parentUserFile.getId());
            userFile.setFoldType(parentUserFile.getFoldType());
            userFile.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
            userFile.setId(null);
            save(userFile);

            oldfilePath = new CommonFile(oldfilePath, fileName, true).getPath();
            targetFoldPath = new CommonFile(targetFoldPath, fileName, true).getPath();


            if (userFile.isDirectory()) {
                List<UserFile> subUserFileList = selectUserFileByLikeRightFilePath(oldfilePath, oldUserId);

                for (UserFile newUserFile : subUserFileList) {
                    newUserFile.setFilePath(newUserFile.getFilePath().replaceFirst(oldfilePath, targetFoldPath));
                    if (newUserFile.isDirectory()) {
                        String repeatFileName = fileDealComp.getRepeatFileName(newUserFile, newUserFile.getFilePath());
                        newUserFile.setFileName(repeatFileName);
                    }
                    newUserFile.setUserId(StpLoginUserUtil.getLoginUser().getId());
                    try {
                        newUserFile.setId(null);
                        save(newUserFile);
                    } catch (Exception e) {
                        log.warn(e.getMessage());
                    }
                }
            }
            fileDealComp.deleteRepeatSubDirFile(fianlTargetFoldPath,StpLoginUserUtil.getLoginUser().getId());
        }


    }

    @Override
    public List<UserFile> getRootFold(PanRootFoldParam panRootFoldParam) {
        if(ObjectUtil.isEmpty(panRootFoldParam.getType())){
            panRootFoldParam.setType(UserFileEnum.SELF_ROOT.getLevel());
        }
        LambdaQueryWrapper<UserFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFile::getFoldType, panRootFoldParam.getType()).eq(UserFile::getFilePath,ROOT_FOLD_PATH);
        if(0==panRootFoldParam.getType()){
            queryWrapper.eq(UserFile::getUserId,StpLoginUserUtil.getLoginUser().getId());
        }else {
//            String orgId = StpLoginUserUtil.getLoginUser().getOrgId();
//            List<String> childrenIds = sysOrgApi.getChildrenIds(orgId);
            queryWrapper.eq(UserFile::getOrgId,StpLoginUserUtil.getLoginUser().getOrgId());
            //上级部门可以看到下级部门的文件
//            queryWrapper.in(UserFile::getOrgId,childrenIds);
        }
        return list(queryWrapper);
    }

    @Override
    public UserFile getParentIdByPath(String filePath) {
        if(filePath.equals(ROOT_FOLD_PATH)){
            //如果是 “/” 就是根目录
            UserFile userFile = new UserFile();
            userFile.setId(ROOT_FOLD_ID);
//            return ROOT_FOLD_ID;
            return userFile;
        }
        int index = filePath.lastIndexOf(ROOT_FOLD_PATH);
        String pathName = filePath.substring(index + 1);
        String parentPath = filePath.substring(0, index+1); //  "/" 或者”/A/B/“
        if(!parentPath.equals(ROOT_FOLD_PATH)){
            if(parentPath.endsWith(ROOT_FOLD_PATH)){
                parentPath = filePath.substring(0,index);
            }
        }
        return getOne(new LambdaQueryWrapper<UserFile>().eq(UserFile::getFilePath, parentPath).eq(UserFile::getFileName, pathName));
    }

    private void updateFileDeleteStateByFilePath(String filePath, String deleteBatchNum, String userId) {
        executor.execute(() -> {
            List<UserFile> fileList = selectUserFileByLikeRightFilePath(filePath, userId);
            List<String> userFileIds = fileList.stream().map(UserFile::getId).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(userFileIds)){
                updateDelete(deleteBatchNum,userId,userFileIds);
            }
        });
    }

    @Override
    public void updateDelete(String deleteBatchNum,String userId,List<String> userFileIds){
        userFileMapper.updateDelete(deleteBatchNum,userId,userFileIds);
    }

    @Override
    public Long getFilePointCount(String fileId) {
        LambdaQueryWrapper<UserFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserFile::getFileId, fileId);
        long count = userFileMapper.selectCount(lambdaQueryWrapper);
        return count;
    }

    /**
     * epc项目初始化文件夹
     */
    private void createTemplateFile(File file,String parentId,String filePath,String userId,String orgId) {
        try {
            InputStream inputStream = FileUtil.getInputStream(file);
            String suffix = FileUtil.getSuffix(file);
            long size = FileUtil.size(file);

            Copier copier = fileFactory.getCopier();
            CopyFile copyFile = new CopyFile();
            copyFile.setExtendName(suffix);
            String fileUrl = copier.copy(inputStream, copyFile);
            PanFile panFile = new PanFile();
            panFile.setFileUrl(fileUrl);
            panFile.setFileStatus(1);
            panFile.setFileSize(size);
            panFile.setStorageType(storageType);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            panFile.setIdentifier(uuid);
            panFileService.save(panFile);

            UserFile userFile = new UserFile();
            userFile.setFilePath(filePath);
            userFile.setUserId(userId);
            userFile.setFileName(FileUtil.mainName(file));
            userFile.setFileId(panFile.getId());
            userFile.setIsDir(0);
            userFile.setOrgId(orgId);
            userFile.setParentId(parentId);
            userFile.setExtendName(suffix);
            userFile.setFileSize(size);
            userFile.setFoldType(UserFileEnum.GROUP_FOLD_TYPE.getLevel());
            save(userFile);
        } catch (Exception e) {
            log.error("初始化文件失败");
        }
    }


    private void handleEpcFile(String parentId,String filePath,String userId,String orgId) {
//        org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:epc");
        try {
//            log.info("路径问题----"+filePath);
//            String absolutePath = epcFolder.getAbsolutePath();
//            log.info("路径问题----"+absolutePath);
            String valueByKey = devConfigApi.getValueByKey(TEMPLATE_FOLD);
            if(StrUtil.isNotEmpty(valueByKey)){
                File epcFolder = new File(valueByKey);
                if (epcFolder.exists() && epcFolder.isDirectory()) {
                    findFiles(epcFolder,parentId,filePath,userId,orgId);
                }
            }
        }catch (Exception e){
            log.error("获取模板文件异常",e);
        }

    }

    private void findFiles(File folder,String parentId,String filePath,String userId,String orgId) {
        File[] files = folder.listFiles();
        if (files != null) {
            log.info("默认创建文件---------------------个数"+files.length);
            for (File file : files) {
                if (file.isDirectory()) {
                    String name = file.getName();
                    UserFile fold = createTemplateFold(parentId, filePath, userId, orgId, name);
                    findFiles(file,fold.getId(),fold.getFilePath() +ROOT_FOLD_PATH+fold.getFileName(),userId,orgId); // 递归查找子目录
                } else {
                    createTemplateFile(file,parentId,filePath,userId,orgId);
//                    System.out.println("File: " + file.getName());
                    // 处理文件的逻辑
                }
            }
        }
    }

    public UserFile createTemplateFold(String parentId,String filePath,String userId,String orgId,String foldName){
        LambdaQueryWrapper<UserFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFile::getParentId,parentId).eq(UserFile::getIsDir,IS_DIR).eq(UserFile::getFileName,foldName)
                .eq(UserFile::getOrgId,orgId);
        UserFile one = getOne(queryWrapper);
        if(ObjectUtil.isNotEmpty(one)){
            return one;
        }
        UserFile userFile = new UserFile();
        userFile.setParentId(parentId);
        userFile.setIsDir(IS_DIR);
        userFile.setFilePath(filePath);
        userFile.setFoldType(UserFileEnum.GROUP_FOLD_TYPE.getLevel());
        userFile.setOrgId(orgId);
        userFile.setUserId(userId);
        //部门根目录文件夹名不允许修改、移动和删除
        userFile.setFileName(foldName);
//        userFiles.add(userFile);
        save(userFile);
        return userFile;
    }


    @Override
    public Boolean checkHomonym(UploadFileParam uploadFileParam){
        return false;
    }


    @Override
    public Page<UserFile> groupPage(UserFilePageParam userFilePageParam) {
        QueryWrapper<UserFile> queryWrapper = new QueryWrapper<>();
        String userId = StpLoginUserUtil.getLoginUser().getId();
        String orgId = StpLoginUserUtil.getLoginUser().getOrgId();
        List<String> orgIds = sysOrgApi.getParentIds(orgId,true);
        //判断是否为顶级目录
        if(ROOT_FOLD_PATH.equals(userFilePageParam.getFilePath())){
            //构造sql条件参数
            queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
            queryWrapper.eq("b.DELETE_FLAG","NOT_DELETE");
            queryWrapper.eq("a.FOLD_TYPE","1");
            queryWrapper.eq("a.FILE_PATH",userFilePageParam.getFilePath());
            queryWrapper.apply(" (b.RIGHT_VALUE & {0} > 0 ) ",FileRight.LIST);
            queryWrapper.and(q->{
                q.eq("b.OBJECT_ID",userId);
                if(CollUtil.isNotEmpty(orgIds)) q.or().in("b.OBJECT_ID",orgIds);
            });
            List<String> userFileIds = panFileRightService.notInUserFileIds(userId,orgId,orgIds);
            if(CollUtil.isNotEmpty(userFileIds))
                queryWrapper.notIn("a.ID",userFileIds);

            if(ObjectUtil.isNotEmpty(userFilePageParam.getFileName())) {
                queryWrapper.like("a.FILE_NAME", userFilePageParam.getFileName());
            }
            if(ObjectUtil.isAllNotEmpty(userFilePageParam.getSortField(), userFilePageParam.getSortOrder())) {
                CommonSortOrderEnum.validate(userFilePageParam.getSortOrder());
                queryWrapper.orderBy(true, userFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                        StrUtil.toUnderlineCase(userFilePageParam.getSortField()));
            } else {
                queryWrapper.orderByDesc("a.IS_DIR").orderByAsc("a.CREATE_TIME");
            }

            return userFileMapper.getGroupPage(CommonPageRequest.defaultPage(),queryWrapper);
        }else{
            List<String> userFileIds = panFileRightService.notInUserFileIds(userId,orgId,orgIds);
            queryWrapper.eq("DELETE_FLAG","NOT_DELETE");
            queryWrapper.eq("FOLD_TYPE","1");
            queryWrapper.eq("FILE_PATH",userFilePageParam.getFilePath());
            if(CollUtil.isNotEmpty(userFileIds))
                queryWrapper.notIn("ID",userFileIds);

            if(ObjectUtil.isNotEmpty(userFilePageParam.getFileName())) {
                queryWrapper.like("FILE_NAME", userFilePageParam.getFileName());
            }
            if(ObjectUtil.isAllNotEmpty(userFilePageParam.getSortField(), userFilePageParam.getSortOrder())) {
                CommonSortOrderEnum.validate(userFilePageParam.getSortOrder());
                queryWrapper.orderBy(true, userFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                        StrUtil.toUnderlineCase(userFilePageParam.getSortField()));
            } else {
                queryWrapper.orderByDesc("IS_DIR").orderByAsc("CREATE_TIME");
            }
            return userFileMapper.getGroupPage2(CommonPageRequest.defaultPage(),queryWrapper);
        }
    }
}
