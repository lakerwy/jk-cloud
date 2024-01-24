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
package vip.xiaonuo.sys.modular.hr.certManage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.certManage.entity.CertManage;
import vip.xiaonuo.sys.modular.hr.certManage.mapper.CertManageMapper;
import vip.xiaonuo.sys.modular.hr.certManage.param.*;
import vip.xiaonuo.sys.modular.hr.certManage.service.CertManageService;
import vip.xiaonuo.sys.modular.hr.certManage.vo.PersonVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * 人员资质管理Service接口实现类
 *
 * @author yong.ye
 * @date  2024/01/23 17:23
 **/
@Service
public class CertManageServiceImpl extends ServiceImpl<CertManageMapper, CertManage> implements CertManageService {

    @Resource
    private CertManageMapper certManageMapper;
    @Override
    public Page<CertManage> page(CertManagePageParam certManagePageParam) {
        QueryWrapper<CertManage> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(certManagePageParam.getType())) {
            queryWrapper.lambda().eq(CertManage::getType, certManagePageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getCertType())) {
            queryWrapper.lambda().eq(CertManage::getCertType, certManagePageParam.getCertType());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getName())) {
            queryWrapper.lambda().like(CertManage::getName, certManagePageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getMajor())) {
            queryWrapper.lambda().like(CertManage::getMajor, certManagePageParam.getMajor());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getIdNumber())) {
            queryWrapper.lambda().like(CertManage::getIdNumber, certManagePageParam.getIdNumber());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getCertNo())) {
            queryWrapper.lambda().like(CertManage::getCertNo, certManagePageParam.getCertNo());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getCertSealNo())) {
            queryWrapper.lambda().like(CertManage::getCertSealNo, certManagePageParam.getCertSealNo());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getStartIssueDate()) && ObjectUtil.isNotEmpty(certManagePageParam.getEndIssueDate())) {
            queryWrapper.lambda().between(CertManage::getIssueDate, certManagePageParam.getStartIssueDate(), certManagePageParam.getEndIssueDate());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getStartValidity()) && ObjectUtil.isNotEmpty(certManagePageParam.getEndValidity())) {
            queryWrapper.lambda().between(CertManage::getValidity, certManagePageParam.getStartValidity(), certManagePageParam.getEndValidity());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getDepositAddress())) {
            queryWrapper.lambda().like(CertManage::getDepositAddress, certManagePageParam.getDepositAddress());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getUnit())) {
            queryWrapper.lambda().like(CertManage::getUnit, certManagePageParam.getUnit());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getCertName())) {
            queryWrapper.lambda().like(CertManage::getCertName, certManagePageParam.getCertName());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getJobType())) {
            queryWrapper.lambda().like(CertManage::getJobType, certManagePageParam.getJobType());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getTechnicalTitle())) {
            queryWrapper.lambda().like(CertManage::getTechnicalTitle, certManagePageParam.getTechnicalTitle());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getCompanyName())) {
            queryWrapper.lambda().like(CertManage::getCompanyName, certManagePageParam.getCompanyName());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getCertStatus())) {
            queryWrapper.lambda().eq(CertManage::getCertStatus, certManagePageParam.getCertStatus());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getUserType())) {
            queryWrapper.lambda().eq(CertManage::getUserType, certManagePageParam.getUserType());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(CertManage::getCertNo,certManagePageParam.getSearchKey())
                    .or().like(CertManage::getCertName,certManagePageParam.getSearchKey())
                    .or().like(CertManage::getCertSealNo,certManagePageParam.getSearchKey())
                    .or().like(CertManage::getCertType,certManagePageParam.getSearchKey())
                    .or().like(CertManage::getCompanyName,certManagePageParam.getSearchKey())
                    .or().like(CertManage::getDepositAddress,certManagePageParam.getSearchKey())
                    .or().like(CertManage::getIdNumber,certManagePageParam.getSearchKey())
                    .or().like(CertManage::getMajor,certManagePageParam.getSearchKey())
                    .or().like(CertManage::getUnit,certManagePageParam.getSearchKey())
                    .or().like(CertManage::getTechnicalTitle,certManagePageParam.getSearchKey())
                    .or().like(CertManage::getName,certManagePageParam.getSearchKey())
                    .or().like(CertManage::getJobType,certManagePageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(certManagePageParam.getSortField(), certManagePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(certManagePageParam.getSortOrder());
            queryWrapper.orderBy(true, certManagePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(certManagePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CertManage::getType).orderByAsc(CertManage::getCertType).orderByAsc(CertManage::getName);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CertManageAddParam certManageAddParam) {
        CertManage certManage = BeanUtil.toBean(certManageAddParam, CertManage.class);
        this.save(certManage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CertManageEditParam certManageEditParam) {
        CertManage certManage = this.queryEntity(certManageEditParam.getId());
        BeanUtil.copyProperties(certManageEditParam, certManage);
        this.updateById(certManage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CertManageIdParam> certManageIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(certManageIdParamList, CertManageIdParam::getId));
    }

    @Override
    public CertManage detail(CertManageIdParam certManageIdParam) {
        return this.queryEntity(certManageIdParam.getId());
    }

    @Override
    public CertManage queryEntity(String id) {
        CertManage certManage = this.getById(id);
        if(ObjectUtil.isEmpty(certManage)) {
            throw new CommonException("人员资质管理不存在，id值为：{}", id);
        }
        return certManage;
    }

    @Override
    public List<PersonVo> list(PersonParam personParam) {
        return certManageMapper.getPersonList(personParam);
    }
}
