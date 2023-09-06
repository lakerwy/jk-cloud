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
package vip.xiaonuo.biz.modular.epc.safety.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.epc.safety.entity.EpcPersonnelLedger;
import vip.xiaonuo.biz.modular.epc.safety.entity.EpcPersonnelSpecial;
import vip.xiaonuo.biz.modular.epc.safety.mapper.EpcPersonnelLedgerMapper;
import vip.xiaonuo.biz.modular.epc.safety.param.*;
import vip.xiaonuo.biz.modular.epc.safety.service.EpcPersonnelLedgerService;
import vip.xiaonuo.biz.modular.epc.safety.service.EpcPersonnelSpecialService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * EPC-现场人员台账信息表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/05 14:54
 **/
@Service
public class EpcPersonnelLedgerServiceImpl extends ServiceImpl<EpcPersonnelLedgerMapper, EpcPersonnelLedger> implements EpcPersonnelLedgerService {

    @Resource
    EpcPersonnelSpecialService epcPersonnelSpecialService;

    @Override
    public Page<EpcPersonnelLedger> page(EpcPersonnelLedgerPageParam epcPersonnelLedgerPageParam) {
        QueryWrapper<EpcPersonnelLedger> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getTenantId())) {
            queryWrapper.lambda().eq(EpcPersonnelLedger::getTenantId, epcPersonnelLedgerPageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getProjectId())) {
            queryWrapper.lambda().eq(EpcPersonnelLedger::getProjectId, epcPersonnelLedgerPageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getName())) {
            queryWrapper.lambda().like(EpcPersonnelLedger::getName, epcPersonnelLedgerPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getIdCard())) {
            queryWrapper.lambda().like(EpcPersonnelLedger::getIdCard, epcPersonnelLedgerPageParam.getIdCard());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getGender())) {
            queryWrapper.lambda().eq(EpcPersonnelLedger::getGender, epcPersonnelLedgerPageParam.getGender());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getPhone())) {
            queryWrapper.lambda().like(EpcPersonnelLedger::getPhone, epcPersonnelLedgerPageParam.getPhone());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getHireStatus())) {
            queryWrapper.lambda().eq(EpcPersonnelLedger::getHireStatus, epcPersonnelLedgerPageParam.getHireStatus());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getBelongCompany())) {
            queryWrapper.lambda().like(EpcPersonnelLedger::getBelongCompany, epcPersonnelLedgerPageParam.getBelongCompany());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getWorkType())) {
            queryWrapper.lambda().eq(EpcPersonnelLedger::getWorkType, epcPersonnelLedgerPageParam.getWorkType());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getStartEnterTime()) && ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getEndEnterTime())) {
            queryWrapper.lambda().between(EpcPersonnelLedger::getEnterTime, epcPersonnelLedgerPageParam.getStartEnterTime(), epcPersonnelLedgerPageParam.getEndEnterTime());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getStartOutTime()) && ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getEndOutTime())) {
            queryWrapper.lambda().between(EpcPersonnelLedger::getOutTime, epcPersonnelLedgerPageParam.getStartOutTime(), epcPersonnelLedgerPageParam.getEndOutTime());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getIsInsured())) {
            queryWrapper.lambda().eq(EpcPersonnelLedger::getIsInsured, epcPersonnelLedgerPageParam.getIsInsured());
        }
        if(ObjectUtil.isNotEmpty(epcPersonnelLedgerPageParam.getIsSafety())) {
            queryWrapper.lambda().eq(EpcPersonnelLedger::getIsSafety, epcPersonnelLedgerPageParam.getIsSafety());
        }
        if(ObjectUtil.isAllNotEmpty(epcPersonnelLedgerPageParam.getSortField(), epcPersonnelLedgerPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcPersonnelLedgerPageParam.getSortOrder());
            queryWrapper.orderBy(true, epcPersonnelLedgerPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcPersonnelLedgerPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(EpcPersonnelLedger::getCreateTime);
        }
        Page<EpcPersonnelLedger> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        result.getRecords().forEach(x->{
           boolean isCertificate = epcPersonnelSpecialService.list(new QueryWrapper<EpcPersonnelSpecial>().lambda().eq(EpcPersonnelSpecial::getPersonnelId,x.getId())).size()>0;
           x.setCertificateStage(isCertificate?"有":"无");
        });
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EpcPersonnelLedgerAddParam epcPersonnelLedgerAddParam) {
        EpcPersonnelLedger epcPersonnelLedger = BeanUtil.toBean(epcPersonnelLedgerAddParam, EpcPersonnelLedger.class);
        this.save(epcPersonnelLedger);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EpcPersonnelLedgerEditParam epcPersonnelLedgerEditParam) {
        EpcPersonnelLedger epcPersonnelLedger = this.queryEntity(epcPersonnelLedgerEditParam.getId());
        BeanUtil.copyProperties(epcPersonnelLedgerEditParam, epcPersonnelLedger);
        this.updateById(epcPersonnelLedger);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EpcPersonnelLedgerIdParam> epcPersonnelLedgerIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(epcPersonnelLedgerIdParamList, EpcPersonnelLedgerIdParam::getId));
    }

    @Override
    public EpcPersonnelLedger detail(EpcPersonnelLedgerIdParam epcPersonnelLedgerIdParam) {
        EpcPersonnelLedger epcPersonnelLedger = this.queryEntity(epcPersonnelLedgerIdParam.getId());
        boolean isCertificate = epcPersonnelSpecialService.list(new QueryWrapper<EpcPersonnelSpecial>().lambda().eq(EpcPersonnelSpecial::getPersonnelId,epcPersonnelLedger.getId())).size()>0;
        epcPersonnelLedger.setCertificateStage(isCertificate?"有":"无");
        return epcPersonnelLedger;
    }

    @Override
    public EpcPersonnelLedger queryEntity(String id) {
        EpcPersonnelLedger epcPersonnelLedger = this.getById(id);
        if(ObjectUtil.isEmpty(epcPersonnelLedger)) {
            throw new CommonException("EPC-现场人员台账信息表不存在，id值为：{}", id);
        }
        return epcPersonnelLedger;
    }
}
