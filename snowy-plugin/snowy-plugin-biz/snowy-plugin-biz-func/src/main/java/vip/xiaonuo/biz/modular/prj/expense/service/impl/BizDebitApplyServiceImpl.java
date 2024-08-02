package vip.xiaonuo.biz.modular.prj.expense.service.impl;

import cn.dev33.satoken.stp.StpUtil;
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
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.prj.expense.enums.BizDebitStatusEnum;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizDebitApply;
import vip.xiaonuo.biz.modular.prj.expense.mapper.BizDebitApplyMapper;
import vip.xiaonuo.biz.modular.prj.expense.param.BizDebitApplyAddParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizDebitApplyEditParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizDebitApplyIdParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizDebitApplyPageParam;
import vip.xiaonuo.biz.modular.prj.expense.service.BizDebitApplyService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 财务管理 - 借支单 - 申请流程Service接口实现类
 *
 * @author yong.ye
 * @date  2024/07/30 15:33
 **/

@DS("slave_1")
@Service
public class BizDebitApplyServiceImpl extends ServiceImpl<BizDebitApplyMapper, BizDebitApply> implements BizDebitApplyService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<BizDebitApply> page(BizDebitApplyPageParam bizDebitApplyPageParam) {
        QueryWrapper<BizDebitApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(BizDebitApply::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(bizDebitApplyPageParam.getInitor())) {
            queryWrapper.lambda().like(BizDebitApply::getInitor, bizDebitApplyPageParam.getInitor());
        }
        if(ObjectUtil.isNotEmpty(bizDebitApplyPageParam.getFinCode())) {
            queryWrapper.lambda().like(BizDebitApply::getFinCode, bizDebitApplyPageParam.getFinCode());
        }
        if(ObjectUtil.isNotEmpty(bizDebitApplyPageParam.getUnit())) {
            queryWrapper.lambda().like(BizDebitApply::getUnit, bizDebitApplyPageParam.getUnit());
        }
        if(ObjectUtil.isNotEmpty(bizDebitApplyPageParam.getDept())) {
            queryWrapper.lambda().like(BizDebitApply::getDept, bizDebitApplyPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(bizDebitApplyPageParam.getStartExpDate()) && ObjectUtil.isNotEmpty(bizDebitApplyPageParam.getEndExpDate())) {
            queryWrapper.lambda().between(BizDebitApply::getExpDate, bizDebitApplyPageParam.getStartExpDate(), bizDebitApplyPageParam.getEndExpDate());
        }
        if(ObjectUtil.isNotEmpty(bizDebitApplyPageParam.getBorrower())) {
            queryWrapper.lambda().like(BizDebitApply::getBorrower, bizDebitApplyPageParam.getBorrower());
        }
        if(ObjectUtil.isNotEmpty(bizDebitApplyPageParam.getSn())) {
            queryWrapper.lambda().like(BizDebitApply::getSn, bizDebitApplyPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizDebitApplyPageParam.getStatus())) {
            queryWrapper.lambda().eq(BizDebitApply::getStatus, bizDebitApplyPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(bizDebitApplyPageParam.getDebitStatus())){
            queryWrapper.lambda().eq(BizDebitApply::getDebitStatus,bizDebitApplyPageParam.getDebitStatus());
        }
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(BizDebitApply::getDataScopeId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(BizDebitApply::getCreateUser, StpUtil.getLoginIdAsString());
        }
        if(ObjectUtil.isNotEmpty(bizDebitApplyPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BizDebitApply::getSn,bizDebitApplyPageParam.getSearchKey())
                    .or().like(BizDebitApply::getFinCode,bizDebitApplyPageParam.getSearchKey())
                    .or().like(BizDebitApply::getUnit,bizDebitApplyPageParam.getSearchKey())
                    .or().like(BizDebitApply::getDept,bizDebitApplyPageParam.getSearchKey())
                    .or().like(BizDebitApply::getDuty,bizDebitApplyPageParam.getSearchKey())
                    .or().like(BizDebitApply::getBorrower,bizDebitApplyPageParam.getSearchKey())
                    .or().like(BizDebitApply::getInitor,bizDebitApplyPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(bizDebitApplyPageParam.getSortField(), bizDebitApplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizDebitApplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizDebitApplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizDebitApplyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizDebitApply::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizDebitApplyAddParam bizDebitApplyAddParam) {
        BizDebitApply bizDebitApply = BeanUtil.toBean(bizDebitApplyAddParam, BizDebitApply.class);
        //走审批流程
        String processInstanceId = bizDebitApplyAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizDebitApply.setId(id);
            bizDebitApply.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizDebitApply.setModelId(modelId);
            bizDebitApply.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_EXP_APPLY.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizDebitApply.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizDebitApply);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizDebitApplyEditParam bizDebitApplyEditParam) {
        String id = bizDebitApplyEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizDebitApplyEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizDebitApplyEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizDebitApplyEditParam.setId(id);
            }
        }
        BizDebitApply bizDebitApply = this.queryEntity(bizDebitApplyEditParam.getId());
        BeanUtil.copyProperties(bizDebitApplyEditParam, bizDebitApply);
        this.updateById(bizDebitApply);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizDebitApplyIdParam> bizDebitApplyIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizDebitApplyIdParamList, BizDebitApplyIdParam::getId));
    }

    @Override
    public BizDebitApply detail(BizDebitApplyIdParam bizDebitApplyIdParam) {
        return this.queryEntity(bizDebitApplyIdParam.getId());
    }

    @Override
    public BizDebitApply queryEntity(String id) {
        BizDebitApply bizDebitApply = this.getById(id);
        if(ObjectUtil.isEmpty(bizDebitApply)) {
            throw new CommonException("财务管理 - 借支单 - 申请流程不存在，id值为：{}", id);
        }
        return bizDebitApply;
    }

    @Override
    public void sure(BizDebitApplyIdParam bizDebitApplyIdParam) {
        BizDebitApply bizDebitApply = this.queryEntity(bizDebitApplyIdParam.getId());
        bizDebitApply.setDebitStatus(BizDebitStatusEnum.RETURN.getValue());
        this.updateById(bizDebitApply);
    }

    @Override
    public void cancel(BizDebitApplyIdParam bizDebitApplyIdParam) {
        BizDebitApply bizDebitApply = this.queryEntity(bizDebitApplyIdParam.getId());
        bizDebitApply.setDebitStatus(BizDebitStatusEnum.PRE_RETURN.getValue());
        this.updateById(bizDebitApply);
    }
}
