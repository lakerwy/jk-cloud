package vip.xiaonuo.flw.core.listener.car;

import lombok.extern.slf4j.Slf4j;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/10/10 9:29
 */
@Slf4j
public class CarUtil {
    public static void updateStatus(int status,String businessKey) {
        String sql = "UPDATE BIZ_CAR_VEHICLE_APPLY SET STATUS = '" + status + "' WHERE  ID = '" + businessKey + "'";
        CommonHandleUtil.handleSqlExecution(sql);
    }

    public static void updateDriverStatus(int status,String businessKey) {
        String sql = "UPDATE BIZ_CAR_DRIVERS SET STATUS = '" + status + "' WHERE  ID = '" + businessKey + "'";
        CommonHandleUtil.handleSqlExecution(sql);
    }
}
