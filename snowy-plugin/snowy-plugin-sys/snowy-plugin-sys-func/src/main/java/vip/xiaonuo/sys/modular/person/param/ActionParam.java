package vip.xiaonuo.sys.modular.person.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class ActionParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 评价|回复 */
    @ApiModelProperty(value = "评价|回复", position = 3)
    private String reviewContent;

    /** 操作类型：回复 1 |关闭 2 |删除 3 */
    @ApiModelProperty(value = "操作类型：回复 1 |关闭 2 |删除 3", position = 2)
    private String actionType;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String memo;

}
