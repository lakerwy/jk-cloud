package vip.xiaonuo.pan.modular.file.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.util.FileUtils;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.dev.api.DevConfigApi;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.pan.modular.file.enums.DevFileEnum;
import vip.xiaonuo.pan.modular.file.param.MultipartFileParam;
import vip.xiaonuo.pan.modular.file.service.StorageService;
import vip.xiaonuo.pan.modular.file.utils.Constants;
import vip.xiaonuo.pan.modular.file.vo.FileCheckVo;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static vip.xiaonuo.pan.core.FileCommonConstant.SNOWY_SYS_DEFAULT_FILE_ENGINE_KEY;


/**
 * @author mayike
 * @version 1.0
 * @date 2023/8/25 15:25
 */
@Api(tags = "云盘文件控制器")
@ApiSupport(author = "jk_team", order = 10)
@RestController
@Validated
@Slf4j
public class FileController {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private StorageService storageService;

    @Resource
    private DevConfigApi devConfigApi;

    @Resource
    private UserFileService userFileService;

    /**
     * 秒传判断，断点判断
     *
     * @return
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("秒传判断，断点判断")
    @PostMapping(value = "/pan/file/checkFileMd5")
    public CommonResult<Object> checkFileMd5(@RequestBody String md5) {
        Object processingObj = commonCacheOperator.get(Constants.FILE_UPLOAD_STATUS, md5);
        if (ObjectUtil.isEmpty(processingObj)) {
            return CommonResult.data(Boolean.TRUE);
        }
        String processingStr = processingObj.toString();
        boolean processing = Boolean.parseBoolean(processingStr);
        String value = String.valueOf(commonCacheOperator.get(Constants.FILE_MD5_KEY + md5));
        if (processing) {
            return CommonResult.data(Boolean.FALSE);
        } else {
            File confFile = new File(value);
            byte[] completeList = new byte[0];
            try {
                completeList = FileUtils.readFileToByteArray(confFile);
            } catch (Exception e) {

            }
            List<String> missChunkList = new LinkedList<>();
            for (int i = 0; i < completeList.length; i++) {
                if (completeList[i] != Byte.MAX_VALUE) {
                    missChunkList.add(i + "");
                }
            }
            return CommonResult.data(missChunkList);
        }
    }

    @ApiOperationSupport(order = 1)
    @ApiOperation("秒传判断，断点判断")
    @PostMapping(value = "/pan/file/newCheckFileMd5")
    public CommonResult<FileCheckVo> newCheckFileMd5(@RequestBody String md5) {
        /**
         * 通过MD5判断文件是否已经上传
         * 1、如果不存在，就重新上传
         * 2、存在就找到文件存储的地址，
         *  2.1 如果文件地址存在 就表示为可以秒传
         *  2.2 如果部分存在就说明，就表示断点续传
         *
         */
        Object processingObj = commonCacheOperator.get(Constants.FILE_UPLOAD_STATUS, md5);
        if (ObjectUtil.isEmpty(processingObj)) {
            return CommonResult.data(new FileCheckVo(DevFileEnum.NO_HAVE.getLevel()));
        }
        boolean processing  = Boolean.parseBoolean(String.valueOf(processingObj));
        String pathValue = String.valueOf(commonCacheOperator.get(Constants.FILE_MD5_KEY + md5));
        if (processing) {
            return CommonResult.data(new FileCheckVo(DevFileEnum.NO_HAVE.getLevel(),pathValue));
        } else {
            return CommonResult.data(new FileCheckVo(DevFileEnum.NO_HAVE.getLevel(),storageService.getMissChunk(pathValue)));
        }
    }


    /**
     * 上传文件
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("秒传判断，断点判断")
    @PostMapping(value = "/pan/file/fileUpload")
    public CommonResult<String> fileUpload(MultipartFileParam param, HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            log.info("上传文件start。");
            try {
                // 方法1
                //storageService.uploadFileRandomAccessFile(param);
                // 方法2 这个更快点
                storageService.uploadFileByMappedByteBuffer(param);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("文件上传失败。{}", param.toString());
            }
            log.info("上传文件end。");
        }
        return CommonResult.ok();
    }

    /**
     * 动态上传文件返回id
     *
     * @author mayike
     * @date 2023/09/08
     **/
    @ApiOperationSupport(order = 2)
    @ApiOperation("动态上传文件返回文件信息")
    @CommonLog("动态上传文件返回文件信息")
    @PostMapping("/pan/file/newUploadDynamicReturnBean")
    public CommonResult<List<DevFile>> newUploadDynamicReturnBean(MultipartFileParam param) {
        return CommonResult.data(userFileService.uploadReturnBean(devConfigApi.getValueByKey(SNOWY_SYS_DEFAULT_FILE_ENGINE_KEY), param));
    }

}
