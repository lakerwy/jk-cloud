package vip.xiaonuo.biz.modular.prj.expense.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 费用明细编辑参数
 *
 * @author yong.ye
 * @date  2024/07/30 15:31
 **/
@Getter
@Setter
public class BizExpItemEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 2)
    private String no;

    /** 明细科目Id */
    @ApiModelProperty(value = "明细科目Id", position = 3)
    private String itemId;

    /** 明细科目 */
    @ApiModelProperty(value = "明细科目", position = 4)
    private String item;

    /** 摘要 */
    @ApiModelProperty(value = "摘要", position = 5)
    private String abstractDesc;

    /** 金额（含税） */
    @ApiModelProperty(value = "金额（含税）", position = 6)
    private String amount;

    /** 出差开始时间 */
    @ApiModelProperty(value = "出差开始时间", position = 7)
    private String startDate;

    /** 出差结束时间 */
    @ApiModelProperty(value = "出差结束时间", position = 8)
    private String endDate;

    /** 地址-起 */
    @ApiModelProperty(value = "地址-起", position = 9)
    private String startPoint;

    /** 地址-止 */
    @ApiModelProperty(value = "地址-止", position = 10)
    private String endPoint;

    /** 交通工具 */
    @ApiModelProperty(value = "交通工具", position = 11)
    private String traffic;

    /** 交通工具金额 */
    @ApiModelProperty(value = "交通工具金额", position = 12)
    private String trafficAmount;

    /** 其他项目 */
    @ApiModelProperty(value = "其他项目", position = 13)
    private String otherItem;

    /** 交通工具金额 */
    @ApiModelProperty(value = "交通工具金额", position = 14)
    private String otherAmount;

    /** 补贴项目 */
    @ApiModelProperty(value = "补贴项目", position = 15)
    private String subsidyItem;

    /** 补贴金额 */
    @ApiModelProperty(value = "补贴金额", position = 16)
    private String subsidyAmount;

    /** 小计 */
    @ApiModelProperty(value = "小计", position = 17)
    private String subTotal;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 18)
    private String memo;

    /** 报销申请单Id-外键 */
    @ApiModelProperty(value = "报销申请单Id-外键", position = 19)
    private String bizExpApplyId;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 20)
    private String attach;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 28)
    private String extJson;

}
