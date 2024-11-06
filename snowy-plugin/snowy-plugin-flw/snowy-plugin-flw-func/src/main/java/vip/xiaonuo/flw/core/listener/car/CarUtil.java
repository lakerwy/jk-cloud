package vip.xiaonuo.flw.core.listener.car;

import lombok.extern.slf4j.Slf4j;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;

import java.math.BigDecimal;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/10/10 9:29
 */
@Slf4j
public class CarUtil {
    public static void updateApplyStatus(String applyStatus,String businessKey) {
        String sql = "UPDATE BIZ_CAR_VEHICLE_APPLY SET APPLY_STATUS = '" + applyStatus + "' WHERE  ID = '" + businessKey + "'";
        CommonHandleUtil.handleSqlExecution(sql);
    }

    public static void updateDriverStatus(String status,String businessKey) {
        String sql = "UPDATE BIZ_CAR_DRIVERS SET STATUS = '" + status + "' WHERE  DRIVER_USER_ID = '" + businessKey + "'";
        CommonHandleUtil.handleSqlExecution(sql);
    }

    public static void updateCarStatus(String status, String businessKey) {
        String sql = "UPDATE BIZ_CAR_VEHICLE_INFORMATION SET STATUS = '" + status + "' WHERE  ID = '" + businessKey + "'";
        CommonHandleUtil.handleSqlExecution(sql);
    }

    public static void updateCarTotalMileage(BigDecimal totalMileage, String businessKey) {
        String sql = "UPDATE BIZ_CAR_VEHICLE_INFORMATION SET TOTAL_MILEAGE =" + totalMileage + " WHERE  ID = '" + businessKey + "'";
        CommonHandleUtil.handleSqlExecution(sql);
    }
}
