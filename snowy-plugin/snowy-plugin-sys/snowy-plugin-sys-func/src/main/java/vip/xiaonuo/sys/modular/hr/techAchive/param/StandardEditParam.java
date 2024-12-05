package vip.xiaonuo.sys.modular.hr.techAchive.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 标准规范编辑参数
 *
 * @author pzh
 * @date  2024/10/17 17:05
 **/
@Getter
@Setter
public class StandardEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 持有单位 */
    @ApiModelProperty(value = "持有单位", position = 2)
    private String holdingOrg;

    /** 标准名称 */
    @ApiModelProperty(value = "标准名称", position = 2)
    private String name;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 3)
    private String type;

    /** 标准类型 */
    @ApiModelProperty(value = "标准类型", position = 4)
    private String standardType;

    /** 参编人员 */
    @ApiModelProperty(value = "参编人员", position = 5)
    private String participants;

    /** 参编人员Id */
    @ApiModelProperty(value = "参编人员Id", position = 5)
    private String participantsId;

    /** 发布日期 */
    @ApiModelProperty(value = "发布日期", position = 6)
    private Date publishDate;

    /** 实施日期 */
    @ApiModelProperty(value = "实施日期", position = 7)
    private Date effectiveDate;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 8)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

}
