package vip.xiaonuo.sys.modular.hr.creditEvaluation.service.impl;

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
import vip.xiaonuo.sys.modular.hr.creditEvaluation.entity.CreditEvaluation;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.mapper.CreditEvaluationMapper;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.param.CreditEvaluationAddParam;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.param.CreditEvaluationEditParam;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.param.CreditEvaluationIdParam;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.param.CreditEvaluationPageParam;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.service.CreditEvaluationService;

import java.util.List;

/**
 * 信用评价Service接口实现类
 *
 * @author pzh
 * @date  2024/10/18 10:49
 **/
@Service
public class CreditEvaluationServiceImpl extends ServiceImpl<CreditEvaluationMapper, CreditEvaluation> implements CreditEvaluationService {

    @Override
    public Page<CreditEvaluation> page(CreditEvaluationPageParam creditEvaluationPageParam) {
        QueryWrapper<CreditEvaluation> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(creditEvaluationPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(CreditEvaluation::getCity,creditEvaluationPageParam.getSearchKey())
                    .or().like(CreditEvaluation::getSource,creditEvaluationPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(creditEvaluationPageParam.getSortField(), creditEvaluationPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(creditEvaluationPageParam.getSortOrder());
            queryWrapper.orderBy(true, creditEvaluationPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(creditEvaluationPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(CreditEvaluation::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CreditEvaluationAddParam creditEvaluationAddParam) {
        CreditEvaluation creditEvaluation = BeanUtil.toBean(creditEvaluationAddParam, CreditEvaluation.class);
        this.save(creditEvaluation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CreditEvaluationEditParam creditEvaluationEditParam) {
        CreditEvaluation creditEvaluation = this.queryEntity(creditEvaluationEditParam.getId());
        BeanUtil.copyProperties(creditEvaluationEditParam, creditEvaluation);
        this.updateById(creditEvaluation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CreditEvaluationIdParam> creditEvaluationIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(creditEvaluationIdParamList, CreditEvaluationIdParam::getId));
    }

    @Override
    public CreditEvaluation detail(CreditEvaluationIdParam creditEvaluationIdParam) {
        return this.queryEntity(creditEvaluationIdParam.getId());
    }

    @Override
    public CreditEvaluation queryEntity(String id) {
        CreditEvaluation creditEvaluation = this.getById(id);
        if(ObjectUtil.isEmpty(creditEvaluation)) {
            throw new CommonException("信用评价不存在，id值为：{}", id);
        }
        return creditEvaluation;
    }
}
