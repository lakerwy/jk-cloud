package vip.xiaonuo.biz.modular.ledger.controller;

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
import vip.xiaonuo.biz.modular.ledger.param.*;
import vip.xiaonuo.biz.modular.ledger.vo.BizLedgerAllocationCountAmountVo;
import vip.xiaonuo.biz.modular.ledger.vo.BizLedgerAllocationRecentVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.ledger.entity.BizLedgerAllocation;
import vip.xiaonuo.biz.modular.ledger.service.BizLedgerAllocationService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 拨付台账控制器
 *
 * @author pzh
 * @date  2025/03/18 10:43
 */
@Api(tags = "拨付台账控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizLedgerAllocationController {

    @Resource
    private BizLedgerAllocationService bizLedgerAllocationService;

    /**
     * 获取拨付台账分页
     *
     * @author pzh
     * @date  2025/03/18 10:43
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取拨付台账分页")
    // @SaCheckPermission("/biz/ledger/allocation/page")
    @GetMapping("/biz/ledger/allocation/page")
    public CommonResult<Page<BizLedgerAllocation>> page(BizLedgerAllocationPageParam bizLedgerAllocationPageParam) {
        return CommonResult.data(bizLedgerAllocationService.page(bizLedgerAllocationPageParam));
    }

    /**
     * 添加拨付台账
     *
     * @author pzh
     * @date  2025/03/18 10:43
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加拨付台账")
    @CommonLog("添加拨付台账")
    // @SaCheckPermission("/biz/ledger/allocation/add")
    @PostMapping("/biz/ledger/allocation/add")
    public CommonResult<String> add(@RequestBody @Valid BizLedgerAllocationAddParam bizLedgerAllocationAddParam) {
        bizLedgerAllocationService.add(bizLedgerAllocationAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑拨付台账
     *
     * @author pzh
     * @date  2025/03/18 10:43
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑拨付台账")
    @CommonLog("编辑拨付台账")
    // @SaCheckPermission("/biz/ledger/allocation/edit")
    @PostMapping("/biz/ledger/allocation/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizLedgerAllocationEditParam bizLedgerAllocationEditParam) {
        bizLedgerAllocationService.edit(bizLedgerAllocationEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除拨付台账
     *
     * @author pzh
     * @date  2025/03/18 10:43
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除拨付台账")
    @CommonLog("删除拨付台账")
    // @SaCheckPermission("/biz/ledger/allocation/delete")
    @PostMapping("/biz/ledger/allocation/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizLedgerAllocationIdParam> bizLedgerAllocationIdParamList) {
        bizLedgerAllocationService.delete(bizLedgerAllocationIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取拨付台账详情
     *
     * @author pzh
     * @date  2025/03/18 10:43
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取拨付台账详情")
    // @SaCheckPermission("/biz/ledger/allocation/detail")
    @GetMapping("/biz/ledger/allocation/detail")
    public CommonResult<BizLedgerAllocation> detail(@Valid BizLedgerAllocationIdParam bizLedgerAllocationIdParam) {
        return CommonResult.data(bizLedgerAllocationService.detail(bizLedgerAllocationIdParam));
    }

    /**
     * 获取项目最近一次累计回款金额
     *
     * @author pzh
     * @date  2025/03/18 11:00
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取项目最近一次累计回款金额")
    // @SaCheckPermission("/biz/ledger/allocation/recent")
    @GetMapping("/biz/ledger/allocation/recent")
    public CommonResult<BizLedgerAllocationRecentVo> recent(@Valid BizLedgerAllocationRecentParam param) {
        return CommonResult.data(bizLedgerAllocationService.recent(param));
    }

    /**
     * 获取费用累计信息
     *
     * @author pzh
     * @date  2025/03/18 11:00
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取费用累计信息（传参同分页接口）")
    // @SaCheckPermission("/biz/ledger/allocation/countAmount")
    @GetMapping("/biz/ledger/allocation/countAmount")
    public CommonResult<BizLedgerAllocationCountAmountVo> countAmount(BizLedgerAllocationPageParam bizLedgerAllocationPageParam) {
        return CommonResult.data(bizLedgerAllocationService.countAmount(bizLedgerAllocationPageParam));
    }

    /**
     * 分包单位备案 - 导入
     *
     * @author pzh
     * @date  2025/03/31 10:30
     */
    @ApiOperationSupport(order = 20)
    @ApiOperation("拨付台账 - 导入")
    @CommonLog("拨付台账 - 导入")
    @PostMapping("/biz/ledger/allocation/importData")
    public CommonResult<String> importData(@RequestBody @Valid ImportLedgerAllocation input) {
        bizLedgerAllocationService.importData(input);
        return CommonResult.ok();
    }
}
