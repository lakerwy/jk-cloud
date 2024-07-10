package vip.xiaonuo.oa.modular.attendance.apply.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.oa.modular.attendance.apply.entity.HolidayApply;
import vip.xiaonuo.oa.modular.attendance.apply.mapper.HolidayApplyMapper;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyAddParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyEditParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyIdParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyPageParam;
import vip.xiaonuo.oa.modular.attendance.apply.service.HolidayApplyService;

import java.util.List;

/**
 * 请假管理表Service接口实现类
 *
 * @author mayike
 * @date  2024/07/09 12:00
 **/
@Service
public class HolidayApplyServiceImpl extends ServiceImpl<HolidayApplyMapper, HolidayApply> implements HolidayApplyService {

    @Override
    public Page<HolidayApply> page(HolidayApplyPageParam holidayApplyPageParam) {
        QueryWrapper<HolidayApply> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(holidayApplyPageParam.getSortField(), holidayApplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(holidayApplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, holidayApplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(holidayApplyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HolidayApply::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HolidayApplyAddParam holidayApplyAddParam) {
        HolidayApply holidayApply = BeanUtil.toBean(holidayApplyAddParam, HolidayApply.class);
        /**
         * 1、查看该时间段，是否有请假申请
         * 2、根据请假类型，判断假期情况。
         */


        this.save(holidayApply);
    }


//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public String add(HrUserApplyHolidayAddParam hrUserApplyHolidayAddParam) {
//        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
//        List<HrUserHolidayData> hrUserHolidayDataList = hrUserHolidayDataService.list(new LambdaQueryWrapper<HrUserHolidayData>().eq(HrUserHolidayData::getUserId,loginUser.getId()));
//        if(ObjectUtil.isEmpty(hrUserHolidayDataList)){
//            return HrUserApplyHolidayEnum.NOT_DATA.getValue();
//        }
//        HrUserHolidayData hrUserHolidayData = hrUserHolidayDataList.get(0);
//
//        /**  只有年假和调休余额限制*/
//        Double leaveDays  = hrUserApplyHolidayAddParam.getLeaveDays();
//        if(HrUserApplyHolidayEnum.COMPENSATORY_LEAVE.getValue().equals(hrUserApplyHolidayAddParam.getApplyType().toString())){  /**  调休 */
//            if(leaveDays > Double.valueOf(hrUserHolidayData.getCompensatoryLeave())){
//                return HrUserApplyHolidayEnum.NOT_COMPENSATORY_LEAVE_DATA.getValue();
//            }
//        }
//        if(HrUserApplyHolidayEnum.ANNUAL_LEAVE.getValue().equals(hrUserApplyHolidayAddParam.getApplyType().toString())){  /**  年假 */
//            if(leaveDays > Double.valueOf(hrUserHolidayData.getAnnualLeave())){
//                return HrUserApplyHolidayEnum.NOT_ANNUAL_LEAVE_DATA.getValue(); }
//        }
//        return saveProcessInstance(hrUserApplyHolidayAddParam);
//    }
//
//    private String saveProcessInstance(HrUserApplyHolidayAddParam hrUserApplyHolidayAddParam){
//        /** 申请流程 */
//        HrUserApplyHoliday hrUserApplyHoliday = BeanUtil.toBean(hrUserApplyHolidayAddParam, HrUserApplyHoliday.class);
//        String processInstanceId = hrUserApplyHolidayAddParam.getProcessInstanceId();
//        String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
//        String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
//        hrUserApplyHoliday.setId(id);
//        hrUserApplyHoliday.setModelId(modelId);
//        hrUserApplyHoliday.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
//        FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
//        //将流程实例和业务表关联起来
//        String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));/**  数据库名一定得是小写*/
//        flwCommonApi.save(modelId, BizCommonEnum.HR_USER_APPLY_HOLIDAY.getBizTable(),id,processInstanceId,dbName);
//        hrUserApplyHoliday.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());//申请之后，状态修改为审批中
//
//        this.save(hrUserApplyHoliday);
//        return HrUserApplyHolidayEnum.SUCCESS.getValue();
//    }
//

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HolidayApplyEditParam holidayApplyEditParam) {
        HolidayApply holidayApply = this.queryEntity(holidayApplyEditParam.getId());
        BeanUtil.copyProperties(holidayApplyEditParam, holidayApply);
        this.updateById(holidayApply);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HolidayApplyIdParam> holidayApplyIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(holidayApplyIdParamList, HolidayApplyIdParam::getId));
    }

    @Override
    public HolidayApply detail(HolidayApplyIdParam holidayApplyIdParam) {
        return this.queryEntity(holidayApplyIdParam.getId());
    }

    @Override
    public HolidayApply queryEntity(String id) {
        HolidayApply holidayApply = this.getById(id);
        if(ObjectUtil.isEmpty(holidayApply)) {
            throw new CommonException("请假管理表不存在，id值为：{}", id);
        }
        return holidayApply;
    }
}
