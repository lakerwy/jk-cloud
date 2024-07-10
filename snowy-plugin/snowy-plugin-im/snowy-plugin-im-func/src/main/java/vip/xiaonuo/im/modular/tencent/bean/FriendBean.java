package vip.xiaonuo.im.modular.tencent.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/10 10:20
 */
@Setter
@Getter
public class FriendBean {
    @ApiModelProperty(value = "好友的Uid",required = true,position = 1)
    private String toAccount;

    @ApiModelProperty(value = "好友备注",position = 2)
    private String remark;

    @ApiModelProperty(value = "分组信息",position = 3)
    private String groupName;

    @ApiModelProperty(value = "加好友来源字段",required = true,position = 4)
    private String addSource;

    @ApiModelProperty(value = "好友关系时的附言信息",position = 5)
    private String addWording;
}
