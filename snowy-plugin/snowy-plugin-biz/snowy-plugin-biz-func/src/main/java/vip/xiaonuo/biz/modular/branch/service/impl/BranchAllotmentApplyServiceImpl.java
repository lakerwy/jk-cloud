package vip.xiaonuo.biz.modular.branch.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.branch.entity.BranchPaymentInfo;
import vip.xiaonuo.biz.modular.branch.param.*;
import vip.xiaonuo.biz.modular.branch.service.BranchPaymentInfoService;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.branch.entity.BranchAllotmentApply;
import vip.xiaonuo.biz.modular.branch.mapper.BranchAllotmentApplyMapper;
import vip.xiaonuo.biz.modular.branch.service.BranchAllotmentApplyService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分公司日常费用拨付 - 申请流程Service接口实现类
 *
 * @author pzh
 * @date  2024/08/06 14:03
 **/
@DS("slave_1")
@Service
public class BranchAllotmentApplyServiceImpl extends ServiceImpl<BranchAllotmentApplyMapper, BranchAllotmentApply> implements BranchAllotmentApplyService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    FlwCommonApi flwCommonApi;

    @Resource
    private BranchPaymentInfoService branchPaymentInfoService;

    @Override
    public Page<BranchAllotmentApply> page(BranchAllotmentApplyPageParam branchAllotmentApplyPageParam) {
        QueryWrapper<BranchAllotmentApply> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(branchAllotmentApplyPageParam.getSortField(), branchAllotmentApplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(branchAllotmentApplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, branchAllotmentApplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(branchAllotmentApplyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BranchAllotmentApply::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BranchAllotmentApplyAddParam branchAllotmentApplyAddParam) {
        BranchAllotmentApply branchAllotmentApply = BeanUtil.toBean(branchAllotmentApplyAddParam, BranchAllotmentApply.class);
        //走审批流程
        String processInstanceId = branchAllotmentApplyAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            branchAllotmentApply.setId(id);
            branchAllotmentApply.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            branchAllotmentApply.setModelId(modelId);
            branchAllotmentApply.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_BRANCH_ALLOTMENT_APPLY.getBizTable(),id,processInstanceId,dbName);
        }else{
            branchAllotmentApply.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(branchAllotmentApply);
        //添加付款信息
        if(CollUtil.isNotEmpty(branchAllotmentApplyAddParam.getPaymentInfoList())){
            List<BranchPaymentInfo> paymentInfoList = branchAllotmentApplyAddParam.getPaymentInfoList();
            paymentInfoList.forEach(item -> {
                item.setAllotmentApplyId(branchAllotmentApply.getId());
            });
            branchPaymentInfoService.saveBatch(paymentInfoList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BranchAllotmentApplyEditParam branchAllotmentApplyEditParam) {
        String id = branchAllotmentApplyEditParam.getId();
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(branchAllotmentApplyEditParam.getProcessInstanceId())){
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(branchAllotmentApplyEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id=formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                branchAllotmentApplyEditParam.setId(id);
            }
        }
        BranchAllotmentApply branchAllotmentApply = this.queryEntity(branchAllotmentApplyEditParam.getId());
        BeanUtil.copyProperties(branchAllotmentApplyEditParam, branchAllotmentApply);
        this.updateById(branchAllotmentApply);
        //修改付款信息
        branchPaymentInfoService.batchUpdate(branchAllotmentApplyEditParam.getPaymentInfoList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BranchAllotmentApplyIdParam> branchAllotmentApplyIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(branchAllotmentApplyIdParamList, BranchAllotmentApplyIdParam::getId));
    }

    @Override
    public BranchAllotmentApply detail(BranchAllotmentApplyIdParam branchAllotmentApplyIdParam) {
        return this.queryEntity(branchAllotmentApplyIdParam.getId());
    }

    @Override
    public BranchAllotmentApply queryEntity(String id) {
        BranchAllotmentApply branchAllotmentApply = this.getById(id);
        if(ObjectUtil.isEmpty(branchAllotmentApply)) {
            throw new CommonException("分公司日常费用拨付 - 申请流程不存在，id值为：{}", id);
        }
        return branchAllotmentApply;
    }

    @Override
    public void updateRealBalance(BranchAllotmentApplyRealBalanceEditParam editParam) {
        String id = editParam.getId();
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(editParam.getProcessInstanceId())){
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(editParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id=formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                editParam.setId(id);
            }
        }
        UpdateWrapper<BranchAllotmentApply> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(BranchAllotmentApply::getId,editParam.getId());
        updateWrapper.lambda().set(BranchAllotmentApply::getRealBalance ,editParam.getRealBalance());
        this.update(updateWrapper);
    }
}
