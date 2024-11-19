package vip.xiaonuo.sys.modular.hr.techAchive.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 其他（科技成果）编辑参数
 *
 * @author pzh
 * @date  2024/10/18 09:10
 **/
@Getter
@Setter
public class OtherTechAchiveEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 3)
    private String type;

    /** 完成人员 */
    @ApiModelProperty(value = "完成人员", position = 4)
    private String completedPersonnel;

    /** 完成人员 */
    @ApiModelProperty(value = "完成人员Id", position = 4)
    private String completedPersonnelId;

    /** 发证单位 */
    @ApiModelProperty(value = "发证单位", position = 5)
    private String issueOrg;

    /** 证书时间 */
    @ApiModelProperty(value = "证书时间", position = 6)
    private Date certDate;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 7)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

}
