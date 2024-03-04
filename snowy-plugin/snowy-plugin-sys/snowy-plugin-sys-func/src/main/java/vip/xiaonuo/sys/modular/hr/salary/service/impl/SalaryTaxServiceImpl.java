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
package vip.xiaonuo.sys.modular.hr.salary.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryTax;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryTaxMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryTaxAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryTaxEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryTaxIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryTaxPageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryTaxService;

import java.util.List;

/**
 * 工资系统-专项扣除Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:38
 **/
@Service
public class SalaryTaxServiceImpl extends ServiceImpl<SalaryTaxMapper, SalaryTax> implements SalaryTaxService {

    @Override
    public Page<SalaryTax> page(SalaryTaxPageParam salaryTaxPageParam) {
        QueryWrapper<SalaryTax> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(salaryTaxPageParam.getYearMonth())) {
            queryWrapper.lambda().eq(SalaryTax::getYearMonth, salaryTaxPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryTaxPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(SalaryTax::getIdCardNumber, salaryTaxPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(salaryTaxPageParam.getUserName())) {
            queryWrapper.lambda().like(SalaryTax::getUserName, salaryTaxPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(salaryTaxPageParam.getFirstOrgName())) {
            queryWrapper.lambda().like(SalaryTax::getFirstOrgName, salaryTaxPageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryTaxPageParam.getFirstOrgId())) {
            queryWrapper.lambda().eq(SalaryTax::getFirstOrgId, salaryTaxPageParam.getFirstOrgId());
        }
        if(ObjectUtil.isNotEmpty(salaryTaxPageParam.getOrgName())) {
            queryWrapper.lambda().like(SalaryTax::getOrgName, salaryTaxPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryTaxPageParam.getOrgId())) {
            queryWrapper.lambda().eq(SalaryTax::getOrgId, salaryTaxPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(salaryTaxPageParam.getCompany())) {
            queryWrapper.lambda().like(SalaryTax::getCompany, salaryTaxPageParam.getCompany());
        }
        if(ObjectUtil.isAllNotEmpty(salaryTaxPageParam.getSortField(), salaryTaxPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryTaxPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryTaxPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryTaxPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SalaryTax::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryTaxAddParam salaryTaxAddParam) {
        //同一年月，同一花名册Id，只存在一条记录
        List<SalaryTax> oldList = this.list(new QueryWrapper<SalaryTax>().lambda()
                .eq(SalaryTax::getYearMonth,salaryTaxAddParam.getYearMonth())
                .eq(SalaryTax::getPersonnelId,salaryTaxAddParam.getPersonnelId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("{}年月{}已存在记录",salaryTaxAddParam.getYearMonth(),salaryTaxAddParam.getUserName());
        }
        SalaryTax salaryTax = BeanUtil.toBean(salaryTaxAddParam, SalaryTax.class);
        this.save(salaryTax);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryTaxEditParam salaryTaxEditParam) {
        //同一年月，同一花名册Id，只存在一条记录
        List<SalaryTax> oldList = this.list(new QueryWrapper<SalaryTax>().lambda()
                .eq(SalaryTax::getYearMonth,salaryTaxEditParam.getYearMonth())
                .eq(SalaryTax::getPersonnelId,salaryTaxEditParam.getPersonnelId())
                .ne(SalaryTax::getId,salaryTaxEditParam.getId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("{}年月{}已存在记录",salaryTaxEditParam.getYearMonth(),salaryTaxEditParam.getUserName());
        }
        SalaryTax salaryTax = this.queryEntity(salaryTaxEditParam.getId());
        BeanUtil.copyProperties(salaryTaxEditParam, salaryTax);
        this.updateById(salaryTax);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryTaxIdParam> salaryTaxIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(salaryTaxIdParamList, SalaryTaxIdParam::getId));
    }

    @Override
    public SalaryTax detail(SalaryTaxIdParam salaryTaxIdParam) {
        return this.queryEntity(salaryTaxIdParam.getId());
    }

    @Override
    public SalaryTax queryEntity(String id) {
        SalaryTax salaryTax = this.getById(id);
        if(ObjectUtil.isEmpty(salaryTax)) {
            throw new CommonException("工资系统-专项扣除不存在，id值为：{}", id);
        }
        return salaryTax;
    }
}
