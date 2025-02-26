package vip.xiaonuo.biz.modular.prj.exp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormFeeCost;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeCostAddParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeCostEditParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeCostIdParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeCostPageParam;

import java.util.List;

/**
 * 费用报销（二期）- 费用报销单 - 成本明细Service接口
 *
 * @author yong.ye
 * @date  2025/02/24 15:15
 **/
public interface BizFormFeeCostService extends IService<BizFormFeeCost> {

    /**
     * 获取费用报销（二期）- 费用报销单 - 成本明细分页
     *
     * @author yong.ye
     * @date  2025/02/24 15:15
     */
    Page<BizFormFeeCost> page(BizFormFeeCostPageParam bizFormFeeCostPageParam);

    /**
     * 添加费用报销（二期）- 费用报销单 - 成本明细
     *
     * @author yong.ye
     * @date  2025/02/24 15:15
     */
    void add(BizFormFeeCostAddParam bizFormFeeCostAddParam);

    /**
     * 编辑费用报销（二期）- 费用报销单 - 成本明细
     *
     * @author yong.ye
     * @date  2025/02/24 15:15
     */
    void edit(BizFormFeeCostEditParam bizFormFeeCostEditParam);

    /**
     * 删除费用报销（二期）- 费用报销单 - 成本明细
     *
     * @author yong.ye
     * @date  2025/02/24 15:15
     */
    void delete(List<BizFormFeeCostIdParam> bizFormFeeCostIdParamList);

    /**
     * 获取费用报销（二期）- 费用报销单 - 成本明细详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:15
     */
    BizFormFeeCost detail(BizFormFeeCostIdParam bizFormFeeCostIdParam);

    /**
     * 获取费用报销（二期）- 费用报销单 - 成本明细详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:15
     **/
    BizFormFeeCost queryEntity(String id);
}
