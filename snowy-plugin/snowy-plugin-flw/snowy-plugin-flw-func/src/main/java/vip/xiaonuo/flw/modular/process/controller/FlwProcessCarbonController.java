package vip.xiaonuo.flw.modular.process.controller;

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
import vip.xiaonuo.flw.modular.process.entity.FlyCarbonCopy;
import vip.xiaonuo.flw.modular.process.service.FlwProcessCarbonCopyService;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 流程抄送控制器
 * @author hxw
 * @date  2024/04/23 16:05
 */
@Api(tags = "流程抄送控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class FlwProcessCarbonController {
    @Resource
    private FlwProcessCarbonCopyService flwProcessCarbonCopyService;

    /**
     * 流程抄送
     * @author hxw
     * @date  2024/04/23 16:05
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("流程抄送")
    @CommonLog("流程抄送")
//    @SaCheckPermission("/flw/carboncopy/add")
    @PostMapping("/flw/carboncopy/add")
    public CommonResult<String> add(@RequestBody @Valid FlyCarbonCopy flyCarbonCopy) {
        flwProcessCarbonCopyService.getCarbonCopy(flyCarbonCopy);
        return CommonResult.ok();
    }
}
