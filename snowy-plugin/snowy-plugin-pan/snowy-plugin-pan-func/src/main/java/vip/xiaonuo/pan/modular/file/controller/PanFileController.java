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
package vip.xiaonuo.pan.modular.file.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.prop.CommonProperties;
import vip.xiaonuo.dev.api.DevLogApi;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.dev.modular.file.param.DevFileIdParam;
import vip.xiaonuo.dev.modular.file.service.DevFileService;
import vip.xiaonuo.pan.modular.file.entity.PanFile;
import vip.xiaonuo.pan.modular.file.param.*;
import vip.xiaonuo.pan.modular.file.service.PanFileService;
import vip.xiaonuo.pan.modular.file.service.impl.FileTransferService;
import vip.xiaonuo.pan.modular.file.vo.UploadFileVo;
import vip.xiaonuo.pan.modular.fileCenter.component.FileDealComp;
import vip.xiaonuo.pan.modular.fileCenter.entity.PanFileStorage;
import vip.xiaonuo.pan.modular.fileCenter.factory.FileFactory;
import vip.xiaonuo.pan.modular.fileCenter.office.documentserver.models.enums.Action;
import vip.xiaonuo.pan.modular.fileCenter.office.documentserver.models.enums.Type;
import vip.xiaonuo.pan.modular.fileCenter.office.documentserver.models.filemodel.EditorConfig;
import vip.xiaonuo.pan.modular.fileCenter.office.documentserver.models.filemodel.FileModel;
import vip.xiaonuo.pan.modular.fileCenter.office.entities.User;
import vip.xiaonuo.pan.modular.fileCenter.office.services.configurers.FileConfigurer;
import vip.xiaonuo.pan.modular.fileCenter.office.services.configurers.wrappers.DefaultFileWrapper;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.Downloader;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.domain.DownloadFile;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.domain.Range;
import vip.xiaonuo.pan.modular.fileCenter.param.UnzipFileParam;
import vip.xiaonuo.pan.modular.fileCenter.param.ZipFileParam;
import vip.xiaonuo.pan.modular.fileCenter.service.PanFileStorageService;
import vip.xiaonuo.pan.modular.fileCenter.util.CommonFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;
import vip.xiaonuo.pan.modular.fileCenter.util.MimeUtils;
import vip.xiaonuo.pan.modular.fileRight.constant.FileRight;
import vip.xiaonuo.pan.modular.fileRight.enums.FileActionEnum;
import vip.xiaonuo.pan.modular.fileRight.service.PanFileRightService;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文件控制器
 *
 * @author mayike
 * @date  2023/09/06 21:15
 */
@Api(tags = "网盘文件管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
@Slf4j
public class PanFileController {

    @Resource
    private PanFileService panFileService;

    @Resource
    private PanFileStorageService panFileStorageService;

    @Resource
    FileTransferService fileTransferService;

    @Resource
    UserFileService userFileService;

    @Resource
    FileFactory fileFactory;

    @Resource
    private CommonProperties commonProperties;

    @Resource
    private FileConfigurer<DefaultFileWrapper> fileConfigurer;

    @Resource
    FileDealComp fileDealComp;

    @Value("${files.docservice.url.site}")
    private String docServiceSite;

    @Value("${files.docservice.url.api}")
    private String docServiceApiUrl;

    @Resource
    private PanFileRightService panFileRightService;

    @Resource
    private DevFileService devFileService;

    @Resource
    private DevLogApi devLogApi;

    /**
     * 获取文件详情
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取文件详情")
//    @SaCheckPermission("/pan/file/detail")
    @GetMapping("/pan/file/detail")
    public CommonResult<PanFile> detail(@Valid PanFileIdParam panFileIdParam) {
        return CommonResult.data(panFileService.detail(panFileIdParam));
    }


    @ApiOperationSupport(order = 6)
    @ApiOperation("解压文件")
    @CommonLog("解压文件")
//    @SaCheckPermission("/pan/fold/delete")
    @PostMapping("/pan/file/unzipFile")
    public CommonResult<String> unzipFile(@Valid @RequestBody UnzipFileParam userFileAddParam) {
        panFileService.unzipFile(userFileAddParam);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 7)
    @ApiOperation("极速上传")
//    @SaCheckPermission("/pan/file/uploadFile")
    @GetMapping("/pan/file/uploadFile")
    public CommonResult<UploadFileVo> uploadFileSpeed(UploadFileParam uploadFileParam) {
//        Boolean isHomonym = userFileService.checkHomonym(uploadFileParam);
//        if(!isHomonym){
//            return CommonResult.error("用重名文件，重命名后再上传");
//        }
        Integer foldType = 0;
        if(StrUtil.isNotEmpty(uploadFileParam.getFilePath())){
            UserFile userFile = userFileService.getParentIdByPath(uploadFileParam.getFilePath());
            if(ObjectUtil.isNotEmpty(userFile)){
                uploadFileParam.setParentId(userFile.getId());
                foldType =userFile.getFoldType();
                uploadFileParam.setFoldType(foldType);
            }
        }
        //校验权限
        panFileRightService.checkFileRight(Arrays.asList(uploadFileParam.getParentId()), FileRight.UPLOAD,uploadFileParam.getRightType());

        boolean isCheckSuccess = panFileStorageService.checkStorage(uploadFileParam.getTotalSize(),uploadFileParam.getType());
        if (!isCheckSuccess) {
            return CommonResult.error("存储空间不足");
        }
        UploadFileVo uploadFileVo = fileTransferService.uploadFileSpeed(uploadFileParam);

        //记录文件操作日志
        devLogApi.executeFileActionLog(StpLoginUserUtil.getLoginUser().getName(), FileActionEnum.UPLOAD.getValue(),uploadFileParam.getFilename(), Arrays.asList(uploadFileParam.getParentId()));

        return CommonResult.data(uploadFileVo);
    }

    @ApiOperationSupport(order = 8)
    @ApiOperation("上传文件")
//    @SaCheckPermission("/pan/file/uploadFile")
    @PostMapping("/pan/file/uploadFile")
    public CommonResult<String> uploadFile(HttpServletRequest request, UploadFileParam uploadFileParam){

        UserFile parentUserFile = userFileService.getParentIdByPath(uploadFileParam.getFilePath());
        //校验权限
        panFileRightService.checkFileRight(Collections.singletonList(parentUserFile.getId()),FileRight.UPLOAD,uploadFileParam.getRightType());

        fileTransferService.uploadFile(request,uploadFileParam);

        //记录文件操作日志
        devLogApi.executeFileActionLog(StpLoginUserUtil.getLoginUser().getName(), FileActionEnum.UPLOAD.getValue(),uploadFileParam.getFilename(), Collections.singletonList(parentUserFile.getId()));

        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 9)
    @ApiOperation("下载文件(支持批量)")
//    @SaCheckPermission("/pan/file/uploadFile")
    @GetMapping("/pan/file/downloadFile")
    public void downloadFile(HttpServletResponse httpServletResponse,DownloadParam downloadParam){
        //todo
        // 按时不判断是否有下载权限

        httpServletResponse.setContentType("application/force-download");// 设置强制下载不打开
        if(StrUtil.isNotEmpty(downloadParam.getFileIds())){
//            ProjectContextHolder.put(downloadParam.getTenantId());
            List<String> collect = Arrays.stream(downloadParam.getFileIds().split(", ")).collect(Collectors.toList());

            //校验权限
//            panFileRightService.checkFileRight(collect,FileRight.DOWNLOAD,downloadParam.getRightType());

            if(collect.size() == 1){
                UserFile userFile = userFileService.getById(collect.get(0));
                String fileName = "";
                if (userFile.getIsDir() == 1) {
                    fileName = userFile.getFileName() + ".zip";
                } else {
                    fileName = userFile.getFileName() + "." + userFile.getExtendName();

                }
                fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

                httpServletResponse.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名

                fileTransferService.downloadFile(httpServletResponse, collect,downloadParam.getRightType());
            }else if(collect.size() >1){
                List<String> userFileIds = new ArrayList<>();
                for(String userFileId : collect) {
                    UserFile userFile = userFileService.getById(userFileId);
                    if (userFile.getIsDir() == 0) {
                        userFileIds.add(userFileId);
                    } else {
                        CommonFile commonFile = new CommonFile(userFile.getFilePath(), userFile.getFileName(), true);
                        List<UserFile> userFileList = userFileService.selectUserFileByLikeRightFilePath(commonFile.getPath(), userFile.getUserId());
                        List<String> userFileIds1 = userFileList.stream().map(UserFile::getId).collect(Collectors.toList());
                        userFileIds.add(userFile.getId());
                        userFileIds.addAll(userFileIds1);
                    }

                }
                UserFile userFile = userFileService.getById(collect.get(0));
                httpServletResponse.setContentType("application/force-download");// 设置强制下载不打开
                Date date = new Date();
                String fileName = String.valueOf(date.getTime());
                httpServletResponse.addHeader("Content-Disposition", "attachment;fileName=" + fileName + ".zip");// 设置文件名
                fileTransferService.downloadUserFileList(httpServletResponse, userFile.getFilePath(), fileName, userFileIds,downloadParam.getRightType());
            }
            //记录文件操作日志
//            devLogApi.executeFileActionLog(StpLoginUserUtil.getLoginUser().getName(), FileActionEnum.DOWNLOAD.getValue(),"", collect);
        }
    }

    @ApiOperationSupport(order = 11)
    @ApiOperation("预览文件")
//    @SaCheckPermission("/pan/file/uploadFile")
    @GetMapping("/pan/file/preview")
    public void preview(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,  PreviewParam previewParam) throws IOException {

        //记录文件操作日志
//        devLogApi.executeFileActionLog(StpLoginUserUtil.getLoginUser().getName(), FileActionEnum.PREVIEW.getValue(),"", CollUtil.newArrayList(previewParam.getUserFileId()));

        if (previewParam.getPlatform() != null && previewParam.getPlatform() == 2) {
            fileTransferService.previewPictureFile(httpServletResponse, previewParam);
            return ;
        }
        if(StrUtil.isEmpty(previewParam.getType())){
            //todo
            // 暂时不判断是否有下载权限
            //校验权限
            log.info("doc文件预览进入下载------------------------------------------------------------------------");
//            panFileRightService.checkFileRight(Collections.singletonList(previewParam.getUserFileId()),FileRight.PREVIEW,previewParam.getRightType());
            UserFile userFile = userFileService.getById(previewParam.getUserFileId());

            String fileName = userFile.getFileName() + "." + userFile.getExtendName();
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

            httpServletResponse.addHeader("Content-Disposition", "fileName=" + fileName);// 设置文件名
            String mime = MimeUtils.getMime(userFile.getExtendName());
            httpServletResponse.setHeader("Content-Type", mime);
            if (FileCenterUtil.isImageFile(userFile.getExtendName())) {
                httpServletResponse.setHeader("cache-control", "public");
            }

            PanFile fileBean = panFileService.getById(userFile.getFileId());
            if (FileCenterUtil.isVideoFile(userFile.getExtendName()) || "mp3".equalsIgnoreCase(userFile.getExtendName()) || "flac".equalsIgnoreCase(userFile.getExtendName())) {
                //获取从那个字节开始读取文件
                String rangeString = httpServletRequest.getHeader("Range");
                int start = 0;
                if (StringUtils.isNotBlank(rangeString)) {
                    start = Integer.parseInt(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
                }

                Downloader downloader = fileFactory.getDownloader(fileBean.getStorageType());
                DownloadFile downloadFile = new DownloadFile();
                downloadFile.setFileUrl(fileBean.getFileUrl());
                Range range = new Range();
                range.setStart(start);

                if (start + 1024 * 1024 >= fileBean.getFileSize().intValue()) {
                    range.setLength(fileBean.getFileSize().intValue() - start);
                } else {
                    range.setLength(1024 * 1024);
                }
                downloadFile.setRange(range);
                InputStream inputStream = downloader.getInputStream(downloadFile);

                OutputStream outputStream = httpServletResponse.getOutputStream();
                try {

                    //返回码需要为206，代表只处理了部分请求，响应了部分数据

                    httpServletResponse.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                    // 每次请求只返回1MB的视频流

                    httpServletResponse.setHeader("Accept-Ranges", "bytes");
                    //设置此次相应返回的数据范围
                    httpServletResponse.setHeader("Content-Range", "bytes " + start + "-" + (fileBean.getFileSize() - 1) + "/" + fileBean.getFileSize());
                    IOUtils.copy(inputStream, outputStream);


                } finally {
                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly(outputStream);
                    if (downloadFile.getOssClient() != null) {
                        downloadFile.getOssClient().shutdown();
                    }
                }

            } else {
                fileTransferService.previewFile(httpServletResponse, previewParam);
            }
        }else {
//            log.info("评论业务doc文件预览进入下载------------------------------------------------------------------------");
            DevFileIdParam devFileIdParam = new DevFileIdParam();
            devFileIdParam.setId(previewParam.getUserFileId());
            devFileService.download(devFileIdParam, httpServletResponse);
        }

    }

    @ApiOperationSupport(order = 12)
    @ApiOperation("获取存储信息")
//    @SaCheckPermission("/pan/file/getStorage")
    @GetMapping("/pan/file/getStorage")
    public CommonResult<PanFileStorage> getStorage(){
        Long storageSize = fileTransferService.selectStorageSizeByUserId();
        PanFileStorage panFileStorage = panFileStorageService.getOne(new LambdaQueryWrapper<PanFileStorage>().eq(PanFileStorage::getUserId, StpLoginUserUtil.getLoginUser().getId()));
        panFileStorage.setTotalStorageSize(panFileStorage.getTotalStorageSize()*1024*1024*1024);
        panFileStorage.setStorageSize(storageSize);
        return CommonResult.data(panFileStorage);
    }

    @ApiOperationSupport(order = 1)
    @ApiOperation("预览office文件)")
//    @SaCheckPermission("/pan/commonFile/commonFileByUser")
    @PostMapping("/pan/file/previewOfficeFile")
    public CommonResult<Object> previewOfficeFile(HttpServletRequest request, @RequestBody PreviewOfficeFileParam previewOfficeFileParam) {
        CommonResult<Object> result = new CommonResult<>();
        try {
            String backendUrl = commonProperties.getBackendUrl();
            String previewUrl = backendUrl+"/api/webapp/pan/file/preview?userFileId="+previewOfficeFileParam.getUserFileId()+"&isMin=false&shareBatchNum=undefined&extractionCode=undefined";
            UserFile userFile ;

            if(StrUtil.isNotEmpty(previewOfficeFileParam.getPreviewUrl())){
                String previewUrl1 = previewOfficeFileParam.getPreviewUrl();
                UrlBuilder urlBuilder = UrlBuilder.ofHttp(previewUrl1, Charset.defaultCharset());
                String id = (String) urlBuilder.getQuery().get("id");
                DevFile devFile = devFileService.getById(id);
                userFile = new UserFile();
                String newFileName = devFile.getName().substring(0,devFile.getName().lastIndexOf(StrUtil.DOT));
                userFile.setFileName(newFileName);
                userFile.setCreateTime(devFile.getCreateTime());
                userFile.setExtendName(devFile.getSuffix());
                userFile.setId(devFile.getId());
                previewUrl = backendUrl+"/api/webapp/pan/file/preview?userFileId="+userFile.getId()+"&isMin=false&shareBatchNum=undefined&extractionCode=undefined&type=1";
            }else {
                userFile = userFileService.getById(previewOfficeFileParam.getUserFileId()) ;
            }

            SaBaseLoginUser userBean = StpLoginUserUtil.getLoginUser();

            User user = new User();
            user.setId(userBean.getId());
            user.setName(userBean.getAccount());

            Action action = Action.view;
            Type type = Type.desktop;
            Locale locale = new Locale("zh");
            FileModel fileModel = fileConfigurer.getFileModel(
                    DefaultFileWrapper
                            .builder()
                            .userFile(userFile)
                            .type(type)
                            .lang(locale.toLanguageTag())
                            .action(action)
                            .user(user)
                            .actionData(previewUrl)
                            .build()
            );
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("file",fileModel);
//            jsonObject.put("fileHistory", historyManager.getHistory(fileModel.getDocument()));  // get file history and add it to the model
            jsonObject.put("docServiceApiUrl", docServiceSite + docServiceApiUrl);
            jsonObject.put("reportName",userFile.getFileName());
            result = CommonResult.data(jsonObject);

        } catch (Exception e) {
            result = CommonResult.error(e.getMessage());
        }
        return result;
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation("编辑office文件")
    @PostMapping(value = "/pan/file/editOfficeFile")
    public CommonResult<JSONObject> editOfficeFile(HttpServletRequest request, @RequestBody PreviewOfficeFileParam previewOfficeFileParam) {

        //记录文件操作日志
//        devLogApi.executeFileActionLog(StpLoginUserUtil.getLoginUser().getName(), FileActionEnum.EDIT.getValue(),"", CollUtil.newArrayList(previewOfficeFileParam.getUserFileId()));

        CommonResult<JSONObject> result;
        //String token = request.getHeader("Token");
        String previewUrl = commonProperties.getBackendUrl()+"/api/webapp/pan/file/preview?userFileId="+previewOfficeFileParam.getUserFileId()+"&isMin=false&shareBatchNum=undefined&extractionCode=undefined";
        try {
            UserFile userFile = userFileService.getById(previewOfficeFileParam.getUserFileId());

            SaBaseLoginUser userBean = StpLoginUserUtil.getLoginUser();
            User user = new User();
            user.setId(userBean.getId());
            user.setName(userBean.getAccount());

            Action action = Action.edit;
            Type type = Type.desktop;
            Locale locale = new Locale("zh");
            FileModel fileModel = fileConfigurer.getFileModel(
                    DefaultFileWrapper
                            .builder()
                            .userFile(userFile)
                            .type(type)
                            .lang(locale.toLanguageTag())
                            .action(action)
                            .user(user)
                            .actionData(previewUrl)
                            .build()
            );
            EditorConfig editorConfig = fileModel.getEditorConfig();
//            editorConfig.setCallbackUrl(null);
            fileModel.setEditorConfig(editorConfig);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("file",fileModel);
            jsonObject.put("docServiceApiUrl", docServiceSite + docServiceApiUrl);
            jsonObject.put("reportName",userFile.getFileName());
            result = CommonResult.data(jsonObject);
        } catch (Exception e) {
            result = CommonResult.error();
        }
        return result;
    }


    @ApiOperationSupport(order = 2)
    @ApiOperation("office文件处理")
    @PostMapping(value = "/pan/file/IndexServlet")
    public void IndexServlet(HttpServletResponse response, HttpServletRequest request) throws IOException {
//        String token = request.getParameter("token");
//        String userId = userService.getUserIdByToken(token);
//        try {
//            String id = StpLoginUserUtil.getLoginUser().getId();
//
//        }catch (Exception e) {
//            throw new CommonException(e.getMessage());
//        }

        PrintWriter writer = response.getWriter();
        Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
        String body = scanner.hasNext() ? scanner.next() : "";

        JSONObject jsonObj = JSON.parseObject(body);
        //log.info("===saveeditedfile:" + jsonObj.get("status")); ;
        String status = jsonObj != null ? jsonObj.get("status").toString() : "";
        if ("2".equals(status) || "6".equals(status)) {
            String type = request.getParameter("type");
            String downloadUri = (String) jsonObj.get("url");

            if("edit".equals(type)){ //修改报告
                String userFileId = request.getParameter("userFileId");
                UserFile userFile = userFileService.getById(userFileId);
                PanFile fileBean = panFileService.getById(userFile.getFileId());
                Long pointCount = userFileService.getFilePointCount(userFile.getFileId());
                String fileUrl = fileBean.getFileUrl();
                if (pointCount > 1) {
                    fileUrl = fileDealComp.copyFile(fileBean, userFile);
                }

                URL url = new URL(downloadUri);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                try {
                    InputStream stream = connection.getInputStream();
                    fileDealComp.saveFileInputStream(fileBean.getStorageType(), fileUrl, stream);

                } catch (Exception e) {
//                    log.error(e.getMessage());
                } finally {

                    int fileLength = connection.getContentLength();
//                    log.info("当前修改文件大小为：" + (long) fileLength);

                    DownloadFile downloadFile = new DownloadFile();
                    downloadFile.setFileUrl(fileBean.getFileUrl());
                    InputStream inputStream = fileFactory.getDownloader(fileBean.getStorageType()).getInputStream(downloadFile);
                    String md5Str = DigestUtils.md5Hex(inputStream);

                    panFileService.updateFileDetail(userFile.getId(), md5Str, fileLength);
                    connection.disconnect();
                }
            }
        }

        if("3".equals(status)||"7".equals(status)) {//不强制手动保存时为6,"6".equals(status)
            // log.debug("====保存失败:");
            writer.write("{\"error\":1}");
        }else {
            //log.debug("状态为：0") ;
            writer.write("{\"error\":" + "0" + "}");

        }
    }

    @ApiOperationSupport(order = 20)
    @ApiOperation("在线压缩文件")
    @CommonLog("在线压缩文件")
    @GetMapping("/pan/file/zipFile")
    public CommonResult<String> zipFile(@Valid ZipFileParam zipFileParam) throws IOException, InterruptedException{
        panFileService.zipFile(zipFileParam);
        return CommonResult.ok();
    }
}
