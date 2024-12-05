package vip.xiaonuo.sys.modular.hr.techAchive.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 查新添加参数
 *
 * @author pzh
 * @date  2024/10/18 08:44
 **/
@Getter
@Setter
public class NoveltySearchAddParam {

    /** 持有单位 */
    @ApiModelProperty(value = "持有单位", position = 2)
    private String holdingOrg;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 2)
    private String prjName;

    /** 报告编号 */
    @ApiModelProperty(value = "报告编号", position = 3)
    private String reportNo;

    /** 查新目的 */
    @ApiModelProperty(value = "查新目的", position = 4)
    private String purpose;

    /** 委托日期 */
    @ApiModelProperty(value = "委托日期", position = 5)
    private Date entrustDate;

    /** 完成日期 */
    @ApiModelProperty(value = "完成日期", position = 6)
    private Date finishDate;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 7)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

}
