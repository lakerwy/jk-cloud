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
package vip.xiaonuo.biz.modular.school.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 校招-简历实体
 *
 * @author yong.ye
 * @date  2023/11/15 08:54
 **/
@Getter
@Setter
@TableName("BIZ_SCHOOL_RESUME")
public class Resume {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户Id */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 3)
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 4)
    private String sex;

    /** 手机号 */
    @ApiModelProperty(value = "手机号", position = 5)
    private String mobile;

    /** 学校 */
    @ApiModelProperty(value = "学校", position = 6)
    private String school;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 7)
    private String subject;

    /** 岗位类别 */
    @ApiModelProperty(value = "岗位类别", position = 8)
    private String positionType;

    /** 信息来源 */
    @ApiModelProperty(value = "信息来源", position = 9)
    private String infoSource;

    /** 优势 */
    @ApiModelProperty(value = "优势", position = 10)
    private String advantages;

    /** 简历附件地址|简历附件Id */
    @ApiModelProperty(value = "简历附件地址|简历附件Id", position = 11)
    private String resumeAttId;

    /** 成绩单附件地址|成绩单附件Id */
    @ApiModelProperty(value = "成绩单附件地址|成绩单附件Id", position = 12)
    private String reportAttId;

    /** 其他附件地址|其他附件Id */
    @ApiModelProperty(value = "其他附件地址|其他附件Id", position = 13)
    private String otherAttId;

    /** 简历状态 */
    @ApiModelProperty(value = "简历状态", position = 14)
    private String status;

    /** 简历类型 */
    @ApiModelProperty(value = "简历类型", position = 15)
    private String resumeType;

    /** 简历投递次数 */
    @ApiModelProperty(value = "简历投递次数", position = 16)
    private Integer postTimes;

    /** 推荐部门 */
    @ApiModelProperty(value = "推荐部门", position = 17)
    private String belongsDepts;

    /** 部门意见 */
    @ApiModelProperty(value = "部门意见", position = 18)
    private String belongsDeptsComments;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 19)
    private Integer academic;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 20)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 21)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 22)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 23)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 24)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 是否历史数据 */
    @ApiModelProperty(value = "是否历史数据", position = 25)
    private Integer isOld;

    /** 历史简历附件JSON转换体 */
    @ApiModelProperty(value = "历史简历附件JSON转换体", position = 26)
    private String resumeJson;

    /** 历史成绩单附件JSON转换体 */
    @ApiModelProperty(value = "历史成绩单附件JSON转换体", position = 27)
    private String reportJson;

    /** 历史其他附件JSON转换体 */
    @ApiModelProperty(value = "历史其他附件JSON转换体", position = 28)
    private String otherJson;
}
