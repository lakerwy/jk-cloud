package vip.xiaonuo.biz.modular.bidFee.controller;

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
import vip.xiaonuo.biz.modular.bidFee.param.*;
import vip.xiaonuo.biz.modular.bidFee.vo.BizProjectVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.bidFee.entity.BidFee;
import vip.xiaonuo.biz.modular.bidFee.service.BidFeeService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 投标费用审批控制器
 *
 * @author pzh
 * @date  2024/12/26 16:37
 */
@Api(tags = "投标费用审批控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BidFeeController {

    @Resource
    private BidFeeService bidFeeService;

    /**
     * 获取投标费用审批分页
     *
     * @author pzh
     * @date  2024/12/26 16:37
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取投标费用审批分页")
//    @SaCheckPermission("/biz/bidFee/page")
    @GetMapping("/biz/bidFee/page")
    public CommonResult<Page<BidFee>> page(BidFeePageParam bidFeePageParam) {
        return CommonResult.data(bidFeeService.page(bidFeePageParam));
    }

    /**
     * 添加投标费用审批
     *
     * @author pzh
     * @date  2024/12/26 16:37
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加投标费用审批")
    @CommonLog("添加投标费用审批")
    // @SaCheckPermission("/biz/bidFee/add")
    @PostMapping("/biz/bidFee/add")
    public CommonResult<String> add(@RequestBody @Valid BidFeeAddParam bidFeeAddParam) {
        bidFeeService.add(bidFeeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑投标费用审批
     *
     * @author pzh
     * @date  2024/12/26 16:37
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑投标费用审批")
    @CommonLog("编辑投标费用审批")
    // @SaCheckPermission("/biz/bidFee/edit")
    @PostMapping("/biz/bidFee/edit")
    public CommonResult<String> edit(@RequestBody @Valid BidFeeEditParam bidFeeEditParam) {
        bidFeeService.edit(bidFeeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除投标费用审批
     *
     * @author pzh
     * @date  2024/12/26 16:37
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除投标费用审批")
    @CommonLog("删除投标费用审批")
    // @SaCheckPermission("/biz/bidFee/delete")
    @PostMapping("/biz/bidFee/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BidFeeIdParam> bidFeeIdParamList) {
        bidFeeService.delete(bidFeeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取投标费用审批详情
     *
     * @author pzh
     * @date  2024/12/26 16:37
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取投标费用审批详情")
    // @SaCheckPermission("/biz/bidFee/detail")
    @GetMapping("/biz/bidFee/detail")
    public CommonResult<BidFee> detail(@Valid BidFeeIdParam bidFeeIdParam) {
        return CommonResult.data(bidFeeService.detail(bidFeeIdParam));
    }

    /**
     * 获取合同关联立项列表
     *
     * @author pzh
     * @date  2024/12/26 16:37
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取合同关联立项列表")
    // @SaCheckPermission("/biz/bidFee/project/page")
    @GetMapping("/biz/bidFee/project/page")
    public CommonResult<Page<BizProjectVo>> projectPage(BizProjectPageParam bizProjectPageParam) {
        return CommonResult.data(bidFeeService.projectPage(bizProjectPageParam));
    }

    /**
     * 投标费用审批 - 生成打印Excel
     *
     * @author pzh
     * @date  2024/12/30 10:00
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("投标费用审批  - 生成打印Excel")
    @CommonLog("投标费用审批  - 生成打印Excel")
    @PostMapping("/biz/bidFee/genExcel")
    public void genExcel(@RequestBody @Valid ExcelParam excelParam, HttpServletResponse response) {
        bidFeeService.genExcel(excelParam,response);
    }
}
