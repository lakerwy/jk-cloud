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
import vip.xiaonuo.biz.modular.cmb.service.CmbApiService;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;

/**
 * 招商银行回调API
 * @author pzh
 * @date 2024/02/08 09:10
 */
@Api(tags = "招商银行回调API控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class CmbCallbackApiController {

    @Resource
    private CmbApiService cmbApiService;

    /**
     * @author pzh
     * @date 2024/02/08 09:10
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("通用回调接口")
    @PostMapping("/openApi/biz/cmb/callback")
    public CommonResult<String> callback(@RequestBody String receiveEncryptMsg) {
        cmbApiService.callback(receiveEncryptMsg);
        return CommonResult.ok();
    }
}
