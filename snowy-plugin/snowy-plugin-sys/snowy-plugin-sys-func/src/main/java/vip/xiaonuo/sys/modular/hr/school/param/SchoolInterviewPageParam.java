package vip.xiaonuo.sys.modular.hr.school.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 校招 - （应聘者）- 面试入职流程查询参数
 *
 * @author yong.ye
 * @date  2024/09/03 10:46
 **/
@Getter
@Setter
public class SchoolInterviewPageParam {

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

    /** 应聘部门Id */
    @ApiModelProperty(value = "应聘部门Id")
    private String deptId;

    /** 应聘部门 */
    @ApiModelProperty(value = "应聘部门")
    private String dept;

    /** 外键：应聘者简历Id */
    @ApiModelProperty(value = "外键：应聘者简历Id")
    private String resumeId;

    /** SN */
    @ApiModelProperty(value = "SN")
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

    /** 年 */
    @ApiModelProperty(value = "年")
    private String year;

    /** 春招|秋招 */
    @ApiModelProperty(value = "春招|秋招")
    private String type;

    /** 学校 */
    @ApiModelProperty(value = "学校")
    private String school;

    /** 学历 */
    @ApiModelProperty(value = "学历")
    private String education;

    /** 应聘信息来源 */
    @ApiModelProperty(value = "应聘信息来源")
    private String infoResource;

    /** 岗位类别 */
    @ApiModelProperty(value = "岗位类别")
    private String postType;
}
