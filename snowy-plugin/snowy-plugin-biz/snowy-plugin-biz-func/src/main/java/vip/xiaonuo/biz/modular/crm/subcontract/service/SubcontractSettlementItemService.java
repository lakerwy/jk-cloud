package vip.xiaonuo.biz.modular.crm.subcontract.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.SubcontractSettlementItem;
import vip.xiaonuo.biz.modular.crm.subcontract.param.SubcontractSettlementItemAddParam;
import vip.xiaonuo.biz.modular.crm.subcontract.param.SubcontractSettlementItemEditParam;
import vip.xiaonuo.biz.modular.crm.subcontract.param.SubcontractSettlementItemIdParam;
import vip.xiaonuo.biz.modular.crm.subcontract.param.SubcontractSettlementItemPageParam;

import java.util.List;

/**
 * 分包合同结算明细Service接口
 *
 * @author pzh
 * @date  2025/04/30 11:20
 **/
public interface SubcontractSettlementItemService extends IService<SubcontractSettlementItem> {

    /**
     * 获取分包合同结算明细分页
     *
     * @author pzh
     * @date  2025/04/30 11:20
     */
    Page<SubcontractSettlementItem> page(SubcontractSettlementItemPageParam subcontractSettlementItemPageParam);

    /**
     * 添加分包合同结算明细
     *
     * @author pzh
     * @date  2025/04/30 11:20
     */
    void add(SubcontractSettlementItemAddParam subcontractSettlementItemAddParam);

    /**
     * 编辑分包合同结算明细
     *
     * @author pzh
     * @date  2025/04/30 11:20
     */
    void edit(SubcontractSettlementItemEditParam subcontractSettlementItemEditParam);

    /**
     * 删除分包合同结算明细
     *
     * @author pzh
     * @date  2025/04/30 11:20
     */
    void delete(List<SubcontractSettlementItemIdParam> subcontractSettlementItemIdParamList);

    /**
     * 获取分包合同结算明细详情
     *
     * @author pzh
     * @date  2025/04/30 11:20
     */
    SubcontractSettlementItem detail(SubcontractSettlementItemIdParam subcontractSettlementItemIdParam);

    /**
     * 获取分包合同结算明细详情
     *
     * @author pzh
     * @date  2025/04/30 11:20
     **/
    SubcontractSettlementItem queryEntity(String id);
}
