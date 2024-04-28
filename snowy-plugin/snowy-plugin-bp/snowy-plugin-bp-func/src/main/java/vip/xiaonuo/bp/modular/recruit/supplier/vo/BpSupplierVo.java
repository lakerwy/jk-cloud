package vip.xiaonuo.bp.modular.recruit.supplier.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BpSupplierVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 公司名称 */
    @ApiModelProperty(value = "公司名称", position = 2)
    private String companyName;

    /** 经营范围 */
    @ApiModelProperty(value = "经营范围", position = 3)
    private String businessScope;

    /** 来源类型 */
    @ApiModelProperty(value = "来源类型", position = 4)
    private String source;

    /** 业务负责人姓名 */
    @ApiModelProperty(value = "业务负责人姓名", position = 5)
    private String businessadminName;

    /** 业务负责人手机号 */
    @ApiModelProperty(value = "业务负责人手机号", position = 6)
    private String businessadminPhone;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 7)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 审核状态 */
    @ApiModelProperty(value = "审核状态", position = 8)
    private String status;

    /** 黑名单原因 */
    @ApiModelProperty(value = "黑名单原因", position = 9)
    private String blacklistReason;

    /** 黑名单标记 */
    @ApiModelProperty(value = "黑名单标记", position = 10)
    private String isBlacklist;
}
