package vip.xiaonuo.pan.modular.office.controllers;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.prop.CommonProperties;
import vip.xiaonuo.pan.modular.file.entity.PanFile;
import vip.xiaonuo.pan.modular.file.param.PreviewOfficeFileParam;
import vip.xiaonuo.pan.modular.file.service.PanFileService;
import vip.xiaonuo.pan.modular.fileCenter.component.FileDealComp;
import vip.xiaonuo.pan.modular.fileCenter.factory.FileFactory;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.domain.DownloadFile;
import vip.xiaonuo.pan.modular.office.documentserver.managers.history.HistoryManager;
import vip.xiaonuo.pan.modular.office.documentserver.models.enums.Action;
import vip.xiaonuo.pan.modular.office.documentserver.models.enums.Type;
import vip.xiaonuo.pan.modular.office.documentserver.models.filemodel.FileModel;
import vip.xiaonuo.pan.modular.office.entities.User;
import vip.xiaonuo.pan.modular.office.services.configurers.FileConfigurer;
import vip.xiaonuo.pan.modular.office.services.configurers.wrappers.DefaultFileWrapper;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;
import vip.xiaonuo.sys.api.SysUserApi;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

@Api(tags = "网盘文件管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class OfficeController {
    @Resource
    FileFactory fileFactory;
    @Resource
    FileDealComp fileDealComp;

    @Autowired
    private FileConfigurer<DefaultFileWrapper> fileConfigurer;

    @Resource
    PanFileService panFileService;
    @Resource
    SysUserApi sysUserApi;
    @Resource
    UserFileService userFileService;
    @Autowired
    private HistoryManager historyManager;

    @Value("${files.docservice.url.site}")
    private String docServiceSite;

    @Value("${files.docservice.url.api}")
    private String docServiceApiUrl;

    @Resource
    private CommonProperties commonProperties;

    @ApiOperationSupport(order = 1)
    @ApiOperation("预览office文件)")
//    @SaCheckPermission("/pan/commonFile/commonFileByUser")
    @PostMapping("/pan/file/previewOfficeFile")
    public CommonResult<Object> previewOfficeFile(HttpServletRequest request, @RequestBody PreviewOfficeFileParam previewOfficeFileParam) {
        CommonResult<Object> result = new CommonResult<>();
        try {
            String token = request.getHeader("Token");
            String previewUrl = commonProperties.getBackendUrl()+"/api/webapp/pan/file/preview?userFileId="+previewOfficeFileParam.getUserFileId()+"&isMin=false&shareBatchNum=undefined&extractionCode=undefined";
            //String previewUrl = request.getScheme() + "://" + deploymentHost + ":" + port + "/filetransfer/preview?userFileId=" + previewOfficeFileDTO.getUserFileId() + "&isMin=false&shareBatchNum=undefined&extractionCode=undefined&token=" + token;
            UserFile userFile = userFileService.getById(previewOfficeFileParam.getUserFileId());

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
        try {
            String id = StpLoginUserUtil.getLoginUser().getId();

        }catch (Exception e) {
            throw new CommonException(e.getMessage());
        }

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

}