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
package vip.xiaonuo.sys.modular.hr.registerCert.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 注册补贴-人力资源中心证书添加参数
 *
 * @author ljy
 * @date  2024/03/27 15:30
 **/
@Getter
@Setter
public class RegCertStatusDataAddParam {

    /** 注册补贴-申请ID */
    @ApiModelProperty(value = "注册补贴-申请ID", position = 2)
    private String applyRegCertdId;

    /** 证书等级 */
    @ApiModelProperty(value = "证书等级", position = 3)
    private String certLever;

    /** 证书类别 */
    @ApiModelProperty(value = "证书类别", position = 4)
    private String certType;

    /** 证书专业 */
    @ApiModelProperty(value = "证书专业", position = 5)
    private String certSubject;

    /** 是否参与项目 */
    @ApiModelProperty(value = "是否参与项目", position = 6)
    private String isJoinPrj;

    /** 补贴标准 */
    @ApiModelProperty(value = "补贴标准", position = 7)
    private String certAlwStd;

    /** 系数 */
    @ApiModelProperty(value = "系数", position = 8)
    private String certRate;

    /** 补贴金额 */
    @ApiModelProperty(value = "补贴金额", position = 9)
    private String certAlwAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String certMemo;

    /** 是否审核通过 */
    @ApiModelProperty(value = "是否审核通过", position = 11)
    private String isPass;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 12)
    private String idCardNumber;

}
