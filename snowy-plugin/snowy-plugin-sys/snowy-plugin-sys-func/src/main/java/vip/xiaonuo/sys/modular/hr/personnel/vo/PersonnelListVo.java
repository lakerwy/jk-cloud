package vip.xiaonuo.sys.modular.hr.personnel.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class PersonnelListVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 档案号 */
    @ApiModelProperty(value = "档案号", position = 2)
    private String fileNo;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 3)
    private String name;

    /** 就职状态 */
    @ApiModelProperty(value = "就职状态", position = 8)
    private String status;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 9)
    private String sex;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 10)
    private String idNumber;

    /** 入职时间 */
    @ApiModelProperty(value = "入职时间", position = 29)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 转正时间 */
    @ApiModelProperty(value = "转正时间", position = 34)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date positiveTime;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 7)
    private String duty;

    /** 入职司龄(自动算) */
    @ApiModelProperty(value = "入职司龄(自动算)", position = 32)
    private String enterage;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 5)
    private String dept;

    /** 转正司龄(自动算) */
    @ApiModelProperty(value = "转正司龄(自动算)", position = 40)
    private String positiveAge;
}
