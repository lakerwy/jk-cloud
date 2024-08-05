package vip.xiaonuo.biz.modular.prj.expense.utils;

import cn.hutool.core.util.ObjectUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Decimal 转换 金额 格式
 */
public class MyDecimalUtils {
    /**
     * 获取千分位展示 199，299,991.00
     * @param  amount 金额
     * @return
     */
    public static String getQian(String amount){
        if(ObjectUtil.isEmpty(amount)){
            return "";
        }
        try {
            BigDecimal tmpAmount = new BigDecimal(amount);
            // 使用 DecimalFormat 格式化金额
            DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
            String formattedAmount = decimalFormat.format(tmpAmount);
            return formattedAmount;
        }catch (Exception e){
            return "";
        }

    }
}
