package vip.xiaonuo.im.modular.tencent.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/11 14:39
 * 群组创建参数
 */
@Setter
@Getter
public class GroupCreateParam {
    @ApiModelProperty(value = "群主ID",position = 1)
    private String Owner_Account;

    @ApiModelProperty(value = "群组形态，包括 Public（陌生人社交群），" +
            "Private（即 Work，好友工作群），ChatRoom（即 Meeting，会议群），" +
            "AVChatRoom（直播群），Community（社群）",required = true,position = 2)
    @NotBlank(message = "群组形态不能为空")
    private String Type;

    @ApiModelProperty(value = "自定义群组 ID",position = 3)
    private String GroupId;

    @ApiModelProperty(value = "群组名称", position = 4)
    private String Name;

    @ApiModelProperty(value = "群简介", position = 5)
    private String Introduction;

    @ApiModelProperty(value = "群公告", position = 6)
    private String Notification;

    @ApiModelProperty(value = "群头像 URL", position = 6)
    private String FaceUrl;

    /**
     * 最大群成员数量，缺省时的默认值：付费套餐包上限，
     * 例如体验版是20，如果升级套餐包，需按照修改群基础资料修改这个字段
     */
    @ApiModelProperty(value = "最大群成员数量", position = 7)
    private Integer MaxMemberCount;

    /***
     * 申请加群处理方式。包含 FreeAccess（自由加入），
     * NeedPermission（需要验证），
     * DisableApply（禁止加群），如果不填，具体默认项可参见 加群方式差异；
     * 仅当创建支持申请加群的 群组 时，该字段有效。
     * 社群目前不支持此字段。
     */
    @ApiModelProperty(value = "申请加群处理方式", position = 8)
    private String ApplyJoinOption;

    /**
     * 包含 FreeAccess （直接邀请用户进群，不需要审批等操作），
     * NeedPermission 需要群管理员或者群主审批，
     * DisableInvite 不支持 SDK 邀请进群， 该选项 AVChatRoom 群类型不支持
     */
    @ApiModelProperty(value = "邀请加群处理方式", position = 9)
    private String InviteJoinOption;

    @ApiModelProperty(value = "群组维度的自定义字段", position = 10)
    private List<Map<String, Object>> AppDefinedData;

    @ApiModelProperty(value = "初始群成员列表", position = 11)
    private List<GroupMember> MemberList;

    @ApiModelProperty(value = "群成员维度的自定义字段", position = 12)
    private List<Map<String, Object>> AppMemberDefinedData;

    @ApiModelProperty(value = "是否支持话题选项", position = 13)
    private Integer SupportTopic;

    @ApiModelProperty(value = "话题的社群权限字段", position = 14)
    private Integer EnablePermission;

    @ApiModelProperty(value = "邀请加群处理方式", position = 15)
    private Integer DefaultPermission;

}
