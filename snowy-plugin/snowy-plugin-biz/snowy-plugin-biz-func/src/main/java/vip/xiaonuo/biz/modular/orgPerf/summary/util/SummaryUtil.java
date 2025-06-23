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
        if(compValue == null || goalValue == null) {
            return null;
        }
        return compValue.divide(goalValue, 4, RoundingMode.FLOOR).multiply(new BigDecimal(100));
    }

    /**
     * 计算得分，向下保留2位小数，最高120分
     * @param compValue
     * @param goalValue
     * @param weight
     * @return
     */
    public static BigDecimal calcScore(BigDecimal compValue, BigDecimal goalValue, BigDecimal weight){
        if(compValue == null || goalValue == null || weight == null) {
            return null;
        }
        BigDecimal score = compValue.multiply(weight).divide(goalValue, 2, RoundingMode.FLOOR);
        score = score.compareTo(new BigDecimal(120)) > 0 ? new BigDecimal(120) : score;
        return score;
    }
}
