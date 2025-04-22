package vip.xiaonuo.sys.modular.hr.icManage.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ChangeParam {

    /** id */
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 时间 */
    @ApiModelProperty(value = "时间", position = 2)
    private String date;

    /** 变更事项 */
    @ApiModelProperty(value = "变更事项", position = 3)
    private List<String> changeItem;

    /** 经办人 */
    @ApiModelProperty(value = "经办人", position = 4)
    private String operator;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 5)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 6)
    private String memo;


    /** 流程实例 */
    @ApiModelProperty(value = "流程实例", position = 7)
    private String processInstanceId;

}
