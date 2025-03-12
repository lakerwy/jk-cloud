package vip.xiaonuo.sys.modular.hr.sub.service.impl;

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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFilingItem;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubFilingItemMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFilingItemAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFilingItemEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFilingItemIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubFilingItemPageParam;
import vip.xiaonuo.sys.modular.hr.sub.service.SubFilingItemService;

import java.util.List;

/**
 * 分包单位备案明细Service接口实现类
 *
 * @author pzh
 * @date  2025/03/11 14:32
 **/
@Service
public class SubFilingItemServiceImpl extends ServiceImpl<SubFilingItemMapper, SubFilingItem> implements SubFilingItemService {

    @Override
    public Page<SubFilingItem> page(SubFilingItemPageParam subFilingItemPageParam) {
        QueryWrapper<SubFilingItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(subFilingItemPageParam.getSortField(), subFilingItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subFilingItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, subFilingItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subFilingItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SubFilingItem::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubFilingItemAddParam subFilingItemAddParam) {
        SubFilingItem subFilingItem = BeanUtil.toBean(subFilingItemAddParam, SubFilingItem.class);
        this.save(subFilingItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubFilingItemEditParam subFilingItemEditParam) {
        SubFilingItem subFilingItem = this.queryEntity(subFilingItemEditParam.getId());
        BeanUtil.copyProperties(subFilingItemEditParam, subFilingItem);
        this.updateById(subFilingItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubFilingItemIdParam> subFilingItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(subFilingItemIdParamList, SubFilingItemIdParam::getId));
    }

    @Override
    public SubFilingItem detail(SubFilingItemIdParam subFilingItemIdParam) {
        return this.queryEntity(subFilingItemIdParam.getId());
    }

    @Override
    public SubFilingItem queryEntity(String id) {
        SubFilingItem subFilingItem = this.getById(id);
        if(ObjectUtil.isEmpty(subFilingItem)) {
            throw new CommonException("分包单位备案明细不存在，id值为：{}", id);
        }
        return subFilingItem;
    }
}
