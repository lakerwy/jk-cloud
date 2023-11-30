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
package vip.xiaonuo.sys.modular.hr.userHolidayData.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonTimeFormatUtil;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.entity.HrAttendanceAnalStat;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.service.HrAttendanceAnalStatService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.entity.HrUserApplyHoliday;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.service.HrUserApplyHolidayService;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.enums.HrUserAttendanceStatEnum;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.service.HrUserAttendanceStatService;
import vip.xiaonuo.sys.modular.hr.userHolidayData.entity.HrUserHolidayData;
import vip.xiaonuo.sys.modular.hr.userHolidayData.enums.HrUserHolidayDataEnum;
import vip.xiaonuo.sys.modular.hr.userHolidayData.mapper.HrUserHolidayDataMapper;
import vip.xiaonuo.sys.modular.hr.userHolidayData.param.*;
import vip.xiaonuo.sys.modular.hr.userHolidayData.service.HrUserHolidayDataService;
import vip.xiaonuo.sys.modular.hr.userHolidayData.vo.HolidayDataRec;
import vip.xiaonuo.sys.modular.hr.userHolidayData.vo.HolidayDataRecVo;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.entity.HrUserHolidayDataRec;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.service.HrUserHolidayDataRecService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 公司员工假期数据表Service接口实现类
 *
 * @author ljy
 * @date  2023/09/22 16:48
 **/
@Service
public class HrUserHolidayDataServiceImpl extends ServiceImpl<HrUserHolidayDataMapper, HrUserHolidayData> implements HrUserHolidayDataService {

    @Resource
    private HrUserHolidayDataRecService hrUserHolidayDataRecService;
    @Resource
    private HrUserApplyHolidayService hrUserApplyHolidayService;
    @Resource
    private HrUserAttendanceStatService hrUserAttendanceStatService;
    @Resource
    private HrAttendanceAnalStatService hrAttendanceAnalStatService;
    @Resource
    ConferenceRezService conferenceRezService;

    @Override
    public Page<HrUserHolidayData> page(HrUserHolidayDataPageParam hrUserHolidayDataPageParam) {
        QueryWrapper<HrUserHolidayData> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserHolidayDataPageParam.getUserName())) {
            queryWrapper.lambda().like(HrUserHolidayData::getUserName, hrUserHolidayDataPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(hrUserHolidayDataPageParam.getDeparId())) {
            queryWrapper.lambda().eq(HrUserHolidayData::getDeparId, hrUserHolidayDataPageParam.getDeparId());
        }
        if(ObjectUtil.isNotEmpty(hrUserHolidayDataPageParam.getDeparName())) {
            queryWrapper.lambda().like(HrUserHolidayData::getDeparName, hrUserHolidayDataPageParam.getDeparName());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserHolidayDataPageParam.getSortField(), hrUserHolidayDataPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserHolidayDataPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserHolidayDataPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserHolidayDataPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserHolidayData::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserHolidayDataAddParam hrUserHolidayDataAddParam) {
        List<HrUserHolidayData> hrUserHolidayDataList = this.list(new LambdaQueryWrapper<HrUserHolidayData>().eq(HrUserHolidayData::getUserId,hrUserHolidayDataAddParam.getUserId()));
        HrUserHolidayData hrUserHolidayData = new HrUserHolidayData();
        HrUserHolidayDataEditParam hrUserHolidayDataEditParam = BeanUtil.toBean(hrUserHolidayDataAddParam,HrUserHolidayDataEditParam.class);
        if(CollectionUtil.isNotEmpty(hrUserHolidayDataList)){
            hrUserHolidayData = hrUserHolidayDataList.get(0);
            if(StringUtils.isNotEmpty(hrUserHolidayDataAddParam.getAnnualLeave())){
                Double annualLeave = Double.parseDouble(hrUserHolidayDataAddParam.getAnnualLeave());
                Double addAnnualLeave =  hrUserHolidayData.getAnnualLeave() != null ? Double.parseDouble(hrUserHolidayData.getAnnualLeave()) : 0.0;
                Double all = annualLeave + addAnnualLeave;
                hrUserHolidayData.setAnnualLeave(all.toString());
                hrUserHolidayDataEditParam.setApplyType(6);
            }
            if(StringUtils.isNotEmpty(hrUserHolidayDataAddParam.getCompensatoryLeave())){
                Double compensatoryLeave = Double.parseDouble(hrUserHolidayDataAddParam.getCompensatoryLeave());
                Double addCompensatoryLeave = hrUserHolidayData.getCompensatoryLeave() != null ? Double.parseDouble(hrUserHolidayData.getCompensatoryLeave()) : 0.0;
                Double all = compensatoryLeave + addCompensatoryLeave;
                hrUserHolidayData.setCompensatoryLeave(all.toString());
                hrUserHolidayDataEditParam.setApplyType(7);
            }
        }else{
            hrUserHolidayData = BeanUtil.toBean(hrUserHolidayDataAddParam, HrUserHolidayData.class);
            if(StringUtils.isNotEmpty(hrUserHolidayDataAddParam.getAnnualLeave())){
                hrUserHolidayDataEditParam.setApplyType(6);
            }
            if(StringUtils.isNotEmpty(hrUserHolidayDataAddParam.getCompensatoryLeave())){
                hrUserHolidayDataEditParam.setApplyType(7);
            }
        }
        this.saveOrUpdate(hrUserHolidayData);
        saveRec(hrUserHolidayDataEditParam,hrUserHolidayData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserHolidayDataEditParam hrUserHolidayDataEditParam) {
        HrUserHolidayData hrUserHolidayData = this.queryEntity(hrUserHolidayDataEditParam.getId());
        setHrUserHolidayData(hrUserHolidayDataEditParam,hrUserHolidayData);
        //BeanUtil.copyProperties(hrUserHolidayDataEditParam, hrUserHolidayData);
        this.updateById(hrUserHolidayData);
        saveRec(hrUserHolidayDataEditParam,hrUserHolidayData);
    }

    private void saveRec(HrUserHolidayDataEditParam hrUserHolidayDataEditParam,HrUserHolidayData hrUserHolidayData){
        /** 同步增减假期记录*/
        HrUserHolidayDataRec hrUserHolidayDataRec = BeanUtil.copyProperties(hrUserHolidayDataEditParam,HrUserHolidayDataRec.class);
        hrUserHolidayDataRec.setUserId(hrUserHolidayData.getUserId());
        hrUserHolidayDataRec.setUserName(hrUserHolidayData.getUserName());
        hrUserHolidayDataRec.setDeparId(hrUserHolidayData.getDeparId());
        hrUserHolidayData.setDeparName(hrUserHolidayData.getDeparName());
        hrUserHolidayDataRec.setId("");//去掉主键
        hrUserHolidayDataRecService.save(hrUserHolidayDataRec);
    }
    private void setHrUserHolidayData(HrUserHolidayDataEditParam hrUserHolidayDataEditParam,HrUserHolidayData hrUserHolidayData){
        /** 假期类别：事假 1，病假 2，婚假 3，产假 4，丧假 5，年假 6，调休 7，出差 8 */
        //ANNUAL_LEAVE  年假;COMPENSATORY_LEAVE  调休;PERSONAL_LEAVE  事假;SICK_LEAVE  病假
        //MARRIAGE_LEAVE  婚假;MATERNITY_LEAVE 产假;BEREAVEMENT_LEAVE 丧假
        switch (hrUserHolidayDataEditParam.getApplyType()) {
            case 6: //年假
                if(StringUtils.isNotEmpty(hrUserHolidayData.getAnnualLeave())){
                    Double annualLeave = Double.parseDouble(hrUserHolidayData.getAnnualLeave()) + Double.parseDouble(hrUserHolidayDataEditParam.getAnnualLeave());
                    hrUserHolidayData.setAnnualLeave(annualLeave.toString());
                }else{
                    hrUserHolidayData.setAnnualLeave(hrUserHolidayDataEditParam.getAnnualLeave()); }
                break;
            case 7: //调休
                if(StringUtils.isNotEmpty(hrUserHolidayData.getCompensatoryLeave())){
                    Double compensatoryLeave = Double.parseDouble(hrUserHolidayData.getCompensatoryLeave()) + Double.parseDouble(hrUserHolidayDataEditParam.getCompensatoryLeave());
                    hrUserHolidayData.setCompensatoryLeave(compensatoryLeave.toString());
                }else {
                    hrUserHolidayData.setCompensatoryLeave(hrUserHolidayDataEditParam.getCompensatoryLeave()); }
                break;
            default:
                break;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserHolidayDataIdParam> hrUserHolidayDataIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserHolidayDataIdParamList, HrUserHolidayDataIdParam::getId));
    }

    @Override
    public HolidayDataRecVo detail(EditHolidayParam editHolidayParam) {
        List<HrUserHolidayData> hrUserHolidayDataList = this.list(new LambdaQueryWrapper<HrUserHolidayData>().eq(HrUserHolidayData::getUserId,editHolidayParam.getUserId()));
        List<HrUserHolidayDataRec> hrUserHolidayDataRecs = hrUserHolidayDataRecService.list(new LambdaQueryWrapper<HrUserHolidayDataRec>().eq(HrUserHolidayDataRec::getUserId,editHolidayParam.getUserId())
                .eq(HrUserHolidayDataRec::getApplyType,editHolidayParam.getApplyType()));
        Map<String,String> userMap = conferenceRezService.getUserData();

        List<HolidayDataRec> holidayDataRecList =  hrUserHolidayDataRecs.stream().map(rec -> {
            HolidayDataRec holidayDataRec = new HolidayDataRec();
            holidayDataRec.setUserId(rec.getUserId());
            holidayDataRec.setUserName(rec.getUserName());
            String detail = "";
            String detailDay = "";
            String createUserName = userMap.get(rec.getCreateUser()) != null ? userMap.get(rec.getCreateUser()) : "";
            String userName = StringUtils.isNotEmpty(rec.getUserName()) ? rec.getUserName() : "";
            //假期类别：事假 1，病假 2，婚假 3，产假 4，丧假 5，年假 6，调休 7，出差 8
            switch (Integer.parseInt(editHolidayParam.getApplyType())){
                case 1://+0.5天陈雨 为冷磊 增加了0.5天调休
                    detailDay = StringUtils.isNotEmpty(rec.getPersonalLeave()) && Integer.parseInt(rec.getPersonalLeave()) > 0 ?
                            "+"+rec.getPersonalLeave()+"天 ": rec.getPersonalLeave()+"天 ";
                    detail = StringUtils.isNotEmpty(rec.getPersonalLeave()) && Integer.parseInt(rec.getPersonalLeave()) > 0 ?
                            createUserName+"为 "+userName+"增加 "+rec.getPersonalLeave()+"天事假": userName+"使用"+rec.getPersonalLeave()+"天事假";
                    holidayDataRec.setStatus(StringUtils.isNotEmpty(rec.getPersonalLeave()) && Integer.parseInt(rec.getPersonalLeave()) > 0 ? 1 :2);
                    break;
                case 2:
                    detailDay = StringUtils.isNotEmpty(rec.getSickLeave()) && Integer.parseInt(rec.getSickLeave()) > 0 ?
                            "+"+rec.getSickLeave()+"天 ": rec.getSickLeave()+"天 ";
                    detail = StringUtils.isNotEmpty(rec.getSickLeave()) && Integer.parseInt(rec.getSickLeave()) > 0 ?
                            createUserName+"为 "+userName+"增加" +rec.getSickLeave()+"天病假": userName+"使用"+rec.getSickLeave()+"天病假";
                    holidayDataRec.setStatus(StringUtils.isNotEmpty(rec.getSickLeave()) && Integer.parseInt(rec.getSickLeave()) > 0? 1 :2);
                    break;
                case 3:
                    detailDay = StringUtils.isNotEmpty(rec.getMarriageLeave()) && Integer.parseInt(rec.getMarriageLeave()) > 0 ?
                            "+"+rec.getMarriageLeave()+"天 ": rec.getMarriageLeave()+"天 ";
                    detail = StringUtils.isNotEmpty(rec.getMarriageLeave()) && Integer.parseInt(rec.getMarriageLeave()) > 0 ?
                            createUserName+"为 "+userName+"增加 "+rec.getMarriageLeave()+"天婚假": userName+"使用"+rec.getMarriageLeave()+"天婚假";
                    holidayDataRec.setStatus(StringUtils.isNotEmpty(rec.getMarriageLeave()) && Integer.parseInt(rec.getMarriageLeave()) > 0? 1 :2);
                    break;
                case 4:
                    detailDay = StringUtils.isNotEmpty(rec.getMaternityLeave()) && Integer.parseInt(rec.getMaternityLeave()) > 0 ?
                            "+"+rec.getMaternityLeave()+"天 ": rec.getMaternityLeave()+"天 ";
                    detail = StringUtils.isNotEmpty(rec.getMaternityLeave()) && Integer.parseInt(rec.getMaternityLeave()) > 0 ?
                            createUserName+"为 "+userName+"增加 "+rec.getMaternityLeave()+"天产假": rec.getUserName()+"使用"+rec.getMaternityLeave()+"天产假";
                    holidayDataRec.setStatus(StringUtils.isNotEmpty(rec.getMaternityLeave()) && Integer.parseInt(rec.getMaternityLeave()) > 0 ? 1 :2);
                    break;
                case 5:
                    detailDay = StringUtils.isNotEmpty(rec.getBereavementLeave()) && Integer.parseInt(rec.getBereavementLeave()) > 0 ?
                            "+"+rec.getBereavementLeave()+"天 ": rec.getBereavementLeave()+"天 ";
                    detail = StringUtils.isNotEmpty(rec.getBereavementLeave()) && Integer.parseInt(rec.getBereavementLeave()) > 0 ?
                            createUserName+"为 "+userName+"增加 "+rec.getBereavementLeave()+"天丧假": userName+"使用"+rec.getBereavementLeave()+"天丧假";
                    holidayDataRec.setStatus(StringUtils.isNotEmpty(rec.getBereavementLeave()) && Integer.parseInt(rec.getBereavementLeave()) > 0 ? 1 :2);
                    break;
                case 6:
                    detailDay = StringUtils.isNotEmpty(rec.getAnnualLeave()) && Integer.parseInt(rec.getAnnualLeave()) > 0 ?
                            "+"+rec.getAnnualLeave()+"天 ": rec.getAnnualLeave()+"天 ";
                    detail = StringUtils.isNotEmpty(rec.getAnnualLeave()) && Integer.parseInt(rec.getAnnualLeave()) > 0 ?
                            createUserName+"为 "+userName+"增加 "+rec.getAnnualLeave()+"天年假": userName+"使用"+rec.getAnnualLeave()+"天年假";
                    holidayDataRec.setStatus(StringUtils.isNotEmpty(rec.getAnnualLeave()) && Integer.parseInt(rec.getAnnualLeave()) > 0? 1 :2);
                    break;
                case 7:
                    detailDay = StringUtils.isNotEmpty(rec.getCompensatoryLeave()) && Integer.parseInt(rec.getCompensatoryLeave()) > 0 ?
                            "+"+rec.getCompensatoryLeave()+"天 ": rec.getCompensatoryLeave()+"天 ";
                    detail = StringUtils.isNotEmpty(rec.getCompensatoryLeave()) && Integer.parseInt(rec.getCompensatoryLeave()) > 0 ?
                            createUserName+"为 "+userName+"增加 "+rec.getCompensatoryLeave()+"天调休": userName+"使用"+rec.getCompensatoryLeave()+"天调休";
                    holidayDataRec.setStatus(StringUtils.isNotEmpty(rec.getCompensatoryLeave())  && Integer.parseInt(rec.getCompensatoryLeave()) > 0? 1 :2);
                    String validTime = StringUtils.isNotEmpty(rec.getCompensatoryLeave()) && Integer.parseInt(rec.getCompensatoryLeave()) > 0 ? rec.getValidBeginTime()+"到"+rec.getValidEndTime()+"有效" :"";
                    holidayDataRec.setValidTime(validTime);
                    break;
                default:
                    break;
            }
            holidayDataRec.setCreateTime(rec.getCreateTime());
            holidayDataRec.setDetailDay(detailDay);
            holidayDataRec.setDetail(detail);
            return holidayDataRec;
        }).collect(Collectors.toList());
        HolidayDataRecVo holidayDataRecVo = new HolidayDataRecVo();
        holidayDataRecVo.setHolidayDataRecList(holidayDataRecList);
        holidayDataRecVo.setHolidayData(CollectionUtil.isNotEmpty(hrUserHolidayDataList) ? hrUserHolidayDataList.get(0) : new HrUserHolidayData());
        return holidayDataRecVo;
    }

    @Override
    public HrUserHolidayData queryEntity(String id) {
        HrUserHolidayData hrUserHolidayData = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserHolidayData)) {
            throw new CommonException("公司员工假期数据表不存在，id值为：{}", id);
        }
        return hrUserHolidayData;
    }

    /** 申请假期之后的统计操作*/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void calculateHolidayJobHandle(String processInstanceId){
        HrUserApplyHoliday hrUserApplyHoliday = hrUserApplyHolidayService.getOne(new LambdaQueryWrapper<HrUserApplyHoliday>().eq(HrUserApplyHoliday::getProcessInstanceId,processInstanceId));
        if(ObjectUtil.isEmpty(hrUserApplyHoliday)){
            throw new RuntimeException("无申请数据，processInstanceId："+processInstanceId);
        }
        HrUserHolidayData hrUserHolidayData = this.getOne(new LambdaQueryWrapper<HrUserHolidayData>().eq(HrUserHolidayData::getUserId,hrUserApplyHoliday.getApplyUserId()));
        if(ObjectUtil.isEmpty(hrUserHolidayData)){
            throw new RuntimeException("该人员在假期数据表中无数据，userId："+hrUserApplyHoliday.getApplyUserId());
        }
        if(HrUserHolidayDataEnum.ANNUAL_LEAVE.getValue().equals(hrUserApplyHoliday.getApplyType().toString())){//年假
            Double remindDays = Double.parseDouble(hrUserHolidayData.getAnnualLeave())-hrUserApplyHoliday.getLeaveDays();
            hrUserHolidayData.setAnnualLeave(remindDays.toString());
        }
        if(HrUserHolidayDataEnum.COMPENSATORY_LEAVE.getValue().equals(hrUserApplyHoliday.getApplyType().toString())){//调休
            Double remindDays = Double.parseDouble(hrUserHolidayData.getCompensatoryLeave())-hrUserApplyHoliday.getLeaveDays();
            hrUserHolidayData.setCompensatoryLeave(remindDays.toString());
        }
        this.updateById(hrUserHolidayData);
        saveHolidayRec(hrUserApplyHoliday);
        holidayToStat(hrUserApplyHoliday);//请假数据到统计库
    }
    private void saveHolidayRec(HrUserApplyHoliday hrUserApplyHoliday){
        HrUserHolidayDataRec hrUserHolidayDataRec = new HrUserHolidayDataRec();
        hrUserHolidayDataRec.setUserId(hrUserApplyHoliday.getApplyUserId());
        hrUserHolidayDataRec.setApplyType(hrUserApplyHoliday.getApplyType());
        if(HrUserHolidayDataEnum.ANNUAL_LEAVE.getValue().equals(hrUserApplyHoliday.getApplyType().toString())){//年假
            hrUserHolidayDataRec.setAnnualLeave("-"+hrUserApplyHoliday.getLeaveDays().toString());
        }
        if(HrUserHolidayDataEnum.COMPENSATORY_LEAVE.getValue().equals(hrUserApplyHoliday.getApplyType().toString())){//调休
            hrUserHolidayDataRec.setCompensatoryLeave("-"+hrUserApplyHoliday.getLeaveDays().toString());
        }
        hrUserHolidayDataRec.setBeginTime(DateUtil.format(hrUserApplyHoliday.getApplyBeginTime(),DatePattern.NORM_DATETIME_PATTERN));
        hrUserHolidayDataRec.setEndTime(DateUtil.format(hrUserApplyHoliday.getApplyEndTime(),DatePattern.NORM_DATETIME_PATTERN));
        hrUserHolidayDataRecService.save(hrUserHolidayDataRec);
    }

    //请假数据到统计表
    private void holidayToStat( HrUserApplyHoliday hrUserApplyHoliday){
        List<String> dayList = CommonTimeFormatUtil.getPerStrDayList(hrUserApplyHoliday.getApplyBeginTime(),hrUserApplyHoliday.getApplyEndTime());
        dayList.forEach(day -> {
            List<HrAttendanceAnalStat> analStatList =  hrAttendanceAnalStatService.list(new LambdaQueryWrapper<HrAttendanceAnalStat>()
                    .eq(HrAttendanceAnalStat::getUserId,hrUserApplyHoliday.getApplyUserId())
                    .between(HrAttendanceAnalStat::getCheckTime,day+ HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue(),day+HrUserAttendanceStatEnum.END_TIME.getValue()));

            Boolean isWeek = DateUtil.isWeekend(CommonTimeFormatUtil.strToDate(day));
            Boolean isFestival =  hrUserAttendanceStatService.isFestival(day,1);//type类别：1 判断是法定节假日，2 判断是法定加班日
            Boolean isWork =  hrUserAttendanceStatService.isFestival(day,2);

            HrAttendanceAnalStat hrAttendanceAnalStat = CollectionUtil.isNotEmpty(analStatList) ? analStatList.get(0) : new HrAttendanceAnalStat();
            if(isWeek && isWork){   //周末+法定加班日
                hrAttendanceAnalStat.setHolidayDays(1.0);
                hrAttendanceAnalStat.setHolidayFlag(1);
                hrAttendanceAnalStat.setHolidayType(hrUserApplyHoliday.getApplyType());
            }
            if(!isWeek && !isFestival){  //非周末+非法定休息日
                hrAttendanceAnalStat.setHolidayDays(1.0);
                hrAttendanceAnalStat.setHolidayFlag(1);
                hrAttendanceAnalStat.setHolidayType(hrUserApplyHoliday.getApplyType());
            }
            hrAttendanceAnalStatService.saveOrUpdate(hrAttendanceAnalStat);
        });

    }

}
