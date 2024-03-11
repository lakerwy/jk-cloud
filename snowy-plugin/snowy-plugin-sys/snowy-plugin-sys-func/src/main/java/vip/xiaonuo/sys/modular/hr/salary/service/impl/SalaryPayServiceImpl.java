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
import cn.hutool.core.date.DateUtil;
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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryPay;
import vip.xiaonuo.sys.modular.hr.salary.enums.SalaryTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryPayMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.*;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.PayParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryPayService;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryStandardService;
import vip.xiaonuo.sys.modular.hr.salary.util.ComputeUtil;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryPayVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalarySeniorityVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryStandardVo;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 工资系统-工龄工资|生日福利Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:34
 **/
@Service
public class SalaryPayServiceImpl extends ServiceImpl<SalaryPayMapper, SalaryPay> implements SalaryPayService {

    @Resource
    private SalaryPayMapper salaryPayMapper;

    @Resource
    private SalaryStandardService salaryStandardService;

    @Override
    public Page<SalaryPayVo> page(SalaryPayPageParam salaryPayPageParam) {
        QueryWrapper<SalaryPay> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(salaryPayPageParam.getType())) {
            queryWrapper.eq("a.TYPE", salaryPayPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(salaryPayPageParam.getItemType())){
            queryWrapper.eq("a.ITEM_TYPE",salaryPayPageParam.getItemType());
        }
        if(ObjectUtil.isNotEmpty(salaryPayPageParam.getYearMonth())) {
            queryWrapper.eq("b.`YEAR_MONTH`", salaryPayPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryPayPageParam.getIdCardNumber())) {
            queryWrapper.like("b.ID_CARD_NUMBER", salaryPayPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(salaryPayPageParam.getUserName())) {
            queryWrapper.like("b.USER_NAME", salaryPayPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(salaryPayPageParam.getFirstOrgName())) {
            queryWrapper.like("b.FIRST_ORG_NAME", salaryPayPageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryPayPageParam.getOrgName())) {
            queryWrapper.like("b.ORG_NAME", salaryPayPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(salaryPayPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("b.ID_CARD_NUMBER",salaryPayPageParam.getSearchKey())
                    .or().like("b.USER_NAME",salaryPayPageParam.getSearchKey())
                    .or().like("b.FIRST_ORG_NAME",salaryPayPageParam.getSearchKey())
                    .or().like("b.ORG_NAME",salaryPayPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(salaryPayPageParam.getSortField(), salaryPayPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryPayPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryPayPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryPayPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("b.`YEAR_MONTH`").orderByAsc("b.ID_CARD_NUMBER");
        }
        return salaryPayMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryPayAddParam salaryPayAddParam) {
        //同一年月，同一花名册Id，只存在一条记录(工龄工资|生日福利)
        if(SalaryTypeEnum.BIRTHDAY.getValue().equals(salaryPayAddParam.getItemType())
                || SalaryTypeEnum.SENIORITY.getValue().equals(salaryPayAddParam.getType())) {
            QueryWrapper<SalaryPay> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SalaryPay::getType,salaryPayAddParam.getType());
            queryWrapper.lambda().eq(SalaryPay::getStandardId,salaryPayAddParam.getStandardId());
            if(SalaryTypeEnum.BIRTHDAY.getValue().equals(salaryPayAddParam.getItemType())){
                queryWrapper.lambda().eq(SalaryPay::getItemType,salaryPayAddParam.getItemType());
            }
            List<SalaryPay> oldList = this.list(queryWrapper);
            if (CollUtil.isNotEmpty(oldList)) {
                throw new CommonException("当前员工本年月已存在记录");
            }
        }
        SalaryPay salaryPay = BeanUtil.toBean(salaryPayAddParam, SalaryPay.class);
        this.save(salaryPay);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryPayEditParam salaryPayEditParam) {
        //同一年月，同一员工，只存在一条记录(工龄工资|生日福利)
        if(SalaryTypeEnum.BIRTHDAY.getValue().equals(salaryPayEditParam.getItemType())
                || SalaryTypeEnum.SENIORITY.getValue().equals(salaryPayEditParam.getType())) {
            QueryWrapper<SalaryPay> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SalaryPay::getType,salaryPayEditParam.getType());
            queryWrapper.lambda().eq(SalaryPay::getStandardId,salaryPayEditParam.getStandardId());
            queryWrapper.lambda().ne(SalaryPay::getId,salaryPayEditParam.getId());
            if(SalaryTypeEnum.BIRTHDAY.getValue().equals(salaryPayEditParam.getItemType())){
                queryWrapper.lambda().eq(SalaryPay::getItemType,salaryPayEditParam.getItemType());
            }
            List<SalaryPay> oldList = this.list(queryWrapper);
            if (CollUtil.isNotEmpty(oldList)) {
                throw new CommonException("当前员工本年月已存在记录");
            }
        }
        SalaryPay salaryPay = this.queryEntity(salaryPayEditParam.getId());
        BeanUtil.copyProperties(salaryPayEditParam, salaryPay);
        this.updateById(salaryPay);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryPayIdParam> salaryPayIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(salaryPayIdParamList, SalaryPayIdParam::getId));
    }

    @Override
    public SalaryPayVo detail(SalaryPayIdParam salaryPayIdParam) {
        return salaryPayMapper.getDetailById(salaryPayIdParam.getId());
    }

    @Override
    public SalaryPay queryEntity(String id) {
        SalaryPay salaryPay = this.getById(id);
        if(ObjectUtil.isEmpty(salaryPay)) {
            throw new CommonException("工资系统-工龄工资|生日福利不存在，id值为：{}", id);
        }
        return salaryPay;
    }

    /**
     * 生成 指定 年月 的工龄工资
     * @param yearMonth
     */
    @Override
    public void computeSeniorityGZ(String yearMonth) {
        //获取当前年月  格式化当前日期的年月部分
        String payType = SalaryTypeEnum.SENIORITY.getValue();
        //条件：(框定计算工龄工资的人员范围),且当月无记录
        List<SalaryStandardVo> personnelList = salaryPayMapper.getSeniorityPersonnel(payType, yearMonth);
        if(CollUtil.isNotEmpty(personnelList)){
            List<SalaryPay> addList = new ArrayList<>();
            personnelList.forEach(item->{
                SalaryPay addParam = new SalaryPay();
                addParam.setType(payType);
                addParam.setStandardId(item.getId());
                SalarySeniorityVo salarySeniorityVo = ComputeUtil.seniorityGZ(item.getPositiveTime());
                addParam.setAmount(salarySeniorityVo.getAmount());
                addParam.setPositiveAge(salarySeniorityVo.getPositiveAge());
                addParam.setMemo("记录生成时间："+ DateUtil.now()+"，"+salarySeniorityVo.getMemo()+"，转正日期："+ DateUtil.formatDate(item.getPositiveTime()));
                addList.add(addParam);
            });
            this.saveBatch(addList);
        }
    }

    /**
     * 生成 指定 年月 的生日福利
     * @param yearMonth
     */
    @Override
    public void computeBirthdayGZ(String yearMonth) {
        //获取当前年月  格式化当前日期的年月部分
        String payType = SalaryTypeEnum.EMP_BONUS.getValue();
        String itemType = SalaryTypeEnum.BIRTHDAY.getValue();
        LocalDate tmpDate = LocalDate.parse(yearMonth, DateTimeFormatter.ofPattern("yyyyMM"));
        int month = tmpDate.getMonthValue();
        //条件：出生日期为当前月(框定计算生日福利的人员范围),且当月无记录
        List<SalaryStandardVo> personnelList = salaryPayMapper.getBirthdayPersonnel(payType,itemType, yearMonth, month);
        if(CollUtil.isNotEmpty(personnelList)){
            List<SalaryPay> addList = new ArrayList<>();
            personnelList.forEach(item->{
                SalaryPay addParam = new SalaryPay();
                addParam.setType(payType);
                addParam.setItemType(itemType);
                addParam.setStandardId(item.getId());
                addParam.setAmount(ComputeUtil.birthdayGZ(item.getBirthDay()));
                addParam.setMemo("记录生成时间："+DateUtil.now()+"，生日日期是："+DateUtil.formatDate(item.getBirthDay()));
                addList.add(addParam);
            });
            this.saveBatch(addList);
        }
    }

    @Override
    public void reSeniorityGZ(PayParam payParam) {
        //0. 获取年月 yyyyMM 涉及的 standardIds
        List<String> delIds = salaryPayMapper.getSeniorityDelIds(SalaryTypeEnum.SENIORITY.getValue(),payParam.getYearMonth());
        //1. 删除历史数据
        if(CollUtil.isNotEmpty(delIds)) {
            this.removeByIds(delIds);
        }
        //2. 重新生成数据
        computeSeniorityGZ(payParam.getYearMonth());
    }

    @Override
    public void reBirthdayGZ(PayParam payParam) {
        //0. 获取年月 yyyyMM 涉及的 standardIds
        List<String> delIds = salaryPayMapper.getBirthdayDelIds(SalaryTypeEnum.EMP_BONUS.getValue(), SalaryTypeEnum.BIRTHDAY.getValue(), payParam.getYearMonth());
        //1. 删除历史数据
        if(CollUtil.isNotEmpty(delIds)) {
            this.removeByIds(delIds);
        }
        //2. 重新生成数据
        computeBirthdayGZ(payParam.getYearMonth());
    }
}
