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
package vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.modular.job.task.CommonHandleUtil;
import vip.xiaonuo.sys.modular.hr.userAttendance.service.HrUserAttendanceService;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity.HrUserAttendanceConfig;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigIdParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.HrUserAttendanceConfigService;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.entity.HrUserAttendanceConfigGroup;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.enums.HrUserAttendanceConfigGroupEnum;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.mapper.HrUserAttendanceConfigGroupMapper;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.param.*;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.service.HrUserAttendanceConfigGroupService;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.entity.HrUserAttendanceRelation;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.service.HrUserAttendanceRelationService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 考勤班次组配置表Service接口实现类
 *
 * @author ljy
 * @date  2023/09/21 15:55
 **/
@Service
public class HrUserAttendanceConfigGroupServiceImpl extends ServiceImpl<HrUserAttendanceConfigGroupMapper, HrUserAttendanceConfigGroup> implements HrUserAttendanceConfigGroupService {

    private static final String HR_ATTENDANCE_CONFIG_KEY = "HR_ATTENDANCE_CONFIG_KEY";

    @Resource
    private HrUserAttendanceService hrUserAttendanceService;
    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    HrUserAttendanceConfigService hrUserAttendanceConfigService;
    @Resource
    HrUserAttendanceRelationService hrUserAttendanceRelationService;
    @Resource
    private SysUserService sysUserService;

    @Override
    public Page<HrUserAttendanceConfigGroup> page(HrUserAttendanceConfigGroupPageParam hrUserAttendanceConfigGroupPageParam) {
        QueryWrapper<HrUserAttendanceConfigGroup> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserAttendanceConfigGroupPageParam.getAttendanceTypeName())) {
            queryWrapper.lambda().eq(HrUserAttendanceConfigGroup::getAttendanceTypeName, hrUserAttendanceConfigGroupPageParam.getAttendanceTypeName());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendanceConfigGroupPageParam.getLeadId())) {
            queryWrapper.lambda().eq(HrUserAttendanceConfigGroup::getLeadId, hrUserAttendanceConfigGroupPageParam.getLeadId());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendanceConfigGroupPageParam.getLeadName())) {
            queryWrapper.lambda().eq(HrUserAttendanceConfigGroup::getLeadName, hrUserAttendanceConfigGroupPageParam.getLeadName());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserAttendanceConfigGroupPageParam.getSortField(), hrUserAttendanceConfigGroupPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserAttendanceConfigGroupPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserAttendanceConfigGroupPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserAttendanceConfigGroupPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserAttendanceConfigGroup::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserAttendanceConfigGroupAddParam hrUserAttendanceConfigGroupAddParam) {
        HrUserAttendanceConfigGroup hrUserAttendanceConfigGroup = BeanUtil.toBean(hrUserAttendanceConfigGroupAddParam, HrUserAttendanceConfigGroup.class);
        this.save(hrUserAttendanceConfigGroup);
        commonCacheOperator.remove(HR_ATTENDANCE_CONFIG_KEY);
        hrUserAttendanceService.attendanceConfigCache();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserAttendanceConfigGroupEditParam hrUserAttendanceConfigGroupEditParam) {
        HrUserAttendanceConfigGroup hrUserAttendanceConfigGroup = this.queryEntity(hrUserAttendanceConfigGroupEditParam.getId());
        BeanUtil.copyProperties(hrUserAttendanceConfigGroupEditParam, hrUserAttendanceConfigGroup);
        this.updateById(hrUserAttendanceConfigGroup);
        commonCacheOperator.remove(HR_ATTENDANCE_CONFIG_KEY);
        hrUserAttendanceService.attendanceConfigCache();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserAttendanceConfigGroupIdParam> hrUserAttendanceConfigGroupIdParamList) {
        // 执行考勤配置组删除，以及删除配置详情
        this.removeByIds(CollStreamUtil.toList(hrUserAttendanceConfigGroupIdParamList, HrUserAttendanceConfigGroupIdParam::getId));

        List<String> groupIdList = hrUserAttendanceConfigGroupIdParamList.stream().map(group -> group.getId()).collect(Collectors.toList());
        List<String> configList = hrUserAttendanceConfigService.list(new LambdaQueryWrapper<HrUserAttendanceConfig>().in(HrUserAttendanceConfig::getAttendanceTypeId,groupIdList))
                .stream().map(config -> config.getId()).collect(Collectors.toList());

        hrUserAttendanceConfigService.removeBatchByIds(configList);
        hrUserAttendanceService.attendanceConfigCache();
    }

    @Override
    public HrUserAttendanceConfigGroup detail(HrUserAttendanceConfigGroupIdParam hrUserAttendanceConfigGroupIdParam) {
        return this.queryEntity(hrUserAttendanceConfigGroupIdParam.getId());
    }

    @Override
    public HrUserAttendanceConfigGroup queryEntity(String id) {
        HrUserAttendanceConfigGroup hrUserAttendanceConfigGroup = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserAttendanceConfigGroup)) {
            throw new CommonException("考勤班次组配置表不存在，id值为：{}", id);
        }
        return hrUserAttendanceConfigGroup;
    }

    @Override
    public Map<String,Object> attendanceConfigGroupMap(HrAttendanceConfigAndUserParam hrAttendanceConfigAndUserParam){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<HrUserAttendanceRelation> queryWrapper = new QueryWrapper<>();
        List<HrUserAttendanceRelation> userAttendanceRelationList = hrUserAttendanceRelationService.list(queryWrapper);
        Map<String,Object> relationMap = new HashMap<>();
        relationMap.put("invalid",userAttendanceRelationList.stream().filter(relation -> relation.getIsValid() == 1));/** 未生效考勤班次*/
        relationMap.put("valid",userAttendanceRelationList.stream().filter(relation -> relation.getIsValid() == 0));/** 生效考勤班次*/
        map.put("relationDat",relationMap);

        map.put("groupData",this.getById(hrAttendanceConfigAndUserParam.getAttendanceTypeId()));/** 负责人数据*/

        return map;
    }

    @Override
    public String configAndUserDataEdit(HrAttendanceConfigAndUserDataParam hrAttendanceConfigAndUserDataParam){
        HrUserAttendanceConfigGroup hrUserAttendanceConfigGroup = this.getById(hrAttendanceConfigAndUserDataParam.getId());
        hrUserAttendanceConfigGroup.setLeadId(hrAttendanceConfigAndUserDataParam.getLeadId());
        this.updateById(hrUserAttendanceConfigGroup);

        List<HrAttendanceConfigAndUserData> hrAttendanceConfigAndUserDataList = hrAttendanceConfigAndUserDataParam.getHrAttendanceConfigAndUserData();

        List<HrUserAttendanceRelation> hrUserAttendanceRelationList = new ArrayList<>();
        hrAttendanceConfigAndUserDataList.stream().forEach(config -> {
            List<String> employIdList = Arrays.asList(config.getEmployIds().split(","));
            Map<String,HrUserAttendanceRelation> relationMap = new HashMap<>();
            hrUserAttendanceRelationService.list(new LambdaQueryWrapper<HrUserAttendanceRelation>().in(HrUserAttendanceRelation::getEmployId,employIdList))
                    .forEach(relation -> relationMap.put(relation.getEmployId(),relation));/** 存在相同数据，更新 */
            for(String employId : employIdList){
                HrUserAttendanceRelation hrUserAttendanceRelation = ObjectUtil.isEmpty(relationMap.get(employId)) ?
                        new HrUserAttendanceRelation() : relationMap.get(employId);
                hrUserAttendanceRelation.setAttendanceTypeId(hrAttendanceConfigAndUserDataParam.getId());
                hrUserAttendanceRelation.setBeginTime(hrAttendanceConfigAndUserDataParam.getBeginTime());
                hrUserAttendanceRelation.setEndTime(hrAttendanceConfigAndUserDataParam.getEndTime());
                hrUserAttendanceRelation.setEmployId(employId);
                hrUserAttendanceRelation.setEmployType(config.getEmployType());
                hrUserAttendanceRelation.setIsValid(ObjectUtil.isEmpty(config.getIsValid()) ? 0 : config.getIsValid());
                hrUserAttendanceRelationList.add(hrUserAttendanceRelation);
            }
        });
        hrUserAttendanceRelationService.saveOrUpdateBatch(hrUserAttendanceRelationList);

        userAttendConfig(hrAttendanceConfigAndUserDataList,hrAttendanceConfigAndUserDataParam.getId());
        /** 重置缓存数据*/
        commonCacheOperator.remove(HR_ATTENDANCE_CONFIG_KEY);
        hrUserAttendanceService.attendanceConfigCache();
        return HrUserAttendanceConfigGroupEnum.SUCCESS.getValue();
    }

    private void userAttendConfig(List<HrAttendanceConfigAndUserData> hrAttendanceConfigAndUserDataList,String attendGroupId){

        //先清理所有人的
        String sql = "update HR_APPLY_WORK_OT set STATUS = null";
        CommonHandleUtil.handleSqlExecution(sql);

        List<SysUser> sysUserList = new ArrayList<>();
        hrAttendanceConfigAndUserDataList.stream().forEach(config -> {
            if(config.getEmployType().equals("2") && StringUtils.isNotEmpty(config.getEmployIds())){ //人员
                List<SysUser> atUser = sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getId,Arrays.asList(config.getEmployType().split(","))));
                sysUserList.addAll(atUser.stream().map(user -> { user.setAttendanceGroupId(attendGroupId);
                    return user;
                }).collect(Collectors.toList()));
            }
            if(config.getEmployType().equals("1") && StringUtils.isNotEmpty(config.getEmployIds())){ //部门
                List<String> orgIdList = Arrays.asList(config.getEmployIds().split(","));
                orgIdList.forEach(orgId -> {
                    List<SysUser> atUser = hrUserAttendanceService.getOrgAndUserList(orgId);
                    if(CollectionUtil.isNotEmpty(atUser)){
                        sysUserList.addAll(atUser.stream().map(user -> { user.setAttendanceGroupId(attendGroupId);
                            return user;
                        }).collect(Collectors.toList()));
                    }
                });
            }
        });
        sysUserService.saveOrUpdateBatch(sysUserList);
    }
}
