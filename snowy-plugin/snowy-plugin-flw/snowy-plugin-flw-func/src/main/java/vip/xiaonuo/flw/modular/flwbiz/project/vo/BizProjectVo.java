package vip.xiaonuo.flw.modular.flwbiz.project.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
public class BizProjectVo {

    /** 主键 */
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** sn值 */
    @ApiModelProperty(value = "sn值", position = 2)
    private String sn;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 6)
    private String name;

    /** 是否EPC */
    @ApiModelProperty(value = "是否EPC", position = 7)
    private String isEpc;

    /** 是否EPC+F */
    @ApiModelProperty(value = "是否EPC+F", position = 8)
    private String isEpcf;

    /** 是否牵头 */
    @ApiModelProperty(value = "是否牵头", position = 9)
    private String isLead;

    /** 项目类型 */
    @ApiModelProperty(value = "项目类型", position = 10)
    private String type;

//    /** 项目介绍 */
//    @ApiModelProperty(value = "项目介绍", position = 15)
//    private String introduce;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 38)
    private String status;

    /** 删除状态 */
    @ApiModelProperty(value = "删除状态", position = 39)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 40)
    private Date createTime;

    /** 行业分类 */
    @ApiModelProperty(value = "行业分类", position = 67)
    private String industryClassification;





    /** 合同总额 */
    @ApiModelProperty(value = "合同总额", position = 100)
    private BigDecimal htSumAmount;

}
