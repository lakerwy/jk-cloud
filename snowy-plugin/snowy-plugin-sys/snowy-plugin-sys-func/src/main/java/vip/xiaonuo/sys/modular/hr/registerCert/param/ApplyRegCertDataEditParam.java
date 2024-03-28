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
 * 注册补贴-申请基本信息编辑参数
 *
 * @author ljy
 * @date  2024/03/27 15:28
 **/
@Getter
@Setter
public class ApplyRegCertDataEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 注册等级 */
    @ApiModelProperty(value = "注册等级", position = 2)
    private String registerLever;

    /** 注册类别 */
    @ApiModelProperty(value = "注册类别", position = 3)
    private String registerType;

    /** 注册专业 */
    @ApiModelProperty(value = "注册专业", position = 4)
    private String registerSubject;

    /** 申请事项 */
    @ApiModelProperty(value = "申请事项", position = 5)
    private String aplyItem;

    /** 是否正常 */
    @ApiModelProperty(value = "是否正常", position = 6)
    private String isFlag;

    /** 注册成功日期 */
    @ApiModelProperty(value = "注册成功日期", position = 7)
    private Date registerSucDate;

    /** 参与项目名称 */
    @ApiModelProperty(value = "参与项目名称", position = 8)
    private String joinPrjName;

    /** 参与项目日期 */
    @ApiModelProperty(value = "参与项目日期", position = 9)
    private Date joinPrjDate;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 10)
    private String status;

    /** 流程实例ID */
    @ApiModelProperty(value = "流程实例ID", position = 11)
    private String processInstanceId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 12)
    private String extJson;

}
