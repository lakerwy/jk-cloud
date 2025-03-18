package vip.xiaonuo.sys.modular.hr.branchRating.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class BranchFillVo {

    /** 填报记录 - 主键ID */
    @ApiModelProperty(value = "填报记录 - 主键ID", position = 1)
    private String id;

    /** 填报季度 */
    @ApiModelProperty(value = "填报季度", position = 2)
    private String fillSeason;

    /** 分公司id */
    @ApiModelProperty(value = "分公司id", position = 3)
    private String branchId;

    /** 分公司名称 */
    @ApiModelProperty(value = "分公司名称", position = 3)
    private String branchName;

    /** 分公司经营范围 */
    @ApiModelProperty(value = "分公司经营范围", position = 4)
    private String branchScope;

    /** 区域 */
    @ApiModelProperty(value = "区域", position = 5)
    private String area;

    /** 区域经理id */
    @ApiModelProperty(value = "区域经理id", position = 6)
    private String areaManagerId;

    /** 区域经理 */
    @ApiModelProperty(value = "区域经理", position = 7)
    private String areaManager;

    /** 总分 */
    @ApiModelProperty(value = "总分", position = 8)
    private BigDecimal totalScore;

    /** 级别 */
    @ApiModelProperty(value = "级别", position = 9)
    private String level;

    /** 是否公示 */
    @ApiModelProperty(value = "是否公示", position = 10)
    private Integer isShow;


    /** SN */
    @ApiModelProperty(value = "SN", position = 11)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 12)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 13)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 14)
    private String status;


    /** 填报状态 */
    @ApiModelProperty(value = "填报状态", position = 15)
    private String fillStatus;

}
