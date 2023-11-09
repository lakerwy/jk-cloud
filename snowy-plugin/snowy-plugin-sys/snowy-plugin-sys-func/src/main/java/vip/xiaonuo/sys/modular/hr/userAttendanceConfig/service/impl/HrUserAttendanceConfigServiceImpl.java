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
package vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity.HrUserAttendanceConfig;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.*;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.vo.HrUserAttendanceConfigEntity;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.enums.HrUserAttendanceConfigEnum;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.mapper.HrUserAttendanceConfigMapper;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.HrUserAttendanceConfigService;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.entity.HrUserAttendanceConfigGroup;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.service.HrUserAttendanceConfigGroupService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 打卡班次配置表Service接口实现类
 *
 * @author ljy
 * @date  2023/09/07 13:07
 **/
@Service
@Slf4j
public class HrUserAttendanceConfigServiceImpl extends ServiceImpl<HrUserAttendanceConfigMapper, HrUserAttendanceConfig> implements HrUserAttendanceConfigService {

    private static final String HR_ATTENDANCE_CONFIG_KEY = "HR_ATTENDANCE_CONFIG_KEY";

    @Resource
    private HrUserAttendanceService hrUserAttendanceService;
    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    HrUserAttendanceConfigMapper hrUserAttendanceConfigMapper;
    @Resource
    HrUserAttendanceConfigGroupService hrUserAttendanceConfigGroupService;

    @Override
    public Page<HrUserAttendanceConfig> page(HrUserAttendanceConfigPageParam hrUserAttendanceConfigPageParam) {
        QueryWrapper<HrUserAttendanceConfig> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserAttendanceConfigPageParam.getTenantId())) {
            queryWrapper.lambda().eq(HrUserAttendanceConfig::getTenantId, hrUserAttendanceConfigPageParam.getTenantId());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserAttendanceConfigPageParam.getSortField(), hrUserAttendanceConfigPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserAttendanceConfigPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserAttendanceConfigPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserAttendanceConfigPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserAttendanceConfig::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }


    /**  必传值：orderNumber[排序-用户选择]，attendanceTypeName[考勤类型-取字典表]，dutyType[班次类型-取字典表]，beginMinTime[提前时间-用户计算填写]，checkTime[班次时间-用户填写]，endMinTime[延迟时间-用户填写]，workHours[工时-用户计算填写]*/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserAttendanceConfigListAddParam hrUserAttendanceConfigListAddParam) {
        HrUserAttendanceConfigGroup hrUserAttendanceConfigGroup = hrUserAttendanceConfigGroupService.getById(hrUserAttendanceConfigListAddParam.getAttendanceTypeId());
        if(ObjectUtil.isEmpty(hrUserAttendanceConfigGroup)){
            hrUserAttendanceConfigGroup.setLeadId(hrUserAttendanceConfigListAddParam.getLeadId());
            hrUserAttendanceConfigGroupService.saveOrUpdate(hrUserAttendanceConfigGroup);
        }

        List<HrUserAttendanceConfig> hrUserAttendanceConfigList = hrUserAttendanceConfigListAddParam.getHrUserAttendanceConfigAddParamList().stream().map(config ->
            BeanUtil.toBean(config, HrUserAttendanceConfig.class)).collect(Collectors.toList());
        this.saveBatch(hrUserAttendanceConfigList);
        //HrUserAttendanceConfig hrUserAttendanceConfig = BeanUtil.toBean(hrUserAttendanceConfigAddParam, HrUserAttendanceConfig.class);
        //this.save(hrUserAttendanceConfig);
        /** 先删除原有缓存，再计算最新的缓存 */
        commonCacheOperator.remove(HR_ATTENDANCE_CONFIG_KEY);
        hrUserAttendanceService.attendanceConfigCache();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserAttendanceConfigEditParam hrUserAttendanceConfigEditParam) {
        HrUserAttendanceConfig hrUserAttendanceConfig = this.queryEntity(hrUserAttendanceConfigEditParam.getId());
        BeanUtil.copyProperties(hrUserAttendanceConfigEditParam, hrUserAttendanceConfig);
        this.updateById(hrUserAttendanceConfig);
        /** 先删除原有缓存，再计算最新的缓存 */
        commonCacheOperator.remove(HR_ATTENDANCE_CONFIG_KEY);
        hrUserAttendanceService.attendanceConfigCache();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserAttendanceConfigIdParam> hrUserAttendanceConfigIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserAttendanceConfigIdParamList, HrUserAttendanceConfigIdParam::getId));
    }

    @Override
    public HrUserAttendanceConfig detail(HrUserAttendanceConfigIdParam hrUserAttendanceConfigIdParam) {
        return this.queryEntity(hrUserAttendanceConfigIdParam.getId());
    }

    @Override
    public HrUserAttendanceConfig queryEntity(String id) {
        HrUserAttendanceConfig hrUserAttendanceConfig = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserAttendanceConfig)) {
            throw new CommonException("打卡班次配置表不存在，id值为：{}", id);
        }
        return hrUserAttendanceConfig;
    }

    @Override
    public Map<String,Object> attendanceConfigList(HrUserAttendanceConfigPageParam hrUserAttendanceConfigPageParam){
        if(StringUtils.isEmpty(hrUserAttendanceConfigPageParam.getAttendanceTypeId())){
            return new HashMap<>();
        }
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<HrUserAttendanceConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ATTENDANCE_TYPE_ID",hrUserAttendanceConfigPageParam.getAttendanceTypeId());
        map.put("attendanceConfigList",hrUserAttendanceConfigMapper.selectList(queryWrapper));
        map.put("elasticity","");
        map.put("seriousLate","");
        map.put("absenteeismLate","");
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String editConfig(HrUserAttendanceConfigEditParam hrUserAttendanceConfigEditParam){
        /** 修改考勤配置组 */
        if(StringUtils.isNotEmpty(hrUserAttendanceConfigEditParam.getAttendanceTypeName()) && ObjectUtil.isNotEmpty(hrUserAttendanceConfigEditParam.getAttendanceTypeId())){
            HrUserAttendanceConfigGroup hrUserAttendanceConfigGroup = hrUserAttendanceConfigGroupService.getById(hrUserAttendanceConfigEditParam.getAttendanceTypeId());
            if(ObjectUtil.isNotEmpty(hrUserAttendanceConfigGroup)){
                hrUserAttendanceConfigGroup.setAttendanceTypeName(hrUserAttendanceConfigEditParam.getAttendanceTypeName());
                hrUserAttendanceConfigGroup.setLeadId(hrUserAttendanceConfigEditParam.getLeadId());
                hrUserAttendanceConfigGroup.setLeadName(hrUserAttendanceConfigEditParam.getLeadName());
                hrUserAttendanceConfigGroupService.updateById(hrUserAttendanceConfigGroup);
            }
        }
        List<HrUserAttendanceConfigEntity> attendanceConfigEntityList = ObjectUtil.isNotEmpty(hrUserAttendanceConfigEditParam.getUserAttendanceConfigEntityList()) ?
                hrUserAttendanceConfigEditParam.getUserAttendanceConfigEntityList() : new ArrayList<>();
        attendanceConfigEntityList.stream().forEach(entity -> entity.setAttendanceTypeName(hrUserAttendanceConfigEditParam.getAttendanceTypeName()));
        List<HrUserAttendanceConfig> attendanceConfigList = attendanceConfigEntityList.stream().map(entity -> BeanUtil.toBean(entity, HrUserAttendanceConfig.class)).collect(Collectors.toList());
        log.info("editConfig更新条数-----"+attendanceConfigList.size());
        if(attendanceConfigList.size() == 0){
            return "1";
        }
        return this.saveOrUpdateBatch(attendanceConfigList) ? "1" : "-1";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String addList(HrUserAttendanceConfigEditParam hrUserAttendanceConfigEditParam){
        if(ObjectUtil.isEmpty(hrUserAttendanceConfigEditParam)){
            return HrUserAttendanceConfigEnum.FAIL.getValue();
        }
        HrUserAttendanceConfigGroup hrUserAttendanceConfigGroup = new HrUserAttendanceConfigGroup();
        hrUserAttendanceConfigGroup.setAttendanceTypeName(hrUserAttendanceConfigEditParam.getAttendanceTypeName());
        hrUserAttendanceConfigGroup.setLeadId(hrUserAttendanceConfigEditParam.getLeadId());
        hrUserAttendanceConfigGroup.setLeadName(hrUserAttendanceConfigEditParam.getLeadName());
        hrUserAttendanceConfigGroup.setAttendanceUserNum(hrUserAttendanceConfigEditParam.getAttendanceUserNum());
        hrUserAttendanceConfigGroupService.save(hrUserAttendanceConfigGroup);
        String id = hrUserAttendanceConfigGroup.getId();

        List<HrUserAttendanceConfig> hrUserAttendanceConfigList = hrUserAttendanceConfigEditParam.getUserAttendanceConfigEntityList().stream()
                .map(param -> {
                    param.setAttendanceTypeName(hrUserAttendanceConfigEditParam.getAttendanceTypeName());
                    param.setAttendanceTypeId(id);
                   return BeanUtil.toBean(param, HrUserAttendanceConfig.class);
                }).collect(Collectors.toList());
        setAttendanceConfig(hrUserAttendanceConfigList);
        return this.saveBatch(hrUserAttendanceConfigList) ? HrUserAttendanceConfigEnum.SUCESS.getValue() : HrUserAttendanceConfigEnum.FAIL.getValue();
    }

    private void setAttendanceConfig(List<HrUserAttendanceConfig> hrUserAttendanceConfigList){

        if(hrUserAttendanceConfigList.size()%2 == 1 && hrUserAttendanceConfigList.size() == 3){
            //班次打卡类别 (上班OnDuty，下班 OffDuty，休息 REST)
            hrUserAttendanceConfigList.get(0).setDutyType("OnDuty");
            hrUserAttendanceConfigList.get(0).setOrderNumber(1);
            hrUserAttendanceConfigList.get(1).setDutyType("REST");
            hrUserAttendanceConfigList.get(1).setOrderNumber(2);
            hrUserAttendanceConfigList.get(2).setDutyType("OffDuty");
            hrUserAttendanceConfigList.get(2).setOrderNumber(3);
        }

        if(hrUserAttendanceConfigList.size()%2 == 0){
            for(int i=0;i<hrUserAttendanceConfigList.size();i++){
                if(i%2 == 0){
                    hrUserAttendanceConfigList.get(i).setDutyType("OnDuty");
                    hrUserAttendanceConfigList.get(i).setOrderNumber(i+1);
                }
                if(i%2 == 1){
                    hrUserAttendanceConfigList.get(i).setDutyType("OffDuty");
                    hrUserAttendanceConfigList.get(i).setOrderNumber(i+1);
                }
            }
        }

    }
}
