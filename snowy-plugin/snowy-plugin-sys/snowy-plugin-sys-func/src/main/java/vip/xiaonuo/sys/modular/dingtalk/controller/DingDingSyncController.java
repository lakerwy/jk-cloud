package vip.xiaonuo.sys.modular.dingtalk.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.sys.modular.dingtalk.service.DingDingSyncService;

import javax.annotation.Resource;

/**
 * 钉钉数据同步 - 专属控制器
 */

@Api(tags = "钉钉数据同步控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 100)
@RestController
@Validated
public class DingDingSyncController {

    @Resource
    private DingDingSyncService dingDingSyncService;

    /**
     * 同步 - 钉钉 - 组织架构
     *
     * 说明：
     *    1. 处理组织架构同步
     *    2. 处理人员相关信息同步
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("同步 - 钉钉 - 组织架构")
    @CommonLog("同步 - 钉钉 - 组织架构")
    @PostMapping("/sys/dingTalk/sync")
    public CommonResult<String> sync() {
        dingDingSyncService.sync();
        return CommonResult.ok();
    }

}
