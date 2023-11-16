package vip.xiaonuo.biz.modular.school.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReportPageParam {
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

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别")
    private String sex;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /** 学校 */
    @ApiModelProperty(value = "学校")
    private String school;

    /** 专业 */
    @ApiModelProperty(value = "专业")
    private String subject;

    /** 岗位类别 */
    @ApiModelProperty(value = "岗位类别")
    private String positionType;

    /** 信息来源 */
    @ApiModelProperty(value = "信息来源")
    private String infoSource;

    /** 简历状态 */
    @ApiModelProperty(value = "简历状态")
    private String status;

    /** 学历 */
    @ApiModelProperty(value = "学历")
    private Integer academic;

    /** 创建时间 - 起 */
    @ApiModelProperty(value = "创建时间 - 起")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime ;

    /** 创建时间 - 止 */
    @ApiModelProperty(value = "创建时间 - 止")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime ;

}
