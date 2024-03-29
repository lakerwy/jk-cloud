package vip.xiaonuo.sys.modular.hr.jkAttendance.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Setter
@Getter
public class UserWorkOtVo {
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

    /** 申请人ID */
    @ApiModelProperty(value = "申请人ID",position = 1)
    private String applyUserId;

    /** 申请人名称 */
    @ApiModelProperty(value = "申请人名称", position = 2)
    private String applyUserName;

    /** 加班时长单位：小时 */
    @ApiModelProperty(value = "加班时长单位：小时",position = 3)
    private Double workHours;

    /** 加班时长单位：小时 */
    @ApiModelProperty(value = "加班时长单位：天",position = 4)
    private Double workDays;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", required = true,position = 5)
    @NotBlank(message = "开始时间不能为空")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyBeginTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间", required = true,position = 6)
    @NotBlank(message = "结束时间不能为空")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyEndTime;

}
