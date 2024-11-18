package vip.xiaonuo.sys.modular.hr.honorAward.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 企业荣誉添加参数
 *
 * @author pzh
 * @date  2024/10/16 11:17
 **/
@Getter
@Setter
public class EnterpriseHonorAddParam {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 3)
    private String type;

    /** 证书编号 */
    @ApiModelProperty(value = "证书编号", position = 4)
    private String certNo;

    /** 发奖单位 */
    @ApiModelProperty(value = "发奖单位", position = 5)
    private String awardingOrg;

    /** 招标类型 */
    @ApiModelProperty(value = "招标类型", position = 6)
    private String bidType;

    /** 甲方 */
    @ApiModelProperty(value = "甲方", position = 7)
    private String counterparty;

    /** 项目负责人 */
    @ApiModelProperty(value = "项目负责人", position = 8)
    private String projectLeader;

    /** 获奖时间 */
    @ApiModelProperty(value = "获奖时间", position = 9)
    private Date awardDate;

    /** 有效期至 */
    @ApiModelProperty(value = "有效期至", position = 10)
    private Date invalidDate;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String note;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 12)
    private String attachments;

}
