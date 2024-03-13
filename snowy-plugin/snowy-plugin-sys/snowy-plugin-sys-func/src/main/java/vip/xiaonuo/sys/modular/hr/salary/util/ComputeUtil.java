package vip.xiaonuo.sys.modular.hr.salary.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryAttend;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryGjj;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryImportTemp;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryIns;
import vip.xiaonuo.sys.modular.hr.salary.enums.FullStatusEnum;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalarySeniorityVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SumAttendData;
import vip.xiaonuo.sys.modular.hr.salary.vo.SumInsGjjVo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
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
    private static final Float FLOAT_2 = 2F;
    /**
     * Int value = 2
     */
    private static final Integer INT_2 = 2;
    /**
     * Int value = 15
     */
    private static final Integer INT_15 = 15;



    /**
     * 根据 转正日期，计算工龄工资
     *
     * 从转正时间起算，每满一年增加50元工龄工资，500元封顶，工龄工资按月发放
     * 新增工龄在每月15日（含）前满工龄期的，当月增加50元工龄工资
     * 在16日(含）之后满工龄期的当月增加25元工龄工资，次月调整为50元
     *
     * @param positiveTime 转正日期
     * @return
     */
    public static SalarySeniorityVo seniorityGZ(Date positiveTime){
        SalarySeniorityVo salarySeniorityVo = new SalarySeniorityVo();
        //工龄工资
        BigDecimal seniorityPay = BIG_DECIMAL_0;

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 设置月份保持不变，日为15号
        LocalDate fifteenthOfMonth = currentDate.withDayOfMonth(INT_15);
        // 获取当前月份的最后一天
        LocalDate lastDayOfMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth());
        // 转正日期
        LocalDate positiveDate = LocalDate.parse(DateUtil.formatDate(positiveTime));

        //计算时间差（转正日期|本月15号 与 转正日期|本月最后一天 计算）
        Period fifteenthPeriod = Period.between(positiveDate,fifteenthOfMonth);
        Period lastPeriod = Period.between(positiveDate,lastDayOfMonth);
        // 本月15号来看，工龄满几年
        int yearsOfFifteenthPeriod = fifteenthPeriod.getYears();
        // 本月最后一天来看，工龄满几年
        int yearsOfLastPeriod = lastPeriod.getYears();

        // 计算满整年的情况(15号前满一年)
        seniorityPay = seniorityPay.add(BIG_DECIMAL_50.multiply(BigDecimal.valueOf(yearsOfFifteenthPeriod)));
        //两者年不相等，说明是在15号后满一年的，加25
        if(yearsOfLastPeriod != yearsOfFifteenthPeriod){
            seniorityPay = seniorityPay.add(BIG_DECIMAL_25);
        }
        // 考虑到500元封顶
        if(seniorityPay.compareTo(BIG_DECIMAL_500)>0){
            seniorityPay = BIG_DECIMAL_500;
        }

        salarySeniorityVo.setAmount(seniorityPay);
        salarySeniorityVo.setPositiveAge((lastPeriod.getYears() + "年" + lastPeriod.getMonths() + "月" + lastPeriod.getDays() + "日").replaceAll("-",""));
        salarySeniorityVo.setMemo("工龄：转正日期至本月末");
        return salarySeniorityVo;
    }


    /**
     * 根据 生日日期，计算生日福利(数据源获取的是生日为当前月)
     *
     * 转正员工享有，目前标准为200元/人，根据花名册自动生成月度生日福利表格，可导出
     * 生日福利在工资发放公司（实发工资非零的公司）的下月工资表中合并计税
     *
     * @param birthday
     * @return
     */
    public static BigDecimal birthdayGZ(Date birthday) {
        return BIG_DECIMAL_200;
    }


    /**
     * 根据考勤数据，计算考勤工资
     * @return SumAttendData
     */
    public static SumAttendData computeAttendGZ(SalaryAttend attend, SalaryVo salaryVo, List<SalaryIns> insData, List<SalaryGjj> gjjData){
        //考勤扣款
        SumAttendData attendData = new SumAttendData();
        //全勤奖
        attendData.setFullAmount(FullStatusEnum.YES.getValue().equals(attend.getIsFull()) ? FULL_AMOUNT : BIG_DECIMAL_0);
        /**
         * 病假
         * 一个月以内的病假扣款=月工资标准/21.75*病假天数*50%
         * 一个月以上病假扣款=（月工资标准-基本工资）/21.75*病假天数
         */
        BigDecimal realMonthSickAmount = salaryVo.getStandardAmount()
                .divide(BIG_DECIMAL_2175,INT_2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(attend.getRealMonthSickDay()))
                .multiply(BIG_DECIMAL_05);
        BigDecimal realUnMonthSickAmount = (salaryVo.getStandardAmount()
                .subtract(salaryVo.getBasicAmount()))
                .divide(BIG_DECIMAL_2175,INT_2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(attend.getRealUnMonthSickDay()));
        attendData.setSickAmount(realMonthSickAmount.add(realUnMonthSickAmount));
        /**
         * 事假
         * 2天以内的事假扣款=月工资标准/21.75*事假天数
         * 超过2天的事假扣款=月工资标准/21.75*事假天数*2+月工资标准/21.75*（事假天数-2）*1.2
         * 未打卡按事假扣款=月工资标准/21.75*（上班+下班未打卡次数）/2
         * 扣款=事假扣款+未打卡事假扣款
         *
         */
        BigDecimal workAmount = BIG_DECIMAL_0;
        if(attend.getWorkDay()<=FLOAT_2){
            workAmount = salaryVo.getStandardAmount()
                    .divide(BIG_DECIMAL_2175,INT_2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(attend.getWorkDay()));
        }else{
            workAmount = salaryVo.getStandardAmount()
                    .divide(BIG_DECIMAL_2175,INT_2, RoundingMode.HALF_UP)
                    .multiply(BIG_DECIMAL_2)
                    .add(
                         salaryVo.getStandardAmount()
                                 .divide(BIG_DECIMAL_2175,INT_2, RoundingMode.HALF_UP)
                                 .multiply(BigDecimal.valueOf(attend.getWorkDay() - FLOAT_2))
                                 .multiply(BIG_DECIMAL_12));
        }
        BigDecimal noWorkAmount = salaryVo.getStandardAmount()
                .divide(BIG_DECIMAL_2175,INT_2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf((attend.getUpMissNum()+attend.getDownMissNum())/2));
        attendData.setWorkAmount(workAmount.add(noWorkAmount));
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
                .divide(BIG_DECIMAL_2175,INT_2, RoundingMode.HALF_UP)
                .multiply(BIG_DECIMAL_05)
                .multiply(BigDecimal.valueOf(attend.getLateNumC()));
        BigDecimal lateAmountC = lateAmountC1.compareTo(lateAmountC2) <=0 ? lateAmountC1 : lateAmountC2;
        attendData.setLateAmount(lateAmountA.add(lateAmountB).add(lateAmountC));
        /**
         * 旷工：
         * 扣款=月工资标准/21.75*旷工天数*4
         */
        BigDecimal minerAmount = salaryVo.getStandardAmount()
                .divide(BIG_DECIMAL_2175,INT_2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(attend.getMinerDay()))
                .multiply(BIG_DECIMAL_4);
        attendData.setMinerAmount(minerAmount);
        /**
         * 产假
         * 全月产假扣款=月工资标准-个人社保小计-个人公积金-公积金差额(合计额)
         * 非全月产假=月工资标准/21.75*产假天数
         * 产假天数=应出勤天数，则为全月产假扣款，否则为非全月产假扣款
         */
        BigDecimal materAmount = BIG_DECIMAL_0;
        if(attend.getRealMaterDay() == attend.getRequiredDay()){
            //获取 个人社保小计-个人公积金-公积金差额
            SumInsGjjVo sumInsGjjVo = getSumInsGjj(attend.getStandardId(),insData,gjjData);
            materAmount = salaryVo.getStandardAmount()
                    .subtract(sumInsGjjVo.getInsPerAmount())
                    .subtract(sumInsGjjVo.getGjjPerAmount())
                    .subtract(sumInsGjjVo.getGjjChaAmount());
        }else{
            materAmount = salaryVo.getStandardAmount()
                    .divide(BIG_DECIMAL_2175,INT_2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(attend.getRealMaterDay()));
        }
        attendData.setMaterAmount(materAmount);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        try {
            LocalDate.parse(dateStr, formatter);
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
        return data.setScale(2,RoundingMode.HALF_UP);
    }


    /**
     * 由于 一个人 可能 存在 多条 社保 公积金 数据，汇总统计
     * @param standardId 工资人员Id
     * @param insData 社保数据集合
     * @param gjjData 公积金数据集合
     * @return 个人社保小计|个人公积金小计|个人公积金差额小计
     */
    public static SumInsGjjVo getSumInsGjj(String standardId,List<SalaryIns> insData, List<SalaryGjj> gjjData){
        //获取 个人社保小计-个人公积金-公积金差额
        SumInsGjjVo sumInsGjjVo = new SumInsGjjVo();
        List<SalaryIns> insList = insData.stream().filter(x->x.getStandardId().equals(standardId)).collect(Collectors.toList());
        List<SalaryGjj> gjjList = gjjData.stream().filter(x->x.getStandardId().equals(standardId)).collect(Collectors.toList());
        BigDecimal insPer = BIG_DECIMAL_0;
        BigDecimal gjjSysPer = BIG_DECIMAL_0;
        BigDecimal gjjCha = BIG_DECIMAL_0;
        insList.forEach(x-> insPer.add(x.getPer()));
        gjjList.forEach(x->{
            gjjSysPer.add(x.getSysPer());
            gjjCha.add(x.getAmount());
        });
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
    public static List<SalaryImportTemp> initImportData(List<SalaryImportTemp> importTempData){
        List<SalaryImportTemp> importData = new ArrayList<>();
        importTempData.forEach(item->{
            if(ObjectUtil.isEmpty(item.getHaveBirthdayPay())){
                item.setHaveBirthdayPay("是");
            }
            if(ObjectUtil.isEmpty(item.getHaveSeniorityPay())){
                item.setHaveSeniorityPay("是");
            }
            importData.add(DefaultValueUtil.defaultValues(item));
        });
        return importData;
    }


    /**
     * 预览工资数据 - 默认 0
     * @param salaryList
     */
    public static List<SalaryVo> initGZData(List<SalaryVo> salaryList){
        List<SalaryVo> newSalaryList = new ArrayList<>();
        salaryList.forEach(item->{
            newSalaryList.add(DefaultValueUtil.defaultValues(item));
        });
        return newSalaryList;
    }
}
