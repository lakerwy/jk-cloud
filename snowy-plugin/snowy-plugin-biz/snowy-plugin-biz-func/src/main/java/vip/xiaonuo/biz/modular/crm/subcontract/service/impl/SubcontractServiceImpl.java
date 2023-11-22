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
package vip.xiaonuo.biz.modular.crm.subcontract.service.impl;

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
import vip.xiaonuo.biz.modular.crm.hetong.param.CheckNameParam;
import vip.xiaonuo.biz.modular.crm.subcontract.param.*;
import vip.xiaonuo.biz.modular.crm.subcontract.vo.SubContractInfoVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.Subcontract;
import vip.xiaonuo.biz.modular.crm.subcontract.mapper.SubcontractMapper;
import vip.xiaonuo.biz.modular.crm.subcontract.service.SubcontractService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FormRelationTableApi;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 业务表-CRM-分包合同Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:22
 **/

@DS("slave_1")
@Service
public class SubcontractServiceImpl extends ServiceImpl<SubcontractMapper, Subcontract> implements SubcontractService {

    @Resource
    private SubcontractMapper subcontractMapper;

    @Resource
    CommonCacheOperator commonCacheOperator;

    @Resource
    FormRelationTableApi formRelationTableApi;
    @Override
    public Page<SubContractInfoVo> page(SubcontractPageParam subcontractPageParam) {
        QueryWrapper<Subcontract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.in("a.STATUS",FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue());
        if (ObjectUtil.isNotEmpty(subcontractPageParam.getSearchKey())) {
            queryWrapper.like("a.SECOND_LEVEL", subcontractPageParam.getSearchKey()).or()
                    .like("a.OPT", subcontractPageParam.getSearchKey()).or()
                    .like("a.CONTRACT_NO", subcontractPageParam.getSearchKey()).or()
                    .like("a.COMPANY_OR_DEPARTMENT", subcontractPageParam.getSearchKey()).or()
                    .like("a.PROJECT_NAME", subcontractPageParam.getSearchKey()).or()
                    .like("a.COUNTERPARTY", subcontractPageParam.getSearchKey()).or()
                    .like("a.MAIN_CONTRACT_NO", subcontractPageParam.getSearchKey()).or()
                    .like("a.OWNER", subcontractPageParam.getSearchKey()).or()
                    .like("a.FIRSTPARTY", subcontractPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getOpt())) {
            queryWrapper.like("a.OPT", subcontractPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getStartRegisterDate()) && ObjectUtil.isNotEmpty(subcontractPageParam.getEndRegisterDate())) {
            queryWrapper.between("a.REGISTER_DATE", subcontractPageParam.getStartRegisterDate(), subcontractPageParam.getEndRegisterDate());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getIsFiling())) {
            queryWrapper.eq("a.IS_FILING", subcontractPageParam.getIsFiling());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getSecondLevel())) {
            queryWrapper.eq("a.SECOND_LEVEL", subcontractPageParam.getSecondLevel());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getContractNo())) {
            queryWrapper.like("a.CONTRACT_NO", subcontractPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getProjectName())) {
            queryWrapper.like("a.PROJECT_NAME", subcontractPageParam.getProjectName());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getCounterparty())) {
            queryWrapper.like("a.COUNTERPARTY", subcontractPageParam.getCounterparty());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getFirstparty())) {
            queryWrapper.like("a.FIRSTPARTY", subcontractPageParam.getFirstparty());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getCompanyOrDepartment())) {
            queryWrapper.like("a.COMPANY_OR_DEPARTMENT", subcontractPageParam.getCompanyOrDepartment());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getOwner())) {
            queryWrapper.like("a.OWNER", subcontractPageParam.getOwner());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getStartSealDate()) && ObjectUtil.isNotEmpty(subcontractPageParam.getEndSealDate())) {
            queryWrapper.between("a.SEAL_DATE", subcontractPageParam.getStartSealDate(), subcontractPageParam.getEndSealDate());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getHetongId())) {
            queryWrapper.eq("a.HETONG_ID", subcontractPageParam.getHetongId());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getMainContractNo())) {
            queryWrapper.eq("a.MAIN_CONTRACT_NO", subcontractPageParam.getMainContractNo());
        }
        if(ObjectUtil.isAllNotEmpty(subcontractPageParam.getSortField(), subcontractPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subcontractPageParam.getSortOrder());
            queryWrapper.orderBy(true, subcontractPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subcontractPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.REGISTER_DATE").orderByDesc("a.CONTRACT_NO");
        }
        return subcontractMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubcontractAddParam subcontractAddParam) {
        Subcontract subcontract = BeanUtil.toBean(subcontractAddParam, Subcontract.class);
        subcontract.setOpt(StpLoginUserUtil.getLoginUser().getName());
        subcontract.setRegisterDate(new Date());
        //走合同审批流程
        String processInstanceId = subcontractAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            subcontract.setId(id);
            subcontract.setContractNo(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            subcontract.setModelId(modelId);
            subcontract.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            formRelationTableApi.save(modelId, BizCommonEnum.BIZ_CRM_SUBCONTRACT.getBizTable(),id,processInstanceId,dbName);
        }
        else{//不走审批流程
            String modelId = "1720327698938114049";   //分包合同流程Id
            if(StrUtil.isNotEmpty(subcontractAddParam.getModelId())){
                modelId = subcontractAddParam.getModelId();
            }
            String newSn = formRelationTableApi.getSnByModelId(modelId);
            subcontract.setContractNo(newSn);
            subcontract.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(subcontract);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubcontractEditParam subcontractEditParam) {
        String id = subcontractEditParam.getId();
        //判断是否走合同审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(subcontractEditParam.getProcessInstanceId())){
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            JSONObject formRelationTable = formRelationTableApi.getInfoByProcessInstanceId(subcontractEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                subcontractEditParam.setId(id);
            }
        }
        Subcontract subcontract = this.queryEntity(subcontractEditParam.getId());
        BeanUtil.copyProperties(subcontractEditParam, subcontract);
        this.updateById(subcontract);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubcontractIdParam> subcontractIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(subcontractIdParamList, SubcontractIdParam::getId));
    }

    @Override
    public Subcontract detail(SubcontractIdParam subcontractIdParam) {
        return this.queryEntity(subcontractIdParam.getId());
    }

    @Override
    public Subcontract queryEntity(String id) {
        Subcontract subcontract = this.getById(id);
        if(ObjectUtil.isEmpty(subcontract)) {
            throw new CommonException("业务表-CRM-分包合同不存在，id值为：{}", id);
        }
        return subcontract;
    }

    @Override
    public SubContractInfoVo detailById(SubcontractIdParam subcontractIdParam) {
        return subcontractMapper.getDetailById(subcontractIdParam.getId());
    }

    @Override
    public List<Subcontract> detailByHetongId(SubContractHetongIdParam subContractHetongIdParam) {
        return subcontractMapper.getSubContractListByHetongId(subContractHetongIdParam.getHetongId());
    }

    @Override
    public Boolean checkName(CheckNameParam checkNameParam) {
        if(StrUtil.isNotEmpty(checkNameParam.getProjectName())) {
            List<Subcontract> list = this.list(new QueryWrapper<Subcontract>().lambda()
                    .eq(Subcontract::getProjectName, checkNameParam.getProjectName())
                    .in(Subcontract::getStatus,"0","1"));
            return !list.isEmpty();
        }
        return false;
    }

    @Override
    public Page<SubContractInfoVo> limitPage(SubcontractPageParam subcontractPageParam) {
        QueryWrapper<Subcontract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.in("a.STATUS",FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue());
        if (ObjectUtil.isNotEmpty(subcontractPageParam.getSearchKey())) {
            queryWrapper.like("a.SECOND_LEVEL", subcontractPageParam.getSearchKey()).or()
                    .like("a.OPT", subcontractPageParam.getSearchKey()).or()
                    .like("a.CONTRACT_NO", subcontractPageParam.getSearchKey()).or()
                    .like("a.COMPANY_OR_DEPARTMENT", subcontractPageParam.getSearchKey()).or()
                    .like("a.PROJECT_NAME", subcontractPageParam.getSearchKey()).or()
                    .like("a.COUNTERPARTY", subcontractPageParam.getSearchKey()).or()
                    .like("a.MAIN_CONTRACT_NO", subcontractPageParam.getSearchKey()).or()
                    .like("a.OWNER", subcontractPageParam.getSearchKey()).or()
                    .like("a.FIRSTPARTY", subcontractPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getOpt())) {
            queryWrapper.like("a.OPT", subcontractPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getStartRegisterDate()) && ObjectUtil.isNotEmpty(subcontractPageParam.getEndRegisterDate())) {
            queryWrapper.between("a.REGISTER_DATE", subcontractPageParam.getStartRegisterDate(), subcontractPageParam.getEndRegisterDate());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getIsFiling())) {
            queryWrapper.eq("a.IS_FILING", subcontractPageParam.getIsFiling());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getSecondLevel())) {
            queryWrapper.eq("a.SECOND_LEVEL", subcontractPageParam.getSecondLevel());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getContractNo())) {
            queryWrapper.like("a.CONTRACT_NO", subcontractPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getProjectName())) {
            queryWrapper.like("a.PROJECT_NAME", subcontractPageParam.getProjectName());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getCounterparty())) {
            queryWrapper.like("a.COUNTERPARTY", subcontractPageParam.getCounterparty());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getFirstparty())) {
            queryWrapper.like("a.FIRSTPARTY", subcontractPageParam.getFirstparty());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getCompanyOrDepartment())) {
            queryWrapper.like("a.COMPANY_OR_DEPARTMENT", subcontractPageParam.getCompanyOrDepartment());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getOwner())) {
            queryWrapper.like("a.OWNER", subcontractPageParam.getOwner());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getStartSealDate()) && ObjectUtil.isNotEmpty(subcontractPageParam.getEndSealDate())) {
            queryWrapper.between("a.SEAL_DATE", subcontractPageParam.getStartSealDate(), subcontractPageParam.getEndSealDate());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getHetongId())) {
            queryWrapper.eq("a.HETONG_ID", subcontractPageParam.getHetongId());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getMainContractNo())) {
            queryWrapper.eq("a.MAIN_CONTRACT_NO", subcontractPageParam.getMainContractNo());
        }

        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.in("a.DATA_SCOPE_ID", loginUserDataScope);
        } else {
            queryWrapper.eq("a.CREATE_USER", StpUtil.getLoginIdAsString());
        }

        if(ObjectUtil.isAllNotEmpty(subcontractPageParam.getSortField(), subcontractPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subcontractPageParam.getSortOrder());
            queryWrapper.orderBy(true, subcontractPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subcontractPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.REGISTER_DATE").orderByDesc("a.CONTRACT_NO");
        }
        return subcontractMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }
}
