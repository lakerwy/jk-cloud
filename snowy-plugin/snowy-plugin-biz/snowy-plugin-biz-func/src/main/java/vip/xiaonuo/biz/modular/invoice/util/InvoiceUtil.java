package vip.xiaonuo.biz.modular.invoice.util;

import cn.hutool.core.date.DateUtil;

import java.math.BigDecimal;

/**
 * 发票系统-工具类
 */
public class InvoiceUtil {

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
     * 校验字符串 是否为金额
     * @param bigStr
     * @return
     */
    public static Boolean isValidBigDecimal(String bigStr){
        try {
            new BigDecimal(bigStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
