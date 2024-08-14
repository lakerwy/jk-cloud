package vip.xiaonuo.common.consts;

/**
 * @PACKAGE_NAME: vip.xiaonuo.common.consts
 * @NAME: FlwCacheConstant
 * @USER: jack
 * @DATE: 7/27/2023
 * @PROJECT_NAME: jk-cloud
 **/
public class FlwCacheConstant {
    public static final String  CACHE_FLW_KEY_PREFIX = "flw_";

    public static final String CACHE_FLW_ID_KEY = CACHE_FLW_KEY_PREFIX+"ID_";

    public static final String CACHE_FLW_SN_KEY = CACHE_FLW_KEY_PREFIX+"SN_";

    public static final String CACHE_FLW_MODEL_ID_KEY = CACHE_FLW_KEY_PREFIX+"MODEL_ID_";

    public static final String FLW_ID = "flw_Id";

    /**
     * 流程名称
     */
    public static final String FLW_NAME = "flw_name";

    /**
     * 流程基本信息
     */
    public static final String FLW_BASE_INFO = "flw_base_info";

    /**
     * 流程sn
     */
    public static final String FLW_SN= "flw_sn";

    /**
     * 流程发起人
     */
    public static final String FLW_STARTER= "flw_starter";

    /**
     * 流程开始时间
     */
    public static final String FLW_START_TIME= "flw_start_time";

    /**
     * 发送人集合
     */
    public static final String SENDERS = "senders";

    /**
     * 流程的标题
     */
    public static final String TITLE = "title";

    /**
     * 流程提示
     */
    public static final String TIPS = "tips";

    /**
     * 流程类型---todo done copy
     */
    public static final String FLW_TYPE = "flw_type";

    /**
     * 当前节点值
     */
    //currentActivityId
    public static final String CURRENT_ACTIVITY_ID = "currentActivityId";

    public static final String STATE_CODE = "stateCode";

    /**
     * 分享抄送通知人
     */
    public static final String CARBON_NOTIFICATION_NAME = "carbon_notification_name";


    /**
     * 评论人
     */
    public static final String FLW_COMMENT_USER= "flw_comment_user";

    /**
     * 评论内容
     */
    public static final String FLW_COMMENT_CONTEXT= "flw_comment_context";


    public static final String FLW_STATUS= "flw_status";

    public static final String FLW_TASK_USER = "flw_task_user_";

    public static final String FLW_MSG_TYPE= "flw_msg_type";

    public static final String FLE_PROCESS_INSTANCE_ID = "flw_processInstanceId";


    public static  final String FLW_URGE_KEY = "flw_urge:";

    public static  final String FLW_URGE_HOURS = "24";

    public static  final String BIZ_VISIT_KEY = "biz_visit_expire";

    public static class FlwType{
        public static final String TODO = "todo";

        public static final String DONE = "done";
    }
}
