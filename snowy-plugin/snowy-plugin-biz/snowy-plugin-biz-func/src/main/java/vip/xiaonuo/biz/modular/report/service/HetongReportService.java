package vip.xiaonuo.biz.modular.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.report.param.HetongReportParam;
import vip.xiaonuo.biz.modular.report.param.HtBillParam;
import vip.xiaonuo.biz.modular.report.param.HtFilingPageParam;
import vip.xiaonuo.biz.modular.report.param.HtFilingParam;
import vip.xiaonuo.biz.modular.report.result.HTBillResult;
import vip.xiaonuo.biz.modular.report.result.HTFilingPageResult;
import vip.xiaonuo.biz.modular.report.result.HTYearFilingRate;
import vip.xiaonuo.biz.modular.report.result.HetongReportResult;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/7/10 9:11
 */
public interface HetongReportService extends IService<Hetong> {

    /**
     * 获取业务-CRM-合同报表分页
     *
     * @author yong.ye
     * @date  2023/07/10 16:31
     */
    HetongReportResult listReport(HetongReportParam hetongReportParam);

    /**
     * 分公司|零星 收入合同归档统计表
     *
     * @author yong.ye
     * @date  2023/07/10 16:31
     */
    List<HTYearFilingRate> filingReport(HtFilingParam htFilingParam);

    /**
     * 根据合同类型、统计时间段、报表类型 获取收入合同清单报表数据
     *
     * @param htBillParam
     * @return
     */
    IPage<HTBillResult> billReport(HtBillParam htBillParam);

    /**
     * 分页获取合同信息+所属责任单位合同归档率+超过3个月未归档统计数据
     *
     * @param htFilingPageParam
     * @return
     */
    IPage<HTFilingPageResult> pageHtFiling(HtFilingPageParam htFilingPageParam);
}
