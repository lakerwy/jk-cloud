package vip.xiaonuo.sys.modular.dingtalk.controller;

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
import vip.xiaonuo.sys.modular.dingtalk.param.SyncAttendDataParam;
import vip.xiaonuo.sys.modular.dingtalk.param.SyncDeptParam;
import vip.xiaonuo.sys.modular.dingtalk.param.SyncUserParam;
import vip.xiaonuo.sys.modular.dingtalk.service.DingDingSyncService;

import javax.annotation.Resource;
import javax.validation.Valid;

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



    /**
     * 同步 - 钉钉 - 同步指定用户  -- 说明：只处理指定用户的同步（新增|更新）
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("同步 - 钉钉 - 同步指定用户  -- 说明：只处理指定用户的同步（新增|更新）")
    @CommonLog("同步 - 钉钉 - 同步指定用户  -- 说明：只处理指定用户的同步（新增|更新）")
    @PostMapping("/sys/dingTalk/syncUser")
    public CommonResult<String> syncUser(@RequestBody @Valid SyncUserParam syncUserParam) {
        dingDingSyncService.syncUser(syncUserParam);
        return CommonResult.ok();
    }



    /**
     * 同步 - 钉钉 - 同步指定部门  -- 说明：只处理指定部门的同步（新增|更新）
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("同步 - 钉钉 - 同步指定部门  -- 说明：只处理指定部门的同步（新增|更新）")
    @CommonLog("同步 - 钉钉 - 同步指定部门  -- 说明：只处理指定部门的同步（新增|更新）")
    @PostMapping("/sys/dingTalk/syncDept")
    public CommonResult<String> syncDept(@RequestBody @Valid SyncDeptParam syncDeptParam) {
        dingDingSyncService.syncDept(syncDeptParam);
        return CommonResult.ok();
    }

//    /**
//     * 同步 - 钉钉 - 考勤数据
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("同步 - 钉钉 - 考勤数据")
//    @CommonLog("同步 - 钉钉 - 考勤数据")
//    @PostMapping("/sys/dingTalk/syncAttendData")
//    public CommonResult<String> syncAttendData(@RequestBody @Valid SyncAttendDataParam syncAttendDataParam) {
//        dingDingSyncService.syncAttendData(syncAttendDataParam);
//        return CommonResult.ok();
//    }

    /**
     * 同步 - 钉钉 - 考勤数据
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("同步 - 钉钉 - 考勤数据（异步处理）")
    @CommonLog("同步 - 钉钉 - 考勤数据（异步处理）")
    @PostMapping("/sys/dingTalk/syncAttendDAsync")
    public CommonResult<String> syncAttendAsync(@RequestBody @Valid SyncAttendDataParam syncAttendDataParam) {
        dingDingSyncService.syncAttendAsync(syncAttendDataParam);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 4)
    @ApiOperation("同步 - 钉钉 - 考勤同步状态查询")
    @PostMapping("/sys/dingTalk/syncAttendStatus")
    public CommonResult<Boolean> syncStatus() {
        return CommonResult.data(dingDingSyncService.syncAttendStatus());
    }
}
