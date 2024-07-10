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
package vip.xiaonuo.oa.modular.attendance.shift.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 日历表编辑参数
 *
 * @author mayike
 * @date  2024/06/15 12:59
 **/
@Getter
@Setter
public class CalendarEditParam {

    /** 日期 */
    @ApiModelProperty(value = "日期", position = 3)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date calendarDate;

    /** 是否为工作日 */
    @ApiModelProperty(value = "是否为工作日", position = 8)
    private Boolean isWorkday;

    /** 节假日名称（如果是节假日） */
    @ApiModelProperty(value = "节假日名称（如果是节假日）", position = 9)
    private String holidayName;

    /** 备注信息 */
    @ApiModelProperty(value = "备注信息", position = 12)
    private String remark;

}
