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
package vip.xiaonuo.biz.modular.sealMessage.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 印章基础信息表添加参数
 *
 * @author hxw
 * @date  2024/05/17 08:55
 **/
@Getter
@Setter
public class SealMessageAddParam {

    /** 用印标题 */
    @ApiModelProperty(value = "用印标题", position = 2)
    private String title;

    /** 用印部门 */
    @ApiModelProperty(value = "用印部门", position = 2)
    private String sealDept;

    /** 用印人 */
    @ApiModelProperty(value = "用印人", position = 3)
    private String sealPerson;

    /** 用印日期 */
    @ApiModelProperty(value = "用印日期", position = 4)
    private Date sealTime;

    /** 用印名称 */
    @ApiModelProperty(value = "用印名称", position = 5)
    private String sealName;

    /** 用印份数 */
    @ApiModelProperty(value = "用印份数", position = 6)
    private String sealPage;

    /** 用印主体 */
    @ApiModelProperty(value = "用印主体", position = 7)
    private String sealObj;

    /** 用印事项 */
    @ApiModelProperty(value = "用印事项", position = 8)
    private String sealItem;

    /** 内容摘要 */
    @ApiModelProperty(value = "内容摘要", position = 9)
    private String content;

    /** 用印要求 */
    @ApiModelProperty(value = "用印要求", position = 10)
    private String demand;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 11)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 19)
    private String dataScopeId;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 20)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 21)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 22)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 23)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 24)
    @NotBlank(message = "入参信息不能为空")
    private String extJson;
}
