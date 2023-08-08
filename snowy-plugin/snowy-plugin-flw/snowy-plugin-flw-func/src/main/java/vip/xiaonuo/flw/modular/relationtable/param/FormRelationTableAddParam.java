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
package vip.xiaonuo.flw.modular.relationtable.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 表单与表的关系添加参数
 *
 * @author mayike
 * @date  2023/06/08 11:20
 **/
@Getter
@Setter
public class FormRelationTableAddParam {

    /** 流程id */
    @ApiModelProperty(value = "流程id", position = 2)
    private String actModelId;

    /** 数据库表名 */
    @ApiModelProperty(value = "数据库表名", position = 3)
    private String tableName;

    /** 定义id */
    @ApiModelProperty(value = "定义id", position = 5)
    private String definitionId;

    /** 部署id */
    @ApiModelProperty(value = "部署id", position = 6)
    private String deploymentId;

    /** 租户id */
    @ApiModelProperty(value = "系统表单路由地址", position = 7)
    private String formUrl;

    @ApiModelProperty(value = "流程实例id", position = 8)
    private String processInstanceId;

    /** 数据库表名业务表 (1:n  针对1个流程多条业务数据的情况，用于流程中处理) */
    @ApiModelProperty(value = "数据库表名业务表 (1:n  针对1个流程多条业务数据的情况，用于流程中处理) ", position = 9)
    private String bizTableName;

}
