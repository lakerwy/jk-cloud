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
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormFeeCost;
import vip.xiaonuo.biz.modular.prj.exp.mapper.BizFormFeeCostMapper;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeCostAddParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeCostEditParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeCostIdParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeCostPageParam;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormFeeCostService;

import java.util.List;

/**
 * 费用报销（二期）- 费用报销单 - 成本明细Service接口实现类
 *
 * @author yong.ye
 * @date  2025/02/24 15:15
 **/
@Service
@DS("slave_1")
public class BizFormFeeCostServiceImpl extends ServiceImpl<BizFormFeeCostMapper, BizFormFeeCost> implements BizFormFeeCostService {

    @Override
    public Page<BizFormFeeCost> page(BizFormFeeCostPageParam bizFormFeeCostPageParam) {
        QueryWrapper<BizFormFeeCost> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizFormFeeCostPageParam.getHtId())) {
            queryWrapper.lambda().eq(BizFormFeeCost::getHtId, bizFormFeeCostPageParam.getHtId());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeeCostPageParam.getHtName())) {
            queryWrapper.lambda().like(BizFormFeeCost::getHtName, bizFormFeeCostPageParam.getHtName());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeeCostPageParam.getHtSn())) {
            queryWrapper.lambda().like(BizFormFeeCost::getHtSn, bizFormFeeCostPageParam.getHtSn());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeeCostPageParam.getExpItem())) {
            queryWrapper.lambda().eq(BizFormFeeCost::getExpItem, bizFormFeeCostPageParam.getExpItem());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeeCostPageParam.getSecondExpItem())) {
            queryWrapper.lambda().eq(BizFormFeeCost::getSecondExpItem, bizFormFeeCostPageParam.getSecondExpItem());
        }
        if(ObjectUtil.isAllNotEmpty(bizFormFeeCostPageParam.getSortField(), bizFormFeeCostPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFormFeeCostPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFormFeeCostPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFormFeeCostPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizFormFeeCost::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFormFeeCostAddParam bizFormFeeCostAddParam) {
        BizFormFeeCost bizFormFeeCost = BeanUtil.toBean(bizFormFeeCostAddParam, BizFormFeeCost.class);
        this.save(bizFormFeeCost);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFormFeeCostEditParam bizFormFeeCostEditParam) {
        BizFormFeeCost bizFormFeeCost = this.queryEntity(bizFormFeeCostEditParam.getId());
        BeanUtil.copyProperties(bizFormFeeCostEditParam, bizFormFeeCost);
        this.updateById(bizFormFeeCost);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFormFeeCostIdParam> bizFormFeeCostIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizFormFeeCostIdParamList, BizFormFeeCostIdParam::getId));
    }

    @Override
    public BizFormFeeCost detail(BizFormFeeCostIdParam bizFormFeeCostIdParam) {
        return this.queryEntity(bizFormFeeCostIdParam.getId());
    }

    @Override
    public BizFormFeeCost queryEntity(String id) {
        BizFormFeeCost bizFormFeeCost = this.getById(id);
        if(ObjectUtil.isEmpty(bizFormFeeCost)) {
            throw new CommonException("费用报销（二期）- 费用报销单 - 成本明细不存在，id值为：{}", id);
        }
        return bizFormFeeCost;
    }
}
