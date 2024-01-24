package vip.xiaonuo.sys.modular.hr.certManage.param;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/***
 * 人员资质【综合搜索】
 */
@Getter
@Setter
public class CertSearchPageParam extends CertManagePageParam {
    /** 标记字段 - 是否开启综合搜索 */
    @ApiModelProperty(value = "标记字段 - 是否开启综合搜索")
    private Boolean isOpen;

    /** 注册类 - 是否参与查询 */
    @ApiModelProperty(value = "注册类 - 是否参与查询")
    private Boolean isRe;

    /** 注册类 - 关键词 */
    @ApiModelProperty(value = "注册类 - 关键词")
    private String reSearchKey;

    /** 注册类 - 注册类别 */
    @ApiModelProperty(value = "注册类 - 注册类别")
    private String reCertType;

    /** 注册类 - 注册专业 */
    @ApiModelProperty(value = "注册类 - 注册专业")
    private String reMajor;

    /** 注册类 - 发证日期开始 */
    @ApiModelProperty(value = "注册类 - 发证日期开始")
    private String reStartIssueDate;

    /** 注册类 - 发证日期结束 */
    @ApiModelProperty(value = "注册类 - 发证日期结束")
    private String reEndIssueDate;

    /** 注册类 - 存管部门 */
    @ApiModelProperty(value = "注册类 - 存管部门")
    private String reDepositAddress;

    /** 注册类 - 注册单位 */
    @ApiModelProperty(value = "注册类 - 注册单位")
    private String reUnit;



    /** 培训证书类 - 是否参与查询 */
    @ApiModelProperty(value = "培训证书类 - 是否参与查询")
    private Boolean isTr;

    /** 培训证书类 - 关键词 */
    @ApiModelProperty(value = "培训证书类 - 关键词")
    private String trSearchKey;

    /** 培训证书类 - 类别 */
    @ApiModelProperty(value = "培训证书类 - 类别")
    private String trCertType;

    /** 培训证书类 - 证书名称 */
    @ApiModelProperty(value = "培训证书类 - 证书名称")
    private String trCertName;

    /** 培训证书类 - 职务工种 */
    @ApiModelProperty(value = "培训证书类 - 职务工种")
    private String trJobType;

    /** 培训证书类 - 技术职称 */
    @ApiModelProperty(value = "培训证书类 - 技术职称")
    private String trTechnicalTitle;

    /** 培训证书类 - 专业 */
    @ApiModelProperty(value = "培训证书类 - 专业")
    private String trMajor;

    /** 培训证书类 - 发证日期开始 */
    @ApiModelProperty(value = "培训证书类 - 发证日期开始")
    private String trStartIssueDate;

    /** 培训证书类 - 发证日期结束 */
    @ApiModelProperty(value = "培训证书类 - 发证日期结束")
    private String trEndIssueDate;

    /** 培训证书类 - 存管部门 */
    @ApiModelProperty(value = "培训证书类 - 存管部门")
    private String trDepositAddress;

    /** 培训证书类 - 发证单位 */
    @ApiModelProperty(value = "培训证书类 - 发证单位")
    private String trUnit;




    /** 其他类 - 是否参与查询 */
    @ApiModelProperty(value = "其他类 - 是否参与查询")
    private Boolean isOther;

    /** 其他类 - 关键词 */
    @ApiModelProperty(value = "其他类 - 关键词")
    private String otherSearchKey;

    /** 其他类 - 类别 */
    @ApiModelProperty(value = "其他类 - 类别")
    private String otherCertType;

    /** 其他类 - 证书名称 */
    @ApiModelProperty(value = "其他类 - 证书名称")
    private String otherCertName;

    /** 其他类 - 技术职称 */
    @ApiModelProperty(value = "其他类 - 技术职称")
    private String otherTechnicalTitle;

    /** 其他类 - 专业 */
    @ApiModelProperty(value = "其他类 - 专业")
    private String otherMajor;

    /** 其他类 - 发证日期开始 */
    @ApiModelProperty(value = "其他类 - 发证日期开始")
    private String otherStartIssueDate;

    /** 其他类 - 发证日期结束 */
    @ApiModelProperty(value = "其他类 - 发证日期结束")
    private String otherEndIssueDate;

    /** 其他类 - 存管部门 */
    @ApiModelProperty(value = "其他类 - 存管部门")
    private String otherDepositAddress;

    /** 其他类 - 发证单位 */
    @ApiModelProperty(value = "其他类 - 发证单位")
    private String otherUnit;

}
