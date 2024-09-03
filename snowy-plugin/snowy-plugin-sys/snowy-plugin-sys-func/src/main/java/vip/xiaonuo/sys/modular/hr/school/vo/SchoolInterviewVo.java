package vip.xiaonuo.sys.modular.hr.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 校招 - （应聘者）简历库信息实体
 *
 * @author yong.ye
 * @date  2024/09/03 10:44
 **/
@Getter
@Setter
public class SchoolInterviewVo{
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 应聘部门 */
    @ApiModelProperty(value = "应聘部门", position = 3)
    private String dept;

    /** 外键：应聘者简历Id */
    @ApiModelProperty(value = "外键：应聘者简历Id", position = 4)
    private String resumeId;

    /** 匹配岗位说明书任职资格要求JSON */
    @ApiModelProperty(value = "匹配岗位说明书任职资格要求JSON", position = 5)
    private String matchJson;

    /** 建议薪酬JSON */
    @ApiModelProperty(value = "建议薪酬JSON", position = 6)
    private String salaryJson;

    /** SN */
    @ApiModelProperty(value = "SN", position = 20)
    private String sn;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 22)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 23)
    private String status;



    /** 年 */
    @ApiModelProperty(value = "年", position = 2)
    private String year;

    /** 春招|秋招 */
    @ApiModelProperty(value = "春招|秋招", position = 3)
    private String type;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 4)
    private String name;

    /** 电话|手机 */
    @ApiModelProperty(value = "电话|手机", position = 5)
    private String phone;

    /** 学校 */
    @ApiModelProperty(value = "学校", position = 6)
    private String school;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 7)
    private String education;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 8)
    private String major;

    /** 应聘信息来源 */
    @ApiModelProperty(value = "应聘信息来源", position = 9)
    private String infoResource;

    /** 简历附件 */
    @ApiModelProperty(value = "简历附件", position = 10)
    private String resumeAttach;

    /** 岗位类别 */
    @ApiModelProperty(value = "岗位类别", position = 13)
    private String postType;

    /** 应聘岗位 */
    @ApiModelProperty(value = "应聘岗位", position = 14)
    private String applyPost;

    /** 期望薪资 */
    @ApiModelProperty(value = "期望薪资", position = 15)
    private String expectSalary;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 16)
    private String idNumber;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 17)
    private String sex;

}
