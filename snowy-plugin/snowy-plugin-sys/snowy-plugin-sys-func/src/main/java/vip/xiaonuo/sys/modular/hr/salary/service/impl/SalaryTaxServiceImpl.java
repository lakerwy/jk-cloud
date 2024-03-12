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
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryTaxVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工资系统-专项扣除Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:38
 **/
@Service
public class SalaryTaxServiceImpl extends ServiceImpl<SalaryTaxMapper, SalaryTax> implements SalaryTaxService {

    @Resource
    private SalaryTaxMapper salaryTaxMapper;

    @Override
    public Page<SalaryTaxVo> page(SalaryTaxPageParam salaryTaxPageParam) {
        QueryWrapper<SalaryTax> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(salaryTaxPageParam.getYearMonth())) {
            queryWrapper.eq("b.`YEAR_MONTH`", salaryTaxPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryTaxPageParam.getIdCardNumber())) {
            queryWrapper.like("b.ID_CARD_NUMBER", salaryTaxPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(salaryTaxPageParam.getUserName())) {
            queryWrapper.like("b.USER_NAME", salaryTaxPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(salaryTaxPageParam.getFirstOrgName())) {
            queryWrapper.like("b.FIRST_ORG_NAME", salaryTaxPageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryTaxPageParam.getOrgName())) {
            queryWrapper.like("b.ORG_NAME", salaryTaxPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryTaxPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("b.ID_CARD_NUMBER",salaryTaxPageParam.getSearchKey())
                    .or().like("b.USER_NAME",salaryTaxPageParam.getSearchKey())
                    .or().like("b.FIRST_ORG_NAME",salaryTaxPageParam.getSearchKey())
                    .or().like("b.ORG_NAME",salaryTaxPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(salaryTaxPageParam.getSortField(), salaryTaxPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryTaxPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryTaxPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryTaxPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("b.`YEAR_MONTH`").orderByAsc("b.ID_CARD_NUMBER");
        }
        return salaryTaxMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryTaxAddParam salaryTaxAddParam) {
        //同一年月，同一花名册Id，只存在一条记录
        List<SalaryTax> oldList = this.list(new QueryWrapper<SalaryTax>().lambda()
                        .eq(SalaryTax::getUnitName,salaryTaxAddParam.getUnitName())
                        .eq(SalaryTax::getStandardId,salaryTaxAddParam.getStandardId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("当前员工本年月本公司已存在记录");
        }
        SalaryTax salaryTax = BeanUtil.toBean(salaryTaxAddParam, SalaryTax.class);
        this.save(salaryTax);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryTaxEditParam salaryTaxEditParam) {
        //同一年月，同一花名册Id，只存在一条记录
        List<SalaryTax> oldList = this.list(new QueryWrapper<SalaryTax>().lambda()
                .eq(SalaryTax::getUnitName,salaryTaxEditParam.getUnitName())
                .eq(SalaryTax::getStandardId,salaryTaxEditParam.getStandardId())
                .ne(SalaryTax::getId,salaryTaxEditParam.getId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("当前员工本年月本公司已存在记录");
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
    public SalaryTaxVo detail(SalaryTaxIdParam salaryTaxIdParam) {
        return salaryTaxMapper.getDetailById(salaryTaxIdParam.getId());
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
