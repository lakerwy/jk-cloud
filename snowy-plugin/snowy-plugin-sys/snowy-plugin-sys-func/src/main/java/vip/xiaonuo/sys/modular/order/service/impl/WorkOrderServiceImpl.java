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
import vip.xiaonuo.sys.modular.order.entity.WorkOrder;
import vip.xiaonuo.sys.modular.order.mapper.WorkOrderMapper;
import vip.xiaonuo.sys.modular.order.param.WorkOrderAddParam;
import vip.xiaonuo.sys.modular.order.param.WorkOrderEditParam;
import vip.xiaonuo.sys.modular.order.param.WorkOrderIdParam;
import vip.xiaonuo.sys.modular.order.param.WorkOrderPageParam;
import vip.xiaonuo.sys.modular.order.service.WorkOrderService;

import java.util.Date;
import java.util.List;

/**
 * 事件处理单Service接口实现类
 *
 * @author yong.ye
 * @date  2025/01/13 15:34
 **/
@Service
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements WorkOrderService {

    @Override
    public Page<WorkOrder> page(WorkOrderPageParam workOrderPageParam) {
        QueryWrapper<WorkOrder> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(workOrderPageParam.getEventNo())) {
            queryWrapper.lambda().like(WorkOrder::getEventNo, workOrderPageParam.getEventNo());
        }
        if(ObjectUtil.isNotEmpty(workOrderPageParam.getEventType())) {
            queryWrapper.lambda().eq(WorkOrder::getEventType, workOrderPageParam.getEventType());
        }
        if(ObjectUtil.isNotEmpty(workOrderPageParam.getEventAffect())) {
            queryWrapper.lambda().eq(WorkOrder::getEventAffect, workOrderPageParam.getEventAffect());
        }
        if(ObjectUtil.isNotEmpty(workOrderPageParam.getCtName())) {
            queryWrapper.lambda().like(WorkOrder::getCtName, workOrderPageParam.getCtName());
        }
        if(ObjectUtil.isNotEmpty(workOrderPageParam.getEventPriority())) {
            queryWrapper.lambda().eq(WorkOrder::getEventPriority, workOrderPageParam.getEventPriority());
        }
        if(ObjectUtil.isNotEmpty(workOrderPageParam.getEventLevel())) {
            queryWrapper.lambda().eq(WorkOrder::getEventLevel, workOrderPageParam.getEventLevel());
        }
        if(ObjectUtil.isNotEmpty(workOrderPageParam.getPrjName())) {
            queryWrapper.lambda().like(WorkOrder::getPrjName, workOrderPageParam.getPrjName());
        }
        if(ObjectUtil.isNotEmpty(workOrderPageParam.getEventSender())) {
            queryWrapper.lambda().like(WorkOrder::getEventSender, workOrderPageParam.getEventSender());
        }
        if(ObjectUtil.isNotEmpty(workOrderPageParam.getEventDealer())) {
            queryWrapper.lambda().like(WorkOrder::getEventDealer, workOrderPageParam.getEventDealer());
        }
        if(ObjectUtil.isNotEmpty(workOrderPageParam.getStartEventTime()) && ObjectUtil.isNotEmpty(workOrderPageParam.getEndEventTime())) {
            queryWrapper.lambda().between(WorkOrder::getEventTime, workOrderPageParam.getStartEventTime(), workOrderPageParam.getEndEventTime());
        }
        if(ObjectUtil.isNotEmpty(workOrderPageParam.getEventTitle())) {
            queryWrapper.lambda().like(WorkOrder::getEventTitle, workOrderPageParam.getEventTitle());
        }
        if(ObjectUtil.isNotEmpty(workOrderPageParam.getStartDealTime()) && ObjectUtil.isNotEmpty(workOrderPageParam.getEndDealTime())) {
            queryWrapper.lambda().between(WorkOrder::getDealTime, workOrderPageParam.getStartDealTime(), workOrderPageParam.getEndDealTime());
        }
        if(ObjectUtil.isNotEmpty(workOrderPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(WorkOrder::getEventNo,workOrderPageParam.getSearchKey())
                    .or().like(WorkOrder::getCtName,workOrderPageParam.getSearchKey())
                    .or().like(WorkOrder::getPrjName,workOrderPageParam.getSearchKey())
                    .or().like(WorkOrder::getEventTitle,workOrderPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(workOrderPageParam.getSortField(), workOrderPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(workOrderPageParam.getSortOrder());
            queryWrapper.orderBy(true, workOrderPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(workOrderPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(WorkOrder::getEventNo);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(WorkOrderAddParam workOrderAddParam) {
        WorkOrder workOrder = BeanUtil.toBean(workOrderAddParam, WorkOrder.class);
        workOrder.setEventNo("SJ-"+ DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN));
        this.save(workOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(WorkOrderEditParam workOrderEditParam) {
        WorkOrder workOrder = this.queryEntity(workOrderEditParam.getId());
        BeanUtil.copyProperties(workOrderEditParam, workOrder);
        this.updateById(workOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<WorkOrderIdParam> workOrderIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(workOrderIdParamList, WorkOrderIdParam::getId));
    }

    @Override
    public WorkOrder detail(WorkOrderIdParam workOrderIdParam) {
        return this.queryEntity(workOrderIdParam.getId());
    }

    @Override
    public WorkOrder queryEntity(String id) {
        WorkOrder workOrder = this.getById(id);
        if(ObjectUtil.isEmpty(workOrder)) {
            throw new CommonException("事件处理单不存在，id值为：{}", id);
        }
        return workOrder;
    }
}
