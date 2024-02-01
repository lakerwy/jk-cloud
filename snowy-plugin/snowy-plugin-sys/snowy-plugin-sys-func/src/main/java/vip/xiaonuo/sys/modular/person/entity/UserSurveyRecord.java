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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户问卷收集-问题|意见-一问一答记录实体
 *
 * @author yong.ye
 * @date  2024/02/01 09:53
 **/
@Getter
@Setter
@TableName("USER_SURVEY_RECORD")
public class UserSurveyRecord {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 问题或建议主键ID */
    @ApiModelProperty(value = "问题或建议主键ID", position = 2)
    private String surveyId;

    /** 身份类别：1 提问放| 2 回复方  */
    @ApiModelProperty(value = "身份类别：1 提问放| 2 回复方 ", position = 3)
    private String type;

    /** 回复内容 */
    @ApiModelProperty(value = "回复内容", position = 4)
    private String content;

    /** 操作时间 */
    @ApiModelProperty(value = "操作时间", position = 5)
    private Date oprTime;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 6)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 8)
    private String extJson;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 9)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 10)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
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

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 操作人姓名 */
    @ApiModelProperty(value = "操作人姓名", position = 16)
    private String oprName;
}
