package vip.xiaonuo.dev.modular.mxDraw.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.dev.modular.mxDraw.param.ConvertData;
import vip.xiaonuo.dev.modular.mxDraw.service.MxDrawService;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 云图MxDraw
 */
@Api(tags = "MxDraw-cad云图")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class MxDrawController {

    @Resource
    private MxDrawService mxDrawService;

    /**
     * 服务器dwg文件转换成mxweb文件
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 1)
    @ApiOperation("服务器dwg文件转换成mxweb文件")
    @CommonLog("服务器dwg文件转换成mxweb文件")
    @PostMapping("/dev/mxDraw/convert")
    public CommonResult<String> convert(@RequestBody @Valid ConvertData convertData) {
        return CommonResult.data(mxDrawService.convert(convertData));
    }
}
