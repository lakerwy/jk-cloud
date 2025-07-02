package vip.xiaonuo.biz.modular.orgPerf.perfAss.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class BizOrgAssRecordVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 考核年月 */
    @ApiModelProperty(value = "考核年度", position = 3)
    private String assYear;

    /** 字典类型（加分项、减分项） */
    @ApiModelProperty(value = "考核类型（1-加分，2-减分）", position = 5)
    private String dictType;

    /** 加分|减分类型 */
    @ApiModelProperty(value = "加分|减分类型", position = 7)
    private String dictName;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 9)
    private String memo;

    /** 分值 */
    @ApiModelProperty(value = "分值", position = 10)
    private Double dictScore;

    /** 实际加|减分值 */
    @ApiModelProperty(value = "实际加分分值", position = 11)
    private Double addScore;

    /** 实际加|减分值 */
    @ApiModelProperty(value = "实际减分分值", position = 13)
    private Double minusScore;
}
