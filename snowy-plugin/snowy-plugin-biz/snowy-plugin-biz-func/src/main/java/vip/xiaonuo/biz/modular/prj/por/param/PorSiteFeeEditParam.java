package vip.xiaonuo.biz.modular.prj.por.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSiteFeeItem;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目现场管理费用编辑参数
 *
 * @author yy
 * @date  2025/06/03 09:45
 **/
@Getter
@Setter
public class PorSiteFeeEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 收入合同- 主键Id */
    @ApiModelProperty(value = "收入合同- 主键Id", position = 2)
    private String hetongId;

    /** 费用归属月份 */
    @ApiModelProperty(value = "费用归属月份", position = 3)
    private String feeMonth;

    /** 本月费用合计 */
    @ApiModelProperty(value = "本月费用合计", position = 4)
    private BigDecimal feeMonthTotal;

    /** 预算金额 */
    @ApiModelProperty(value = "预算金额", position = 5)
    private BigDecimal feePreAmount;

    /** 超支比例 */
    @ApiModelProperty(value = "超支比例", position = 6)
    private Double feeOutRate;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 7)
    private String feeAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String feeMemo;

    /** 记录人id */
    @ApiModelProperty(value = "记录人id", position = 9)
    private String feeOptId;

    /** 记录人 */
    @ApiModelProperty(value = "记录人", position = 10)
    private String feeOptName;

    /** 记录时间 */
    @ApiModelProperty(value = "记录时间", position = 11)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date feeOptTime;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 16)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 17)
    private String attach;



    /** 项目管理费分类明细 */
    @ApiModelProperty(value = "项目管理费分类明细", position = 200)
    private List<PorSiteFeeItem> itemList;
}
