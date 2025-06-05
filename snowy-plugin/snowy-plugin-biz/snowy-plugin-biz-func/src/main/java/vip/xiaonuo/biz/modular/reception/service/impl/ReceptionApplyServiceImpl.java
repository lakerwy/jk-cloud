package vip.xiaonuo.biz.modular.reception.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
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
import vip.xiaonuo.biz.modular.reception.entity.ReceptionApply;
import vip.xiaonuo.biz.modular.reception.mapper.ReceptionApplyMapper;
import vip.xiaonuo.biz.modular.reception.param.ReceptionApplyAddParam;
import vip.xiaonuo.biz.modular.reception.param.ReceptionApplyEditParam;
import vip.xiaonuo.biz.modular.reception.param.ReceptionApplyIdParam;
import vip.xiaonuo.biz.modular.reception.param.ReceptionApplyPageParam;
import vip.xiaonuo.biz.modular.reception.service.ReceptionApplyService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 接待申请Service接口实现类
 *
 * @author pzh
 * @date  2025/05/28 10:15
 **/
@Service
@DS("slave_1")
public class ReceptionApplyServiceImpl extends ServiceImpl<ReceptionApplyMapper, ReceptionApply> implements ReceptionApplyService {

    @Resource
    CommonCacheOperator commonCacheOperator;

    @Resource
    FlwCommonApi flwCommonApi;

    @Override
    public Page<ReceptionApply> page(ReceptionApplyPageParam receptionApplyPageParam) {
        QueryWrapper<ReceptionApply> queryWrapper = new QueryWrapper<>();
        // 默认只展示审核中和已完成的数据
        if(ObjectUtil.isNotEmpty(receptionApplyPageParam.getStatus())){
            queryWrapper.lambda().eq(ReceptionApply::getStatus,receptionApplyPageParam.getStatus());
        }else {
            queryWrapper.lambda().in(ReceptionApply::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        }
        if(ObjectUtil.isNotEmpty(receptionApplyPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(ReceptionApply::getVisitUnit, receptionApplyPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(receptionApplyPageParam.getSortField(), receptionApplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(receptionApplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, receptionApplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(receptionApplyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(ReceptionApply::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ReceptionApplyAddParam receptionApplyAddParam) {
        ReceptionApply receptionApply = BeanUtil.toBean(receptionApplyAddParam, ReceptionApply.class);
        //走审批流程
        String processInstanceId = receptionApplyAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            receptionApply.setId(id);
            receptionApply.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            receptionApply.setModelId(modelId);
            receptionApply.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_RECEPTION_APPLY.getBizTable(),id,processInstanceId,dbName);
        }
        //不走审批流程
        else{
            receptionApply.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(receptionApply);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ReceptionApplyEditParam receptionApplyEditParam) {
        String id = receptionApplyEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(receptionApplyEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(receptionApplyEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                receptionApplyEditParam.setId(id);
            }
        }
        ReceptionApply receptionApply = this.queryEntity(receptionApplyEditParam.getId());
        BeanUtil.copyProperties(receptionApplyEditParam, receptionApply);
        this.updateById(receptionApply);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ReceptionApplyIdParam> receptionApplyIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(receptionApplyIdParamList, ReceptionApplyIdParam::getId));
    }

    @Override
    public ReceptionApply detail(ReceptionApplyIdParam receptionApplyIdParam) {
        return this.queryEntity(receptionApplyIdParam.getId());
    }

    @Override
    public ReceptionApply queryEntity(String id) {
        ReceptionApply receptionApply = this.getById(id);
        if(ObjectUtil.isEmpty(receptionApply)) {
            throw new CommonException("接待申请不存在，id值为：{}", id);
        }
        return receptionApply;
    }
}
