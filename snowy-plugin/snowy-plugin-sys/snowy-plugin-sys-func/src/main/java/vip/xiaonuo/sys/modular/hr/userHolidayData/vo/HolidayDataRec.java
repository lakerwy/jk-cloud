package vip.xiaonuo.sys.modular.hr.userHolidayData.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HolidayDataRec {
    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 2)
    private String tenantId;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 3)
    private String userId;

    /** 用户姓名 */
    @ApiModelProperty(value = "用户姓名", position = 4)
    private String userName;

    /** 假期类别：事假 1，病假 2，婚假 3，产假 4，丧假 5，年假 6，调休 7，出差 8 */
    @ApiModelProperty(value = "假期类别：事假 1，病假 2，婚假 3，产假 4，丧假 5，年假 6，调休 7，出差 8", position = 5)
    private Integer applyType;

    /** 理由 */
    @ApiModelProperty(value = "理由", position = 6)
    private String reason;

    /** 详细内容 */
    @ApiModelProperty(value = "详细内容", position = 7)
    private String detail;

    /** 天数 */
    @ApiModelProperty(value = "增减天数", position = 7)
    private String detailDay;

    /** 有效时间 */
    @ApiModelProperty(value = "有效时间", position = 8)
    private String validTime;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 9)
    private String beginTime;

    /** 开始时间 */
    @ApiModelProperty(value = "结束时间", position = 10)
    private String endTime;

    /**状态*/
    @ApiModelProperty(value = "状态：1 正，2 负", position = 11)
    @TableField(exist = false)
    private Integer status;

    /**状态*/
    @ApiModelProperty(value = "创建时间", position = 12)
    private Date createTime;
}
