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
package vip.xiaonuo.biz.modular.car.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CAR-违章记录添加参数
 *
 * @author yong.ye
 * @date  2023/11/08 15:58
 **/
@Getter
@Setter
public class DriverBreaksAddParam {

    /** SN */
    @ApiModelProperty(value = "SN", position = 3)
    private String sn;

    /** 司机姓名 */
    @ApiModelProperty(value = "司机姓名", position = 4)
    private String name;

    /** 违章车辆 */
    @ApiModelProperty(value = "违章车辆", position = 5)
    private String vehicle;

    /** 违章时间 */
    @ApiModelProperty(value = "违章时间", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date breakTime;

    /** 罚款金额 */
    @ApiModelProperty(value = "罚款金额", position = 7)
    private BigDecimal forfeit;

    /** 决定书编号 */
    @ApiModelProperty(value = "决定书编号", position = 8)
    private String decisionNo;

    /** 记分 */
    @ApiModelProperty(value = "记分", position = 9)
    private Integer score;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10)
    private String status;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String note;

    /** 司机UserId */
    @ApiModelProperty(value = "司机UserId", position = 17)
    private String driverUserId;

    /** 附件Id */
    @ApiModelProperty(value = "附件Id", position = 18)
    private String attachId;

    /** 车辆Id */
    @ApiModelProperty(value = "车辆Id", position = 19)
    private String vehicleId;

}
