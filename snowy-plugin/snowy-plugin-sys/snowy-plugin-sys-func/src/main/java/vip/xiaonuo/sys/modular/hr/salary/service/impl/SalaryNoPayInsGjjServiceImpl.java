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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryNoPayInsGjj;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryNoPayInsGjjMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryNoPayInsGjjAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryNoPayInsGjjEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryNoPayInsGjjIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryNoPayInsGjjPageParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryNoPayInsGjjService;
import vip.xiaonuo.sys.modular.hr.salary.util.AesUtil;

import java.util.List;

/**
 * 工资系统 - 无工资人员社保|公积金(不参与工资计算)Service接口实现类
 *
 * @author yong.ye
 * @date  2024/06/17 16:06
 **/
@Service
public class SalaryNoPayInsGjjServiceImpl extends ServiceImpl<SalaryNoPayInsGjjMapper, SalaryNoPayInsGjj> implements SalaryNoPayInsGjjService {

    @Override
    public Page<SalaryNoPayInsGjj> page(SalaryNoPayInsGjjPageParam salaryNopayInsgjjPageParam) {
        QueryWrapper<SalaryNoPayInsGjj> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(salaryNopayInsgjjPageParam.getType())) {
            queryWrapper.lambda().eq(SalaryNoPayInsGjj::getType, salaryNopayInsgjjPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(salaryNopayInsgjjPageParam.getUserType())) {
            queryWrapper.lambda().eq(SalaryNoPayInsGjj::getUserType, salaryNopayInsgjjPageParam.getUserType());
        }
        if(ObjectUtil.isNotEmpty(salaryNopayInsgjjPageParam.getCompany())) {
            queryWrapper.lambda().like(SalaryNoPayInsGjj::getCompany, salaryNopayInsgjjPageParam.getCompany());
        }
        if(ObjectUtil.isNotEmpty(salaryNopayInsgjjPageParam.getYearMonth())) {
            queryWrapper.lambda().eq(SalaryNoPayInsGjj::getYearMonth, salaryNopayInsgjjPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryNopayInsgjjPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(SalaryNoPayInsGjj::getIdCardNumber, salaryNopayInsgjjPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(salaryNopayInsgjjPageParam.getUserName())) {
            queryWrapper.lambda().like(SalaryNoPayInsGjj::getUserName, salaryNopayInsgjjPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(salaryNopayInsgjjPageParam.getFirstOrgName())) {
            queryWrapper.lambda().like(SalaryNoPayInsGjj::getFirstOrgName, salaryNopayInsgjjPageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryNopayInsgjjPageParam.getUnitName())) {
            queryWrapper.lambda().like(SalaryNoPayInsGjj::getUnitName, salaryNopayInsgjjPageParam.getUnitName());
        }
        if(ObjectUtil.isAllNotEmpty(salaryNopayInsgjjPageParam.getSortField(), salaryNopayInsgjjPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryNopayInsgjjPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryNopayInsgjjPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryNopayInsgjjPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(SalaryNoPayInsGjj::getYearMonth).orderByAsc(SalaryNoPayInsGjj::getFirstOrgName).orderByAsc(SalaryNoPayInsGjj::getIdCardNumber);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryNoPayInsGjjAddParam salaryNopayInsgjjAddParam) {
        //同一年月，同一员工，同一缴纳单位，只存在一条记录
        List<SalaryNoPayInsGjj> oldList = this.list(new QueryWrapper<SalaryNoPayInsGjj>().lambda()
                .eq(SalaryNoPayInsGjj::getUnitName,salaryNopayInsgjjAddParam.getUnitName())
                .eq(SalaryNoPayInsGjj::getYearMonth,salaryNopayInsgjjAddParam.getYearMonth())
                .eq(SalaryNoPayInsGjj::getIdCardNumber, AesUtil.encrypt(salaryNopayInsgjjAddParam.getIdCardNumber()))
                .eq(SalaryNoPayInsGjj::getType, salaryNopayInsgjjAddParam.getType()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("当前员工本年月本公司已存在记录");
        }
        SalaryNoPayInsGjj salaryNopayInsgjj = BeanUtil.toBean(AesUtil.encryptGZ(salaryNopayInsgjjAddParam), SalaryNoPayInsGjj.class);
        this.save(salaryNopayInsgjj);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryNoPayInsGjjEditParam salaryNopayInsgjjEditParam) {
        //同一年月，同一员工，同一缴纳单位，只存在一条记录
        List<SalaryNoPayInsGjj> oldList = this.list(new QueryWrapper<SalaryNoPayInsGjj>().lambda()
                .eq(SalaryNoPayInsGjj::getUnitName,salaryNopayInsgjjEditParam.getUnitName())
                .eq(SalaryNoPayInsGjj::getYearMonth,salaryNopayInsgjjEditParam.getYearMonth())
                .eq(SalaryNoPayInsGjj::getIdCardNumber, AesUtil.encrypt(salaryNopayInsgjjEditParam.getIdCardNumber()))
                .eq(SalaryNoPayInsGjj::getType, salaryNopayInsgjjEditParam.getType())
                .ne(SalaryNoPayInsGjj::getId,salaryNopayInsgjjEditParam.getId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("当前员工本年月本公司已存在记录");
        }
        SalaryNoPayInsGjj salaryNopayInsgjj = this.queryEntity(salaryNopayInsgjjEditParam.getId());
        BeanUtil.copyProperties(AesUtil.encryptGZ(salaryNopayInsgjjEditParam), salaryNopayInsgjj);
        this.updateById(salaryNopayInsgjj);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryNoPayInsGjjIdParam> salaryNoPayInsGjjIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(salaryNoPayInsGjjIdParamList, SalaryNoPayInsGjjIdParam::getId));
    }

    @Override
    public SalaryNoPayInsGjj detail(SalaryNoPayInsGjjIdParam salaryNopayInsgjjIdParam) {
        return this.queryEntity(salaryNopayInsgjjIdParam.getId());
    }

    @Override
    public SalaryNoPayInsGjj queryEntity(String id) {
        SalaryNoPayInsGjj salaryNopayInsgjj = this.getById(id);
        if(ObjectUtil.isEmpty(salaryNopayInsgjj)) {
            throw new CommonException("工资系统 - 无工资人员社保|公积金(不参与工资计算)不存在，id值为：{}", id);
        }
        return salaryNopayInsgjj;
    }
}
