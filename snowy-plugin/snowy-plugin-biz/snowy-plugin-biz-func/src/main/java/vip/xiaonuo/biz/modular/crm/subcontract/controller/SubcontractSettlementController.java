package vip.xiaonuo.biz.modular.crm.subcontract.controller;

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
import vip.xiaonuo.biz.modular.crm.subcontract.param.*;
import vip.xiaonuo.biz.modular.crm.subcontract.vo.AllotmentHisVo;
import vip.xiaonuo.biz.modular.crm.subcontract.vo.AllotmentVo;
import vip.xiaonuo.biz.modular.crm.subcontract.vo.SubcontractSettlementItemVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.SubcontractSettlement;
import vip.xiaonuo.biz.modular.crm.subcontract.service.SubcontractSettlementService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 分包合同结算审批控制器
 *
 * @author pzh
 * @date  2025/04/30 11:19
 */
@Api(tags = "分包合同结算审批控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SubcontractSettlementController {

    @Resource
    private SubcontractSettlementService subcontractSettlementService;

    /**
     * 获取分包合同结算审批分页
     *
     * @author pzh
     * @date  2025/04/30 11:19
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取分包合同结算审批分页")
    // @SaCheckPermission("/biz/crm/subcontractSettlement/page")
    @GetMapping("/biz/subcontractSettlement/page")
    public CommonResult<Page<SubcontractSettlement>> page(SubcontractSettlementPageParam subcontractSettlementPageParam) {
        return CommonResult.data(subcontractSettlementService.page(subcontractSettlementPageParam));
    }


    /**
     * 添加分包合同结算审批
     *
     * @author pzh
     * @date  2025/04/30 11:19
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加分包合同结算审批")
    @CommonLog("添加分包合同结算审批")
    // @SaCheckPermission("/biz/subcontractSettlement/add")
    @PostMapping("/biz/subcontractSettlement/add")
    public CommonResult<String> add(@RequestBody @Valid SubcontractSettlementAddParam subcontractSettlementAddParam) {
        subcontractSettlementService.add(subcontractSettlementAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑分包合同结算审批
     *
     * @author pzh
     * @date  2025/04/30 11:19
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑分包合同结算审批")
    @CommonLog("编辑分包合同结算审批")
    // @SaCheckPermission("/biz/subcontractSettlement/edit")
    @PostMapping("/biz/subcontractSettlement/edit")
    public CommonResult<String> edit(@RequestBody @Valid SubcontractSettlementEditParam subcontractSettlementEditParam) {
        subcontractSettlementService.edit(subcontractSettlementEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除分包合同结算审批
     *
     * @author pzh
     * @date  2025/04/30 11:19
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除分包合同结算审批")
    @CommonLog("删除分包合同结算审批")
    // @SaCheckPermission("/biz/subcontractSettlement/delete")
    @PostMapping("/biz/subcontractSettlement/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SubcontractSettlementIdParam> subcontractSettlementIdParamList) {
        subcontractSettlementService.delete(subcontractSettlementIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取分包合同结算审批详情
     *
     * @author pzh
     * @date  2025/04/30 11:19
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取分包合同结算审批详情")
    // @SaCheckPermission("/biz/subcontractSettlement/detail")
    @GetMapping("/biz/subcontractSettlement/detail")
    public CommonResult<SubcontractSettlement> detail(@Valid SubcontractSettlementIdParam subcontractSettlementIdParam) {
        return CommonResult.data(subcontractSettlementService.detail(subcontractSettlementIdParam));
    }

    /**
     * 分包合同结算 - 校验
     *
     * @author pzh
     * @date  2025/04/30 11:19
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("分包合同结算 - 校验")
    @CommonLog("分包合同结算 - 校验")
    // @SaCheckPermission("/biz/subcontractSettlement/check")
    @PostMapping("/biz/subcontractSettlement/check")
    public CommonResult<String> check(@RequestBody @Valid AllotmentHtIdParam param) {
        subcontractSettlementService.check(param);
        return CommonResult.ok();
    }


    /**
     * 获取项目前期拨付情况
     *
     * @author pzh
     * @date  2025/05/06 09:00
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取项目前期拨付情况")
    // @SaCheckPermission("/biz/subcontractSettlement/allotmentHis")
    @GetMapping("/biz/subcontractSettlement/allotmentHis")
    public CommonResult<List<AllotmentHisVo>> allotmentHis(@Valid AllotmentHtIdParam param) {
        return CommonResult.data(subcontractSettlementService.allotmentHis(param));
    }


    /**
     * 获取未结算分包合同列表
     *
     * @author pzh
     * @date  2025/05/07 14:18
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("获取未结算分包合同列表")
    // @SaCheckPermission("/biz/subcontractSettlement/notSettlementList")
    @GetMapping("/biz/subcontractSettlement/notSettlementList")
    public CommonResult<List<SubcontractSettlementItemVo>> notSettlementList(AllotmentHtIdParam param) {
        return CommonResult.data(subcontractSettlementService.notSettlementList(param));
    }


    /**
     * 获取分包拨付情况列表
     *
     * @author pzh
     * @date  2025/05/07 09:00
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("获取分包拨付情况列表")
    // @SaCheckPermission("/biz/subcontractSettlement/allotmentList")
    @GetMapping("/biz/subcontractSettlement/allotmentList")
    public CommonResult<List<AllotmentVo>> list(AllotmentHtIdParam param) {
        return CommonResult.data(subcontractSettlementService.allotmentList(param));
    }

}
