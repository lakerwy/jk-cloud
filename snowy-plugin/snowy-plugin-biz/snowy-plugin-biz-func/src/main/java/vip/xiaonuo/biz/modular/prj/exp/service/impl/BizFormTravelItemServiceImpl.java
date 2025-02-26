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
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormTravelItem;
import vip.xiaonuo.biz.modular.prj.exp.mapper.BizFormTravelItemMapper;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelItemAddParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelItemEditParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelItemIdParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormTravelItemPageParam;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormTravelItemService;

import java.util.List;

/**
 * 费用报销（二期）- 差旅费用报销单 - 报销明细Service接口实现类
 *
 * @author yong.ye
 * @date  2025/02/24 15:19
 **/
@Service
@DS("slave_1")
public class BizFormTravelItemServiceImpl extends ServiceImpl<BizFormTravelItemMapper, BizFormTravelItem> implements BizFormTravelItemService {

    @Override
    public Page<BizFormTravelItem> page(BizFormTravelItemPageParam bizFormTravelItemPageParam) {
        QueryWrapper<BizFormTravelItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizFormTravelItemPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(bizFormTravelItemPageParam.getEndOptDate())) {
            queryWrapper.lambda().between(BizFormTravelItem::getOptDate, bizFormTravelItemPageParam.getStartOptDate(), bizFormTravelItemPageParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelItemPageParam.getPointFrom())) {
            queryWrapper.lambda().like(BizFormTravelItem::getPointFrom, bizFormTravelItemPageParam.getPointFrom());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelItemPageParam.getPointTo())) {
            queryWrapper.lambda().like(BizFormTravelItem::getPointTo, bizFormTravelItemPageParam.getPointTo());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelItemPageParam.getGoType())) {
            queryWrapper.lambda().eq(BizFormTravelItem::getGoType, bizFormTravelItemPageParam.getGoType());
        }
        if(ObjectUtil.isAllNotEmpty(bizFormTravelItemPageParam.getSortField(), bizFormTravelItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFormTravelItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFormTravelItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFormTravelItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizFormTravelItem::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFormTravelItemAddParam bizFormTravelItemAddParam) {
        BizFormTravelItem bizFormTravelItem = BeanUtil.toBean(bizFormTravelItemAddParam, BizFormTravelItem.class);
        this.save(bizFormTravelItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFormTravelItemEditParam bizFormTravelItemEditParam) {
        BizFormTravelItem bizFormTravelItem = this.queryEntity(bizFormTravelItemEditParam.getId());
        BeanUtil.copyProperties(bizFormTravelItemEditParam, bizFormTravelItem);
        this.updateById(bizFormTravelItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFormTravelItemIdParam> bizFormTravelItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizFormTravelItemIdParamList, BizFormTravelItemIdParam::getId));
    }

    @Override
    public BizFormTravelItem detail(BizFormTravelItemIdParam bizFormTravelItemIdParam) {
        return this.queryEntity(bizFormTravelItemIdParam.getId());
    }

    @Override
    public BizFormTravelItem queryEntity(String id) {
        BizFormTravelItem bizFormTravelItem = this.getById(id);
        if(ObjectUtil.isEmpty(bizFormTravelItem)) {
            throw new CommonException("费用报销（二期）- 差旅费用报销单 - 报销明细不存在，id值为：{}", id);
        }
        return bizFormTravelItem;
    }
}
