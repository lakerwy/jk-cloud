package vip.xiaonuo.pan.modular.commonFile.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/11 15:27
 */

@Getter
@Setter
public class CommonFileUserVo {

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 1)
    private String userId;

    /** 用户姓名 */
    @ApiModelProperty(value = "用户姓名", position = 2)
    private String userName;

}
