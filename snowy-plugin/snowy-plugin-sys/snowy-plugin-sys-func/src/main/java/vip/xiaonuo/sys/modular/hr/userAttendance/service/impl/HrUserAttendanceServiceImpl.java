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
package vip.xiaonuo.sys.modular.hr.userAttendance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.util.CommonTimeFormatUtil;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.HrUserAttendance;
import vip.xiaonuo.sys.modular.hr.userAttendance.enums.HrUserAttendanceEnum;
import vip.xiaonuo.sys.modular.hr.userAttendance.mapper.HrUserAttendanceMapper;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceEditParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceIdParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendancePageParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity.HrUserAttendanceConfig;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigPageParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.HrUserAttendanceConfigService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 打卡记录表Service接口实现类
 *
 * @author ljy
 * @date  2023/09/07 14:24
 **/
@Service
public class HrUserAttendanceServiceImpl extends ServiceImpl<HrUserAttendanceMapper, HrUserAttendance> implements HrUserAttendanceService {

    @Autowired
    private HrUserAttendanceConfigService hrUserAttendanceConfigService;

    @Override
    public Page<HrUserAttendance> page(HrUserAttendancePageParam hrUserAttendancePageParam) {
        QueryWrapper<HrUserAttendance> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserAttendancePageParam.getUserId())) {
            queryWrapper.lambda().eq(HrUserAttendance::getUserId, hrUserAttendancePageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendancePageParam.getMachineId())) {
            queryWrapper.lambda().eq(HrUserAttendance::getMachineCode, hrUserAttendancePageParam.getMachineId());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendancePageParam.getCheckType())) {
            queryWrapper.lambda().eq(HrUserAttendance::getCheckType, hrUserAttendancePageParam.getCheckType());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserAttendancePageParam.getSortField(), hrUserAttendancePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserAttendancePageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserAttendancePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserAttendancePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserAttendance::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserAttendanceAddParam hrUserAttendanceAddParam) {
        HrUserAttendance hrUserAttendance = BeanUtil.toBean(hrUserAttendanceAddParam, HrUserAttendance.class);
        this.save(hrUserAttendance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String attendance(HrUserAttendanceAddParam hrUserAttendanceAddParam){
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        hrUserAttendanceAddParam.setUserId(loginUser.getId());
        if(ObjectUtil.isEmpty(hrUserAttendanceAddParam.getCheckTime())){
            hrUserAttendanceAddParam.setCheckTime(new Date());
        }
        Date checkTime = hrUserAttendanceAddParam.getCheckTime();//当前打卡时间
        HrUserAttendance hrUserAttendance = BeanUtil.toBean(hrUserAttendanceAddParam, HrUserAttendance.class);

        //待优化部分：打卡班次设置，后续启用租户,职位，角色等概念
        Date dateNow = new Date();
        Page<HrUserAttendanceConfig> pageList = hrUserAttendanceConfigService.page(new HrUserAttendanceConfigPageParam());
        List<HrUserAttendanceConfig> configList = pageList.getRecords();
        for(HrUserAttendanceConfig config : configList){
            config.setOnDutyCheckTime(CommonTimeFormatUtil.yearAndHourConnect(CommonTimeFormatUtil.dateToStr(dateNow,"yyyy-MM-dd"),CommonTimeFormatUtil.dateToStr(config.getOnDutyCheckTime(),"HH:mm:ss")));//上班时间
            config.setOffDutyCheckTime(CommonTimeFormatUtil.yearAndHourConnect(CommonTimeFormatUtil.dateToStr(dateNow,"yyyy-MM-dd"),CommonTimeFormatUtil.dateToStr(config.getOffDutyCheckTime(),"HH:mm:ss")));//下班时间
        }

        //上班时间到下班时间区间打卡，都属于异常打卡  (有效考勤数据来源于打卡数据)
        // 0 正常，1 迟到，2 早退
        List<HrUserAttendanceConfig> filterConfigList = configList.stream().filter(config -> checkTime.after(config.getOnDutyCheckTime()) && checkTime.before(config.getOffDutyCheckTime())).collect(Collectors.toList());
        if(!ObjectUtil.isEmpty(filterConfigList)){
            List<String> dutyStatus = filterConfigList.stream().map(config ->
                    CommonTimeFormatUtil.twoTimeDiffSecond(checkTime,config.getOnDutyCheckTime()) >= CommonTimeFormatUtil.twoTimeDiffSecond(config.getOffDutyCheckTime(),checkTime) ?
                            HrUserAttendanceEnum.ATTENDANCE_OFF_DUTY.getValue() : HrUserAttendanceEnum.ATTENDANCE_ON_DUTY.getValue()).collect(Collectors.toList());
            hrUserAttendance.setAbnormalFlag(1);
            hrUserAttendance.setCheckType(dutyStatus.get(0));
            this.save(hrUserAttendance);
            return  dutyStatus.get(0).equals(HrUserAttendanceEnum.ATTENDANCE_ON_DUTY.getValue()) ? HrUserAttendanceEnum.ATTENDANCE_LATE.getValue() : HrUserAttendanceEnum.ATTENDANCE_LEAVE_EARLY.getValue();
        }else{
            hrUserAttendance.setCheckType(getChectStatus(configList,checkTime));
            hrUserAttendance.setAbnormalFlag(0);
        }
        this.save(hrUserAttendance);
        return HrUserAttendanceEnum.ATTENDANCE_NORMAL.getValue();
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserAttendanceEditParam hrUserAttendanceEditParam) {
        HrUserAttendance hrUserAttendance = this.queryEntity(hrUserAttendanceEditParam.getId());
        BeanUtil.copyProperties(hrUserAttendanceEditParam, hrUserAttendance);
        this.updateById(hrUserAttendance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserAttendanceIdParam> hrUserAttendanceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserAttendanceIdParamList, HrUserAttendanceIdParam::getId));
    }

    @Override
    public HrUserAttendance detail(HrUserAttendanceIdParam hrUserAttendanceIdParam) {
        return this.queryEntity(hrUserAttendanceIdParam.getId());
    }

    @Override
    public HrUserAttendance queryEntity(String id) {
        HrUserAttendance hrUserAttendance = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserAttendance)) {
            throw new CommonException("打卡记录表不存在，id值为：{}", id);
        }
        return hrUserAttendance;
    }

    public String getChectStatus(List<HrUserAttendanceConfig> configList,Date checkTime){
        String dutyStatus = "";
        String preDutyStatus = "";
        long preTwoTimeDiffSecond = 0L;
        for(HrUserAttendanceConfig config : configList){
            long onSecond = CommonTimeFormatUtil.twoTimeDiffSecond(config.getOnDutyCheckTime(),checkTime);
            long offSceond =  CommonTimeFormatUtil.twoTimeDiffSecond(config.getOnDutyCheckTime(),checkTime);
            long onOrOffSceond = onSecond > offSceond ? offSceond : onSecond;
            String onOrOffStatus = onSecond > offSceond ? "OffDuty" : "OnDuty";
            dutyStatus = onOrOffStatus;

            //和上一个对比
            if(StringUtils.isNotEmpty(preDutyStatus)){
                preTwoTimeDiffSecond = onOrOffSceond > preTwoTimeDiffSecond ? preTwoTimeDiffSecond : onOrOffSceond;
                dutyStatus = preTwoTimeDiffSecond > onOrOffSceond ?  onOrOffStatus : preDutyStatus;
                preDutyStatus = dutyStatus;
                continue;
            }
            preDutyStatus = onOrOffStatus;
            preTwoTimeDiffSecond = onOrOffSceond;
        }
        return dutyStatus;
    }
}
