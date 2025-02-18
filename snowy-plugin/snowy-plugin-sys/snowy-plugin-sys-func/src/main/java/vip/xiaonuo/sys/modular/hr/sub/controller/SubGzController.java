package vip.xiaonuo.sys.modular.hr.sub.controller;

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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubGz;
import vip.xiaonuo.sys.modular.hr.sub.param.*;
import vip.xiaonuo.sys.modular.hr.sub.service.SubGzService;
import vip.xiaonuo.sys.modular.hr.sub.vo.MonthVo;
import vip.xiaonuo.sys.modular.hr.sub.vo.YearReportVo;
import vip.xiaonuo.sys.modular.hr.sub.vo.YearVo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 分公司-工资发放（流程）控制器
 *
 * @author yong.ye
 * @date  2025/02/06 09:09
 */
@Api(tags = "分公司-工资发放（流程）控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SubGzController {

    @Resource
    private SubGzService subGzService;

    /**
     * 获取分公司-工资发放（流程）分页
     *
     * @author yong.ye
     * @date  2025/02/06 09:09
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取分公司-工资发放（流程）分页")
//    @SaCheckPermission("/sys/hr/sub/gz/page")
    @GetMapping("/sys/hr/sub/gz/page")
    public CommonResult<Page<SubGz>> page(SubGzPageParam subGzPageParam) {
        return CommonResult.data(subGzService.page(subGzPageParam));
    }

    /**
     * 添加分公司-工资发放（流程）
     *
     * @author yong.ye
     * @date  2025/02/06 09:09
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加分公司-工资发放（流程）")
    @CommonLog("添加分公司-工资发放（流程）")
//    @SaCheckPermission("/sys/hr/sub/gz/add")
    @PostMapping("/sys/hr/sub/gz/add")
    public CommonResult<String> add(@RequestBody @Valid SubGzParam subGzParam) {
        subGzService.add(subGzParam);
        return CommonResult.ok();
    }

    /**
     * 编辑分公司-工资发放（流程）
     *
     * @author yong.ye
     * @date  2025/02/06 09:09
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑分公司-工资发放（流程）")
    @CommonLog("编辑分公司-工资发放（流程）")
//    @SaCheckPermission("/sys/hr/sub/gz/edit")
    @PostMapping("/sys/hr/sub/gz/edit")
    public CommonResult<String> edit(@RequestBody @Valid SubGzParam subGzParam) {
        subGzService.edit(subGzParam);
        return CommonResult.ok();
    }

    /**
     * 删除分公司-工资发放（流程）
     *
     * @author yong.ye
     * @date  2025/02/06 09:09
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除分公司-工资发放（流程）")
    @CommonLog("删除分公司-工资发放（流程）")
//    @SaCheckPermission("/sys/hr/sub/gz/delete")
    @PostMapping("/sys/hr/sub/gz/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SubGzIdParam> subGzIdParamList) {
        subGzService.delete(subGzIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取分公司-工资发放（流程）详情
     *
     * @author yong.ye
     * @date  2025/02/06 09:09
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取分公司-工资发放（流程）详情")
//    @SaCheckPermission("/sys/hr/sub/gz/detail")
    @GetMapping("/sys/hr/sub/gz/detail")
    public CommonResult<SubGz> detail(@Valid SubGzIdParam subGzIdParam) {
        return CommonResult.data(subGzService.detail(subGzIdParam));
    }


    /**
     * 获取分公司 - 工资数据 -月度统计
     *
     * @author yong.ye
     * @date  2025/02/06 09:09
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取分公司 - 工资数据 -月度统计")
    @GetMapping("/sys/hr/sub/gz/monthTotal")
    public CommonResult<List<MonthVo>> monthTotal(@Valid SearchTotal searchTotal) {
        return CommonResult.data(subGzService.monthTotal(searchTotal));
    }


    /**
     * 获取分公司 - 工资数据 -年度统计
     *
     * @author yong.ye
     * @date  2025/02/06 09:09
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取分公司 - 工资数据 -年度统计")
    @GetMapping("/sys/hr/sub/gz/yearReport")
    public CommonResult<List<YearReportVo>> yearReport(@Valid SearchTotal searchTotal) {
        return CommonResult.data(subGzService.yearReport(searchTotal));
    }



    /**
     * 获取分公司 - 工资数据 - 年度汇总
     *
     * @author yong.ye
     * @date  2025/02/06 09:09
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("获取分公司 - 工资数据 -年度汇总")
    @GetMapping("/sys/hr/sub/gz/yearTotal")
    public CommonResult<List<YearVo>> yearTotal(@Valid SearchTotal searchTotal) {
        return CommonResult.data(subGzService.yearTotal(searchTotal));
    }


    /**
     * 分公司工资数据报表 - 生成打印Excel 【月度统计|年度统计】
     *
     * @author yong.ye
     * @date  2024/09/28 11:30
     */
    @ApiOperationSupport(order = 66)
    @ApiOperation("分公司工资数据报表 - 生成打印Excel")
    @CommonLog("分公司工资数据报表 - 生成打印Excel")
    @PostMapping("/sys/hr/sub/gz/genExcel")
    public void genExcel(@RequestBody @Valid SearchTotal searchTotal, HttpServletResponse response) {
        subGzService.genExcel(searchTotal,response);
    }



    /**
     * 分公司工资数据报表 - 校验
     *
     * @author yong.ye
     * @date  2025/02/28 11:30
     */
    @ApiOperationSupport(order = 88)
    @ApiOperation("分公司工资数据报表 - 校验")
    @CommonLog("分公司工资数据报表 - 校验")
    @PostMapping("/sys/hr/sub/gz/check")
    public CommonResult<SubGzParam> check(@RequestBody @Valid SubGzParam subGzParam) {
        return CommonResult.data(subGzService.check(subGzParam));
    }
}
