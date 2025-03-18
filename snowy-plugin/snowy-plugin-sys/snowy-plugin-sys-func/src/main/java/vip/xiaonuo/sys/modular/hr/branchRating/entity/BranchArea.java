package vip.xiaonuo.sys.modular.hr.branchRating.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司评级（二期）- 区域经济修正系数 实体
 *
 * @author yong.ye
 * @date  2025/03/18 08:47
 **/
@Getter
@Setter
@TableName("BRANCH_AREA")
public class BranchArea {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 分公司id */
    @ApiModelProperty(value = "分公司id", position = 2)
    private String branchId;

    /** 分公司名称 */
    @ApiModelProperty(value = "分公司名称", position = 3)
    private String branchName;

    /** 分公司所在城市 */
    @ApiModelProperty(value = "分公司所在城市", position = 4)
    private String branchCity;

    /** 分公司上一年度所在市GDP（万亿元） */
    @ApiModelProperty(value = "分公司上一年度所在市GDP（万亿元）", position = 5)
    private BigDecimal branchCityGdp;

    /** 武汉市上一季度市GDP（万亿元） */
    @ApiModelProperty(value = "武汉市上一季度市GDP（万亿元）", position = 6)
    private BigDecimal wuhanCityGdp;

    /** 直接系数 */
    @ApiModelProperty(value = "直接系数", position = 7)
    private BigDecimal directRate;

    /** 调整系数 */
    @ApiModelProperty(value = "调整系数", position = 8)
    private BigDecimal adjustRate;

    /** 区域经济修正系数 */
    @ApiModelProperty(value = "区域经济修正系数", position = 9)
    private BigDecimal areaRate;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 10)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 12)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 13)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 18)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
