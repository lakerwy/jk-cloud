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
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormFeeItem;
import vip.xiaonuo.biz.modular.prj.exp.mapper.BizFormFeeItemMapper;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeItemAddParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeItemEditParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeItemIdParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeItemPageParam;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormFeeItemService;

import java.util.List;

/**
 * 费用报销（二期）- 费用报销单 - 报销明细Service接口实现类
 *
 * @author yong.ye
 * @date  2025/02/24 15:16
 **/
@Service
@DS("slave_1")
public class BizFormFeeItemServiceImpl extends ServiceImpl<BizFormFeeItemMapper, BizFormFeeItem> implements BizFormFeeItemService {

    @Override
    public Page<BizFormFeeItem> page(BizFormFeeItemPageParam bizFormFeeItemPageParam) {
        QueryWrapper<BizFormFeeItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizFormFeeItemPageParam.getExpItem())) {
            queryWrapper.lambda().eq(BizFormFeeItem::getExpItem, bizFormFeeItemPageParam.getExpItem());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeeItemPageParam.getSecondExpItem())) {
            queryWrapper.lambda().eq(BizFormFeeItem::getSecondExpItem, bizFormFeeItemPageParam.getSecondExpItem());
        }
        if(ObjectUtil.isAllNotEmpty(bizFormFeeItemPageParam.getSortField(), bizFormFeeItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFormFeeItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFormFeeItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFormFeeItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizFormFeeItem::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFormFeeItemAddParam bizFormFeeItemAddParam) {
        BizFormFeeItem bizFormFeeItem = BeanUtil.toBean(bizFormFeeItemAddParam, BizFormFeeItem.class);
        this.save(bizFormFeeItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFormFeeItemEditParam bizFormFeeItemEditParam) {
        BizFormFeeItem bizFormFeeItem = this.queryEntity(bizFormFeeItemEditParam.getId());
        BeanUtil.copyProperties(bizFormFeeItemEditParam, bizFormFeeItem);
        this.updateById(bizFormFeeItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFormFeeItemIdParam> bizFormFeeItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizFormFeeItemIdParamList, BizFormFeeItemIdParam::getId));
    }

    @Override
    public BizFormFeeItem detail(BizFormFeeItemIdParam bizFormFeeItemIdParam) {
        return this.queryEntity(bizFormFeeItemIdParam.getId());
    }

    @Override
    public BizFormFeeItem queryEntity(String id) {
        BizFormFeeItem bizFormFeeItem = this.getById(id);
        if(ObjectUtil.isEmpty(bizFormFeeItem)) {
            throw new CommonException("费用报销（二期）- 费用报销单 - 报销明细不存在，id值为：{}", id);
        }
        return bizFormFeeItem;
    }
}
