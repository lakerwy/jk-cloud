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

import cn.hutool.core.collection.CollStreamUtil;
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
import vip.xiaonuo.sys.modular.hr.salary.entity.*;
import vip.xiaonuo.sys.modular.hr.salary.enums.UserTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.enums.YesOrNoEnum;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.*;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.SearchParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.UpdateFieldParam;
import vip.xiaonuo.sys.modular.hr.salary.service.*;
import vip.xiaonuo.sys.modular.hr.salary.util.AesUtil;

import java.util.Comparator;
import java.util.List;

/**
 * 工资系统-工资表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/01 16:50
 **/
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements SalaryService {

    @Override
    public Page<Salary> page(SalaryPageParam salaryPageParam) {
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(salaryPageParam.getYearMonth())) {
            queryWrapper.lambda().eq(Salary::getYearMonth, salaryPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getUserType())){
            queryWrapper.lambda().eq(Salary::getUserType,salaryPageParam.getUserType());
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(Salary::getIdCardNumber, AesUtil.encrypt(salaryPageParam.getIdCardNumber()));
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getUserName())) {
            queryWrapper.lambda().like(Salary::getUserName, AesUtil.encrypt(salaryPageParam.getUserName()));
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getFirstOrgName())) {
            queryWrapper.lambda().like(Salary::getFirstOrgName, AesUtil.encrypt(salaryPageParam.getFirstOrgName()));
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getOrgName())) {
            queryWrapper.lambda().like(Salary::getOrgName, AesUtil.encrypt(salaryPageParam.getOrgName()));
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getPosition())) {
            queryWrapper.lambda().like(Salary::getPosition, AesUtil.encrypt(salaryPageParam.getPosition()));
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getIsSlow())){
            if(YesOrNoEnum.YES.getValue().equals(salaryPageParam.getIsSlow())) {
                queryWrapper.lambda().eq(Salary::getIsSlow, salaryPageParam.getIsSlow());
            }else{
                queryWrapper.lambda().and(q->q.isNull(Salary::getIsSlow).or().ne(Salary::getIsSlow,YesOrNoEnum.YES.getValue()));
            }
        }
        if(ObjectUtil.isNotEmpty(salaryPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(Salary::getIdCardNumber,AesUtil.encrypt(salaryPageParam.getSearchKey()))
                    .or().like(Salary::getUserName,AesUtil.encrypt(salaryPageParam.getSearchKey()))
                    .or().like(Salary::getOrgName,AesUtil.encrypt(salaryPageParam.getSearchKey())));
        }
        if(ObjectUtil.isAllNotEmpty(salaryPageParam.getSortField(), salaryPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Salary::getYearMonth);
        }
        Page<Salary> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<Salary> records = AesUtil.decryptGZList(result.getRecords());
        records.forEach(x->x.setSortIndex(UserTypeEnum.ZB.getValue().equals(x.getUserType()) ? 1 : (UserTypeEnum.FGS.getValue().equals(x.getUserType()) ? 2 : 3)));
        records.sort(Comparator.comparing(Salary::getSortIndex).thenComparing(Salary::getOrgName,Comparator.nullsLast(Comparator.naturalOrder())));
        result.setRecords(records);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryIdParam> salaryIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(salaryIdParamList, SalaryIdParam::getId));
    }

    @Override
    public Salary detail(SalaryIdParam salaryIdParam) {
        Salary salary = this.queryEntity(salaryIdParam.getId());
        return AesUtil.decryptGZ(salary);
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
    public List<Salary> empSalary(SearchParam param) {
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(param.getYearMonth())) {
            queryWrapper.lambda().eq(Salary::getYearMonth,param.getYearMonth());
        }
        queryWrapper.lambda().eq(Salary::getUserId, AesUtil.encrypt(StpLoginUserUtil.getLoginUser().getId()));
        queryWrapper.lambda().and(q->q.ne(Salary::getIsSlow, YesOrNoEnum.YES.getValue()).or().isNull(Salary::getIsSlow));
        queryWrapper.lambda().orderByDesc(Salary::getYearMonth);
        List<Salary> result = this.list(queryWrapper);
        return AesUtil.decryptGZList(result);
    }

    @Override
    public void deleteByMonth(SearchParam param) {
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Salary::getYearMonth,param.getYearMonth());
        this.remove(queryWrapper);
    }

    @Override
    public void updateField(UpdateFieldParam fieldParam) {
        Salary salary = this.queryEntity(fieldParam.getId());
        if("isSlow".equals(fieldParam.getFieldName())){
            salary.setIsSlow(fieldParam.getFieldValue());
        }
        this.updateById(salary);
    }
}
