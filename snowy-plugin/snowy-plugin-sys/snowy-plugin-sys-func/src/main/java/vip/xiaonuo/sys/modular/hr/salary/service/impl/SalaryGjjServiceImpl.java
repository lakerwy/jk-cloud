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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryGjj;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryGjjMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryGjjAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryGjjEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryGjjIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryGjjPageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryGjjService;

import java.util.List;

/**
 * 工资系统-公积金明细Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:28
 **/
@Service
public class SalaryGjjServiceImpl extends ServiceImpl<SalaryGjjMapper, SalaryGjj> implements SalaryGjjService {

    @Override
    public Page<SalaryGjj> page(SalaryGjjPageParam salaryGjjPageParam) {
        QueryWrapper<SalaryGjj> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(salaryGjjPageParam.getYearMonth())) {
            queryWrapper.lambda().eq(SalaryGjj::getYearMonth, salaryGjjPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryGjjPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(SalaryGjj::getIdCardNumber, salaryGjjPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(salaryGjjPageParam.getUserName())) {
            queryWrapper.lambda().like(SalaryGjj::getUserName, salaryGjjPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(salaryGjjPageParam.getFirstOrgName())) {
            queryWrapper.lambda().like(SalaryGjj::getFirstOrgName, salaryGjjPageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryGjjPageParam.getFirstOrgId())) {
            queryWrapper.lambda().eq(SalaryGjj::getFirstOrgId, salaryGjjPageParam.getFirstOrgId());
        }
        if(ObjectUtil.isNotEmpty(salaryGjjPageParam.getOrgName())) {
            queryWrapper.lambda().like(SalaryGjj::getOrgName, salaryGjjPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryGjjPageParam.getOrgId())) {
            queryWrapper.lambda().eq(SalaryGjj::getOrgId, salaryGjjPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(salaryGjjPageParam.getCompany())) {
            queryWrapper.lambda().like(SalaryGjj::getCompany, salaryGjjPageParam.getCompany());
        }
        if(ObjectUtil.isAllNotEmpty(salaryGjjPageParam.getSortField(), salaryGjjPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryGjjPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryGjjPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryGjjPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SalaryGjj::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryGjjAddParam salaryGjjAddParam) {
        //同一年月，同一花名册Id，只存在一条记录
        List<SalaryGjj> oldList = this.list(new QueryWrapper<SalaryGjj>().lambda()
                .eq(SalaryGjj::getYearMonth,salaryGjjAddParam.getYearMonth())
                .eq(SalaryGjj::getPersonnelId,salaryGjjAddParam.getPersonnelId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("{}年月{}已存在记录",salaryGjjAddParam.getYearMonth(),salaryGjjAddParam.getUserName());
        }
        SalaryGjj salaryGjj = BeanUtil.toBean(salaryGjjAddParam, SalaryGjj.class);
        this.save(salaryGjj);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryGjjEditParam salaryGjjEditParam) {
        //同一年月，同一花名册Id，只存在一条记录
        List<SalaryGjj> oldList = this.list(new QueryWrapper<SalaryGjj>().lambda()
                .eq(SalaryGjj::getYearMonth,salaryGjjEditParam.getYearMonth())
                .eq(SalaryGjj::getPersonnelId,salaryGjjEditParam.getPersonnelId())
                .ne(SalaryGjj::getId,salaryGjjEditParam.getId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("{}年月{}已存在记录",salaryGjjEditParam.getYearMonth(),salaryGjjEditParam.getUserName());
        }
        SalaryGjj salaryGjj = this.queryEntity(salaryGjjEditParam.getId());
        BeanUtil.copyProperties(salaryGjjEditParam, salaryGjj);
        this.updateById(salaryGjj);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryGjjIdParam> salaryGjjIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(salaryGjjIdParamList, SalaryGjjIdParam::getId));
    }

    @Override
    public SalaryGjj detail(SalaryGjjIdParam salaryGjjIdParam) {
        return this.queryEntity(salaryGjjIdParam.getId());
    }

    @Override
    public SalaryGjj queryEntity(String id) {
        SalaryGjj salaryGjj = this.getById(id);
        if(ObjectUtil.isEmpty(salaryGjj)) {
            throw new CommonException("工资系统-公积金明细不存在，id值为：{}", id);
        }
        return salaryGjj;
    }
}
