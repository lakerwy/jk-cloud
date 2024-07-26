package vip.xiaonuo.im.modular.openIm.controller;

import cn.hutool.json.JSONObject;
import cn.wildfirechat.pojos.OutputGetUserList;
import cn.wildfirechat.pojos.PojoGroupInfos;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.im.modular.chat.service.ImService;
import vip.xiaonuo.im.modular.openIm.service.OpenImService;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/20 16:25
 *
 *Im 即使通信控制期
 */
@Api(tags = "openIM-即时通信控制期")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class OpenImController {

    @Resource
    OpenImService openImService;

    @ApiOperationSupport(order =2)
    @ApiOperation("获取用户所有群组")
    @CommonLog("获取用户所有群组")
    @GetMapping("/openIm/test")
    public CommonResult<Void> test() {
        openImService.test();
        return CommonResult.ok();
    }
}
