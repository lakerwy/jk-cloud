package vip.xiaonuo.biz.modular.report.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.crm.bidopen.entity.Bidopen;
import vip.xiaonuo.biz.modular.crm.bidopen.param.BidopenPageParam;
import vip.xiaonuo.biz.modular.report.param.HetongReportParam;
import vip.xiaonuo.biz.modular.report.result.HetongReportResult;
import vip.xiaonuo.biz.modular.report.service.HetongReportService;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;

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
}
