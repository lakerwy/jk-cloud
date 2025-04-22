package vip.xiaonuo.sys.modular.hr.icManage.service.impl;

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
import vip.xiaonuo.sys.modular.hr.icManage.entity.IcManageApply;
import vip.xiaonuo.sys.modular.hr.icManage.mapper.IcManageApplyMapper;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageApplyAddParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageApplyEditParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageApplyIdParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageApplyPageParam;
import vip.xiaonuo.sys.modular.hr.icManage.service.IcManageApplyService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工商信息新增|变更|注销--流程申请Service接口实现类
 *
 * @author yy
 * @date  2025/04/21 15:58
 **/
@Service
public class IcManageApplyServiceImpl extends ServiceImpl<IcManageApplyMapper, IcManageApply> implements IcManageApplyService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<IcManageApply> page(IcManageApplyPageParam icManageApplyPageParam) {
        QueryWrapper<IcManageApply> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(icManageApplyPageParam.getApplyType())) {
            queryWrapper.lambda().eq(IcManageApply::getApplyType, icManageApplyPageParam.getApplyType());
        }
        if(ObjectUtil.isNotEmpty(icManageApplyPageParam.getCompanyType())) {
            queryWrapper.lambda().eq(IcManageApply::getCompanyType, icManageApplyPageParam.getCompanyType());
        }
        if(ObjectUtil.isNotEmpty(icManageApplyPageParam.getApplyDeptName())) {
            queryWrapper.lambda().like(IcManageApply::getApplyDeptName, icManageApplyPageParam.getApplyDeptName());
        }
        if(ObjectUtil.isNotEmpty(icManageApplyPageParam.getApplyName())) {
            queryWrapper.lambda().like(IcManageApply::getApplyName, icManageApplyPageParam.getApplyName());
        }
        if(ObjectUtil.isNotEmpty(icManageApplyPageParam.getCompanyName())) {
            queryWrapper.lambda().like(IcManageApply::getCompanyName, icManageApplyPageParam.getCompanyName());
        }
        if(ObjectUtil.isNotEmpty(icManageApplyPageParam.getCorpCode())) {
            queryWrapper.lambda().like(IcManageApply::getCorpCode, icManageApplyPageParam.getCorpCode());
        }
        if(ObjectUtil.isNotEmpty(icManageApplyPageParam.getStartEstablishTime()) && ObjectUtil.isNotEmpty(icManageApplyPageParam.getEndEstablishTime())) {
            queryWrapper.lambda().between(IcManageApply::getEstablishTime, icManageApplyPageParam.getStartEstablishTime(), icManageApplyPageParam.getEndEstablishTime());
        }
        if(ObjectUtil.isNotEmpty(icManageApplyPageParam.getSn())) {
            queryWrapper.lambda().like(IcManageApply::getSn, icManageApplyPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(icManageApplyPageParam.getStatus())) {
            queryWrapper.lambda().eq(IcManageApply::getStatus, icManageApplyPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(icManageApplyPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(IcManageApply::getCompanyName,icManageApplyPageParam.getSearchKey())
                    .or().like(IcManageApply::getSn,icManageApplyPageParam.getSearchKey())
                    .or().like(IcManageApply::getIcManageCompanyName,icManageApplyPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(icManageApplyPageParam.getSortField(), icManageApplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(icManageApplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, icManageApplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(icManageApplyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(IcManageApply::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(IcManageApplyAddParam icManageApplyAddParam) {
        IcManageApply icManageApply = BeanUtil.toBean(icManageApplyAddParam, IcManageApply.class);

        //走审批流程
        String processInstanceId = icManageApplyAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            icManageApply.setId(id);
            icManageApply.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            icManageApply.setModelId(modelId);
            icManageApply.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.HR_IC_MANAGE_APPLY.getBizTable(),id,processInstanceId,dbName);
        }else{
            icManageApply.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }

        this.save(icManageApply);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(IcManageApplyEditParam icManageApplyEditParam) {
        String id = icManageApplyEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(icManageApplyEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(icManageApplyEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                icManageApplyEditParam.setId(id);
            }
        }
        IcManageApply icManageApply = this.queryEntity(icManageApplyEditParam.getId());
        BeanUtil.copyProperties(icManageApplyEditParam, icManageApply);
        this.updateById(icManageApply);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<IcManageApplyIdParam> icManageApplyIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(icManageApplyIdParamList, IcManageApplyIdParam::getId));
    }

    @Override
    public IcManageApply detail(IcManageApplyIdParam icManageApplyIdParam) {
        return this.queryEntity(icManageApplyIdParam.getId());
    }

    @Override
    public IcManageApply queryEntity(String id) {
        IcManageApply icManageApply = this.getById(id);
        if(ObjectUtil.isEmpty(icManageApply)) {
            throw new CommonException("工商信息新增|变更|注销--流程申请不存在，id值为：{}", id);
        }
        return icManageApply;
    }
}
