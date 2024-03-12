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
import vip.xiaonuo.sys.modular.hr.salary.vo.SumAttendData;
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

    /**
     * BigDecimal value = 5000
     */
    private static final BigDecimal BIG_DECIMAL_5000 = new BigDecimal("5000");

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
        if(ObjectUtil.isNotEmpty(salaryPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(Salary::getIdCardNumber,salaryPageParam.getSearchKey())
                    .or().like(Salary::getUserName,salaryPageParam.getSearchKey())
                    .or().like(Salary::getOrgName,salaryPageParam.getSearchKey()));
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
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
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
        queryWrapper.orderByAsc("a.ID_CARD_NUMBER");
        List<SalaryVo> salaryVoList = salaryMapper.getPrePageList(queryWrapper);
        //自动生成工资数据
        return computePreGZ(yearMonth,salaryVoList);
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

    @Override
    public void preSalarySure(PayParam param) {
        //查询条件
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("a.`YEAR_MONTH`",param.getYearMonth());
        queryWrapper.isNull("b.ID");
        queryWrapper.orderByAsc("a.ID_CARD_NUMBER");
        List<SalaryVo> salaryVoList = salaryMapper.getPrePageList(queryWrapper);
        //自动生成工资数据
        List<SalaryVo> sureData = computePreGZ(param.getYearMonth(),salaryVoList);
        List<Salary> saveSureData = BeanUtil.copyToList(sureData,Salary.class);
        saveSureData.forEach(x->{
            x.setId(null);
            x.setType(SalaryTypeEnum.EMP_SALARY.getValue());
        });
        this.saveBatch(saveSureData);
    }


    /**
     * 根据 各种数据 ，计算工资（预览数据）
     * @param yearMonth 所属年月
     * @param salaryVoList 工资处理基础数据
     * @return
     */
    public List<SalaryVo> computePreGZ(String yearMonth,List<SalaryVo> salaryVoList){
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
        //获取上月累计应纳税所得额|累计税额等
        String previousYearMonth = ComputeUtil.getPreviousYM(yearMonth);
        List<Salary> previousSalaryData = this.list(new QueryWrapper<Salary>().lambda().eq(Salary::getType,SalaryTypeEnum.EMP_SALARY.getValue()).eq(Salary::getYearMonth,previousYearMonth));

        //1. 第一步，所有的 【工资】 数据copy
        List<SalaryVo> newSalaryVoList = BeanUtil.copyToList(salaryVoList,SalaryVo.class);
        List<SalaryVo> addSalaryVoList = new ArrayList<>();

        //2. 第二步，生成(匹配社保|公积金 -> 生成 1，2，3条记录)
        newSalaryVoList.forEach(item->{
            //copy一份原始数据
            SalaryVo tmpCopySalaryVo = BeanUtil.copyProperties(item, SalaryVo.class);
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
                        item.setPer(item.getYnPer().add(item.getYlPer()).add(item.getYlSale()).add(item.getSyPer()));
                    }else{
                        // 定义临时对象(工资)
                        SalaryVo tempSalaryVo = BeanUtil.copyProperties(tmpCopySalaryVo, SalaryVo.class);
                        tempSalaryVo.setCompany(ins.getUnitName());
                        tempSalaryVo.setDataType(DataTypeEnum.INS.getValue());
                        tempSalaryVo.setYnPer(ComputeUtil.fillZero(ins.getYnPer()));
                        tempSalaryVo.setYlPer(ComputeUtil.fillZero(ins.getYlPer()));
                        tempSalaryVo.setYlSale(ComputeUtil.fillZero(ins.getYlSale()));
                        tempSalaryVo.setSyPer(ComputeUtil.fillZero(ins.getSyPer()));
                        tempSalaryVo.setPer(tempSalaryVo.getYnPer().add(tempSalaryVo.getYlPer()).add(tempSalaryVo.getYlSale()).add(tempSalaryVo.getSyPer()));
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
                        //公积金里的 差额 ，记录到 税前补扣
                        item.setMinusAmount(ComputeUtil.fillZero(gjj.getAmount()));
                        item.setGjjDiffAmount(ComputeUtil.fillZero(gjj.getAmount()));
                    }else{
                        //公积金和社保匹配一下（当前员工）
                        AtomicReference<Boolean> isInsGjjFlag = new AtomicReference<>(false);
                        if(CollUtil.isNotEmpty(addInsSalaryVoList)) {
                            addInsSalaryVoList.forEach(objIns -> {
                                if(objIns.getCompany().equals(gjj.getUnitName())){
                                    isInsGjjFlag.set(true);
                                    objIns.setSysPer(ComputeUtil.fillZero(gjj.getSysPer()));
                                    //公积金里的 差额 ，记录到 税前补扣
                                    objIns.setMinusAmount(ComputeUtil.fillZero(gjj.getAmount()));
                                    item.setGjjDiffAmount(ComputeUtil.fillZero(gjj.getAmount()));
                                }
                            });
                        }
                        if(isInsGjjFlag.equals(false)){
                            // 定义临时对象(工资)
                            SalaryVo tempSalaryVo = BeanUtil.copyProperties(tmpCopySalaryVo, SalaryVo.class);
                            tempSalaryVo.setCompany(gjj.getUnitName());
                            tempSalaryVo.setDataType(DataTypeEnum.GJJ.getValue());
                            tempSalaryVo.setSysPer(ComputeUtil.fillZero(gjj.getSysPer()));
                            //公积金里的 差额 ，记录到 税前补扣
                            tempSalaryVo.setMinusAmount(ComputeUtil.fillZero(gjj.getAmount()));
                            item.setGjjDiffAmount(ComputeUtil.fillZero(gjj.getAmount()));
                            addGjjSalaryVoList.add(tempSalaryVo);
                        }
                    }
                });
            }
            addSalaryVoList.addAll(addInsSalaryVoList);
            addSalaryVoList.addAll(addGjjSalaryVoList);
        });
        newSalaryVoList.addAll(addSalaryVoList);

        //3. 第三步，数据计算
        List<SalaryVo> preSalaryList = ComputeUtil.initGZData(newSalaryVoList);
        preSalaryList.forEach(item->{
            //先处理各类特殊
            if(DataTypeEnum.SALARY.equals(item.getDataType())) {
                //实发绩效
                item.setRealMeritAmount(item.getMeritAmount().multiply(BigDecimal.valueOf(item.getMeritRate())));
                //注册补贴
                SumTypeData registerBT = subsidyData.stream().filter(x -> SalaryTypeEnum.REGISTER_SUBSIDY.getValue().equals(x.getType())
                        && item.getStandardId().equals(x.getStandardId())).findFirst().orElse(null);
                item.setRegisterAmount(ObjectUtil.isNotEmpty(registerBT) ? registerBT.getSumAmount() : BigDecimal.ZERO);
                //工龄工资
                SumTypeData seniorityGZ = payData.stream().filter(x -> SalaryTypeEnum.SENIORITY.getValue().equals(x.getType())
                        && item.getStandardId().equals(x.getStandardId())).findFirst().orElse(null);
                item.setWorkAgeAmount(ObjectUtil.isNotEmpty(seniorityGZ) ? seniorityGZ.getSumAmount() : BigDecimal.ZERO);
                //讲师津贴
                SumTypeData teacherBT = subsidyData.stream().filter(x -> SalaryTypeEnum.TEACHER_SUBSIDY.getValue().equals(x.getType())
                        && item.getStandardId().equals(x.getStandardId())).findFirst().orElse(null);
                item.setTeacherAmount(ObjectUtil.isNotEmpty(teacherBT) ? teacherBT.getSumAmount() : BigDecimal.ZERO);
                //值班补贴
                SumTypeData dutyBT = subsidyData.stream().filter(x -> SalaryTypeEnum.DUTY_SUBSIDY.getValue().equals(x.getType())
                        && item.getStandardId().equals(x.getStandardId())).findFirst().orElse(null);
                item.setDutyAmount(ObjectUtil.isNotEmpty(dutyBT) ? dutyBT.getSumAmount() : BigDecimal.ZERO);
                //考勤 ： 全勤奖|考勤扣款
                SalaryAttend attend = attendData.stream().filter(x -> item.getStandardId().equals(x.getStandardId())).findFirst().orElse(null);
                if (ObjectUtil.isNotEmpty(attend)) {
                    SumAttendData sumAttendData = ComputeUtil.computeAttendGZ(attend,item,insData,gjjData);
                    //全勤奖
                    item.setFullAmount(sumAttendData.getFullAmount());
                    item.setSickAmount(sumAttendData.getSickAmount());
                    item.setWorkAmount(sumAttendData.getWorkAmount());
                    item.setLateAmount(sumAttendData.getLateAmount());
                    item.setMinerAmount(sumAttendData.getMinerAmount());
                    item.setMaterAmount(sumAttendData.getMaterAmount());
                }
                //税前补发补扣
                SalaryAdd add = addData.stream().filter(x -> item.getStandardId().equals(x.getStandardId())).findFirst().orElse(null);
                item.setAddAmount(ObjectUtil.isNotEmpty(add) ? add.getAddAmount() : BigDecimal.ZERO);
                //由于公积金 差额 被记录在 税前补扣中
                BigDecimal minusAmount = ObjectUtil.isNotEmpty(add) ? add.getMinusAmount() : BigDecimal.ZERO;
                item.setMinusAmount(item.getMinusAmount().add(minusAmount));
                //其他税后补发补扣
                item.setLastAddAmount(ObjectUtil.isNotEmpty(add) ? add.getLastAddAmount() : BigDecimal.ZERO);
                item.setLastMinusAmount(ObjectUtil.isNotEmpty(add) ? add.getLastMinusAmount() : BigDecimal.ZERO);
                //员工福利扣税额
                SumTypeData empBonusGZ = payData.stream().filter(x -> SalaryTypeEnum.EMP_BONUS.getValue().equals(x.getType())
                        && item.getStandardId().equals(x.getStandardId())).findFirst().orElse(null);
                item.setEmpWelfareAmount(ObjectUtil.isNotEmpty(empBonusGZ) ? empBonusGZ.getSumAmount() : BigDecimal.ZERO);
                //外业补贴扣税额
                SumTypeData outerBT = subsidyData.stream().filter(x -> SalaryTypeEnum.OUTER_SUBSIDY.getValue().equals(x.getType())
                        && item.getStandardId().equals(x.getStandardId())).findFirst().orElse(null);
                item.setOuterAmount(ObjectUtil.isNotEmpty(outerBT) ? outerBT.getSumAmount() : BigDecimal.ZERO);
            }else if(DataTypeEnum.INS.getValue().equals(item.getDataType())){
                item.setBasicAmount(BigDecimal.ZERO);
                item.setPostAmount(item.getPer().add(item.getSysPer()));
                item.setMeritAmount(BigDecimal.ZERO);
                item.setMeritRate(0F);
            }else if(DataTypeEnum.GJJ.getValue().equals(item.getDataType())){
                item.setBasicAmount(BigDecimal.ZERO);
                item.setPostAmount(item.getPer().add(item.getSysPer()));
                item.setMeritAmount(BigDecimal.ZERO);
                item.setMeritRate(0F);
            }

            /**
             * 再计算公共部分 ： 应发工资|专项附加扣除|本月因纳税所得额|累计应纳税所得额|累计税额|已累计税额|本月应扣税额|实发工资
             */
            //应发工资
            item.setGrossPayAmount(item.getBasicAmount().add(item.getPostAmount()).add(item.getRealMeritAmount()).add(item.getRegisterAmount())
                    .add(item.getWorkAgeAmount()).add(item.getFullAmount()).add(item.getTeacherAmount()).add(item.getDutyAmount())
                    .subtract(item.getSickAmount()).subtract(item.getWorkAmount()).subtract(item.getLateAmount())
                    .subtract(item.getMinerAmount()).subtract(item.getMaterAmount()).add(item.getAddAmount()).subtract(item.getMinusAmount()));

            //【个人社保扣除|个人公积金扣款】 - 已生成 - 匹配 - 专项附加扣除
            SalaryTax tax = taxData.stream().filter(x -> item.getCompany().equals(x.getUnitName()) && item.getStandardId().equals(x.getStandardId())).findFirst().orElse(null);
            item.setTaxSy(ObjectUtil.isNotEmpty(tax) ? tax.getTaxSy() : BigDecimal.ZERO);
            item.setTaxZn(ObjectUtil.isNotEmpty(tax) ? tax.getTaxZn() : BigDecimal.ZERO);
            item.setTaxFd(ObjectUtil.isNotEmpty(tax) ? tax.getTaxFd() : BigDecimal.ZERO);
            item.setTaxZf(ObjectUtil.isNotEmpty(tax) ? tax.getTaxZf() : BigDecimal.ZERO);
            item.setTaxJy(ObjectUtil.isNotEmpty(tax) ? tax.getTaxJy() : BigDecimal.ZERO);
            item.setTaxZh(ObjectUtil.isNotEmpty(tax) ? tax.getTaxZh() : BigDecimal.ZERO);

            //本月因纳税所得额
            item.setTaxIncomeAmount(item.getGrossPayAmount().subtract(item.getSysPer()).subtract(item.getPer())
                    .subtract(item.getTaxSy()).subtract(item.getTaxZn()).subtract(item.getTaxFd()).subtract(item.getTaxZf()).subtract(item.getTaxJy()).subtract(item.getTaxZh())
                    .add(item.getEmpWelfareAmount()).add(item.getOuterAmount()).subtract(BIG_DECIMAL_5000));

            //累计应纳税所得额(上月累计应纳税所得额【12月为0】 + 本月应纳税所得额 )
            Salary tempPreviousSalary = previousSalaryData.stream().filter(x->item.getCompany().equals(x.getCompany())
                    && item.getIdCardNumber().equals(x.getIdCardNumber())).findFirst().orElse(null);
            BigDecimal previousLjTaxIncomeAmount = BigDecimal.ZERO;
            if(!yearMonth.endsWith("12") && ObjectUtil.isNotEmpty(tempPreviousSalary)){
                previousLjTaxIncomeAmount = tempPreviousSalary.getLjTaxIncomeAmount();
            }
            item.setLjTaxIncomeAmount(previousLjTaxIncomeAmount.add(item.getTaxIncomeAmount()));

            //累计税额
            item.setLjTaxAmount(ComputeUtil.getLjTax(item.getLjTaxIncomeAmount()));

            //已缴累计税额
            BigDecimal previousLjTaxAmount = BigDecimal.ZERO;
            BigDecimal previousMonthTaxAmount = BigDecimal.ZERO;
            if(!yearMonth.endsWith("12") && ObjectUtil.isNotEmpty(tempPreviousSalary)){
                previousLjTaxAmount = tempPreviousSalary.getLjTaxAmount();
                previousMonthTaxAmount = tempPreviousSalary.getMonthTaxAmount();
            }
            item.setHaveLjTaxAmount(previousLjTaxAmount.add(previousMonthTaxAmount));

            //本月应扣税额
            item.setMonthTaxAmount(item.getLjTaxAmount().subtract(item.getHaveLjTaxAmount()));

            //实发工资
            item.setRealPayAmount(item.getGrossPayAmount().subtract(item.getSysPer()).subtract(item.getPer()).add(item.getLastAddAmount()).subtract(item.getLastMinusAmount()).subtract(item.getMonthTaxAmount()));
        });
        return salaryVoList;
    }
}
