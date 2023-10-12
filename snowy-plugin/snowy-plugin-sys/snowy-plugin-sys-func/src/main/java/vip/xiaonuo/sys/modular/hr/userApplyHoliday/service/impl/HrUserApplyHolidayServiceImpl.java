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
package vip.xiaonuo.sys.modular.hr.userApplyHoliday.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.entity.HrUserApplyHoliday;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.enums.HrUserApplyHolidayEnum;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.mapper.HrUserApplyHolidayMapper;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.param.HrUserApplyHolidayAddParam;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.param.HrUserApplyHolidayEditParam;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.param.HrUserApplyHolidayIdParam;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.param.HrUserApplyHolidayPageParam;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.service.HrUserApplyHolidayService;
import vip.xiaonuo.sys.modular.hr.userHolidayData.entity.HrUserHolidayData;
import vip.xiaonuo.sys.modular.hr.userHolidayData.service.HrUserHolidayDataService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 请假申请表Service接口实现类
 *
 * @author ljy
 * @date  2023/09/22 17:05
 **/
@Service
public class HrUserApplyHolidayServiceImpl extends ServiceImpl<HrUserApplyHolidayMapper, HrUserApplyHoliday> implements HrUserApplyHolidayService {

    @Resource
    CommonCacheOperator commonCacheOperator;
    @Resource
    HrUserHolidayDataService hrUserHolidayDataService;

    @Override
    public Page<HrUserApplyHoliday> page(HrUserApplyHolidayPageParam hrUserApplyHolidayPageParam) {
        QueryWrapper<HrUserApplyHoliday> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserApplyHolidayPageParam.getApplyUserId())) {
            queryWrapper.lambda().eq(HrUserApplyHoliday::getApplyUserId, hrUserApplyHolidayPageParam.getApplyUserId());
        }
        if(ObjectUtil.isNotEmpty(hrUserApplyHolidayPageParam.getApplyType())) {
            queryWrapper.lambda().eq(HrUserApplyHoliday::getApplyType, hrUserApplyHolidayPageParam.getApplyType());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserApplyHolidayPageParam.getSortField(), hrUserApplyHolidayPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserApplyHolidayPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserApplyHolidayPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserApplyHolidayPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserApplyHoliday::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String add(HrUserApplyHolidayAddParam hrUserApplyHolidayAddParam) {

        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        Long hour=DateUtil.between(hrUserApplyHolidayAddParam.getApplyBeginTime(),hrUserApplyHolidayAddParam.getApplyEndTime(), DateUnit.HOUR);
        QueryWrapper<HrUserHolidayData> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(HrUserHolidayData::getUserId,loginUser.getId());
        List<HrUserHolidayData> hrUserHolidayDataList = hrUserHolidayDataService.list(queryWrapper);
        if(ObjectUtil.isEmpty(hrUserHolidayDataList)){
            return HrUserApplyHolidayEnum.NOT_DATA.getValue();
        }
        HrUserHolidayData hrUserHolidayData = hrUserHolidayDataList.get(0);

        if(HrUserApplyHolidayEnum.PERSONAL_LEAVE.getValue().equals(hrUserApplyHolidayAddParam.getApplyType().toString())){/**  事假限制规则*/}

        /** 计算剩余假期  */
        Double result  = hour.intValue()/7  +  hour.intValue()/7 > 0 ? 0.5 : 0;
        Double remainDays = 0.0;
        if(HrUserApplyHolidayEnum.COMPENSATORY_LEAVE.getValue().equals(hrUserApplyHolidayAddParam.getApplyType().toString())){  /**  调休；天工时后面取配置*/
            if(result > Double.valueOf(hrUserHolidayData.getCompensatoryLeave())){
                return HrUserApplyHolidayEnum.NOT_COMPENSATORY_LEAVE_DATA.getValue();
            }
            remainDays = Double.valueOf(hrUserHolidayData.getCompensatoryLeave()) - result;
        }

        if(HrUserApplyHolidayEnum.ANNUAL_LEAVE.getValue().equals(hrUserApplyHolidayAddParam.getApplyType().toString())){  /**  年假 */
            if(result > Double.valueOf(hrUserHolidayData.getAnnualLeave())){
                return HrUserApplyHolidayEnum.NOT_ANNUAL_LEAVE_DATA.getValue();
            }
            remainDays = Double.valueOf(hrUserHolidayData.getAnnualLeave()) - result;
        }
        hrUserHolidayData.setCompensatoryLeave(remainDays.toString());
        hrUserHolidayDataService.updateById(hrUserHolidayData);

        /** 申请流程 */
        HrUserApplyHoliday hrUserApplyHoliday = BeanUtil.toBean(hrUserApplyHolidayAddParam, HrUserApplyHoliday.class);
        String processInstanceId = hrUserApplyHolidayAddParam.getProcessInstanceId();
        String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
        String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
        hrUserApplyHoliday.setId(id);
        hrUserApplyHoliday.setModelId(modelId);
        hrUserApplyHoliday.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
        FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
        //将流程实例和业务表关联起来
        String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));/**  数据库名一定得是小写*/
        formRelationTableApi.save(modelId, BizCommonEnum.BIZ_AUDIT_LISTS.getBizTable(),id,processInstanceId,dbName);
        hrUserApplyHoliday.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());//申请之后，状态修改为审批中

        this.save(hrUserApplyHoliday);
        return HrUserApplyHolidayEnum.SUCCESS.getValue();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserApplyHolidayEditParam hrUserApplyHolidayEditParam) {
        HrUserApplyHoliday hrUserApplyHoliday = this.queryEntity(hrUserApplyHolidayEditParam.getId());
        BeanUtil.copyProperties(hrUserApplyHolidayEditParam, hrUserApplyHoliday);
        this.updateById(hrUserApplyHoliday);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserApplyHolidayIdParam> hrUserApplyHolidayIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserApplyHolidayIdParamList, HrUserApplyHolidayIdParam::getId));
    }

    @Override
    public HrUserApplyHoliday detail(HrUserApplyHolidayIdParam hrUserApplyHolidayIdParam) {
        return this.queryEntity(hrUserApplyHolidayIdParam.getId());
    }

    @Override
    public HrUserApplyHoliday queryEntity(String id) {
        HrUserApplyHoliday hrUserApplyHoliday = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserApplyHoliday)) {
            throw new CommonException("请假申请表不存在，id值为：{}", id);
        }
        return hrUserApplyHoliday;
    }
}
