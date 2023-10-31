package vip.xiaonuo.pan.modular.commonFile.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/9/11 16:18
 */

@Getter
@Setter
public class CommonFileVo {

    /** CommonFile.Id 共享主键id */
    @ApiModelProperty(value = "CommonFile.Id 共享主键id", position = 1)
    private String id;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 3)
    private String userId;

    /** 用户名称 */
    @ApiModelProperty(value = "用户名称", position = 3)
    private String userName;

    /** 用户文件Id */
    @ApiModelProperty(value = "用户文件Id", position = 4)
    private String userFileId;

    /** 用户对文件的权限码 */
    @ApiModelProperty(value = "用户对文件的权限码", position = 4)
    private Long filePermissionCode;





    /** UserFile信息 文件名 */
    @ApiModelProperty(value = "文件名", position = 2)
    private String fileName;

    /** 父目录文件夹ID */
    @ApiModelProperty(value = "父目录文件夹ID", position = 2)
    private String parentId;

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

    /** 文件夹锁定表示 */
    @ApiModelProperty(value = "文件夹锁定标识", position = 15)
    private Integer lockFlag;

    /** 文件夹类型 */
    @ApiModelProperty(value = "文件夹类型(0 个人文件夹 1、群组文件夹)", position = 16)
    private Integer foldType;

    /** 文件拓展名 */
    @ApiModelProperty(value = "文件拓展名", position = 16)
    private String extendName;

    /** 文件大小 */
    @ApiModelProperty(value = "文件大小", position = 18)
    private Long fileSize;

    @ApiModelProperty(value = "删除批次号",position = 19)
    private String deleteBatchNum;

    /** 删除时间 */
    @ApiModelProperty(value = "删除时间", position = 20)
    private Date deleteTime;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 5)
    private String deleteFlag;

}
