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
package vip.xiaonuo.biz.modular.visit.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 业务表-员工绩效考核-季度任务表实体
 *
 * @author yong.ye
 * @date 2023/09/19 08:47
 **/
@Getter
@Setter
@TableName("BIZ_VISIT_LIST")
public class BizVisitList {

    /**
     * 主键ID
     */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;
    @ApiModelProperty(value = "Biz_visit主键ID", position = 10)
    private String visitId;
    /**
     * 计划时间
     */
    @ApiModelProperty(value = "计划时间", position = 6)
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planDate;

    /**
     * 拜访单位
     */
    @ApiModelProperty(value = "拜访单位", position = 7)
    private String visitUnit;

    /**
     * 主题
     */
    @ApiModelProperty(value = "主题", position = 8)
    private String theme;

    /**
     * 主要人员
     */
    @ApiModelProperty(value = "主要人员", position = 9)
    private String mainPerson;

    /**
     * 删除状态标记
     */
    @ApiModelProperty(value = "删除状态标记", position = 14)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", position = 18)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

}
