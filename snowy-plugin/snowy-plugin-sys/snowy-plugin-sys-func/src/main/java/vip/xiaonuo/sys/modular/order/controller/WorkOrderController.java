package vip.xiaonuo.sys.modular.order.controller;

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
import vip.xiaonuo.sys.modular.order.entity.WorkOrder;
import vip.xiaonuo.sys.modular.order.param.WorkOrderAddParam;
import vip.xiaonuo.sys.modular.order.param.WorkOrderEditParam;
import vip.xiaonuo.sys.modular.order.param.WorkOrderIdParam;
import vip.xiaonuo.sys.modular.order.param.WorkOrderPageParam;
import vip.xiaonuo.sys.modular.order.service.WorkOrderService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 事件处理单控制器
 *
 * @author yong.ye
 * @date  2025/01/13 15:34
 */
@Api(tags = "事件处理单控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class WorkOrderController {

    @Resource
    private WorkOrderService workOrderService;

    /**
     * 获取事件处理单分页
     *
     * @author yong.ye
     * @date  2025/01/13 15:34
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取事件处理单分页")
//    @SaCheckPermission("/sys/order/page")
    @GetMapping("/sys/order/page")
    public CommonResult<Page<WorkOrder>> page(WorkOrderPageParam workOrderPageParam) {
        return CommonResult.data(workOrderService.page(workOrderPageParam));
    }

    /**
     * 添加事件处理单
     *
     * @author yong.ye
     * @date  2025/01/13 15:34
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加事件处理单")
    @CommonLog("添加事件处理单")
//    @SaCheckPermission("/sys/order/add")
    @PostMapping("/sys/order/add")
    public CommonResult<String> add(@RequestBody @Valid WorkOrderAddParam workOrderAddParam) {
        workOrderService.add(workOrderAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑事件处理单
     *
     * @author yong.ye
     * @date  2025/01/13 15:34
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑事件处理单")
    @CommonLog("编辑事件处理单")
//    @SaCheckPermission("/sys/order/edit")
    @PostMapping("/sys/order/edit")
    public CommonResult<String> edit(@RequestBody @Valid WorkOrderEditParam workOrderEditParam) {
        workOrderService.edit(workOrderEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除事件处理单
     *
     * @author yong.ye
     * @date  2025/01/13 15:34
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除事件处理单")
    @CommonLog("删除事件处理单")
//    @SaCheckPermission("/sys/order/delete")
    @PostMapping("/sys/order/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<WorkOrderIdParam> workOrderIdParamList) {
        workOrderService.delete(workOrderIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取事件处理单详情
     *
     * @author yong.ye
     * @date  2025/01/13 15:34
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取事件处理单详情")
//    @SaCheckPermission("/sys/order/detail")
    @GetMapping("/sys/order/detail")
    public CommonResult<WorkOrder> detail(@Valid WorkOrderIdParam workOrderIdParam) {
        return CommonResult.data(workOrderService.detail(workOrderIdParam));
    }
}
