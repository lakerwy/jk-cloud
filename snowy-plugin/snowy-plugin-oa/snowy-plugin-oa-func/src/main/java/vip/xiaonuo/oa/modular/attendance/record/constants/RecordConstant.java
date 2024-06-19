package vip.xiaonuo.oa.modular.attendance.record.constants;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/18 9:23
 * 打卡记录常量表
 */
public class RecordConstant {
    /**
     * 打卡记录来源
     */
    public static final String SOURCE_TYPE_USER_CHECK = "USER_CHECK"; // 用户打卡

    public static final String SOURCE_TYPE_AUTO_CHECK = "AUTO_CHECK"; // 系统自动打卡

    public static final String SOURCE_TYPE_FAST_CHECK = "FAST_CHECK"; // 极速打卡

    public static final String SOURCE_TYPE_SYSTEM_IMPORT = "SYSTEM_IMPORT"; // 系统倒入

    /**
     * 考勤结果
     *    Normal：正常;
     *    Early：早退;
     *    Late：迟到;
     *    SeriousLate：严重迟到；
     *    Absenteeism：旷工迟到；
     *    NotSigned：未打卡
     *    PreCheckIn：预存数据； 为打卡准备
     */

    public static final String CHECKIN_RESULT_NORMAL = "Normal";
    public static final String CHECKIN_RESULT_Early = "Early";
    public static final String CHECKIN_RESULT_Late = "Late";
    public static final String CHECKIN_RESULT_SeriousLate = "SeriousLate";
    public static final String CHECKIN_RESULT_Absenteeism = "Absenteeism";
    public static final String CHECKIN_RESULT_NotSigned = "NotSigned";
    public static final String CHECKIN_RESULT_PreCheckIn = "PreCheckIn";

    /**
     * 考勤类型
     */
    public static final String CHECKIN_TYPE_ON_DUTY = "OnDuty"; //上班打卡
    public static final String CHECKIN_TYPE_OFF_DUTY = "OffDuty"; //下班打卡
    public  static final String CHECKIN_TYPE_MID_DUTY = "MidDuty"; //中午打卡


}
