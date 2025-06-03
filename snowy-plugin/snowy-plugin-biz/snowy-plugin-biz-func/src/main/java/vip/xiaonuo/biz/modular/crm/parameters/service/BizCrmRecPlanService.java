package vip.xiaonuo.biz.modular.crm.parameters.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.parameters.entity.BizCrmRecPlan;
import vip.xiaonuo.biz.modular.crm.parameters.param.BizCrmRecPlanAddParam;
import vip.xiaonuo.biz.modular.crm.parameters.param.BizCrmRecPlanEditParam;
import vip.xiaonuo.biz.modular.crm.parameters.param.BizCrmRecPlanIdParam;
import vip.xiaonuo.biz.modular.crm.parameters.param.BizCrmRecPlanPageParam;

import java.util.List;

/**
 * 合同收款计划Service接口
 *
 * @author pzh
 * @date  2025/05/30 09:35
 **/
public interface BizCrmRecPlanService extends IService<BizCrmRecPlan> {

    /**
     * 获取合同收款计划分页
     *
     * @author pzh
     * @date  2025/05/30 09:35
     */
    Page<BizCrmRecPlan> page(BizCrmRecPlanPageParam bizCrmRecPlanPageParam);

    /**
     * 添加合同收款计划
     *
     * @author pzh
     * @date  2025/05/30 09:35
     */
    void add(BizCrmRecPlanAddParam bizCrmRecPlanAddParam);

    /**
     * 编辑合同收款计划
     *
     * @author pzh
     * @date  2025/05/30 09:35
     */
    void edit(BizCrmRecPlanEditParam bizCrmRecPlanEditParam);

    /**
     * 删除合同收款计划
     *
     * @author pzh
     * @date  2025/05/30 09:35
     */
    void delete(List<BizCrmRecPlanIdParam> bizCrmRecPlanIdParamList);

    /**
     * 获取合同收款计划详情
     *
     * @author pzh
     * @date  2025/05/30 09:35
     */
    BizCrmRecPlan detail(BizCrmRecPlanIdParam bizCrmRecPlanIdParam);

    /**
     * 获取合同收款计划详情
     *
     * @author pzh
     * @date  2025/05/30 09:35
     **/
    BizCrmRecPlan queryEntity(String id);
}
