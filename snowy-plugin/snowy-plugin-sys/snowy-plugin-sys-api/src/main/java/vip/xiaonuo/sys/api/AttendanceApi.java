package vip.xiaonuo.sys.api;

import com.alibaba.fastjson2.JSONObject;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/20 11:35
 */
public interface AttendanceApi {
    void  attendance(JSONObject hrUserAttendanceAddParam);
}
