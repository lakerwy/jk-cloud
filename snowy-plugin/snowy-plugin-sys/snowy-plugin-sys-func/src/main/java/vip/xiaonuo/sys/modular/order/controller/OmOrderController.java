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
import vip.xiaonuo.sys.modular.order.entity.OmOrder;
import vip.xiaonuo.sys.modular.order.param.OmOrderAddParam;
import vip.xiaonuo.sys.modular.order.param.OmOrderEditParam;
import vip.xiaonuo.sys.modular.order.param.OmOrderIdParam;
import vip.xiaonuo.sys.modular.order.param.OmOrderPageParam;
import vip.xiaonuo.sys.modular.order.service.OmOrderService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 运维工单控制器
 *
 * @author yong.ye
 * @date  2025/01/13 16:13
 */
@Api(tags = "运维工单控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class OmOrderController {

    @Resource
    private OmOrderService omOrderService;

    /**
     * 获取运维工单分页
     *
     * @author yong.ye
     * @date  2025/01/13 16:13
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取运维工单分页")
//    @SaCheckPermission("/sys/order/om/page")
    @GetMapping("/sys/order/om/page")
    public CommonResult<Page<OmOrder>> page(OmOrderPageParam omOrderPageParam) {
        return CommonResult.data(omOrderService.page(omOrderPageParam));
    }

    /**
     * 添加运维工单
     *
     * @author yong.ye
     * @date  2025/01/13 16:13
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加运维工单")
    @CommonLog("添加运维工单")
//    @SaCheckPermission("/sys/order/om/add")
    @PostMapping("/sys/order/om/add")
    public CommonResult<String> add(@RequestBody @Valid OmOrderAddParam omOrderAddParam) {
        omOrderService.add(omOrderAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑运维工单
     *
     * @author yong.ye
     * @date  2025/01/13 16:13
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑运维工单")
    @CommonLog("编辑运维工单")
//    @SaCheckPermission("/sys/order/om/edit")
    @PostMapping("/sys/order/om/edit")
    public CommonResult<String> edit(@RequestBody @Valid OmOrderEditParam omOrderEditParam) {
        omOrderService.edit(omOrderEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除运维工单
     *
     * @author yong.ye
     * @date  2025/01/13 16:13
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除运维工单")
    @CommonLog("删除运维工单")
//    @SaCheckPermission("/sys/order/om/delete")
    @PostMapping("/sys/order/om/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<OmOrderIdParam> omOrderIdParamList) {
        omOrderService.delete(omOrderIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取运维工单详情
     *
     * @author yong.ye
     * @date  2025/01/13 16:13
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取运维工单详情")
//    @SaCheckPermission("/sys/order/om/detail")
    @GetMapping("/sys/order/om/detail")
    public CommonResult<OmOrder> detail(@Valid OmOrderIdParam omOrderIdParam) {
        return CommonResult.data(omOrderService.detail(omOrderIdParam));
    }
}
