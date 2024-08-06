package vip.xiaonuo.biz.modular.branch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.branch.entity.BranchAllotmentApply;
import vip.xiaonuo.biz.modular.branch.param.*;

import java.util.List;

/**
 * 分公司日常费用拨付 - 申请流程Service接口
 *
 * @author pzh
 * @date  2024/08/06 14:03
 **/
public interface BranchAllotmentApplyService extends IService<BranchAllotmentApply> {

    /**
     * 获取分公司日常费用拨付 - 申请流程分页
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    Page<BranchAllotmentApply> page(BranchAllotmentApplyPageParam branchAllotmentApplyPageParam);

    /**
     * 添加分公司日常费用拨付 - 申请流程
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    void add(BranchAllotmentApplyAddParam branchAllotmentApplyAddParam);

    /**
     * 编辑分公司日常费用拨付 - 申请流程
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    void edit(BranchAllotmentApplyEditParam branchAllotmentApplyEditParam);

    /**
     * 删除分公司日常费用拨付 - 申请流程
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    void delete(List<BranchAllotmentApplyIdParam> branchAllotmentApplyIdParamList);

    /**
     * 获取分公司日常费用拨付 - 申请流程详情
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    BranchAllotmentApply detail(BranchAllotmentApplyIdParam branchAllotmentApplyIdParam);

    /**
     * 获取分公司日常费用拨付 - 申请流程详情
     *
     * @author pzh
     * @date  2024/08/06 14:03
     **/
    BranchAllotmentApply queryEntity(String id);

    /**
     * 更新出纳登记账户余额
     *
     * @author pzh
     * @date  2024/08/06 15:04
     **/
    void updateRealBalance(BranchAllotmentApplyRealBalanceEditParam editParam);
}
