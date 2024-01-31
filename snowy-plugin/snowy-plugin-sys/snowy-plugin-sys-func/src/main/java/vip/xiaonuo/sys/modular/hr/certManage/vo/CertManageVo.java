package vip.xiaonuo.sys.modular.hr.certManage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/***
 * 大搜索-联合搜索，三个结果集实体
 */
@Getter
@Setter
public class CertManageVo {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 模块类别：注册证书|培训证书 */
    @ApiModelProperty(value = "模块类别：注册证书|培训证书", position = 2)
    private String type;

    /** 证书类别：注册类别|培训类别 */
    @ApiModelProperty(value = "证书类别：注册类别|培训类别", position = 3)
    private String certType;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 4)
    private String name;

    /** 专业1 */
    @ApiModelProperty(value = "专业1", position = 5)
    private String major;

    /** 专业2 */
    @ApiModelProperty(value = "专业2", position = 6)
    private String major2;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 7)
    private String idNumber;

    /** 证书编号 */
    @ApiModelProperty(value = "证书编号", position = 8)
    private String certNo;

    /** 执业印章号 */
    @ApiModelProperty(value = "执业印章号", position = 9)
    private String certSealNo;

    /** 发证日期 */
    @ApiModelProperty(value = "发证日期", position = 10)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date issueDate;

    /** 有效期 */
    @ApiModelProperty(value = "有效期", position = 11)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validity;

//    /** 存管部门 */
//    @ApiModelProperty(value = "存管部门", position = 12)
//    private String depositAddress;

    /** 注册单位|发证单位 */
    @ApiModelProperty(value = "注册单位|发证单位", position = 13)
    private String unit;

    /** 证书名称 */
    @ApiModelProperty(value = "证书名称", position = 14)
    private String certName;

    /** 职务工种 */
    @ApiModelProperty(value = "职务工种", position = 15)
    private String jobType;

    /** 技术职称 */
    @ApiModelProperty(value = "技术职称", position = 16)
    private String technicalTitle;

//    /** 企业名称 */
//    @ApiModelProperty(value = "企业名称", position = 17)
//    private String companyName;

//    /** 备注 */
//    @ApiModelProperty(value = "备注", position = 18)
//    private String memo;

//    /** 附件 */
//    @ApiModelProperty(value = "附件", position = 19)
//    private String attach;

    /** 证书状态：有效|无效 */
    @ApiModelProperty(value = "证书状态：有效|无效", position = 20)
    private String certStatus;

    /** 系统用户：总部 SysUser.ID |分公司 HR_SUB_PERSONNEL.ID|外部人员 */
    @ApiModelProperty(value = "系统用户：总部 SysUser.ID |分公司 HR_SUB_PERSONNEL.ID|外部人员", position = 21)
    private String userId;

    /** 用户类型：总部|分公司|外部人员 */
    @ApiModelProperty(value = "用户类型：总部|分公司|外部人员", position = 22)
    private String userType;

    /** 级别 */
    @ApiModelProperty(value = "级别", position = 3)
    private String level;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 4)
    private String dept;

    /** 社保所在地 */
    @ApiModelProperty(value = "社保所在地", position = 13)
    private String socialLocation;

    /** 学历信息 */
    @ApiModelProperty(value = "学历信息", position = 13)
    private String eduInfo;
}
