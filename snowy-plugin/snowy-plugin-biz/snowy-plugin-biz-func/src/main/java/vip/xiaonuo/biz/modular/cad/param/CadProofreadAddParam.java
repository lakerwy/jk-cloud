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
package vip.xiaonuo.biz.modular.cad.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CAD-提资|校审记录表添加参数
 *
 * @author yong.ye
 * @date  2023/12/01 15:36
 **/
@Getter
@Setter
public class CadProofreadAddParam {
    /** 外键：项目Id -> BIZ_CAD_PROJECT.ID */
    @ApiModelProperty(value = "外键：项目Id -> BIZ_CAD_PROJECT.ID", position = 6)
    private String prjId;

    /** 外键：阶段Id -> BIZ_CAD_STAGE.ID */
    @ApiModelProperty(value = "外键：阶段Id -> BIZ_CAD_STAGE.ID", position = 7)
    private String stageId;

    /** 外键：子项Id -> BIZ_CAD_SUB_ITEM.ID */
    @ApiModelProperty(value = "外键：子项Id -> BIZ_CAD_SUB_ITEM.ID", position = 3)
    private String subItemId;

    /** 外键：cad专业Id -> BIZ_CAD_PROFESSION.ID */
    @ApiModelProperty(value = "外键：cad专业Id -> BIZ_CAD_PROFESSION.ID", position = 4)
    private String professionId;

    /** 外键：提资|成品成果文件Id -> BIZ_CAD_STORAGE.ID */
    @ApiModelProperty(value = "外键：文件版本Id -> BIZ_CAD_STORAGE.ID", position = 5)
    private String storageId;

    /** 审批类型：提资|校审 */
    @ApiModelProperty(value = "审批类型：提资|校审", position = 6)
    private String type;

    /** 状态：校审|校对|审批 - 通过|退回 */
    @ApiModelProperty(value = "状态：校审|校对|审批 - 通过|退回", position = 7)
    private String status;

    /** 校审人 */
    @ApiModelProperty(value = "校审人", position = 8)
    private String auditor;

    /** 校审时间 */
    @ApiModelProperty(value = "校审时间", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

}
