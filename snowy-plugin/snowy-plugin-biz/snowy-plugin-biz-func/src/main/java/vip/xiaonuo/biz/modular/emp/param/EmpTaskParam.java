package vip.xiaonuo.biz.modular.emp.param;

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
 * @date 2023/9/19 9:14
 *
 * 流程中添加员工当前季度任务信息参数实体
 */

@Getter
@Setter
public class EmpTaskParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

//    /** 用户Id */
//    @ApiModelProperty(value = "用户Id", position = 2)
//    private String userId;
//
//    /** 用户名 */
//    @ApiModelProperty(value = "用户名", position = 3)
//    private String userName;
//
//    /** 组织Id */
//    @ApiModelProperty(value = "组织Id", position = 4)
//    private String orgId;
//
//    /** 年度(如：2023) */
//    @ApiModelProperty(value = "年度(如：2023)", position = 5)
//    private String year;
//
//    /** 季度(如:1) */
//    @ApiModelProperty(value = "季度(如:1)", position = 6)
//    private String season;

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 7)
    private String title;

    /** 目标 */
    @ApiModelProperty(value = "目标", position = 8)
    private String goal;

    /** 权重 */
    @ApiModelProperty(value = "权重", position = 9)
    private Double rate;

    /** 完成情况 */
    @ApiModelProperty(value = "完成情况", position = 10)
    private String complete;

    /** 自评分 */
    @ApiModelProperty(value = "自评分", position = 11)
    private Double selfScore;

    /** 评分 */
    @ApiModelProperty(value = "评分", position = 12)
    private Double score;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String memo;

//    /** 删除状态标记 */
//    @ApiModelProperty(value = "删除状态标记", position = 14)
//    @TableLogic
//    @TableField(fill = FieldFill.INSERT)
//    private String deleteFlag;
//
//    /** 创建时间 */
//    @ApiModelProperty(value = "创建时间", position = 15)
//    @TableField(fill = FieldFill.INSERT)
//    private Date createTime;
//
//    /** 创建人 */
//    @ApiModelProperty(value = "创建人", position = 16)
//    @TableField(fill = FieldFill.INSERT)
//    private String createUser;
//
//    /** 更新时间 */
//    @ApiModelProperty(value = "更新时间", position = 17)
//    @TableField(fill = FieldFill.UPDATE)
//    private Date updateTime;
//
//    /** 更新人 */
//    @ApiModelProperty(value = "更新人", position = 18)
//    @TableField(fill = FieldFill.UPDATE)
//    private String updateUser;
//
//    /** 外键（流程单主键Id） */
//    @ApiModelProperty(value = "外键（流程单主键Id）", position = 19)
//    private String flwId;

}
