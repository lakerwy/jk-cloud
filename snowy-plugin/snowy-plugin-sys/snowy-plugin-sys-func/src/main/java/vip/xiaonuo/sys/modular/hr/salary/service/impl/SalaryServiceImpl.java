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
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.salary.entity.Salary;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.*;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.ConfirmSalaryParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.EmpSalaryParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.PayParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryService;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工资系统-工资表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/01 16:50
 **/
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements SalaryService {

    @Resource
    private SalaryMapper salaryMapper;

    @Override
    public Page<Salary> page(SalaryPageParam salaryPageParam) {
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(salaryPageParam.getYearMonth())) {
            queryWrapper.lambda().eq(Salary::getYearMonth, salaryPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getType())){
            queryWrapper.lambda().eq(Salary::getType,salaryPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getUserType())){
            queryWrapper.lambda().eq(Salary::getUserType,salaryPageParam.getUserType());
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(Salary::getIdCardNumber, salaryPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getUserName())) {
            queryWrapper.lambda().like(Salary::getUserName, salaryPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getFirstOrgName())) {
            queryWrapper.lambda().like(Salary::getFirstOrgName, salaryPageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getOrgName())) {
            queryWrapper.lambda().like(Salary::getOrgName, salaryPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getPosition())) {
            queryWrapper.lambda().like(Salary::getPosition, salaryPageParam.getPosition());
        }
        if(ObjectUtil.isAllNotEmpty(salaryPageParam.getSortField(), salaryPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Salary::getYearMonth).orderByAsc(Salary::getIdCardNumber);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public void add(SalaryAddParam salaryAddParam) {
        Salary salary = BeanUtil.toBean(salaryAddParam, Salary.class);
        this.save(salary);
    }

    @Override
    public void edit(SalaryEditParam salaryEditParam) {
        Salary salary = this.queryEntity(salaryEditParam.getId());
        BeanUtil.copyProperties(salaryEditParam, salary);
        this.updateById(salary);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryIdParam> salaryIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(salaryIdParamList, SalaryIdParam::getId));
    }

    @Override
    public Salary detail(SalaryIdParam salaryIdParam) {
        return this.queryEntity(salaryIdParam.getId());
    }

    @Override
    public Salary queryEntity(String id) {
        Salary salary = this.getById(id);
        if(ObjectUtil.isEmpty(salary)) {
            throw new CommonException("工资系统-工资表不存在，id值为：{}", id);
        }
        return salary;
    }

    @Override
    public Page<Salary> pageList(SalaryPageParam salaryPageParam) {
        //基础数据源：员工月度工资标准

        return null;
    }

    @Override
    public List<Salary> empSalary(EmpSalaryParam empSalaryParam) {
        if(ObjectUtil.isEmpty(empSalaryParam.getYear()) || ObjectUtil.isEmpty(empSalaryParam.getYearMonth())){
            throw new CommonException("请输入所属年月");
        }
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(empSalaryParam.getYearMonth())) {
            queryWrapper.lambda().eq(Salary::getYearMonth,empSalaryParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(empSalaryParam.getYear())){
            queryWrapper.lambda().likeRight(Salary::getYearMonth,empSalaryParam.getYear());
        }
        queryWrapper.lambda().eq(Salary::getUserId, StpLoginUserUtil.getLoginUser().getId());
        queryWrapper.lambda().orderByDesc(Salary::getYearMonth);
        return this.list(queryWrapper);
    }


    /**
     * 月工资报表 - 2部分组成 ：已确认的封存数据 + 未确认的计算数据
     * 注意：
     * 已封存的数据：只能从Salary取
     * 未确认的数据：通过SalaryStandard月工资标准，自动生成
     * @param confirmSalaryParam
     * @return
     */
    @Override
    public List<SalaryVo> confirmPageList(ConfirmSalaryParam confirmSalaryParam) {
        String yearMonth = confirmSalaryParam.getYearMonth();
        if(ObjectUtil.isEmpty(yearMonth)){
            throw new CommonException("所属年月必传");
        }
        //查询条件
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(confirmSalaryParam.getUserType())){
            queryWrapper.eq("tmp.userType",confirmSalaryParam.getUserType());
        }
        if(ObjectUtil.isNotEmpty(confirmSalaryParam.getYearMonth())){
            queryWrapper.eq("tmp.yearMonth",confirmSalaryParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(confirmSalaryParam.getIdCardNumber())){
            queryWrapper.eq("tmp.idCardNumber",confirmSalaryParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(confirmSalaryParam.getUserName())){
            queryWrapper.eq("tmp.userName",confirmSalaryParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(confirmSalaryParam.getOrgName())){
            queryWrapper.eq("tmp.orgName",confirmSalaryParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(confirmSalaryParam.getConfirmStatus())){
            queryWrapper.eq("tmp.confirmStatus",confirmSalaryParam.getConfirmStatus());
        }
        if(ObjectUtil.isNotEmpty(confirmSalaryParam.getSearchKey())){
            queryWrapper.and(q->q.like("tmp.idCardNumber",confirmSalaryParam.getSearchKey())
                    .or().like("tmp.userName",confirmSalaryParam.getSearchKey())
                    .or().like("tmp.orgName",confirmSalaryParam.getSearchKey()));
        }
        queryWrapper.orderByDesc("tmp.yearMonth").orderByAsc("tmp.idCardNumber");
        return salaryMapper.getConfirmGZ(yearMonth,queryWrapper);
    }

    @Override
    public List<SalaryVo> getSalaryAllList(String type, List<String> yearMonths) {
        if(CollUtil.isEmpty(yearMonths)){
            return null;
        }
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(type)) {
            queryWrapper.lambda().eq(Salary::getType,type);
        }
        queryWrapper.lambda().in(Salary::getYearMonth,yearMonths);
        return BeanUtil.copyToList(this.list(queryWrapper),SalaryVo.class);
    }

    @Override
    public void deleteByMonth(PayParam param) {
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Salary::getYearMonth,param.getYearMonth());
        this.remove(queryWrapper);
    }
}
