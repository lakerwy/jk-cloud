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
import vip.xiaonuo.pan.core.RegexConstant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 文件主表添加参数
 *
 * @author mayike
 * @date  2023/08/28 10:09
 **/
@Getter
@Setter
public class UserFileAddParam {

    /** 父目录文件夹ID */
    @ApiModelProperty(value = "父目录文件夹ID",  position = 2)
    @NotBlank(message = "父目录文件夹ID ---根目录为0")
    private String parentId;

    /** 文件名称 */
    @ApiModelProperty(value = "文件名称", required = true, position = 3)
    @NotBlank(message = "文件名不能为空")
    @Pattern(regexp = RegexConstant.FILE_NAME_REGEX, message = "文件名不合法！", flags = {Pattern.Flag.CASE_INSENSITIVE})
    private String fileName;

    /** 文件路径 */
    @ApiModelProperty(value = "文件路径",required = true, position = 4)
    @NotBlank(message = "文件路径不能为空")
    private String filePath;

    /** 是否是目录(0-否,1-是) */
//    @ApiModelProperty(value = "是否是目录(0-否,1-是)", position = 5)
//    private Integer isDir;

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
    @ApiModelProperty(value = "文件夹类型(0 个人文件夹 1、群组文件夹)", required = true,position = 16)
    private Integer foldType;

    /** 文件拓展名 */
    @ApiModelProperty(value = "文件拓展名 创建文件时必传", position = 16)
    private String extendName;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 16)
    private String userId;

}
