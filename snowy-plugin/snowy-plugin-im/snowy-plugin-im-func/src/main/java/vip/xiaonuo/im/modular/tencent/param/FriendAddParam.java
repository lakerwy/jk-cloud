package vip.xiaonuo.im.modular.tencent.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.im.modular.tencent.bean.FriendBean;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/10 10:19
 */
@Setter
@Getter
public class FriendAddParam {
    @ApiModelProperty(value = "需要为该 UserID 添加好友", required = true,position = 1)
    private String fromAccount;

    @ApiModelProperty(value = "好友结构体对象", required = true, position = 2)
    private List<FriendBean> AddFriendItem;

    @ApiModelProperty(value = "加好友方式（默认双向加好友方式）：\n" +
            "Add_Type_Single 表示单向加好友\n" +
            "Add_Type_Both 表示双向加好友\n", position = 3)
    private String addType;

    @ApiModelProperty(value = "管理员强制加好友标记 1：表示强制加好友 0：表示常规加好友方式 ", position = 4)
    private Integer forceAddFlags;
}
