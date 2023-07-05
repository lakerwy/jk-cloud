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
package vip.xiaonuo.biz.modular.crm.cooperation.service.impl;

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
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.cooperation.entity.Cooperation;
import vip.xiaonuo.biz.modular.crm.cooperation.mapper.CooperationMapper;
import vip.xiaonuo.biz.modular.crm.cooperation.param.CooperationAddParam;
import vip.xiaonuo.biz.modular.crm.cooperation.param.CooperationEditParam;
import vip.xiaonuo.biz.modular.crm.cooperation.param.CooperationIdParam;
import vip.xiaonuo.biz.modular.crm.cooperation.param.CooperationPageParam;
import vip.xiaonuo.biz.modular.crm.cooperation.service.CooperationService;
import vip.xiaonuo.common.util.CommonSnUtil;

import java.util.Date;
import java.util.List;

/**
 * 业务表-CRM-合作协议Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:53
 **/

@DS("slave_1")
@Service
public class CooperationServiceImpl extends ServiceImpl<CooperationMapper, Cooperation> implements CooperationService {

    @Override
    public Page<Cooperation> page(CooperationPageParam cooperationPageParam) {
        QueryWrapper<Cooperation> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(cooperationPageParam.getRegisterYear())) {
            queryWrapper.lambda().eq(Cooperation::getRegisterYear, cooperationPageParam.getRegisterYear());
        }
        if(ObjectUtil.isNotEmpty(cooperationPageParam.getContractNo())) {
            queryWrapper.lambda().like(Cooperation::getContractNo, cooperationPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(cooperationPageParam.getProtocolName())) {
            queryWrapper.lambda().like(Cooperation::getProtocolName, cooperationPageParam.getProtocolName());
        }
        if(ObjectUtil.isNotEmpty(cooperationPageParam.getCounterparty())) {
            queryWrapper.lambda().like(Cooperation::getCounterparty, cooperationPageParam.getCounterparty());
        }
        if(ObjectUtil.isNotEmpty(cooperationPageParam.getFirstParty())) {
            queryWrapper.lambda().like(Cooperation::getFirstParty, cooperationPageParam.getFirstParty());
        }
        if(ObjectUtil.isNotEmpty(cooperationPageParam.getCompanyOrDepartment())) {
            queryWrapper.lambda().like(Cooperation::getCompanyOrDepartment, cooperationPageParam.getCompanyOrDepartment());
        }
        if(ObjectUtil.isNotEmpty(cooperationPageParam.getOwner())) {
            queryWrapper.lambda().like(Cooperation::getOwner, cooperationPageParam.getOwner());
        }
        if(ObjectUtil.isNotEmpty(cooperationPageParam.getContractType())) {
            queryWrapper.lambda().eq(Cooperation::getContractType, cooperationPageParam.getContractType());
        }
        if(ObjectUtil.isNotEmpty(cooperationPageParam.getAgent())) {
            queryWrapper.lambda().like(Cooperation::getAgent, cooperationPageParam.getAgent());
        }
        if(ObjectUtil.isNotEmpty(cooperationPageParam.getStartSealDate()) && ObjectUtil.isNotEmpty(cooperationPageParam.getEndSealDate())) {
            queryWrapper.lambda().between(Cooperation::getSealDate, cooperationPageParam.getStartSealDate(), cooperationPageParam.getEndSealDate());
        }
        if(ObjectUtil.isNotEmpty(cooperationPageParam.getStartFilingDate()) && ObjectUtil.isNotEmpty(cooperationPageParam.getEndFilingDate())) {
            queryWrapper.lambda().between(Cooperation::getFilingDate, cooperationPageParam.getStartFilingDate(), cooperationPageParam.getEndFilingDate());
        }
        if(ObjectUtil.isNotEmpty(cooperationPageParam.getIsFiling())) {
            queryWrapper.lambda().eq(Cooperation::getIsFiling, cooperationPageParam.getIsFiling());
        }
        if(ObjectUtil.isAllNotEmpty(cooperationPageParam.getSortField(), cooperationPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cooperationPageParam.getSortOrder());
            queryWrapper.orderBy(true, cooperationPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cooperationPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Cooperation::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CooperationAddParam cooperationAddParam) {
        Cooperation cooperation = BeanUtil.toBean(cooperationAddParam, Cooperation.class);
        //处理Sn编号
        Cooperation tmp = this.getOne(new QueryWrapper<Cooperation>().lambda().orderByDesc(Cooperation::getCreateTime).last("limit 1"));
        String newSn = CommonSnUtil.GenerateSn(cooperationAddParam.getContractNo(), tmp == null ? "" : tmp.getContractNo(), CommonSnFormatEnum.SNYYYYMM0001.getValue());
        cooperation.setContractNo(newSn);
        cooperation.setOpt(StpLoginUserUtil.getLoginUser().getName());
        cooperation.setOptDate(new Date());
        cooperation.setRegisterYear(new Date().getYear());
        this.save(cooperation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CooperationEditParam cooperationEditParam) {
        Cooperation cooperation = this.queryEntity(cooperationEditParam.getId());
        BeanUtil.copyProperties(cooperationEditParam, cooperation);
        this.updateById(cooperation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CooperationIdParam> cooperationIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cooperationIdParamList, CooperationIdParam::getId));
    }

    @Override
    public Cooperation detail(CooperationIdParam cooperationIdParam) {
        return this.queryEntity(cooperationIdParam.getId());
    }

    @Override
    public Cooperation queryEntity(String id) {
        Cooperation cooperation = this.getById(id);
        if(ObjectUtil.isEmpty(cooperation)) {
            throw new CommonException("业务表-CRM-合作协议不存在，id值为：{}", id);
        }
        return cooperation;
    }
}
