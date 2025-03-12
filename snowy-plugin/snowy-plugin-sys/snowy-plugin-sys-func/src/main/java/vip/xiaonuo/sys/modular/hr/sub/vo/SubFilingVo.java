package vip.xiaonuo.sys.modular.hr.sub.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 分包单位备案Vo
 *
 * @author pzh
 * @date  2025/03/11 14:32
 **/
@Getter
@Setter
public class SubFilingVo {

    /** 分包单位备案id */
    @ApiModelProperty(value = "分包单位备案id", position = 1)
    private String subFilingId;

    /** 分包单位备案明细Id */
    @ApiModelProperty(value = "分包单位备案明细id", position = 2)
    private String subFilingItemId;

    /** 分公司名称(用印部门) */
    @ApiModelProperty(value = "分公司名称(用印部门)", position = 3)
    private String sealDept;

    /** 用印部门ID */
    @ApiModelProperty(value = "用印部门ID", position = 4)
    private String sealDeptId;

    /** 用印人 */
    @ApiModelProperty(value = "用印人", position = 5)
    private String sealUser;

    /** 用印人ID */
    @ApiModelProperty(value = "用印人ID", position = 6)
    private String sealUserId;

    /** 区域经理 */
    @ApiModelProperty(value = "区域经理", position = 7)
    private String manager;

    /** 区域经理ID */
    @ApiModelProperty(value = "区域经理ID", position = 8)
    private String managerId;

    /** 用印事项 */
    @ApiModelProperty(value = "用印事项", position = 9)
    private String sealItem;

    /** 内容摘要 */
    @ApiModelProperty(value = "内容摘要", position = 10)
    private String content;

    @ApiModelProperty(value = "分包单位", position = 21)
    private String unitName;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 22)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 23)
    private String attachments;

    /** 最后更新时间 */
    @ApiModelProperty(value = "最后更新时间", position = 24)
    private Date updateTime;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 30)
    private String processInstanceId;

}
