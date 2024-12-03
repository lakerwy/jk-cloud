package vip.xiaonuo.sys.modular.hr.creditEvaluation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.entity.CreditEvaluation;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.param.CreditEvaluationAddParam;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.param.CreditEvaluationEditParam;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.param.CreditEvaluationIdParam;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.param.CreditEvaluationPageParam;

import java.util.List;

/**
 * 信用评价Service接口
 *
 * @author pzh
 * @date  2024/10/18 10:49
 **/
public interface CreditEvaluationService extends IService<CreditEvaluation> {

    /**
     * 获取信用评价分页
     *
     * @author pzh
     * @date  2024/10/18 10:49
     */
    Page<CreditEvaluation> page(CreditEvaluationPageParam creditEvaluationPageParam);

    /**
     * 添加信用评价
     *
     * @author pzh
     * @date  2024/10/18 10:49
     */
    void add(CreditEvaluationAddParam creditEvaluationAddParam);

    /**
     * 编辑信用评价
     *
     * @author pzh
     * @date  2024/10/18 10:49
     */
    void edit(CreditEvaluationEditParam creditEvaluationEditParam);

    /**
     * 删除信用评价
     *
     * @author pzh
     * @date  2024/10/18 10:49
     */
    void delete(List<CreditEvaluationIdParam> creditEvaluationIdParamList);

    /**
     * 获取信用评价详情
     *
     * @author pzh
     * @date  2024/10/18 10:49
     */
    CreditEvaluation detail(CreditEvaluationIdParam creditEvaluationIdParam);

    /**
     * 获取信用评价详情
     *
     * @author pzh
     * @date  2024/10/18 10:49
     **/
    CreditEvaluation queryEntity(String id);
}
