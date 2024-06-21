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
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryCost;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryGjj;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryIns;
import vip.xiaonuo.sys.modular.hr.salary.enums.CostTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.enums.DataTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryCostMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryCostAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryCostEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryCostIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryCostPageParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.ReportParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.SearchParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryCostService;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryGjjService;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryInsService;
import vip.xiaonuo.sys.modular.hr.salary.util.ComputeUtil;
import vip.xiaonuo.sys.modular.hr.salary.vo.CostReportVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryCostConfig;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryCostVo;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工资系统 - 成本划分 - 月度个人统计Service接口实现类
 *
 * @author yong.ye
 * @date  2024/06/19 15:06
 **/
@Service
public class SalaryCostServiceImpl extends ServiceImpl<SalaryCostMapper, SalaryCost> implements SalaryCostService {
    @Resource
    private SalaryInsService salaryInsService;
    @Resource
    private SalaryGjjService salaryGjjService;
    @Resource
    private SalaryCostMapper salaryCostMapper;
    @Override
    public Page<SalaryCost> page(SalaryCostPageParam salaryCostPageParam) {
        QueryWrapper<SalaryCost> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(salaryCostPageParam.getDeptId())) {
            queryWrapper.lambda().eq(SalaryCost::getDeptId, salaryCostPageParam.getDeptId());
        }
        if(ObjectUtil.isNotEmpty(salaryCostPageParam.getDept())) {
            queryWrapper.lambda().like(SalaryCost::getDept, salaryCostPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(salaryCostPageParam.getYearMonth())) {
            queryWrapper.lambda().eq(SalaryCost::getYearMonth, salaryCostPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryCostPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(SalaryCost::getIdCardNumber, salaryCostPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(salaryCostPageParam.getUserName())) {
            queryWrapper.lambda().like(SalaryCost::getUserName, salaryCostPageParam.getUserName());
        }
        if(ObjectUtil.isAllNotEmpty(salaryCostPageParam.getSortField(), salaryCostPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryCostPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryCostPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryCostPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SalaryCost::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryCostAddParam salaryCostAddParam) {
        SalaryCost salaryCost = BeanUtil.toBean(salaryCostAddParam, SalaryCost.class);
        this.save(salaryCost);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryCostEditParam salaryCostEditParam) {
        SalaryCost salaryCost = this.queryEntity(salaryCostEditParam.getId());
        BeanUtil.copyProperties(salaryCostEditParam, salaryCost);
        this.updateById(salaryCost);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryCostIdParam> salaryCostIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(salaryCostIdParamList, SalaryCostIdParam::getId));
    }

    @Override
    public SalaryCost detail(SalaryCostIdParam salaryCostIdParam) {
        return this.queryEntity(salaryCostIdParam.getId());
    }

    @Override
    public SalaryCost queryEntity(String id) {
        SalaryCost salaryCost = this.getById(id);
        if(ObjectUtil.isEmpty(salaryCost)) {
            throw new CommonException("工资系统 - 成本划分 - 月度个人统计不存在，id值为：{}", id);
        }
        return salaryCost;
    }



    @Override
    public void genPerCost(SearchParam param) {
        //0. 清理历史数据
        this.remove(new QueryWrapper<SalaryCost>().lambda().eq(SalaryCost::getYearMonth,param.getYearMonth()));

        //1. 准备数据（年度数据-准备） 工资 + 社保 + 公积金
        List<SalaryCostVo> costList = salaryCostMapper.getSalaryCost(param.getYearMonth());  //工资-相关
        if(CollUtil.isEmpty(costList)) return;
        List<SalaryIns> insList = new ArrayList<>();
        List<SalaryGjj> gjjList = new ArrayList<>();
        List<String> standardIds = costList.stream().map(x->x.getStandardId()).distinct().collect(Collectors.toList());
        if(CollUtil.isNotEmpty(standardIds)){
            insList = salaryInsService.list(new QueryWrapper<SalaryIns>().lambda().in(SalaryIns::getStandardId,standardIds));
            gjjList = salaryGjjService.list(new QueryWrapper<SalaryGjj>().lambda().in(SalaryGjj::getStandardId,standardIds));
        }

        //2. 生成成本划分数据
        List<SalaryCost> addSalaryCostList = new ArrayList<>();
        List<SalaryIns> finalInsList = insList;
        List<SalaryGjj> finalGjjList = gjjList;
        // 从工资表数据（基准） -> 出发
        costList.forEach(item->{
            //计算 月发薪人数 【月度发薪人数=∑当月在职天数/当月自然日天数】
            double payNum = ComputeUtil.getPayNum(param.getYearMonth(),item.getHireDate(),item.getLeaveDate());
            //解析 成本划分设置
            List<SalaryCostConfig> costConfigList = ObjectUtil.isNotEmpty(item.getCostJson()) ? JSONUtil.toList(item.getCostJson(), SalaryCostConfig.class) : new ArrayList<>();
            //人力成本划分归属部门-设置
            List<String> deptIds = costConfigList.stream().filter(x->ObjectUtil.isNotEmpty(x.getDeptId())).map(x->x.getDeptId()).distinct().collect(Collectors.toList());  //一条工资数据成本划分涉及到多少部门
            String tmpNote = "月度发薪人数："+payNum+" 成本划分设置："+item.getCostJson();
            /** 人力成本分析 - 汇总维度 **/
            deptIds.forEach(deptId->{
                SalaryCost tmpSalaryCost = new SalaryCost();
                tmpSalaryCost.setType(CostTypeEnum.REPORT_PER.getValue());
                tmpSalaryCost.setYearMonth(item.getYearMonth());
                tmpSalaryCost.setIdCardNumber(item.getIdCardNumber());
                tmpSalaryCost.setUserName(item.getUserName());
                tmpSalaryCost.setPosition(item.getPosition());
                tmpSalaryCost.setHireDate(item.getHireDate());
                tmpSalaryCost.setLeaveDate(item.getLeaveDate());
                tmpSalaryCost.setStandardId(item.getStandardId());
                tmpSalaryCost.setSalaryId(item.getId());
                SalaryCostConfig tmpDept = costConfigList.stream().filter(x->x.getDeptId().equals(deptId)).findFirst().orElse(null);
                tmpSalaryCost.setDeptId(deptId);
                tmpSalaryCost.setDept(tmpDept.getDept());
                tmpSalaryCost.setNote(tmpNote);
                //循环处理 各类配置
                costConfigList.forEach(config->{
                    //工资 【工资=应发工资+公积金差额-注册补贴】
                    if(CostTypeEnum.GZ.getValue().equals(config.getType()) && deptId.equals(config.getDeptId())){
                        if(DataTypeEnum.SALARY.getValue().equals(item.getDataType())) {
                            tmpSalaryCost.setPayNum(payNum * config.getRate());
                        }
                        BigDecimal tmp = ComputeUtil.setScale((item.getGrossPayAmount().add(item.getGjjDiffAmount()).subtract(item.getRegisterAmount())).multiply(BigDecimal.valueOf(config.getRate())),6);
                        tmpSalaryCost.setGz(tmp);
                    }
                    //社会保险费 【社保保险费=合计-单位（社保）】
                    if(CostTypeEnum.INS.getValue().equals(config.getType()) && deptId.equals(config.getDeptId())){
                        SalaryIns tmpIns = finalInsList.stream().filter(x -> x.getStandardId().equals(item.getStandardId()) && x.getUnitName().equals(item.getCompany())).findFirst().orElse(null);
                        if(ObjectUtil.isNotEmpty(tmpIns)) {
                            BigDecimal tmp = ComputeUtil.setScale(tmpIns.getUnit().multiply(BigDecimal.valueOf(config.getRate())),6);
                            tmpSalaryCost.setIns(tmp);
                        }
                    }
                    //住房公积金 【住房公积金=单位实际缴纳】
                    if(CostTypeEnum.GJJ.getValue().equals(config.getType()) && deptId.equals(config.getDeptId())){
                        SalaryGjj tmpGjj = finalGjjList.stream().filter(x -> x.getStandardId().equals(item.getStandardId()) && x.getUnitName().equals(item.getCompany())).findFirst().orElse(null);
                        if(ObjectUtil.isNotEmpty(tmpGjj)) {
                            BigDecimal tmp = ComputeUtil.setScale(tmpGjj.getUnit().multiply(BigDecimal.valueOf(config.getRate())),6);
                            tmpSalaryCost.setGjj(tmp);
                        }
                    }
                    //注册补贴费用 【注册补贴费用=注册补贴】
                    if(CostTypeEnum.REGISTER.getValue().equals(config.getType()) && deptId.equals(config.getDeptId())){
                        BigDecimal tmp = ComputeUtil.setScale(item.getRegisterAmount().multiply(BigDecimal.valueOf(config.getRate())),6);
                        tmpSalaryCost.setRegister(tmp);
                    }
                    //员工福利费 【员工福利费=工资表里的员工福利】
                    if(CostTypeEnum.WELFARE.getValue().equals(config.getType()) && deptId.equals(config.getDeptId())){
                        BigDecimal tmp = ComputeUtil.setScale(item.getEmpWelfareAmount().multiply(BigDecimal.valueOf(config.getRate())),6);
                        tmpSalaryCost.setWelfare(tmp);
                    }
                    //外业补贴  【外业补贴=外业补贴】
                    if(CostTypeEnum.OUTER.getValue().equals(config.getType()) && deptId.equals(config.getDeptId())){
                        BigDecimal tmp = ComputeUtil.setScale(item.getOuterAmount().multiply(BigDecimal.valueOf(config.getRate())),6);
                        tmpSalaryCost.setField(tmp);
                    }
                });
                addSalaryCostList.add(tmpSalaryCost);
            });

            /** 人力成本分析 - 社保维度 **/
            SalaryIns tmpIns = finalInsList.stream().filter(x -> x.getStandardId().equals(item.getStandardId()) && x.getUnitName().equals(item.getCompany())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(tmpIns)){
                List<SalaryCostConfig> insCostConfigList = costConfigList.stream().filter(x->CostTypeEnum.INS.getValue().equals(x.getType())).collect(Collectors.toList());
                List<String> insDeptIds = insCostConfigList.stream().filter(x->ObjectUtil.isNotEmpty(x.getDeptId())).map(x->x.getDeptId()).distinct().collect(Collectors.toList());
                insDeptIds.forEach(deptId->{
                    SalaryCost tmpSalaryCost = new SalaryCost();
                    tmpSalaryCost.setType(CostTypeEnum.REPORT_INS.getValue());
                    tmpSalaryCost.setYearMonth(item.getYearMonth());
                    tmpSalaryCost.setIdCardNumber(item.getIdCardNumber());
                    tmpSalaryCost.setUserName(item.getUserName());
                    tmpSalaryCost.setPosition(item.getPosition());
                    tmpSalaryCost.setHireDate(item.getHireDate());
                    tmpSalaryCost.setLeaveDate(item.getLeaveDate());
                    tmpSalaryCost.setStandardId(item.getStandardId());
                    tmpSalaryCost.setSalaryId(item.getId());
                    SalaryCostConfig tmpDept = insCostConfigList.stream().filter(x->x.getDeptId().equals(deptId)).findFirst().orElse(null);
                    tmpSalaryCost.setDeptId(deptId);
                    tmpSalaryCost.setDept(tmpDept.getDept());
                    tmpSalaryCost.setNote(tmpNote);
                    insCostConfigList.forEach(config->{
                        if(deptId.equals(config.getDeptId())){
                            tmpSalaryCost.setPayNum(payNum * config.getRate());
                            BigDecimal tmpPer = ComputeUtil.setScale(tmpIns.getPer().multiply(BigDecimal.valueOf(config.getRate())),6);
                            BigDecimal tmpUnit = ComputeUtil.setScale(tmpIns.getUnit().multiply(BigDecimal.valueOf(config.getRate())),6);
                            tmpSalaryCost.setIns(tmpPer);
                            tmpSalaryCost.setGjj(tmpUnit);
                        }
                    });
                    addSalaryCostList.add(tmpSalaryCost);
                });
            }

            /** 人力成本分析 - 公积金维度 **/
            SalaryGjj tmpGjj = finalGjjList.stream().filter(x -> x.getStandardId().equals(item.getStandardId()) && x.getUnitName().equals(item.getCompany())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(tmpGjj)){
                List<SalaryCostConfig> gjjCostConfigList = costConfigList.stream().filter(x->CostTypeEnum.GJJ.getValue().equals(x.getType())).collect(Collectors.toList());
                List<String> gjjDeptIds = gjjCostConfigList.stream().filter(x->ObjectUtil.isNotEmpty(x.getDeptId())).map(x->x.getDeptId()).distinct().collect(Collectors.toList());
                gjjDeptIds.forEach(deptId->{
                    SalaryCost tmpSalaryCost = new SalaryCost();
                    tmpSalaryCost.setType(CostTypeEnum.REPORT_GJJ.getValue());
                    tmpSalaryCost.setYearMonth(item.getYearMonth());
                    tmpSalaryCost.setIdCardNumber(item.getIdCardNumber());
                    tmpSalaryCost.setUserName(item.getUserName());
                    tmpSalaryCost.setPosition(item.getPosition());
                    tmpSalaryCost.setHireDate(item.getHireDate());
                    tmpSalaryCost.setLeaveDate(item.getLeaveDate());
                    tmpSalaryCost.setStandardId(item.getStandardId());
                    tmpSalaryCost.setSalaryId(item.getId());
                    SalaryCostConfig tmpDept = gjjCostConfigList.stream().filter(x->x.getDeptId().equals(deptId)).findFirst().orElse(null);
                    tmpSalaryCost.setDeptId(deptId);
                    tmpSalaryCost.setDept(tmpDept.getDept());
                    tmpSalaryCost.setNote(tmpNote);
                    gjjCostConfigList.forEach(config->{
                        if(deptId.equals(config.getDeptId())){
                            tmpSalaryCost.setPayNum(payNum * config.getRate());
                            BigDecimal tmpPer = ComputeUtil.setScale(tmpGjj.getPer().multiply(BigDecimal.valueOf(config.getRate())),6);
                            BigDecimal tmpUnit = ComputeUtil.setScale(tmpGjj.getUnit().multiply(BigDecimal.valueOf(config.getRate())),6);
                            tmpSalaryCost.setIns(tmpPer);
                            tmpSalaryCost.setGjj(tmpUnit);
                        }
                    });
                    addSalaryCostList.add(tmpSalaryCost);
                });
            }
        });
        this.saveBatch(addSalaryCostList);
    }

    @Override
    public List<CostReportVo> genCostReport(ReportParam reportParam) {
        List<CostReportVo> costReportList = salaryCostMapper.getCostReport(reportParam.getYearMonth(),reportParam.getReportType());
        costReportList.forEach(item->{
            item.setSubTotal(item.getGz().add(item.getIns()).add(item.getGjj()).add(item.getRegister()).add(item.getWelfare()).add(item.getField()));
        });
        return costReportList;
    }
}
