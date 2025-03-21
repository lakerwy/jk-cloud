package vip.xiaonuo.biz.modular.prj.exp.controller;

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
import vip.xiaonuo.biz.modular.prj.exp.param.*;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormFeeTotal;
import vip.xiaonuo.biz.modular.prj.exp.vo.ExpMonthTotal;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormFee;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormFeeService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 费用报销（二期）- 费用报销单控制器
 *
 * @author yong.ye
 * @date  2025/02/24 15:14
 */
@Api(tags = "费用报销（二期）- 费用报销单控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizFormFeeController {

    @Resource
    private BizFormFeeService bizFormFeeService;

    /**
     * 获取费用报销（二期）- 费用报销单分页
     *
     * @author yong.ye
     * @date  2025/02/24 15:14
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取费用报销（二期）- 费用报销单分页")
    @SaCheckPermission("/biz/prj/exp/fee/page")
    @GetMapping("/biz/prj/exp/fee/page")
    public CommonResult<Page<BizFormFee>> page(BizFormFeePageParam bizFormFeePageParam) {
        return CommonResult.data(bizFormFeeService.page(bizFormFeePageParam));
    }

    /**
     * 添加费用报销（二期）- 费用报销单
     *
     * @author yong.ye
     * @date  2025/02/24 15:14
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加费用报销（二期）- 费用报销单")
    @CommonLog("添加费用报销（二期）- 费用报销单")
//    @SaCheckPermission("/biz/prj/exp/fee/add")
    @PostMapping("/biz/prj/exp/fee/add")
    public CommonResult<String> add(@RequestBody @Valid BizFormFeeAddParam bizFormFeeAddParam) {
        bizFormFeeService.add(bizFormFeeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑费用报销（二期）- 费用报销单
     *
     * @author yong.ye
     * @date  2025/02/24 15:14
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑费用报销（二期）- 费用报销单")
    @CommonLog("编辑费用报销（二期）- 费用报销单")
//    @SaCheckPermission("/biz/prj/exp/fee/edit")
    @PostMapping("/biz/prj/exp/fee/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizFormFeeEditParam bizFormFeeEditParam) {
        bizFormFeeService.edit(bizFormFeeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除费用报销（二期）- 费用报销单
     *
     * @author yong.ye
     * @date  2025/02/24 15:14
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除费用报销（二期）- 费用报销单")
    @CommonLog("删除费用报销（二期）- 费用报销单")
//    @SaCheckPermission("/biz/prj/exp/fee/delete")
    @PostMapping("/biz/prj/exp/fee/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizFormFeeIdParam> bizFormFeeIdParamList) {
        bizFormFeeService.delete(bizFormFeeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取费用报销（二期）- 费用报销单详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:14
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取费用报销（二期）- 费用报销单详情")
//    @SaCheckPermission("/biz/prj/exp/fee/detail")
    @GetMapping("/biz/prj/exp/fee/detail")
    public CommonResult<BizFormFee> detail(@Valid BizFormFeeIdParam bizFormFeeIdParam) {
        return CommonResult.data(bizFormFeeService.detail(bizFormFeeIdParam));
    }




    /**
     * 获取费用报销（二期）- 费用报销单 - 列表统计（所有满足条件的数据统计）
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    @ApiOperationSupport(order = 66)
    @ApiOperation("获取费用报销（二期）- 费用报销单 - 列表统计（所有满足条件的数据统计）")
    @SaCheckPermission("/biz/prj/exp/fee/pageTotal")
    @GetMapping("/biz/prj/exp/fee/pageTotal")
    public CommonResult<BizFormFeeTotal> pageTotal(BizFormFeePageParam bizFormFeePageParam) {
        return CommonResult.data(bizFormFeeService.pageTotal(bizFormFeePageParam));
    }



    /**
     *  报销统计
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    @ApiOperationSupport(order = 88)
    @ApiOperation("报销统计")
    @SaCheckPermission("/biz/prj/exp/monthTotal")
    @GetMapping("/biz/prj/exp/monthTotal")
    public CommonResult<List<ExpMonthTotal>> monthTotal(@Valid ExpMonthParam expMonthParam) {
        return CommonResult.data(bizFormFeeService.monthTotal(expMonthParam));
    }



    /**
     *  费用报销|差旅费用报销 - 发起校验 - 通用
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    @ApiOperationSupport(order = 100)
    @ApiOperation("费用报销|差旅费用报销 - 发起校验 - 通用")
//    @SaCheckPermission("/biz/prj/exp/check")
    @GetMapping("/biz/prj/exp/check")
    public CommonResult<String> check(ExpCheckParam checkParam) {
        bizFormFeeService.check(checkParam);
        return CommonResult.ok();
    }
}
