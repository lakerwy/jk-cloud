package vip.xiaonuo.biz.modular.prj.fin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataInvoice;
import vip.xiaonuo.biz.modular.prj.fin.param.*;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;

import java.util.List;

/**
 * 财务数据汇总台账 - 销售发票Service接口
 *
 * @author yy
 * @date  2025/06/05 17:24
 **/
public interface FinDataInvoiceService extends IService<FinDataInvoice> {

    /**
     * 获取财务数据汇总台账 - 销售发票分页
     *
     * @author yy
     * @date  2025/06/05 17:24
     */
    Page<FinDataInvoice> page(FinDataInvoicePageParam finDataInvoicePageParam);

    /**
     * 添加财务数据汇总台账 - 销售发票
     *
     * @author yy
     * @date  2025/06/05 17:24
     */
    void add(FinDataInvoiceAddParam finDataInvoiceAddParam);

    /**
     * 编辑财务数据汇总台账 - 销售发票
     *
     * @author yy
     * @date  2025/06/05 17:24
     */
    void edit(FinDataInvoiceEditParam finDataInvoiceEditParam);

    /**
     * 删除财务数据汇总台账 - 销售发票
     *
     * @author yy
     * @date  2025/06/05 17:24
     */
    void delete(List<FinDataInvoiceIdParam> finDataInvoiceIdParamList);

    /**
     * 获取财务数据汇总台账 - 销售发票详情
     *
     * @author yy
     * @date  2025/06/05 17:24
     */
    FinDataInvoice detail(FinDataInvoiceIdParam finDataInvoiceIdParam);

    /**
     * 获取财务数据汇总台账 - 销售发票详情
     *
     * @author yy
     * @date  2025/06/05 17:24
     **/
    FinDataInvoice queryEntity(String id);

    ImportResult doImport(ImportParam<ImportFinDataInvoice> importParam);
}
