package vip.xiaonuo.sys.modular.hr.salary.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.sys.modular.hr.salary.entity.Salary;

import java.util.Date;

/**
 * 工资数据 - 成本划分 - 基础数据（关联查询）
 */

@Getter
@Setter
public class SalaryCostVo extends Salary {

    /** 入职日期 */
    @ApiModelProperty(value = "入职日期", position = 101)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 离职日期 */
    @ApiModelProperty(value = "离职日期", position = 102)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leaveDate;

    /** 【外键】发放工资的员工Id：HR_SALARY_STANDARD.ID */
    @ApiModelProperty(value = "【外键】发放工资的员工Id：HR_SALARY_STANDARD.ID", position = 103)
    private String standardId;

    /** 成本划分 - 存储JSON字符串 */
    @ApiModelProperty(value = "成本划分 - 存储JSON字符串", position = 104)
    private String costJson;

    /** 数据类型 - 工资(可能含 社保、公积金)|社保|公积金 */
    @ApiModelProperty(value = "数据类型 - 工资|社保|公积金", position = 105)
    private String dataType;

}
