package vip.xiaonuo.sys.modular.hr.salary.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryAttend;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryImportTemp;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryStandard;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalarySeniorityVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryStandardVo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

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
     * BigDecimal value = 0.1
     */
    private static final BigDecimal BIG_DECIMAL_01 = new BigDecimal("0.1");
    /**
     * BigDecimal value = 0.15
     */
    private static final BigDecimal BIG_DECIMAL_015 = new BigDecimal("0.15");
    /**
     * BigDecimal value = 0.2
     */
    private static final BigDecimal BIG_DECIMAL_02 = new BigDecimal("0.2");
    /**
     * BigDecimal value = 0.5
     */
    private static final BigDecimal BIG_DECIMAL_05 = new BigDecimal("0.5");
    /**
     * BigDecimal value = 1.2
     */
    private static final BigDecimal BIG_DECIMAL_12 = new BigDecimal("1.2");
    /**
     * BigDecimal value = 4
     */
    private static final BigDecimal BIG_DECIMAL_4 = new BigDecimal("4");
    /**
     * BigDecimal value = 6
     */
    private static final BigDecimal BIG_DECIMAL_6 = new BigDecimal("6");
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
     * BigDecimal value = 4000
     */
    private static final BigDecimal BIG_DECIMAL_4000 = new BigDecimal("4000");
    /**
     * BigDecimal value = 10000
     */
    private static final BigDecimal BIG_DECIMAL_10000 = new BigDecimal("10000");
    /**
     * Float value = 0
     */
    private static final Float FLOAT_0 = 0F;
    /**
     * Float value = 1
     */
    private static final Float FLOAT_1 = 1.0F;
    /**
     * Float value = 1.5
     */
    private static final Float FLOAT_15 = 1.5F;
    /**
     * Int value = 0
     */
    private static final Integer INT_0 = 0;
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
     *
     * 计算规则：
     * ①病假工资=月标准工资/21.75*病假天数*0.5
     * ②事假天数小于等于1.5天，事假工资=月标准工资/21.75*事假天数
     * 事假天数大于1.5天，事假工资=月标准工资/21.75*1+月标准工资/21.75*（事假天数-1）*1.2
     * ③迟到工资=月标准工资*1%*A类迟到次数+月标准工资*2%*B类迟到次数+月标准工资/21.75*0.5*C类迟到次数
     * ④旷工1天，旷工工资=月标准工资/21.75*旷工天数*4
     * 连续旷工2天，旷工工资=月标准工资/21.75*旷工天数*6
     * ⑤全勤奖：当月无迟到、早退、脱岗、旷工、未打卡、病事假、调休等考勤异况，给予全勤奖20元
     *
     * @return
     */
    public static List<SalaryAttend> computeAttendGZ(List<SalaryAttend> attendList,List<SalaryStandardVo> standardList){
        attendList.forEach(item->{
            //获取当前人的月工资标准
            SalaryStandardVo standard = standardList.stream().filter(x->x.getId().equals(item.getStandardId())).findFirst().orElse(null);
            //计算
            BigDecimal sickAmount = BIG_DECIMAL_0;
            BigDecimal workAmount = BIG_DECIMAL_0;
            BigDecimal lateAmount = BIG_DECIMAL_0;
            BigDecimal minerAmount = BIG_DECIMAL_0;
            BigDecimal fullAmount = BIG_DECIMAL_0;
            if(ObjectUtil.isNotEmpty(standard)){
                //月工资标准
                BigDecimal standardAmount = standard.getStandardAmount();
                //①病假工资=月标准工资/21.75*病假天数*0.5
                sickAmount = standardAmount.divide(BIG_DECIMAL_2175,INT_2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(item.getSickDay()))
                        .multiply(BIG_DECIMAL_05);
                //②事假天数小于等于1.5天，事假工资=月标准工资/21.75*事假天数
                //  事假天数大于1.5天，事假工资=月标准工资/21.75*1+月标准工资/21.75*（事假天数-1）*1.2
                if(item.getWorkDay() > FLOAT_15){
                    workAmount = standardAmount.divide(BIG_DECIMAL_2175,INT_2,RoundingMode.HALF_UP)
                            .add(standardAmount.divide(BIG_DECIMAL_2175,INT_2,RoundingMode.HALF_UP)
                                    .multiply(BigDecimal.valueOf(item.getWorkDay()-FLOAT_1))
                                    .multiply(BIG_DECIMAL_12));
                }else{
                    workAmount = standardAmount.divide(BIG_DECIMAL_2175,INT_2,RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(item.getWorkDay()));
                }
                //③迟到工资=月标准工资*1%*A类迟到次数+月标准工资*2%*B类迟到次数+月标准工资/21.75*0.5*C类迟到次数
                lateAmount = standardAmount.multiply(BIG_DECIMAL_001).multiply(BigDecimal.valueOf(item.getLateNumA()))
                        .add(standardAmount.multiply(BIG_DECIMAL_002).multiply(BigDecimal.valueOf(item.getLateNumB())))
                        .add(standardAmount.divide(BIG_DECIMAL_2175,INT_2,RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(item.getLateNumC())).multiply(BIG_DECIMAL_05));
                //④旷工1天，旷工工资=月标准工资/21.75*旷工天数*4
                //     * 连续旷工2天，旷工工资=月标准工资/21.75*旷工天数*6
                if(item.getMinerDay() <= FLOAT_1){
                    minerAmount = standardAmount.divide(BIG_DECIMAL_2175,INT_2,RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(item.getMinerDay())).multiply(BIG_DECIMAL_4);
                }else{
                    minerAmount = standardAmount.divide(BIG_DECIMAL_2175,INT_2,RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(item.getMinerDay())).multiply(BIG_DECIMAL_6);
                }
                //⑤全勤奖：当月无迟到、早退、脱岗、旷工、未打卡、病事假、调休等考勤异况，给予全勤奖20元
            }
        });
        return attendList;
    }


    /**
     * 月工资标准 - 计算 ③考核工资 ②岗位工资
     *
     * ①基本工资
     * 根据武汉市中心城区上一年度最低月工资标准确定
     * 目前刚从2010调整为2210元，后续会根据政策变化同步调整
     * ②岗位工资
     * 岗位工资=月工资标准—考核工资—餐饮补助
     * ③考核工资
     * 月标准工资4000以下，考核工资为月标准工资的10%
     * 月标准工资大于等于4000小于等于10000，考核工资为月标准工资的15%
     * 月标准工资10000以上，考核工资为月标准工资的20%
     * 有另行约定的按约定金额或比例
     * ④餐饮补助目前为150元
     *
     * @param standardList 月工资标准数据集合
     * @return
     */
    public static List<SalaryStandard> computeStandardGZ(List<SalaryStandard> standardList){
        standardList.forEach(item->{
            BigDecimal meritAmount = BIG_DECIMAL_0;
            if(item.getStandardAmount().compareTo(BIG_DECIMAL_4000)<0){
                meritAmount = item.getStandardAmount().multiply(BIG_DECIMAL_01);
            }else if(item.getStandardAmount().compareTo(BIG_DECIMAL_4000)>=0 && item.getStandardAmount().compareTo(BIG_DECIMAL_10000)<=0){
                meritAmount = item.getStandardAmount().multiply(BIG_DECIMAL_015);
            }else{
                meritAmount = item.getStandardAmount().multiply(BIG_DECIMAL_02);
            }
            //②岗位工资
            //岗位工资=月工资标准—考核工资
            BigDecimal postAmount = item.getStandardAmount().subtract(meritAmount);
            item.setMeritAmount(meritAmount);
            item.setPostAmount(postAmount);
        });
        return standardList;
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
     * doImport
     * 导入数据 - 同步至 正式库（处理默认值 0）
     * @param importTempData 导入确认时，无错误信息的数据
     * @return
     */
    public static List<SalaryImportTemp> initImportData(List<SalaryImportTemp> importTempData){
        importTempData.forEach(item->{
            if(ObjectUtil.isEmpty(item.getBasicAmount())){
                item.setBasicAmount(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getPostAmount())){
                item.setPostAmount(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getMeritAmount())){
                item.setMeritAmount(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getStandardAmount())){
                item.setStandardAmount(BIG_DECIMAL_0);
            }

            if(ObjectUtil.isEmpty(item.getMissNum())){
                item.setMissNum(INT_0);
            }
            if(ObjectUtil.isEmpty(item.getLateNumA())){
                item.setLateNumA(INT_0);
            }
            if(ObjectUtil.isEmpty(item.getLateNumB())){
                item.setLateNumB(INT_0);
            }
            if(ObjectUtil.isEmpty(item.getLateNumC())){
                item.setLateNumC(INT_0);
            }
            if(ObjectUtil.isEmpty(item.getSickDay())){
                item.setSickDay(FLOAT_0);
            }
            if(ObjectUtil.isEmpty(item.getWorkDay())){
                item.setWorkDay(FLOAT_0);
            }
            if(ObjectUtil.isEmpty(item.getMinerDay())){
                item.setMinerDay(FLOAT_0);
            }
            if(ObjectUtil.isEmpty(item.getYearDay())){
                item.setYearDay(FLOAT_0);
            }
            if(ObjectUtil.isEmpty(item.getMaterDay())){
                item.setMaterDay(FLOAT_0);
            }
            if(ObjectUtil.isEmpty(item.getMarryDay())){
                item.setMarryDay(FLOAT_0);
            }
            if(ObjectUtil.isEmpty(item.getFunDay())){
                item.setFunDay(FLOAT_0);
            }

            if(ObjectUtil.isEmpty(item.getBase())){
                item.setBase(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getNeedPay())){
                item.setNeedPay(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getPer())){
                item.setPer(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getUnit())){
                item.setUnit(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getAmount())){
                item.setAmount(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getYnPer())){
                item.setYnPer(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getYnUnit())){
                item.setYnUnit(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getSyPer())){
                item.setSysPer(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getSyUnit())){
                item.setSyUnit(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getGsUnit())){
                item.setGsUnit(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getYlPer())){
                item.setYlPer(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getYlUnit())){
                item.setYlUnit(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getYlSale())){
                item.setYlSale(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getSysPer())){
                item.setSysPer(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getTaxSy())){
                item.setTaxSy(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getTaxZn())){
                item.setTaxZn(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getTaxFd())){
                item.setTaxFd(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getTaxZf())){
                item.setTaxZf(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getTaxJy())){
                item.setTaxJy(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getTaxZh())){
                item.setTaxZh(BIG_DECIMAL_0);
            }

            if(ObjectUtil.isEmpty(item.getBtNum())){
                item.setBtNum(FLOAT_0);
            }
            if(ObjectUtil.isEmpty(item.getBtStandard1())){
                item.setBtStandard1(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getBtStandard1Total())){
                item.setBtStandard1Total(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getBtStandard2())){
                item.setBtStandard2(BIG_DECIMAL_0);
            }
            if(ObjectUtil.isEmpty(item.getBtStandard2Total())){
                item.setBtStandard2Total(BIG_DECIMAL_0);
            }

            if(ObjectUtil.isEmpty(item.getHaveBirthdayPay())){
                item.setHaveBirthdayPay("是");
            }
            if(ObjectUtil.isEmpty(item.getHaveSeniorityPay())){
                item.setHaveSeniorityPay("是");
            }
        });
        return importTempData;
    }
}
