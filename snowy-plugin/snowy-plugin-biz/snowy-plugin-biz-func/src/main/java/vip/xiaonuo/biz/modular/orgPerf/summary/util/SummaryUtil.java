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
        return compValue.divide(goalValue, 4, RoundingMode.FLOOR).multiply(new BigDecimal(100));
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
}
