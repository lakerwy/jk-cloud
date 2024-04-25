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
import vip.xiaonuo.bp.modular.recruit.bankinformation.entity.BpBankInformation;
import vip.xiaonuo.bp.modular.recruit.bankinformation.param.BpBankInformationAddParam;
import vip.xiaonuo.bp.modular.recruit.bankinformation.service.BpBankInformationService;
import vip.xiaonuo.bp.modular.recruit.finance.param.BpFinanceAddParam;
import vip.xiaonuo.bp.modular.recruit.finance.service.BpFinanceService;
import vip.xiaonuo.bp.modular.recruit.businessscope.entity.BpBusinessScope;
import vip.xiaonuo.bp.modular.recruit.businessscope.service.BpBusinessScopeService;
import vip.xiaonuo.bp.modular.recruit.finance.entity.BpFinance;
import vip.xiaonuo.bp.modular.recruit.relation.entity.BpRelation;
import vip.xiaonuo.bp.modular.recruit.relation.param.BpRelationAddParam;
import vip.xiaonuo.bp.modular.recruit.relation.service.BpRelationService;
import vip.xiaonuo.bp.modular.recruit.supplier.enums.BpBlacklistEnum;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierEditParam;
import vip.xiaonuo.bp.modular.recruit.supplier.service.BpSupplierService;
import vip.xiaonuo.bp.modular.recruit.supplier.vo.BpSupplierVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.bp.modular.recruit.supplier.entity.BpSupplier;
import vip.xiaonuo.bp.modular.recruit.supplier.mapper.BpSupplierMapper;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierAddParam;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierIdParam;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierPageParam;
import vip.xiaonuo.ten.api.TenApi;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 供应商基本信息表Service接口实现类
 *
 * @author hxw
 * @date  2024/04/17 15:18
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

    private static final TenApi tenApi = SpringUtil.getBean(TenApi.class);

    @Override
    public Page<BpSupplierVo> page(BpSupplierPageParam bpSupplierPageParam) {
        QueryWrapper<BpSupplier> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("supplier.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("business.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("relation.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(bpSupplierPageParam.getCompanyName())) {
            queryWrapper.like("supplier.COMPANY_NAME", bpSupplierPageParam.getCompanyName());
        }
        if(ObjectUtil.isNotEmpty(bpSupplierPageParam.getStairclassifyId())) {
            queryWrapper.eq("business.STAIRCLASSIFY_ID", bpSupplierPageParam.getStairclassifyId());
        }
        if(ObjectUtil.isNotEmpty(bpSupplierPageParam.getAccessclassifyId())) {
            queryWrapper.eq("business.ACCESSCLASSIFY_ID", bpSupplierPageParam.getAccessclassifyId());
        }
        if(ObjectUtil.isNotEmpty(bpSupplierPageParam.getReclassifyId())) {
            queryWrapper.in("business.RECLASSIFY_ID", Arrays.stream(bpSupplierPageParam.getReclassifyId().split(",")).map(Integer::parseInt).collect(Collectors.toList()));
        }
        if(ObjectUtil.isAllNotEmpty(bpSupplierPageParam.getSortField(), bpSupplierPageParam.getSortOrder())) {
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
        // 新增供应商基本信息
        BpSupplier bpSupplier = BeanUtil.toBean(bpSupplierAddParam, BpSupplier.class);
        bpSupplier.setIsBlacklist(EnumUtil.toString(BpBlacklistEnum.NOT_BLACKLIST));
        bpSupplier.setTenantId(tenApi.getCurrentTenId());
        this.save(bpSupplier);

        // 新增经营范围分类
        List<BpBankInformationAddParam> classifyList = bpSupplierAddParam.getClassifyList();
        if(CollectionUtil.isEmpty(classifyList)){
            throw new CommonException("经营范围数据不能为空!");
        }
        for (BpBankInformationAddParam bpBankInformationAddParam : classifyList) {
            bpBankInformationAddParam.setSupplierId(bpSupplier.getId());
            bpBankInformationAddParam.setTenantId(tenApi.getCurrentTenId());
        }
        List<BpBusinessScope> newCopyAdd = BeanUtil.copyToList(classifyList, BpBusinessScope.class);
        bpBusinessScopeService.saveBatch(newCopyAdd);

        // 新增供应商联系信息
        BpRelation bpRelation = BeanUtil.toBean(bpSupplierAddParam, BpRelation.class);
        bpRelation.setSupplierId(bpSupplier.getId());
        bpRelation.setTenantId(tenApi.getCurrentTenId());
        bpRelationService.save(bpRelation);

        // 新增供应商银行开户信息
        List<BpBankInformationAddParam> bankInformationList = bpSupplierAddParam.getBankInformationList();
        if(CollectionUtil.isEmpty(bankInformationList)){
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
        if(CollectionUtil.isEmpty(bpFinanceList)){
            throw new CommonException("财务信息不能为空!");
        }
        for (BpFinanceAddParam bpFinance : bpFinanceList) {
            bpFinance.setSupplierId(bpSupplier.getId());
            bpFinance.setTenantId(tenApi.getCurrentTenId());
        }
        List<BpFinance> newCopyFinanceAdd = BeanUtil.copyToList(bpFinanceList, BpFinance.class);
        BpFinanceService.saveBatch(newCopyFinanceAdd);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BpSupplierEditParam bpSupplierEditParam) {
        BpSupplier bpSupplier = this.getById(bpSupplierEditParam.getId());
        if(ObjectUtil.isEmpty(bpSupplier)) {
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
    public BpSupplierAddParam detail(BpSupplierIdParam bpSupplierIdParam) {
        return this.queryEntity(bpSupplierIdParam.getId());
    }

    @Override
    public BpSupplierAddParam queryEntity(String supplierId) {
        // 查询供应商主表基本信息
        BpSupplier bpSupplier = this.getById(supplierId);
        if(ObjectUtil.isEmpty(bpSupplier)) {
            throw new CommonException("供应商基本信息表不存在，id值为：{}", supplierId);
        }
        BpSupplierAddParam bpSupplierAddParam = BeanUtil.copyProperties(bpSupplier, BpSupplierAddParam.class);

        // 查询供应商经营范围分类
        List<BpBusinessScope> businessScopeList = bpBusinessScopeService.list(new LambdaQueryWrapper<BpBusinessScope>().eq(BpBusinessScope::getSupplierId,supplierId).eq(BpBusinessScope::getDeleteFlag, "NOT_DELETE"));
        List<BpBankInformationAddParam> bpBankInformationAddParams = BeanUtil.copyToList(businessScopeList, BpBankInformationAddParam.class);
        bpSupplierAddParam.setClassifyList(bpBankInformationAddParams);

        // 查询供应商联系信息
        List<BpRelation> bpRelationList = bpRelationService.list(new LambdaQueryWrapper<BpRelation>().eq(BpRelation::getSupplierId,supplierId).eq(BpRelation::getDeleteFlag, "NOT_DELETE"));
        List<BpRelationAddParam> bpBankInformationAddParam = BeanUtil.copyToList(bpRelationList, BpRelationAddParam.class);
        bpSupplierAddParam.setRelationAddParamList(bpBankInformationAddParam);

        // 查询供应商银行开户信息
        List<BpBankInformation> bpBankInformationList = bpBankInformationService.list(new LambdaQueryWrapper<BpBankInformation>().eq(BpBankInformation::getSupplierId,supplierId).eq(BpBankInformation::getDeleteFlag, "NOT_DELETE"));
        List<BpBankInformationAddParam> bankInformationAddParams = BeanUtil.copyToList(bpBankInformationList, BpBankInformationAddParam.class);
        bpSupplierAddParam.setBankInformationList(bankInformationAddParams);

        // 查询供应商财务信息
        List<BpFinance> bpFinanceList = BpFinanceService.list(new LambdaQueryWrapper<BpFinance>().eq(BpFinance::getSupplierId,supplierId).eq(BpFinance::getDeleteFlag, "NOT_DELETE"));
        List<BpFinanceAddParam> financeAddParam = BeanUtil.copyToList(bpFinanceList, BpFinanceAddParam.class);
        bpSupplierAddParam.setBpFinanceList(financeAddParam);
        return bpSupplierAddParam;
    }
}
