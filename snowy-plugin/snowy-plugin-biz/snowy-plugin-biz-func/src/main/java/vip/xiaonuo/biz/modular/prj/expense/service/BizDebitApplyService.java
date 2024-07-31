package vip.xiaonuo.biz.modular.prj.expense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizDebitApply;
import vip.xiaonuo.biz.modular.prj.expense.param.BizDebitApplyAddParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizDebitApplyEditParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizDebitApplyIdParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizDebitApplyPageParam;

import java.util.List;

/**
 * 财务管理 - 借支单 - 申请流程Service接口
 *
 * @author yong.ye
 * @date  2024/07/30 15:33
 **/
public interface BizDebitApplyService extends IService<BizDebitApply> {

    /**
     * 获取财务管理 - 借支单 - 申请流程分页
     *
     * @author yong.ye
     * @date  2024/07/30 15:33
     */
    Page<BizDebitApply> page(BizDebitApplyPageParam bizDebitApplyPageParam);

    /**
     * 添加财务管理 - 借支单 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/30 15:33
     */
    void add(BizDebitApplyAddParam bizDebitApplyAddParam);

    /**
     * 编辑财务管理 - 借支单 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/30 15:33
     */
    void edit(BizDebitApplyEditParam bizDebitApplyEditParam);

    /**
     * 删除财务管理 - 借支单 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/30 15:33
     */
    void delete(List<BizDebitApplyIdParam> bizDebitApplyIdParamList);

    /**
     * 获取财务管理 - 借支单 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/07/30 15:33
     */
    BizDebitApply detail(BizDebitApplyIdParam bizDebitApplyIdParam);

    /**
     * 获取财务管理 - 借支单 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/07/30 15:33
     **/
    BizDebitApply queryEntity(String id);
}
