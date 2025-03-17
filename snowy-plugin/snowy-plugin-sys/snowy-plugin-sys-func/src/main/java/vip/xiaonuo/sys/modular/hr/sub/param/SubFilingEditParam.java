package vip.xiaonuo.sys.modular.hr.sub.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 分包单位备案编辑参数
 *
 * @author pzh
 * @date  2025/03/11 14:32
 **/
@Getter
@Setter
public class SubFilingEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    private String id;

    /** 用印部门 */
    @ApiModelProperty(value = "用印部门", position = 2)
    private String sealDept;

    /** 用印部门ID(合作伙伴ID) */
    @ApiModelProperty(value = "用印部门ID(合作伙伴ID)", position = 3)
    private String sealDeptId;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 3)
    private String orgId;

    /** 用印人 */
    @ApiModelProperty(value = "用印人", position = 4)
    private String sealUser;

    /** 用印人ID */
    @ApiModelProperty(value = "用印人ID", position = 5)
    private String sealUserId;

    /** 区域经理 */
    @ApiModelProperty(value = "区域经理", position = 6)
    private String manager;

    /** 区域经理ID */
    @ApiModelProperty(value = "区域经理ID", position = 7)
    private String managerId;

    /** 用印事项 */
    @ApiModelProperty(value = "用印事项", position = 8)
    private String sealItem;

    /** 内容摘要 */
    @ApiModelProperty(value = "内容摘要", position = 9)
    private String content;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 15)
    private String attachments;

    /** 分包单位备案-明细 */
    @ApiModelProperty(value = "分包单位备案-明细", position = 17)
    private List<SubFilingItemEditParam> subFilingItemList;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 19)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 20)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 21)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 22)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 23)
    private String extJson;

}
