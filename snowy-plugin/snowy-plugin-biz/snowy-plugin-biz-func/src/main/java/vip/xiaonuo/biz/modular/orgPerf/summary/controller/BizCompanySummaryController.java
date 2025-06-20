package vip.xiaonuo.biz.modular.orgPerf.summary.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizCompanyGoalService;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.CompanySummaryListParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.CompanySummaryListVo;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公司绩效汇总控制器
 *
 * @author pzh
 * @date  2025/06/19 15:55
 */
@Api(tags = "公司绩效汇总控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizCompanySummaryController {

    @Resource
    private BizCompanyGoalService bizCompanyGoalService;

    /**
     * 获取公司绩效汇总列表
     *
     * @author pzh
     * @date  2025/06/19 15:55
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取公司绩效汇总列表")
    // @SaCheckPermission("/biz/orgPerf/summary/company/list")
    @GetMapping("/biz/orgPerf/summary/company/list")
    public CommonResult<List<CompanySummaryListVo>> companySummaryList(CompanySummaryListParam companySummaryListParam) {
        return CommonResult.data(bizCompanyGoalService.companySummaryList(companySummaryListParam));
    }

}
