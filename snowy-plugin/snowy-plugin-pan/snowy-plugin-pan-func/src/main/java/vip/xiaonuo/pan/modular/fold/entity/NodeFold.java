package vip.xiaonuo.pan.modular.fold.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/4 14:59
 */
@Getter
@Setter
public class NodeFold {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 父目录文件夹ID */
    @ApiModelProperty(value = "父目录文件夹ID", position = 2)
    private String parentId;

    /** 文件名称 */
    @ApiModelProperty(value = "文件名称", position = 3)
    private String fileName;

    @ApiModelProperty(value = "层级",position = 3)
    private Integer level;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 4)
    private Date createTime;

    @ApiModelProperty(value = "子孙节点",position = 5)
    private List<NodeFold> children = new ArrayList<>();

}
