package vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.entity.TaxGradeEvaluation;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.mapper.TaxGradeEvaluationMapper;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param.TaxGradeEvaluationAddParam;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param.TaxGradeEvaluationEditParam;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param.TaxGradeEvaluationIdParam;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param.TaxGradeEvaluationPageParam;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.service.TaxGradeEvaluationService;

import java.util.List;

/**
 * 纳税等级评价Service接口实现类
 *
 * @author pzh
 * @date  2024/11/27 14:45
 **/
@Service
public class TaxGradeEvaluationServiceImpl extends ServiceImpl<TaxGradeEvaluationMapper, TaxGradeEvaluation> implements TaxGradeEvaluationService {

    @Override
    public Page<TaxGradeEvaluation> page(TaxGradeEvaluationPageParam taxGradeEvaluationPageParam) {
        QueryWrapper<TaxGradeEvaluation> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(taxGradeEvaluationPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(TaxGradeEvaluation::getName,taxGradeEvaluationPageParam.getSearchKey())
                    .or().like(TaxGradeEvaluation::getTaxpayerName,taxGradeEvaluationPageParam.getSearchKey())
                    .or().like(TaxGradeEvaluation::getUnifiedSocialCreditCode,taxGradeEvaluationPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(taxGradeEvaluationPageParam.getSortField(), taxGradeEvaluationPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(taxGradeEvaluationPageParam.getSortOrder());
            queryWrapper.orderBy(true, taxGradeEvaluationPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(taxGradeEvaluationPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(TaxGradeEvaluation::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TaxGradeEvaluationAddParam taxGradeEvaluationAddParam) {
        TaxGradeEvaluation taxGradeEvaluation = BeanUtil.toBean(taxGradeEvaluationAddParam, TaxGradeEvaluation.class);
        this.save(taxGradeEvaluation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TaxGradeEvaluationEditParam taxGradeEvaluationEditParam) {
        TaxGradeEvaluation taxGradeEvaluation = this.queryEntity(taxGradeEvaluationEditParam.getId());
        BeanUtil.copyProperties(taxGradeEvaluationEditParam, taxGradeEvaluation);
        this.updateById(taxGradeEvaluation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TaxGradeEvaluationIdParam> taxGradeEvaluationIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(taxGradeEvaluationIdParamList, TaxGradeEvaluationIdParam::getId));
    }

    @Override
    public TaxGradeEvaluation detail(TaxGradeEvaluationIdParam taxGradeEvaluationIdParam) {
        return this.queryEntity(taxGradeEvaluationIdParam.getId());
    }

    @Override
    public TaxGradeEvaluation queryEntity(String id) {
        TaxGradeEvaluation taxGradeEvaluation = this.getById(id);
        if(ObjectUtil.isEmpty(taxGradeEvaluation)) {
            throw new CommonException("纳税等级评价不存在，id值为：{}", id);
        }
        return taxGradeEvaluation;
    }
}
