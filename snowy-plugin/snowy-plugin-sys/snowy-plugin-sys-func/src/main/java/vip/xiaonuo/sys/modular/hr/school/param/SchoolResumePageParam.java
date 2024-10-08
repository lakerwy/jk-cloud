package vip.xiaonuo.sys.modular.hr.school.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 校招 - （应聘者）简历库信息查询参数
 *
 * @author yong.ye
 * @date  2024/09/03 10:44
 **/
@Getter
@Setter
public class SchoolResumePageParam {

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

    /** 年 */
    @ApiModelProperty(value = "年")
    private String year;

    /** 春招|秋招 */
    @ApiModelProperty(value = "春招|秋招")
    private String type;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name;

    /** 电话|手机 */
    @ApiModelProperty(value = "电话|手机")
    private String phone;

    /** 学校 */
    @ApiModelProperty(value = "学校")
    private String school;

    /** 学历 */
    @ApiModelProperty(value = "学历")
    private String education;

    /** 专业 */
    @ApiModelProperty(value = "专业")
    private String major;

    /** 应聘信息来源 */
    @ApiModelProperty(value = "应聘信息来源")
    private String infoResource;

    /** 岗位类别 */
    @ApiModelProperty(value = "岗位类别")
    private String postType;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idNumber;

    /** 性别 */
    @ApiModelProperty(value = "性别")
    private String sex;

    /** 所处阶段 */
    @ApiModelProperty(value = "所处阶段")
    private String resumeStage;

    /** 是否发送短信：0：否 1：是 */
    @ApiModelProperty(value = "是否发送短信：0：否 1：是")
    private String isSend;

    /** 简历状态 0 未审核 1 合格 -1 不合格*/
    @ApiModelProperty(value = "简历状态 0 未审核 1 合格 -1 不合格")
    private String resumeStatus;

    /** 开始 */
    @ApiModelProperty(value = "开始")
    private String startDate;

    /** 结束 */
    @ApiModelProperty(value = "结束")
    private String endDate;

}
