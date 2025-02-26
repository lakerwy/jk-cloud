package vip.xiaonuo.biz.modular.prj.exp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormTravelCost;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelCostAddParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelCostEditParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelCostIdParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelCostPageParam;

import java.util.List;

/**
 * 费用报销（二期）- 差旅费用报销单 - 成本明细Service接口
 *
 * @author yong.ye
 * @date  2025/02/24 15:19
 **/
public interface BizFormTravelCostService extends IService<BizFormTravelCost> {

    /**
     * 获取费用报销（二期）- 差旅费用报销单 - 成本明细分页
     *
     * @author yong.ye
     * @date  2025/02/24 15:19
     */
    Page<BizFormTravelCost> page(BizFormTravelCostPageParam bizFormTravelCostPageParam);

    /**
     * 添加费用报销（二期）- 差旅费用报销单 - 成本明细
     *
     * @author yong.ye
     * @date  2025/02/24 15:19
     */
    void add(BizFormTravelCostAddParam bizFormTravelCostAddParam);

    /**
     * 编辑费用报销（二期）- 差旅费用报销单 - 成本明细
     *
     * @author yong.ye
     * @date  2025/02/24 15:19
     */
    void edit(BizFormTravelCostEditParam bizFormTravelCostEditParam);

    /**
     * 删除费用报销（二期）- 差旅费用报销单 - 成本明细
     *
     * @author yong.ye
     * @date  2025/02/24 15:19
     */
    void delete(List<BizFormTravelCostIdParam> bizFormTravelCostIdParamList);

    /**
     * 获取费用报销（二期）- 差旅费用报销单 - 成本明细详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:19
     */
    BizFormTravelCost detail(BizFormTravelCostIdParam bizFormTravelCostIdParam);

    /**
     * 获取费用报销（二期）- 差旅费用报销单 - 成本明细详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:19
     **/
    BizFormTravelCost queryEntity(String id);
}
