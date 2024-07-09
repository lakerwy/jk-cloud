package vip.xiaonuo.oa.modular.attendance.holiday.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 假期类型配置表添加参数
 *
 * @author mayike
 * @date  2024/07/05 09:19
 **/
@Getter
@Setter
public class HolidayRuleAddParam {

    /** 假期类型 */
    @ApiModelProperty(value = "假期类型", position = 3)
    private String holidayName;

    /** 请假单位(hour/day) */
    @ApiModelProperty(value = "请假单位(hour/day)", position = 4)
    private String leaveUnit;

    /** 计算方式（按照工作日计算 0 /自然日计算 1） */
    @ApiModelProperty(value = "计算方式（按照工作日计算 0 /自然日计算 1）", position = 5)
    private Integer dayType;

    /** 是否限额 */
    @ApiModelProperty(value = "是否限额", position = 6)
    private Boolean expireFlag;

    /** 余额设置方式 （系统自动设置 0 手动发放） */
    @ApiModelProperty(value = "余额设置方式 （系统自动设置 0 手动发放）", position = 7)
    private Integer setWay;

    /** 有效期类型 （0 长期有效 1 手动设置） */
    @ApiModelProperty(value = "有效期类型 （0 长期有效 1 手动设置）", position = 8)
    private Integer expireLimit;

    /** 过期时间 */
    @ApiModelProperty(value = "过期时间（不设置就默认今年的最后一天）", position = 9)
    private Date expireDate;

    /** 最大可结转天数 */
    @ApiModelProperty(value = "最大可结转天数", position = 10)
    private Double maxCarryOver;

    /** 结转比例 */
    @ApiModelProperty(value = "结转比例", position = 11)
    private Double carryOverRatio;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 12)
    private String extJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String remark;

}
