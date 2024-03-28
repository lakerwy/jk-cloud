package vip.xiaonuo.sys.modular.hr.registerCert.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
public class ApplyRegCertAlwVo {
    /** 注册等级 */
    @ApiModelProperty(value = "注册等级", position = 2)
    private String registerLever;

    /** 注册类别 */
    @ApiModelProperty(value = "注册类别", position = 3)
    private String registerType;

    /** 注册专业 */
    @ApiModelProperty(value = "注册专业", position = 4)
    private String registerSubject;

    /** 申请事项 */
    @ApiModelProperty(value = "申请事项", position = 5)
    private String aplyItem;

    /** 是否正常 */
    @ApiModelProperty(value = "是否正常", position = 6)
    private String isFlag;

    /** 注册成功日期 */
    @ApiModelProperty(value = "注册成功日期", position = 7)
    private Date registerSucDate;

    /** 参与项目名称 */
    @ApiModelProperty(value = "参与项目名称", position = 8)
    private String joinPrjName;

    /** 参与项目日期 */
    @ApiModelProperty(value = "参与项目日期", position = 9)
    private Date joinPrjDate;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 10)
    private String status;

    /** 流程实例ID */
    @ApiModelProperty(value = "流程实例ID", position = 11)
    private String processInstanceId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 12)
    private String extJson;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 15)
    private String userId;

    /** 员工类别：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类别：总部|分公司|外部")
    private String userType;

    /** 用户姓名 */
    @ApiModelProperty(value = "用户姓名")
    private String userName;
}
