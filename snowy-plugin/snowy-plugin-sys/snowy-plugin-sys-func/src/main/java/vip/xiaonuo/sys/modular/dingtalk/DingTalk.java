package vip.xiaonuo.sys.modular.dingtalk;

import lombok.Data;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/14 17:05
 */
@Data
public class DingTalk {
    public interface EventType{
        public static final String USER_ADD = "user_add_org";  //通讯录用户增加

        public static final String USER_UPDATE = "user_modify_org";  //通讯录用户更改

        public static final String USER_LEAVE = "user_leave_org";  //通讯录用户离职

        public static final String ORG_ADD = "org_dept_create";  //通讯录企业部门创建

        public static final String ORG_UPDATE = "org_dept_modify";  //通讯录企业部门修改

        public static final String ORG_REMOVE = "org_dept_remove";  //通讯录企业部门修改

        public static final String MEETING_BOOK = "meetingroom_book"; //钉钉日程
    }
}
