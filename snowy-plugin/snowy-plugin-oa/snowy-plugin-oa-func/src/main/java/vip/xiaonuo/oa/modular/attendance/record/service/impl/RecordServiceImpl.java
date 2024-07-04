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
package vip.xiaonuo.oa.modular.attendance.record.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.oa.modular.attendance.group.entity.GroupWorkDay;
import vip.xiaonuo.oa.modular.attendance.record.constants.RecordConstant;
import vip.xiaonuo.oa.modular.attendance.record.mapper.RecordMapper;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordAddParam;
import vip.xiaonuo.oa.modular.attendance.record.service.RecordService;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Rule;
import vip.xiaonuo.oa.modular.attendance.shift.service.ShiftService;
import vip.xiaonuo.oa.modular.attendance.group.constants.GroupConstant;
import vip.xiaonuo.oa.modular.attendance.group.entity.Group;
import vip.xiaonuo.oa.modular.attendance.group.service.GroupService;
import vip.xiaonuo.oa.modular.attendance.record.entity.Record;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordEditParam;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordIdParam;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordPageParam;
import vip.xiaonuo.oa.modular.attendance.record.vo.RecordPreVo;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Calendar;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Shift;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftIdParam;
import vip.xiaonuo.oa.modular.attendance.shift.service.CalendarService;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * 考勤记录表Service接口实现类
 *
 * @author mayike
 * @date  2024/06/12 11:03
 **/
@Service
@Slf4j
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Resource
    GroupService groupService;

    @Resource
    ShiftService shiftService;

    @Resource
    CalendarService calendarService;

    public static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public Page<Record> page(RecordPageParam recordPageParam) {
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(recordPageParam.getSortField(), recordPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(recordPageParam.getSortOrder());
            queryWrapper.orderBy(true, recordPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(recordPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Record::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(RecordAddParam recordAddParam) {
        Record record = BeanUtil.toBean(recordAddParam, Record.class);
        this.save(record);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(RecordEditParam recordEditParam) {
        Record record = this.queryEntity(recordEditParam.getId());
        BeanUtil.copyProperties(recordEditParam, record);
        this.updateById(record);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<RecordIdParam> recordIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(recordIdParamList, RecordIdParam::getId));
    }

    @Override
    public Record detail(RecordIdParam recordIdParam) {
        return this.queryEntity(recordIdParam.getId());
    }

    @Override
    public Record queryEntity(String id) {
        Record record = this.getById(id);
        if(ObjectUtil.isEmpty(record)) {
            throw new CommonException("考勤记录表不存在，id值为：{}", id);
        }
        return record;
    }

    /**
     * 打卡前预处理
     */
    @Override
    public RecordPreVo preCheck() {
        // 获取当前登录用户
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();

        // 根据当前日期和人获取考勤组
        Group group = groupService.getGroupByUserId(loginUser.getId());
        DateTime date = DateUtil.date();
        boolean isWorkDay = isWorkDay(date);
        Shift detail = null;
        if (isWorkDay) {
            List<GroupWorkDay> groupWorkDays = group.getGroupWorkDays();
            //先从工作日中获取班次ID
            if(CollUtil.isNotEmpty(groupWorkDays)){
                //周几
                int dayOfWeek = date.dayOfWeek();
                Map<Integer, GroupWorkDay> workDayMap = groupWorkDays.stream().collect(Collectors.toMap(GroupWorkDay::getWeekDay, groupWorkDay -> groupWorkDay));
                GroupWorkDay groupWorkDay = workDayMap.get(dayOfWeek);
                if(ObjectUtil.isNotEmpty(groupWorkDay) && groupWorkDay.isChecked()){
                    ShiftIdParam shiftIdParam = new ShiftIdParam();
                    shiftIdParam.setId(groupWorkDay.getShiftId());
                    detail = shiftService.detail(shiftIdParam);
                }
            }else {
                //从特殊日期中获取
                List<GroupWorkDay> specialDays = group.getSpecialDays();
                if(CollUtil.isNotEmpty(specialDays)){
                    Map<String, GroupWorkDay> spMap = specialDays.stream().filter(sp -> sp.isChecked()).collect(Collectors.toMap(groupWorkDay -> DateUtil.formatDate(groupWorkDay.getWorkDay()), groupWorkDay -> groupWorkDay));
                    GroupWorkDay groupWorkDay = spMap.get(date.toDateStr());
                    if(ObjectUtil.isNotEmpty(groupWorkDay)){
                        ShiftIdParam shiftIdParam = new ShiftIdParam();
                        shiftIdParam.setId(groupWorkDay.getShiftId());
                        detail = shiftService.detail(shiftIdParam);
                    }
                }
            }
            // 工作日
            if (ObjectUtil.isNull(detail))  {
                // 获取默认班次或设置第一个创建的班次为默认班次
                List<Shift> defaultShifts = shiftService.list(new LambdaQueryWrapper<Shift>().eq(Shift::getDefaultFlag, true));
                if (CollUtil.isNotEmpty(defaultShifts)) {
                    detail = defaultShifts.get(0);
                } else {
                    // 无默认班次
                    List<Shift> allShifts = shiftService.list();
                    if (CollUtil.isEmpty(allShifts)) {
                        throw new CommonException("暂无班次信息，请设置后再次操作.");
                    }
                    Shift firstShift = allShifts.get(0);
                    ShiftIdParam shiftIdParam = new ShiftIdParam();
                    shiftIdParam.setId(firstShift.getId());
                    shiftService.setDefaultShift(shiftIdParam);
                    detail = firstShift;
                }
            }
        } else {
            // 非工作日
            if (group.getEnableWeekendWork()) {
                // 获取休息日打卡班次
                String weekDayShiftId = group.getWeekDayShiftId();
                ShiftIdParam shiftIdParam = new ShiftIdParam();
                shiftIdParam.setId(weekDayShiftId);
                detail = shiftService.detail(shiftIdParam);
            }
        }

        // 获取考勤记录
        List<Record> records = new ArrayList<>();
        try {
            records = executor.submit(new CallableImpl(group, detail, loginUser.getId(), isWorkDay)).get();
        } catch (Exception e) {
            log.error("获取考勤打卡列表失败", e);
        }

        if (CollUtil.isEmpty(records)) {
            throw new CommonException("没有对应的上下班打卡时间");
        }

        // 构建返回对象
        RecordPreVo recordPreVo = new RecordPreVo();
        recordPreVo.setCanCheckIn(true);
        recordPreVo.setAllowFieldWork(group.getAllowFieldWork());
        recordPreVo.setRequiredFieldWorkRemarks(group.getRequiredFieldWorkRemarks());
        recordPreVo.setRecords(records);

        return recordPreVo;
    }


    /**
     * 考勤打卡
     */
    @Override
    public void checkIn(RecordAddParam recordAddParam) {
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        if(recordAddParam.getFieldWork()){
            //外勤打卡就需要经度和维度
            if(null == recordAddParam.getLatitude() || null == recordAddParam.getLongitude()){
                throw new CommonException("外勤打卡经度或维度不能为空");
            }
        }
        // 根据当前日期和人获取考勤组
        Group group = groupService.getGroupByUserId(loginUser.getId());
        if(null == group){
            throw new CommonException("考勤组信息不能为空.");
        }

        Record record = queryEntity(recordAddParam.getId());
        if(null == record){
            throw new CommonException("打卡记录不存在");
        }
        if(RecordConstant.SOURCE_TYPE_FAST_CHECK.equals(recordAddParam.getSourceType()) && !RecordConstant.CHECKIN_RESULT_PreCheckIn.equals(record.getCheckinType())){
            throw new CommonException("不能更新打卡记录");
        }
        String todayStr = DateUtil.today();
        Date nowDate = new Date();
        String checkInResult = RecordConstant.CHECKIN_RESULT_NORMAL;
        if(StrUtil.isNotEmpty(record.getShiftId())){
            Shift shift = shiftService.queryEntity(record.getShiftId());
            if(null == shift){
                throw new CommonException("班次不存在");
            }
            if(null != record.getPreDutyTimeBeforeLimit()){
                DateTime checkTime = DateUtil.parse(todayStr + record.getPreDutyTimeBeforeLimit(), DatePattern.NORM_DATETIME_FORMAT);
                if(nowDate.before(checkTime)){
                    throw new CommonException("未到最早打卡时间.");
                }
            }

            if(null != record.getPreDutyTimeAfterLimit()){
                DateTime checkTime = DateUtil.parse(todayStr + record.getPreDutyTimeAfterLimit(), DatePattern.NORM_DATETIME_FORMAT);
                if(nowDate.after(checkTime)){
                    throw new CommonException("超过结束时间不能打卡.");
                }
            }

            //上班打卡
            if(record.getCheckinType().equals(RecordConstant.CHECKIN_TYPE_ON_DUTY)){
                DateTime dutyTime = DateUtil.parse(todayStr + record.getDutyTime(), DatePattern.NORM_DATETIME_FORMAT);
                checkInResult = RecordConstant.CHECKIN_RESULT_NORMAL;
                //晚于正常打卡就记录为迟到打卡
                if(nowDate.after(dutyTime)){
                    checkInResult = RecordConstant.CHECKIN_RESULT_Late;
                }
                //超过严重打卡时间，记录为严重迟到
//                if(shift.getSeriousTardinessLateMinutes()>0){
//                    DateTime seriousLateTime = DateUtil.offsetMinute(dutyTime, shift.getSeriousTardinessLateMinutes());
//                    if(nowDate.after(seriousLateTime)){
//                        checkInResult = RecordConstant.CHECKIN_RESULT_SeriousLate;
//                    }
//                }
            } else if (RecordConstant.CHECKIN_TYPE_OFF_DUTY.equals(record.getCheckinType())){
                DateTime dutyTime = DateUtil.parse(todayStr + record.getDutyTime(), DatePattern.NORM_DATETIME_FORMAT);
                //早退
                if(nowDate.before(dutyTime)){
                    checkInResult = RecordConstant.CHECKIN_RESULT_Early;
                }

                if(checkInResult.equals(RecordConstant.CHECKIN_RESULT_NORMAL)){
                    List<Record> toDayRecords = list(new LambdaQueryWrapper<Record>().eq(Record::getCheckinDate, DateUtil.today()).eq(Record::getUserId,loginUser.getId())
                            .orderByAsc(Record::getCreateTime));
                    if(CollUtil.isEmpty(toDayRecords)){
                        throw new CommonException("暂无打卡数据。");
                    }
                    if(record.getId().equals(toDayRecords.get(toDayRecords.size()-1).getId())){
                        long realWorkTime = 0;
                        //上班打卡
                        List<Record> onDutyList = toDayRecords.stream().filter(record1 ->
                                record1.getCheckinType().equals(RecordConstant.CHECKIN_TYPE_ON_DUTY)
                        ).sorted(Comparator.comparing(Record::getCheckinTime)).collect(Collectors.toList());
                        // 下班打卡
                        List<Record> offDutyList = toDayRecords.stream().filter(
                                        (r) -> r.getCheckinType().equals(RecordConstant.CHECKIN_TYPE_OFF_DUTY) )
                                .sorted(Comparator.comparing(Record::getCheckinTime)).collect(Collectors.toList());

                        for (int i = 0; i < onDutyList.size(); i++) {
                            Record onDuty = onDutyList.get(i);
                            Record offDuty = offDutyList.get(i);
                            if (offDuty.getId().equals(record.getId())) {
                                realWorkTime += (nowDate.getTime() - onDuty.getCheckinTime().getTime());
                            } else {
                                realWorkTime += (offDuty.getCheckinTime().getTime() - offDuty.getCheckinTime().getTime());
                            }
                        }

                    }
                }

            }
        }
        record.setCheckinTime(nowDate);
        if(StrUtil.isNotEmpty(recordAddParam.getSourceType())){
            record.setSourceType(recordAddParam.getSourceType());
        }else {
            record.setSourceType(RecordConstant.SOURCE_TYPE_USER_CHECK);
        }
        record.setCheckinResult(checkInResult);
        record.setSourceDevice(recordAddParam.getSourceDevice());
        record.setDescription(StrUtil.isNotEmpty(recordAddParam.getDescription())?recordAddParam.getDescription():"");
        record.setLongitude(null!=recordAddParam.getLongitude()?recordAddParam.getLongitude():0);
        record.setLatitude(null!=recordAddParam.getLatitude()?recordAddParam.getLatitude():0);
        saveOrUpdate(record);
        //todo 处理异常情况

    }

    /**
     * 新增 工作日期表 判断是否未工作日
     * @return
     */
    private boolean isWorkDay(DateTime date){
        Calendar one = calendarService.getOne(new LambdaQueryWrapper<Calendar>().eq(Calendar::getCalendarDate, date.toDateStr()));
        return ObjectUtil.isNotEmpty(one)?one.getIsWorkday():false;
    }

    private class CallableImpl implements Callable<List<Record>>{

        private Group group;

        private Shift shift;

        private String userId;

        private boolean isWork;

        public CallableImpl(Group group, Shift shift, String userId, boolean isWork) {
            this.group = group;
            this.shift = shift;
            this.userId = userId;
            this.isWork = isWork;
        }

        @Override
        public List<Record> call() throws Exception {
            //查询打卡记录
            Date nowDate = new Date();
            String today = DateUtil.today();
            List<Record> toDayRecords = list(new LambdaQueryWrapper<Record>().eq(Record::getCheckinDate, today).eq(Record::getUserId,userId)
                    .orderByAsc(Record::getCreateTime));
            if(group.getGroupType().equals(GroupConstant.TURN)){
                DateTime yesterday = DateUtil.yesterday();
                String yesterdayStr = DateUtil.formatDate(yesterday);
                List<Record> yesterRecords = list(new LambdaQueryWrapper<Record>().eq(Record::getCheckinDate, today).eq(Record::getUserId,userId)
                        .orderByAsc(Record::getCreateTime));
                if(CollUtil.isEmpty(yesterRecords)){
                    if(shift != null){
                        yesterRecords = dealShiftForRecord(group, shift, userId, yesterday, yesterdayStr, null);
                    }
                }
                if(CollUtil.isNotEmpty(yesterRecords)){
                    Record last = yesterRecords.get(yesterRecords.size() - 1);
                    if(last.getOffDutyNextDay()){
                        Date onDutyAfterTime;
                        if(null == last.getPreDutyTimeAfterLimit()){
                            onDutyAfterTime = DateUtil.parse(today+ " " + last.getDutyTime(),DatePattern.NORM_DATETIME_FORMAT);
                            onDutyAfterTime = DateUtil.offsetMinute(onDutyAfterTime,60);
                        }else {
                            onDutyAfterTime = DateUtil.parse(today+ " " + last.getPreDutyTimeAfterLimit(),DatePattern.NORM_DATETIME_FORMAT);
                        }

                        // 如果没有在限制时间结束前 就是返回昨天的打卡记录
                        if (!nowDate.after(onDutyAfterTime)) {
                            return yesterRecords;
                        }
                    }
                }
            }

            if(CollUtil.isNotEmpty(toDayRecords)){
                dealWithOvertimeRecord(nowDate,today,toDayRecords);
                return toDayRecords;
            }

            if(shift != null){
                toDayRecords = dealShiftForRecord(group,shift,userId,nowDate,today,toDayRecords);
            }

            if(CollUtil.isEmpty(toDayRecords)){
                List<Record> finalToDayRecords = new ArrayList<>();
                //先按休息日处理
                Boolean weekendWorkday = group.getEnableWeekendWork();
                if(!isWork && weekendWorkday){
                    String weekDayShiftId = group.getWeekDayShiftId();
                    //休息日排班规则
                    Shift weekShift = shiftService.queryEntity(weekDayShiftId);
                    weekShift.getRules().forEach(rule->{
                        finalToDayRecords.add(savePreCheckInRecord(userId, rule.getType(), group, shift.getId(), nowDate, today, rule));
                    });
                }else {
                    Rule onDutyRule = new Rule(RecordConstant.CHECKIN_TYPE_ON_DUTY,DateUtil.parse("08:30",DatePattern.NORM_TIME_PATTERN),DateUtil.parse("05:00",DatePattern.NORM_TIME_PATTERN),DateUtil.parse("08:30",DatePattern.NORM_TIME_PATTERN),15,15,15,false);
                    Record record = savePreCheckInRecord(userId, RecordConstant.CHECKIN_TYPE_ON_DUTY, group, null, nowDate, today, onDutyRule);
                    finalToDayRecords.add(record);
                    Rule offDutyRule = new Rule(RecordConstant.CHECKIN_TYPE_OFF_DUTY,DateUtil.parse("17:30",DatePattern.NORM_TIME_PATTERN),DateUtil.parse("17:30",DatePattern.NORM_TIME_PATTERN),DateUtil.parse("23:59",DatePattern.NORM_TIME_PATTERN),15,15,15,false);
                    Record record1 = savePreCheckInRecord(userId, RecordConstant.CHECKIN_TYPE_ON_DUTY, group, null, nowDate, today, offDutyRule);
                    finalToDayRecords.add(record1);
                }
                toDayRecords = finalToDayRecords;
            }
            return toDayRecords;
        }
    }

    private List<Record> dealShiftForRecord(Group group,Shift shift,String userId, Date checkDate,String dateStr,List<Record> records){
        List<Rule> rules = shift.getRules();
        if(CollUtil.isEmpty(rules)){
            throw new CommonException(String.format("%s该班次的考勤规则为空",shift.getShiftName()));
        }
        //没有打卡记录，那就先预生成打卡记录
        if(CollUtil.isEmpty(records)){
            List<Record> finalRecords = new ArrayList<>();
            rules.forEach(rule->{
                finalRecords.add(savePreCheckInRecord(userId, rule.getType(), group, shift.getId(), checkDate, dateStr, rule));
            });
            records = finalRecords;
        }

        return records;
    }

    /**
     * 预存打卡记录
     * @return
     */
    private Record savePreCheckInRecord(String userId, String dutyType, Group group, String shiftId, Date dutyDate, String dutyTimeStr, Rule rule){
        Record record = new Record();
        record.setCheckinType(dutyType);
        record.setCheckinResult(RecordConstant.CHECKIN_RESULT_PreCheckIn);
        record.setUserId(userId);
        record.setCheckinDate(dutyDate);
        //打卡时间处理
        if(StrUtil.isEmpty(dutyTimeStr)){
            dutyTimeStr = rule.getNormal().toString();
        }
        Date checkinTime = DateUtil.parse(dutyDate+ " " + dutyTimeStr,DatePattern.NORM_DATETIME_FORMAT);
        if(dutyType.equals(RecordConstant.CHECKIN_TYPE_OFF_DUTY) && rule.getOffDutyNextDay()){
            DateTime dateTime = DateUtil.offsetDay(checkinTime, 1);
            record.setCheckinTime(dateTime);
        }else {
            record.setCheckinTime(checkinTime);
        }
        record.setDutyTime(DateUtil.parseTime(dutyTimeStr));
        record.setOffDutyNextDay(rule.getOffDutyNextDay());
        record.setSourceType(RecordConstant.SOURCE_TYPE_AUTO_CHECK);
        record.setPreDutyTimeBeforeLimit(rule.getEarliest());
        record.setPreDutyTimeBeforeLimit(rule.getLatest());
        record.setSourceDevice("其他");
        record.setDescription("系统生成，预打卡记录");
        record.setGroupId(group.getId());
        record.setShiftId(shiftId);
        save(record);
        return record;
    }

    /**
     * 处理已超过打卡时间的记录，标记为未打卡
     */
    private void dealWithOvertimeRecord(Date nowDate, String today, List<Record> records) {
        // 筛选出预打卡的数据
        List<Record> preCheckInRecords = records.stream()
                .filter(record -> RecordConstant.CHECKIN_RESULT_PreCheckIn.equals(record.getCheckinResult()))
                .collect(Collectors.toList());

        for (int i = 0; i < preCheckInRecords.size(); i++) {
            Record currentRecord = preCheckInRecords.get(i);
            Date checkinTime;

            if (currentRecord.getPreDutyTimeAfterLimit() != null) {
                // 配置了打卡结束时间限制
                checkinTime = DateUtil.parse(today + " " + currentRecord.getPreDutyTimeAfterLimit(), DatePattern.NORM_DATETIME_FORMAT);
            } else {
                // 未配置结束时间限制，使用正常打卡时间
                checkinTime = DateUtil.parse(today + " " + currentRecord.getDutyTime(), DatePattern.NORM_DATETIME_FORMAT);
            }

            if (nowDate.after(checkinTime)) {
                if (currentRecord.getPreDutyTimeAfterLimit() != null || i == preCheckInRecords.size() - 1) {
                    // 如果超过打卡结束限制时间，或者是最后一条记录，直接生成未打卡数据
                    update2NoCheckInRecord(currentRecord);
                } else {
                    // 处理没有打卡结束时间限制且不是最后一条记录的情况
                    Record nextRecord = preCheckInRecords.get(i + 1);
                    Date nextCheckTime = DateUtil.parse(today + " " + nextRecord.getDutyTime(), DatePattern.NORM_DATETIME_FORMAT);

                    if (nextRecord.getOffDutyNextDay()) {
                        nextCheckTime = DateUtil.offsetDay(nextCheckTime, 1);
                    }

                    // 计算中间时间点
                    long halfIntervalMinutes = (nextCheckTime.getTime() - checkinTime.getTime()) / 60000 / 2;
                    DateTime middleTime = DateUtil.offsetMinute(checkinTime, (int) halfIntervalMinutes);

                    // 如果当前时间超过中间时间点，生成未打卡数据
                    if (nowDate.after(middleTime)) {
                        update2NoCheckInRecord(currentRecord);
                    }
                }
            }
        }
    }

    private void update2NoCheckInRecord(Record record){
        record.setCheckinResult(RecordConstant.CHECKIN_RESULT_NotSigned);
        record.setSourceType(RecordConstant.SOURCE_TYPE_AUTO_CHECK);
        record.setSourceDevice("其他");
        record.setDescription("系统生成，缺卡记录");
        updateById(record);
    }

    /**
     * 自动处理异常打卡问题
     * 包括申诉打卡的情况
     * @param record
     * @param fieldWorkMarkError
     */
    private void generateAppealInfo(Record record, boolean fieldWorkMarkError){
        if(null != record && record.checkResultException(fieldWorkMarkError)){

        }
    }
}
