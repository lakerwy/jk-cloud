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
package vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 考勤班次组配置表Id参数
 *
 * @author ljy
 * @date  2023/09/21 15:55
 **/
@Getter
@Setter
public class HrAttendanceConfigAndUserParam {

    /** 考勤组ID */
    @ApiModelProperty(value = "考勤组ID")
    private String attendanceTypeId;


    /** 当前的人或者部门是否生效这个考勤班次：0 -- 生效，1 未生效 */
    @ApiModelProperty(value = "当前的人或者部门是否生效这个考勤班次：0生效, 1未生效", position = 16)
    private String isValid;
}
