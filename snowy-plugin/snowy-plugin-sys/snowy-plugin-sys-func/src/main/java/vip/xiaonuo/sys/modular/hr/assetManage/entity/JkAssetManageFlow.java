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
package vip.xiaonuo.sys.modular.hr.assetManage.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 建科资产管理权限申请表实体
 *
 * @author ljy
 * @date  2024/04/10 08:56
 **/
@Getter
@Setter
@TableName("JK_ASSET_MANAGE_FLOW")
public class JkAssetManageFlow {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 申请人ID */
    @ApiModelProperty(value = "申请人ID", position = 2)
    private String userId;

    /** 申请的资产ID */
    @ApiModelProperty(value = "申请的资产ID", position = 3)
    private String assetId;

    /** 模型ID */
    @ApiModelProperty(value = "模型ID", position = 4)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 5)
    private String processInstanceId;

    /** SN单号 */
    @ApiModelProperty(value = "SN单号", position = 6)
    private String sn;

    /** 流程状态：是否审核通过 */
    @ApiModelProperty(value = "流程状态：是否审核通过", position = 7)
    private String status;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 8)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 资产数据 */
    @ApiModelProperty(value = "资产数据", position = 13)
    @TableField(exist = false)
    private JkAssetManage jkAssetManage;
}
