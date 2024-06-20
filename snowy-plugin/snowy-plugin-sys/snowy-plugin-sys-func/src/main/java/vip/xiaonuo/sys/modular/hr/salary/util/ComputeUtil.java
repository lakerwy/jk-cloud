package vip.xiaonuo.sys.modular.hr.salary.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryAttend;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryGjj;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryImportTemp;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryIns;
import vip.xiaonuo.sys.modular.hr.salary.enums.YesOrNoEnum;
import vip.xiaonuo.sys.modular.hr.salary.enums.SalaryTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.vo.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 工资系统 - 相关计算工具类
 */
public class ComputeUtil {

    //定义常量
    /**
     * BigDecimal value = 0
     */
    private static final BigDecimal BIG_DECIMAL_0 = BigDecimal.ZERO;
    /**
     * BigDecimal value = 0.01
     */
    private static final BigDecimal BIG_DECIMAL_001 = new BigDecimal("0.01");
    /**
     * BigDecimal value = 0.02
     */
    private static final BigDecimal BIG_DECIMAL_002 = new BigDecimal("0.02");
    /**
     * BigDecimal value = 0.03
     */
    private static final BigDecimal BIG_DECIMAL_003 = new BigDecimal("0.03");
    /**
     * BigDecimal value = 0.5
     */
    private static final BigDecimal BIG_DECIMAL_05 = new BigDecimal("0.5");
    /**
     * BigDecimal value = 1.2
     */
    private static final BigDecimal BIG_DECIMAL_12 = new BigDecimal("1.2");
    /**
     * BigDecimal value = 2
     */
    private static final BigDecimal BIG_DECIMAL_2 = new BigDecimal("2");
    /**
     * BigDecimal value = 4
     */
    private static final BigDecimal BIG_DECIMAL_4 = new BigDecimal("4");
    /**
     * 全勤奖 20
     */
    private static final BigDecimal FULL_AMOUNT = new BigDecimal("20");
    /**
     * BigDecimal value = 21.75 月工作日天数
     */
    private static final BigDecimal BIG_DECIMAL_2175 = new BigDecimal("21.75");
    /**
     * BigDecimal value = 25
     */
    private static final BigDecimal BIG_DECIMAL_25 = new BigDecimal("25");
    /**
     * BigDecimal value = 50
     */
    private static final BigDecimal BIG_DECIMAL_50 = new BigDecimal("50");
    /**
     * BigDecimal value = 200
     */
    private static final BigDecimal BIG_DECIMAL_200 = new BigDecimal("200");
    /**
     * BigDecimal value = 500
     */
    private static final BigDecimal BIG_DECIMAL_500 = new BigDecimal("500");
    /**
     * Float value = 2
     */
    private static final Float FLOAT_2 = 2.0F;
    /**
     * Int value = 2
     */
    private static final Integer INT_2 = 2;
    /**
     * Int value = 15
     */
    private static final Integer INT_15 = 15;



    /**
     * 根据 转正日期，计算yyyyMM的工龄工资(数据源获取的是转正日期在本月末之前)
     *
     * 从转正时间起算，每满一年增加50元工龄工资，500元封顶，工龄工资按月发放
     * 新增工龄在每月15日（含）前满工龄期的，当月增加50元工龄工资
     * 在16日(含）之后满工龄期的当月增加25元工龄工资，次月调整为50元
     *
     * @param currentYearMonth 当前年月
     * @param positiveTime 转正日期
     * @return
     */
    public static SalarySeniorityVo seniorityGZ(String currentYearMonth,Date positiveTime){
        SalarySeniorityVo salarySeniorityVo = new SalarySeniorityVo();
        //工龄工资
        BigDecimal seniorityPay = BIG_DECIMAL_0;
        // 转正日期
        LocalDate positiveDate = LocalDate.parse(DateUtil.formatDate(positiveTime));

        // 创建格式器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        // 当前年月2024-01
        YearMonth currentYM = YearMonth.parse(currentYearMonth,formatter);
        // 获取当前月份15号
        LocalDate fifteenthOfMonth = currentYM.atDay(INT_15);
        // 获取当前月份的最后一天
        LocalDate lastDayOfMonth = currentYM.atEndOfMonth();

        //计算时间差（转正日期|本月15号 与 转正日期|本月最后一天 计算）
        Period fifteenthPeriod = Period.between(positiveDate,fifteenthOfMonth);
        Period lastPeriod = Period.between(positiveDate,lastDayOfMonth);

        /**
         * 转正日期 在 fifteenthOfMonth 前才计算工龄工资【转正日期在当月，且在15号后，不用计算，为0】
         */
        if(fifteenthOfMonth.isAfter(positiveDate)) {
            // 本月15号来看，工龄满几年
            int yearsOfFifteenthPeriod = fifteenthPeriod.getYears();
            // 本月最后一天来看，工龄满几年
            int yearsOfLastPeriod = lastPeriod.getYears();
            // 计算满整年的情况(15号前满一年)
            seniorityPay = seniorityPay.add(BIG_DECIMAL_50.multiply(BigDecimal.valueOf(yearsOfFifteenthPeriod)));
            //两者年不相等，说明是在15号后满一年的，加25
            if (yearsOfLastPeriod != yearsOfFifteenthPeriod) {
                seniorityPay = seniorityPay.add(BIG_DECIMAL_25);
            }
            // 考虑到500元封顶
            if (seniorityPay.compareTo(BIG_DECIMAL_500) > 0) {
                seniorityPay = BIG_DECIMAL_500;
            }
        }
        salarySeniorityVo.setAmount(seniorityPay);
        salarySeniorityVo.setPositiveAge((lastPeriod.getYears() + "年" + lastPeriod.getMonths() + "月" + lastPeriod.getDays() + "日").replaceAll("-",""));
        salarySeniorityVo.setMemo("工龄：转正日期至本月末");
        return salarySeniorityVo;
    }


    /**
     * 根据 生日日期，计算生日福利(数据源获取的是生日为当前月，转正日期在本月末之前)
     *
     * 转正员工享有，目前标准为200元/人
     *
     * 1、当月生日，如还在试用期，则没有生日福利；
     * 2、当月生日，当月转正的情形，转正日期在生日日期之前，则有生日福利，转正日期在生日日期之后，则没有。
     *
     * @param currentYearMonth 当前年月
     * @param positiveTime 转正日期
     * @param birthday 生日日期
     * @return
     */
    public static BigDecimal birthdayGZ(String currentYearMonth,Date positiveTime,Date birthday) {
        // 生日福利
        BigDecimal birthDayPay = BIG_DECIMAL_200;
        // 转正日期是当前年月yyyyMM,则判断 转正日期的月日和生日月日
        String positiveYM = DateUtil.format(positiveTime,DateTimeFormatter.ofPattern("yyyyMM"));
        if(currentYearMonth.equals(positiveYM) && DateUtil.dayOfMonth(positiveTime) > DateUtil.dayOfMonth(birthday)){
            birthDayPay = BIG_DECIMAL_0;
        }
        return birthDayPay;
    }


    /**
     * 根据考勤数据，计算考勤工资
     * @return SumAttendData
     */
    public static SumAttendData computeAttendGZ(SalaryAttend attend, SalaryPreviewVo salaryVo){
        //考勤扣款
        SumAttendData attendData = new SumAttendData();
        //全勤奖
        attendData.setFullAmount(YesOrNoEnum.YES.getValue().equals(attend.getIsFull()) ? FULL_AMOUNT : BIG_DECIMAL_0);
        /**
         * 病假
         * 一个月以内的病假扣款=月工资标准/21.75*病假天数*50%
         * 一个月以上病假扣款=（月工资标准-基本工资）/21.75*病假天数
         */
        BigDecimal realUnMonthSickAmount = salaryVo.getStandardAmount()
                .multiply(BigDecimal.valueOf(attend.getRealUnMonthSickDay()))
                .multiply(BIG_DECIMAL_05)
                .divide(BIG_DECIMAL_2175,6,RoundingMode.HALF_UP);
        BigDecimal realMonthSickAmount = (salaryVo.getStandardAmount()
                .subtract(salaryVo.getBasicAmount()))
                .multiply(BigDecimal.valueOf(attend.getRealMonthSickDay()))
                .divide(BIG_DECIMAL_2175,6,RoundingMode.HALF_UP);
        attendData.setSickAmount(realMonthSickAmount.add(realUnMonthSickAmount));
        /**
         * 事假
         * 2天以内的事假扣款=月工资标准/21.75*事假天数
         * 超过2天的事假扣款=月工资标准/21.75*事假天数*2+月工资标准/21.75*（事假天数-2）*1.2
         * 未打卡按事假扣款=月工资标准/21.75*（实扣上班+实扣下班未打卡次数）/2
         * 扣款=事假扣款+未打卡事假扣款
         *
         */
        BigDecimal workAmount = BIG_DECIMAL_0;
        if(attend.getWorkDay()<=FLOAT_2){
            workAmount = salaryVo.getStandardAmount()
                    .multiply(BigDecimal.valueOf(attend.getWorkDay()))
                    .divide(BIG_DECIMAL_2175,6,RoundingMode.HALF_UP);
        }else{
            workAmount = salaryVo.getStandardAmount()
                    .multiply(BIG_DECIMAL_2)
                    .divide(BIG_DECIMAL_2175,6,RoundingMode.HALF_UP)
                    .add(
                         salaryVo.getStandardAmount()
                                 .multiply(BigDecimal.valueOf(attend.getWorkDay() - FLOAT_2))
                                 .multiply(BIG_DECIMAL_12)
                                 .divide(BIG_DECIMAL_2175,6,RoundingMode.HALF_UP));
        }
        BigDecimal noWorkAmount = salaryVo.getStandardAmount()
                .multiply(BigDecimal.valueOf((attend.getRealUpMissNum()+attend.getRealDownMissNum())/FLOAT_2))
                .divide(BIG_DECIMAL_2175,6,RoundingMode.HALF_UP);
        BigDecimal tmpTotalWorkAmount = workAmount.add(noWorkAmount);
        BigDecimal totalWorkAmount = tmpTotalWorkAmount.compareTo(salaryVo.getStandardAmount()) > 0 ? salaryVo.getStandardAmount() : tmpTotalWorkAmount;
        attendData.setWorkAmount(totalWorkAmount);
        /**
         * 迟到
         * A类迟到超2次：
         * 扣款=月工资标准*1%*（A类迟到次数-2）
         *
         * B类迟到：
         * 扣款=月工资标准*2%*B类迟到次数
         *
         * C类迟到：
         * 扣款1=月工资标准*3%*C类迟到次数
         * 扣款2=月工资标准/21.75*0.5*C类迟到次数
         * 扣款1和扣款2比较取最小值
         *
         * 迟到扣款=A类迟到扣款+B类迟到扣款+C类迟到扣款
         */
        BigDecimal lateAmountA = BIG_DECIMAL_0;
        if(attend.getLateNumA() > INT_2){
            lateAmountA = salaryVo.getStandardAmount()
                    .multiply(BIG_DECIMAL_001)
                    .multiply(BigDecimal.valueOf(attend.getLateNumA() - FLOAT_2));
        }
        BigDecimal lateAmountB = salaryVo.getStandardAmount()
                .multiply(BIG_DECIMAL_002)
                .multiply(BigDecimal.valueOf(attend.getLateNumB()));
        BigDecimal lateAmountC1 = salaryVo.getStandardAmount()
                .multiply(BIG_DECIMAL_003)
                .multiply(BigDecimal.valueOf(attend.getLateNumC()));
        BigDecimal lateAmountC2 = salaryVo.getStandardAmount()
                .multiply(BIG_DECIMAL_05)
                .multiply(BigDecimal.valueOf(attend.getLateNumC()))
                .divide(BIG_DECIMAL_2175,6,RoundingMode.HALF_UP);
        BigDecimal lateAmountC = lateAmountC1.compareTo(lateAmountC2) <=0 ? lateAmountC1 : lateAmountC2;
        attendData.setLateAmount(lateAmountA.add(lateAmountB).add(lateAmountC));
        /**
         * 旷工：
         * 扣款=月工资标准/21.75*旷工天数*4
         */
        BigDecimal minerAmount = salaryVo.getStandardAmount()
                .multiply(BigDecimal.valueOf(attend.getMinerDay()))
                .multiply(BIG_DECIMAL_4)
                .divide(BIG_DECIMAL_2175,6,RoundingMode.HALF_UP);
        attendData.setMinerAmount(minerAmount);
        /**
         * 产假
         * 产假扣款=月工资标准/21.75*实扣产假天数
         *
         * 特别说明：不用再区分全月和非全月
         */
        BigDecimal materAmount = salaryVo.getStandardAmount()
                    .multiply(BigDecimal.valueOf(attend.getRealMaterDay()))
                    .divide(BIG_DECIMAL_2175,6,RoundingMode.HALF_UP);
        attendData.setMaterAmount(materAmount);
        /**
         * 扣款=月工资标准-月工资标准/21.75*应计薪天数）
         *
         * 说明：
         * 1、工资表新增当月入离职未满勤扣款。
         * 2、应计薪天数在考勤报表中有数据
         * 3、考勤报表中应出勤字段改为应计薪天数
         */
        BigDecimal inOutAmount = salaryVo.getStandardAmount()
                .subtract(
                        salaryVo.getStandardAmount()
                                .multiply(BigDecimal.valueOf(attend.getRequiredDay()))
                                .divide(BIG_DECIMAL_2175,6,RoundingMode.HALF_UP)
                );
        attendData.setInOutAmount(inOutAmount);

        /**
         * 实习生工资：基本工资*实际出勤天数
         */
        BigDecimal tryAmount = BIG_DECIMAL_0;
        if(YesOrNoEnum.YES.getValue().equals(salaryVo.getIsTry())){
            tryAmount = salaryVo.getBasicAmount().multiply(BigDecimal.valueOf(attend.getRealDay()));
        }
        attendData.setTryAmount(tryAmount);
        return attendData;
    }

    /**
     * 将Object转String
     * @param obj
     * @return
     */
    public static String objToString(Object obj){
        return ObjectUtil.isEmpty(obj) ? "" : String.valueOf(obj);
    }

    /**
     * 校验字符串 是否为日期格式
     * @param dateStr
     * @return
     */
    public static Boolean isValidDate(String dateStr){
        try {
            DateUtil.parseDate(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * BigDecimal  若为null,给默认值 0,否则格式化保留2位小数
     * @param data
     * @return
     */
    public static BigDecimal fillZero(BigDecimal data){
        return ObjectUtil.isNotEmpty(data) ? setScale(data,2) : BigDecimal.ZERO;
    }

    /**
     * BigDecimal 保留几位小数
     * @param data BigDecimal 数据
     * @param scaleNum 保留小数位
     * @return
     */
    public static BigDecimal setScale(BigDecimal data,int scaleNum){
        return data.setScale(scaleNum,RoundingMode.HALF_UP);
    }


    /**
     * 由于 一个人 可能 存在 多条 社保 公积金 数据，汇总统计
     *
     * company 不一致的 社保合计
     * company 不一致的 公积金合计 | 公积金差额
     *
     * @param company 工资发放公司
     * @param standardId 工资人员Id
     * @param insData 社保数据集合
     * @param gjjData 公积金数据集合
     * @return 个人社保小计|个人公积金小计|个人公积金差额小计
     */
    public static SumInsGjjVo getSumInsGjj(String company,String standardId,List<SalaryIns> insData, List<SalaryGjj> gjjData){
        //获取 个人社保小计-个人公积金-公积金差额
        SumInsGjjVo sumInsGjjVo = new SumInsGjjVo();
        List<SalaryIns> insList = insData.stream().filter(x->x.getStandardId().equals(standardId) && !x.getUnitName().equals(company)).collect(Collectors.toList());
        List<SalaryGjj> gjjList = gjjData.stream().filter(x->x.getStandardId().equals(standardId) && !x.getUnitName().equals(company)).collect(Collectors.toList());
        BigDecimal insPer = BIG_DECIMAL_0;
        BigDecimal gjjSysPer = BIG_DECIMAL_0;
        BigDecimal gjjCha = BIG_DECIMAL_0;
        for (SalaryIns item : insList) {
            insPer = insPer.add(item.getPer());
        }
        for (SalaryGjj item : gjjList){
            gjjSysPer = gjjSysPer.add(item.getSysPer());
            gjjCha = gjjCha.add(item.getAmount());
        }
        sumInsGjjVo.setStandardId(standardId);
        sumInsGjjVo.setCompany(company);
        sumInsGjjVo.setInsPerAmount(insPer);
        sumInsGjjVo.setGjjPerAmount(gjjSysPer);
        sumInsGjjVo.setGjjChaAmount(gjjCha);
        return sumInsGjjVo;
    }


    /**
     * 根据当前 年月 yyyyMM 获取上个月
     * @param currentYearMonth 当前年月 yyyyMM 202401
     * @return 202312
     */
    public static String getPreviousYM(String currentYearMonth){
        // 创建格式器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        // 当前年月2024-01
        YearMonth currentYM = YearMonth.parse(currentYearMonth,formatter);
        // 计算上个年月2023-12
        YearMonth previousYM = currentYM.minusMonths(1);
        String previousYearMonth = previousYM.format(formatter);
        return previousYearMonth;
    }

    /**
     * 根据yyyyMM获取当前年月最后一天
     *
     * @param currentYearMonth 当前年月 yyyyMM 202401
     * @return 2024-01-31
     */
    public static String getLastDayOfYM(String currentYearMonth){
        // 创建格式器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        // 当前年月2024-01
        YearMonth currentYM = YearMonth.parse(currentYearMonth,formatter);
        // 当月最后一天
        String lastDayOfYM = currentYM.atEndOfMonth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return lastDayOfYM;
    }


    /**
     * 累计预扣预缴应纳税所得额 税率 速算扣除数
     *
     * 全年应纳税所得额税率表一（综合所得适用）
     *
     * 1.年度不超过36000元的税率为：3%   速算扣除数：0
     *
     * 2.超过36000-144000元的部分税率为：10%   速算扣除数：2520
     *
     * 3.超过144000-300000元的部分税率为：20%   速算扣除数：16920
     *
     * 4.超过300000-420000元的部分税率为：25%    速算扣除数：31920
     *
     * 5.超过420000-660000元的部分税率为：30%     速算扣除数：52920
     *
     * 6.超过660000-960000元的部分税率为：35%     速算扣除数：85920
     *
     * 7.超过960000元的税率为：45%   速算扣除数：181920
     *
     * @return 累计税额，小于0，则取0
     */
    public static BigDecimal getLjTax(BigDecimal ljTaxIncomeAmount){
        List<BigDecimal> taxList = new ArrayList<>();
        taxList.add(BIG_DECIMAL_0);
        taxList.add(ljTaxIncomeAmount.multiply(BigDecimal.valueOf(0.03)));
        taxList.add(ljTaxIncomeAmount.multiply(BigDecimal.valueOf(0.1)).subtract(BigDecimal.valueOf(2520)));
        taxList.add(ljTaxIncomeAmount.multiply(BigDecimal.valueOf(0.2)).subtract(BigDecimal.valueOf(16920)));
        taxList.add(ljTaxIncomeAmount.multiply(BigDecimal.valueOf(0.25)).subtract(BigDecimal.valueOf(31920)));
        taxList.add(ljTaxIncomeAmount.multiply(BigDecimal.valueOf(0.3)).subtract(BigDecimal.valueOf(52920)));
        taxList.add(ljTaxIncomeAmount.multiply(BigDecimal.valueOf(0.35)).subtract(BigDecimal.valueOf(85920)));
        taxList.add(ljTaxIncomeAmount.multiply(BigDecimal.valueOf(0.45)).subtract(BigDecimal.valueOf(181920)));
        // 使用Optional.max()防止空列表异常
        Optional<BigDecimal> maxValue = taxList.stream().max(BigDecimal::compareTo);
        return setScale(maxValue.get(),2);
    }



    /**
     * doImport
     * 导入数据 - 同步至 正式库（处理默认值 0）
     * @param importTempData 导入确认时，无错误信息的数据
     * @return
     */
    public static List<SalaryImportTemp> initImportData(String type,List<SalaryImportTemp> importTempData){
        List<SalaryImportTemp> importData = new ArrayList<>();
        importTempData.forEach(item->{
            if(SalaryTypeEnum.STANDARD.getValue().equals(type)) {
                if (ObjectUtil.isEmpty(item.getHaveBirthdayPay())) {
                    item.setHaveBirthdayPay("是");
                }
                if (ObjectUtil.isEmpty(item.getHaveSeniorityPay())) {
                    item.setHaveSeniorityPay("是");
                }
                if (ObjectUtil.isEmpty(item.getIsTry())) {
                    item.setIsTry("否");
                }
                if(ObjectUtil.isEmpty(item.getIsSpec())){
                    item.setIsSpec("否");
                }
            }
            if(SalaryTypeEnum.ATTEND.getValue().equals(type)){
                if(ObjectUtil.isEmpty(item.getIsFull())){
                    item.setIsFull("是");
                }
            }
            importData.add(DefaultValueUtil.defaultValues(item));
        });
        return importData;
    }


    /**
     * 预览工资数据 - 默认 0
     * @param salaryList
     */
    public static List<SalaryPreviewVo> initGZData(List<SalaryPreviewVo> salaryList){
        List<SalaryPreviewVo> newSalaryList = new ArrayList<>();
        salaryList.forEach(item->{
            newSalaryList.add(DefaultValueUtil.defaultValues(item));
        });
        return newSalaryList;
    }


    /**
     * 根据一个人，汇总所有公积金差额，（不区分缴纳公司）
     */
    public static BigDecimal getSumGjjCha(String standardId,List<SalaryGjj> gjjData){
        //获取 个人社保小计-个人公积金-公积金差额
        List<SalaryGjj> gjjList = gjjData.stream().filter(x->x.getStandardId().equals(standardId)).collect(Collectors.toList());
        BigDecimal gjjCha = BIG_DECIMAL_0;
        for (SalaryGjj item : gjjList){
            gjjCha = gjjCha.add(item.getAmount());
        }
        return gjjCha;
    }


    /**
     * 根据身份证号，获取出生日期
     * @param idCardNumber
     * @return
     */
    public static String getBirthDayFromIDCard(String idCardNumber) {
        String birthDateStr = "";
        if(ObjectUtil.isEmpty(idCardNumber)) return birthDateStr;
        // 确保身份证号码长度符合第二代身份证标准（18位）
        if (idCardNumber.length() == 18) {
            // 截取第7位到第14位
            birthDateStr = idCardNumber.substring(6, 14);
        }else if(idCardNumber.length() == 15) {
            //第7位到第12位表示出生年月日,拼接
            birthDateStr = "19"+idCardNumber.substring(6, 12);
        }else {
            return birthDateStr;
        }
        // 将截取的字符串转换为日期格式
        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (Exception e) {
            // 如果解析失败，可能是身份证号码中的日期部分不合法
            return "";
        }
        // 返回标准日期格式的字符串，例如：1990-01-01
        return birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 根据 工资年月 入职日期 离职日期 计算月度发薪人数（<=1）
     * 月度发薪人数=∑当月在职天数/当月自然日天数
     * 月度发薪人次 = 当月在职天数/当月自然日天数
     *
     * @param yearMonth
     * @param hireDate
     * @param leaveDate
     * @return
     */
    public static double getPayNum(String yearMonth,Date hireDate,Date leaveDate){
        //入职日期|离职日期  日期转换
        LocalDate tmpHireDate = null;
        LocalDate tmpLeaveDate = null;
        if(ObjectUtil.isNotEmpty(hireDate)) {
            tmpHireDate = LocalDate.parse(DateUtil.formatDate(hireDate));
        }
        if(ObjectUtil.isNotEmpty(leaveDate)) {
            tmpLeaveDate = LocalDate.parse(DateUtil.formatDate(leaveDate));
        }

        //获取yyyyMM月有多少自然日
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        YearMonth currentYM = YearMonth.parse(yearMonth, formatter);  // 解析字符串为YearMonth对象
        int daysInMonth = currentYM.lengthOfMonth();  // 获取该月的天数

        //计算在职天数
        int daysInWork = 0;
        if(ObjectUtil.isEmpty(tmpHireDate) || (ObjectUtil.isNotEmpty(tmpLeaveDate) && tmpHireDate.isAfter(tmpLeaveDate))){
            daysInWork = 0;
        }
        LocalDate startOfMonth = currentYM.atDay(1);  // 本月第一天
        LocalDate endOfMonth = currentYM.atEndOfMonth();  // 本月最后一天
        // 计算该员工在目标月的实际在职起始和结束日期
        LocalDate actualStart = tmpHireDate.isBefore(startOfMonth) ? startOfMonth : tmpHireDate;
        LocalDate actualEnd = (ObjectUtil.isNotEmpty(tmpLeaveDate) && tmpLeaveDate.isBefore(endOfMonth)) ? tmpLeaveDate : endOfMonth;
        // 确保实际在职结束日期不早于起始日期
        if(actualStart.isAfter(actualEnd)){
            daysInWork = 0;
        }
        // 计算并返回实际在职天数
        daysInWork = (int) ChronoUnit.DAYS.between(actualStart, actualEnd) + 1; // 加1是因为两端日期都包括在内

        return (double) daysInWork / daysInMonth;
    }
}
