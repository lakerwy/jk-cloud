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
package vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import vip.xiaonuo.flw.api.FormRelationTableApi;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.entity.HrUserApplyBusinessTrip;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.mapper.HrUserApplyBusinessTripMapper;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.param.HrUserApplyBusinessTripAddParam;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.param.HrUserApplyBusinessTripEditParam;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.param.HrUserApplyBusinessTripIdParam;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.param.HrUserApplyBusinessTripPageParam;
import vip.xiaonuo.sys.modular.hr.userApplyBusinessTrip.service.HrUserApplyBusinessTripService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户出差申请表Service接口实现类
 *
 * @author ljy
 * @date  2023/11/01 23:12
 **/
@Service
public class HrUserApplyBusinessTripServiceImpl extends ServiceImpl<HrUserApplyBusinessTripMapper, HrUserApplyBusinessTrip> implements HrUserApplyBusinessTripService {

    @Resource
    CommonCacheOperator commonCacheOperator;

    @Override
    public Page<HrUserApplyBusinessTrip> page(HrUserApplyBusinessTripPageParam hrUserApplyBusinessTripPageParam) {
        QueryWrapper<HrUserApplyBusinessTrip> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserApplyBusinessTripPageParam.getApplyUserId())) {
            queryWrapper.lambda().eq(HrUserApplyBusinessTrip::getApplyUserId, hrUserApplyBusinessTripPageParam.getApplyUserId());
        }
        if(ObjectUtil.isNotEmpty(hrUserApplyBusinessTripPageParam.getProjectId())) {
            queryWrapper.lambda().eq(HrUserApplyBusinessTrip::getProjectId, hrUserApplyBusinessTripPageParam.getProjectId());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserApplyBusinessTripPageParam.getSortField(), hrUserApplyBusinessTripPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserApplyBusinessTripPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserApplyBusinessTripPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserApplyBusinessTripPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserApplyBusinessTrip::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserApplyBusinessTripAddParam hrUserApplyBusinessTripAddParam) {
        /** 申请流程 */
        HrUserApplyBusinessTrip hrUserApplyBusinessTrip = BeanUtil.toBean(hrUserApplyBusinessTripAddParam, HrUserApplyBusinessTrip.class);
        String processInstanceId = hrUserApplyBusinessTripAddParam.getProcessInstanceId();
        String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
        String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
        hrUserApplyBusinessTrip.setId(id);
        hrUserApplyBusinessTrip.setModelId(modelId);
        hrUserApplyBusinessTrip.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
        FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
        //将流程实例和业务表关联起来
        String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));/**  数据库名一定得是小写*/
        formRelationTableApi.save(modelId, BizCommonEnum.BIZ_AUDIT_LISTS.getBizTable(),id,processInstanceId,dbName);
        hrUserApplyBusinessTrip.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());//申请之后，状态修改为审批中

        this.save(hrUserApplyBusinessTrip);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserApplyBusinessTripEditParam hrUserApplyBusinessTripEditParam) {
        HrUserApplyBusinessTrip hrUserApplyBusinessTrip = this.queryEntity(hrUserApplyBusinessTripEditParam.getId());
        BeanUtil.copyProperties(hrUserApplyBusinessTripEditParam, hrUserApplyBusinessTrip);
        this.updateById(hrUserApplyBusinessTrip);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserApplyBusinessTripIdParam> hrUserApplyBusinessTripIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserApplyBusinessTripIdParamList, HrUserApplyBusinessTripIdParam::getId));
    }

    @Override
    public HrUserApplyBusinessTrip detail(HrUserApplyBusinessTripIdParam hrUserApplyBusinessTripIdParam) {
        return this.queryEntity(hrUserApplyBusinessTripIdParam.getId());
    }

    @Override
    public HrUserApplyBusinessTrip queryEntity(String id) {
        HrUserApplyBusinessTrip hrUserApplyBusinessTrip = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserApplyBusinessTrip)) {
            throw new CommonException("用户出差申请表不存在，id值为：{}", id);
        }
        return hrUserApplyBusinessTrip;
    }
}
