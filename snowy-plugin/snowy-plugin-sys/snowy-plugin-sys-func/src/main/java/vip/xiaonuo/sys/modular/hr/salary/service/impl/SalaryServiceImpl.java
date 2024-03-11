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
import vip.xiaonuo.sys.modular.hr.salary.entity.*;
import vip.xiaonuo.sys.modular.hr.salary.enums.DataTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.enums.SalaryTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.*;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.MySalaryParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.EmpSalaryParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.PayParam;
import vip.xiaonuo.sys.modular.hr.salary.service.*;
import vip.xiaonuo.sys.modular.hr.salary.util.ComputeUtil;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SumTypeData;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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

    @Resource
    private SalaryAttendService salaryAttendService;
    @Resource
    private SalarySubsidyRecordService salarySubsidyRecordService;
    @Resource
    private SalaryInsService salaryInsService;
    @Resource
    private SalaryGjjService salaryGjjService;
    @Resource
    private SalaryTaxService salaryTaxService;
    @Resource
    private SalaryPayService salaryPayService;

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
     * 月工资报表 - (未确认的)预览数据（自动生成）
     * @param param
     * @return
     */
    @Override
    public List<SalaryVo> prePageList(MySalaryParam param) {
        String yearMonth = param.getYearMonth();
        if(ObjectUtil.isEmpty(yearMonth)){
            throw new CommonException("所属年月必传");
        }
        //查询条件
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(param.getUserType())){
            queryWrapper.eq("a.USER_TYPE",param.getUserType());
        }
        if(ObjectUtil.isNotEmpty(param.getYearMonth())){
            queryWrapper.eq("a.`YEAR_MONTH`",param.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(param.getIdCardNumber())){
            queryWrapper.eq("a.ID_CARD_NUMBER",param.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(param.getUserName())){
            queryWrapper.eq("a.USER_NAME",param.getUserName());
        }
        if(ObjectUtil.isNotEmpty(param.getOrgName())){
            queryWrapper.eq("a.ORG_NAME",param.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(param.getSearchKey())){
            queryWrapper.and(q->q.like("a.ID_CARD_NUMBER",param.getSearchKey())
                    .or().like("a.USER_NAME",param.getSearchKey())
                    .or().like("a.ORG_NAME",param.getSearchKey()));
        }
        queryWrapper.isNull("b.ID");
        queryWrapper.orderByDesc("a.TYPE").orderByAsc("a.ID_CARD_NUMBER");
        List<SalaryVo> salaryVoList = salaryMapper.getPrePageList(queryWrapper);
        //自动生成工资数据
        return computePreGZ(salaryVoList);
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


    /**
     * 根据 各种数据 ，计算工资（预览数据）
     * @param salaryVoList
     * @return
     */
    public List<SalaryVo> computePreGZ(List<SalaryVo> salaryVoList){
        if(CollUtil.isEmpty(salaryVoList)){
            return salaryVoList;
        }
        //获取涉及到的员工 standardIds
        List<String> standardIds = salaryVoList.stream().map(x->x.getId()).collect(Collectors.toList());
        if(CollUtil.isEmpty(standardIds)){
            return salaryVoList;
        }
        //获取补贴数据(按type+standardId分组)
        List<SumTypeData> subsidyData = salaryMapper.getSubsidyData(standardIds);
        //获取福利数据(按type+standardId分组)
        List<SumTypeData> payData = salaryMapper.getPayData(standardIds);
        //获取考勤数据
        List<SalaryAttend> attendData = salaryMapper.getAttendData(standardIds);
        //获取社保数据
        List<SalaryIns> insData = salaryMapper.getInsData(standardIds);
        //获取公积金数据
        List<SalaryGjj> gjjData = salaryMapper.getGjjData(standardIds);
        //获取加计扣除数据
        List<SalaryTax> taxData = salaryMapper.getTaxData(standardIds);
        //获取税前税后补发补扣数据
        List<SalaryAdd> addData = salaryMapper.getAddData(standardIds);

        //1. 第一步，所有的 【工资】 数据copy
        List<SalaryVo> newSalaryVoList = BeanUtil.copyToList(salaryVoList,SalaryVo.class);
        List<SalaryVo> addSalaryVoList = new ArrayList<>();
        //2. 第二步，生成(匹配社保|公积金 -> 生成 1，2，3条记录)
        newSalaryVoList.forEach(item->{
            //社保 - (该员工所有社保数据)
            List<SalaryVo> addInsSalaryVoList = new ArrayList<>();
            List<SalaryIns> insList = insData.stream().filter(x->item.getStandardId().equals(x.getStandardId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(insList)) {
                insList.forEach(ins -> {
                    if (item.getCompany().equals(ins.getUnitName())) {
                        //直接更新
                        item.setYnPer(ComputeUtil.fillZero(ins.getYnPer()));
                        item.setYlPer(ComputeUtil.fillZero(ins.getYlPer()));
                        item.setYlSale(ComputeUtil.fillZero(ins.getYlSale()));
                        item.setSyPer(ComputeUtil.fillZero(ins.getSyPer()));
                        item.setPer(ComputeUtil.fillZero(ins.getPer()));
                    }else{
                        // 定义临时对象(工资)
                        SalaryVo tempSalaryVo = BeanUtil.copyProperties(item, SalaryVo.class);
                        tempSalaryVo.setCompany(ins.getUnitName());
                        tempSalaryVo.setDataType(DataTypeEnum.INS.getValue());
                        tempSalaryVo.setYnPer(ComputeUtil.fillZero(ins.getYnPer()));
                        tempSalaryVo.setYlPer(ComputeUtil.fillZero(ins.getYlPer()));
                        tempSalaryVo.setYlSale(ComputeUtil.fillZero(ins.getYlSale()));
                        tempSalaryVo.setSyPer(ComputeUtil.fillZero(ins.getSyPer()));
                        tempSalaryVo.setPer(ComputeUtil.fillZero(ins.getPer()));
                        addInsSalaryVoList.add(tempSalaryVo);
                    }
                });
            }
            //公积金 - (该员工所有公积金数据)
            List<SalaryVo> addGjjSalaryVoList = new ArrayList<>();
            List<SalaryGjj> gjjList = gjjData.stream().filter(x->item.getStandardId().equals(x.getStandardId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(gjjList)) {
                gjjList.forEach(gjj -> {
                    if (item.getCompany().equals(gjj.getUnitName())) {
                        //直接更新
                        item.setSysPer(ComputeUtil.fillZero(gjj.getSysPer()));
                    }else{
                        //公积金和社保匹配一下（当前员工）
                        AtomicReference<Boolean> isInsGjjFlag = new AtomicReference<>(false);
                        if(CollUtil.isNotEmpty(addInsSalaryVoList)) {
                            addInsSalaryVoList.forEach(objIns -> {
                                if(objIns.getCompany().equals(gjj.getUnitName())){
                                    isInsGjjFlag.set(true);
                                    objIns.setSysPer(ComputeUtil.fillZero(gjj.getSysPer()));
                                }
                            });
                        }
                        if(isInsGjjFlag.equals(false)){
                            // 定义临时对象(工资)
                            SalaryVo tempSalaryVo = BeanUtil.copyProperties(item, SalaryVo.class);
                            tempSalaryVo.setCompany(gjj.getUnitName());
                            tempSalaryVo.setDataType(DataTypeEnum.GJJ.getValue());
                            tempSalaryVo.setSysPer(ComputeUtil.fillZero(gjj.getSysPer()));
                            addGjjSalaryVoList.add(tempSalaryVo);
                        }
                    }
                });
            }
            addSalaryVoList.addAll(addInsSalaryVoList);
            addSalaryVoList.addAll(addGjjSalaryVoList);
        });
        //3. 第三步，生成（匹配公积金）
        salaryVoList.forEach(item->{
            item.setRealMeritAmount(item.getMeritAmount().multiply(BigDecimal.valueOf(item.getMeritRate())));
            //注册补贴
            SumTypeData registerBT = subsidyData.stream().filter(x-> SalaryTypeEnum.REGISTER_SUBSIDY.getValue().equals(x.getType()) && item.getId().equals(x.getStandardId())).findFirst().orElse(null);
            item.setRegisterAmount(ObjectUtil.isNotEmpty(registerBT) ? registerBT.getSumAmount() : BigDecimal.ZERO);
            //工龄工资
            SumTypeData seniorityGZ = payData.stream().filter(x-> SalaryTypeEnum.SENIORITY.getValue().equals(x.getType()) && item.getId().equals(x.getStandardId())).findFirst().orElse(null);
            item.setWorkAgeAmount(ObjectUtil.isNotEmpty(seniorityGZ) ? seniorityGZ.getSumAmount() : BigDecimal.ZERO);
            //讲师津贴
            SumTypeData teacherBT = subsidyData.stream().filter(x-> SalaryTypeEnum.TEACHER_SUBSIDY.getValue().equals(x.getType()) && item.getId().equals(x.getStandardId())).findFirst().orElse(null);
            item.setTeacherAmount(ObjectUtil.isNotEmpty(teacherBT) ? teacherBT.getSumAmount() : BigDecimal.ZERO);
            //值班补贴
            SumTypeData dutyBT = subsidyData.stream().filter(x-> SalaryTypeEnum.DUTY_SUBSIDY.getValue().equals(x.getType()) && item.getId().equals(x.getStandardId())).findFirst().orElse(null);
            item.setDutyAmount(ObjectUtil.isNotEmpty(dutyBT) ? dutyBT.getSumAmount() : BigDecimal.ZERO);
            //考勤 ： 全勤奖|考勤扣款
            SalaryAttend attend = attendData.stream().filter(x->item.getId().equals(x.getStandardId())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(attend)){
                item.setFullAmount(BigDecimal.ZERO);
                item.setSickAmount(BigDecimal.ZERO);
                item.setWorkAmount(BigDecimal.ZERO);
                item.setLateAmount(BigDecimal.ZERO);
                item.setMinerAmount(BigDecimal.ZERO);
                item.setMaterAmount(BigDecimal.ZERO);
            }
            //税前补发补扣
            SalaryAdd add = addData.stream().filter(x->item.getId().equals(x.getStandardId())).findFirst().orElse(null);
            item.setAddAmount(ObjectUtil.isNotEmpty(add) ? add.getAddAmount() : BigDecimal.ZERO);
            item.setMinusAmount(ObjectUtil.isNotEmpty(add) ? add.getMinusAmount() : BigDecimal.ZERO);
            //其他税后补发补扣
            item.setLastAddAmount(ObjectUtil.isNotEmpty(add) ? add.getLastAddAmount() : BigDecimal.ZERO);
            item.setLastMinusAmount(ObjectUtil.isNotEmpty(add) ? add.getLastMinusAmount() : BigDecimal.ZERO);
            //应发工资
            item.setGrossPayAmount(BigDecimal.ZERO);
            //个人社保扣除
            SalaryIns ins = insData.stream().filter(x->item.getCompany().equals(x.getUnitName()) && item.getId().equals(x.getStandardId())).findFirst().orElse(null);
            item.setYnPer(ObjectUtil.isNotEmpty(ins) ? ins.getYnPer() : BigDecimal.ZERO);
            item.setYlPer(ObjectUtil.isNotEmpty(ins) ? ins.getYlPer() : BigDecimal.ZERO);
            item.setYlSale(ObjectUtil.isNotEmpty(ins) ? ins.getYlSale() : BigDecimal.ZERO);
            item.setSyPer(ObjectUtil.isNotEmpty(ins) ? ins.getSyPer() : BigDecimal.ZERO);
            item.setPer(ObjectUtil.isNotEmpty(ins) ? ins.getPer() : BigDecimal.ZERO);
            //个人公积金扣款
            SalaryGjj gjj = gjjData.stream().filter(x->item.getCompany().equals(x.getUnitName()) && item.getId().equals(x.getStandardId())).findFirst().orElse(null);
            item.setSysPer(ObjectUtil.isNotEmpty(gjj) ? gjj.getSysPer() : BigDecimal.ZERO);
            //专项附加扣除
            SalaryTax tax = taxData.stream().filter(x->item.getCompany().equals(x.getUnitName()) && item.getId().equals(x.getStandardId())).findFirst().orElse(null);
            item.setTaxSy(ObjectUtil.isNotEmpty(tax) ? tax.getTaxSy() : BigDecimal.ZERO);
            item.setTaxZn(ObjectUtil.isNotEmpty(tax) ? tax.getTaxZn() : BigDecimal.ZERO);
            item.setTaxFd(ObjectUtil.isNotEmpty(tax) ? tax.getTaxFd() : BigDecimal.ZERO);
            item.setTaxZf(ObjectUtil.isNotEmpty(tax) ? tax.getTaxZf() : BigDecimal.ZERO);
            item.setTaxJy(ObjectUtil.isNotEmpty(tax) ? tax.getTaxJy() : BigDecimal.ZERO);
            item.setTaxZh(ObjectUtil.isNotEmpty(tax) ? tax.getTaxZh() : BigDecimal.ZERO);
            //员工福利扣税额
            SumTypeData empBonusGZ = payData.stream().filter(x-> SalaryTypeEnum.EMP_BONUS.getValue().equals(x.getType()) && item.getId().equals(x.getStandardId())).findFirst().orElse(null);
            item.setEmpWelfareAmount(ObjectUtil.isNotEmpty(empBonusGZ) ? empBonusGZ.getSumAmount() : BigDecimal.ZERO);
            //外业补贴扣税额
            SumTypeData outerBT = subsidyData.stream().filter(x-> SalaryTypeEnum.OUTER_SUBSIDY.getValue().equals(x.getType()) && item.getId().equals(x.getStandardId())).findFirst().orElse(null);
            item.setOuterAmount(ObjectUtil.isNotEmpty(outerBT) ? outerBT.getSumAmount() : BigDecimal.ZERO);
            //本月因纳税所得额
            item.setTaxIncomeAmount(BigDecimal.ZERO);
            //累计应纳税所得额
            item.setLjTaxIncomeAmount(BigDecimal.ZERO);
            //累计税额
            item.setLjTaxAmount(BigDecimal.ZERO);
            //已累计税额
            item.setHaveLjTaxAmount(BigDecimal.ZERO);
            //本月应扣税额
            item.setMonthTaxAmount(BigDecimal.ZERO);
            //实发工资
            item.setRealPayAmount(BigDecimal.ZERO);
        });
        return salaryVoList;
    }
}
