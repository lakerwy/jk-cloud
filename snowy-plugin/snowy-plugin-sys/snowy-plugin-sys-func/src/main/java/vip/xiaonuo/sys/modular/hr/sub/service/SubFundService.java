package vip.xiaonuo.sys.modular.hr.sub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFund;
import vip.xiaonuo.sys.modular.hr.sub.param.*;

import java.util.List;

/**
 * 分公司-五险一金（流程）Service接口
 *
 * @author yong.ye
 * @date  2025/02/06 09:12
 **/
public interface SubFundService extends IService<SubFund> {

    /**
     * 获取分公司-五险一金（流程）分页
     *
     * @author yong.ye
     * @date  2025/02/06 09:12
     */
    Page<SubFund> page(SubFundPageParam subFundPageParam);

    /**
     * 添加分公司-五险一金（流程）
     *
     * @author yong.ye
     * @date  2025/02/06 09:12
     */
    void add(SubFundParam subFundParam);

    /**
     * 编辑分公司-五险一金（流程）
     *
     * @author yong.ye
     * @date  2025/02/06 09:12
     */
    void edit(SubFundParam subFundParam);

    /**
     * 删除分公司-五险一金（流程）
     *
     * @author yong.ye
     * @date  2025/02/06 09:12
     */
    void delete(List<SubFundIdParam> subFundIdParamList);

    /**
     * 获取分公司-五险一金（流程）详情
     *
     * @author yong.ye
     * @date  2025/02/06 09:12
     */
    SubFund detail(SubFundIdParam subFundIdParam);

    /**
     * 获取分公司-五险一金（流程）详情
     *
     * @author yong.ye
     * @date  2025/02/06 09:12
     **/
    SubFund queryEntity(String id);
}
