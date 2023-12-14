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
package vip.xiaonuo.biz.modular.cad.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * CAD-文件历史版本记录表实体
 *
 * @author yong.ye
 * @date  2023/12/01 15:34
 **/
@Getter
@Setter
@TableName("BIZ_CAD_FILE_HISTORY")
public class CadFileHistory {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户Id */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /** 外键：文件Id -> BIZ_CAD_FILE.ID */
    @ApiModelProperty(value = "外键：文件Id -> BIZ_CAD_FILE.ID", position = 3)
    private String fileId;

    /** 版本号 */
    @ApiModelProperty(value = "版本号", position = 4)
    private Integer version;

    /** 文件大小 */
    @ApiModelProperty(value = "文件大小", position = 5)
    private Long fileSize;

    /** 是否重要版本 */
    @ApiModelProperty(value = "是否重要版本", position = 6)
    private String isImportant;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 7)
    private String status;

    /** 下载地址 */
    @ApiModelProperty(value = "下载地址", position = 8)
    private String downLoadUrl;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /** 文件名称 */
    @ApiModelProperty(value = "文件名称", position = 9)
    private String fileName;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 10)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 操作人 */
    @ApiModelProperty(value = "操作人", position = 7)
    private String oprUser;

    /** 操作时间 */
    @ApiModelProperty(value = "操作时间", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date oprTime;
}
