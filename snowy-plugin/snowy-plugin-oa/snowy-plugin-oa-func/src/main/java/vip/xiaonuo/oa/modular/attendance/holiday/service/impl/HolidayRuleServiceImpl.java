
package vip.xiaonuo.oa.modular.attendance.holiday.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.oa.core.event.Event;
import vip.xiaonuo.oa.core.event.EventPublisher;
import vip.xiaonuo.oa.core.event.holiday.HolidayEvent;
import vip.xiaonuo.oa.modular.attendance.holiday.entity.HolidayBalance;
import vip.xiaonuo.oa.modular.attendance.holiday.entity.HolidayRule;
import vip.xiaonuo.oa.modular.attendance.holiday.mapper.HolidayRuleMapper;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayRuleAddParam;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayRuleEditParam;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayRuleIdParam;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayRulePageParam;
import vip.xiaonuo.oa.modular.attendance.holiday.service.HolidayBalanceService;
import vip.xiaonuo.oa.modular.attendance.holiday.service.HolidayRuleService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 假期类型配置表Service接口实现类
 *
 * @author mayike
 * @date  2024/07/05 09:19
 **/
@Service
public class HolidayRuleServiceImpl extends ServiceImpl<HolidayRuleMapper, HolidayRule> implements HolidayRuleService {

    @Resource
    private EventPublisher eventPublisher;

    @Resource
    private HolidayBalanceService holidayBalanceService;

    @Override
    public Page<HolidayRule> page(HolidayRulePageParam holidayRulePageParam) {
        QueryWrapper<HolidayRule> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(holidayRulePageParam.getSortField(), holidayRulePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(holidayRulePageParam.getSortOrder());
            queryWrapper.orderBy(true, holidayRulePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(holidayRulePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HolidayRule::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HolidayRuleAddParam holidayRuleAddParam) {
        HolidayRule holidayRule = BeanUtil.toBean(holidayRuleAddParam, HolidayRule.class);
        this.save(holidayRule);
        //添加假期类型以后,要初始化每个用户的该假期数据
        handleEvent(holidayRule.getId());
    }

    /**
     *
     */
    @Async
    public void handleEvent(String id){
        List<String> userIds = holidayBalanceService.getUserIds();
        HolidayEvent holidayEvent = new HolidayEvent();
        holidayEvent.setHolidayRuleId(id);
        holidayEvent.setUserIds(userIds);
        Event<HolidayEvent> event = new Event<>(this, holidayEvent);
        eventPublisher.publishEvent(event);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HolidayRuleEditParam holidayRuleEditParam) {
        HolidayRule holidayRule = this.queryEntity(holidayRuleEditParam.getId());
        BeanUtil.copyProperties(holidayRuleEditParam, holidayRule);
        this.updateById(holidayRule);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HolidayRuleIdParam> holidayRuleIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(holidayRuleIdParamList, HolidayRuleIdParam::getId));
        //同时删除所有员工的该假期数据
        List<String> collect = holidayRuleIdParamList.stream().map(HolidayRuleIdParam::getId).collect(Collectors.toList());
        holidayBalanceService.remove(new LambdaQueryWrapper<HolidayBalance>().in(HolidayBalance::getHolidayTypeId,collect));
    }

    @Override
    public HolidayRule detail(HolidayRuleIdParam holidayRuleIdParam) {
        return this.queryEntity(holidayRuleIdParam.getId());
    }

    @Override
    public HolidayRule queryEntity(String id) {
        HolidayRule holidayRule = this.getById(id);
        if(ObjectUtil.isEmpty(holidayRule)) {
            throw new CommonException("假期类型配置表不存在，id值为：{}", id);
        }
        return holidayRule;
    }

    @Override
    public void initUserHolidayData(int year, String userId) {
        List<HolidayRule> rules = this.list();
        List<HolidayBalance> balances = new ArrayList<>();
        rules.forEach(holidayRule -> {
            HolidayBalance holidayBalance = new HolidayBalance();
            holidayBalance.setUserId(userId);
            holidayBalance.setHolidayTypeId(holidayRule.getId());
            holidayBalance.setBalance(BigDecimal.valueOf(-1));
            holidayBalance.setYear(year);
            balances.add(holidayBalance);
        });
        if(CollUtil.isNotEmpty(balances)){
            holidayBalanceService.saveBatch(balances);
        }
    }
}
