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
package vip.xiaonuo.sys.modular.hr.tryTurn.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
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
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.sys.modular.hr.tryTurn.entity.TryDuty;
import vip.xiaonuo.sys.modular.hr.tryTurn.mapper.TryDutyMapper;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryDutyAddParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryDutyEditParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryDutyIdParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryDutyPageParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.service.TryDutyService;

import javax.annotation.Resource;
import java.util.List;

/**
 * HR - 试用期考核责任Service接口实现类
 *
 * @author yong.ye
 * @date  2024/06/12 10:13
 **/
@Service
public class TryDutyServiceImpl extends ServiceImpl<TryDutyMapper, TryDuty> implements TryDutyService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<TryDuty> page(TryDutyPageParam tryDutyPageParam) {
        QueryWrapper<TryDuty> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(TryDuty::getStatus,FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getName())) {
            queryWrapper.lambda().like(TryDuty::getName, tryDutyPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getIdNumber())) {
            queryWrapper.lambda().like(TryDuty::getIdNumber, tryDutyPageParam.getIdNumber());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getDept())) {
            queryWrapper.lambda().like(TryDuty::getDept, tryDutyPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getDuty())) {
            queryWrapper.lambda().like(TryDuty::getDuty, tryDutyPageParam.getDuty());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getStartHireDate()) && ObjectUtil.isNotEmpty(tryDutyPageParam.getEndHireDate())) {
            queryWrapper.lambda().between(TryDuty::getHireDate, tryDutyPageParam.getStartHireDate(), tryDutyPageParam.getEndHireDate());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getStartPositiveTime()) && ObjectUtil.isNotEmpty(tryDutyPageParam.getEndPositiveTime())) {
            queryWrapper.lambda().between(TryDuty::getPositiveTime, tryDutyPageParam.getStartPositiveTime(), tryDutyPageParam.getEndPositiveTime());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getContractUnit())) {
            queryWrapper.lambda().like(TryDuty::getContractUnit, tryDutyPageParam.getContractUnit());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getSn())) {
            queryWrapper.lambda().like(TryDuty::getSn, tryDutyPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getStatus())) {
            queryWrapper.lambda().eq(TryDuty::getStatus, tryDutyPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(TryDuty::getSn,tryDutyPageParam.getSearchKey())
                    .or().like(TryDuty::getName,tryDutyPageParam.getSearchKey())
                    .or().like(TryDuty::getIdNumber,tryDutyPageParam.getSearchKey())
                    .or().like(TryDuty::getDept,tryDutyPageParam.getSearchKey())
                    .or().like(TryDuty::getContractUnit,tryDutyPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(tryDutyPageParam.getSortField(), tryDutyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tryDutyPageParam.getSortOrder());
            queryWrapper.orderBy(true, tryDutyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tryDutyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(TryDuty::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TryDutyAddParam tryDutyAddParam) {
        TryDuty tryDuty = BeanUtil.toBean(tryDutyAddParam, TryDuty.class);
        //走审批流程
        String processInstanceId = tryDutyAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            tryDuty.setId(id);
            tryDuty.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            tryDuty.setModelId(modelId);
            tryDuty.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.HR_TRY_DUTY.getBizTable(),id,processInstanceId,dbName);
        }else{
            tryDuty.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(tryDuty);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TryDutyEditParam tryDutyEditParam) {
        String id = tryDutyEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(tryDutyEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(tryDutyEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                tryDutyEditParam.setId(id);
            }
        }
        TryDuty tryDuty = this.queryEntity(tryDutyEditParam.getId());
        BeanUtil.copyProperties(tryDutyEditParam, tryDuty);
        this.updateById(tryDuty);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TryDutyIdParam> tryDutyIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(tryDutyIdParamList, TryDutyIdParam::getId));
    }

    @Override
    public TryDuty detail(TryDutyIdParam tryDutyIdParam) {
        return this.queryEntity(tryDutyIdParam.getId());
    }

    @Override
    public TryDuty queryEntity(String id) {
        TryDuty tryDuty = this.getById(id);
        if(ObjectUtil.isEmpty(tryDuty)) {
            throw new CommonException("HR - 试用期考核责任不存在，id值为：{}", id);
        }
        return tryDuty;
    }
}
