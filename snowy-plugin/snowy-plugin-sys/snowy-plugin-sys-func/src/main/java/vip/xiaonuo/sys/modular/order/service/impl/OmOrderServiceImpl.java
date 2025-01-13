package vip.xiaonuo.sys.modular.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
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
import vip.xiaonuo.sys.modular.order.entity.OmOrder;
import vip.xiaonuo.sys.modular.order.mapper.OmOrderMapper;
import vip.xiaonuo.sys.modular.order.param.OmOrderAddParam;
import vip.xiaonuo.sys.modular.order.param.OmOrderEditParam;
import vip.xiaonuo.sys.modular.order.param.OmOrderIdParam;
import vip.xiaonuo.sys.modular.order.param.OmOrderPageParam;
import vip.xiaonuo.sys.modular.order.service.OmOrderService;

import java.util.Date;
import java.util.List;

/**
 * 运维工单Service接口实现类
 *
 * @author yong.ye
 * @date  2025/01/13 16:13
 **/
@Service
public class OmOrderServiceImpl extends ServiceImpl<OmOrderMapper, OmOrder> implements OmOrderService {

    @Override
    public Page<OmOrder> page(OmOrderPageParam omOrderPageParam) {
        QueryWrapper<OmOrder> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(omOrderPageParam.getOmNo())) {
            queryWrapper.lambda().like(OmOrder::getOmNo, omOrderPageParam.getOmNo());
        }
        if(ObjectUtil.isNotEmpty(omOrderPageParam.getDept())) {
            queryWrapper.lambda().like(OmOrder::getDept, omOrderPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(omOrderPageParam.getOmType())) {
            queryWrapper.lambda().eq(OmOrder::getOmType, omOrderPageParam.getOmType());
        }
        if(ObjectUtil.isNotEmpty(omOrderPageParam.getSender())) {
            queryWrapper.lambda().like(OmOrder::getSender, omOrderPageParam.getSender());
        }
        if(ObjectUtil.isNotEmpty(omOrderPageParam.getContact())) {
            queryWrapper.lambda().like(OmOrder::getContact, omOrderPageParam.getContact());
        }
        if(ObjectUtil.isNotEmpty(omOrderPageParam.getContent())) {
            queryWrapper.lambda().like(OmOrder::getContent, omOrderPageParam.getContent());
        }
        if(ObjectUtil.isNotEmpty(omOrderPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(OmOrder::getTitle,omOrderPageParam.getSearchKey())
                    .or().like(OmOrder::getOmNo,omOrderPageParam.getSearchKey())
                    .or().like(OmOrder::getContent,omOrderPageParam.getSearchKey())
                    .or().like(OmOrder::getDept,omOrderPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(omOrderPageParam.getSortField(), omOrderPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(omOrderPageParam.getSortOrder());
            queryWrapper.orderBy(true, omOrderPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(omOrderPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(OmOrder::getOmNo);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(OmOrderAddParam omOrderAddParam) {
        OmOrder omOrder = BeanUtil.toBean(omOrderAddParam, OmOrder.class);
        if(ObjectUtil.isEmpty(omOrder.getOmNo())) {
            omOrder.setOmNo("YW-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN));
        }
        this.save(omOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(OmOrderEditParam omOrderEditParam) {
        OmOrder omOrder = this.queryEntity(omOrderEditParam.getId());
        BeanUtil.copyProperties(omOrderEditParam, omOrder);
        this.updateById(omOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<OmOrderIdParam> omOrderIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(omOrderIdParamList, OmOrderIdParam::getId));
    }

    @Override
    public OmOrder detail(OmOrderIdParam omOrderIdParam) {
        return this.queryEntity(omOrderIdParam.getId());
    }

    @Override
    public OmOrder queryEntity(String id) {
        OmOrder omOrder = this.getById(id);
        if(ObjectUtil.isEmpty(omOrder)) {
            throw new CommonException("运维工单不存在，id值为：{}", id);
        }
        return omOrder;
    }
}
