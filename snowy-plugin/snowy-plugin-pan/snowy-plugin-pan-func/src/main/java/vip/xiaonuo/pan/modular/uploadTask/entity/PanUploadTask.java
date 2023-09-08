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
package vip.xiaonuo.pan.modular.uploadTask.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 云盘-上传任务主表实体
 *
 * @author mayike
 * @date  2023/09/08 16:21
 **/
@Getter
@Setter
@TableName("PAN_UPLOAD_TASK")
public class PanUploadTask {

    /** ID */
    @TableId
    @ApiModelProperty(value = "ID", position = 1)
    private String id;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 2)
    private String tenantId;

    /** 扩展名 */
    @ApiModelProperty(value = "扩展名", position = 3)
    private String extendName;

    /** 文件名 */
    @ApiModelProperty(value = "文件名", position = 4)
    private String fileName;

    /** 文件路径 */
    @ApiModelProperty(value = "文件路径", position = 5)
    private String filePath;

    /** md5唯一标识 */
    @ApiModelProperty(value = "md5唯一标识", position = 6)
    private String identifer;

    /** 上传状态(1-成功,0-失败或未完成) */
    @ApiModelProperty(value = "上传状态(1-成功,0-失败或未完成)", position = 7)
    private Integer uploadStatus;

    /** 上传时间 */
    @ApiModelProperty(value = "上传时间", position = 8)
    private Date uploadTime;

    /** 用户id */
    @ApiModelProperty(value = "用户id", position = 9)
    private Long userId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 10)
    private String extJson;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 11)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
