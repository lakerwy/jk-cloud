package vip.xiaonuo.sys.api;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/20 11:35
 */
public interface SysAttendanceApi {
    void  resubmitAttendance(String processInstanceId, String applyType);
}
