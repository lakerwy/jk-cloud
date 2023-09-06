package vip.xiaonuo.flw.modular.util;

import cn.hutool.core.util.StrUtil;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;
import vip.xiaonuo.flw.core.util.NodeRuntimeUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HandleExecutionUtil {
    private static final String ID_PRE = "LC";

    public static String getTableSn(String tableName){
        String id = CommonHandleUtil.newHandleSqlIdExecution(tableName);
        String idPre = "";
        String newId = "";
        if(StrUtil.isNotBlank(id)){
            //xb2023050001
            idPre = id.substring(0,8);
            //0001
            newId = String.format("%04d", Integer.parseInt(id.substring(8)) + 1);
        }else {
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyyMM");
            idPre = ID_PRE +format.format(date);
            newId = String.format("%04d", 1);
        }
        return idPre+newId;
    }
}
