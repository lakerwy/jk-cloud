package vip.xiaonuo.sys.modular.hr.financialAuditReport.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.entity.FinancialAuditReport;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.param.FinancialAuditReportAddParam;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.param.FinancialAuditReportEditParam;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.param.FinancialAuditReportIdParam;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.param.FinancialAuditReportPageParam;

import java.util.List;

/**
 * 财务审计报告Service接口
 *
 * @author pzh
 * @date  2024/10/18 14:51
 **/
public interface FinancialAuditReportService extends IService<FinancialAuditReport> {

    /**
     * 获取财务审计报告分页
     *
     * @author pzh
     * @date  2024/10/18 14:51
     */
    Page<FinancialAuditReport> page(FinancialAuditReportPageParam financialAuditReportPageParam);

    /**
     * 添加财务审计报告
     *
     * @author pzh
     * @date  2024/10/18 14:51
     */
    void add(FinancialAuditReportAddParam financialAuditReportAddParam);

    /**
     * 编辑财务审计报告
     *
     * @author pzh
     * @date  2024/10/18 14:51
     */
    void edit(FinancialAuditReportEditParam financialAuditReportEditParam);

    /**
     * 删除财务审计报告
     *
     * @author pzh
     * @date  2024/10/18 14:51
     */
    void delete(List<FinancialAuditReportIdParam> financialAuditReportIdParamList);

    /**
     * 获取财务审计报告详情
     *
     * @author pzh
     * @date  2024/10/18 14:51
     */
    FinancialAuditReport detail(FinancialAuditReportIdParam financialAuditReportIdParam);

    /**
     * 获取财务审计报告详情
     *
     * @author pzh
     * @date  2024/10/18 14:51
     **/
    FinancialAuditReport queryEntity(String id);
}
