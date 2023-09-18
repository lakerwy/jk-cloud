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
package vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 打卡班次配置表Id参数
 *
 * @author ljy
 * @date  2023/09/07 13:07
 **/
@Getter
@Setter
public class HrUserAttendanceConfigIdParam {

    /** 主键id */
    @ApiModelProperty(value = "主键id", required = true)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 考勤班次类别 */
    @ApiModelProperty(value = "考勤班次类别名", position = 13)
    private String attendanceTypeName;

    /** 班次工时，单位：小时 */
    @ApiModelProperty(value = "班次工时，单位：小时", position = 14)
    private String workHours;
}
