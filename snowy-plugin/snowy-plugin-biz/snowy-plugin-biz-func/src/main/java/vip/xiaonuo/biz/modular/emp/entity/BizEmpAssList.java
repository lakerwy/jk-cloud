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
package vip.xiaonuo.biz.modular.emp.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-员工绩效考核-考核人员名单实体
 *
 * @author yong.ye
 * @date  2023/09/26 10:40
 **/
@Getter
@Setter
@TableName("BIZ_EMP_ASS_LIST")
public class BizEmpAssList {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 2)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 3)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 4)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 5)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 6)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 7)
    private String dataScopeId;

    /** 考核对象-用户Id */
    @ApiModelProperty(value = "考核对象-用户Id", position = 8)
    private String userId;

    /** 考核对象-用户名 */
    @ApiModelProperty(value = "考核对象-用户名", position = 9)
    private String userName;

    /** 考核对象-组织Id */
    @ApiModelProperty(value = "考核对象-组织Id", position = 10)
    private String orgId;

    /** 考核对象-组织名称 */
    @ApiModelProperty(value = "考核对象-组织名称", position = 11)
    private String orgName;

    /** 年度(如：2023) */
    @ApiModelProperty(value = "年度(如：2023)", position = 12)
    private String year;

    /** 季度(如:1) */
    @ApiModelProperty(value = "季度(如:1)", position = 13)
    private String season;
}
