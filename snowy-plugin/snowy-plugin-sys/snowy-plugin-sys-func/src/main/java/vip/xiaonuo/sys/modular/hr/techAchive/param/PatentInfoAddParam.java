package vip.xiaonuo.sys.modular.hr.techAchive.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 专利信息添加参数
 *
 * @author pzh
 * @date  2024/10/17 10:22
 **/
@Getter
@Setter
public class PatentInfoAddParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 专利名称 */
    @ApiModelProperty(value = "专利名称", position = 2)
    private String name;

    /** 申请类型 */
    @ApiModelProperty(value = "申请类型", position = 3)
    private String applyType;

    /** 申请号 */
    @ApiModelProperty(value = "申请号", position = 4)
    private String applyNo;

    /** 专利号 */
    @ApiModelProperty(value = "专利号", position = 5)
    private String patentNo;

    /** 发明人 */
    @ApiModelProperty(value = "发明人", position = 6)
    private String inventor;

    /** 申请日期 */
    @ApiModelProperty(value = "申请日期", position = 7)
    private Date applyDate;

    /** 授权日期 */
    @ApiModelProperty(value = "授权日期", position = 8)
    private Date authDate;

    /** 专利状态 */
    @ApiModelProperty(value = "专利状态", position = 9)
    private String patentStatus;

    /** 有效期至 */
    @ApiModelProperty(value = "有效期至", position = 10)
    private Date invalidDate;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 11)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

}
