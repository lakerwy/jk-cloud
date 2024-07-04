/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.oa.modular.attendance.shift.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Calendar;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarAddParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarEditParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarIdParam;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Calendar;
import vip.xiaonuo.oa.modular.attendance.shift.mapper.CalendarMapper;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarAddParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarEditParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarIdParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarPageParam;
import vip.xiaonuo.oa.modular.attendance.shift.service.CalendarService;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Calendar;
import vip.xiaonuo.oa.modular.attendance.shift.mapper.CalendarMapper;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarEditParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarIdParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarPageParam;
import vip.xiaonuo.oa.modular.attendance.shift.service.CalendarService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 日历表Service接口实现类
 *
 * @author mayike
 * @date  2024/06/15 12:59
 **/
@Service
public class CalendarServiceImpl extends ServiceImpl<CalendarMapper, Calendar> implements CalendarService {

    //@Value("${syncAddress}")
    private String syncAddress = "https://timor.tech/api/holiday/year/";

    @Override
    public Page<Calendar> page(CalendarPageParam calendarPageParam) {
        QueryWrapper<Calendar> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(calendarPageParam.getSortField(), calendarPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(calendarPageParam.getSortOrder());
            queryWrapper.orderBy(true, calendarPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(calendarPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Calendar::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CalendarAddParam calendarAddParam) {
        Calendar calendar = BeanUtil.toBean(calendarAddParam, Calendar.class);
        this.save(calendar);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CalendarEditParam calendarEditParam) {
        Calendar calendar = this.queryEntity(calendarEditParam.getId());
        BeanUtil.copyProperties(calendarEditParam, calendar);
        this.updateById(calendar);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CalendarIdParam> calendarIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(calendarIdParamList, CalendarIdParam::getId));
    }

    @Override
    public Calendar detail(CalendarIdParam calendarIdParam) {
        return this.queryEntity(calendarIdParam.getId());
    }

    @Override
    public Calendar queryEntity(String id) {
        Calendar calendar = this.getById(id);
        if(ObjectUtil.isEmpty(calendar)) {
            throw new CommonException("日历表不存在，id值为：{}", id);
        }
        return calendar;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void generateAndSaveCalendar(int year) {
        List<Calendar> calendars = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM-dd");
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        Map<String, Map<String, Object>> holidays = getHolidays(year);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            String dateString = date.format(formatter);
            String monthDateStr = date.format(monthFormatter);
            Calendar calendar = new Calendar();
            calendar.setCalendarDate(java.sql.Date.valueOf(date));
            calendar.setYear(date.getYear());
            calendar.setMonth(date.getMonthValue());
            calendar.setDayOfMonth(date.getDayOfMonth());
            calendar.setDayOfWeek(date.getDayOfWeek().getValue());
            calendar.setWeekOfYear(date.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR));
            calendar.setQuarter((date.getMonthValue() - 1) / 3 + 1);

            if (holidays.containsKey(monthDateStr)) {
                Map<String, Object> holidayInfo = holidays.get(monthDateStr);
                boolean isHoliday = (boolean) holidayInfo.get("holiday");
                calendar.setIsWorkday(!isHoliday);
                calendar.setHolidayName((String) holidayInfo.get("name"));
            } else {
                boolean isWeekend = date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7;
                calendar.setIsWorkday(!isWeekend);
            }
            calendars.add(calendar);
        }

        saveBatch(calendars);

    }

    public Map<String, Map<String, Object>>  getHolidays(int year){
        String url = syncAddress;
        HttpResponse response = HttpUtil.createGet(url+year+"?type=Y&week=Y").execute();
        String body = response.body();
        JSONObject jsonObject = JSONUtil.parseObj(body);
        int code = jsonObject.getInt("code");
        if(code == 0){
            JSONObject holiday = jsonObject.getJSONObject("holiday");
            if(ObjectUtil.isNotEmpty(holiday)){
                // 将 JSONObject 转换为 Map<String, Map<String, Object>>
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    return objectMapper.readValue(holiday.toString(), new TypeReference<Map<String, Map<String, Object>>>() {});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return java.util.Collections.emptyMap();
    }
}
