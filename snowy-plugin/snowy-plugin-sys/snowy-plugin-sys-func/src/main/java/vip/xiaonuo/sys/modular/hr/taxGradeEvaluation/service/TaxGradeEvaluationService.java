package vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.entity.TaxGradeEvaluation;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param.TaxGradeEvaluationAddParam;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param.TaxGradeEvaluationEditParam;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param.TaxGradeEvaluationIdParam;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param.TaxGradeEvaluationPageParam;

import java.util.List;

/**
 * 纳税等级评价Service接口
 *
 * @author pzh
 * @date  2024/11/27 14:45
 **/
public interface TaxGradeEvaluationService extends IService<TaxGradeEvaluation> {

    /**
     * 获取纳税等级评价分页
     *
     * @author pzh
     * @date  2024/11/27 14:45
     */
    Page<TaxGradeEvaluation> page(TaxGradeEvaluationPageParam taxGradeEvaluationPageParam);

    /**
     * 添加纳税等级评价
     *
     * @author pzh
     * @date  2024/11/27 14:45
     */
    void add(TaxGradeEvaluationAddParam taxGradeEvaluationAddParam);

    /**
     * 编辑纳税等级评价
     *
     * @author pzh
     * @date  2024/11/27 14:45
     */
    void edit(TaxGradeEvaluationEditParam taxGradeEvaluationEditParam);

    /**
     * 删除纳税等级评价
     *
     * @author pzh
     * @date  2024/11/27 14:45
     */
    void delete(List<TaxGradeEvaluationIdParam> taxGradeEvaluationIdParamList);

    /**
     * 获取纳税等级评价详情
     *
     * @author pzh
     * @date  2024/11/27 14:45
     */
    TaxGradeEvaluation detail(TaxGradeEvaluationIdParam taxGradeEvaluationIdParam);

    /**
     * 获取纳税等级评价详情
     *
     * @author pzh
     * @date  2024/11/27 14:45
     **/
    TaxGradeEvaluation queryEntity(String id);
}
