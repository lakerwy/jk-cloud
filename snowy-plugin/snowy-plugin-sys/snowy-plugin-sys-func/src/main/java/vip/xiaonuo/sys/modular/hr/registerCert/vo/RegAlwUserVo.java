package vip.xiaonuo.sys.modular.hr.registerCert.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class RegAlwUserVo {

    /** 用户名称 */
    @ApiModelProperty(value = "用户名称", position = 2)
    private String name;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 3)
    private String position;

    /** 身份证号 */
    /*@ApiModelProperty(value = "身份证号", position = 4)
    private String idCardNumber;*/

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 5)
    private String firstOrgId;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 6)
    private String secOrgId;

    /** 入职时间 */
    @ApiModelProperty(value = "入职时间", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 8)
    private String education;

    /** 毕业院校 */
    @ApiModelProperty(value = "毕业院校", position = 9)
    private String college;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 10)
    private String subject;

    /** 毕业时间 */
    @ApiModelProperty(value = "毕业时间", position = 11)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date gradTime;

    /** 职称等级 */
    @ApiModelProperty(value = "职称等级", position = 12)
    private String titleLever;

    /** 职称专业 */
    @ApiModelProperty(value = "职称专业", position = 13)
    private String titleProfession;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 14)
    private String extJson;
}
