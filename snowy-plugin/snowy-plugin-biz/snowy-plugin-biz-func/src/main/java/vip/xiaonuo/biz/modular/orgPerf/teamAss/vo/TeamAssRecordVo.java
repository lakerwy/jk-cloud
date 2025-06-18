package vip.xiaonuo.biz.modular.orgPerf.teamAss.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 班子考核记录Vo
 * @author pzh
 * @date  2025/06/17 17:08
 */
@Getter
@Setter
public class TeamAssRecordVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 考核人ID */
    @ApiModelProperty(value = "考核人ID", position = 3)
    private String assessorId;

    /** 考核人姓名 */
    @ApiModelProperty(value = "考核人姓名", position = 5)
    private String assessorName;

    /** 考核年月 */
    @ApiModelProperty(value = "考核年度", position = 7)
    private String assYear;

    /** 考核类型 */
    @ApiModelProperty(value = "考核类型", position = 9)
    private String dictType;

    /** 加分|减分类型 */
    @ApiModelProperty(value = "加分|减分类型", position = 11)
    private String dictName;

    /** 实际加|减分值 */
    @ApiModelProperty(value = "实际加|减分值", position = 13)
    private Double realScore;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 17)
    private String memo;

}
