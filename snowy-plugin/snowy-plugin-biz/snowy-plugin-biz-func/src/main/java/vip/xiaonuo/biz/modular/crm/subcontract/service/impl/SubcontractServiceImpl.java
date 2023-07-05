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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.crm.rollinout.entity.Rollinout;
import vip.xiaonuo.biz.modular.crm.subcontract.param.*;
import vip.xiaonuo.biz.modular.crm.subcontract.vo.SubContractInfoVo;
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.Subcontract;
import vip.xiaonuo.biz.modular.crm.subcontract.mapper.SubcontractMapper;
import vip.xiaonuo.biz.modular.crm.subcontract.service.SubcontractService;
import vip.xiaonuo.common.util.CommonSnUtil;

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
    @Override
    public Page<Subcontract> page(SubcontractPageParam subcontractPageParam) {
        QueryWrapper<Subcontract> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(subcontractPageParam.getSearchKey())) {
            queryWrapper.lambda().like(Subcontract::getSecondLevel, subcontractPageParam.getSearchKey()).or()
                    .like(Subcontract::getOpt, subcontractPageParam.getSearchKey()).or()
                    .like(Subcontract::getContractNo, subcontractPageParam.getSearchKey()).or()
                    .like(Subcontract::getCompanyOrDepartment, subcontractPageParam.getSearchKey()).or()
                    .like(Subcontract::getProjectName, subcontractPageParam.getSearchKey()).or()
                    .like(Subcontract::getCounterparty, subcontractPageParam.getSearchKey()).or()
                    .like(Subcontract::getMainContractNo, subcontractPageParam.getSearchKey()).or()
                    .like(Subcontract::getOwner, subcontractPageParam.getSearchKey()).or()
                    .like(Subcontract::getFirstparty, subcontractPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getOpt())) {
            queryWrapper.lambda().like(Subcontract::getOpt, subcontractPageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getStartRegisterDate()) && ObjectUtil.isNotEmpty(subcontractPageParam.getEndRegisterDate())) {
            queryWrapper.lambda().between(Subcontract::getRegisterDate, subcontractPageParam.getStartRegisterDate(), subcontractPageParam.getEndRegisterDate());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getIsFiling())) {
            queryWrapper.lambda().eq(Subcontract::getIsFiling, subcontractPageParam.getIsFiling());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getSecondLevel())) {
            queryWrapper.lambda().eq(Subcontract::getSecondLevel, subcontractPageParam.getSecondLevel());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getContractNo())) {
            queryWrapper.lambda().like(Subcontract::getContractNo, subcontractPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getProjectName())) {
            queryWrapper.lambda().like(Subcontract::getProjectName, subcontractPageParam.getProjectName());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getCounterparty())) {
            queryWrapper.lambda().like(Subcontract::getCounterparty, subcontractPageParam.getCounterparty());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getFirstparty())) {
            queryWrapper.lambda().like(Subcontract::getFirstparty, subcontractPageParam.getFirstparty());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getCompanyOrDepartment())) {
            queryWrapper.lambda().like(Subcontract::getCompanyOrDepartment, subcontractPageParam.getCompanyOrDepartment());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getOwner())) {
            queryWrapper.lambda().like(Subcontract::getOwner, subcontractPageParam.getOwner());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getStartSealDate()) && ObjectUtil.isNotEmpty(subcontractPageParam.getEndSealDate())) {
            queryWrapper.lambda().between(Subcontract::getSealDate, subcontractPageParam.getStartSealDate(), subcontractPageParam.getEndSealDate());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getHetongId())) {
            queryWrapper.lambda().eq(Subcontract::getHetongId, subcontractPageParam.getHetongId());
        }
        if(ObjectUtil.isNotEmpty(subcontractPageParam.getMainContractNo())) {
            queryWrapper.lambda().like(Subcontract::getMainContractNo, subcontractPageParam.getMainContractNo());
        }
        if(ObjectUtil.isAllNotEmpty(subcontractPageParam.getSortField(), subcontractPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subcontractPageParam.getSortOrder());
            queryWrapper.orderBy(true, subcontractPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subcontractPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Subcontract::getContractNo);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubcontractAddParam subcontractAddParam) {
        Subcontract subcontract = BeanUtil.toBean(subcontractAddParam, Subcontract.class);
        //处理Sn编号
        Subcontract tmp = this.getOne(new QueryWrapper<Subcontract>().lambda().orderByDesc(Subcontract::getCreateTime).last("limit 1"));
        String newSn = CommonSnUtil.GenerateSn(subcontractAddParam.getContractNo(), tmp == null ? "" : tmp.getContractNo(), CommonSnFormatEnum.SNYYYYMM0001.getValue());
        subcontract.setContractNo(newSn);
        subcontract.setOpt(StpLoginUserUtil.getLoginUser().getName());
        subcontract.setRegisterDate(new Date());
        this.save(subcontract);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubcontractEditParam subcontractEditParam) {
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

}
