package vip.xiaonuo.biz.modular.prj.por.service.impl;

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
import vip.xiaonuo.biz.modular.prj.por.entity.PorSiteFeeItem;
import vip.xiaonuo.biz.modular.prj.por.mapper.PorSiteFeeItemMapper;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeItemAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeItemEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeItemIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeItemPageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorSiteFeeItemService;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细Service接口实现类
 *
 * @author yy
 * @date  2025/06/03 09:47
 **/
@Service
@DS("slave_1")
public class PorSiteFeeItemServiceImpl extends ServiceImpl<PorSiteFeeItemMapper, PorSiteFeeItem> implements PorSiteFeeItemService {

    @Override
    public Page<PorSiteFeeItem> page(PorSiteFeeItemPageParam porSiteFeeItemPageParam) {
        QueryWrapper<PorSiteFeeItem> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(porSiteFeeItemPageParam.getPorSiteFeeId())) {
            queryWrapper.lambda().eq(PorSiteFeeItem::getPorSiteFeeId, porSiteFeeItemPageParam.getPorSiteFeeId());
        }
        if(ObjectUtil.isNotEmpty(porSiteFeeItemPageParam.getFeeMonth())) {
            queryWrapper.lambda().like(PorSiteFeeItem::getFeeMonth, porSiteFeeItemPageParam.getFeeMonth());
        }
        if(ObjectUtil.isNotEmpty(porSiteFeeItemPageParam.getFeeType())) {
            queryWrapper.lambda().eq(PorSiteFeeItem::getFeeType, porSiteFeeItemPageParam.getFeeType());
        }
        if(ObjectUtil.isAllNotEmpty(porSiteFeeItemPageParam.getSortField(), porSiteFeeItemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porSiteFeeItemPageParam.getSortOrder());
            queryWrapper.orderBy(true, porSiteFeeItemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porSiteFeeItemPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PorSiteFeeItem::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PorSiteFeeItemAddParam porSiteFeeItemAddParam) {
        PorSiteFeeItem porSiteFeeItem = BeanUtil.toBean(porSiteFeeItemAddParam, PorSiteFeeItem.class);
        this.save(porSiteFeeItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PorSiteFeeItemEditParam porSiteFeeItemEditParam) {
        PorSiteFeeItem porSiteFeeItem = this.queryEntity(porSiteFeeItemEditParam.getId());
        BeanUtil.copyProperties(porSiteFeeItemEditParam, porSiteFeeItem);
        this.updateById(porSiteFeeItem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PorSiteFeeItemIdParam> porSiteFeeItemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(porSiteFeeItemIdParamList, PorSiteFeeItemIdParam::getId));
    }

    @Override
    public PorSiteFeeItem detail(PorSiteFeeItemIdParam porSiteFeeItemIdParam) {
        return this.queryEntity(porSiteFeeItemIdParam.getId());
    }

    @Override
    public PorSiteFeeItem queryEntity(String id) {
        PorSiteFeeItem porSiteFeeItem = this.getById(id);
        if(ObjectUtil.isEmpty(porSiteFeeItem)) {
            throw new CommonException("项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细不存在，id值为：{}", id);
        }
        return porSiteFeeItem;
    }
}
