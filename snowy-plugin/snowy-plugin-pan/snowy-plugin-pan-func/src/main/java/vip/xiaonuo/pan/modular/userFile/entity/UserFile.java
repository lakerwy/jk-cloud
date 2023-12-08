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
package vip.xiaonuo.pan.modular.userFile.entity;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.pan.modular.fileCenter.util.CommonFile;

import java.util.Date;

/**
 * 文件主表实体
 *
 * @author mayike
 * @date  2023/08/28 10:09
 **/
@Getter
@Setter
@TableName("PAN_USER_FILE")
public class UserFile {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户Id */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /** 父目录文件夹ID */
    @ApiModelProperty(value = "父目录文件夹ID", position = 2,required = true)
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

    /** 是否是目录(0-否,1-是) */
    @ApiModelProperty(value = "根目录标记(0顶级目录 1、个人顶级目录 2 、部门顶级目录)", position = 7)
    private Integer rootDir;

    /** 文件所属组织ID */
    @ApiModelProperty(value = "文件所属组织ID", position = 20)
    private String orgId;

    /** 权限值 */
    @ApiModelProperty(value = "权限值", position = 8)
    @TableField("`right`")
    private String right;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 9)
    private String dataScopeId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 10)
    @TableLogic
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 文件夹锁定表示 */
    @ApiModelProperty(value = "文件夹锁定标识", position = 15)
    private Integer lockFlag;

    /** 文件夹类型 */
    @ApiModelProperty(value = "文件夹类型(0 个人文件夹 1、群组文件夹)", position = 16)
    private Integer foldType;

    /** 文件拓展名 */
    @ApiModelProperty(value = "文件拓展名", position = 16)
    private String extendName;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 17)
    private String userId;

    /** 文件大小 */
    @ApiModelProperty(value = "文件大小", position = 18)
    private Long fileSize;


    public boolean isDirectory() {
        return this.isDir == 1;
    }

    @ApiModelProperty(value = "删除批次号",position = 19)
    private String deleteBatchNum;

    /** 删除时间 */
    @ApiModelProperty(value = "删除时间", position = 20)
    private Date deleteTime;

    /** 文件负者人（管理者） 多个用英文逗号分割 */
    @ApiModelProperty(value = "文件负者人（管理者）多个用英文逗号分割", position = 20)
    private String fileManager;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 21)
    private String memo;

    public boolean isFile() {
        return this.isDir == 0;
    }

    public UserFile(CommonFile commonFile, String userId, String fileId) {
        this.userId = userId;
        this.fileId = fileId;
        this.filePath = commonFile.getParent();
        this.fileName = commonFile.getNameNotExtend();
        this.extendName = commonFile.getExtendName();
        this.isDir = commonFile.isDirectory() ? 1 : 0;
        this.orgId = StpLoginUserUtil.getLoginUser().getOrgId();
    }

    public UserFile() {
    }
}
