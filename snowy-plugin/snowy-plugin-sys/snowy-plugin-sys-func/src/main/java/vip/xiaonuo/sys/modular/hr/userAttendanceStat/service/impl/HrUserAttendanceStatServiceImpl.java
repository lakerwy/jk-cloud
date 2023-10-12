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
package vip.xiaonuo.sys.modular.hr.userAttendanceStat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonTimeFormatUtil;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.HrUserAttendance;
import vip.xiaonuo.sys.modular.hr.userAttendance.enums.HrUserAttendanceEnum;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendancePageParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.AttendanceConfigCacheEntity;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.UserAttendanceConfigEntity;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.UserAttendanceCountEntity;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity.HrUserAttendanceConfig;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.HrUserAttendanceConfigService;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.entity.HrUserAttendanceConfigGroup;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.service.HrUserAttendanceConfigGroupService;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity.HrUserAttendanceStat;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.HrUserAttendanceStatEvent;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.mapper.HrUserAttendanceStatMapper;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatEditParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatIdParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatPageParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.service.HrUserAttendanceStatService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 考勤统计表Service接口实现类
 *
 * @author ljy
 * @date  2023/10/07 10:28
 **/
@Service
public class HrUserAttendanceStatServiceImpl extends ServiceImpl<HrUserAttendanceStatMapper, HrUserAttendanceStat> implements HrUserAttendanceStatService {

    private final String ON_DUTY = "OnDuty";
    private final String OFF_DUTY = "OffDuty";
    private static final String HR_ATTENDANCE_CONFIG_KEY = "HR_ATTENDANCE_CONFIG_KEY";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Resource
    HrUserAttendanceStatService hrUserAttendanceStatService;
    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private HrUserAttendanceConfigService hrUserAttendanceConfigService;
    @Resource
    private HrUserAttendanceConfigGroupService hrUserAttendanceConfigGroupService;
    @Resource
    private HrUserAttendanceService hrUserAttendanceService;

    @Override
    public Page<HrUserAttendanceStat> page(HrUserAttendanceStatPageParam hrUserAttendanceStatPageParam) {
        QueryWrapper<HrUserAttendanceStat> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserAttendanceStatPageParam.getUserId())) {
            queryWrapper.lambda().eq(HrUserAttendanceStat::getUserId, hrUserAttendanceStatPageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendanceStatPageParam.getMachineCode())) {
            queryWrapper.lambda().eq(HrUserAttendanceStat::getMachineCode, hrUserAttendanceStatPageParam.getMachineCode());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendanceStatPageParam.getCheckType())) {
            queryWrapper.lambda().eq(HrUserAttendanceStat::getCheckType, hrUserAttendanceStatPageParam.getCheckType());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendanceStatPageParam.getIsResubmitAttendanceFlag())) {
            queryWrapper.lambda().eq(HrUserAttendanceStat::getIsResubmitAttendanceFlag, hrUserAttendanceStatPageParam.getIsResubmitAttendanceFlag());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserAttendanceStatPageParam.getSortField(), hrUserAttendanceStatPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserAttendanceStatPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserAttendanceStatPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserAttendanceStatPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserAttendanceStat::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserAttendanceStatAddParam hrUserAttendanceStatAddParam) {
        HrUserAttendanceStat hrUserAttendanceStat = BeanUtil.toBean(hrUserAttendanceStatAddParam, HrUserAttendanceStat.class);
        this.save(hrUserAttendanceStat);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserAttendanceStatEditParam hrUserAttendanceStatEditParam) {
        HrUserAttendanceStat hrUserAttendanceStat = this.queryEntity(hrUserAttendanceStatEditParam.getId());
        BeanUtil.copyProperties(hrUserAttendanceStatEditParam, hrUserAttendanceStat);
        this.updateById(hrUserAttendanceStat);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserAttendanceStatIdParam> hrUserAttendanceStatIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserAttendanceStatIdParamList, HrUserAttendanceStatIdParam::getId));
    }

    @Override
    public HrUserAttendanceStat detail(HrUserAttendanceStatIdParam hrUserAttendanceStatIdParam) {
        return this.queryEntity(hrUserAttendanceStatIdParam.getId());
    }

    @Override
    public HrUserAttendanceStat queryEntity(String id) {
        HrUserAttendanceStat hrUserAttendanceStat = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserAttendanceStat)) {
            throw new CommonException("考勤统计表不存在，id值为：{}", id);
        }
        return hrUserAttendanceStat;
    }

    /**
     * 异步处理打卡记录到考勤统计表，每人每天存在统计表的数据不能超过配置次数
     * */
    @EventListener
    @Async
    public void handleAttendanceEvent(HrUserAttendanceStatEvent hrUserAttendanceStatEvent){
        HrUserAttendanceStat hrUserAttendanceStat = hrUserAttendanceStatService.getOne(new LambdaQueryWrapper<HrUserAttendanceStat>()
                .eq(HrUserAttendanceStat::getUserId, hrUserAttendanceStatEvent.getUserId()).
                eq(HrUserAttendanceStat::getCheckType, hrUserAttendanceStatEvent.getCheckType())
                .between(HrUserAttendanceStat::getCheckTime, DateUtil.beginOfDay(hrUserAttendanceStatEvent.getCheckTime()), DateUtil.endOfDay(hrUserAttendanceStatEvent.getCheckTime())));
        /**
         * 查询stat统计表中是否存在当前时段的打卡记录，
         * 若不存在，则直接存stat表
         * 若存在，判断是属于上班(取最早数据)，还是属于下班(取最晚数据)
         * */
        if(ObjectUtil.isEmpty(hrUserAttendanceStat)){
            this.save(BeanUtil.toBean(hrUserAttendanceStatEvent, HrUserAttendanceStat.class));
            return;
        }

        if(ON_DUTY.equals(hrUserAttendanceStatEvent.getCheckType())){
            if(hrUserAttendanceStatEvent.getCheckTime().before(hrUserAttendanceStat.getCheckTime())){
                this.removeById(hrUserAttendanceStat.getId());
                this.save(BeanUtil.toBean(hrUserAttendanceStatEvent, HrUserAttendanceStat.class));
            }
            return;
        }
        if(hrUserAttendanceStatEvent.getCheckTime().after(hrUserAttendanceStat.getCheckTime())){
            this.removeById(hrUserAttendanceStat.getId());
            this.save(BeanUtil.toBean(hrUserAttendanceStatEvent, HrUserAttendanceStat.class));
            return;
        }
    }

    @Override
    public Map<String,Object> dayStatInfo(){
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        Map<String,Object> map = new HashMap<>();
        Date beginDate = DateUtil.beginOfDay(DateUtil.date());
        Date endDate = DateUtil.endOfDay(DateUtil.date());
        List<HrUserAttendanceStat> hrUserAttendanceStatList = this.list(new QueryWrapper<HrUserAttendanceStat>().lambda()
                .eq(HrUserAttendanceStat::getUserId,loginUser.getId())
                .between(HrUserAttendanceStat::getCheckTime,beginDate,endDate));
        List<UserAttendanceConfigEntity> userAttendanceConfigEntityList = getUserConfigCachData(loginUser.getId());
        List<Map<String,Object>> mapList = new ArrayList<>();
        for(UserAttendanceConfigEntity entity : userAttendanceConfigEntityList){
            Map<String,Object> attendanceMap = new HashMap<>();
            attendanceMap.put("DutyType",entity.getDutyType());
            attendanceMap.put("checkTime",entity.getCheckTime());
            attendanceMap.put("attendanceTime","");
            attendanceMap.put("status","0");//状态
            for(HrUserAttendanceStat stat : hrUserAttendanceStatList){
                if(entity.getDutyType().equals(stat.getCheckType())){
                    attendanceMap.put("attendanceTime",stat.getCheckTime());
                    if(stat.getAbnormalFlag() == 2){
                        attendanceMap.put("status",stat.equals("OnDuty") ? "1" : "2");
                    }
                }
            }
            mapList.add(attendanceMap);
        }
        map.put("dayAttendanceNum",hrUserAttendanceStatList.size());//已经打卡次数
        map.put("attendance",mapList);
        return map;
    }

    @Override
    public List<UserAttendanceConfigEntity> getUserConfigCachData(String userId){ //获取打卡次数
        String cache = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_KEY));
        if(StringUtils.isNotEmpty(cache)){
            List<AttendanceConfigCacheEntity> configCacheEntityList = JSONArray.parseArray(cache, AttendanceConfigCacheEntity.class);
            for(AttendanceConfigCacheEntity entity : configCacheEntityList){
                if(entity.getUserIds().contains(userId)){
                    return entity.getUserAttendanceConfigEntityList();
                }
            }
        }
        List<HrUserAttendanceConfigGroup>  hrUserAttendanceConfigGroupList = hrUserAttendanceConfigGroupService.list(new QueryWrapper<HrUserAttendanceConfigGroup>()
                .lambda().eq(HrUserAttendanceConfigGroup::getIsDefaultConfig,"0"));

        List<HrUserAttendanceConfig> configList = hrUserAttendanceConfigService.list(new QueryWrapper<HrUserAttendanceConfig>().lambda()
                .eq(HrUserAttendanceConfig::getAttendanceTypeId,hrUserAttendanceConfigGroupList.get(0).getId()));
        return configList.stream().map(config -> BeanUtil.toBean(config, UserAttendanceConfigEntity.class)).collect(Collectors.toList());
    }

    @Override
    public Map<String,List<HrUserAttendance>> attendanceDayInfo(HrUserAttendancePageParam hrUserAttendancePageParam){
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        hrUserAttendancePageParam.setUserId(loginUser.getId());
        if(StringUtils.isEmpty(hrUserAttendancePageParam.getStartTime()) || StringUtils.isEmpty(hrUserAttendancePageParam.getEndTime())){
            return new HashMap<>();
        }
        List<HrUserAttendance> hrUserAttendanceList = hrUserAttendanceService.list(new LambdaQueryWrapper<HrUserAttendance>()
                .eq(HrUserAttendance::getUserId,loginUser.getId())
                .between(HrUserAttendance::getCheckTime,hrUserAttendancePageParam.getStartTime(),hrUserAttendancePageParam.getEndTime())
                .orderByAsc(HrUserAttendance::getCheckTime));

        List<HrUserAttendanceStat> hrUserAttendanceStatList = hrUserAttendanceStatService.list(new LambdaQueryWrapper<HrUserAttendanceStat>()
                .eq(HrUserAttendanceStat::getUserId,loginUser.getId())
                .between(HrUserAttendanceStat::getCheckTime,hrUserAttendancePageParam.getStartTime(),hrUserAttendancePageParam.getEndTime())
                .orderByAsc(HrUserAttendanceStat::getCheckTime));

        Map<String,HrUserAttendance> statMap = new HashMap<>();/** 判断考勤数据是否存在迟到或者早退*/
        hrUserAttendanceStatList.forEach(stat -> {
            if(stat.getCheckType().equals(ON_DUTY)){
                stat.setAbnormalFlag(3);//迟到
            }else if(stat.getCheckType().equals(OFF_DUTY)){
                stat.setAbnormalFlag(4);//早退
            }
            statMap.put(stat.getCheckType(),BeanUtil.toBean(stat,HrUserAttendance.class));
        });
        List<UserAttendanceConfigEntity> userAttendanceConfigEntityList = hrUserAttendanceStatService.getUserConfigCachData(loginUser.getId());/** 获取打卡配置*/

        Map<String,List<HrUserAttendance>> map = new HashMap<>();
        for(int i=0;i<userAttendanceConfigEntityList.size();i++){
            String dutyType = userAttendanceConfigEntityList.get(i).getDutyType();
            HrUserAttendance hrUserAttendance = statMap.get(dutyType);
            List<HrUserAttendance> list = new ArrayList<>();
            list.add(hrUserAttendance);
            if(ObjectUtil.isEmpty(hrUserAttendance)){
                list = hrUserAttendanceList.stream().filter(attendance -> attendance.getCheckType().equals(dutyType)
                ).collect(Collectors.toList());
                /** 防止打卡记录表存在数据，而统计表没有数据，且对数据做异常判断 */
                if(CollectionUtil.isNotEmpty(list) && list.get(0).getAbnormalFlag() == 2){
                    list.get(0).setAbnormalFlag(3);
                }
                map.put(String.valueOf(i),list);
            }else{
                list.addAll(hrUserAttendanceList.stream().filter(attendance ->
                        attendance.getCheckType().equals(dutyType) && attendance.getCheckTime().compareTo(hrUserAttendance.getCheckTime()) != 0
                ).collect(Collectors.toList()));
                map.put(String.valueOf(i),list);
            }
        }
        return map;
    }


    @Override
    public List<UserAttendanceCountEntity> attendanceMonthCount(HrUserAttendancePageParam hrUserAttendancePageParam) {
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();

        if(StringUtils.isEmpty(hrUserAttendancePageParam.getStartTime()) || StringUtils.isEmpty(hrUserAttendancePageParam.getEndTime())){
            return new ArrayList<>();
        }
        /** 获取当月有效工作日时间列表 */
        List<HrUserAttendance> hrUserAttendanceList = hrUserAttendanceService.list(new LambdaQueryWrapper<HrUserAttendance>().eq(HrUserAttendance::getUserId,loginUser.getId())
                .between(HrUserAttendance::getCheckTime,hrUserAttendancePageParam.getStartTime(),hrUserAttendancePageParam.getEndTime()));

        /**  list循环一次，获取-1  循环二次获取 1*/
        Map<String,UserAttendanceCountEntity> map = new HashMap<>();
        for(HrUserAttendance hrUserAttendance : hrUserAttendanceList){
            String day = CommonTimeFormatUtil.dateToStr(hrUserAttendance.getCheckTime(),DATE_FORMAT);
            map.put(day,new UserAttendanceCountEntity("",hrUserAttendance.getAbnormalFlag() != null ? "0" : "1",day,hrUserAttendance.getCheckTime()));
        }
        List<HrUserAttendance> userAttendances = hrUserAttendanceList.stream().filter(attendance ->
                attendance.getAbnormalFlag() != null && attendance.getAbnormalFlag() == 2).collect(Collectors.toList());/**  筛选打卡异常的数据*/
        /** 打卡异常的数据 */
        userAttendances.forEach(attendance ->  {
            String day = CommonTimeFormatUtil.dateToStr(attendance.getCheckTime(),DATE_FORMAT);
            if(map.get(day) != null){
                map.get(day).setDayAttendanceStatus("1");
            }
        });
        return map.keySet().stream().map(map::get).collect(Collectors.toList());
    }

}
