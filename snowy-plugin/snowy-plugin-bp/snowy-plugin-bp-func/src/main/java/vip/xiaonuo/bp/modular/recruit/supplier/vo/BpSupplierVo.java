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

    /** 一级分类ID */
    @ApiModelProperty(value = "一级分类ID", position = 3)
    private String stairclassifyId;

    /** 一级分类名称 */
    @ApiModelProperty(value = "一级分类名称", position = 4)
    private String stairclassifyName;

    /** 二级分类ID */
    @ApiModelProperty(value = "二级分类ID", position = 5)
    private String accessclassifyId;

    /** 二级分类名称 */
    @ApiModelProperty(value = "二级分类名称", position = 6)
    private String accessclassifyName;

    /** 三级分类ID */
    @ApiModelProperty(value = "三级分类ID", position = 7)
    private String reclassifyId;

    /** 三级分类名称 */
    @ApiModelProperty(value = "三级分类名称", position = 8)
    private String reclassifyName;

    /** 来源类型 */
    @ApiModelProperty(value = "来源类型", position = 9)
    private String source;

    /** 业务负责人姓名 */
    @ApiModelProperty(value = "业务负责人姓名", position = 10)
    private String businessadminName;

    /** 业务负责人手机号 */
    @ApiModelProperty(value = "业务负责人手机号", position = 11)
    private String businessadminPhone;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 黑名单原因 */
    @ApiModelProperty(value = "黑名单原因", position = 13)
    private String blacklistReason;

}
