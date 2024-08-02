package vip.xiaonuo.biz.modular.prj.expense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpApply;
import vip.xiaonuo.biz.modular.prj.expense.param.*;
import vip.xiaonuo.biz.modular.prj.expense.vo.BizExpApplyVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 申请流程Service接口
 *
 * @author yong.ye
 * @date  2024/07/30 15:26
 **/
public interface BizExpApplyService extends IService<BizExpApply> {

    /**
     * 获取财务管理 - 费用报销|差旅费用报销 - 申请流程分页
     *
     * @author yong.ye
     * @date  2024/07/30 15:26
     */
    Page<BizExpApplyVo> page(BizExpApplyPageParam bizExpApplyPageParam);

    /**
     * 添加财务管理 - 费用报销|差旅费用报销 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/30 15:26
     */
    void add(BizExpApplyAddParam bizExpApplyAddParam);

    /**
     * 编辑财务管理 - 费用报销|差旅费用报销 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/30 15:26
     */
    void edit(BizExpApplyEditParam bizExpApplyEditParam);

    /**
     * 删除财务管理 - 费用报销|差旅费用报销 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/30 15:26
     */
    void delete(List<BizExpApplyIdParam> bizExpApplyIdParamList);

    /**
     * 获取财务管理 - 费用报销|差旅费用报销 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/07/30 15:26
     */
    BizExpApplyVo detail(BizExpApplyIdParam bizExpApplyIdParam);

    /**
     * 获取财务管理 - 费用报销|差旅费用报销 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/07/30 15:26
     **/
    BizExpApply queryEntity(String id);


    /**
     * excel报表生成打印版
     * @param excelParam
     */
    void genExcel(ExcelParam excelParam, HttpServletResponse response);
}
