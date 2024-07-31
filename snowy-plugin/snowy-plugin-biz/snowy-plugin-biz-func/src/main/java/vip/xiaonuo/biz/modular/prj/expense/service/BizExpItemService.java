package vip.xiaonuo.biz.modular.prj.expense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpItem;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpItemAddParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpItemEditParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpItemIdParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpItemPageParam;

import java.util.List;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 费用明细Service接口
 *
 * @author yong.ye
 * @date  2024/07/30 15:31
 **/
public interface BizExpItemService extends IService<BizExpItem> {

    /**
     * 获取财务管理 - 费用报销|差旅费用报销 - 费用明细分页
     *
     * @author yong.ye
     * @date  2024/07/30 15:31
     */
    Page<BizExpItem> page(BizExpItemPageParam bizExpItemPageParam);

    /**
     * 添加财务管理 - 费用报销|差旅费用报销 - 费用明细
     *
     * @author yong.ye
     * @date  2024/07/30 15:31
     */
    void add(BizExpItemAddParam bizExpItemAddParam);

    /**
     * 编辑财务管理 - 费用报销|差旅费用报销 - 费用明细
     *
     * @author yong.ye
     * @date  2024/07/30 15:31
     */
    void edit(BizExpItemEditParam bizExpItemEditParam);

    /**
     * 删除财务管理 - 费用报销|差旅费用报销 - 费用明细
     *
     * @author yong.ye
     * @date  2024/07/30 15:31
     */
    void delete(List<BizExpItemIdParam> bizExpItemIdParamList);

    /**
     * 获取财务管理 - 费用报销|差旅费用报销 - 费用明细详情
     *
     * @author yong.ye
     * @date  2024/07/30 15:31
     */
    BizExpItem detail(BizExpItemIdParam bizExpItemIdParam);

    /**
     * 获取财务管理 - 费用报销|差旅费用报销 - 费用明细详情
     *
     * @author yong.ye
     * @date  2024/07/30 15:31
     **/
    BizExpItem queryEntity(String id);
}
