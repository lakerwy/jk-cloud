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

    /** 所属大区 */
    @ApiModelProperty(value = "所属大区", position = 1)
    private String region;

    /** 部门Id - 分公司Id */
    @ApiModelProperty(value = "部门Id - 分公司Id", position = 2)
    private String orgId;

    /** 部门 - 分公司 */
    @ApiModelProperty(value = "部门 - 分公司", position = 3)
    private String orgName;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 4)
    private String yM;

    /** 1月人数 */
    @ApiModelProperty(value = "1月人数", position = 11)
    private Integer num1;

    /** 2月人数 */
    @ApiModelProperty(value = "2月人数", position = 12)
    private Integer num2;

    /** 3月人数 */
    @ApiModelProperty(value = "3月人数", position = 13)
    private Integer num3;

    /** 4月人数 */
    @ApiModelProperty(value = "4月人数", position = 14)
    private Integer num4;

    /** 5月人数 */
    @ApiModelProperty(value = "5月人数", position = 15)
    private Integer num5;

    /** 6月人数 */
    @ApiModelProperty(value = "6月人数", position = 16)
    private Integer num6;

    /** 7月人数 */
    @ApiModelProperty(value = "7月人数", position = 17)
    private Integer num7;

    /** 8月人数 */
    @ApiModelProperty(value = "8月人数", position = 18)
    private Integer num8;

    /** 9月人数 */
    @ApiModelProperty(value = "9月人数", position = 19)
    private Integer num9;

    /** 10月人数 */
    @ApiModelProperty(value = "10月人数", position = 20)
    private Integer num10;

    /** 11月人数 */
    @ApiModelProperty(value = "11月人数", position = 21)
    private Integer num11;

    /** 12月人数 */
    @ApiModelProperty(value = "12月人数", position = 22)
    private Integer num12;

    /** 平均人数 */
    @ApiModelProperty(value = "平均人数", position = 23)
    private Double avgNum;
}
