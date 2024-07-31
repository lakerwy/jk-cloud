package vip.xiaonuo.biz.modular.prj.expense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpCost;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpCostAddParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpCostEditParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpCostIdParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpCostPageParam;

import java.util.List;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 成本一览表Service接口
 *
 * @author yong.ye
 * @date  2024/07/30 15:32
 **/
public interface BizExpCostService extends IService<BizExpCost> {

    /**
     * 获取财务管理 - 费用报销|差旅费用报销 - 成本一览表分页
     *
     * @author yong.ye
     * @date  2024/07/30 15:32
     */
    Page<BizExpCost> page(BizExpCostPageParam bizExpCostPageParam);

    /**
     * 添加财务管理 - 费用报销|差旅费用报销 - 成本一览表
     *
     * @author yong.ye
     * @date  2024/07/30 15:32
     */
    void add(BizExpCostAddParam bizExpCostAddParam);

    /**
     * 编辑财务管理 - 费用报销|差旅费用报销 - 成本一览表
     *
     * @author yong.ye
     * @date  2024/07/30 15:32
     */
    void edit(BizExpCostEditParam bizExpCostEditParam);

    /**
     * 删除财务管理 - 费用报销|差旅费用报销 - 成本一览表
     *
     * @author yong.ye
     * @date  2024/07/30 15:32
     */
    void delete(List<BizExpCostIdParam> bizExpCostIdParamList);

    /**
     * 获取财务管理 - 费用报销|差旅费用报销 - 成本一览表详情
     *
     * @author yong.ye
     * @date  2024/07/30 15:32
     */
    BizExpCost detail(BizExpCostIdParam bizExpCostIdParam);

    /**
     * 获取财务管理 - 费用报销|差旅费用报销 - 成本一览表详情
     *
     * @author yong.ye
     * @date  2024/07/30 15:32
     **/
    BizExpCost queryEntity(String id);
}
