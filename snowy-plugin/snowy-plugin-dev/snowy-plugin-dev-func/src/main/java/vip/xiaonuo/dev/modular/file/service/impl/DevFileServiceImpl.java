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
package vip.xiaonuo.dev.modular.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.prop.CommonProperties;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.common.util.CommonResponseUtil;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.dev.modular.file.entity.DevFileUnzip;
import vip.xiaonuo.dev.modular.file.enums.DevFileEngineTypeEnum;
import vip.xiaonuo.dev.modular.file.mapper.DevFileMapper;
import vip.xiaonuo.dev.modular.file.param.DevFileIdParam;
import vip.xiaonuo.dev.modular.file.param.DevFileListParam;
import vip.xiaonuo.dev.modular.file.param.DevFilePageParam;
import vip.xiaonuo.dev.modular.file.service.DevFileService;
import vip.xiaonuo.dev.modular.file.service.DevFileUnzipService;
import vip.xiaonuo.dev.modular.file.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 文件Service接口实现类
 *
 * @author xuyuxiang
 * @date 2022/2/23 18:43
 **/
@Service
public class DevFileServiceImpl extends ServiceImpl<DevFileMapper, DevFile> implements DevFileService {

    @Resource
    private CommonProperties commonProperties;

    @Resource
    private DevFileMapper devFileMapper;
    @Resource
    private DevFileUnzipService devFileUnzipService;

    private final static String DOWNLOADURL = "/dev/file/download?id=";
    private final static String GATEWAYDOWNLOADURL = "/api/webapp/dev/file/download?id=";

    @Override
    public String uploadReturnId(String engine, MultipartFile file) {
        return this.storageFile(engine, file, null).getId();
    }

    @Override
    public String uploadReturnUrl(String engine, MultipartFile file) {
        return this.storageFile(engine, file, null).getDownloadPath();
    }

    @Override
    public Page<DevFile> page(DevFilePageParam devFilePageParam) {
        QueryWrapper<DevFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(devFilePageParam.getEngine())) {
            queryWrapper.lambda().eq(DevFile::getEngine, devFilePageParam.getEngine());
        }
        if(ObjectUtil.isNotEmpty(devFilePageParam.getSearchKey())) {
            queryWrapper.lambda().like(DevFile::getName, devFilePageParam.getSearchKey());
        }
        queryWrapper.lambda().orderByDesc(DevFile::getCreateTime);
        Page<DevFile> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<DevFile> records = page.getRecords();
        List<DevFile> newRecords = new ArrayList<>();
        records.forEach(devFile -> {
            if(devFile.getEngine().equals("LOCAL")){
                devFile.setDownloadPath(commonProperties.getBackendUrl()+devFile.getDownloadPath());
            }
            newRecords.add(devFile);
        });
        return  page.setRecords(newRecords);
    }

    @Override
    public List<DevFile> list(DevFileListParam devFileListParam) {
        QueryWrapper<DevFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(devFileListParam.getEngine())) {
            queryWrapper.lambda().eq(DevFile::getEngine, devFileListParam.getEngine());
        }
        if(ObjectUtil.isNotEmpty(devFileListParam.getSearchKey())) {
            queryWrapper.lambda().like(DevFile::getName, devFileListParam.getSearchKey());
        }
        List<DevFile> list = this.list(queryWrapper);
        List<DevFile> newRecords = new ArrayList<>();
        list.forEach(devFile -> {
            if(devFile.getEngine().equals("LOCAL")){
                devFile.setDownloadPath(commonProperties.getBackendUrl()+devFile.getDownloadPath());
            }
            newRecords.add(devFile);
        });
        return newRecords;
    }

    @Override
    public void download(DevFileIdParam devFileIdParam, HttpServletResponse response) throws IOException {
        DevFile devFile;
        try {
            devFile = this.getById(devFileIdParam.getId());
            if(ObjectUtil.isEmpty(devFile)) {
                DevFileUnzip devFileUnzip = devFileUnzipService.queryEntity(devFileIdParam.getId());
                devFile = BeanUtil.toBean(devFileUnzip,DevFile.class);
                devFile.setStoragePath(devFile.getStoragePath()+"."+devFile.getSuffix());
            }
        } catch (Exception e) {
            CommonResponseUtil.renderError(response, e.getMessage());
            return;
        }
        if(!devFile.getEngine().equals(DevFileEngineTypeEnum.LOCAL.getValue())) {
            CommonResponseUtil.renderError(response, "非本地文件不支持此方式下载，id值为：" + devFile.getId());
            return;
        }
        File file = FileUtil.file(devFile.getStoragePath());
        if(!FileUtil.exist(file)) {
            CommonResponseUtil.renderError(response, "找不到存储的文件，id值为：" + devFile.getId());
            return;
        }
        CommonDownloadUtil.download(devFile.getName(), IoUtil.readBytes(FileUtil.getInputStream(file)), response);

    }

    @Override
    public void unzipDownload(DevFileIdParam devFileIdParam, HttpServletResponse response) throws IOException {
        DevFileUnzip devFile;
        try {
            devFile = devFileUnzipService.queryEntity(devFileIdParam.getId());
        } catch (Exception e) {
            CommonResponseUtil.renderError(response, e.getMessage());
            return;
        }
        if(!devFile.getEngine().equals(DevFileEngineTypeEnum.LOCAL.getValue())) {
            CommonResponseUtil.renderError(response, "非本地文件不支持此方式下载，id值为：" + devFile.getId());
            return;
        }
        File file = FileUtil.file(devFile.getStoragePath()+"."+devFile.getSuffix());
        if(!FileUtil.exist(file)) {
            CommonResponseUtil.renderError(response, "找不到存储的文件，id值为：" + devFile.getId());
            return;
        }
        CommonDownloadUtil.download(devFile.getName(), IoUtil.readBytes(FileUtil.getInputStream(file)), response);
    }

    @Override
    public void delete(List<DevFileIdParam> devFileIdParamList) {
        this.removeByIds(CollStreamUtil.toList(devFileIdParamList, DevFileIdParam::getId));
    }

    /**
     * 存储文件
     *
     * @author xuyuxiang
     * @date 2022/6/16 16:24
     **/
    private DevFile storageFile(String engine, MultipartFile file, String bucketName) {

        // 如果引擎为空，默认使用本地
        if(ObjectUtil.isEmpty(engine)) {
            engine = DevFileEngineTypeEnum.LOCAL.getValue();
        }

        // 生成id
        //String fileId = IdWorker.getIdStr();
        String fileIdPre = DateUtil.date().toString("yyyyMMdd");
        String fileName = RandomUtil.randomNumbers(10);
        // 存储桶名称
//        String bucketName;

        // 定义存储的url，本地文件返回文件实际路径，其他引擎返回网络地址
        String storageUrl;

        final  String fullFileId = fileIdPre+fileName;

        // 根据引擎类型执行不同方法
        if(engine.equals(DevFileEngineTypeEnum.LOCAL.getValue())) {

            // 使用固定名称defaultBucketName
            if(StrUtil.isBlank(bucketName)){
                bucketName = "jk";
            }
            storageUrl = DevFileLocalUtil.storageFileWithReturnUrl(bucketName, genNewFileKey(fileName, file), file);
        } else if(engine.equals(DevFileEngineTypeEnum.ALIYUN.getValue())) {

            // 使用阿里云默认配置的bucketName
            bucketName = DevFileAliyunUtil.getDefaultBucketName();
            storageUrl = DevFileAliyunUtil.storageFileWithReturnUrl(bucketName, genNewFileKey(fileName, file), file);
        } else if(engine.equals(DevFileEngineTypeEnum.TENCENT.getValue())) {

            // 使用腾讯云默认配置的bucketName
            bucketName = DevFileTencentUtil.getDefaultBucketName();
            storageUrl = DevFileTencentUtil.storageFileWithReturnUrl(bucketName, genNewFileKey(fileName, file), file);
        } else if(engine.equals(DevFileEngineTypeEnum.MINIO.getValue())) {

            // 使用MINIO默认配置的bucketName
            bucketName = DevFileMinIoUtil.getDefaultBucketName();
            storageUrl = DevFileMinIoUtil.storageFileWithReturnUrl(bucketName, genNewFileKey(fileName, file), file);
        } else {
            throw new CommonException("不支持的文件引擎：{}", engine);
        }

        // 将文件信息保存到数据库
        DevFile devFile = new DevFile();

        // 设置文件id
        devFile.setId(fullFileId);

        // 设置存储引擎类型
        devFile.setEngine(engine);
        devFile.setBucket(bucketName);
        devFile.setName(file.getOriginalFilename());
        String suffix = ObjectUtil.isNotEmpty(file.getOriginalFilename())?StrUtil.subAfter(file.getOriginalFilename(),
                StrUtil.DOT, true):null;
        devFile.setSuffix(suffix);
        devFile.setSizeKb(Convert.toStr(NumberUtil.div(new BigDecimal(file.getSize()), BigDecimal.valueOf(1024))
                .setScale(0, RoundingMode.HALF_UP)));
        devFile.setSizeInfo(FileUtil.readableFileSize(file.getSize()));
        devFile.setObjName(ObjectUtil.isNotEmpty(devFile.getSuffix())?fullFileId + StrUtil.DOT + devFile.getSuffix():null);
        // 如果是图片，则压缩生成缩略图
        if(ObjectUtil.isNotEmpty(suffix)) {
            if(isPic(suffix)) {
                try {
                    devFile.setThumbnail(ImgUtil.toBase64DataUri(ImgUtil.scale(ImgUtil.toImage(file.getBytes()),
                            100, 100, null), suffix));
                } catch (Exception ignored) {
                }
            }
        }
        // 存储路径
        devFile.setStoragePath(storageUrl);

        // 定义下载地址
        String downloadUrl;

        // 下载路径，注意：本地文件下载地址设置为下载接口地址 + 文件id
        if(engine.equals(DevFileEngineTypeEnum.LOCAL.getValue())) {
            String apiUrl = commonProperties.getBackendUrl();
            if(ObjectUtil.isEmpty(apiUrl)) {
                throw new CommonException("后端域名地址未正确配置：snowy.config.common.backend-url为空");
            }
            String baseDownUrl = GATEWAYDOWNLOADURL + fullFileId;
            downloadUrl = apiUrl + baseDownUrl;
            devFile.setDownloadPath(baseDownUrl);
        } else {
            // 阿里云、腾讯云、MINIO可以直接使用存储地址（公网）作为下载地址
            downloadUrl= storageUrl;
            devFile.setDownloadPath(devFile.getStoragePath());
        }

        this.save(devFile);

        return  devFile;
//        // 如果是返回id则返回文件id
//        if(returnFileId) {
//            return fullFileId;
//        } else {
//            return downloadUrl;
//        }
    }

    /**
     * 生成文件的key，格式如 2021/10/11/1377109572375810050.docx
     *
     * @author xuyuxiang
     * @date 2022/4/22 15:58
     **/
    public String genFileKey(String fileId, MultipartFile file) {

        // 获取文件原始名称
        String originalFileName = file.getOriginalFilename();

        // 获取文件后缀
        String fileSuffix = FileUtil.getSuffix(originalFileName);

        // 生成文件的对象名称，格式如:1377109572375810050.docx
        String fileObjectName = fileId + StrUtil.DOT + fileSuffix;

        // 获取日期文件夹，格式如，2021/10/11/
        String dateFolderPath = DateUtil.thisYear() + StrUtil.SLASH +
                (DateUtil.thisMonth() + 1) + StrUtil.SLASH +
                DateUtil.thisDayOfMonth() + StrUtil.SLASH;

        // 返回
        return dateFolderPath + fileObjectName;
    }

    @Override
    public DevFile detail(DevFileIdParam devFileIdParam) {
        DevFile devFile = this.queryEntity(devFileIdParam.getId());
        if(devFile.getEngine().equals("LOCAL")){
            devFile.setDownloadPath(commonProperties.getBackendUrl()+devFile.getDownloadPath());
        }
        return devFile;
    }

    @Override
    public DevFile queryEntity(String id) {
        DevFile devFile = this.getById(id);
        if(ObjectUtil.isEmpty(devFile)) {
            throw new CommonException("文件不存在，id值为：{}", id);
        }
        return devFile;
    }

    /**
     * 根据文件后缀判断是否图片
     *
     * @author xuyuxiang
     * @date 2020/7/6 15:31
     */
    public static boolean isPic(String fileSuffix) {
        fileSuffix = fileSuffix.toLowerCase();
        return ImgUtil.IMAGE_TYPE_GIF.equals(fileSuffix)
                || ImgUtil.IMAGE_TYPE_JPG.equals(fileSuffix)
                || ImgUtil.IMAGE_TYPE_JPEG.equals(fileSuffix)
                || ImgUtil.IMAGE_TYPE_BMP.equals(fileSuffix)
                || ImgUtil.IMAGE_TYPE_PNG.equals(fileSuffix)
                || ImgUtil.IMAGE_TYPE_PSD.equals(fileSuffix);
    }

    @Override
    public Map<String,Object> uploadReturnId(String engine, String bucketName, MultipartFile file) {
        return this.newStorageFile(engine, file, bucketName);
    }

    @Override
    public DevFile selectInfoNow() {
        return devFileMapper.selectInfoNow();
    }

    @Override
    public List<DevFile> getInfosByIds(List<String> ids) {
        return devFileMapper.selectBatchIds(ids);
    }


    /**
     * 存储文件
     *
     * @author mayike
     * @date 2023/6/12 16:24
     **/
    private  Map<String,Object> newStorageFile(String engine, MultipartFile file, String bucketName) {

        // 如果引擎为空，默认使用本地
        if(ObjectUtil.isEmpty(engine)) {
            engine = DevFileEngineTypeEnum.LOCAL.getValue();
        }

        // 生成id
        //String fileId = IdWorker.getIdStr();

        // 存储桶名称
        //String bucketName;

        // 定义存储的url，本地文件返回文件实际路径，其他引擎返回网络地址
        String storageUrl;

        //获取一天中最后一条数据的id
//        DevFile devFile = new DevFile();
//        String fileIdPre = getFileIdPre();
        String fileIdPre = DateUtil.date().toString("yyyyMMdd");
        String fileName = RandomUtil.randomNumbers(10);
//        if(null != devFile){
//            //202008090001
//            String fileid = devFile.getId();
//            fileIdPre = devFile.getId().substring(0,8);
//            //0001
//            fileName = String.format("%04d", Integer.parseInt(fileid.substring(8)) + 1);
//        }else {
//            Date date = new Date();
//            DateFormat format = new SimpleDateFormat("yyyyMMdd");
//            fileIdPre = format.format(date);
//            fileName = String.format("%04d", 1);
//        }
        final  String fullFileId = fileIdPre+fileName;
        // 根据引擎类型执行不同方法
        if(engine.equals(DevFileEngineTypeEnum.LOCAL.getValue())) {
            if(StrUtil.isBlank(bucketName)){
                // 使用固定名称defaultBucketName
                bucketName = "jk";
            }
            storageUrl = DevFileLocalUtil.storageFileWithReturnUrl(bucketName, genNewFileKey(fileName, file), file);
        } else if(engine.equals(DevFileEngineTypeEnum.ALIYUN.getValue())) {

            // 使用阿里云默认配置的bucketName
            bucketName = DevFileAliyunUtil.getDefaultBucketName();
            storageUrl = DevFileAliyunUtil.storageFileWithReturnUrl(bucketName, genNewFileKey(fileName, file), file);
        } else if(engine.equals(DevFileEngineTypeEnum.TENCENT.getValue())) {

            // 使用腾讯云默认配置的bucketName
            bucketName = DevFileTencentUtil.getDefaultBucketName();
            storageUrl = DevFileTencentUtil.storageFileWithReturnUrl(bucketName, genNewFileKey(fileName, file), file);
        } else if(engine.equals(DevFileEngineTypeEnum.MINIO.getValue())) {

            // 使用MINIO默认配置的bucketName
            bucketName = DevFileMinIoUtil.getDefaultBucketName();
            storageUrl = DevFileMinIoUtil.storageFileWithReturnUrl(bucketName, genNewFileKey(fileName, file), file);
        } else {
            throw new CommonException("不支持的文件引擎：{}", engine);
        }

        // 将文件信息保存到数据库
        DevFile devFile = new DevFile();
        //String fullFileId = fileIdPre+fileName;
        // 设置文件id
        devFile.setId(fullFileId);

        // 设置存储引擎类型
        devFile.setEngine(engine);
        devFile.setBucket(bucketName);
        devFile.setName(file.getOriginalFilename());
        String suffix = ObjectUtil.isNotEmpty(file.getOriginalFilename())?StrUtil.subAfter(file.getOriginalFilename(),
                StrUtil.DOT, true):null;
        devFile.setSuffix(suffix);
        devFile.setSizeKb(Convert.toStr(NumberUtil.div(new BigDecimal(file.getSize()), BigDecimal.valueOf(1024))
                .setScale(0, BigDecimal.ROUND_HALF_UP)));
        devFile.setSizeInfo(FileUtil.readableFileSize(file.getSize()));
        devFile.setObjName(ObjectUtil.isNotEmpty(devFile.getSuffix())?fullFileId + StrUtil.DOT + devFile.getSuffix():fullFileId);
        // 如果是图片，则压缩生成缩略图
        if(ObjectUtil.isNotEmpty(suffix)) {
            if(isPic(suffix)) {
                try {
                    devFile.setThumbnail(ImgUtil.toBase64DataUri(ImgUtil.scale(ImgUtil.toImage(file.getBytes()),
                            100, 100, null), suffix));
                } catch (IOException e) {
                    throw new CommonException("文件上传失败，获取文件流错误");
                }
            }
        }
        // 存储路径
        devFile.setStoragePath(storageUrl);

        // 定义下载地址
        String downloadUrl;

        // 下载路径，注意：本地文件下载地址设置为下载接口地址 + 文件id
        if(engine.equals(DevFileEngineTypeEnum.LOCAL.getValue())) {
//            String apiUrl = commonProperties.getBackendUrl();
//            if(ObjectUtil.isEmpty(apiUrl)) {
//                throw new CommonException("后端域名地址未正确配置：snowy.config.common.backend-url为空");
//            }
//            downloadUrl= apiUrl + "/dev/file/download?id=" + fullFileId;
            //String apiUrl = commonProperties.getBackendUrl();
            downloadUrl = GATEWAYDOWNLOADURL+fullFileId;
            devFile.setDownloadPath(downloadUrl);
        } else {
            // 阿里云、腾讯云、MINIO可以直接使用存储地址（公网）作为下载地址
            downloadUrl= storageUrl;
            devFile.setDownloadPath(devFile.getStoragePath());
        }

        this.save(devFile);
        //保存附件
        Map<String, Object> map = new HashMap<>();
        map.put("url",downloadUrl);
        map.put("name",file.getOriginalFilename());
        map.put("fileId",fullFileId);
        map.put("previewUrl",commonProperties.getBackendUrl()+downloadUrl);
        return map;
        // 如果是返回id则返回文件id
//        if(returnFileId) {
//            return fileName;
//        } else {
//            // 否则返回下载地址
//            return downloadUrl;
//        }
    }

    public String genNewFileKey(String fileName, MultipartFile file) {

        // 获取文件原始名称
        //String originalFileName = file.getOriginalFilename();

        // 获取文件后缀
        //String fileSuffix = FileUtil.getSuffix(originalFileName);
        //        String fileObjectName =fileId;
//        if(StrUtil.isNotBlank(fileSuffix)){
//            // 生成文件的对象名称，格式如:1377109572375810050.docx
//            fileObjectName += StrUtil.DOT + fileSuffix;
//        }

        // 获取日期文件夹，格式如，2023/0612/
        String fmt = "yyyy" + StrUtil.SLASH +"MMdd";
        Date date = new Date();
        DateFormat format = new SimpleDateFormat(fmt);
        String dateFolderPath = format.format(date)+StrUtil.SLASH;

        // 返回
        return dateFolderPath + fileName;
    }

    private  String getFileIdPre(){
        Map<String, String> map = handleFileName();
        return map.get("fileIdPre");
    }

    private  String getFileName(){
        Map<String, String> map = handleFileName();
        return map.get("fileName");
    }

    private  Map<String,String> handleFileName(){
        //获取一天中最后一条数据的id
        DevFile devFile = this.selectInfoNow();
        String fileIdPre = "";
        String fileName = "";
        if(null != devFile){
            //202008090001
            String fileid = devFile.getId();
            fileIdPre = devFile.getId().substring(0,8);
            //0001
            fileName = String.format("%04d", Integer.parseInt(fileid.substring(8)) + 1);
        }else {
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyyMMdd");
            fileIdPre = format.format(date);
            fileName = String.format("%04d", 1000);
        }
        Map<String,String> map = new HashMap<>();
        map.put("fileIdPre",fileIdPre);
        map.put("fileName",fileName);
        return map;
    }

    @Override
    public String uploadReturnUrl(MultipartFile file, String bucketName) {
        return this.storageFile(null, file,bucketName).getDownloadPath();
    }

    @Override
    public DevFile uploadReturnBean(String engine, MultipartFile file) {
        return this.storageFile(null, file,null);
    }

    @Override
    public void mkDir(String engine, String dirName) {
        // 如果引擎为空，默认使用本地
        if (ObjectUtil.isEmpty(engine)) {
            engine = DevFileEngineTypeEnum.LOCAL.getValue();
        }
        // 根据引擎类型执行不同方法
        if (engine.equals(DevFileEngineTypeEnum.LOCAL.getValue())) {

            // 使用固定名称defaultBucketName
            if (StrUtil.isBlank(dirName)) {
                dirName = "jk";
            }
            DevFileLocalUtil.mkdirBucket(dirName);
        } else if (engine.equals(DevFileEngineTypeEnum.ALIYUN.getValue())) {
            //todo
            // 使用阿里云默认配置的bucketName
            //bucketName = DevFileAliyunUtil.getDefaultBucketName();
            //storageUrl = DevFileAliyunUtil.storageFileWithReturnUrl(dirName, genNewFileKey(fileName, file), file);
        } else if (engine.equals(DevFileEngineTypeEnum.TENCENT.getValue())) {
            //todo
            // 使用腾讯云默认配置的bucketName
            //bucketName = DevFileTencentUtil.getDefaultBucketName();
            // storageUrl = DevFileTencentUtil.storageFileWithReturnUrl(bucketName, genNewFileKey(fileName, file), file);
        } else if (engine.equals(DevFileEngineTypeEnum.MINIO.getValue())) {

            // 使用MINIO默认配置的bucketName
            //bucketName = DevFileMinIoUtil.getDefaultBucketName();
            DevFileMinIoUtil.mkdirBucket(dirName);
        } else {
            throw new CommonException("不支持的文件引擎：{}", engine);
        }
    }

//    public void pageList(String engine, String dirPath) {
//        // 如果引擎为空，默认使用本地
//        if(ObjectUtil.isEmpty(engine)) {
//            engine = DevFileEngineTypeEnum.LOCAL.getValue();
//        }
//
//
//        // 定义存储的url，本地文件返回文件实际路径，其他引擎返回网络地址
//        String storageUrl;
//        String dirPath = parentPath + FileUtil.FILE_SEPARATOR + bucketName + FileUtil.FILE_SEPARATOR;
//        // 根据引擎类型执行不同方法
//        if(engine.equals(DevFileEngineTypeEnum.LOCAL.getValue())) {
//            DevFileLocalUtil.mkdirBucket(dirPath);
//        } else if(engine.equals(DevFileEngineTypeEnum.ALIYUN.getValue())) {
//            DevFileAliyunUtil.mkdirDirPath(dirPath);
//        } else if(engine.equals(DevFileEngineTypeEnum.TENCENT.getValue())) {
//            DevFileTencentUtil.mkdirDirPath(dirPath);
//        } else if(engine.equals(DevFileEngineTypeEnum.MINIO.getValue())) {
//            DevFileMinIoUtil.mkdirBucket(dirPath);
//        } else {
//            throw new CommonException("不支持的文件引擎：{}", engine);
//        }
//
////        if (ObjectUtil.isEmpty(engine)) {
////            engine = DevFileEngineTypeEnum.LOCAL.getValue();
////        }
////        // 根据引擎类型执行不同方法
////        if (engine.equals(DevFileEngineTypeEnum.LOCAL.getValue())) {
////
////            // 使用固定名称defaultBucketName
////            if (StrUtil.isBlank(dirPath)) {
////                dirName = "jk";
////            }
////            DevFileLocalUtil.mkdirBucket(dirName);
////        } else if (engine.equals(DevFileEngineTypeEnum.ALIYUN.getValue())) {
////            //todo
////            // 使用阿里云默认配置的bucketName
////            //bucketName = DevFileAliyunUtil.getDefaultBucketName();
////            //storageUrl = DevFileAliyunUtil.storageFileWithReturnUrl(dirName, genNewFileKey(fileName, file), file);
////        } else if (engine.equals(DevFileEngineTypeEnum.TENCENT.getValue())) {
////            //todo
////            // 使用腾讯云默认配置的bucketName
////            //bucketName = DevFileTencentUtil.getDefaultBucketName();
////            // storageUrl = DevFileTencentUtil.storageFileWithReturnUrl(bucketName, genNewFileKey(fileName, file), file);
////        } else if (engine.equals(DevFileEngineTypeEnum.MINIO.getValue())) {
////
////            // 使用MINIO默认配置的bucketName
////            //bucketName = DevFileMinIoUtil.getDefaultBucketName();
////            DevFileMinIoUtil.mkdirBucket(dirName);
////        } else {
////            throw new CommonException("不支持的文件引擎：{}", engine);
////        }
//    }

    /** 解压文件 */
    @Override
    public List<DevFileUnzip> unZip(DevFileIdParam devFileIdParam){
        DevFile devFile = this.getById(devFileIdParam.getId());
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
        String oldFileName = file.getName();
        if(!FileUtil.exist(file)) {
            throw new CommonException("找不到存储的文件，id值为："+devFileIdParam.getId());
        }
        File fileTemp = convertBytesToFile(IoUtil.readBytes(FileUtil.getInputStream(file)),devFile.getStoragePath() + "." + devFile.getSuffix());
        String outFile = fileTemp.getParent();
        if(outFile.contains("\\")){
            outFile = outFile+"\\"+UUID.randomUUID().toString();
        }else{
            outFile = outFile+"/"+UUID.randomUUID().toString();
        }
        File destFile = new File(outFile);
        try {
            boolean newFile = destFile.mkdir();
        }catch (Exception e){
            throw new CommonException("创建文件夹失败！");
        }
        String fileIdPre = DateUtil.date().toString("yyyyMMdd");
        try {
            //File unzipFile = ZipUtil.unzip(fileTemp, new File(outFile), CharsetUtil.CHARSET_GBK);
            UnRarZip7Z.unRarZip7Z(fileTemp.getAbsolutePath(),outFile);
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
            if(isParentFile){ this.saveOrUpdate(devFile);}
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
}
