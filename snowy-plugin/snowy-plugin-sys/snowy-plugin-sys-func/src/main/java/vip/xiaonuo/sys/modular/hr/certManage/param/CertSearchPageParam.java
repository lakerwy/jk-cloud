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

    /** 专业123 */
    @ApiModelProperty(value = "专业123")
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

    /** 注册证书类 - 混合搜索实体 */
    @ApiModelProperty(value = "注册证书类 - 混合搜索实体")
    private HybridSearchParam registerSearch;

    /** 培训证书类 - 混合搜索实体 */
    @ApiModelProperty(value = "培训证书类 - 混合搜索实体")
    private HybridSearchParam trainSearch;

    /** 职称证书类 - 混合搜索实体 */
    @ApiModelProperty(value = "职称证书类 - 混合搜索实体")
    private HybridSearchParam titleSearch;

}
