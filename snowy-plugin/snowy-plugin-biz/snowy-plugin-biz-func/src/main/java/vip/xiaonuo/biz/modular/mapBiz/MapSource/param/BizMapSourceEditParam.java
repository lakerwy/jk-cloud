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
package vip.xiaonuo.biz.modular.mapBiz.MapSource.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 图审 - 原始资料录入 编辑参数
 *
 * @author yong.ye
 * @date  2024/06/11 17:36
 **/
@Getter
@Setter
public class BizMapSourceEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 登记人单位：总部|分公司|零星 */
    @ApiModelProperty(value = "登记人单位：总部|分公司|零星", position = 2)
    private String unitType;

    /** 登记人姓名 */
    @ApiModelProperty(value = "登记人姓名", position = 3)
    private String optName;

    /** 合同Id */
    @ApiModelProperty(value = "合同Id", position = 4)
    private String htId;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 5)
    private String htProjectName;

    /** 项目合同编号 */
    @ApiModelProperty(value = "项目合同编号", position = 6)
    private String htContractNo;

    /** 项目负责人 */
    @ApiModelProperty(value = "项目负责人", position = 7)
    private String htOwner;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

    /** 勘察大纲 */
    @ApiModelProperty(value = "勘察大纲", position = 9)
    private String kcdgAttach;

    /** 钻孔平面图 */
    @ApiModelProperty(value = "钻孔平面图", position = 10)
    private String zkpmtAttach;

    /** 劳务队伍进场照片 */
    @ApiModelProperty(value = "劳务队伍进场照片", position = 11)
    private String lwdwjczpAttach;

    /** 现场钻孔视频资料 */
    @ApiModelProperty(value = "现场钻孔视频资料", position = 12)
    private String xczkspzlAttach;

    /** 钻孔岩芯照片 */
    @ApiModelProperty(value = "钻孔岩芯照片", position = 13)
    private String zkyxtpAttach;

    /** 土木实验报告 */
    @ApiModelProperty(value = "土木实验报告", position = 14)
    private String tmsybgAttach;

    /** 勘察报告 */
    @ApiModelProperty(value = "勘察报告", position = 15)
    private String kcbgAttach;

    /** 项目其他资料 */
    @ApiModelProperty(value = "项目其他资料", position = 16)
    private String xmqtzlAttach;

}
