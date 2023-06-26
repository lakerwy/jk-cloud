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
package vip.xiaonuo.sys.modular.info.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合作伙伴-管理费缴纳情况编辑参数
 *
 * @author mayike
 * @date  2023/06/26 14:07
 **/
@Getter
@Setter
public class PartnerFeeEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 合作伙伴ID */
    @ApiModelProperty(value = "合作伙伴ID", position = 2)
    private String orgId;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 3)
    private Date startTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间", position = 4)
    private Date endTime;

    /** 管理费率 */
    @ApiModelProperty(value = "管理费率", position = 5)
    private String reeRate;

    /** 保底管理费 */
    @ApiModelProperty(value = "保底管理费", position = 6)
    private String lineManageFee;

    /** 已缴纳管理费 */
    @ApiModelProperty(value = "已缴纳管理费", position = 7)
    private String paidManageFee;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

}
