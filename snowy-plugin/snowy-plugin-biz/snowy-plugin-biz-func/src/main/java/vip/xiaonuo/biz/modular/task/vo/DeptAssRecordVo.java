package vip.xiaonuo.biz.modular.task.vo;

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
 * @date 2023/8/9 10:50
 */

@Getter
@Setter
public class DeptAssRecordVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 考核年月 */
    @ApiModelProperty(value = "考核年月", position = 2)
    private String assYear;

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 3)
    private String orgId;

    /** 考核类型--对应的字典Id */
    @ApiModelProperty(value = "考核类型--对应的字典Id", position = 4)
    private String dictId;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 5)
    private String memo;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 6)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 7)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 8)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 9)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 10)
    private String updateUser;

    /** (外键)部门考核表主键Id */
    @ApiModelProperty(value = "(外键)部门考核表主键Id", position = 11)
    private String assId;

    /** 字典类型（加分项、减分项） */
    @ApiModelProperty(value = "字典类型（加分项、减分项）", position = 12)
    private String dictType;

    /** 加分|减分类型 */
    @ApiModelProperty(value = "加分|减分类型", position = 13)
    private String dictName;

    /** 分值 */
    @ApiModelProperty(value = "分值", position = 14)
    private Double dictScore;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 15)
    private String dictDescription;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 16)
    private String orgName;

    /** 实际加|减分值 */
    @ApiModelProperty(value = "实际加|减分值", position = 17)
    private Double realScore;
}
