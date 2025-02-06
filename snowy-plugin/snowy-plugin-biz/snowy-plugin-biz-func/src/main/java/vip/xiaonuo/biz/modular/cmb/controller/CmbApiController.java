package vip.xiaonuo.biz.modular.cmb.controller;

import cn.hutool.json.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.cmb.param.*;
import vip.xiaonuo.biz.modular.cmb.service.CmbApiService;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;

/**
 * 招商银行API
 * @author pzh
 * @date 2024/02/06 10:30
 */
@Api(tags = "招商银行API控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class CmbApiController {

    @Resource
    private CmbApiService cmbApiService;

    /**
     * @author pzh
     * @date 2024/02/06 10:30
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("账务查询：1.可经办业务模式查询DCLISMOD")
    @PostMapping("/biz/cmb/dclismod")
    public CommonResult<JSONObject> dclismod(@RequestBody CmbDclismodParam param) {
        return CommonResult.data(cmbApiService.dclismod(param));
    }

    /**
     * @author pzh
     * @date 2024/02/06 14:10
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("账务查询：2.查询可经办的账户列表DCLISACC")
    @PostMapping("/biz/cmb/dclisacc")
    public CommonResult<JSONObject> dclisacc(@RequestBody CmbDclisaccParam param) {
        return CommonResult.data(cmbApiService.dclisacc(param));
    }

    /**
     * @author pzh
     * @date 2024/02/06 14:10
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("账务查询：3.账户详细信息查询NTQACINF")
    @PostMapping("/biz/cmb/ntqacinf")
    public CommonResult<JSONObject> ntqacinf(@RequestBody CmbNtqacinfParam param) {
        return CommonResult.data(cmbApiService.ntqacinf(param));
    }

    /**
     * @author pzh
     * @date 2024/02/06 16:10
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("账务查询：4.查询账户历史余额NTQABINF")
    @PostMapping("/biz/cmb/ntqabinf")
    public CommonResult<JSONObject> ntqabinf(@RequestBody CmbNtqabinfParam param) {
        return CommonResult.data(cmbApiService.ntqabinf(param));
    }

    /**
     * @author pzh
     * @date 2024/02/06 16:10
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("账务查询：5.查询分行号信息NTACCBBK")
    @PostMapping("/biz/cmb/ntaccbbk")
    public CommonResult<JSONObject> ntaccbbk(@RequestBody CmbNtaccbbkParam param) {
        return CommonResult.data(cmbApiService.ntaccbbk(param));
    }

    /**
     * @author pzh
     * @date 2024/02/06 16:10
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("账务查询：6.批量查询余额NTQADINF")
    @PostMapping("/biz/cmb/ntqadinf")
    public CommonResult<JSONObject> ntqadinf(@RequestBody CmbNtqadinfParam param) {
        return CommonResult.data(cmbApiService.ntqadinf(param));
    }

    /**
     * @author pzh
     * @date 2024/02/06 16:10
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("账务查询：7.账户交易信息查询trsQryByBreakPoint")
    @PostMapping("/biz/cmb/trsQryByBreakPoint")
    public CommonResult<JSONObject> trsQryByBreakPoint(@RequestBody CmbTrsQryByBreakPointParam param) {
        return CommonResult.data(cmbApiService.trsQryByBreakPoint(param));
    }
}
