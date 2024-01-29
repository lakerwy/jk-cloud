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
package vip.xiaonuo.pan.modular.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.pan.modular.file.entity.PanFile;
import vip.xiaonuo.pan.modular.file.mapper.PanFileMapper;
import vip.xiaonuo.pan.modular.file.param.PanFileAddParam;
import vip.xiaonuo.pan.modular.file.param.PanFileEditParam;
import vip.xiaonuo.pan.modular.file.param.PanFileIdParam;
import vip.xiaonuo.pan.modular.file.param.PanFilePageParam;
import vip.xiaonuo.pan.modular.file.service.PanFileService;
import vip.xiaonuo.pan.modular.file.util.CommonZipUtil;
import vip.xiaonuo.pan.modular.fileCenter.component.AsyncTaskComp;
import vip.xiaonuo.pan.modular.fileCenter.factory.FileFactory;
import vip.xiaonuo.pan.modular.fileCenter.operation.FileOperation;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.Downloader;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.domain.DownloadFile;
import vip.xiaonuo.pan.modular.fileCenter.param.UnzipFileParam;
import vip.xiaonuo.pan.modular.fileCenter.param.ZipFileParam;
import vip.xiaonuo.pan.modular.fileCenter.util.CommonFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 文件Service接口实现类
 *
 * @author mayike
 * @date  2023/09/06 21:15
 **/
@Service
public class PanFileServiceImpl extends ServiceImpl<PanFileMapper, PanFile> implements PanFileService {

    @Resource
    private UserFileService userFileService;

    @Resource
    private FileFactory fileFactory;
    @Resource
    private PanFileService panFileService;

    @Resource
    AsyncTaskComp asyncTaskComp;
    @Resource
    CommonZipUtil commonZipUtil;

    public static Executor exec = Executors.newFixedThreadPool(20);

    @Override
    public Page<PanFile> page(PanFilePageParam panFilePageParam) {
        QueryWrapper<PanFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(panFilePageParam.getSortField(), panFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, panFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanFile::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanFileAddParam panFileAddParam) {
        PanFile panFile = BeanUtil.toBean(panFileAddParam, PanFile.class);
        this.save(panFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanFileEditParam panFileEditParam) {
        PanFile panFile = this.queryEntity(panFileEditParam.getId());
        BeanUtil.copyProperties(panFileEditParam, panFile);
        this.updateById(panFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanFileIdParam> panFileIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panFileIdParamList, PanFileIdParam::getId));
    }

    @Override
    public PanFile detail(PanFileIdParam panFileIdParam) {
        return this.queryEntity(panFileIdParam.getId());
    }

    @Override
    public PanFile queryEntity(String id) {
        PanFile panFile = this.getById(id);
        if(ObjectUtil.isEmpty(panFile)) {
            throw new CommonException("文件不存在，id值为：{}", id);
        }
        return panFile;
    }

    @Override
    public void unzipFile(UnzipFileParam unzipFileParam) {
        String parentId = null;
        if(unzipFileParam.getUnzipMode() == 2){
            if(StrUtil.isEmpty(unzipFileParam.getFilePath())){
                throw new CommonException("解压目标目录不能为空");
            }
            String filePath = unzipFileParam.getFilePath();
            UserFile parentIdByPath = userFileService.getParentIdByPath(filePath);
            if(ObjectUtil.isNull(parentIdByPath)){
                throw new CommonException("目标目录不存在");
            }
            parentId = parentIdByPath.getId();
        }
        UserFile userFile = userFileService.getById(unzipFileParam.getUserFileId());
        if(ObjectUtil.isEmpty(userFile)){
            throw new CommonException("文件不存在，id值为：{}", unzipFileParam.getUserFileId());
        }
        if(unzipFileParam.getUnzipMode() == 0){
            parentId = userFile.getParentId();
        }
        PanFile panFile = getById(userFile.getFileId());
        File destFile = new File(FileCenterUtil.getStaticPath() + "temp" + File.separator + panFile.getFileUrl().replace(FileCenterUtil.getStaticPath(),""));


        Downloader downloader = fileFactory.getDownloader(panFile.getStorageType());
        DownloadFile downloadFile = new DownloadFile();
        downloadFile.setFileUrl(panFile.getFileUrl());
        InputStream inputStream = downloader.getInputStream(downloadFile);

        try {
            FileUtils.copyInputStreamToFile(inputStream, destFile);
        } catch (IOException e) {
            throw new CommonException("文件转换失败--");
        }


        String extendName = userFile.getExtendName();

        String unzipUrl = FileCenterUtil.getTempFile(panFile.getFileUrl()).getAbsolutePath().replace("." + extendName, "");

        List<String> fileEntryNameList = new ArrayList<>();

        try {
//            fileEntryNameList = FileOperation.unzip(destFile, unzipUrl);
            ZipUtil.unzip(destFile, new File(unzipUrl), CharsetUtil.CHARSET_GBK);
            fileEntryNameList =  Arrays.stream(FileUtil.ls(unzipUrl))
                    .map(File::getName)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e+"");
            throw new CommonException("解压失败--",e);
        }

        if (destFile.exists()) {
            boolean delete = destFile.delete();
        }

        if (CollUtil.isNotEmpty(fileEntryNameList) && unzipFileParam.getUnzipMode() == 1) {
            //新建文件夹
            UserFile userFile1 = new UserFile();
//            userFile1.setFileId(userFile.getUserId());
            userFile1.setFileName(userFile.getFileName());
            userFile1.setFilePath(CommonFile.formatPath(userFile.getFilePath()));
            userFile1.setParentId(userFile.getParentId());
            userFile1.setIsDir(1);
            userFile1.setFoldType(userFile.getFoldType());
            userFile1.setOrgId(userFile1.getOrgId());
            userFileService.save(userFile1);
            parentId = userFile1.getId();
        }
        for (String entryName : fileEntryNameList) {
            asyncTaskComp.saveUnzipFile(userFile, panFile, unzipFileParam.getUnzipMode(), entryName, unzipFileParam.getFilePath(),parentId);
        }
    }

    @Override
    public void updateFileDetail(String userFileId, String identifier, long fileSize) {
        UserFile userFile = userFileService.getById(userFileId);
        PanFile fileBean = new PanFile();
        fileBean.setIdentifier(identifier);
        fileBean.setFileSize(fileSize);
        fileBean.setId(userFile.getFileId());
        updateById(fileBean);
        userFileService.updateById(userFile);
    }

    @Override
    public void zipFile(ZipFileParam zipFileParam){
        if(CollectionUtil.isEmpty(zipFileParam.getUserFileId())){
            throw new CommonException("需要压缩的文件夹ID不能为空！");
        }
        Map<String,String> inputMap = new HashMap<>();
        inputMap.put("inputFile","");//压缩文件输出路径
        List<String> sourceFileList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(zipFileParam.getUserFileId())){
            List<UserFile> userFiles = userFileService.list(new LambdaQueryWrapper<UserFile>().in(UserFile::getId,zipFileParam.getUserFileId()));
            List<String> filePaths = new ArrayList<>();
            List<String> panFileIds = new ArrayList<>();
            userFiles.forEach(file -> {
                if(file.isDirectory()){
                    filePaths.add(file.getFilePath());//文件夹
                }else{
                    panFileIds.add(file.getFileId());
                }
            });
            if(CollectionUtil.isNotEmpty(filePaths)){
                filePaths.forEach(path -> {
                    List<UserFile>  list = userFileService.selectUserFileByLikeRightFilePath(path,StpLoginUserUtil.getLoginUser().getId());
                    if(CollectionUtil.isNotEmpty(list)){
                        list.forEach(file -> { if(!file.isDirectory()){ panFileIds.add(file.getFileId());}});
                    }
                });
            }
            if(CollectionUtil.isEmpty(panFileIds)){ throw new CommonException("需要压缩的文件为空！");}
            List<PanFile> panFileList = panFileService.list(new LambdaQueryWrapper<PanFile>().in(PanFile::getId,panFileIds));
            panFileList.forEach(pan -> {
                sourceFileList.add(pan.getFileUrl());
                if(StringUtils.isEmpty(inputMap.get("inputFile"))){
                    File file = new File(pan.getFileUrl());
                    inputMap.put("inputFile",file.getParent());
                }
            });
        }
        String inputFile = StringUtils.isNotEmpty(inputMap.get("inputFile")) ? inputMap.get("inputFile") : "";
        boolean isTrue = inputFile.contains("/") ? true : false;
        if(CollectionUtil.isNotEmpty(sourceFileList)){
            UserFile userFile = userFileService.getById(zipFileParam.getSaveFileId());
            if(ObjectUtil.isEmpty(inputFile)){ throw new CommonException("未找到该文件夹！");}
            if(!userFile.isDirectory()){ throw new CommonException("保存的路径不是文件夹！");}
            if(isTrue){
                inputFile = inputFile+"/"+zipFileParam.getIdentifier()+".zip";
            }else {
                inputFile = inputFile+"\\"+zipFileParam.getIdentifier()+".zip";
            }

            Long fileSize = commonZipUtil.zip(sourceFileList,inputFile);
            //插入PAN_FILE表数据
            PanFile panFile = new PanFile();
            panFile.setFileSize(fileSize);
            panFile.setFileUrl(inputFile);
            panFile.setFileStatus(1);
            panFile.setIdentifier(zipFileParam.getIdentifier());
            panFileService.save(panFile);

            //插入PAN_USER_FILE表数据
            UserFile userFile1 = new UserFile();
            userFile1.setParentId(userFile.getId());
            userFile1.setFileName(zipFileParam.getZipName());
            userFile1.setFilePath(CommonFile.formatPath(userFile.getFilePath()+"/"+userFile.getFileName()));
            userFile1.setIsDir(0);
            userFile1.setFileSize(fileSize);
            userFile1.setFoldType(userFile.getFoldType());
            userFile1.setOrgId(userFile.getOrgId());
            userFile1.setExtendName("zip");
            userFile1.setUserId(StpLoginUserUtil.getLoginUser().getId());
            userFile1.setFileId(panFile.getId());
            userFileService.save(userFile1);
        }
    }

}
