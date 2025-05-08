package vip.xiaonuo.biz.modular.crm.subcontract.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.SubcontractSettlement;
import vip.xiaonuo.biz.modular.crm.subcontract.param.*;
import vip.xiaonuo.biz.modular.crm.subcontract.vo.AllotmentHisVo;
import vip.xiaonuo.biz.modular.crm.subcontract.vo.AllotmentVo;
import vip.xiaonuo.biz.modular.crm.subcontract.vo.SubcontractSettlementItemVo;

import java.util.List;

/**
 * 分包合同结算审批Service接口
 *
 * @author pzh
 * @date  2025/04/30 11:19
 **/
public interface SubcontractSettlementService extends IService<SubcontractSettlement> {

    /**
     * 获取分包合同结算审批分页
     *
     * @author pzh
     * @date  2025/04/30 11:19
     */
    Page<SubcontractSettlement> page(SubcontractSettlementPageParam subcontractSettlementPageParam);

    /**
     * 添加分包合同结算审批
     *
     * @author pzh
     * @date  2025/04/30 11:19
     */
    void add(SubcontractSettlementAddParam subcontractSettlementAddParam);

    /**
     * 编辑分包合同结算审批
     *
     * @author pzh
     * @date  2025/04/30 11:19
     */
    void edit(SubcontractSettlementEditParam subcontractSettlementEditParam);

    /**
     * 删除分包合同结算审批
     *
     * @author pzh
     * @date  2025/04/30 11:19
     */
    void delete(List<SubcontractSettlementIdParam> subcontractSettlementIdParamList);

    /**
     * 获取分包合同结算审批详情
     *
     * @author pzh
     * @date  2025/04/30 11:19
     */
    SubcontractSettlement detail(SubcontractSettlementIdParam subcontractSettlementIdParam);

    /**
     * 获取分包合同结算审批详情
     *
     * @author pzh
     * @date  2025/04/30 11:19
     **/
    SubcontractSettlement queryEntity(String id);

    /**
     * 分包合同结算 - 校验
     *
     * @author pzh
     * @date  2025/04/30 11:19
     **/
    void check(AllotmentHtIdParam param);

    /**
     * 获取项目前期拨付情况
     *
     * @author pzh
     * @date 2025/05/06 09:00
     */
    List<AllotmentHisVo> allotmentHis(AllotmentHtIdParam param);

    /**
     * 获取分包拨付情况列表
     *
     * @author pzh
     * @date 2025/05/07 09:00
     */
    List<AllotmentVo> allotmentList(AllotmentHtIdParam param);

    /**
     * 获取未结算分包合同列表
     *
     * @author pzh
     * @date  2025/05/07 14:18
     */
    List<SubcontractSettlementItemVo> notSettlementList(AllotmentHtIdParam param);


    /**
     * 获取结算分包合同号
     *
     * @author pzh
     * @date  2025/05/07 14:18
     */
    List<String> getSettlementSubcontractNo(String hetongId);
}
