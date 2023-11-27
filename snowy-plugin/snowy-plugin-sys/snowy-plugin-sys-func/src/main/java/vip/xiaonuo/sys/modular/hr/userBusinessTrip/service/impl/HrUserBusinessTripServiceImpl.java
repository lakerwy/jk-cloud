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
package vip.xiaonuo.sys.modular.hr.userBusinessTrip.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.common.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.sys.modular.hr.userBusinessTrip.entity.HrUserBusinessTrip;
import vip.xiaonuo.sys.modular.hr.userBusinessTrip.mapper.HrUserBusinessTripMapper;
import vip.xiaonuo.sys.modular.hr.userBusinessTrip.param.*;
import vip.xiaonuo.sys.modular.hr.userBusinessTrip.service.HrUserBusinessTripService;
import vip.xiaonuo.sys.modular.hr.userBusinessTripFlw.entity.HrUserBusinessTripFlw;
import vip.xiaonuo.sys.modular.hr.userBusinessTripFlw.service.HrUserBusinessTripFlwService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务表-考勤-用户出差申请实体数据表Service接口实现类
 *
 * @author ljy
 * @date  2023/11/02 09:03
 **/
@Service
public class HrUserBusinessTripServiceImpl extends ServiceImpl<HrUserBusinessTripMapper, HrUserBusinessTrip> implements HrUserBusinessTripService {

    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private HrUserBusinessTripFlwService hrUserBusinessTripFlwService;


    @Override
    public Page<HrUserBusinessTrip> page(HrUserBusinessTripPageParam hrUserBusinessTripPageParam) {
        QueryWrapper<HrUserBusinessTrip> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserBusinessTripPageParam.getApplyUserId())) {
            queryWrapper.lambda().eq(HrUserBusinessTrip::getApplyUserId, hrUserBusinessTripPageParam.getApplyUserId());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserBusinessTripPageParam.getSortField(), hrUserBusinessTripPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserBusinessTripPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserBusinessTripPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserBusinessTripPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserBusinessTrip::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserBusinessTripAddParam hrUserBusinessTripAddParam) {
        HrUserBusinessTrip hrUserBusinessTrip = BeanUtil.toBean(hrUserBusinessTripAddParam, HrUserBusinessTrip.class);
        this.save(hrUserBusinessTrip);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserBusinessTripEditParam hrUserBusinessTripEditParam) {
        HrUserBusinessTrip hrUserBusinessTrip = this.queryEntity(hrUserBusinessTripEditParam.getId());
        BeanUtil.copyProperties(hrUserBusinessTripEditParam, hrUserBusinessTrip);
        this.updateById(hrUserBusinessTrip);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserBusinessTripIdParam> hrUserBusinessTripIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserBusinessTripIdParamList, HrUserBusinessTripIdParam::getId));
    }

    @Override
    public HrUserBusinessTrip detail(HrUserBusinessTripIdParam hrUserBusinessTripIdParam) {
        return this.queryEntity(hrUserBusinessTripIdParam.getId());
    }

    @Override
    public HrUserBusinessTrip queryEntity(String id) {
        HrUserBusinessTrip hrUserBusinessTrip = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserBusinessTrip)) {
            throw new CommonException("业务表-考勤-用户出差申请实体数据表不存在，id值为：{}", id);
        }
        return hrUserBusinessTrip;
    }

    @Override
    public void addFlw(BusinessTripFlwAddParam businessTripFlwAddParam){
        String processInstanceId = businessTripFlwAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            //第一步： 任务流程
            HrUserBusinessTripFlw businessTripFlw = BeanUtil.copyProperties(businessTripFlwAddParam,HrUserBusinessTripFlw.class);
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            businessTripFlw.setId(id);
            businessTripFlw.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            businessTripFlw.setModelId(modelId);
            businessTripFlw.setProcessInstanceId(processInstanceId);
            businessTripFlw.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());

            hrUserBusinessTripFlwService.save(businessTripFlw);

            //第二步： 存储流程表对应关系
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来(任务流程单表)
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save2(modelId, BizCommonEnum.HR_USER_BUSINESS_TRIP_FLW.getBizTable(),id,processInstanceId,dbName,BizCommonEnum.HR_USER_BUSINESS_TRIP_FLW.getBizTable());

            //第三步： 处理员工申请出差记录表（审批流程中一次提交多申请出差）
            List<HrUserBusinessTrip> businessTripList = BeanUtil.copyToList(businessTripFlwAddParam.getBusinessTripAddParamList(),HrUserBusinessTrip.class);
            businessTripList.forEach(x->{
                x.setApplyUserId(businessTripFlwAddParam.getUserId());
                x.setApplyUserName(businessTripFlwAddParam.getUserName());
                x.setFlwId(businessTripFlw.getId());
            });
            if(CollUtil.isNotEmpty(businessTripList)) {
                this.saveBatch(businessTripList);
            }
        }
    }

    @Override
    public void editFlw(BusinessTripFlwAddParam businessTripFlwAddParam){
        //任务流程清单主键Id，与流程表单对应
        String businessTripFlwId = "";
        //判断审批流程内的修改
        if(ObjectUtil.isNotEmpty(businessTripFlwAddParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(businessTripFlwAddParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                businessTripFlwId = formRelationTable.getStr("id");
            }
        }

        if(StrUtil.isNotEmpty(businessTripFlwId)){
            HrUserBusinessTripFlw businessTripFlw = hrUserBusinessTripFlwService.queryEntity(businessTripFlwId);
            businessTripFlw.setExtJson(JsonUtils.object2json(businessTripFlwAddParam));
            hrUserBusinessTripFlwService.updateById(businessTripFlw);
            //处理该流程的业务数据(业务数据主键由前端提供)
            //格式化前端数据
            List<HrUserBusinessTrip> businessTripList = BeanUtil.copyToList(businessTripFlwAddParam.getBusinessTripAddParamList(),HrUserBusinessTrip.class);
            businessTripList.forEach(x->{
                x.setApplyUserId(businessTripFlwAddParam.getUserId());
                x.setApplyUserName(businessTripFlwAddParam.getUserName());
                x.setFlwId(businessTripFlw.getId());
            });

            this.saveBatch(businessTripList);
        }
    }
}
