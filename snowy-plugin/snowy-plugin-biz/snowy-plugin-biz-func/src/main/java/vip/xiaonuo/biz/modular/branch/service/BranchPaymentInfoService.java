package vip.xiaonuo.biz.modular.branch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.branch.entity.BranchPaymentInfo;
import vip.xiaonuo.biz.modular.branch.param.BranchPaymentInfoAddParam;
import vip.xiaonuo.biz.modular.branch.param.BranchPaymentInfoEditParam;
import vip.xiaonuo.biz.modular.branch.param.BranchPaymentInfoIdParam;
import vip.xiaonuo.biz.modular.branch.param.BranchPaymentInfoPageParam;

import java.util.List;

/**
 * 分公司日常费用拨付 - 付款信息Service接口
 *
 * @author pzh
 * @date  2024/08/06 14:03
 **/
public interface BranchPaymentInfoService extends IService<BranchPaymentInfo> {

    /**
     * 获取分公司日常费用拨付 - 付款信息分页
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    Page<BranchPaymentInfo> page(BranchPaymentInfoPageParam branchPaymentInfoPageParam);

    /**
     * 添加分公司日常费用拨付 - 付款信息
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    void add(BranchPaymentInfoAddParam branchPaymentInfoAddParam);

    /**
     * 编辑分公司日常费用拨付 - 付款信息
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    void edit(BranchPaymentInfoEditParam branchPaymentInfoEditParam);

    /**
     * 删除分公司日常费用拨付 - 付款信息
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    void delete(List<BranchPaymentInfoIdParam> branchPaymentInfoIdParamList);

    /**
     * 获取分公司日常费用拨付 - 付款信息详情
     *
     * @author pzh
     * @date  2024/08/06 14:03
     */
    BranchPaymentInfo detail(BranchPaymentInfoIdParam branchPaymentInfoIdParam);

    /**
     * 获取分公司日常费用拨付 - 付款信息详情
     *
     * @author pzh
     * @date  2024/08/06 14:03
     **/
    BranchPaymentInfo queryEntity(String id);


    /**
     * 根据分公司拨付id获取付款信息列表
     *
     * @author pzh
     * @date  2024/08/06 16:00
     */
    List<BranchPaymentInfo> getListByAllotmentId(BranchPaymentInfoIdParam branchPaymentInfoIdParam);


    /**
     * 批量更新分公司日常费用拨付 - 付款信息
     *
     * @author pzh
     * @date  2024/08/06 16:39
     */
    void batchUpdate(List<BranchPaymentInfoEditParam> branchPaymentInfoEditParamList);
}
