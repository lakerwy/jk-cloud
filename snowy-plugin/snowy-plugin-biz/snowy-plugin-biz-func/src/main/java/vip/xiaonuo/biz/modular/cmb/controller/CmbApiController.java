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
    @ApiOperation("通用请求接口")
    @PostMapping("/biz/cmb/api")
    public CommonResult<JSONObject> api(@RequestBody CmbApiParam param) {
        return CommonResult.data(cmbApiService.sendRequest(param));
    }
}
