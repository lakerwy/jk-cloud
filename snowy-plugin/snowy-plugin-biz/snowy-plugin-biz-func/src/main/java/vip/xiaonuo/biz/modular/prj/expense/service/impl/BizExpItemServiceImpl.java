package vip.xiaonuo.biz.modular.prj.expense.service.impl;

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
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpItem;
import vip.xiaonuo.biz.modular.prj.expense.mapper.BizExpItemMapper;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpItemAddParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpItemEditParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpItemIdParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpItemPageParam;
import vip.xiaonuo.biz.modular.prj.expense.service.BizExpItemService;

import java.util.List;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 费用明细Service接口实现类
 *
 * @author yong.ye
 * @date  2024/07/30 15:31
 **/

@DS("slave_1")
@Service
public class BizExpItemServiceImpl extends ServiceImpl<BizExpItemMapper, BizExpItem> implements BizExpItemService {

    @Override
    public Page<BizExpItem> page(BizExpItemPageParam bizExpItemPageParam) {
        QueryWrapper<BizExpItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizExpItemPageParam.getItemId())) {
            queryWrapper.lambda().eq(BizExpItem::getItemId, bizExpItemPageParam.getItemId());
        }
        if(ObjectUtil.isNotEmpty(bizExpItemPageParam.getItem())) {
            queryWrapper.lambda().like(BizExpItem::getItem, bizExpItemPageParam.getItem());
        }
        if(ObjectUtil.isAllNotEmpty(bizExpItemPageParam.getSortField(), bizExpItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizExpItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizExpItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizExpItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizExpItem::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizExpItemAddParam bizExpItemAddParam) {
        BizExpItem bizExpItem = BeanUtil.toBean(bizExpItemAddParam, BizExpItem.class);
        this.save(bizExpItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizExpItemEditParam bizExpItemEditParam) {
        BizExpItem bizExpItem = this.queryEntity(bizExpItemEditParam.getId());
        BeanUtil.copyProperties(bizExpItemEditParam, bizExpItem);
        this.updateById(bizExpItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizExpItemIdParam> bizExpItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizExpItemIdParamList, BizExpItemIdParam::getId));
    }

    @Override
    public BizExpItem detail(BizExpItemIdParam bizExpItemIdParam) {
        return this.queryEntity(bizExpItemIdParam.getId());
    }

    @Override
    public BizExpItem queryEntity(String id) {
        BizExpItem bizExpItem = this.getById(id);
        if(ObjectUtil.isEmpty(bizExpItem)) {
            throw new CommonException("财务管理 - 费用报销|差旅费用报销 - 费用明细不存在，id值为：{}", id);
        }
        return bizExpItem;
    }
}
