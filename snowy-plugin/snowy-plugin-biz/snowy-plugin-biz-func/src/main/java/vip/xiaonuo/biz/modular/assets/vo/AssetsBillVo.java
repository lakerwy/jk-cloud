package vip.xiaonuo.biz.modular.assets.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.assets.entity.AssetsBill;

@Getter
@Setter
public class AssetsBillVo extends AssetsBill {

    /** 固资申购申请单-主键ID */
    @TableId
    @ApiModelProperty(value = "固资申购申请单-主键ID", position = 101)
    private String applyId;

    /** 固资申购申请单-申请部门 */
    @ApiModelProperty(value = "固资申购申请单-申请部门", position = 102)
    private String orgName;

    /** 固资申购申请单-使用人 */
    @ApiModelProperty(value = "固资申购申请单-使用人", position = 104)
    private String userName;

    /** 固资申购申请单-申购类别 */
    @ApiModelProperty(value = "固资申购申请单-申购类别", position = 105)
    private String type;

    /** 固资申购申请单-申购事项 */
    @ApiModelProperty(value = "固资申购申请单-申购事项", position = 106)
    private String items;

    /** 固资申购申请单-申购处理 */
    @ApiModelProperty(value = "固资申购申请单-申购处理", position = 107)
    private String handle;

    /** 固资申购申请单-合计 */
    @ApiModelProperty(value = "固资申购申请单-合计", position = 108)
    private String totalAmount;

    /** 固资申购申请单-备注 */
    @ApiModelProperty(value = "固资申购申请单-备注", position = 109)
    private String memo;

    /** 固资申购申请单-附件 */
    @ApiModelProperty(value = "固资申购申请单-附件", position = 110)
    private String attach;

    /** 固资申购申请单-SN流水号 */
    @ApiModelProperty(value = "固资申购申请单-SN流水号", position = 111)
    private String sn;

    /** 固资申购申请单-模型Id */
    @ApiModelProperty(value = "固资申购申请单-模型Id", position = 112)
    private String modelId;

    /** 固资申购申请单-流程实例Id */
    @ApiModelProperty(value = "固资申购申请单-流程实例Id", position = 113)
    private String processInstanceId;

    /** 固资申购申请单-流程状态 */
    @ApiModelProperty(value = "固资申购申请单-流程状态", position = 114)
    private String status;
}
