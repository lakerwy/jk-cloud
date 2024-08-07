package vip.xiaonuo.iot.modular.group.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.iot.modular.group.entity.IotGroup;
import vip.xiaonuo.iot.modular.group.param.IotGroupAddParam;
import vip.xiaonuo.iot.modular.group.param.IotGroupEditParam;
import vip.xiaonuo.iot.modular.group.param.IotGroupIdParam;
import vip.xiaonuo.iot.modular.group.param.IotGroupPageParam;
import vip.xiaonuo.iot.modular.group.service.IotGroupService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 设备分组控制器
 *
 * @author mayike
 * @date  2024/08/06 08:55
 */
@Api(tags = "设备分组控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class IotGroupController {

    @Resource
    private IotGroupService iotGroupService;

    /**
     * 获取设备分组分页
     *
     * @author mayike
     * @date  2024/08/06 08:55
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取设备分组分页")
//    @SaCheckPermission("/iot/group/page")
    @GetMapping("/iot/group/page")
    public CommonResult<Page<IotGroup>> page(IotGroupPageParam iotGroupPageParam) {
        return CommonResult.data(iotGroupService.page(iotGroupPageParam));
    }

    /**
     * 添加设备分组
     *
     * @author mayike
     * @date  2024/08/06 08:55
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加设备分组")
    @CommonLog("添加设备分组")
//    @SaCheckPermission("/iot/group/add")
    @PostMapping("/iot/group/add")
    public CommonResult<String> add(@RequestBody @Valid IotGroupAddParam iotGroupAddParam) {
        iotGroupService.add(iotGroupAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑设备分组
     *
     * @author mayike
     * @date  2024/08/06 08:55
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑设备分组")
    @CommonLog("编辑设备分组")
//    @SaCheckPermission("/iot/group/edit")
    @PostMapping("/iot/group/edit")
    public CommonResult<String> edit(@RequestBody @Valid IotGroupEditParam iotGroupEditParam) {
        iotGroupService.edit(iotGroupEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除设备分组
     *
     * @author mayike
     * @date  2024/08/06 08:55
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除设备分组")
    @CommonLog("删除设备分组")
//    @SaCheckPermission("/iot/group/delete")
    @PostMapping("/iot/group/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<IotGroupIdParam> iotGroupIdParamList) {
        iotGroupService.delete(iotGroupIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取设备分组详情
     *
     * @author mayike
     * @date  2024/08/06 08:55
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取设备分组详情")
//    @SaCheckPermission("/iot/group/detail")
    @GetMapping("/iot/group/detail")
    public CommonResult<IotGroup> detail(@Valid IotGroupIdParam iotGroupIdParam) {
        return CommonResult.data(iotGroupService.detail(iotGroupIdParam));
    }
}
