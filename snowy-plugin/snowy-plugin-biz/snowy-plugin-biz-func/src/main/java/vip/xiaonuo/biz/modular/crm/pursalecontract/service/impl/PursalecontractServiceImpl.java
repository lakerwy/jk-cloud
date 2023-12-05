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
package vip.xiaonuo.biz.modular.crm.pursalecontract.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.pursalecontract.entity.Pursalecontract;
import vip.xiaonuo.biz.modular.crm.pursalecontract.mapper.PursalecontractMapper;
import vip.xiaonuo.biz.modular.crm.pursalecontract.param.PursalecontractAddParam;
import vip.xiaonuo.biz.modular.crm.pursalecontract.param.PursalecontractEditParam;
import vip.xiaonuo.biz.modular.crm.pursalecontract.param.PursalecontractIdParam;
import vip.xiaonuo.biz.modular.crm.pursalecontract.param.PursalecontractPageParam;
import vip.xiaonuo.biz.modular.crm.pursalecontract.service.PursalecontractService;
import vip.xiaonuo.common.util.CommonSnUtil;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 业务表-CRM-购销合同Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:03
 **/

@DS("slave_1")
@Service
public class PursalecontractServiceImpl extends ServiceImpl<PursalecontractMapper, Pursalecontract> implements PursalecontractService {

    @Resource
    private FlwCommonApi flwCommonApi;
    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Override
    public Page<Pursalecontract> page(PursalecontractPageParam pursalecontractPageParam) {
        QueryWrapper<Pursalecontract> queryWrapper = new QueryWrapper<>();
//        //审批通过|审批中
//        queryWrapper.lambda().in(Pursalecontract::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if (ObjectUtil.isNotEmpty(pursalecontractPageParam.getSearchKey())) {
            queryWrapper.lambda().like(Pursalecontract::getContractNo, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getOpt, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getContractName, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getPartyA, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getOperator, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getDept, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getBehalfA, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getBehalfB, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getPartyB, pursalecontractPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getStartRegisterDate()) && ObjectUtil.isNotEmpty(pursalecontractPageParam.getEndRegisterDate())) {
            queryWrapper.lambda().between(Pursalecontract::getRegisterDate, pursalecontractPageParam.getStartRegisterDate(), pursalecontractPageParam.getEndRegisterDate());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getContractNo())) {
            queryWrapper.lambda().like(Pursalecontract::getContractNo, pursalecontractPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getContractName())) {
            queryWrapper.lambda().like(Pursalecontract::getContractName, pursalecontractPageParam.getContractName());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getPartyA())) {
            queryWrapper.lambda().like(Pursalecontract::getPartyA, pursalecontractPageParam.getPartyA());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getPartyB())) {
            queryWrapper.lambda().like(Pursalecontract::getPartyB, pursalecontractPageParam.getPartyB());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getAddressA())) {
            queryWrapper.lambda().like(Pursalecontract::getAddressA, pursalecontractPageParam.getAddressA());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getAddressB())) {
            queryWrapper.lambda().like(Pursalecontract::getAddressB, pursalecontractPageParam.getAddressB());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getBrief())) {
            queryWrapper.lambda().like(Pursalecontract::getBrief, pursalecontractPageParam.getBrief());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getDept())) {
            queryWrapper.lambda().like(Pursalecontract::getDept, pursalecontractPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getOperator())) {
            queryWrapper.lambda().like(Pursalecontract::getOperator, pursalecontractPageParam.getOperator());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getStartSealDate()) && ObjectUtil.isNotEmpty(pursalecontractPageParam.getEndSealDate())) {
            queryWrapper.lambda().between(Pursalecontract::getSealDate, pursalecontractPageParam.getStartSealDate(), pursalecontractPageParam.getEndSealDate());
        }
        if(ObjectUtil.isAllNotEmpty(pursalecontractPageParam.getSortField(), pursalecontractPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(pursalecontractPageParam.getSortOrder());
            queryWrapper.orderBy(true, pursalecontractPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(pursalecontractPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Pursalecontract::getContractNo);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PursalecontractAddParam pursalecontractAddParam) {
        Pursalecontract pursalecontract = BeanUtil.toBean(pursalecontractAddParam, Pursalecontract.class);
        //处理Sn编号
        Pursalecontract tmp = this.getOne(new QueryWrapper<Pursalecontract>().lambda().orderByDesc(Pursalecontract::getCreateTime).last("limit 1"));
        String newSn = CommonSnUtil.GenerateSn(pursalecontractAddParam.getContractNo(), tmp == null ? "" : tmp.getContractNo(), CommonSnFormatEnum.SNYYYYMM0001.getValue());
        pursalecontract.setContractNo(newSn);
        pursalecontract.setOpt(StpLoginUserUtil.getLoginUser().getName());
        pursalecontract.setRegisterDate(new Date());
        this.save(pursalecontract);

//        //处理业务表
//        Pursalecontract pursalecontract = BeanUtil.toBean(pursalecontractAddParam, Pursalecontract.class);
//        pursalecontract.setOpt(StpLoginUserUtil.getLoginUser().getName());
//        pursalecontract.setRegisterDate(new Date());
//        //走合同审批流程
//        String processInstanceId = pursalecontractAddParam.getProcessInstanceId();
//        if(ObjectUtil.isNotEmpty(processInstanceId)){
//            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
//            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
//            pursalecontract.setId(id);
//            pursalecontract.setContractNo(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
//            pursalecontract.setModelId(modelId);
//            pursalecontract.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
//            pursalecontract.setProcessInstanceId(processInstanceId);
//            //将流程实例和业务表关联起来
//            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
//            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
//            flwCommonApi.save(modelId, BizCommonEnum.BIZ_CRM_PURSALECONTRACT.getBizTable(),id,processInstanceId,dbName);
//        }else{
//            //不走审批流程
//            String modelId = "1729307184302993410";   //购销合同流程Id
//            if(StrUtil.isNotEmpty(pursalecontractAddParam.getModelId())){
//                modelId = pursalecontractAddParam.getModelId();
//            }
//            String newSn = flwCommonApi.getSnByModelId(modelId);
//            pursalecontract.setContractNo(newSn);
//            pursalecontract.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
//        }
//        this.save(pursalecontract);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PursalecontractEditParam pursalecontractEditParam) {
        Pursalecontract pursalecontract = this.queryEntity(pursalecontractEditParam.getId());
        BeanUtil.copyProperties(pursalecontractEditParam, pursalecontract);
        this.updateById(pursalecontract);

//        String id = pursalecontractEditParam.getId();
//        //判断是否走合同审批流程内的修改
//        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(pursalecontractEditParam.getProcessInstanceId())){
//            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(pursalecontractEditParam.getProcessInstanceId());
//            if(ObjectUtil.isNotEmpty(formRelationTable)){
//                id = formRelationTable.getStr("id");
//                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
//                pursalecontractEditParam.setId(id);
//            }
//        }
//        Pursalecontract pursalecontract = this.queryEntity(pursalecontractEditParam.getId());
//        BeanUtil.copyProperties(pursalecontractEditParam, pursalecontract);
//        this.updateById(pursalecontract);
    }


    @DS("slave_1")
    @DSTransactional
    @Override
    public void addStart(PursalecontractAddParam pursalecontractAddParam) {
        //处理业务表
        Pursalecontract pursalecontract = BeanUtil.toBean(pursalecontractAddParam, Pursalecontract.class);
        pursalecontract.setOpt(StpLoginUserUtil.getLoginUser().getName());
        pursalecontract.setRegisterDate(new Date());

        //若传入start流程发起接口参数，则代表走审批流程，先调用流程start接口,获取流程实例Id
        String processInstanceId = null;
        if(ObjectUtil.isNotEmpty(pursalecontractAddParam.getCommonFlwEntity())){
            try {
                 processInstanceId = flwCommonApi.flwStart(JSONUtil.parseObj(pursalecontractAddParam.getCommonFlwEntity()));
            }catch (CommonException e){
                if(StrUtil.isNotEmpty(processInstanceId)){
                    flwCommonApi.rollback(processInstanceId);
                }
                throw  new CommonException("流程发起失败，请检查后再发起");
            }

            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            pursalecontract.setId(id);
            pursalecontract.setContractNo(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            pursalecontract.setModelId(modelId);
            pursalecontract.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            pursalecontract.setProcessInstanceId(processInstanceId);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_CRM_PURSALECONTRACT.getBizTable(),id,processInstanceId,dbName);
        }else{
            //不走审批流程
            String modelId = "1729307184302993410";   //购销合同流程Id
            if(StrUtil.isNotEmpty(pursalecontractAddParam.getModelId())){
                modelId = pursalecontractAddParam.getModelId();
            }
            String newSn = flwCommonApi.getSnByModelId(modelId);
            pursalecontract.setContractNo(newSn);
            pursalecontract.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        try {
            this.save(pursalecontract);
        }catch (Exception e){
            if(ObjectUtil.isNotEmpty(pursalecontractAddParam.getCommonFlwEntity()) && StrUtil.isNotEmpty(processInstanceId)){
                flwCommonApi.rollback(processInstanceId);
            }
            throw  new CommonException("新增保存失败");
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editStart(PursalecontractEditParam pursalecontractEditParam) {
        String id = pursalecontractEditParam.getId();
        //判断是否走合同审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(pursalecontractEditParam.getProcessInstanceId())){
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(pursalecontractEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                pursalecontractEditParam.setId(id);
            }
        }
        Pursalecontract pursalecontract = this.queryEntity(pursalecontractEditParam.getId());
        BeanUtil.copyProperties(pursalecontractEditParam, pursalecontract);
        this.updateById(pursalecontract);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PursalecontractIdParam> pursalecontractIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(pursalecontractIdParamList, PursalecontractIdParam::getId));
    }

    @Override
    public Pursalecontract detail(PursalecontractIdParam pursalecontractIdParam) {
        return this.queryEntity(pursalecontractIdParam.getId());
    }

    @Override
    public Pursalecontract queryEntity(String id) {
        Pursalecontract pursalecontract = this.getById(id);
        if(ObjectUtil.isEmpty(pursalecontract)) {
            throw new CommonException("业务表-CRM-购销合同不存在，id值为：{}", id);
        }
        return pursalecontract;
    }

    @Override
    public Page<Pursalecontract> limitPage(PursalecontractPageParam pursalecontractPageParam) {
        QueryWrapper<Pursalecontract> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().in(Pursalecontract::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if (ObjectUtil.isNotEmpty(pursalecontractPageParam.getSearchKey())) {
            queryWrapper.lambda().like(Pursalecontract::getContractNo, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getOpt, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getContractName, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getPartyA, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getOperator, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getDept, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getBehalfA, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getBehalfB, pursalecontractPageParam.getSearchKey()).or()
                    .like(Pursalecontract::getPartyB, pursalecontractPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getStartRegisterDate()) && ObjectUtil.isNotEmpty(pursalecontractPageParam.getEndRegisterDate())) {
            queryWrapper.lambda().between(Pursalecontract::getRegisterDate, pursalecontractPageParam.getStartRegisterDate(), pursalecontractPageParam.getEndRegisterDate());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getContractNo())) {
            queryWrapper.lambda().like(Pursalecontract::getContractNo, pursalecontractPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getContractName())) {
            queryWrapper.lambda().like(Pursalecontract::getContractName, pursalecontractPageParam.getContractName());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getPartyA())) {
            queryWrapper.lambda().like(Pursalecontract::getPartyA, pursalecontractPageParam.getPartyA());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getPartyB())) {
            queryWrapper.lambda().like(Pursalecontract::getPartyB, pursalecontractPageParam.getPartyB());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getAddressA())) {
            queryWrapper.lambda().like(Pursalecontract::getAddressA, pursalecontractPageParam.getAddressA());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getAddressB())) {
            queryWrapper.lambda().like(Pursalecontract::getAddressB, pursalecontractPageParam.getAddressB());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getBrief())) {
            queryWrapper.lambda().like(Pursalecontract::getBrief, pursalecontractPageParam.getBrief());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getDept())) {
            queryWrapper.lambda().like(Pursalecontract::getDept, pursalecontractPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getOperator())) {
            queryWrapper.lambda().like(Pursalecontract::getOperator, pursalecontractPageParam.getOperator());
        }
        if(ObjectUtil.isNotEmpty(pursalecontractPageParam.getStartSealDate()) && ObjectUtil.isNotEmpty(pursalecontractPageParam.getEndSealDate())) {
            queryWrapper.lambda().between(Pursalecontract::getSealDate, pursalecontractPageParam.getStartSealDate(), pursalecontractPageParam.getEndSealDate());
        }
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(Pursalecontract::getDataScopeId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(Pursalecontract::getCreateUser, StpUtil.getLoginIdAsString());
        }
        if(ObjectUtil.isAllNotEmpty(pursalecontractPageParam.getSortField(), pursalecontractPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(pursalecontractPageParam.getSortOrder());
            queryWrapper.orderBy(true, pursalecontractPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(pursalecontractPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Pursalecontract::getContractNo);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }
}
