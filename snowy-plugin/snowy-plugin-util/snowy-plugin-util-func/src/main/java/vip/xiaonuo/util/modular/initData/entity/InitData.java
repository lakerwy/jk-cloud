package vip.xiaonuo.util.modular.initData.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/10 9:38
 */
@Data
@TableName("INIT_DATA")
public class InitData {
    /** id */
    @TableField
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 初始化数据类型 */
    @ApiModelProperty(value = "初始化数据类型", position = 2)
    private String type;

    /** 初始化数据类型 */
    @ApiModelProperty(value = "初始化状态", position = 3)
    private Boolean initStatus;

    @ApiModelProperty(value = "备注说明", position = 4)
    private String remark;
}
