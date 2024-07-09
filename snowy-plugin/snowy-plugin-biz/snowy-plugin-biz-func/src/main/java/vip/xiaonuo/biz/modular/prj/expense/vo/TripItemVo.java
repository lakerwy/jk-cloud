package vip.xiaonuo.biz.modular.prj.expense.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 差旅费用明细详情实体
 */

@Getter
@Setter
public class TripItemVo {
    /** 起止时间 - 起 */
    @ApiModelProperty(value = "起止时间 - 起", position = 1)
    private String startDate;

    /** 起止时间 - 止 */
    @ApiModelProperty(value = "起止时间 - 止", position = 2)
    private String endDate;

    /** 地点 - 起 */
    @ApiModelProperty(value = "地点 - 起", position = 3)
    private String startPoint;

    /** 地点 - 止 */
    @ApiModelProperty(value = "地点 - 止", position = 4)
    private String endPoint;

    /** 交通费 - 交通工具 */
    @ApiModelProperty(value = "交通费 - 交通工具", position = 5)
    private String traffic;

    /** 交通费 - 金额 */
    @ApiModelProperty(value = "交通费 - 金额", position = 6)
    private String trafficAmount;

    /** 其他 - 项目 */
    @ApiModelProperty(value = "其他 - 项目", position = 7)
    private String otherItem;

    /** 其他 - 金额 */
    @ApiModelProperty(value = "其他 - 金额", position = 8)
    private String otherAmount;

    /** 补贴 - 项目 */
    @ApiModelProperty(value = "补贴 - 项目", position = 9)
    private String subsidyItem;

    /** 补贴 - 金额 */
    @ApiModelProperty(value = "补贴 - 金额", position = 10)
    private String subsidyAmount;

    /** 小计 */
    @ApiModelProperty(value = "小计", position = 11)
    private String subTotal;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

}
