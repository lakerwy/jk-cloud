package vip.xiaonuo.sys.modular.hr.goods.service.impl;

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
import vip.xiaonuo.sys.modular.hr.goods.entity.GoodsItem;
import vip.xiaonuo.sys.modular.hr.goods.mapper.GoodsItemMapper;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsItemAddParam;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsItemEditParam;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsItemIdParam;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsItemPageParam;
import vip.xiaonuo.sys.modular.hr.goods.service.GoodsItemService;

import java.util.List;

/**
 * 物资领用申请（附表） - 物资清单+退回清单 Service接口实现类
 *
 * @author yy
 * @date  2025/05/23 14:35
 **/
@Service
public class GoodsItemServiceImpl extends ServiceImpl<GoodsItemMapper, GoodsItem> implements GoodsItemService {

    @Override
    public Page<GoodsItem> page(GoodsItemPageParam goodsItemPageParam) {
        QueryWrapper<GoodsItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(goodsItemPageParam.getType())) {
            queryWrapper.lambda().eq(GoodsItem::getType, goodsItemPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(goodsItemPageParam.getGoodName())) {
            queryWrapper.lambda().like(GoodsItem::getGoodName, goodsItemPageParam.getGoodName());
        }
        if(ObjectUtil.isAllNotEmpty(goodsItemPageParam.getSortField(), goodsItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(goodsItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, goodsItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(goodsItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(GoodsItem::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(GoodsItemAddParam goodsItemAddParam) {
        GoodsItem goodsItem = BeanUtil.toBean(goodsItemAddParam, GoodsItem.class);
        this.save(goodsItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(GoodsItemEditParam goodsItemEditParam) {
        GoodsItem goodsItem = this.queryEntity(goodsItemEditParam.getId());
        BeanUtil.copyProperties(goodsItemEditParam, goodsItem);
        this.updateById(goodsItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<GoodsItemIdParam> goodsItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(goodsItemIdParamList, GoodsItemIdParam::getId));
    }

    @Override
    public GoodsItem detail(GoodsItemIdParam goodsItemIdParam) {
        return this.queryEntity(goodsItemIdParam.getId());
    }

    @Override
    public GoodsItem queryEntity(String id) {
        GoodsItem goodsItem = this.getById(id);
        if(ObjectUtil.isEmpty(goodsItem)) {
            throw new CommonException("物资领用申请（附表） - 物资清单+退回清单 不存在，id值为：{}", id);
        }
        return goodsItem;
    }
}
