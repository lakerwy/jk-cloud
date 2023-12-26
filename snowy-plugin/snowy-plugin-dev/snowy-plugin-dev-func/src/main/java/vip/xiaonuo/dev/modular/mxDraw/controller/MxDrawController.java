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
import vip.xiaonuo.dev.modular.mxDraw.param.CadToParam;
import vip.xiaonuo.dev.modular.mxDraw.param.ConvertData;
import vip.xiaonuo.dev.modular.mxDraw.service.MxDrawService;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 云图MxDraw
 */
@Api(tags = "【MxDraw】-cad云图")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class MxDrawController {

    @Resource
    private MxDrawService mxDrawService;

    /**
     * DWG文件转换编辑格式mxweb
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 1)
    @ApiOperation("DWG文件转换编辑格式mxweb")
    @CommonLog("DWG文件转换编辑格式mxweb")
    @PostMapping("/dev/mxDraw/convert")
    public CommonResult<String> convert(@RequestBody @Valid ConvertData convertData) {
        return CommonResult.data(mxDrawService.convert(convertData));
    }


    /**
     * DWG文件转成PDF
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 2)
    @ApiOperation("DWG文件转成PDF")
    @CommonLog("DWG文件转成PDF")
    @PostMapping("/dev/mxDraw/cadToPdf")
    public CommonResult<String> cadToPdf(@RequestBody @Valid CadToParam cadToPdfParam) {
        return CommonResult.data(mxDrawService.cadToPdf(cadToPdfParam));
    }

    /**
     * DWG文件转成JPG
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 3)
    @ApiOperation("DWG文件转成JPG")
    @CommonLog("DWG文件转成JPG")
    @PostMapping("/dev/mxDraw/cadToJpg")
    public CommonResult<String> cadToJpg(@RequestBody @Valid CadToParam cadToPdfParam) {
        return CommonResult.data(mxDrawService.cadToJpg(cadToPdfParam));
    }

    /**
     * DWG文件转换浏览格式wgh
     *
     * @author xuyuxiang
     * @date 2021/10/13 14:01
     **/
    @ApiOperationSupport(order = 3)
    @ApiOperation("DWG文件转换浏览格式wgh")
    @CommonLog("DWG文件转换浏览格式wgh")
    @PostMapping("/dev/mxDraw/convertWgh")
    public CommonResult<String> convertWgh(@RequestBody @Valid CadToParam cadToParam) {
        return CommonResult.data(mxDrawService.convertWgh(cadToParam));
    }
}
