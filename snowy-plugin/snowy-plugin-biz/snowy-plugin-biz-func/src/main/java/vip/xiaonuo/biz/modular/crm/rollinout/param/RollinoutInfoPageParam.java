package vip.xiaonuo.biz.modular.crm.rollinout.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/7/3 17:47
 */

@Getter
@Setter
public class RollinoutInfoPageParam {

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

    /** 合作伙伴人员信息Id主键（历史表RollInOut.FPID） */
    @ApiModelProperty(value = "合作伙伴人员信息Id主键（历史表RollInOut.FPID）")
    private String fpId;

    /** 流水号SN（历史表RollInOut.SN） */
    @ApiModelProperty(value = "流水号SN（历史表RollInOut.SN）")
    private String sn;

    /** 登记人（历史表RollInOut.Opt） */
    @ApiModelProperty(value = "登记人（历史表RollInOut.Opt）")
    private String opt;

    /** 登记日期（历史表RollInOut.OptDate）开始 */
    @ApiModelProperty(value = "登记日期（历史表RollInOut.OptDate）开始")
    private String startOptDate;

    /** 登记日期（历史表RollInOut.OptDate）结束 */
    @ApiModelProperty(value = "登记日期（历史表RollInOut.OptDate）结束")
    private String endOptDate;

    /** 操作（历史表RollInOut.Operate） */
    @ApiModelProperty(value = "操作（历史表RollInOut.Operate）")
    private String operate;

    /** 姓名（历史表RollInOut.Name） */
    @ApiModelProperty(value = "姓名（历史表RollInOut.Name）")
    private String name;

    /** 部门（历史表RollInOut.Dept） */
    @ApiModelProperty(value = "部门（历史表RollInOut.Dept）")
    private String dept;

    /** 职称（历史表RollInOut.Rank） */
    @ApiModelProperty(value = "职称（历史表RollInOut.Rank）")
    private String rank;

    /** 人员-学历 */
    @ApiModelProperty(value = "人员-学历")
    private String FPEducation;

    /** 人员-身份证号 */
    @ApiModelProperty(value = "人员-身份证号")
    private String FPIDNumber;
}
