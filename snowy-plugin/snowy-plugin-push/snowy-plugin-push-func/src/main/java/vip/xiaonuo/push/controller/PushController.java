package vip.xiaonuo.push.controller;

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
import vip.xiaonuo.push.param.PushMessage;
import vip.xiaonuo.push.service.JPushService;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/7 14:10
 */

@Api(tags = "推送-消息推送控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PushController {


    @Resource
    JPushService jPushService;

    @ApiOperationSupport(order = 1)
    @ApiOperation("发送推送消息")
    @CommonLog("发送推送消息")
    @PostMapping("/push/sendMsg")
    public CommonResult<String> sendMsg(@RequestBody @Valid PushMessage pushMessage) {
        return CommonResult.ok(jPushService.sendMsg(pushMessage));
    }

}
