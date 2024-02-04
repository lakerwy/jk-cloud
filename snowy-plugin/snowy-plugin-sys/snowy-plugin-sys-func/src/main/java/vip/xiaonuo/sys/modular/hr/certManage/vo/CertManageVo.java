package vip.xiaonuo.sys.modular.hr.certManage.vo;

import com.baomidou.mybatisplus.annotation.TableField;
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

    /** 级别 */
    @ApiModelProperty(value = "级别", position = 3)
    private String level;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 4)
    private String name;

    /** 专业123 */
    @ApiModelProperty(value = "专业123", position = 5)
    private String major;

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

    /** 存管部门 */
    @ApiModelProperty(value = "存管部门", position = 12)
    private String depositAddress;

    /** 注册单位|发证单位 */
    @ApiModelProperty(value = "注册单位|发证单位", position = 13)
    private String unit;

    /** 证书所在单位 */
    @ApiModelProperty(value = "证书所在单位", position = 14)
    private String certUnit;


    /** 证书有效期预警标识 */
    @ApiModelProperty(value = "证书有效期预警标识", position = 31)
    private String certStage;

    /** 用户在职状态 */
    @ApiModelProperty(value = "用户在职状态", position = 31)
    private String userStatus;

    /** 用户类型：总部|分公司|外部人员 */
    @ApiModelProperty(value = "用户类型：总部|分公司|外部人员", position = 22)
    private String userType;

    /** 社保所在地 */
    @ApiModelProperty(value = "社保所在地", position = 16)
    private String insUnit;

    /** 学校专业 */
    @ApiModelProperty(value = "学校专业", position = 17)
    private String eduMajor;

    /** 证书状态：有效|无效 */
    @ApiModelProperty(value = "证书状态：有效|无效", position = 20)
    private String certStatus;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 15)
    private String dept;
}
