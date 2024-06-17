/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.oa.modular.attendance.calendar.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 日历表添加参数
 *
 * @author mayike
 * @date  2024/06/15 12:59
 **/
@Getter
@Setter
public class CalendarAddParam {

    /** 日期 */
    @ApiModelProperty(value = "日期", position = 3)
    private Date calendarDate;

    /** 年份 */
    @ApiModelProperty(value = "年份", position = 4)
    private Integer year;

    /** 月份 */
    @ApiModelProperty(value = "月份", position = 5)
    private Integer month;

    /** 月份中的天数 */
    @ApiModelProperty(value = "月份中的天数", position = 6)
    private Integer dayOfMonth;

    /** 星期几（1=星期日, 2=星期一, ..., 7=星期六） */
    @ApiModelProperty(value = "星期几（1=星期日, 2=星期一, ..., 7=星期六）", position = 7)
    private Integer dayOfWeek;

    /** 是否为工作日 */
    @ApiModelProperty(value = "是否为工作日", position = 8)
    private Boolean isWorkday;

    /** 节假日名称（如果是节假日） */
    @ApiModelProperty(value = "节假日名称（如果是节假日）", position = 9)
    private String holidayName;

    /** 年度中的第几周 */
    @ApiModelProperty(value = "年度中的第几周", position = 10)
    private Integer weekOfYear;

    /** 季度 */
    @ApiModelProperty(value = "季度", position = 11)
    private Integer quarter;

}
