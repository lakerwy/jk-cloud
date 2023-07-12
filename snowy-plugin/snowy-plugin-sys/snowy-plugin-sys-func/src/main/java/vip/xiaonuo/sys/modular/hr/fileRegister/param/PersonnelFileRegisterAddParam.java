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
package vip.xiaonuo.sys.modular.hr.fileRegister.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表- 签约信息添加参数
 *
 * @author mayike
 * @date  2023/07/12 10:55
 **/
@Getter
@Setter
public class PersonnelFileRegisterAddParam {

    /** 档案号 */
    @ApiModelProperty(value = "档案号", position = 2)
    private String fileId;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 3)
    private String name;

    /** 起始时间 */
    @ApiModelProperty(value = "起始时间", position = 4)
    private Date startDate;

    /** 到期时间 */
    @ApiModelProperty(value = "到期时间", position = 5)
    private Date expireDate;

    /** 签约单位 */
    @ApiModelProperty(value = "签约单位", position = 6)
    private String signUnit;

    /** 合同附件 */
    @ApiModelProperty(value = "合同附件", position = 7)
    private String attachments;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 8)
    private String extJson;

}
