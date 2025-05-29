package vip.xiaonuo.sys.modular.hr.personnel.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 简历-workJson实体
 *
 * @author pzh
 * @date  2025/05/29 10:08
 */
@Getter
@Setter
public class ResumeWork {

    /** 起止时间 */
    @ApiModelProperty(value = "起止时间", position = 1)
    private String timeRange;

    /** 工作单位 */
    @ApiModelProperty(value = "工作单位", position = 2)
    private String professionalTitle;

    /** 职务 */
    @ApiModelProperty(value = "职务", position = 3)
    private String duty;

    /** 原月工资 */
    @ApiModelProperty(value = "原月工资", position = 4)
    private String monthlySalary;

    /** 离职原因 */
    @ApiModelProperty(value = "离职原因", position = 5)
    private String leaveReason;

    /** 证明人和联系电话 */
    @ApiModelProperty(value = "证明人和联系电话", position = 6)
    private String certifier;

}
