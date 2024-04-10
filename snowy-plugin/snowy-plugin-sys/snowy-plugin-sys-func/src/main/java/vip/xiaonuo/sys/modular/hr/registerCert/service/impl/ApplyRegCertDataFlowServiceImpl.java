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
package vip.xiaonuo.sys.modular.hr.registerCert.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import vip.xiaonuo.sys.modular.hr.registerCert.entity.*;
import vip.xiaonuo.sys.modular.hr.registerCert.mapper.ApplyRegCertDataFlowMapper;
import vip.xiaonuo.sys.modular.hr.registerCert.param.ApplyRegCertDataFlowAddParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.ApplyRegCertDataFlowEditParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.ApplyRegCertDataFlowIdParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.ApplyRegCertDataFlowPageParam;
import vip.xiaonuo.sys.modular.hr.registerCert.service.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 注册补贴-申请FLW流程表信息Service接口实现类
 *
 * @author ljy
 * @date  2024/03/28 15:48
 **/
@Service
public class ApplyRegCertDataFlowServiceImpl extends ServiceImpl<ApplyRegCertDataFlowMapper, ApplyRegCertDataFlow> implements ApplyRegCertDataFlowService {

    @Resource
    CommonCacheOperator commonCacheOperator;
    @Resource
    RegAlwUserService regAlwUserService;
    @Resource
    RegCertDataService regCertDataService;
    @Resource
    RegCertStatusDataService regCertStatusDataService;
    @Resource
    ApplyRegCertDataService applyRegCertDataService;
    @Override
    public Page<ApplyRegCertDataFlow> page(ApplyRegCertDataFlowPageParam applyRegCertDataFlowPageParam) {
        QueryWrapper<ApplyRegCertDataFlow> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(applyRegCertDataFlowPageParam.getSortField(), applyRegCertDataFlowPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(applyRegCertDataFlowPageParam.getSortOrder());
            queryWrapper.orderBy(true, applyRegCertDataFlowPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(applyRegCertDataFlowPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ApplyRegCertDataFlow::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ApplyRegCertDataFlowAddParam applyRegCertDataFlowAddParam) {
        ApplyRegCertDataFlow applyRegCertDataFlow = BeanUtil.toBean(applyRegCertDataFlowAddParam, ApplyRegCertDataFlow.class);
        String processInstanceId = applyRegCertDataFlowAddParam.getProcessInstanceId();
        String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
        String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
        applyRegCertDataFlow.setId(id);
        applyRegCertDataFlow.setModelId(modelId);
        applyRegCertDataFlow.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
        FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
        //将流程实例和业务表关联起来
        String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));/** 数据库名一定得是小写*/
        flwCommonApi.save(modelId, BizCommonEnum.APPLY_REG_CERT_DATA_FLOW.getBizTable(),id,processInstanceId,dbName);
        applyRegCertDataFlow.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());//申请之后，状态修改为审批中
        this.save(applyRegCertDataFlow);
        saveRegAlwAndCertData(applyRegCertDataFlowAddParam,applyRegCertDataFlow);
    }

    /**
     * 注册补贴 - 基本信息  REG_ALW_USER
     * 注册补贴 - 已有证书  REG_CERT_DATA
     * 注册补贴-申请基本信息 APPLY_REG_CERT_DATA
     * 注册补贴 - 人力资源-注册执业资格证书  REG_CERT_STATUS_DATA
     * 注册补贴-申请表 APPLY_REG_CERT_DATA_FLOW
     * */
    /** 后面异步处理 */
    private void saveRegAlwAndCertData(ApplyRegCertDataFlowAddParam applyRegCertDataFlowAddParam,ApplyRegCertDataFlow applyRegCertDataFlow){
        /** 注册补贴 - 基本信息  REG_ALW_USER*/
        if(ObjectUtil.isNotEmpty(applyRegCertDataFlowAddParam.getRegAlwUserVo())){
            List<RegAlwUser> regAlwUserList = regAlwUserService.list(new LambdaQueryWrapper<RegAlwUser>().eq(RegAlwUser::getIdCardNumber,applyRegCertDataFlowAddParam.getIdCardNumber()));//身份证号码唯一
            RegAlwUser regAlwUser = new RegAlwUser();
            if(CollectionUtil.isNotEmpty(regAlwUserList)){
                regAlwUser = regAlwUserList.get(0);
            }
            BeanUtil.copyProperties(applyRegCertDataFlowAddParam.getRegAlwUserVo(), regAlwUser);
            regAlwUser.setIdCardNumber(applyRegCertDataFlowAddParam.getIdCardNumber());
            regAlwUserService.saveOrUpdate(regAlwUser);
        }
        /** 注册补贴 - 已有证书  REG_CERT_DATA*/
        if(CollectionUtil.isNotEmpty(applyRegCertDataFlowAddParam.getRegCertDataList())){
            List<RegCertData> regCertDataList = new ArrayList<>();
            applyRegCertDataFlowAddParam.getRegCertDataList().forEach(cert -> {
                RegCertData regCertData = BeanUtil.toBean(cert,RegCertData.class);
                regCertData.setApplyRegFlwId(applyRegCertDataFlow.getId());
                regCertData.setIdCardNumber(applyRegCertDataFlowAddParam.getIdCardNumber());
                regCertDataList.add(regCertData);
            });
            regCertDataService.saveBatch(regCertDataList);
        }
        /** 注册补贴 - 人力资源-注册执业资格证书  REG_CERT_STATUS_DATA*/
        if(CollectionUtil.isNotEmpty(applyRegCertDataFlowAddParam.getRegCertStatusDataList())){
            List<RegCertStatusData> regCertStatusDataList = new ArrayList<>();
            applyRegCertDataFlowAddParam.getRegCertStatusDataList().forEach(status -> {
                RegCertStatusData regCertStatusData = BeanUtil.toBean(status,RegCertStatusData.class);
                regCertStatusData.setApplyRegFlwId(applyRegCertDataFlow.getId());
                regCertStatusData.setIsPass("0");//0 原始状态为未通过
                regCertStatusData.setIdCardNumber(applyRegCertDataFlowAddParam.getIdCardNumber());
                regCertStatusDataList.add(regCertStatusData);
            });
            regCertStatusDataService.saveBatch(regCertStatusDataList);
        }

        /** 注册补贴-申请基本信息 APPLY_REG_CERT_DATA*/
        if(CollectionUtil.isNotEmpty(applyRegCertDataFlowAddParam.getApplyRegCertAlwVoList())){
            List<ApplyRegCertData> applyRegCertDataList = new ArrayList<>();
            applyRegCertDataFlowAddParam.getApplyRegCertAlwVoList().forEach(applyData -> {
                ApplyRegCertData applyRegCertData = BeanUtil.toBean(applyData,ApplyRegCertData.class);
                applyRegCertData.setApplyRegFlwId(applyRegCertDataFlow.getId());
                applyRegCertData.setIdCardNumber(applyRegCertDataFlowAddParam.getIdCardNumber());
                applyRegCertDataList.add(applyRegCertData);
            });
            applyRegCertDataService.saveBatch(applyRegCertDataList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ApplyRegCertDataFlowEditParam applyRegCertDataFlowEditParam) {
        List<RegCertStatusData> deletRegCertStatus = regCertStatusDataService.list(new LambdaQueryWrapper<RegCertStatusData>().eq(RegCertStatusData::getApplyRegFlwId,applyRegCertDataFlowEditParam.getId()));
        regCertStatusDataService.removeByIds(deletRegCertStatus.stream().map(reg -> reg.getId()).collect(Collectors.toList()));
        if(CollectionUtil.isNotEmpty(applyRegCertDataFlowEditParam.getRegCertStatusDataList())){
            List<RegCertStatusData> regCertStatusDataList = new ArrayList<>();
            applyRegCertDataFlowEditParam.getRegCertStatusDataList().forEach(status -> {
                RegCertStatusData regCertStatusData = BeanUtil.toBean(status,RegCertStatusData.class);
                regCertStatusData.setApplyRegFlwId(applyRegCertDataFlowEditParam.getId());
                regCertStatusData.setIdCardNumber(applyRegCertDataFlowEditParam.getIdCardNumber());
                regCertStatusDataList.add(regCertStatusData);
            });
            regCertStatusDataService.saveBatch(regCertStatusDataList);
        }

        ApplyRegCertDataFlow applyRegCertDataFlow = this.queryEntity(applyRegCertDataFlowEditParam.getId());
        BeanUtil.copyProperties(applyRegCertDataFlowEditParam, applyRegCertDataFlow);
        this.updateById(applyRegCertDataFlow);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ApplyRegCertDataFlowIdParam> applyRegCertDataFlowIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(applyRegCertDataFlowIdParamList, ApplyRegCertDataFlowIdParam::getId));
    }

    @Override
    public ApplyRegCertDataFlow detail(ApplyRegCertDataFlowIdParam applyRegCertDataFlowIdParam) {
        ApplyRegCertDataFlow  applyRegCertDataFlow = this.queryEntity(applyRegCertDataFlowIdParam.getId());
        getRegAlwAndCertData(applyRegCertDataFlowIdParam,applyRegCertDataFlow);
        return applyRegCertDataFlow;
    }

    private void getRegAlwAndCertData(ApplyRegCertDataFlowIdParam applyRegCertDataFlowIdParam,ApplyRegCertDataFlow applyRegCertDataFlow){
        List<RegAlwUser> regAlwUserList = regAlwUserService.list(new LambdaQueryWrapper<RegAlwUser>().eq(RegAlwUser::getIdCardNumber,applyRegCertDataFlowIdParam.getIdCardNumber()));//身份证号码唯一
        if(CollectionUtil.isNotEmpty(regAlwUserList)){ applyRegCertDataFlow.setRegAlwUser(regAlwUserList.get(0)); }

        List<ApplyRegCertData> applyRegCertDataList = applyRegCertDataService.list(new LambdaQueryWrapper<ApplyRegCertData>()
                .eq(ApplyRegCertData::getApplyRegFlwId,applyRegCertDataFlowIdParam.getId()).eq(ApplyRegCertData::getIdCardNumber,applyRegCertDataFlowIdParam.getIdCardNumber()));
        if(CollectionUtil.isNotEmpty(applyRegCertDataList)){applyRegCertDataFlow.setApplyRegCertDataList(applyRegCertDataList);}

        List<RegCertData> regCertDataList = regCertDataService.list(new LambdaQueryWrapper<RegCertData>()
                .eq(RegCertData::getApplyRegFlwId,applyRegCertDataFlowIdParam.getId()).eq(RegCertData::getIdCardNumber,applyRegCertDataFlowIdParam.getIdCardNumber()));
        if(CollectionUtil.isNotEmpty(regCertDataList)){applyRegCertDataFlow.setRegCertDataList(regCertDataList);}

        List<RegCertStatusData> regCertStatusDataList = regCertStatusDataService.list(new LambdaQueryWrapper<RegCertStatusData>()
                .eq(RegCertStatusData::getApplyRegFlwId,applyRegCertDataFlowIdParam.getId()).eq(RegCertStatusData::getIdCardNumber,applyRegCertDataFlowIdParam.getIdCardNumber()));
        if(CollectionUtil.isNotEmpty(regCertStatusDataList)){applyRegCertDataFlow.setRegCertStatusDataList(regCertStatusDataList);}
    }

    @Override
    public ApplyRegCertDataFlow queryEntity(String id) {
        ApplyRegCertDataFlow applyRegCertDataFlow = this.getById(id);
        if(ObjectUtil.isEmpty(applyRegCertDataFlow)) {
            throw new CommonException("注册补贴-申请FLW流程表信息不存在，id值为：{}", id);
        }
        return applyRegCertDataFlow;
    }
}
