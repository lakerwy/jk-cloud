package vip.xiaonuo.sys.modular.hr.certManage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 总部人员+分公司人员 合集 数据实体
 */

@Getter
@Setter
public class PersonVo {

    /** 主键：总部花名册主键id|分公司花名册主键id */
    @ApiModelProperty(value = "主键：总部花名册主键id|分公司花名册主键id", position = 1)
    private String id;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 2)
    private String name;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 3)
    private String deptId;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 4)
    private String dept;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 5)
    private String idNumber;

    /** 人员信息来源：1 总部花名册 2 分公司花名册  用户类型：总部|分公司|外部人员 */
    @ApiModelProperty(value = "人员信息来源：1 总部花名册 2 分公司花名册", position = 7)
    private String userType;

    /** 社保所在地 */
    @ApiModelProperty(value = "社保所在地", position = 8)
    private String insUnit;

    /** 学校专业 */
    @ApiModelProperty(value = "学校专业", position = 9)
    private String eduMajor;

    /** 毕业时间 */
    @ApiModelProperty(value = "毕业时间", position = 10)
    private String graduateDate;

}
