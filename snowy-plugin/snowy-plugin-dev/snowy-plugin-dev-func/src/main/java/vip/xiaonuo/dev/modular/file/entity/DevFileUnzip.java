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
package vip.xiaonuo.dev.modular.file.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 文件-附件压缩包解压列表实体
 *
 * @author ljy
 * @date  2023/12/24 20:09
 **/
@Getter
@Setter
@TableName("DEV_FILE_UNZIP")
public class DevFileUnzip {

    /** ID */
    @TableId
    @ApiModelProperty(value = "ID", position = 1)
    private String id;

    /** 压缩包附件父类ID */
    @ApiModelProperty(value = "压缩包附件父类ID", position = 2)
    private String parentId;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 3)
    private String tenantId;

    /** 存储引擎 */
    @ApiModelProperty(value = "存储引擎", position = 4)
    private String engine;

    /** 存储桶 */
    @ApiModelProperty(value = "存储桶", position = 5)
    private String bucket;

    /** 文件名称 */
    @ApiModelProperty(value = "文件名称", position = 6)
    private String name;

    /** 文件后缀 */
    @ApiModelProperty(value = "文件后缀", position = 7)
    private String suffix;

    /** 文件大小kb */
    @ApiModelProperty(value = "文件大小kb", position = 8)
    private Long sizeKb;

    /** 文件大小（格式化后） */
    @ApiModelProperty(value = "文件大小（格式化后）", position = 9)
    private String sizeInfo;

    /** 文件的对象名（唯一名称） */
    @ApiModelProperty(value = "文件的对象名（唯一名称）", position = 10)
    private String objName;

    /** 文件存储路径 */
    @ApiModelProperty(value = "文件存储路径", position = 11)
    private String storagePath;

    /** 文件下载路径 */
    @ApiModelProperty(value = "文件下载路径", position = 12)
    private String downloadPath;

    /** 图片缩略图 */
    @ApiModelProperty(value = "图片缩略图", position = 13)
    private String thumbnail;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 14)
    private String extJson;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 15)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 17)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 18)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 19)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 操作人 */
    @ApiModelProperty(value = "操作人", position = 20)
    private String owneraccount;

    /** 是否是目录(0-否,1-是) */
    @ApiModelProperty(value = "是否是目录(0-否,1-是)", position = 21)
    private Integer isDir;

    /**是否带后缀名*/
    @ApiModelProperty(value = "是否带后缀名", position = 22)
    @TableField(exist = false)
    private Boolean isSuffix;

}
