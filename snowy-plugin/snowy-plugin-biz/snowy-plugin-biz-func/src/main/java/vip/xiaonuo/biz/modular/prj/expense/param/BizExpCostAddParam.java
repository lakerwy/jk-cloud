package vip.xiaonuo.biz.modular.prj.expense.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 成本一览表添加参数
 *
 * @author yong.ye
 * @date  2024/07/30 15:32
 **/
@Getter
@Setter
public class BizExpCostAddParam {

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 2)
    private String no;

    /** 项目编号 */
    @ApiModelProperty(value = "项目编号", position = 3)
    private String prjNo;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 4)
    private String prjName;

    /** 明细科目Id */
    @ApiModelProperty(value = "明细科目Id", position = 5)
    private String itemId;

    /** 明细科目 */
    @ApiModelProperty(value = "明细科目", position = 6)
    private String item;

    /** 摘要 */
    @ApiModelProperty(value = "摘要", position = 7)
    private String abstractDesc;

    /** 金额（含税） */
    @ApiModelProperty(value = "金额（含税）", position = 8)
    private String amount;

    /** 进项税额 */
    @ApiModelProperty(value = "进项税额", position = 9)
    private String tax;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

    /** 报销申请单Id-外键 */
    @ApiModelProperty(value = "报销申请单Id-外键", position = 11)
    private String bizExpApplyId;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 12)
    private String attach;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 20)
    private String extJson;

}
