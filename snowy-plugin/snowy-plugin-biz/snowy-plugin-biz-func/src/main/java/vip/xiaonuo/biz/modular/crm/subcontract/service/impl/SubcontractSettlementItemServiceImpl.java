package vip.xiaonuo.biz.modular.crm.subcontract.service.impl;

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
import vip.xiaonuo.biz.modular.crm.subcontract.entity.SubcontractSettlementItem;
import vip.xiaonuo.biz.modular.crm.subcontract.mapper.SubcontractSettlementItemMapper;
import vip.xiaonuo.biz.modular.crm.subcontract.param.SubcontractSettlementItemAddParam;
import vip.xiaonuo.biz.modular.crm.subcontract.param.SubcontractSettlementItemEditParam;
import vip.xiaonuo.biz.modular.crm.subcontract.param.SubcontractSettlementItemIdParam;
import vip.xiaonuo.biz.modular.crm.subcontract.param.SubcontractSettlementItemPageParam;
import vip.xiaonuo.biz.modular.crm.subcontract.service.SubcontractSettlementItemService;

import java.util.List;

/**
 * 分包合同结算明细Service接口实现类
 *
 * @author pzh
 * @date  2025/04/30 11:20
 **/
@Service
public class SubcontractSettlementItemServiceImpl extends ServiceImpl<SubcontractSettlementItemMapper, SubcontractSettlementItem> implements SubcontractSettlementItemService {

    @Override
    public Page<SubcontractSettlementItem> page(SubcontractSettlementItemPageParam subcontractSettlementItemPageParam) {
        QueryWrapper<SubcontractSettlementItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(subcontractSettlementItemPageParam.getSortField(), subcontractSettlementItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subcontractSettlementItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, subcontractSettlementItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subcontractSettlementItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SubcontractSettlementItem::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubcontractSettlementItemAddParam subcontractSettlementItemAddParam) {
        SubcontractSettlementItem subcontractSettlementItem = BeanUtil.toBean(subcontractSettlementItemAddParam, SubcontractSettlementItem.class);
        this.save(subcontractSettlementItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubcontractSettlementItemEditParam subcontractSettlementItemEditParam) {
        SubcontractSettlementItem subcontractSettlementItem = this.queryEntity(subcontractSettlementItemEditParam.getId());
        BeanUtil.copyProperties(subcontractSettlementItemEditParam, subcontractSettlementItem);
        this.updateById(subcontractSettlementItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubcontractSettlementItemIdParam> subcontractSettlementItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(subcontractSettlementItemIdParamList, SubcontractSettlementItemIdParam::getId));
    }

    @Override
    public SubcontractSettlementItem detail(SubcontractSettlementItemIdParam subcontractSettlementItemIdParam) {
        return this.queryEntity(subcontractSettlementItemIdParam.getId());
    }

    @Override
    public SubcontractSettlementItem queryEntity(String id) {
        SubcontractSettlementItem subcontractSettlementItem = this.getById(id);
        if(ObjectUtil.isEmpty(subcontractSettlementItem)) {
            throw new CommonException("分包合同结算明细不存在，id值为：{}", id);
        }
        return subcontractSettlementItem;
    }
}
