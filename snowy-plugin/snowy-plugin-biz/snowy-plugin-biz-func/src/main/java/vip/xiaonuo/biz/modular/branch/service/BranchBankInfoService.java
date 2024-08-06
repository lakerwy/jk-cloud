package vip.xiaonuo.biz.modular.branch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.branch.entity.BranchBankInfo;
import vip.xiaonuo.biz.modular.branch.param.BranchBankInfoAddParam;
import vip.xiaonuo.biz.modular.branch.param.BranchBankInfoEditParam;
import vip.xiaonuo.biz.modular.branch.param.BranchBankInfoIdParam;
import vip.xiaonuo.biz.modular.branch.param.BranchBankInfoPageParam;

import java.util.List;

/**
 * 分公司银行账户信息Service接口
 *
 * @author pzh
 * @date  2024/08/05 11:46
 **/
public interface BranchBankInfoService extends IService<BranchBankInfo> {

    /**
     * 获取分公司银行账户信息分页
     *
     * @author pzh
     * @date  2024/08/05 11:46
     */
    Page<BranchBankInfo> page(BranchBankInfoPageParam branchBankInfoPageParam);

    /**
     * 添加分公司银行账户信息
     *
     * @author pzh
     * @date  2024/08/05 11:46
     */
    void add(BranchBankInfoAddParam branchBankInfoAddParam);

    /**
     * 编辑分公司银行账户信息
     *
     * @author pzh
     * @date  2024/08/05 11:46
     */
    void edit(BranchBankInfoEditParam branchBankInfoEditParam);

    /**
     * 删除分公司银行账户信息
     *
     * @author pzh
     * @date  2024/08/05 11:46
     */
    void delete(List<BranchBankInfoIdParam> branchBankInfoIdParamList);

    /**
     * 获取分公司银行账户信息详情
     *
     * @author pzh
     * @date  2024/08/05 11:46
     */
    BranchBankInfo detail(BranchBankInfoIdParam branchBankInfoIdParam);

    /**
     * 获取分公司银行账户信息详情
     *
     * @author pzh
     * @date  2024/08/05 11:46
     **/
    BranchBankInfo queryEntity(String id);
}
