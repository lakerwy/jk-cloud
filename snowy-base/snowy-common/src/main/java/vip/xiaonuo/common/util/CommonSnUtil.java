package vip.xiaonuo.common.util;

import cn.hutool.core.util.StrUtil;
import vip.xiaonuo.common.enums.CommonSnFormatEnum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 根据规则，生成各类SN
 *
 * @author yong.ye
 * @date 2023/7/3 8:29
 */
public class CommonSnUtil {

    /**
     *
     * @param preFix Sn前缀
     * @param maxSn 当前数据库最大Sn
     * @param type Sn 格式类别
     *        Type = SNYYYYMM0001 : Sn = [前缀][yyyyMM][0001]  每月从0001开始，每次自增1
     *        Type = SN0001 : Sn = [前缀][0001]  全体从0001开始，每次自增1
     *        Type = SNYYYY001 : Sn = [前缀][yyyy][001] 每年从001开始，每次自增1
     * @return 返回按照格式生成的Sn流水号
     *        Type = SNYYYYMM0001 : Sn = HT2023070001
     *        Type = SN0001 : Sn = ZY0001
     *        Type = SNYYYY001 : Sn = CA2023001
     */
    public static String GenerateSn(String preFix,String maxSn,String type){

        if(CommonSnFormatEnum.SNYYYYMM0001.getValue().equals(type)){
            //准备初始参数
            String strDate = new SimpleDateFormat("yyyyMM").format(new Date());
            String strNum = "0001";
            //判断逻辑
            if(StrUtil.isEmpty(maxSn)){
                return preFix + strDate + strNum;
            }
            //获取数据库最大Sn的{yyyyMM}部分，判断是否在同一个月份
            String tmpSnDate = maxSn.substring(preFix.length(), preFix.length() + 6);
            if(strDate.equals(tmpSnDate)){
                String tmpSnNum = maxSn.substring(preFix.length() + 6);
                strNum = String.format("%04d", Integer.parseInt(tmpSnNum ) + 1);
            }
            return preFix + strDate + strNum;
        }else if(CommonSnFormatEnum.SN0001.getValue().equals(type)){
            //准备参数
            String strNum = "0001";
            //判断逻辑
            if(StrUtil.isEmpty(maxSn)){
                return preFix + strNum;
            }
            //获取Sn的计数部分{0001}
            String tmpSnNum = maxSn.substring(preFix.length());
            strNum = String.format("%04d", Integer.parseInt(tmpSnNum ) + 1);
            return preFix + strNum;

        }else if(CommonSnFormatEnum.SNYYYY001.getValue().equals(type)){
            //准备参数
            String strDate = new SimpleDateFormat("yyyy").format(new Date());
            String strNum = "001";
            //判断逻辑
            if(StrUtil.isEmpty(maxSn)){
                return preFix + strDate + strNum;
            }
            //获取数据库最大Sn的{yyyy}部分，判断是否在同一年份
            String tmpSnDate = maxSn.substring(preFix.length(), preFix.length() + 4);
            if(strDate.equals(tmpSnDate)){
                String tmpSnNum = maxSn.substring(preFix.length() + 4);
                strNum = String.format("%03d", Integer.parseInt(tmpSnNum ) + 1);
            }
            return preFix + strDate + strNum;
        }else{
            return "";
        }
    }

}
