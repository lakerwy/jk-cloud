package vip.xiaonuo.sys.modular.hr.techAchive.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 软著信息添加参数
 *
 * @author pzh
 * @date  2024/10/17 10:53
 **/
@Getter
@Setter
public class SoftwareCopyrightAddParam {

    /** 软著名称 */
    @ApiModelProperty(value = "软著名称", position = 2)
    private String name;

    /** 著作权人 */
    @ApiModelProperty(value = "著作权人", position = 3)
    private String owner;

    /** 登记号 */
    @ApiModelProperty(value = "登记号", position = 4)
    private String registerNo;

    /** 下证日期 */
    @ApiModelProperty(value = "下证日期", position = 8)
    private Date authDate;

    /** 首次发表日期 */
    @ApiModelProperty(value = "首次发表日期", position = 9)
    private Date firstPublishDate;

    /** 有效期至 */
    @ApiModelProperty(value = "有效期至", position = 11)
    private Date invalidDate;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 12)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String memo;

}
