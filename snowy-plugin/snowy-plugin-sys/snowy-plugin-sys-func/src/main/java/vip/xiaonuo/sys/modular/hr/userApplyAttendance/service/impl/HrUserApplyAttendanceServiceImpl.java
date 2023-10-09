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
package vip.xiaonuo.sys.modular.hr.userApplyAttendance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FormRelationTableApi;
import vip.xiaonuo.sys.modular.hr.userApplyAttendance.entity.HrUserApplyAttendance;
import vip.xiaonuo.sys.modular.hr.userApplyAttendance.mapper.HrUserApplyAttendanceMapper;
import vip.xiaonuo.sys.modular.hr.userApplyAttendance.param.HrUserApplyAttendanceAddParam;
import vip.xiaonuo.sys.modular.hr.userApplyAttendance.param.HrUserApplyAttendanceEditParam;
import vip.xiaonuo.sys.modular.hr.userApplyAttendance.param.HrUserApplyAttendanceIdParam;
import vip.xiaonuo.sys.modular.hr.userApplyAttendance.param.HrUserApplyAttendancePageParam;
import vip.xiaonuo.sys.modular.hr.userApplyAttendance.service.HrUserApplyAttendanceService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 补卡或远程打卡Service接口实现类
 *
 * @author ljy
 * @date  2023/09/12 09:17
 **/
@Service
public class HrUserApplyAttendanceServiceImpl extends ServiceImpl<HrUserApplyAttendanceMapper, HrUserApplyAttendance> implements HrUserApplyAttendanceService {


    @Resource
    CommonCacheOperator commonCacheOperator;

    @Override
    public Page<HrUserApplyAttendance> page(HrUserApplyAttendancePageParam hrUserApplyAttendancePageParam) {
        QueryWrapper<HrUserApplyAttendance> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserApplyAttendancePageParam.getApplyUserId())) {
            queryWrapper.lambda().eq(HrUserApplyAttendance::getApplyUserId, hrUserApplyAttendancePageParam.getApplyUserId());
        }
        if(ObjectUtil.isNotEmpty(hrUserApplyAttendancePageParam.getApplyType())) {
            queryWrapper.lambda().eq(HrUserApplyAttendance::getApplyType, hrUserApplyAttendancePageParam.getApplyType());
        }
        if(ObjectUtil.isNotEmpty(hrUserApplyAttendancePageParam.getStartApplyTime()) && ObjectUtil.isNotEmpty(hrUserApplyAttendancePageParam.getEndApplyTime())) {
            queryWrapper.lambda().between(HrUserApplyAttendance::getApplyAttendanceTime, hrUserApplyAttendancePageParam.getStartApplyTime(), hrUserApplyAttendancePageParam.getEndApplyTime());
        }
        if(ObjectUtil.isNotEmpty(hrUserApplyAttendancePageParam.getStartApplyBeginTime()) && ObjectUtil.isNotEmpty(hrUserApplyAttendancePageParam.getEndApplyBeginTime())) {
            queryWrapper.lambda().between(HrUserApplyAttendance::getApplyBeginTime, hrUserApplyAttendancePageParam.getStartApplyBeginTime(), hrUserApplyAttendancePageParam.getEndApplyBeginTime());
        }
        if(ObjectUtil.isNotEmpty(hrUserApplyAttendancePageParam.getStartApplyEngTime()) && ObjectUtil.isNotEmpty(hrUserApplyAttendancePageParam.getEndApplyEngTime())) {
            queryWrapper.lambda().between(HrUserApplyAttendance::getApplyEngTime, hrUserApplyAttendancePageParam.getStartApplyEngTime(), hrUserApplyAttendancePageParam.getEndApplyEngTime());
        }
        if(ObjectUtil.isNotEmpty(hrUserApplyAttendancePageParam.getModelId())) {
            queryWrapper.lambda().eq(HrUserApplyAttendance::getModelId, hrUserApplyAttendancePageParam.getModelId());
        }
        if(ObjectUtil.isNotEmpty(hrUserApplyAttendancePageParam.getProcessInstanceId())) {
            queryWrapper.lambda().eq(HrUserApplyAttendance::getProcessInstanceId, hrUserApplyAttendancePageParam.getProcessInstanceId());
        }
        if(ObjectUtil.isNotEmpty(hrUserApplyAttendancePageParam.getStatus())) {
            queryWrapper.lambda().eq(HrUserApplyAttendance::getStatus, hrUserApplyAttendancePageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserApplyAttendancePageParam.getSortField(), hrUserApplyAttendancePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserApplyAttendancePageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserApplyAttendancePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserApplyAttendancePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserApplyAttendance::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserApplyAttendanceAddParam hrUserApplyAttendanceAddParam) {
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        hrUserApplyAttendanceAddParam.setApplyUserId(loginUser.getId());
        if(ObjectUtil.isEmpty(hrUserApplyAttendanceAddParam.getApplyType()) && StringUtils.isEmpty(hrUserApplyAttendanceAddParam.getProcessInstanceId())){
            return;
        }
        if(5!=hrUserApplyAttendanceAddParam.getApplyType()){
            return;
        }
        resubmitAttendanceApply(hrUserApplyAttendanceAddParam);
    }

    /** 补卡申请 */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resubmitAttendanceApply(HrUserApplyAttendanceAddParam hrUserApplyAttendanceAddParam){

        HrUserApplyAttendance hrUserApplyAttendance = BeanUtil.toBean(hrUserApplyAttendanceAddParam, HrUserApplyAttendance.class);

        String processInstanceId = hrUserApplyAttendanceAddParam.getProcessInstanceId();
        String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
        String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
        hrUserApplyAttendance.setId(id);
        hrUserApplyAttendance.setModelId(modelId);
        hrUserApplyAttendance.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
        FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
        //将流程实例和业务表关联起来
        String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));/**  数据库名一定得是小写*/
        formRelationTableApi.save(modelId, BizCommonEnum.HR_APPLY_ATTENDANCE.getBizTable(),id,processInstanceId,dbName);
        hrUserApplyAttendance.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());//申请之后，状态修改为审批中
        this.save(hrUserApplyAttendance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserApplyAttendanceEditParam hrUserApplyAttendanceEditParam) {
        HrUserApplyAttendance hrUserApplyAttendance = this.queryEntity(hrUserApplyAttendanceEditParam.getId());
        BeanUtil.copyProperties(hrUserApplyAttendanceEditParam, hrUserApplyAttendance);
        this.updateById(hrUserApplyAttendance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserApplyAttendanceIdParam> hrUserApplyAttendanceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserApplyAttendanceIdParamList, HrUserApplyAttendanceIdParam::getId));
    }

    @Override
    public HrUserApplyAttendance detail(HrUserApplyAttendanceIdParam hrUserApplyAttendanceIdParam) {
        return this.queryEntity(hrUserApplyAttendanceIdParam.getId());
    }

    @Override
    public HrUserApplyAttendance queryEntity(String id) {
        HrUserApplyAttendance hrUserApplyAttendance = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserApplyAttendance)) {
            throw new CommonException("补卡或远程打卡不存在，id值为：{}", id);
        }
        return hrUserApplyAttendance;
    }
}
