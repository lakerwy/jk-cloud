package vip.xiaonuo.sys.modular.hr.branchRating.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司评级（二期）- 分公司评级填报添加参数
 *
 * @author yong.ye
 * @date  2025/03/18 08:48
 **/
@Getter
@Setter
public class BranchFillAddParam {

    /** 分公司id */
    @ApiModelProperty(value = "分公司id", position = 2)
    private String branchId;

    /** 填报季度 */
    @ApiModelProperty(value = "填报季度", position = 2)
    private String fillSeason;

    /** 分公司名称 */
    @ApiModelProperty(value = "分公司名称", position = 3)
    private String branchName;

    /** 分公司经营范围 */
    @ApiModelProperty(value = "分公司经营范围", position = 4)
    private String branchScope;

    /** 区域 */
    @ApiModelProperty(value = "区域", position = 5)
    private String area;

    /** 区域经理id */
    @ApiModelProperty(value = "区域经理id", position = 6)
    private String areaManagerId;

    /** 区域经理 */
    @ApiModelProperty(value = "区域经理", position = 7)
    private String areaManager;

    /** 总分 */
    @ApiModelProperty(value = "总分", position = 8)
    private BigDecimal totalScore;

    /** 级别 */
    @ApiModelProperty(value = "级别", position = 9)
    private String level;

    /** 是否公示 */
    @ApiModelProperty(value = "是否公示", position = 10)
    private Integer isShow;

    /** (A)承包合同应缴承包保底技术服务费评分 - 当年应缴承包保底额（万元） */
    @ApiModelProperty(value = "(A)承包合同应缴承包保底技术服务费评分 - 当年应缴承包保底额（万元）", position = 11)
    private BigDecimal aYjbdeAmount;

    /** (A)承包合同应缴承包保底技术服务费评分 - 区域经济修正系数 */
    @ApiModelProperty(value = "(A)承包合同应缴承包保底技术服务费评分 - 区域经济修正系数", position = 12)
    private BigDecimal aAreaRate;

    /** (A)承包合同应缴承包保底技术服务费评分 - 修正保底额（万元） */
    @ApiModelProperty(value = "(A)承包合同应缴承包保底技术服务费评分 - 修正保底额（万元）", position = 13)
    private BigDecimal aXzbdeAmount;

    /** (A)承包合同应缴承包保底技术服务费评分 - 技术服务费费率% */
    @ApiModelProperty(value = "(A)承包合同应缴承包保底技术服务费评分 - 技术服务费费率%", position = 14)
    private BigDecimal aJsfwfRate;

    /** (A)承包合同应缴承包保底技术服务费评分 - 基础分值 */
    @ApiModelProperty(value = "(A)承包合同应缴承包保底技术服务费评分 - 基础分值", position = 15)
    private BigDecimal aBaseScore;

    /** (A)承包合同应缴承包保底技术服务费评分 - 权重 */
    @ApiModelProperty(value = "(A)承包合同应缴承包保底技术服务费评分 - 权重", position = 16)
    private BigDecimal aWeight;

    /** (A)承包合同应缴承包保底技术服务费评分 - 加权后分值 */
    @ApiModelProperty(value = "(A)承包合同应缴承包保底技术服务费评分 - 加权后分值", position = 17)
    private BigDecimal aWeightScore;

    /** (A)承包合同应缴承包保底技术服务费评分 - 备注 */
    @ApiModelProperty(value = "(A)承包合同应缴承包保底技术服务费评分 - 备注", position = 18)
    private String aMemo;

    /** (A)承包合同应缴承包保底技术服务费评分 - JSON数据 */
    @ApiModelProperty(value = "(A)承包合同应缴承包保底技术服务费评分 - JSON数据", position = 19)
    private String aJson;

    /** (B)区域发展部和经营管理部评分 - 区域发展部评分 */
    @ApiModelProperty(value = "(B)区域发展部和经营管理部评分 - 区域发展部评分", position = 20)
    private BigDecimal bFzbScore;

    /** (B)区域发展部和经营管理部评分 - 经营管理部评分 */
    @ApiModelProperty(value = "(B)区域发展部和经营管理部评分 - 经营管理部评分", position = 21)
    private BigDecimal bGlbScore;

    /** (B)区域发展部和经营管理部评分 - 区域经理评分 */
    @ApiModelProperty(value = "(B)区域发展部和经营管理部评分 - 区域经理评分", position = 22)
    private BigDecimal bJlScore;

    /** (B)区域发展部和经营管理部评分 - 基础分值 */
    @ApiModelProperty(value = "(B)区域发展部和经营管理部评分 - 基础分值", position = 23)
    private BigDecimal bBaseScore;

    /** (B)区域发展部和经营管理部评分 - 权重 */
    @ApiModelProperty(value = "(B)区域发展部和经营管理部评分 - 权重", position = 24)
    private BigDecimal bWeight;

    /** (B)区域发展部和经营管理部评分 - 加权后分值 */
    @ApiModelProperty(value = "(B)区域发展部和经营管理部评分 - 加权后分值", position = 25)
    private BigDecimal bWeightScore;

    /** (B)区域发展部和经营管理部评分 - 理由 */
    @ApiModelProperty(value = "(B)区域发展部和经营管理部评分 - 理由", position = 26)
    private String bMemo;

    /** (B)区域发展部和经营管理部评分 - JSON数据 */
    @ApiModelProperty(value = "(B)区域发展部和经营管理部评分 - JSON数据", position = 27)
    private String bJson;

    /** (C)承包总年数评分 - 已完成的承包年数 */
    @ApiModelProperty(value = "(C)承包总年数评分 - 已完成的承包年数", position = 28)
    private BigDecimal cYwccbYear;

    /** (C)承包总年数评分 - 基础分值 */
    @ApiModelProperty(value = "(C)承包总年数评分 - 基础分值", position = 29)
    private BigDecimal cBaseScore;

    /** (C)承包总年数评分 - 权重 */
    @ApiModelProperty(value = "(C)承包总年数评分 - 权重", position = 30)
    private BigDecimal cWeight;

    /** (C)承包总年数评分 - 加权后分值 */
    @ApiModelProperty(value = "(C)承包总年数评分 - 加权后分值", position = 31)
    private BigDecimal cWeightScore;

    /** (C)承包总年数评分 - 理由 */
    @ApiModelProperty(value = "(C)承包总年数评分 - 理由", position = 32)
    private String cMemo;

    /** (C)承包总年数评分 - JSON数据 */
    @ApiModelProperty(value = "(C)承包总年数评分 - JSON数据", position = 33)
    private String cJson;

    /** (D)承包期内实际签订合同额评分 - 当期考核日期范围 - 开始 */
    @ApiModelProperty(value = "(D)承包期内实际签订合同额评分 - 当期考核日期范围 - 开始", position = 34)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dKhFrom;

    /** (D)承包期内实际签订合同额评分 - 当期考核日期范围 - 结束 */
    @ApiModelProperty(value = "(D)承包期内实际签订合同额评分 - 当期考核日期范围 - 结束", position = 35)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dKhTo;

    /** (D)承包期内实际签订合同额评分 - 当期已签合同总额（万元） */
    @ApiModelProperty(value = "(D)承包期内实际签订合同额评分 - 当期已签合同总额（万元）", position = 36)
    private BigDecimal dHtAmount;

    /** (D)承包期内实际签订合同额评分 - 所在区域当前应考核标准（万元） */
    @ApiModelProperty(value = "(D)承包期内实际签订合同额评分 - 所在区域当前应考核标准（万元）", position = 37)
    private BigDecimal dYkhbzAmount;

    /** (D)承包期内实际签订合同额评分 - 基础分值 */
    @ApiModelProperty(value = "(D)承包期内实际签订合同额评分 - 基础分值", position = 38)
    private BigDecimal dBaseScore;

    /** (D)承包期内实际签订合同额评分 - 权重 */
    @ApiModelProperty(value = "(D)承包期内实际签订合同额评分 - 权重", position = 39)
    private BigDecimal dWeight;

    /** (D)承包期内实际签订合同额评分 - 加权后分值 */
    @ApiModelProperty(value = "(D)承包期内实际签订合同额评分 - 加权后分值", position = 40)
    private BigDecimal dWeightScore;

    /** (D)承包期内实际签订合同额评分 - 备注 */
    @ApiModelProperty(value = "(D)承包期内实际签订合同额评分 - 备注", position = 41)
    private String dMemo;

    /** (D)承包期内实际签订合同额评分 - JSON数据 */
    @ApiModelProperty(value = "(D)承包期内实际签订合同额评分 - JSON数据", position = 42)
    private String dJson;

    /** (E)近三年实缴技术服务费考核评分 - 加权后分值总计 */
    @ApiModelProperty(value = "(E)近三年实缴技术服务费考核评分 - 加权后分值总计", position = 43)
    private BigDecimal eWeightScore;

    /** (E)近三年实缴技术服务费考核评分 - JSON数据 */
    @ApiModelProperty(value = "(E)近三年实缴技术服务费考核评分 - JSON数据", position = 44)
    private String eJson;

    /** (F)人员情况评分 - 高级工程师最低标准人数 */
    @ApiModelProperty(value = "(F)人员情况评分 - 高级工程师最低标准人数", position = 45)
    private Integer fGjgcsNum;

    /** (F)人员情况评分 - 工程师最低标准人数 */
    @ApiModelProperty(value = "(F)人员情况评分 - 工程师最低标准人数", position = 46)
    private Integer fGcsNum;

    /** (F)人员情况评分 - 助理工程师最低标准人数 */
    @ApiModelProperty(value = "(F)人员情况评分 - 助理工程师最低标准人数", position = 47)
    private Integer fZlgcsNum;

    /** (F)人员情况评分 - 姓名+职称级别+职称专业+执业证书 - JSON数据 */
    @ApiModelProperty(value = "(F)人员情况评分 - 姓名+职称级别+职称专业+执业证书 - JSON数据", position = 48)
    private String fEmpJson;

    /** (F)人员情况评分 - 基础分值 */
    @ApiModelProperty(value = "(F)人员情况评分 - 基础分值", position = 49)
    private BigDecimal fBaseScore;

    /** (F)人员情况评分 - 权重 */
    @ApiModelProperty(value = "(F)人员情况评分 - 权重", position = 50)
    private BigDecimal fWeight;

    /** (F)人员情况评分 - 加权后分值 */
    @ApiModelProperty(value = "(F)人员情况评分 - 加权后分值", position = 51)
    private BigDecimal fWeightScore;

    /** (F)人员情况评分 - 备注 */
    @ApiModelProperty(value = "(F)人员情况评分 - 备注", position = 52)
    private String fMemo;

    /** (F)人员情况评分 - JSON数据 */
    @ApiModelProperty(value = "(F)人员情况评分 - JSON数据", position = 53)
    private String fJson;

    /** (G)招投标部评分 - 基础分值 */
    @ApiModelProperty(value = "(G)招投标部评分 - 基础分值", position = 54)
    private BigDecimal gBaseScore;

    /** (G)招投标部评分 - 权重 */
    @ApiModelProperty(value = "(G)招投标部评分 - 权重", position = 55)
    private BigDecimal gWeight;

    /** (G)招投标部评分 - 加权后分值 */
    @ApiModelProperty(value = "(G)招投标部评分 - 加权后分值", position = 56)
    private BigDecimal gWeightScore;

    /** (G)招投标部评分 - 备注 */
    @ApiModelProperty(value = "(G)招投标部评分 - 备注", position = 57)
    private String gMemo;

    /** (G)招投标部评分 - JSON数据 */
    @ApiModelProperty(value = "(G)招投标部评分 - JSON数据", position = 58)
    private String gJson;

    /** (H)技术质量中心/总承包（时代智能建造公司）评分 - 评分类型 */
    @ApiModelProperty(value = "(H)技术质量中心/总承包（时代智能建造公司）评分 - 评分类型", position = 59)
    private String hType;

    /** (H)技术质量中心/总承包（时代智能建造公司）评分 - 时代智能建造公司评分 */
    @ApiModelProperty(value = "(H)技术质量中心/总承包（时代智能建造公司）评分 - 时代智能建造公司评分", position = 60)
    private BigDecimal hZnjzgsScore;

    /** (H)技术质量中心/总承包（时代智能建造公司）评分 - 技术质量中心评分 */
    @ApiModelProperty(value = "(H)技术质量中心/总承包（时代智能建造公司）评分 - 技术质量中心评分", position = 61)
    private BigDecimal hJszlzxScore;

    /** (H)技术质量中心/总承包（时代智能建造公司）评分 - 基础分值 */
    @ApiModelProperty(value = "(H)技术质量中心/总承包（时代智能建造公司）评分 - 基础分值", position = 62)
    private BigDecimal hBaseScore;

    /** (H)技术质量中心/总承包（时代智能建造公司）评分 - 权重 */
    @ApiModelProperty(value = "(H)技术质量中心/总承包（时代智能建造公司）评分 - 权重", position = 63)
    private BigDecimal hWeight;

    /** (H)技术质量中心/总承包（时代智能建造公司）评分 - 加权后分值 */
    @ApiModelProperty(value = "(H)技术质量中心/总承包（时代智能建造公司）评分 - 加权后分值", position = 64)
    private BigDecimal hWeightScore;

    /** (H)技术质量中心/总承包（时代智能建造公司）评分 - 备注 */
    @ApiModelProperty(value = "(H)技术质量中心/总承包（时代智能建造公司）评分 - 备注", position = 65)
    private String hMemo;

    /** (H)技术质量中心/总承包（时代智能建造公司）评分 - JSON数据 */
    @ApiModelProperty(value = "(H)技术质量中心/总承包（时代智能建造公司）评分 - JSON数据", position = 66)
    private String hJson;

    /** (I)加分与扣分 - 加分 */
    @ApiModelProperty(value = "(I)加分与扣分 - 加分", position = 67)
    private BigDecimal iAddScore;

    /** (I)加分与扣分 - 加分 - 理由 */
    @ApiModelProperty(value = "(I)加分与扣分 - 加分 - 理由", position = 68)
    private String iAddMemo;

    /** (I)加分与扣分 - 扣分 */
    @ApiModelProperty(value = "(I)加分与扣分 - 扣分", position = 69)
    private BigDecimal iSubtractScore;

    /** (I)加分与扣分 - 扣分 - 理由 */
    @ApiModelProperty(value = "(I)加分与扣分 - 扣分 - 理由", position = 70)
    private String iSubtractMemo;

    /** (I)加分与扣分 - JSON数据 */
    @ApiModelProperty(value = "(I)加分与扣分 - JSON数据", position = 71)
    private String iJson;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 72)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 73)
    private String memo;

    /** SN */
    @ApiModelProperty(value = "SN", position = 81)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 82)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 83)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 84)
    private String status;

}
