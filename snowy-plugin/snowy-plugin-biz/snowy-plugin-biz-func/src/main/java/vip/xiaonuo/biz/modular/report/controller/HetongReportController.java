package vip.xiaonuo.biz.modular.report.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.report.param.HetongReportParam;
import vip.xiaonuo.biz.modular.report.param.HtBillParam;
import vip.xiaonuo.biz.modular.report.param.HtFilingPageParam;
import vip.xiaonuo.biz.modular.report.param.HtFilingParam;
import vip.xiaonuo.biz.modular.report.result.HTBillResult;
import vip.xiaonuo.biz.modular.report.result.HTFilingPageResult;
import vip.xiaonuo.biz.modular.report.result.HTYearFilingRate;
import vip.xiaonuo.biz.modular.report.result.HetongReportResult;
import vip.xiaonuo.biz.modular.report.service.HetongReportService;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yong.ye
 * @date 2023/7/10 8:52
 */

@Api(tags = "业务-CRM-合同相关报表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HetongReportController {

    @Resource
    private HetongReportService hetongReportService;

    /**
     * 获取业务-CRM-合同报表
     *
     * @author yong.ye
     * @date  2023/07/10 16:31
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务-CRM-合同报表")
    @GetMapping("/biz/report/hetong/listReport")
    public CommonResult<HetongReportResult> listReport(HetongReportParam hetongReportParam) {
        return CommonResult.data(hetongReportService.listReport(hetongReportParam));
    }

    /**
     * 获取（分公司|零星|机构|公司部门）收入合同归档率|超过3月未归档统计表
     * 默认统计近1年合同归档率 :当前日期向前推3个月后的近一年
     * 默认2017-1-1至今未归档:自2017-1-1至当前日期向前3个月范围内，超过3个月未归档
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("获取（分公司|零星|机构|公司部门）收入合同归档率|超过3月未归档统计表")
    @GetMapping("/biz/report/hetong/filingReport")
    public CommonResult<List<HTYearFilingRate>> filingReport(HtFilingParam htFilingParam) {
        return CommonResult.data(hetongReportService.filingReport(htFilingParam));
    }

    /**
     * 根据合同类型：分公司、零星、机构、公司部门
     * 统计时间阶段：如 2022-3-31 至 2023-3-31
     * 统计报表类型：1、分公司近一年收入合同清单（盖章日期2022.3.31-2023.3.31）
     *            2、分公司超过3个月未归档合同数统计表（盖章日期2017.1.1-2023.3.31）
     * 获取对应的合同报表数据
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("根据合同类型、统计时间段、报表类型 获取收入合同清单报表数据")
    @GetMapping("/biz/report/hetong/billReport")
    public CommonResult<IPage<HTBillResult>> billReport(HtBillParam htBillParam) {
        return CommonResult.data(hetongReportService.billReport(htBillParam));
    }


    /**
     * 分页获取合同信息+所属责任单位合同归档率+超过3个月未归档统计数据
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("分页获取合同信息+所属责任单位合同归档率+超过3个月未归档统计数据")
    @GetMapping("/biz/report/hetong/pageHtFiling")
    public CommonResult<IPage<HTFilingPageResult>> pageHtFiling(HtFilingPageParam htFilingPageParam) {
        return CommonResult.data(hetongReportService.pageHtFiling(htFilingPageParam));
    }
}
