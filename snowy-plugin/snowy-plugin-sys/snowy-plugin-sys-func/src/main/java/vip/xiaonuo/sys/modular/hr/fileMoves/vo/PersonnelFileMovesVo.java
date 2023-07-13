package vip.xiaonuo.sys.modular.hr.fileMoves.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class PersonnelFileMovesVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 人员档案ID */
    @ApiModelProperty(value = "人员档案ID", position = 2)
    private String fileId;

    /** 经办日期 */
    @ApiModelProperty(value = "经办日期", position = 3)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date optDate;

    /** 经办人 */
    @ApiModelProperty(value = "经办人", position = 4)
    private String opt;

    /** SN */
    @ApiModelProperty(value = "SN", position = 5)
    private String sn;

    /** 审批单号 */
    @ApiModelProperty(value = "审批单号", position = 6)
    private String approvalNo;

    /** 异动类型（字典） */
    @ApiModelProperty(value = "异动类型（字典）", position = 7)
    private String moveType;

    /** 异动事项 */
    @ApiModelProperty(value = "异动事项", position = 8)
    private String moveItem;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 9)
    private String attachments;

    /** 用工性质 */
    @ApiModelProperty(value = "用工性质", position = 10)
    private String useNature;

    /** 转正时间 */
    @ApiModelProperty(value = "转正时间", position = 11)
    private Date positiveTime;

    /** 现部门ID */
    @ApiModelProperty(value = "现部门ID", position = 12)
    private String origDeptId;

    /** 现岗位ID */
    @ApiModelProperty(value = "现岗位ID", position = 13)
    private String origDutyId;

    /** 新部门 */
    @ApiModelProperty(value = "新部门", position = 14)
    private String nowDept;

    /** 新岗位 */
    @ApiModelProperty(value = "新岗位", position = 15)
    private String nowDuty;

    /** 状态表更 */
    @ApiModelProperty(value = "状态表更", position = 16)
    private String stateChange;

    /** 离职时间 */
    @ApiModelProperty(value = "离职时间", position = 17)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date livetime;

    /** 现部门 */
    @ApiModelProperty(value = "现部门", position = 18)
    private String origDept;

    /** 现岗位 */
    @ApiModelProperty(value = "现岗位", position = 19)
    private String origDuty;

    /** 新岗位ID */
    @ApiModelProperty(value = "新岗位ID", position = 20)
    private String nowDeptId;

    /** 新部门ID */
    @ApiModelProperty(value = "新部门ID", position = 21)
    private String nowDutyId;

    @ApiModelProperty(value = "姓名", position = 22)
    private String name;

}
