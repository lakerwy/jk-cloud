package vip.xiaonuo.pan.modular.recoveryfile.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/9/11 9:16
 */

@Getter
@Setter
public class RecoveryFileVo {

    /** PanRecoveryFile 信息 **/
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户Id */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /** 用户文件Id */
    @ApiModelProperty(value = "用户文件Id", position = 3)
    private String userFileId;

    /** 删除时间 */
    @ApiModelProperty(value = "删除时间", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deleteTime;

    /** 删除批次号 */
    @ApiModelProperty(value = "删除批次号", position = 5)
    private String deleteBatchNum;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 6)
    private String memo;


    /*************** UserFile表数据 ***************/

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

    /*************** PanFile表数据 ***************/

    /** 文件大小 */
    @ApiModelProperty(value = "文件大小", position = 2)
    private Long fileSize;

    /** 文件状态(0-失效，1-生效) */
    @ApiModelProperty(value = "文件状态(0-失效，1-生效)", position = 3)
    private Integer fileStatus;

    /** 文件url */
    @ApiModelProperty(value = "文件url", position = 4)
    private String fileUrl;

    /** md5唯一标识 */
    @ApiModelProperty(value = "md5唯一标识", position = 5)
    private String identifier;

    /** 存储类型 */
    @ApiModelProperty(value = "存储类型", position = 6)
    private String storageType;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 7)
    private String extJson;

    /** 文件后缀 */
    @ApiModelProperty(value = "文件后缀", position = 13)
    private String suffix;
}
