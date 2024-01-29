package vip.xiaonuo.sys.modular.hr.certManage.param;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/***
 * 人员资质【综合搜索】
 */
@Getter
@Setter
public class CertSearchPageParam {

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 模块类别：注册证书|培训证书 */
    @ApiModelProperty(value = "模块类别：注册证书|培训证书")
    private String type;

    /** 证书类别：注册类别|培训类别 */
    @ApiModelProperty(value = "证书类别：注册类别|培训类别")
    private String certType;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name;

    /** 专业 */
    @ApiModelProperty(value = "专业")
    private String major;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idNumber;

    /** 证书编号 */
    @ApiModelProperty(value = "证书编号")
    private String certNo;

    /** 执业印章号 */
    @ApiModelProperty(value = "执业印章号")
    private String certSealNo;

    /** 发证日期开始 */
    @ApiModelProperty(value = "发证日期开始")
    private String startIssueDate;

    /** 发证日期结束 */
    @ApiModelProperty(value = "发证日期结束")
    private String endIssueDate;

    /** 有效期开始 */
    @ApiModelProperty(value = "有效期开始")
    private String startValidity;

    /** 有效期结束 */
    @ApiModelProperty(value = "有效期结束")
    private String endValidity;

    /** 注册单位|发证单位 */
    @ApiModelProperty(value = "注册单位|发证单位")
    private String unit;

    /** 证书名称 */
    @ApiModelProperty(value = "证书名称")
    private String certName;

    /** 职务工种 */
    @ApiModelProperty(value = "职务工种")
    private String jobType;

    /** 技术职称 */
    @ApiModelProperty(value = "技术职称")
    private String technicalTitle;

    /** 企业名称 */
    @ApiModelProperty(value = "企业名称")
    private String companyName;

    /** 证书状态：有效|无效 */
    @ApiModelProperty(value = "证书状态：有效|无效")
    private String certStatus;

    /** 用户类型：总部|分公司|外部人员 */
    @ApiModelProperty(value = "用户类型：总部|分公司|外部人员")
    private String userType;





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

    /** 其他类 - 发证单位 */
    @ApiModelProperty(value = "其他类 - 发证单位")
    private String otherUnit;

}
