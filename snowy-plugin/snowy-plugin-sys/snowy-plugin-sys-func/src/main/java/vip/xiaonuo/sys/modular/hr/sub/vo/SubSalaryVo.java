package vip.xiaonuo.sys.modular.hr.sub.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubSalaryVo {
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 分公司组织Id */
    @ApiModelProperty(value = "分公司组织Id", position = 2)
    private String orgId;

    /** 分公司名称 */
    @ApiModelProperty(value = "分公司名称", position = 3)
    private String orgName;

    /** 工资发放年度 */
    @ApiModelProperty(value = "工资发放年度", position = 4)
    private String year;

    /** 工资发放月 */
    @ApiModelProperty(value = "工资发放月", position = 5)
    private String month;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 6)
    private String note;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 7)
    private String attachments;

    /** SN */
    @ApiModelProperty(value = "SN", position = 16)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 17)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 18)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 19)
    private String status;

    /** 是否申报 */
    @ApiModelProperty(value = "是否申报", position = 20)
    private String isApply;
}
