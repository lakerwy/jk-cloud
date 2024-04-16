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
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.dev.modular.file.entity.DevFileUnzip;
import vip.xiaonuo.dev.modular.file.param.DevFileIdParam;
import vip.xiaonuo.dev.modular.file.service.DevFileService;
import vip.xiaonuo.dev.modular.file.service.DevFileUnzipService;
import vip.xiaonuo.dev.modular.file.util.UnRarZip7Z;
import vip.xiaonuo.pan.modular.file.entity.PanFile;
import vip.xiaonuo.pan.modular.file.mapper.PanFileMapper;
import vip.xiaonuo.pan.modular.file.param.PanFileAddParam;
import vip.xiaonuo.pan.modular.file.param.PanFileEditParam;
import vip.xiaonuo.pan.modular.file.param.PanFileIdParam;
import vip.xiaonuo.pan.modular.file.param.PanFilePageParam;
import vip.xiaonuo.pan.modular.file.service.PanFileService;
import vip.xiaonuo.pan.modular.file.util.CommonZipUtil;
import vip.xiaonuo.pan.modular.file.util.PanSpZipUtil;
import vip.xiaonuo.pan.modular.file.vo.PanSourceFileVo;
import vip.xiaonuo.pan.modular.fileCenter.component.AsyncTaskComp;
import vip.xiaonuo.pan.modular.fileCenter.factory.FileFactory;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.Downloader;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.domain.DownloadFile;
import vip.xiaonuo.pan.modular.fileCenter.param.UnzipFileParam;
import vip.xiaonuo.pan.modular.fileCenter.param.ZipFileParam;
import vip.xiaonuo.pan.modular.fileCenter.util.CommonFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;
import vip.xiaonuo.pan.modular.userFile.mapper.UserFileMapper;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;

import javax.annotation.Resource;
import java.io.*;
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
@Slf4j
public class PanFileServiceImpl extends ServiceImpl<PanFileMapper, PanFile> implements PanFileService {

    private final static String GATEWAYDOWNLOADURL = "/api/webapp/dev/file/download?id=";

    @Resource
    private UserFileService userFileService;

    @Resource
    private FileFactory fileFactory;
    @Resource
    private PanFileService panFileService;
    @Resource
    UnRarZip7Z unRarZip7Z;
    @Resource
    AsyncTaskComp asyncTaskComp;
    @Resource
    CommonZipUtil commonZipUtil;
    @Resource
    DevFileService devFileService;
    @Resource
    DevFileUnzipService devFileUnzipService;
    @Resource
    private UserFileMapper userFileMapper;

    @Resource
    PanSpZipUtil panSpZipUtil;


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
        unzipFileNew(unzipFileParam);
//        String parentId = null;
//        if(unzipFileParam.getUnzipMode() == 2){
//            if(StrUtil.isEmpty(unzipFileParam.getFilePath())){
//                throw new CommonException("解压目标目录不能为空");
//            }
//            String filePath = unzipFileParam.getFilePath();
//            UserFile parentIdByPath = userFileService.getParentIdByPath(filePath);
//            if(ObjectUtil.isNull(parentIdByPath)){
//                throw new CommonException("目标目录不存在");
//            }
//            parentId = parentIdByPath.getId();
//        }
//        UserFile userFile = userFileService.getById(unzipFileParam.getUserFileId());
//        if(ObjectUtil.isEmpty(userFile)){
//            throw new CommonException("文件不存在，id值为：{}", unzipFileParam.getUserFileId());
//        }
//        if(unzipFileParam.getUnzipMode() == 0){
//            parentId = userFile.getParentId();
//        }
//        PanFile panFile = getById(userFile.getFileId());
//        File destFile = new File(FileCenterUtil.getStaticPath() + "temp" + File.separator + panFile.getFileUrl().replace(FileCenterUtil.getStaticPath(),""));
//
//
//        Downloader downloader = fileFactory.getDownloader(panFile.getStorageType());
//        DownloadFile downloadFile = new DownloadFile();
//        downloadFile.setFileUrl(panFile.getFileUrl());
//        InputStream inputStream = downloader.getInputStream(downloadFile);
//
//        try {
//            FileUtils.copyInputStreamToFile(inputStream, destFile);
//        } catch (IOException e) {
//            throw new CommonException("文件转换失败--");
//        }
//
//
//        String extendName = userFile.getExtendName();
//
//        String unzipUrl = FileCenterUtil.getTempFile(panFile.getFileUrl()).getAbsolutePath().replace("." + extendName, "");
//
//        List<String> fileEntryNameList = new ArrayList<>();
//
//        try {
////            fileEntryNameList = FileOperation.unzip(destFile, unzipUrl);
//            ZipUtil.unzip(destFile, new File(unzipUrl), CharsetUtil.CHARSET_GBK);
//            fileEntryNameList =  Arrays.stream(FileUtil.ls(unzipUrl))
//                    .map(File::getName)
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            log.error(e+"");
//            throw new CommonException("解压失败--",e);
//        }
//
//        if (destFile.exists()) {
//            boolean delete = destFile.delete();
//        }
//
//        if (CollUtil.isNotEmpty(fileEntryNameList) && unzipFileParam.getUnzipMode() == 1) {
//            //新建文件夹
//            UserFile userFile1 = new UserFile();
////            userFile1.setFileId(userFile.getUserId());
//            userFile1.setFileName(userFile.getFileName());
//            userFile1.setFilePath(CommonFile.formatPath(userFile.getFilePath()));
//            userFile1.setParentId(userFile.getParentId());
//            userFile1.setIsDir(1);
//            userFile1.setFoldType(userFile.getFoldType());
//            userFile1.setOrgId(userFile1.getOrgId());
//            userFileService.save(userFile1);
//            parentId = userFile1.getId();
//        }
//        panSpZipUtil.iteraSaveFile(userFile, panFile, unzipFileParam.getUnzipMode(),fileEntryNameList, unzipFileParam.getFilePath(),parentId);
//       /* for (String entryName : fileEntryNameList) {
//            asyncTaskComp.saveUnzipFile(userFile, panFile, unzipFileParam.getUnzipMode(), entryName, unzipFileParam.getFilePath(),parentId);
//        }*/
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
        if(CollectionUtil.isNotEmpty(zipFileParam.getUserFileId())){
            List<UserFile> userFiles = userFileService.list(new LambdaQueryWrapper<UserFile>().in(UserFile::getId,zipFileParam.getUserFileId()));
            if(CollectionUtil.isEmpty(userFiles)){
                throw new CommonException("未找到文件数据！");
            }
        }
        List<PanSourceFileVo> childrenFileList = getChildrenFileList(zipFileParam,new ArrayList<>(),inputMap);

        String inputFile = StringUtils.isNotEmpty(inputMap.get("inputFile")) ? inputMap.get("inputFile") : "";
        if(CollectionUtil.isNotEmpty(childrenFileList)){
            UserFile userFile = userFileService.getById(zipFileParam.getSaveFileId());
            if(ObjectUtil.isEmpty(inputFile)){ throw new CommonException("未找到该文件夹！");}
            if(!userFile.isDirectory()){ throw new CommonException("保存的路径不是文件夹！");}
            inputFile = inputFile+File.separator+zipFileParam.getIdentifier()+".zip";

            Long fileSize = commonZipUtil.zip(childrenFileList,inputFile);
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

    private List<PanSourceFileVo> getChildrenFileList(ZipFileParam zipFileParam,List<PanSourceFileVo> panSourceFileVoList,Map<String,String> inputMap){
        List<UserFile> userFiles = userFileService.list(new LambdaQueryWrapper<UserFile>().in(UserFile::getId,zipFileParam.getUserFileId()));
        List<UserFile> childrenFileList =  new ArrayList<>();
        if(CollectionUtil.isEmpty(userFiles)){
            throw new CommonException("未找到文件数据！");
        }
        userFiles.forEach(file -> {
            if(file.isDirectory()){
                List<UserFile> childrenList = userFileMapper.getChildren(file.getId());
                if(CollectionUtil.isNotEmpty(childrenList)){ childrenFileList.addAll(childrenList);}
            }else{
                childrenFileList.add(file);
            }
        });
        if(CollectionUtil.isEmpty(childrenFileList)){ throw new CommonException("需要压缩的文件为空！");}
        List<String> ids = new ArrayList<>();
        childrenFileList.forEach(file -> {
            if(StringUtils.isNotEmpty(file.getFileId())){
                panSourceFileVoList.add(new PanSourceFileVo(file.getFileName()+"."+file.getExtendName(),file.getFileId()));
                ids.add(file.getFileId());
            }
        });
        if(CollectionUtil.isEmpty(ids)){ throw new CommonException("需要压缩的文件为空！");}
        List<PanFile> panFileList = panFileService.list(new LambdaQueryWrapper<PanFile>().in(PanFile::getId,ids));
        Map<String,String> map = new HashMap<>();
        panFileList.forEach(pan ->{ map.put(pan.getId(),pan.getFileUrl());
            if(StringUtils.isEmpty(inputMap.get("inputFile"))){
                File fileTemp = new File(pan.getFileUrl());
                inputMap.put("inputFile",fileTemp.getParent());
            }
        });
        panSourceFileVoList.forEach( pan -> pan.setFilePath(map.get(pan.getFileId())));
        return panSourceFileVoList;
    }

    /** 解压文件 */
    @Override
    public List<DevFileUnzip> unZip(DevFileIdParam devFileIdParam){
        if(ObjectUtil.isNotEmpty(devFileIdParam.getPanFileFlag()) && devFileIdParam.getPanFileFlag()){
            return unPanZip(devFileIdParam);
        }
        DevFile devFile = devFileService.getById(devFileIdParam.getId());
        Boolean isParentFile = true;
        if(ObjectUtil.isEmpty(devFile)){
            DevFileUnzip devFileUnzip = devFileUnzipService.getById(devFileIdParam.getId());
            if(ObjectUtil.isEmpty(devFileUnzip)){
                throw new CommonException("找不到存储的文件，id值为："+devFileIdParam.getId());
            }
            isParentFile = false;
            devFile = BeanUtil.toBean(devFileUnzip,DevFile.class);
        }
        if(!devFile.getSuffix().equals("zip") && !devFile.getSuffix().equals("rar") && !devFile.getSuffix().equals("7z")){
            throw new CommonException("暂时只支持zip和rar的格式，id值为："+devFileIdParam.getId());
        }
        if(ObjectUtil.isNotEmpty(devFile.getIsUnzip()) && devFile.getIsUnzip() == 1){
            return devFileUnzipService.list(new LambdaQueryWrapper<DevFileUnzip>().eq(DevFileUnzip::getParentId,devFile.getId()));
        }
        File file = isParentFile ? FileUtil.file(devFile.getStoragePath()): FileUtil.file(devFile.getStoragePath()+ "." + devFile.getSuffix());
        /*if(devFile.getSuffix().equals("zip") && File.separator.contains("/")){
            String unixPath = devConfigService.getConfigByKey(PREFIX_UNZIP_PATH);
            return unzipSp( unixPath,devFile,isParentFile );
        }*/
        String oldFileName = file.getName();
        if(!FileUtil.exist(file)) {
            throw new CommonException("找不到存储的文件，id值为："+devFileIdParam.getId());
        }
        File fileTemp = convertBytesToFile(IoUtil.readBytes(FileUtil.getInputStream(file)),devFile.getStoragePath() + "." + devFile.getSuffix());
        String outFile = fileTemp.getParent()+File.separator+"temp"+File.separator+UUID.randomUUID().toString()+File.separator;

        File destFile = new File(outFile);
        try {
            boolean newFile = destFile.mkdir();
        }catch (Exception e){
            throw new CommonException("创建文件夹失败！");
        }
        String fileIdPre = DateUtil.date().toString("yyyyMMdd");
        try {
            unRarZip7Z.unRarZip7Z(fileTemp.getAbsolutePath(),outFile);
            List<Map<String,String>> filePaths = new ArrayList<>();
            getFileList(new File(outFile),filePaths,oldFileName);
            //getFileList(unzipFile,filePaths,oldFileName);
            List<DevFileUnzip> devFileIds = new ArrayList<>();
            for(Map<String,String> data : filePaths){
                DevFileUnzip devFileUnzip = new DevFileUnzip();
                String fileName = RandomUtil.randomNumbers(10);
                String fullFileId = fileIdPre+fileName;
                devFileUnzip.setId(fullFileId);
                devFileUnzip.setEngine(devFile.getEngine());
                devFileUnzip.setBucket(devFile.getBucket());
                devFileUnzip.setName(data.get("fileName"));
                devFileUnzip.setParentId(devFile.getId());
                devFileUnzip.setSuffix(data.get("suffix"));
                //devFileUnzip.setSizeKb(devFile.getSizeKb().toString());
                devFileUnzip.setSizeInfo(devFile.getSizeInfo());
                devFileUnzip.setStoragePath(data.get("absolutePath"));
                devFileUnzip.setDownloadPath(GATEWAYDOWNLOADURL+fullFileId);
                devFileIds.add(devFileUnzip);
            }
            devFileUnzipService.saveBatch(devFileIds);
            devFile.setIsUnzip(1);
            if(isParentFile){ devFileService.saveOrUpdate(devFile);}
            return devFileIds;
        } catch (Exception e) {
            log.error(e+"");
            throw new CommonException("解压失败--",e);
        }
    }

    private void getFileList(File file, List<Map<String,String>> fileList,String oldFileName){
        if (file == null) { return; }
        if (file.isDirectory()) {//如果是文件夹，就继续递归
            for (File tempFile : Objects.requireNonNull(file.listFiles())){getFileList(tempFile,fileList,oldFileName);}
        }
        //如果是文件，就解析一下，放到文件集合中
        if (file.isFile()){
            Map<String,String> map = new HashMap<>();
            int dotIndex = file.getAbsolutePath().lastIndexOf(".");
            if((dotIndex > 0 && dotIndex < file.getAbsolutePath().length() - 1) && !file.getAbsolutePath().contains(oldFileName)){
                map.put("absolutePath",file.getAbsolutePath().substring(0,dotIndex));
                map.put("fileName",file.getName());
                map.put("suffix",file.getAbsolutePath().substring(dotIndex+1,file.getAbsolutePath().length()));
                fileList.add(map);
            }
        }
    }

    public static File convertBytesToFile(byte[] bytes, String outputPath) {
        File outputFile = new File(outputPath);
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(bytes);
            return outputFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<DevFileUnzip> unzipSp(String prefixPath,DevFile devFile,Boolean isParentFile ){

        int dotIndex = devFile.getStoragePath().lastIndexOf("/");
        String filePath = "";
        if (dotIndex != -1) {
            filePath = devFile.getStoragePath().substring(0, dotIndex);
        }
        String fileName = devFile.getStoragePath().substring(dotIndex, devFile.getStoragePath().length());
        try {
            unRarZip7Z.unzipUnix(prefixPath+filePath,fileName);
            List<Map<String,String>> filePaths = new ArrayList<>();
            String outFilePath ="";
            int outIndex = devFile.getName().lastIndexOf(".");
            if (outIndex != -1) {
                outFilePath = filePath+"/"+devFile.getName().substring(0, outIndex);
            }
            File outFile = new File(outFilePath);
            log.info("outPath:"+outFilePath+",isTrue:"+outFile.exists());
            getFileList(outFile,filePaths,fileName);

            List<DevFileUnzip> devFileIds = new ArrayList<>();
            String fileIdPre = DateUtil.date().toString("yyyyMMdd");
            for(Map<String,String> data : filePaths){
                DevFileUnzip devFileUnzip = new DevFileUnzip();
                String randomNumbers = RandomUtil.randomNumbers(10);
                String fullFileId = fileIdPre+randomNumbers;
                devFileUnzip.setId(fullFileId);
                devFileUnzip.setEngine(devFile.getEngine());
                devFileUnzip.setBucket(devFile.getBucket());
                devFileUnzip.setName(data.get("fileName"));
                devFileUnzip.setParentId(devFile.getId());
                devFileUnzip.setSuffix(data.get("suffix"));
                //devFileUnzip.setSizeKb(devFile.getSizeKb().toString());
                devFileUnzip.setSizeInfo(devFile.getSizeInfo());
                devFileUnzip.setStoragePath(data.get("absolutePath"));
                devFileUnzip.setDownloadPath(GATEWAYDOWNLOADURL+fullFileId);
                devFileIds.add(devFileUnzip);
            }
            devFileUnzipService.saveBatch(devFileIds);
            devFile.setIsUnzip(1);
            if(isParentFile){ devFileService.saveOrUpdate(devFile);}
            return devFileIds;
        } catch (Exception e) {
            log.error(e+"");
            throw new CommonException("解压失败--",e);
        }
    }

    public List<DevFileUnzip> unPanZip(DevFileIdParam devFileIdParam){
        UserFile userFile = userFileService.getById(devFileIdParam.getId());
        Boolean isParentFile = true;
        PanFile panFile = panFileService.getById(userFile.getFileId());
        DevFile devFile = new DevFile();//以为dev为模版
        if(ObjectUtil.isEmpty(panFile)){
            DevFileUnzip devFileUnzip = devFileUnzipService.getById(devFileIdParam.getId());
            if(ObjectUtil.isEmpty(devFileUnzip)){throw new CommonException("找不到存储的文件，id值为："+devFileIdParam.getId());}
            isParentFile = false;
            devFile = BeanUtil.toBean(devFileUnzip,DevFile.class);
        }else{
            devFile.setId(userFile.getId());
            devFile.setStoragePath(panFile.getFileUrl());
            devFile.setSizeKb(ObjectUtil.isNotEmpty(panFile.getFileSize()) ? panFile.getFileSize().toString() : "0");
            devFile.setName(userFile.getFileName());
            devFile.setSuffix(userFile.getExtendName());
            devFile.setEngine("LOCAL");
            devFile.setSizeInfo(devFile.getSizeKb()+"KB");
        }
        if(!devFile.getSuffix().equals("zip") && !devFile.getSuffix().equals("rar") && !devFile.getSuffix().equals("7z")){
            throw new CommonException("暂时只支持zip和rar的格式，id值为："+devFileIdParam.getId());
        }
        File file = isParentFile ? FileUtil.file(devFile.getStoragePath()): FileUtil.file(devFile.getStoragePath());
        String oldFileName = file.getName();
        if(!FileUtil.exist(file)) {
            throw new CommonException("找不到存储的文件，id值为："+devFileIdParam.getId());
        }
        File fileTemp = convertBytesToFile(IoUtil.readBytes(FileUtil.getInputStream(file)),devFile.getStoragePath());
        String outFile = fileTemp.getParent()+File.separator+"temp"+File.separator+UUID.randomUUID().toString()+File.separator;

        File destFile = new File(outFile);
        try {
            boolean newFile = destFile.mkdir();
        }catch (Exception e){
            throw new CommonException("创建文件夹失败！");
        }
        String fileIdPre = DateUtil.date().toString("yyyyMMdd");
        try {
            unRarZip7Z.unRarZip7Z(fileTemp.getAbsolutePath(),outFile);
            List<Map<String,String>> filePaths = new ArrayList<>();
            getFileList(new File(outFile),filePaths,oldFileName);
            List<DevFileUnzip> devFileIds = new ArrayList<>();
            for(Map<String,String> data : filePaths){
                DevFileUnzip devFileUnzip = new DevFileUnzip();
                String fileName = RandomUtil.randomNumbers(10);
                String fullFileId = fileIdPre+fileName;
                devFileUnzip.setId(fullFileId);
                devFileUnzip.setEngine(devFile.getEngine());
                devFileUnzip.setBucket(devFile.getBucket());
                devFileUnzip.setName(data.get("fileName"));
                devFileUnzip.setParentId(devFile.getId());
                devFileUnzip.setSuffix(data.get("suffix"));
                devFileUnzip.setSizeInfo(devFile.getSizeInfo());
                devFileUnzip.setStoragePath(data.get("absolutePath"));
                devFileUnzip.setDownloadPath(GATEWAYDOWNLOADURL+fullFileId);
                devFileIds.add(devFileUnzip);
            }
            devFileUnzipService.saveBatch(devFileIds);
            return devFileIds;
        } catch (Exception e) {
            log.error(e+"");
            throw new CommonException("解压失败--",e);
        }
    }




    public void unzipFileNew(UnzipFileParam unzipFileParam) {
        if(unzipFileParam.getUnzipMode() == 2){
            if(StrUtil.isEmpty(unzipFileParam.getFilePath())){
                throw new CommonException("解压目标目录不能为空");
            }
            String filePath = unzipFileParam.getFilePath();
            UserFile parentIdByPath = userFileService.getParentIdByPath(filePath);
            if(ObjectUtil.isNull(parentIdByPath)){
                throw new CommonException("目标目录不存在");
            }
        }
        UserFile userFile = userFileService.getById(unzipFileParam.getUserFileId());
        if(ObjectUtil.isEmpty(userFile)){
            throw new CommonException("文件不存在，id值为：{}", unzipFileParam.getUserFileId());
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

        try {
            ZipUtil.unzip(destFile, new File(unzipUrl), CharsetUtil.CHARSET_GBK);
        } catch (Exception e) {
            log.error(e+"");
            throw new CommonException("解压失败--",e);
        }

        if (destFile.exists()) {
            boolean delete = destFile.delete();
        }
        panSpZipUtil.iteraSaveFileNew(userFile,unzipUrl,unzipFileParam);
    }
}
