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
package vip.xiaonuo.dev.modular.supervisionPlatform.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 市场监管公共服务平台-个人业绩信息表实体
 *
 * @author ljy
 * @date  2024/01/11 10:58
 **/
@Getter
@Setter
@TableName("TBPERSONPERFORMANCE")
public class Tbpersonperformance {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 个人业绩记录编号 */
    @ApiModelProperty(value = "个人业绩记录编号", position = 2)
    private String personPerfNum;

    /** 企业业绩记录编号 */
    @ApiModelProperty(value = "企业业绩记录编号", position = 3)
    private String perfNum;

    /** 人员姓名 */
    @ApiModelProperty(value = "人员姓名", position = 4)
    private String personName;

    /** 证件类型 */
    @ApiModelProperty(value = "证件类型", position = 5)
    private String idCardTypeNum;

    /** 人员证件号码 */
    @ApiModelProperty(value = "人员证件号码", position = 6)
    private String personIdCard;

    /** 本人在工程中所起作用:1项目负责2 技术负责入3专业负责M4 主要设计5 总监理工程师6项目经理 */
    @ApiModelProperty(value = "本人在工程中所起作用:1项目负责2 技术负责入3专业负责M4 主要设计5 总监理工程师6项目经理", position = 7)
    private Integer prjDuty;

    /** 信息审核部门 */
    @ApiModelProperty(value = "信息审核部门", position = 8)
    private String checkDepartName;

    /** 信息审核人 */
    @ApiModelProperty(value = "信息审核人", position = 9)
    private String checkPersonName;

    /** 数据等级 */
    @ApiModelProperty(value = "数据等级", position = 10)
    private String dataLevel;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 11)
    private String extJson;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 12)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
