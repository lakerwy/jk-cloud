package vip.xiaonuo.biz.modular.report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.report.param.HetongReportParam;
import vip.xiaonuo.biz.modular.report.result.HetongReportResult;

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
}
