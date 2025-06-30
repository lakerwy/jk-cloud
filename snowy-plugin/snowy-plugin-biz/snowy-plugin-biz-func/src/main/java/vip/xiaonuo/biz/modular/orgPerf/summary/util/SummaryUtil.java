package vip.xiaonuo.biz.modular.orgPerf.summary.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SummaryUtil {
    /**
     * 计算进度，向下保留2位小数
     * @param compValue
     * @param goalValue
     * @return
     */
    public static BigDecimal calcProgress(BigDecimal compValue, BigDecimal goalValue){
        if(compValue == null || goalValue == null || goalValue.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return compValue.multiply(new BigDecimal(100)).divide(goalValue, 2, RoundingMode.FLOOR);
    }

    /**
     * 计算得分，向下保留2位小数
     * 达到目标值得100分，最高加20分
     * @param compValue
     * @param goalValue
     * @param weight
     * @return
     */
    public static BigDecimal calcScore(BigDecimal compValue, BigDecimal goalValue, BigDecimal weight){
        if(compValue == null || goalValue == null || weight == null || goalValue.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        BigDecimal score = compValue.multiply(weight).divide(goalValue, 2, RoundingMode.FLOOR);
        BigDecimal maxScore = new BigDecimal("120").multiply(weight).divide(new BigDecimal(100), 2, RoundingMode.FLOOR);
        score = score.compareTo(maxScore) > 0 ? maxScore : score;
        return score;
    }

    /**
     * 计算预算执行率得分，向下保留2位小数
     * 年度财务预算执行率不高于100%，得100分
     * 年度财务预算执行率每高于目标1%减1分，减分扣完为止
     * @param compValue
     * @param weight
     * @return
     */
    public static BigDecimal calcBudgetExecRateScore(BigDecimal compValue, BigDecimal goalValue, BigDecimal weight){
        BigDecimal score = null;
        if(compValue == null || goalValue == null || weight == null) {
            return score;
        }
        if(compValue.compareTo(new BigDecimal(100)) <= 0){
            score = new BigDecimal(100);
        } else {
            // 100-(compValue-goalValue) = 200-compValue
            score = new BigDecimal(100).subtract(compValue.subtract(goalValue));
        }
        // 最低0分
        if(score.compareTo(BigDecimal.ZERO) < 0){
            score = BigDecimal.ZERO;
        }
        score = score.multiply(weight).divide(new BigDecimal(100), 2, RoundingMode.FLOOR);;
        return score;
    }

    /**
     *
     * 数字公司-净利润得分单独计算（实际完成值可能为负数）
     * 否决计分法&完成率计分法：
     * 1.实际完成值≥0，得120分；
     * 2.实际完成值=目标值，得100分；
     * 3.实际完成值<目标值，得0分；
     * 4.实际完成值=（-147.8,0），得分=100+（1-实际完成值/目标值）*20，即减亏的，按减亏比例核算
     * @param compValue
     * @param goalValue
     * @param weight
     * @return
    */
    public static BigDecimal calcDigitalCompProfitScore(BigDecimal compValue, BigDecimal goalValue, BigDecimal weight) {
        BigDecimal score = null;
        if(compValue == null || goalValue == null || weight == null || goalValue.compareTo(BigDecimal.ZERO) == 0) {
            return score;
        }
        if(compValue.compareTo(BigDecimal.ZERO) >= 0){
            score = new BigDecimal(120);
        } else if (compValue.compareTo(goalValue) == 0) {
            score = new BigDecimal(100);
        } else if(compValue.compareTo(goalValue) < 0){
            score = BigDecimal.ZERO;
        } else {
            // 100 + (1-(-50/-100))*20 = 80
            score = new BigDecimal(100).add((BigDecimal.ONE.subtract(compValue.divide(goalValue,6, RoundingMode.FLOOR))).multiply(new BigDecimal(20)));
        }
        score = score.multiply(weight).divide(new BigDecimal(100), 2, RoundingMode.FLOOR);
        return score;
    }

    public static void main(String[] args) {
        System.out.println(calcDigitalCompProfitScore(new BigDecimal(-50), new BigDecimal(-100), new BigDecimal(20)));
    }
}
