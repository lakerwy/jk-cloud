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
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.oa.modular.attendance.group.entity.Group;
import vip.xiaonuo.oa.modular.attendance.group.service.GroupService;
import vip.xiaonuo.oa.modular.attendance.record.entity.Record;
import vip.xiaonuo.oa.modular.attendance.record.mapper.RecordMapper;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordAddParam;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordEditParam;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordIdParam;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordPageParam;
import vip.xiaonuo.oa.modular.attendance.record.service.RecordService;
import vip.xiaonuo.oa.modular.attendance.shift.entity.ClockInRule;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Shift;
import vip.xiaonuo.oa.modular.attendance.shift.param.ShiftIdParam;
import vip.xiaonuo.oa.modular.attendance.shift.service.ShiftService;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 考勤记录表Service接口实现类
 *
 * @author mayike
 * @date  2024/06/12 11:03
 **/
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Resource
    GroupService groupService;

    @Resource
    ShiftService shiftService;

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
    public void preAdd() {
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        /**
         * 1、根据当前日期和人获取 考勤组和考勤班次
         */
        //周日-周六 1-7
        int weekValue = DateUtil.dayOfWeekEnum(DateUtil.date()).getValue();
        assert loginUser != null;
        Group group = groupService.getGroupByUserId(loginUser.getId());
        String workDates = group.getWorkDates();
        if(StrUtil.isEmpty(workDates)){
            workDates = "0111110"; //考勤工作日为空时 ，默认赋值为周一到周五为工作日
        }
        boolean workDay = isWorkDay(workDates, weekValue);
        //工作日
        Shift detail = null;
        if(workDay){
            if(StrUtil.isNotEmpty(group.getShiftId())){
                ShiftIdParam shiftIdParam = new ShiftIdParam();
                shiftIdParam.setId(group.getShiftId());
                detail = shiftService.detail(shiftIdParam);
            }else {
                List<Shift> list = shiftService.list(new LambdaQueryWrapper<Shift>().eq(Shift::getDefaultFlag, true));
                if(CollUtil.isEmpty(list)){
                    //无默认班次，那就查询所有班次，设置第一个创建得班次为默认班次
                    List<Shift> list1 = shiftService.list();
                    if(CollUtil.isEmpty(list1)){
                        throw  new CommonException("暂无班次信息，请设置后再次操作.");
                    }
                    String id = list1.get(0).getId();
                    ShiftIdParam shiftIdParam = new ShiftIdParam();
                    shiftIdParam.setId(id);
                    shiftService.setDefaultShift(shiftIdParam);
                    detail = list1.get(0);
                }else {
                    detail = list.get(0);
                }
            }
        }else {
            //非工作日打卡，若非工作日打卡不按照正常工作日打卡规则
            if (!group.getWeekendWorkday()){
                //获取休息日打卡班次
                //此处无需考虑没有休息日打卡班次Id ,在新增时已校验
                String weekDayShiftId = group.getWeekDayShiftId();
                ShiftIdParam shiftIdParam = new ShiftIdParam();
                shiftIdParam.setId(weekDayShiftId);
                detail = shiftService.detail(shiftIdParam);
            }
        }

        List<ClockInRule> clockInRules = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(detail)){
            assert detail != null;
            clockInRules = detail.getClockInRules();
        }
        //获取当日的打卡记录
        List<Record> records = this.list(new LambdaQueryWrapper<Record>().eq(Record::getCheckinDate, DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN))
                .orderByAsc(Record::getCreateTime));
        /**
         * 上班只会有一条数据
         * 下班会有多条
         */

    }

    /**
     * 新增 工作日期表 判断是否未工作日
     * @param workDates
     * @param weekValue
     * @return
     */
    private boolean isWorkDay(String workDates, int weekValue){
        // 在 workDates 中，周日对应第一个字符，周一对应第二个字符（例如: "0111110"）
        int index = weekValue - 1;

        // 判断当前星期几是否为工作日
        return workDates.charAt(index) == '1';
    }
}
