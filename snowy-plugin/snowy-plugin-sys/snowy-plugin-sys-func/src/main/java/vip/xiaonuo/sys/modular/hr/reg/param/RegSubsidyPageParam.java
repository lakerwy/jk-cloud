package vip.xiaonuo.sys.modular.hr.reg.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 注册补贴（新版）-审批流查询参数
 *
 * @author yong.ye
 * @date  2025/01/15 09:07
 **/
@Getter
@Setter
public class RegSubsidyPageParam {

    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 申请人 */
    @ApiModelProperty(value = "申请人")
    private String name;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idCardNumber;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门")
    private String firstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门")
    private String secondOrgName;

    /** 入职时间开始 */
    @ApiModelProperty(value = "入职时间开始")
    private String startHireDate;

    /** 入职时间结束 */
    @ApiModelProperty(value = "入职时间结束")
    private String endHireDate;

    /** 毕业院校 */
    @ApiModelProperty(value = "毕业院校")
    private String college;

    /** 专业 */
    @ApiModelProperty(value = "专业")
    private String major;

    /** 职称等级 */
    @ApiModelProperty(value = "职称等级")
    private String certLevel;

    /** 职称专业 */
    @ApiModelProperty(value = "职称专业")
    private String certMajor;

    /** SN */
    @ApiModelProperty(value = "SN")
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

}
