package vip.xiaonuo.sys.modular.hr.magic.magicattendance.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicCallback;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicResult;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.param.MagicTokenParam;
import vip.xiaonuo.sys.modular.hr.magic.magicattendance.service.MagicService;

import javax.annotation.Resource;

/**
 * 魔点token
 *
 * @author ljy
 * @date  2023/10/18 10:28
 */
@Api(tags = "魔点token")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class MagicController {

    @Resource
    private MagicService magicService;


    @ApiOperationSupport(order = 1)
    @ApiOperation("获取token")
    @CommonLog("获取token")
    @GetMapping("/sys/magic/getToken")
    public CommonResult<String> getToken() {
        return CommonResult.data(magicService.getAppToken());
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation("打卡回调接口(免登录)")
    @CommonLog("打卡回调接口(免登录)")
    @PostMapping("/sys/magic/third/callback")
    public CommonResult<MagicResult> attendanceCallback(MagicCallback magicCallback) {
        return CommonResult.data(magicService.attendanceCallback(magicCallback));
    }

}
