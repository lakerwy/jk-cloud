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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 打卡班次配置表编辑参数
 *
 * @author ljy
 * @date  2023/09/07 13:07
 **/
@Getter
@Setter
public class HrUserAttendanceConfigEditParam {

    /** 主键id */
    @ApiModelProperty(value = "主键id", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 排班顺序 */
    @ApiModelProperty(value = "排班顺序", position = 3)
    private Integer orderNumber;

    /** 上班允许最早多少时间打卡：单位分钟 */
    @ApiModelProperty(value = "上班允许最早多少时间打卡：单位分钟", position = 4)
    private String onDutyBeginMin;

    /** 上班打卡时间 */
    @ApiModelProperty(value = "上班打卡时间", position = 5)
    private Date onDutyCheckTime;

    /** 上班允许最晚多少时间打卡：单位分钟 */
    @ApiModelProperty(value = "上班允许最晚多少时间打卡：单位分钟", position = 6)
    private String onDutyEndMin;

    /** 下班允许最早多少时间打卡：单位分钟 */
    @ApiModelProperty(value = "下班允许最早多少时间打卡：单位分钟", position = 7)
    private String offDutyBeginMin;

    /** 下班打卡时间 */
    @ApiModelProperty(value = "下班打卡时间", position = 8)
    private Date offDutyCheckTime;

    /** 下班允许最晚多少时间打卡：单位分钟 */
    @ApiModelProperty(value = "下班允许最晚多少时间打卡：单位分钟", position = 9)
    private String offDutyEndMin;

    /** 作用范围区域  */
    @ApiModelProperty(value = "作用范围区域 ", position = 10)
    private String actionRange;

    /** 区域范围是否有效 */
    @ApiModelProperty(value = "区域范围是否有效", position = 11)
    private Integer isValidRange;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 12)
    private String extJson;

}
