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
package vip.xiaonuo.sys.modular.hr.jkAttendance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 打卡机信息表添加参数
 *
 * @author ljy
 * @date  2023/09/08 14:39
 **/
@Getter
@Setter
public class HrAttendanceMachineAddParam {

    /** 打卡机编码 */
    @ApiModelProperty(value = "打卡机编码", position = 2)
    private String code;

    /** 打卡机位置 */
    @ApiModelProperty(value = "打卡机位置", position = 3)
    private String location;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 5)
    private String extJson;

    /** 设备广播的uuid */
    @ApiModelProperty(value = "设备广播的uuid", position = 11)
    private String uuid;

    /** 设备的主ID */
    @ApiModelProperty(value = "设备的主ID", position = 12)
    private String major;

    /** 设备的次ID */
    @ApiModelProperty(value = "设备的次ID", position = 13)
    private String minor;

}
