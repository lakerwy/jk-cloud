package vip.xiaonuo.pan.modular.userFile.event;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDevLogEvent {

    /** 用户姓名 */
    @ApiModelProperty(value = "用户姓名", position = 1)
    private String userName;

    /** 操作类别 */
    @ApiModelProperty(value = "操作类别", position = 2)
    private String  actionType;

    /** 信息 */
    @ApiModelProperty(value = "信息", position = 3)
    private String  message;

    /** 文件ID */
    @ApiModelProperty(value = "文件ID", position = 4)
    private List<String> fileIds;

}
