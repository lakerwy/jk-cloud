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
package vip.xiaonuo.bp.modular.recruit.supplier.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.bp.modular.platform.entity.BpUser;
import vip.xiaonuo.bp.modular.platform.service.BpUserService;
import vip.xiaonuo.bp.modular.recruit.bankinformation.entity.BpBankInformation;
import vip.xiaonuo.bp.modular.recruit.bankinformation.param.BpBankInformationAddParam;
import vip.xiaonuo.bp.modular.recruit.bankinformation.service.BpBankInformationService;
import vip.xiaonuo.bp.modular.recruit.bankinformation.vo.BpBankInformationReturnVo;
import vip.xiaonuo.bp.modular.recruit.businessscope.param.BpBusinessScopeAddParam;
import vip.xiaonuo.bp.modular.recruit.businessscope.vo.BpBusinessScopeReturnVo;
import vip.xiaonuo.bp.modular.recruit.finance.param.BpFinanceAddParam;
import vip.xiaonuo.bp.modular.recruit.finance.service.BpFinanceService;
import vip.xiaonuo.bp.modular.recruit.businessscope.entity.BpBusinessScope;
import vip.xiaonuo.bp.modular.recruit.businessscope.service.BpBusinessScopeService;
import vip.xiaonuo.bp.modular.recruit.finance.entity.BpFinance;
import vip.xiaonuo.bp.modular.recruit.finance.vo.BpFinanceReturnVo;
import vip.xiaonuo.bp.modular.recruit.relation.entity.BpRelation;
import vip.xiaonuo.bp.modular.recruit.relation.param.BpRelationAddParam;
import vip.xiaonuo.bp.modular.recruit.relation.service.BpRelationService;
import vip.xiaonuo.bp.modular.recruit.relation.vo.BpRelationReturnVo;
import vip.xiaonuo.bp.modular.recruit.supplier.enums.BpBlacklistEnum;
import vip.xiaonuo.bp.modular.recruit.supplier.enums.BpSourceEnum;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierEditParam;
import vip.xiaonuo.bp.modular.recruit.supplier.service.BpSupplierService;
import vip.xiaonuo.bp.modular.recruit.supplier.vo.BpSupplierReturnVo;
import vip.xiaonuo.bp.modular.recruit.supplier.vo.BpSupplierVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.bp.modular.recruit.supplier.entity.BpSupplier;
import vip.xiaonuo.bp.modular.recruit.supplier.mapper.BpSupplierMapper;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierAddParam;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierIdParam;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierPageParam;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.ten.api.TenApi;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 供应商基本信息表Service接口实现类
 *
 * @author hxw
 * @date 2024/04/17 15:18
 **/
@Service
@DS("slave_1")
public class BpSupplierServiceImpl extends ServiceImpl<BpSupplierMapper, BpSupplier> implements BpSupplierService {
    @Resource
    private BpSupplierMapper bpSupplierMapper;
    @Resource
    private BpRelationService bpRelationService;
    @Resource
    private BpBusinessScopeService bpBusinessScopeService;
    @Resource
    private BpBankInformationService bpBankInformationService;
    @Resource
    private BpFinanceService BpFinanceService;
    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private BpUserService bpUserService;

    private static final TenApi tenApi = SpringUtil.getBean(TenApi.class);

    @Override
    public Page<BpSupplierVo> page(BpSupplierPageParam bpSupplierPageParam) {
        QueryWrapper<BpSupplier> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("supplier.DELETE_FLAG", "NOT_DELETE");
        queryWrapper.eq("relation.DELETE_FLAG", "NOT_DELETE");
        //是否选择供应商名称查询
        if (ObjectUtil.isNotEmpty(bpSupplierPageParam.getCompanyName())) {
            queryWrapper.like("supplier.COMPANY_NAME", bpSupplierPageParam.getCompanyName());
        }
        //查询供应商入库审批（审核中）数据
        if (ObjectUtil.isNotEmpty(bpSupplierPageParam.getStatus())) {
            queryWrapper.eq("supplier.STATUS", bpSupplierPageParam.getStatus());
        }
        //查询供应商黑名单列表数据、供应商库（已办结）列表数据，并且不在黑名单中
        if (ObjectUtil.isNotEmpty(bpSupplierPageParam.getIsBlacklist())) {
            queryWrapper.eq("supplier.IS_BLACKLIST", bpSupplierPageParam.getIsBlacklist());
        }
        //是否选择经营范围查询
        if (ObjectUtil.isNotEmpty(bpSupplierPageParam.getClassifySearchParam())) {
            //List<String[]>转List<String> 数据合成一条数据以双引号和逗号隔开 数据格式:["1780126057973739521","1780126194116653058","1780126710414503937"]
            List<String> listOfStrings = bpSupplierPageParam.getClassifySearchParam().stream()
                    .map(array -> "[" + String.join(",", Arrays.stream(array).map(value -> "\"" + value + "\"").toArray(String[]::new)) + "]")
                    .collect(Collectors.toList());
            // 循环比对查询条件
            queryWrapper.and(qw-> {
                listOfStrings.forEach(listOfString -> {
                    qw.or().like("supplier.BUSINESS_SCOPE", listOfString);
                });
            });
        }

        if (ObjectUtil.isAllNotEmpty(bpSupplierPageParam.getSortField(), bpSupplierPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bpSupplierPageParam.getSortOrder());
            queryWrapper.orderBy(true, bpSupplierPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bpSupplierPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("supplier.CREATE_TIME");
        }

        return bpSupplierMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BpSupplierAddParam bpSupplierAddParam) {
        //验证公司名称是否重复 验证审核中，审核完成数据
        Boolean isBoolean = getCompanyName(bpSupplierAddParam.getCompanyName());
        if(isBoolean){
            throw new CommonException("公司名称重复!");
        }
        // 新增供应商基本信息
        BpSupplier bpSupplier = BeanUtil.toBean(bpSupplierAddParam, BpSupplier.class);
        bpSupplier.setIsBlacklist(EnumUtil.toString(BpBlacklistEnum.NOT_BLACKLIST));
        bpSupplier.setTenantId(tenApi.getCurrentTenId());
        // 获取流程信息
        String processInstanceId = bpSupplierAddParam.getProcessInstanceId();
        String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
        String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
        bpSupplier.setId(id);
        bpSupplier.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY + processInstanceId)));
        bpSupplier.setModelId(modelId);
        bpSupplier.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
        FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
        //将流程实例和业务表关联起来
        //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
        String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
        flwCommonApi.save(modelId, BizCommonEnum.BP_SUPPLIER.getBizTable(), id, processInstanceId, dbName);
        //新增供应商信息
        this.save(bpSupplier);

        // 新增经营范围分类
        List<BpBusinessScopeAddParam> classifyList = bpSupplierAddParam.getClassifyList();
        if (CollectionUtil.isEmpty(classifyList)) {
            throw new CommonException("经营范围数据不能为空!");
        }
        for (BpBusinessScopeAddParam bpBusinessScopeAddParam : classifyList) {
            bpBusinessScopeAddParam.setSupplierId(bpSupplier.getId());
            bpBusinessScopeAddParam.setTenantId(tenApi.getCurrentTenId());
        }
        List<BpBusinessScope> newCopyAdd = BeanUtil.copyToList(classifyList, BpBusinessScope.class);
        bpBusinessScopeService.saveBatch(newCopyAdd);

        // 新增供应商联系信息
        List<BpRelationAddParam> relationAddParamList = bpSupplierAddParam.getRelationAddParamList();
        if (CollectionUtil.isEmpty(relationAddParamList)) {
            throw new CommonException("供应商人员信息不能为空!");
        }
        for (BpRelationAddParam bpRelationAddParam : relationAddParamList) {
            Boolean isBooleanPhone = bpRelationService.getBusinessAdminPhone(bpRelationAddParam.getBusinessadminPhone());
            if(isBooleanPhone){
                throw new CommonException("供应商负责人手机号重复!");
            }
            bpRelationAddParam.setSupplierId(bpSupplier.getId());
            bpRelationAddParam.setTenantId(tenApi.getCurrentTenId());
        }
        List<BpRelation> bpRelations = BeanUtil.copyToList(relationAddParamList, BpRelation.class);
        bpRelationService.saveBatch(bpRelations);

        // 新增供应商银行开户信息
        List<BpBankInformationAddParam> bankInformationList = bpSupplierAddParam.getBankInformationList();
        if (CollectionUtil.isEmpty(bankInformationList)) {
            throw new CommonException("银行开户信息不能为空!");
        }
        for (BpBankInformationAddParam bpBankInformationAddParam : bankInformationList) {
            bpBankInformationAddParam.setSupplierId(bpSupplier.getId());
            bpBankInformationAddParam.setTenantId(tenApi.getCurrentTenId());
        }
        List<BpBankInformation> newCopyBankAdd = BeanUtil.copyToList(bankInformationList, BpBankInformation.class);
        bpBankInformationService.saveBatch(newCopyBankAdd);

        // 新增供应商财务信息
        List<BpFinanceAddParam> bpFinanceList = bpSupplierAddParam.getBpFinanceList();
        //目前需要去除财务数据维护 暂时去除
        /*if(CollectionUtil.isEmpty(bpFinanceList)){
            throw new CommonException("财务信息不能为空!");
        }*/
        if (CollectionUtil.isNotEmpty(bpFinanceList)) {
            for (BpFinanceAddParam bpFinance : bpFinanceList) {
                bpFinance.setSupplierId(bpSupplier.getId());
                bpFinance.setTenantId(tenApi.getCurrentTenId());
            }
        }
        List<BpFinance> newCopyFinanceAdd = BeanUtil.copyToList(bpFinanceList, BpFinance.class);
        BpFinanceService.saveBatch(newCopyFinanceAdd);

        //自行申报是没有供应商id，则需要填充
        if(BpSourceEnum.VOLUNTARILY.equals(bpSupplierAddParam.getSource())){
            //通过招采用户id查询供应商对应数据
            BpUser bpUser = bpUserService.getById(bpSupplierAddParam.getBpUserId());
            if(ObjectUtil.isNotEmpty(bpUser)){
                //填充对应用户的供应商id
                bpUser.setSupplierId(bpSupplier.getId());
                bpUserService.updateById(bpUser);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void blackEdit(BpSupplierEditParam bpSupplierEditParam) {
        BpSupplier bpSupplier = this.getById(bpSupplierEditParam.getId());
        if (ObjectUtil.isEmpty(bpSupplier)) {
            throw new CommonException("供应商基本信息表不存在，id值为：{}", bpSupplierEditParam.getId());
        }
        BeanUtil.copyProperties(bpSupplierEditParam, bpSupplier);
        this.updateById(bpSupplier);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BpSupplierIdParam> bpSupplierIdParamList) {
        // 执行删除供应商主表信息
        this.removeByIds(CollStreamUtil.toList(bpSupplierIdParamList, BpSupplierIdParam::getId));

        // 执行删除供应商经营范围分类
        List<BpBusinessScope> removeBpBusinessScopeList = bpBusinessScopeService.list(new QueryWrapper<BpBusinessScope>().lambda().eq(BpBusinessScope::getSupplierId, bpSupplierIdParamList.get(0).getId()));
        if (CollUtil.isNotEmpty(removeBpBusinessScopeList)) {
            bpBusinessScopeService.removeBatchByIds(removeBpBusinessScopeList);
        }

        // 执行删除供应商联系信息
        List<BpRelation> removeBpRelationList = bpRelationService.list(new QueryWrapper<BpRelation>().lambda().eq(BpRelation::getSupplierId, bpSupplierIdParamList.get(0).getId()));
        if (CollUtil.isNotEmpty(removeBpRelationList)) {
            bpRelationService.removeBatchByIds(removeBpRelationList);
        }

        // 执行删除供应商银行开户信息
        List<BpBankInformation> removeBpBankInformationList = bpBankInformationService.list(new QueryWrapper<BpBankInformation>().lambda().eq(BpBankInformation::getSupplierId, bpSupplierIdParamList.get(0).getId()));
        if (CollUtil.isNotEmpty(removeBpBankInformationList)) {
            bpBankInformationService.removeBatchByIds(removeBpBankInformationList);
        }

        // 执行删除供应商财务信息
        List<BpFinance> removeBpFinanceList = BpFinanceService.list(new QueryWrapper<BpFinance>().lambda().eq(BpFinance::getSupplierId, bpSupplierIdParamList.get(0).getId()));
        if (CollUtil.isNotEmpty(removeBpFinanceList)) {
            BpFinanceService.removeBatchByIds(removeBpFinanceList);
        }
    }

    @Override
    public BpSupplierReturnVo detail(BpSupplierIdParam bpSupplierIdParam) {
        return this.queryEntity(bpSupplierIdParam.getId());
    }

    @Override
    public BpSupplierReturnVo queryEntity(String supplierId) {
        // 查询供应商主表基本信息
        BpSupplier bpSupplier = this.getById(supplierId);
        if (ObjectUtil.isEmpty(bpSupplier)) {
            throw new CommonException("供应商基本信息表不存在，id值为：{}", supplierId);
        }
        BpSupplierReturnVo bpSupplierReturnVo = BeanUtil.copyProperties(bpSupplier, BpSupplierReturnVo.class);

        // 查询供应商经营范围分类
        List<BpBusinessScope> businessScopeList = bpBusinessScopeService.list(new LambdaQueryWrapper<BpBusinessScope>().eq(BpBusinessScope::getSupplierId, supplierId).eq(BpBusinessScope::getDeleteFlag, "NOT_DELETE"));
        List<BpBusinessScopeReturnVo> bpSupplierReturnVos = BeanUtil.copyToList(businessScopeList, BpBusinessScopeReturnVo.class);
        bpSupplierReturnVo.setClassifyList(bpSupplierReturnVos);

        // 查询供应商联系信息
        List<BpRelation> bpRelationList = bpRelationService.list(new LambdaQueryWrapper<BpRelation>().eq(BpRelation::getSupplierId, supplierId).eq(BpRelation::getDeleteFlag, "NOT_DELETE"));
        List<BpRelationReturnVo> bpRelationReturnVos = BeanUtil.copyToList(bpRelationList, BpRelationReturnVo.class);
        bpSupplierReturnVo.setRelationAddParamList(bpRelationReturnVos);

        // 查询供应商银行开户信息
        List<BpBankInformation> bpBankInformationList = bpBankInformationService.list(new LambdaQueryWrapper<BpBankInformation>().eq(BpBankInformation::getSupplierId, supplierId).eq(BpBankInformation::getDeleteFlag, "NOT_DELETE"));
        List<BpBankInformationReturnVo> bpBankInformationReturnVos = BeanUtil.copyToList(bpBankInformationList, BpBankInformationReturnVo.class);
        bpSupplierReturnVo.setBankInformationList(bpBankInformationReturnVos);

        // 查询供应商财务信息
        List<BpFinance> bpFinanceList = BpFinanceService.list(new LambdaQueryWrapper<BpFinance>().eq(BpFinance::getSupplierId, supplierId).eq(BpFinance::getDeleteFlag, "NOT_DELETE"));
        List<BpFinanceReturnVo> bpFinanceReturnVos = BeanUtil.copyToList(bpFinanceList, BpFinanceReturnVo.class);
        bpSupplierReturnVo.setBpFinanceList(bpFinanceReturnVos);
        return bpSupplierReturnVo;
    }

    @Override
    public Boolean getCompanyName(String companyName) {
        if(ObjectUtil.isEmpty(companyName)){
            throw new CommonException("供应商公司名称不能为空!");
        }
        return this.list(new QueryWrapper<BpSupplier>().lambda().eq(BpSupplier::getCompanyName,companyName).in(BpSupplier::getStatus,'0','1')).size() > 0;
    }
}
