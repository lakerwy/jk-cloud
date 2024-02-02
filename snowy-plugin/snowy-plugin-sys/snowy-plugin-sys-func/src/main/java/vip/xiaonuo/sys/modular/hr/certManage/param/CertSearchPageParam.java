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

    /** 级别 */
    @ApiModelProperty(value = "级别")
    private String level;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name;

    /** 专业1 */
    @ApiModelProperty(value = "专业1")
    private String major1;

    /** 专业2 */
    @ApiModelProperty(value = "专业2")
    private String major2;

    /** 专业3 */
    @ApiModelProperty(value = "专业3")
    private String major3;

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

    /** 证书状态：有效|无效 */
    @ApiModelProperty(value = "证书状态：有效|无效")
    private String certStatus;

    /** 用户类型：总部|分公司|外部人员 */
    @ApiModelProperty(value = "用户类型：总部|分公司|外部人员")
    private String userType;

    /** 社保所在地 */
    @ApiModelProperty(value = "社保所在地")
    private String insUnit;

    /** 学校专业 */
    @ApiModelProperty(value = "学校专业")
    private String eduMajor;




    /** 标记字段 - 是否开启综合搜索 */
    @ApiModelProperty(value = "标记字段 - 是否开启综合搜索")
    private Boolean isOpen = Boolean.FALSE;

    /** 注册类 - 是否参与查询 */
    @ApiModelProperty(value = "注册类 - 是否参与查询")
    private Boolean isRe = Boolean.FALSE;

    /** 注册类 - 关键词 */
    @ApiModelProperty(value = "注册类 - 关键词")
    private String reSearchKey;

    /** 注册类 - 注册类别 */
    @ApiModelProperty(value = "注册类 - 注册类别")
    private String reCertType;

    /** 注册类 - 等级 */
    @ApiModelProperty(value = "注册类 - 等级")
    private String reLevel;

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
    private Boolean isTr = Boolean.FALSE;

    /** 培训证书类 - 关键词 */
    @ApiModelProperty(value = "培训证书类 - 关键词")
    private String trSearchKey;

    /** 培训证书类 - 类别 */
    @ApiModelProperty(value = "培训证书类 - 类别")
    private String trCertType;

    /** 培训证书类 - 等级 */
    @ApiModelProperty(value = "培训证书类 - 等级")
    private String trLevel;

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




    /** 职称类 - 是否参与查询 */
    @ApiModelProperty(value = "职称类 - 是否参与查询")
    private Boolean isOther = Boolean.FALSE;

    /** 职称类 - 关键词 */
    @ApiModelProperty(value = "职称类 - 关键词")
    private String otherSearchKey;

    /** 职称类 - 类别 */
    @ApiModelProperty(value = "职称类 - 类别")
    private String otherCertType;

    /** 职称类 - 等级 */
    @ApiModelProperty(value = "职称类 - 等级")
    private String otherLevel;

    /** 职称类 - 专业 */
    @ApiModelProperty(value = "职称类 - 专业")
    private String otherMajor;

    /** 职称类 - 发证日期开始 */
    @ApiModelProperty(value = "职称类 - 发证日期开始")
    private String otherStartIssueDate;

    /** 职称类 - 发证日期结束 */
    @ApiModelProperty(value = "职称类 - 发证日期结束")
    private String otherEndIssueDate;

    /** 职称类 - 发证单位 */
    @ApiModelProperty(value = "职称类 - 发证单位")
    private String otherUnit;

}
