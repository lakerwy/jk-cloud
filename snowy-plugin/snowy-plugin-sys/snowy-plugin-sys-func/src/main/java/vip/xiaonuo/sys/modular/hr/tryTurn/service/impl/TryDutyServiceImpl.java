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
import vip.xiaonuo.sys.modular.hr.tryTurn.vo.TryDutyVo;

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
    @Resource
    private TryDutyMapper tryDutyMapper;

    @Override
    public Page<TryDutyVo> page(TryDutyPageParam tryDutyPageParam) {
        QueryWrapper<TryDuty> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("a.STATUS",FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getApplyType())){
            queryWrapper.eq("a.APPLY_TYPE",tryDutyPageParam.getApplyType());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getName())) {
            queryWrapper.like("b.NAME", tryDutyPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getIdNumber())) {
            queryWrapper.like("b.ID_NUMBER", tryDutyPageParam.getIdNumber());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getDept())) {
            queryWrapper.like("b.DEPT", tryDutyPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getDuty())) {
            queryWrapper.like("b.DUTY", tryDutyPageParam.getDuty());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getStartHireDate()) && ObjectUtil.isNotEmpty(tryDutyPageParam.getEndHireDate())) {
            queryWrapper.between("b.HIRE_DATE", tryDutyPageParam.getStartHireDate(), tryDutyPageParam.getEndHireDate());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getStartPositiveTime()) && ObjectUtil.isNotEmpty(tryDutyPageParam.getEndPositiveTime())) {
            queryWrapper.between("b.POSITIVE_TIME", tryDutyPageParam.getStartPositiveTime(), tryDutyPageParam.getEndPositiveTime());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getContractUnit())) {
            queryWrapper.like("b.CONTRACT_UNIT", tryDutyPageParam.getContractUnit());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getSn())) {
            queryWrapper.like("a.SN", tryDutyPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getStatus())) {
            queryWrapper.eq("a.STATUS", tryDutyPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(tryDutyPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.SN",tryDutyPageParam.getSearchKey())
                    .or().like("b.NAME",tryDutyPageParam.getSearchKey())
                    .or().like("b.ID_NUMBER",tryDutyPageParam.getSearchKey())
                    .or().like("b.DEPT",tryDutyPageParam.getSearchKey())
                    .or().like("b.CONTRACT_UNIT",tryDutyPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(tryDutyPageParam.getSortField(), tryDutyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tryDutyPageParam.getSortOrder());
            queryWrapper.orderBy(true, tryDutyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tryDutyPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.SN");
        }
        return tryDutyMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
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
    public TryDutyVo detail(TryDutyIdParam tryDutyIdParam) {
        return tryDutyMapper.getDetail(tryDutyIdParam.getId());
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
