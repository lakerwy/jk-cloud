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
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormCashTotal;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormCashVo;
import vip.xiaonuo.biz.modular.prj.exp.vo.LjNoBackVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormCash;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormCashService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 费用报销（二期）- 借支单控制器
 *
 * @author yong.ye
 * @date  2025/02/24 15:12
 */
@Api(tags = "费用报销（二期）- 借支单控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizFormCashController {

    @Resource
    private BizFormCashService bizFormCashService;

    /**
     * 获取费用报销（二期）- 借支单分页
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取费用报销（二期）- 借支单分页")
    @SaCheckPermission("/biz/prj/exp/cash/page")
    @GetMapping("/biz/prj/exp/cash/page")
    public CommonResult<Page<BizFormCashVo>> page(BizFormCashPageParam bizFormCashPageParam) {
        return CommonResult.data(bizFormCashService.page(bizFormCashPageParam));
    }

    /**
     * 添加费用报销（二期）- 借支单
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加费用报销（二期）- 借支单")
    @CommonLog("添加费用报销（二期）- 借支单")
//    @SaCheckPermission("/biz/prj/exp/cash/add")
    @PostMapping("/biz/prj/exp/cash/add")
    public CommonResult<String> add(@RequestBody @Valid BizFormCashAddParam bizFormCashAddParam) {
        bizFormCashService.add(bizFormCashAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑费用报销（二期）- 借支单
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑费用报销（二期）- 借支单")
    @CommonLog("编辑费用报销（二期）- 借支单")
//    @SaCheckPermission("/biz/prj/exp/cash/edit")
    @PostMapping("/biz/prj/exp/cash/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizFormCashEditParam bizFormCashEditParam) {
        bizFormCashService.edit(bizFormCashEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除费用报销（二期）- 借支单
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除费用报销（二期）- 借支单")
    @CommonLog("删除费用报销（二期）- 借支单")
//    @SaCheckPermission("/biz/prj/exp/cash/delete")
    @PostMapping("/biz/prj/exp/cash/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizFormCashIdParam> bizFormCashIdParamList) {
        bizFormCashService.delete(bizFormCashIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取费用报销（二期）- 借支单详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取费用报销（二期）- 借支单详情")
//    @SaCheckPermission("/biz/prj/exp/cash/detail")
    @GetMapping("/biz/prj/exp/cash/detail")
    public CommonResult<BizFormCash> detail(@Valid BizFormCashIdParam bizFormCashIdParam) {
        return CommonResult.data(bizFormCashService.detail(bizFormCashIdParam));
    }



    /**
     * 获取费用报销（二期）- 借支单 - 累计未还金额
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取费用报销（二期）- 借支单 - 累计未还金额")
//    @SaCheckPermission("/biz/prj/exp/cash/ljNoBack")
    @GetMapping("/biz/prj/exp/cash/ljNoBack")
    public CommonResult<LjNoBackVo> ljNoBack(@Valid LjNoBackParam ljNoBackParam) {
        return CommonResult.data(bizFormCashService.ljNoBack(ljNoBackParam));
    }



    /**
     * 获取费用报销（二期）- 借支单 - 校验
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取费用报销（二期）- 借支单 - 校验")
//    @SaCheckPermission("/biz/prj/exp/cash/check")
    @GetMapping("/biz/prj/exp/cash/check")
    public CommonResult<String> check(@Valid CashCheckParam checkParam) {
        bizFormCashService.check(checkParam);
        return CommonResult.ok();
    }



    /**
     * 获取费用报销（二期）- 借支单 - 借支人(审核通过、借支余额不为0) 借支单
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("获取费用报销（二期）- 借支单 - 借支人(审核通过、借支余额不为0) 借支单")
//    @SaCheckPermission("/biz/prj/exp/cash/select")
    @GetMapping("/biz/prj/exp/cash/select")
    public CommonResult<List<BizFormCashVo>> select(@Valid CashSelectParam selectParam) {
        return CommonResult.data(bizFormCashService.select(selectParam));
    }




    /**
     * 获取费用报销（二期）- 借支单  - 列表统计（所有满足条件的数据统计）
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    @ApiOperationSupport(order = 66)
    @ApiOperation("获取费用报销（二期）- 借支单  - 列表统计（所有满足条件的数据统计）")
    @SaCheckPermission("/biz/prj/exp/cash/pageTotal")
    @GetMapping("/biz/prj/exp/cash/pageTotal")
    public CommonResult<BizFormCashTotal> pageTotal(BizFormCashPageParam bizFormCashPageParam) {
        return CommonResult.data(bizFormCashService.pageTotal(bizFormCashPageParam));
    }



    /**
     * 费用报销（二期）- 借支单 - 生成打印Excel
     *
     * @author yong.ye
     * @date  2025/09/28 11:30
     */
    @ApiOperationSupport(order = 99)
    @ApiOperation("费用报销（二期）- 借支单- 生成打印Excel")
    @CommonLog("费用报销（二期）- 借支单 - 生成打印Excel")
    @PostMapping("/biz/prj/exp/print/genExcel")
    public void genExcel(@RequestBody @Valid ExcelParam excelParam, HttpServletResponse response) {
        bizFormCashService.genExcel(excelParam,response);
    }
}
