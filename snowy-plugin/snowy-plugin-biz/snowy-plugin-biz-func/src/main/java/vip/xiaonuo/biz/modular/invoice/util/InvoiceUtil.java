package vip.xiaonuo.biz.modular.invoice.util;

import cn.hutool.core.date.DateUtil;

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
}
