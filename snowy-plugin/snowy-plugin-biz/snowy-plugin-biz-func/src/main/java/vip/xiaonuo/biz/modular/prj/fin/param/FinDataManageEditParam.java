package vip.xiaonuo.biz.modular.prj.fin.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务数据汇总台账 - 转入管理费编辑参数
 *
 * @author yy
 * @date  2025/06/05 17:25
 **/
@Getter
@Setter
public class FinDataManageEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 合同Id */
    @ApiModelProperty(value = "合同Id", position = 2)
    private String contractId;

    /** 合同编码 */
    @ApiModelProperty(value = "合同编码", position = 3)
    private String contractNo;

    /** 转款单位id */
    @ApiModelProperty(value = "转款单位id", position = 4)
    private String unitId;

    /** 转款单位 */
    @ApiModelProperty(value = "转款单位", position = 5)
    private String unitName;

    /** 转款日期 */
    @ApiModelProperty(value = "转款日期", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date zkDate;

    /** 管理费类型 */
    @ApiModelProperty(value = "管理费类型", position = 7)
    private String feeType;

    /** 转款金额 */
    @ApiModelProperty(value = "转款金额", position = 8)
    private BigDecimal zkAmount;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 9)
    private String content;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 15)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 16)
    private String attach;

}
