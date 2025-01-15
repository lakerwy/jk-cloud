package vip.xiaonuo.biz.modular.prjFee.service.impl;

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
import vip.xiaonuo.biz.modular.prjFee.entity.PrjFeeAllotmentItem;
import vip.xiaonuo.biz.modular.prjFee.mapper.PrjFeeAllotmentItemMapper;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentItemAddParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentItemEditParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentItemIdParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentItemPageParam;
import vip.xiaonuo.biz.modular.prjFee.service.PrjFeeAllotmentItemService;

import java.util.List;

/**
 * 工程款拨付 - 款项拨付Service接口实现类
 *
 * @author pzh
 * @date  2025/01/15 09:33
 **/
@Service
public class PrjFeeAllotmentItemServiceImpl extends ServiceImpl<PrjFeeAllotmentItemMapper, PrjFeeAllotmentItem> implements PrjFeeAllotmentItemService {

    @Override
    public Page<PrjFeeAllotmentItem> page(PrjFeeAllotmentItemPageParam prjFeeAllotmentItemPageParam) {
        QueryWrapper<PrjFeeAllotmentItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(prjFeeAllotmentItemPageParam.getSortField(), prjFeeAllotmentItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(prjFeeAllotmentItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, prjFeeAllotmentItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(prjFeeAllotmentItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(PrjFeeAllotmentItem::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PrjFeeAllotmentItemAddParam prjFeeAllotmentItemAddParam) {
        PrjFeeAllotmentItem prjFeeAllotmentItem = BeanUtil.toBean(prjFeeAllotmentItemAddParam, PrjFeeAllotmentItem.class);
        this.save(prjFeeAllotmentItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PrjFeeAllotmentItemEditParam prjFeeAllotmentItemEditParam) {
        PrjFeeAllotmentItem prjFeeAllotmentItem = this.queryEntity(prjFeeAllotmentItemEditParam.getId());
        BeanUtil.copyProperties(prjFeeAllotmentItemEditParam, prjFeeAllotmentItem);
        this.updateById(prjFeeAllotmentItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PrjFeeAllotmentItemIdParam> prjFeeAllotmentItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(prjFeeAllotmentItemIdParamList, PrjFeeAllotmentItemIdParam::getId));
    }

    @Override
    public PrjFeeAllotmentItem detail(PrjFeeAllotmentItemIdParam prjFeeAllotmentItemIdParam) {
        return this.queryEntity(prjFeeAllotmentItemIdParam.getId());
    }

    @Override
    public PrjFeeAllotmentItem queryEntity(String id) {
        PrjFeeAllotmentItem prjFeeAllotmentItem = this.getById(id);
        if(ObjectUtil.isEmpty(prjFeeAllotmentItem)) {
            throw new CommonException("工程款拨付 - 款项拨付不存在，id值为：{}", id);
        }
        return prjFeeAllotmentItem;
    }
}
