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
package vip.xiaonuo.biz.modular.crm.invoiceregister.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.crm.hetong.service.HetongService;
import vip.xiaonuo.biz.modular.crm.invoiceregister.param.*;
import vip.xiaonuo.biz.modular.crm.invoiceregister.vo.InvoiceRegisterInfoVo;
import vip.xiaonuo.biz.modular.crm.invoiceregister.vo.InvoiceRegisterPageInfoVo;
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.invoiceregister.entity.Invoiceregister;
import vip.xiaonuo.biz.modular.crm.invoiceregister.mapper.InvoiceregisterMapper;
import vip.xiaonuo.biz.modular.crm.invoiceregister.service.InvoiceregisterService;
import vip.xiaonuo.common.util.CommonSnUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 业务表-CRM-合同开票登记Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:48
 **/

@DS("slave_1")
@Service
public class InvoiceregisterServiceImpl extends ServiceImpl<InvoiceregisterMapper, Invoiceregister> implements InvoiceregisterService {

    @Resource
    private HetongService hetongService;

    @Resource
    private InvoiceregisterMapper invoiceregisterMapper;

    @Override
    public Page<Invoiceregister> page(InvoiceregisterPageParam invoiceregisterPageParam) {
        QueryWrapper<Invoiceregister> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(invoiceregisterPageParam.getSearchKey())) {
            queryWrapper.lambda().like(Invoiceregister::getSn, invoiceregisterPageParam.getSearchKey()).or()
                    .like(Invoiceregister::getContractNo, invoiceregisterPageParam.getSearchKey()).or()
                    .like(Invoiceregister::getNote, invoiceregisterPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(invoiceregisterPageParam.getSn())) {
            queryWrapper.lambda().like(Invoiceregister::getSn, invoiceregisterPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(invoiceregisterPageParam.getOpt())) {
            queryWrapper.lambda().like(Invoiceregister::getOpt, invoiceregisterPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(invoiceregisterPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(invoiceregisterPageParam.getEndOptDate())) {
            queryWrapper.lambda().between(Invoiceregister::getOptDate, invoiceregisterPageParam.getStartOptDate(), invoiceregisterPageParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(invoiceregisterPageParam.getContractNo())) {
            queryWrapper.lambda().like(Invoiceregister::getContractNo, invoiceregisterPageParam.getContractNo());
        }
        if(ObjectUtil.isAllNotEmpty(invoiceregisterPageParam.getSortField(), invoiceregisterPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceregisterPageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceregisterPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceregisterPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Invoiceregister::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(InvoiceregisterAddParam invoiceregisterAddParam) {
        Invoiceregister invoiceregister = BeanUtil.toBean(invoiceregisterAddParam, Invoiceregister.class);
        //处理Sn编号
        Invoiceregister tmp = this.getOne(new QueryWrapper<Invoiceregister>().lambda().orderByDesc(Invoiceregister::getCreateTime).last("limit 1"));
        String newSn = CommonSnUtil.GenerateSn(invoiceregisterAddParam.getSn(), tmp == null ? "" : tmp.getSn(), CommonSnFormatEnum.SNYYYYMM0001.getValue());
        invoiceregister.setSn(newSn);
        invoiceregister.setOpt(StpLoginUserUtil.getLoginUser().getName());
        invoiceregister.setOptDate(new Date());
        this.save(invoiceregister);

        //反更新 合同 已开票金额
        if(StrUtil.isNotEmpty(invoiceregister.getContractNo())) {
            Hetong ht = hetongService.getOne(new QueryWrapper<Hetong>().lambda().eq(Hetong::getContractNo, invoiceregister.getContractNo()));
            if(ObjectUtil.isNotEmpty(ht)){
                BigDecimal invoiceAmount = ht.getInvoiceAmount() == null ? BigDecimal.ZERO : ht.getInvoiceAmount();
                BigDecimal addInvoiceAmount = invoiceregister.getMakeAmount() == null ? BigDecimal.ZERO : invoiceregister.getMakeAmount();
                ht.setInvoiceAmount(invoiceAmount.add(addInvoiceAmount));
                hetongService.updateById(ht);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(InvoiceregisterEditParam invoiceregisterEditParam) {
        Invoiceregister invoiceregister = this.queryEntity(invoiceregisterEditParam.getId());
        BeanUtil.copyProperties(invoiceregisterEditParam, invoiceregister);
        this.updateById(invoiceregister);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<InvoiceregisterIdParam> invoiceregisterIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(invoiceregisterIdParamList, InvoiceregisterIdParam::getId));
    }

    @Override
    public Invoiceregister detail(InvoiceregisterIdParam invoiceregisterIdParam) {
        return this.queryEntity(invoiceregisterIdParam.getId());
    }

    @Override
    public Invoiceregister queryEntity(String id) {
        Invoiceregister invoiceregister = this.getById(id);
        if(ObjectUtil.isEmpty(invoiceregister)) {
            throw new CommonException("业务表-CRM-合同开票登记不存在，id值为：{}", id);
        }
        return invoiceregister;
    }

    @Override
    public IPage<InvoiceRegisterPageInfoVo> pageList(InvoiceregisterInfoPageParam invoiceregisterInfoPageParam) {
        QueryWrapper<Invoiceregister> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(invoiceregisterInfoPageParam.getSearchKey())) {
            queryWrapper.like("a.SN", invoiceregisterInfoPageParam.getSearchKey()).or()
                    .like("a.CONTRACT_NO", invoiceregisterInfoPageParam.getSearchKey()).or()
                    .like("b.contractName", invoiceregisterInfoPageParam.getSearchKey()).or()
                    .like("b.companyOrDepartment", invoiceregisterInfoPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(invoiceregisterInfoPageParam.getSn())) {
            queryWrapper.like("a.SN", invoiceregisterInfoPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(invoiceregisterInfoPageParam.getOpt())) {
            queryWrapper.like("a.OPT", invoiceregisterInfoPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(invoiceregisterInfoPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(invoiceregisterInfoPageParam.getEndOptDate())) {
            queryWrapper.between("a.OPT_DATE", invoiceregisterInfoPageParam.getStartOptDate(), invoiceregisterInfoPageParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(invoiceregisterInfoPageParam.getContractNo())) {
            queryWrapper.like("a.CONTRACT_NO", invoiceregisterInfoPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceregisterInfoPageParam.getContractName())) {
            queryWrapper.like("b.CONTRACT_NAME", invoiceregisterInfoPageParam.getContractName());
        }
        if(ObjectUtil.isNotEmpty(invoiceregisterInfoPageParam.getCompanyOrDepartment())) {
            queryWrapper.like("b.COMPANY_OR_DEPARTMENT", invoiceregisterInfoPageParam.getCompanyOrDepartment());
        }
        queryWrapper.eq("a.DELETE_FLAG", "NOT_DELETE");

        if(ObjectUtil.isAllNotEmpty(invoiceregisterInfoPageParam.getSortField(), invoiceregisterInfoPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceregisterInfoPageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceregisterInfoPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceregisterInfoPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.SN");
        }
        return invoiceregisterMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public InvoiceRegisterInfoVo detailById(InvoiceregisterIdParam invoiceregisterIdParam) {
        InvoiceRegisterInfoVo result = new InvoiceRegisterInfoVo();
        Invoiceregister invoiceregister = this.queryEntity(invoiceregisterIdParam.getId());
        BeanUtil.copyProperties(invoiceregister, result);
        Hetong ht = hetongService.getOne(new QueryWrapper<Hetong>().lambda().eq(Hetong::getContractNo,invoiceregister.getContractNo()));
        result.setHetongInfo(ht);
        return result;
    }
}
