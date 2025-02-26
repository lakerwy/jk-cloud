package vip.xiaonuo.biz.modular.prj.exp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormTravelCost;
import vip.xiaonuo.biz.modular.prj.exp.mapper.BizFormTravelCostMapper;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelCostAddParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelCostEditParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelCostIdParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelCostPageParam;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormTravelCostService;

import java.util.List;

/**
 * 费用报销（二期）- 差旅费用报销单 - 成本明细Service接口实现类
 *
 * @author yong.ye
 * @date  2025/02/24 15:19
 **/
@Service
@DS("slave_1")
public class BizFormTravelCostServiceImpl extends ServiceImpl<BizFormTravelCostMapper, BizFormTravelCost> implements BizFormTravelCostService {

    @Override
    public Page<BizFormTravelCost> page(BizFormTravelCostPageParam bizFormTravelCostPageParam) {
        QueryWrapper<BizFormTravelCost> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizFormTravelCostPageParam.getHtId())) {
            queryWrapper.lambda().eq(BizFormTravelCost::getHtId, bizFormTravelCostPageParam.getHtId());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelCostPageParam.getHtName())) {
            queryWrapper.lambda().like(BizFormTravelCost::getHtName, bizFormTravelCostPageParam.getHtName());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelCostPageParam.getHtSn())) {
            queryWrapper.lambda().like(BizFormTravelCost::getHtSn, bizFormTravelCostPageParam.getHtSn());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelCostPageParam.getExpItem())) {
            queryWrapper.lambda().eq(BizFormTravelCost::getExpItem, bizFormTravelCostPageParam.getExpItem());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelCostPageParam.getSecondExpItem())) {
            queryWrapper.lambda().eq(BizFormTravelCost::getSecondExpItem, bizFormTravelCostPageParam.getSecondExpItem());
        }
        if(ObjectUtil.isAllNotEmpty(bizFormTravelCostPageParam.getSortField(), bizFormTravelCostPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFormTravelCostPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFormTravelCostPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFormTravelCostPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizFormTravelCost::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFormTravelCostAddParam bizFormTravelCostAddParam) {
        BizFormTravelCost bizFormTravelCost = BeanUtil.toBean(bizFormTravelCostAddParam, BizFormTravelCost.class);
        this.save(bizFormTravelCost);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFormTravelCostEditParam bizFormTravelCostEditParam) {
        BizFormTravelCost bizFormTravelCost = this.queryEntity(bizFormTravelCostEditParam.getId());
        BeanUtil.copyProperties(bizFormTravelCostEditParam, bizFormTravelCost);
        this.updateById(bizFormTravelCost);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFormTravelCostIdParam> bizFormTravelCostIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizFormTravelCostIdParamList, BizFormTravelCostIdParam::getId));
    }

    @Override
    public BizFormTravelCost detail(BizFormTravelCostIdParam bizFormTravelCostIdParam) {
        return this.queryEntity(bizFormTravelCostIdParam.getId());
    }

    @Override
    public BizFormTravelCost queryEntity(String id) {
        BizFormTravelCost bizFormTravelCost = this.getById(id);
        if(ObjectUtil.isEmpty(bizFormTravelCost)) {
            throw new CommonException("费用报销（二期）- 差旅费用报销单 - 成本明细不存在，id值为：{}", id);
        }
        return bizFormTravelCost;
    }
}
