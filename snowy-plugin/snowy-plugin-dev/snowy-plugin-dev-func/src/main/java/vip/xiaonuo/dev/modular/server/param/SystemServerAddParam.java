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
package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统服务表添加参数
 *
 * @author ljy
 * @date  2023/12/29 11:31
 **/
@Getter
@Setter
public class SystemServerAddParam {

    /** 系统名称 */
    @ApiModelProperty(value = "系统名称", position = 3)
    private String systemName;

    /** 系统类别 */
    @ApiModelProperty(value = "系统类别", position = 4)
    private Integer systemType;

    /** 系统来源 */
    @ApiModelProperty(value = "系统来源", position = 5)
    private String systemSource;

    /** 系统来源类别：合同等 */
    @ApiModelProperty(value = "系统来源类别：合同等", position = 6)
    private Integer systemSourceTable;

    /** 系统来源ID */
    @ApiModelProperty(value = "系统来源ID", position = 7)
    private String systemSourceId;

    /** 系统地址：ip+端口 */
    @ApiModelProperty(value = "系统地址：ip+端口", position = 8)
    private String systemUrl;

    /** 系统域名 */
    @ApiModelProperty(value = "系统域名", position = 9)
    private String systemDomain;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String remark;

    /** EPC单体服务部署状态 */
    @ApiModelProperty(value = "EPC单体服务部署状态", position = 11)
    private Integer systemStatus;

    /** 项目参与人 PARTER*/
    @ApiModelProperty(value = "项目参与人,英文逗号隔开", position = 12)
    private String parter;

    /** 行业分类 */
    @ApiModelProperty(value = "行业分类", position = 13)
    private String industrySort;

    /** 项目类型 */
    @ApiModelProperty(value = "项目类型", position = 14)
    private String projectType;

}
