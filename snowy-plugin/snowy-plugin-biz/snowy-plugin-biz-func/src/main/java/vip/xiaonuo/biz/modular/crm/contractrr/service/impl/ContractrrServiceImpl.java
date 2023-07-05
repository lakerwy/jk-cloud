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
package vip.xiaonuo.biz.modular.crm.contractrr.service.impl;

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
import vip.xiaonuo.biz.modular.crm.calocks.entity.Calocks;
import vip.xiaonuo.biz.modular.crm.contractrr.param.*;
import vip.xiaonuo.biz.modular.crm.contractrr.vo.ContractrrInfoVo;
import vip.xiaonuo.biz.modular.crm.contractrr.vo.ContractrrPageInfoVo;
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.crm.hetong.service.HetongService;
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.contractrr.entity.Contractrr;
import vip.xiaonuo.biz.modular.crm.contractrr.mapper.ContractrrMapper;
import vip.xiaonuo.biz.modular.crm.contractrr.service.ContractrrService;
import vip.xiaonuo.common.util.CommonSnUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 业务表-CRM-合同收款登记Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:47
 **/
@DS("slave_1")
@Service
public class ContractrrServiceImpl extends ServiceImpl<ContractrrMapper, Contractrr> implements ContractrrService {

    @Resource
    private HetongService hetongService;

    @Resource
    private ContractrrMapper contractrrMapper;

    @Override
    public Page<Contractrr> page(ContractrrPageParam contractrrPageParam) {
        QueryWrapper<Contractrr> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(contractrrPageParam.getSearchKey())) {
            queryWrapper.lambda().like(Contractrr::getSn, contractrrPageParam.getSearchKey()).or()
                    .like(Contractrr::getContractNo, contractrrPageParam.getSearchKey()).or()
                    .like(Contractrr::getOpt, contractrrPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(contractrrPageParam.getSn())) {
            queryWrapper.lambda().like(Contractrr::getSn, contractrrPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(contractrrPageParam.getOpt())) {
            queryWrapper.lambda().like(Contractrr::getOpt, contractrrPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(contractrrPageParam.getStartOptDate()) && ObjectUtil.isNotEmpty(contractrrPageParam.getEndOptDate())) {
            queryWrapper.lambda().between(Contractrr::getOptDate, contractrrPageParam.getStartOptDate(), contractrrPageParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(contractrrPageParam.getContractNo())) {
            queryWrapper.lambda().like(Contractrr::getContractNo, contractrrPageParam.getContractNo());
        }
        if(ObjectUtil.isAllNotEmpty(contractrrPageParam.getSortField(), contractrrPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(contractrrPageParam.getSortOrder());
            queryWrapper.orderBy(true, contractrrPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(contractrrPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Contractrr::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ContractrrAddParam contractrrAddParam) {
        Contractrr contractrr = BeanUtil.toBean(contractrrAddParam, Contractrr.class);
        //处理Sn编号
        Contractrr tmp = this.getOne(new QueryWrapper<Contractrr>().lambda().orderByDesc(Contractrr::getCreateTime).last("limit 1"));
        String newSn = CommonSnUtil.GenerateSn(contractrrAddParam.getSn(), tmp == null ? "" : tmp.getSn(), CommonSnFormatEnum.SNYYYYMM0001.getValue());
        contractrr.setSn(newSn);
        contractrr.setOpt(StpLoginUserUtil.getLoginUser().getName());
        contractrr.setOptDate(new Date());
        this.save(contractrr);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ContractrrEditParam contractrrEditParam) {
        Contractrr contractrr = this.queryEntity(contractrrEditParam.getId());
        BeanUtil.copyProperties(contractrrEditParam, contractrr);
        this.updateById(contractrr);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ContractrrIdParam> contractrrIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(contractrrIdParamList, ContractrrIdParam::getId));
    }

    @Override
    public Contractrr detail(ContractrrIdParam contractrrIdParam) {
        return this.queryEntity(contractrrIdParam.getId());
    }

    @Override
    public Contractrr queryEntity(String id) {
        Contractrr contractrr = this.getById(id);
        if(ObjectUtil.isEmpty(contractrr)) {
            throw new CommonException("业务表-CRM-合同收款登记不存在，id值为：{}", id);
        }
        return contractrr;
    }

    @Override
    public IPage<ContractrrPageInfoVo> pageList(ContractrrPageInfoParam contractrrPageInfoParam) {
        QueryWrapper<Contractrr> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(contractrrPageInfoParam.getSearchKey())) {
            queryWrapper.like("a.SN", contractrrPageInfoParam.getSearchKey()).or()
                    .like("a.CONTRACT_NO", contractrrPageInfoParam.getSearchKey()).or()
                    .like("b.CONTRACT_NAME", contractrrPageInfoParam.getSearchKey()).or()
                    .like("b.COMPANY_OR_DEPARTMENT", contractrrPageInfoParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(contractrrPageInfoParam.getSn())) {
            queryWrapper.like("a.SN", contractrrPageInfoParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(contractrrPageInfoParam.getOpt())) {
            queryWrapper.like("a.OPT", contractrrPageInfoParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(contractrrPageInfoParam.getStartOptDate()) && ObjectUtil.isNotEmpty(contractrrPageInfoParam.getEndOptDate())) {
            queryWrapper.between("a.OPT_DATE", contractrrPageInfoParam.getStartOptDate(), contractrrPageInfoParam.getEndOptDate());
        }
        if(ObjectUtil.isNotEmpty(contractrrPageInfoParam.getContractNo())) {
            queryWrapper.like("a.CONTRACT_NO", contractrrPageInfoParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(contractrrPageInfoParam.getContractName())) {
            queryWrapper.like("b.CONTRACT_NAME", contractrrPageInfoParam.getContractName());
        }
        if(ObjectUtil.isNotEmpty(contractrrPageInfoParam.getCompanyOrDepartment())) {
            queryWrapper.like("b.COMPANY_OR_DEPARTMENT", contractrrPageInfoParam.getCompanyOrDepartment());
        }
        queryWrapper.eq("a.DELETE_FLAG", "NOT_DELETE");
        if(ObjectUtil.isAllNotEmpty(contractrrPageInfoParam.getSortField(), contractrrPageInfoParam.getSortOrder())) {
            CommonSortOrderEnum.validate(contractrrPageInfoParam.getSortOrder());
            queryWrapper.orderBy(true, contractrrPageInfoParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(contractrrPageInfoParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.SN");
        }
        return contractrrMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public ContractrrInfoVo detailById(ContractrrIdParam contractrrIdParam) {
        ContractrrInfoVo result = new ContractrrInfoVo();
        Contractrr contractrr = this.queryEntity(contractrrIdParam.getId());
        BeanUtil.copyProperties(contractrr, result);
        Hetong ht = hetongService.getOne(new QueryWrapper<Hetong>().lambda().eq(Hetong::getContractNo,contractrr.getContractNo()));
        result.setHetongInfo(ht);
        return result;
    }


}
