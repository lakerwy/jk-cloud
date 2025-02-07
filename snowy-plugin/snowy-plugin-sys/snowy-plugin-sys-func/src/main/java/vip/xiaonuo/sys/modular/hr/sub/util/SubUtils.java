package vip.xiaonuo.sys.modular.hr.sub.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;

import java.math.BigDecimal;
import java.util.List;

public class SubUtils {

    /**
     * 求和 - 金额
     * @param itemList
     * @return
     */
    public static BigDecimal addTotal(List<BigDecimal> itemList){
        BigDecimal result = BigDecimal.ZERO;
        if(CollUtil.isNotEmpty(itemList)){
            for (BigDecimal item : itemList) {
                if(ObjectUtil.isNotEmpty(item)) {
                    result = result.add(item);
                }
            }
        }
        return result;
    }







}
