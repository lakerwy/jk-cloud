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
package vip.xiaonuo.sys.modular.company.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 建科公司列表(子公司、分公司)实体
 *
 * @author yong.ye
 * @date  2024/03/07 15:04
 **/
@Getter
@Setter
@TableName("COMPANY_LIST")
public class CompanyList {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 分公司|集团子公司 */
    @ApiModelProperty(value = "分公司|集团子公司", position = 2)
    private String type;

    /** 公司名称 */
    @ApiModelProperty(value = "公司名称", position = 3)
    private String companyName;

    /** 登记证号 */
    @ApiModelProperty(value = "登记证号", position = 4)
    private String certNo;

    /** 法人 */
    @ApiModelProperty(value = "法人", position = 5)
    private String legal;

    /** 排序 */
    @ApiModelProperty(value = "排序", position = 6)
    private Integer indexSort;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 8)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 9)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 公示状态 */
    @ApiModelProperty(value = "公示状态", position = 15)
    private String status;
}
