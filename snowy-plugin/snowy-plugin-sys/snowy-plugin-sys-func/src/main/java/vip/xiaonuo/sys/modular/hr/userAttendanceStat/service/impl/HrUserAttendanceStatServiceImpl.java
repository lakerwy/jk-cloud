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
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.AttendanceConfigCacheEntity;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.AttendanceTimeSlot;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.UserAttendanceConfigEntity;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity.HrUserAttendanceConfig;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.HrUserAttendanceConfigService;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.entity.HrUserAttendanceConfigGroup;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.service.HrUserAttendanceConfigGroupService;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity.HrUserAttendanceStat;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity.HrUserAttendanceStatEvent;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.enums.HrUserAttendanceStatEnum;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.mapper.HrUserAttendanceStatMapper;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatEditParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatIdParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.HrUserAttendanceStatPageParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.service.HrUserAttendanceStatService;

import javax.annotation.Resource;
import java.util.*;

/**
 * 考勤统计表Service接口实现类
 *
 * @author ljy
 * @date  2023/10/07 10:28
 **/
@Service
public class HrUserAttendanceStatServiceImpl extends ServiceImpl<HrUserAttendanceStatMapper, HrUserAttendanceStat> implements HrUserAttendanceStatService {

    private final String ON_DUTY = "OnDuty";
    private static final String HR_ATTENDANCE_CONFIG_KEY = "HR_ATTENDANCE_CONFIG_KEY";

    @Resource
    HrUserAttendanceStatService hrUserAttendanceStatService;
    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Autowired
    private HrUserAttendanceConfigService hrUserAttendanceConfigService;
    @Autowired
    private HrUserAttendanceConfigGroupService hrUserAttendanceConfigGroupService;

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
        String time = DateUtil.format(hrUserAttendanceStatEvent.getCheckTime(), DatePattern.NORM_DATE_PATTERN);
        /**  查询 */
        QueryWrapper<HrUserAttendanceStat> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(HrUserAttendanceStat::getUserId, hrUserAttendanceStatEvent.getUserId()).
                eq(HrUserAttendanceStat::getCheckType, hrUserAttendanceStatEvent.getCheckType()).
                between(HrUserAttendanceStat::getCheckTime, time+ HrUserAttendanceStatEnum.BEGIN_TIMNE.getValue(), time+HrUserAttendanceStatEnum.END_TIME.getValue());
        HrUserAttendanceStat hrUserAttendanceStat = hrUserAttendanceStatService.getOne(queryWrapper);
        if(ObjectUtil.isEmpty(hrUserAttendanceStat)){/** 若无记录，则直接存 */
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
        Integer dayAttendanceNum = getUserConfigCachCount(loginUser.getId());
        map.put("dayAttendanceNum",dayAttendanceNum);
        map.put("attendance",hrUserAttendanceStatList);
        return map;
    }

    private Integer getUserConfigCachCount(String userId){ //获取打卡次数
        String cache = String.valueOf(commonCacheOperator.get(HR_ATTENDANCE_CONFIG_KEY));
        if(StringUtils.isNotEmpty(cache)){
            List<AttendanceConfigCacheEntity> configCacheEntityList = JSONArray.parseArray(cache, AttendanceConfigCacheEntity.class);
            for(AttendanceConfigCacheEntity entity : configCacheEntityList){
                if(entity.getUserIds().contains(userId)){
                    List<UserAttendanceConfigEntity> userAttendanceConfigEntityList = entity.getUserAttendanceConfigEntityList();
                    return userAttendanceConfigEntityList.size();
                }
            }
        }
        List<HrUserAttendanceConfigGroup>  hrUserAttendanceConfigGroupList = hrUserAttendanceConfigGroupService.list(new QueryWrapper<HrUserAttendanceConfigGroup>()
                .lambda().eq(HrUserAttendanceConfigGroup::getIsDefaultConfig,"0"));

        List<HrUserAttendanceConfig> configList = hrUserAttendanceConfigService.list(new QueryWrapper<HrUserAttendanceConfig>().lambda()
                .eq(HrUserAttendanceConfig::getAttendanceTypeId,hrUserAttendanceConfigGroupList.get(0).getId()));
        return configList.size();
    }

}
