package vip.xiaonuo.sys.modular.hr.registerCert.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegCertStatusDataVo {
    /** 证书等级 */
    @ApiModelProperty(value = "证书等级", position = 3)
    private String certLever;

    /** 证书类别 */
    @ApiModelProperty(value = "证书类别", position = 4)
    private String certType;

    /** 证书专业 */
    @ApiModelProperty(value = "证书专业", position = 5)
    private String certSubject;

    /** 是否参与项目 */
    @ApiModelProperty(value = "是否参与项目", position = 6)
    private String isJoinPrj;

    /** 补贴标准 */
    @ApiModelProperty(value = "补贴标准", position = 7)
    private String certAlwStd;

    /** 系数 */
    @ApiModelProperty(value = "系数", position = 8)
    private String certRate;

    /** 补贴金额 */
    @ApiModelProperty(value = "补贴金额", position = 9)
    private String certAlwAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String certMemo;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 12)
    private String idCardNumber;
}
