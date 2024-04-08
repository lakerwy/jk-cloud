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
package vip.xiaonuo.pan.modular.userFile.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 文件主表编辑参数
 *
 * @author mayike
 * @date  2023/08/28 10:09
 **/
@Getter
@Setter
public class UserFileEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 父目录文件夹ID */
    @ApiModelProperty(value = "父目录文件夹ID", position = 2)
    //@NotBlank(message = "父目录文件夹ID不能为空")
    private String parentId;

    /** 文件名称 */
    @ApiModelProperty(value = "文件名称", position = 3)
    private String fileName;

    /** 文件路径 */
    @ApiModelProperty(value = "文件路径", position = 4)
    private String filePath;

    /** 是否是目录(0-否,1-是) */
    @ApiModelProperty(value = "是否是目录(0-否,1-是)", position = 5)
    private Integer isDir;

    /** 文件的ID（对应DEV_FILE表的主键ID） */
    @ApiModelProperty(value = "文件的ID（对应DEV_FILE表的主键ID）", position = 6)
    private String fileId;

    /** 权限值 */
    @ApiModelProperty(value = "权限值", position = 7)
    private String right;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 8)
    private String dataScopeId;

    /** 文件夹类型 */
    @ApiModelProperty(value = "文件夹类型", position = 16)
    private Integer foldType;

    /** 文件拓展名 */
    @ApiModelProperty(value = "文件拓展名", position = 16)
    private String extendName;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 16)
    private String userId;

    /** 文件负者人（管理者） */
    @ApiModelProperty(value = "文件负者人（管理者）", position = 20)
    private String fileManager;

    @ApiModelProperty(value = "权限校验类型：GROUP 群组  COMMON 共享  注：（不传或不在指定范围） 不做权限校验")
    private String rightType;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 21)
    private String memo;
}
