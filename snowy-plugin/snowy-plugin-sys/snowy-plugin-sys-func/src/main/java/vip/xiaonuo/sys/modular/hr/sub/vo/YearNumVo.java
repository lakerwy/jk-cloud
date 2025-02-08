package vip.xiaonuo.sys.modular.hr.sub.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 年度 - 人数 统计
 */

@Getter
@Setter
public class YearNumVo {

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 1)
    private Integer no;

    /** 所属大区 */
    @ApiModelProperty(value = "所属大区", position = 1)
    private String region;

    /** 部门Id - 分公司Id */
    @ApiModelProperty(value = "部门Id - 分公司Id", position = 2)
    private String orgId;

    /** 部门 - 分公司 */
    @ApiModelProperty(value = "部门 - 分公司", position = 3)
    private String orgName;

    /** 所属年 */
    @ApiModelProperty(value = "所属年", position = 4)
    private String yM;

    /** 1月人数 */
    @ApiModelProperty(value = "1月人数", position = 11)
    private Integer num1 = 0;

    /** 2月人数 */
    @ApiModelProperty(value = "2月人数", position = 12)
    private Integer num2 = 0;

    /** 3月人数 */
    @ApiModelProperty(value = "3月人数", position = 13)
    private Integer num3 = 0;

    /** 4月人数 */
    @ApiModelProperty(value = "4月人数", position = 14)
    private Integer num4 = 0;

    /** 5月人数 */
    @ApiModelProperty(value = "5月人数", position = 15)
    private Integer num5 = 0;

    /** 6月人数 */
    @ApiModelProperty(value = "6月人数", position = 16)
    private Integer num6 = 0;

    /** 7月人数 */
    @ApiModelProperty(value = "7月人数", position = 17)
    private Integer num7 = 0;

    /** 8月人数 */
    @ApiModelProperty(value = "8月人数", position = 18)
    private Integer num8 = 0;

    /** 9月人数 */
    @ApiModelProperty(value = "9月人数", position = 19)
    private Integer num9 = 0;

    /** 10月人数 */
    @ApiModelProperty(value = "10月人数", position = 20)
    private Integer num10 = 0;

    /** 11月人数 */
    @ApiModelProperty(value = "11月人数", position = 21)
    private Integer num11 = 0;

    /** 12月人数 */
    @ApiModelProperty(value = "12月人数", position = 22)
    private Integer num12 = 0;

    /** 平均人数 */
    @ApiModelProperty(value = "平均人数", position = 23)
    private Double avgNum = 0d;
}
