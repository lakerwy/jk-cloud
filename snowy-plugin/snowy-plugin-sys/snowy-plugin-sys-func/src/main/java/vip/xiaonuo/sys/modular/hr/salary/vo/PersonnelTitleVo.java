package vip.xiaonuo.sys.modular.hr.salary.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class PersonnelTitleVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 外键 - 档案ID（HR_SUB_PERSONNEL.Id） */
    @ApiModelProperty(value = "外键 - 档案ID（HR_SUB_PERSONNEL.Id）", position = 2)
    private String fileId;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 3)
    private String name;

    /** 职称专业 */
    @ApiModelProperty(value = "职称专业", position = 4)
    private String titleProfession;

    /** 职称等级 */
    @ApiModelProperty(value = "职称等级", position = 5)
    private String titleLever;

    /** 职称编号 */
    @ApiModelProperty(value = "职称编号", position = 6)
    private String titleNo;

    /** 发证机关 */
    @ApiModelProperty(value = "发证机关", position = 7)
    private String reviewOrgan;

    /** 发证日期 */
    @ApiModelProperty(value = "发证日期", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date certificateDate;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String note;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 10)
    private String attachments;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 11)
    private String extJson;


    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 13)
    private Date createTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 17)
    private String tenantId;
}
