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
package vip.xiaonuo.sys.modular.person.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * 用户问卷收集-问题|意见实体
 *
 * @author yong.ye
 * @date  2024/01/25 10:44
 **/
@Getter
@Setter
@TableName("USER_SURVEY_COLLECT")
public class UserSurveyCollect {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 问卷类型：问题|建议 */
    @ApiModelProperty(value = "问卷类型：问题|建议", position = 2)
    private String type;

    /** 问题类型 */
    @ApiModelProperty(value = "问题类型", position = 3)
    private String proType;

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 4)
    private String title;

    /** 内容 */
    @ApiModelProperty(value = "内容", position = 5)
    private String content;

    /** 流水号 */
    @ApiModelProperty(value = "流水号", position = 6)
    private String sn;

    /** 是否匿名 0-1 */
    @ApiModelProperty(value = "是否匿名 0-1", position = 7)
    private String isAnony;

    /** 提交时间 */
    @ApiModelProperty(value = "提交时间", position = 8)
    private Date submitTime;

    /** 回复时间 */
    @ApiModelProperty(value = "回复时间", position = 9)
    private Date replyTime;

    /** 评价|回复 */
    @ApiModelProperty(value = "评价|回复", position = 10)
    private String reviewContent;

    /** 状态：已回复|未回复|已关闭 */
    @ApiModelProperty(value = "状态：已回复|未回复|已关闭", position = 11)
    private String status;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 12)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String memo;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 14)
    private String extJson;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 15)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 16)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 17)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 18)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 19)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 20)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 21)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 提交人姓名 */
    @ApiModelProperty(value = "提交人姓名", position = 22)
    private String submitName;

    /** 回复者姓名|操作人姓名 */
    @ApiModelProperty(value = "回复者姓名|操作人姓名", position = 23)
    private String replyName;
}
