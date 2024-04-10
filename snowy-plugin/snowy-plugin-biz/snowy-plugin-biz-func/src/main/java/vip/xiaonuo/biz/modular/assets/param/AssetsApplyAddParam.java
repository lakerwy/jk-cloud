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
package vip.xiaonuo.biz.modular.assets.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.assets.entity.AssetsBill;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 固资申购 - 申请流程添加参数
 *
 * @author yong.ye
 * @date  2024/04/08 16:17
 **/
@Getter
@Setter
public class AssetsApplyAddParam {

    /** 申请人姓名 */
    @ApiModelProperty(value = "申请人姓名", position = 2)
    private String optName;

    /** 申请部门 */
    @ApiModelProperty(value = "申请部门", position = 2)
    private String orgName;

    /** 申请部门Id */
    @ApiModelProperty(value = "申请部门Id", position = 3)
    private String orgId;

    /** 使用人 */
    @ApiModelProperty(value = "使用人", position = 4)
    private String userName;

    /** 申购类别 */
    @ApiModelProperty(value = "申购类别", position = 5)
    private String type;

    /** 申购事项 */
    @ApiModelProperty(value = "申购事项", position = 6)
    private String items;

    /** 申购处理 */
    @ApiModelProperty(value = "申购处理", position = 7)
    private String handle;

    /** 合计 */
    @ApiModelProperty(value = "合计", position = 8)
    private String totalAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 10)
    private String attach;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 18)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 19)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 20)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 21)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 22)
    private String extJson;

    /** 申购物品清单集合 */
    @ApiModelProperty(value = "申购物品清单集合", position = 100)
    private List<AssetsBill> billList;

}
