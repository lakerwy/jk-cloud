package vip.xiaonuo.biz.modular.crm.prjValue.service.impl;

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
import vip.xiaonuo.biz.modular.crm.prjValue.entity.PrjValueAllocationItem;
import vip.xiaonuo.biz.modular.crm.prjValue.mapper.PrjValueAllocationItemMapper;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationItemAddParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationItemEditParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationItemIdParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAllocationItemPageParam;
import vip.xiaonuo.biz.modular.crm.prjValue.service.PrjValueAllocationItemService;

import java.util.List;

/**
 * 项目产值划分明细Service接口实现类
 *
 * @author pzh
 * @date  2025/06/04 16:48
 **/
@Service
@DS("slave_1")
public class PrjValueAllocationItemServiceImpl extends ServiceImpl<PrjValueAllocationItemMapper, PrjValueAllocationItem> implements PrjValueAllocationItemService {

    @Override
    public Page<PrjValueAllocationItem> page(PrjValueAllocationItemPageParam prjValueAllocationItemPageParam) {
        QueryWrapper<PrjValueAllocationItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(prjValueAllocationItemPageParam.getSortField(), prjValueAllocationItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(prjValueAllocationItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, prjValueAllocationItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(prjValueAllocationItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PrjValueAllocationItem::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PrjValueAllocationItemAddParam prjValueAllocationItemAddParam) {
        PrjValueAllocationItem prjValueAllocationItem = BeanUtil.toBean(prjValueAllocationItemAddParam, PrjValueAllocationItem.class);
        this.save(prjValueAllocationItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PrjValueAllocationItemEditParam prjValueAllocationItemEditParam) {
        PrjValueAllocationItem prjValueAllocationItem = this.queryEntity(prjValueAllocationItemEditParam.getId());
        BeanUtil.copyProperties(prjValueAllocationItemEditParam, prjValueAllocationItem);
        this.updateById(prjValueAllocationItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PrjValueAllocationItemIdParam> prjValueAllocationItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(prjValueAllocationItemIdParamList, PrjValueAllocationItemIdParam::getId));
    }

    @Override
    public PrjValueAllocationItem detail(PrjValueAllocationItemIdParam prjValueAllocationItemIdParam) {
        return this.queryEntity(prjValueAllocationItemIdParam.getId());
    }

    @Override
    public PrjValueAllocationItem queryEntity(String id) {
        PrjValueAllocationItem prjValueAllocationItem = this.getById(id);
        if(ObjectUtil.isEmpty(prjValueAllocationItem)) {
            throw new CommonException("项目产值划分明细不存在，id值为：{}", id);
        }
        return prjValueAllocationItem;
    }
}
