package vip.xiaonuo.sys.modular.hr.icManage.param;

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
 * 工商管理添加参数
 *
 * @author pzh
 * @date  2025/02/25 16:33
 **/
@Getter
@Setter
public class IcManageAddParam {

    /** 公司名称 */
    @ApiModelProperty(value = "公司名称", position = 2)
    private String companyName;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 3)
    private String type;

    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码", position = 4)
    private String corpCode;

    /** 注册资本(元) */
    @ApiModelProperty(value = "注册资本(元)", position = 5)
    private BigDecimal registeredCapital;

    /** 经营范围 */
    @ApiModelProperty(value = "经营范围", position = 6)
    private String businessScope;

    /** 实缴资本(元) */
    @ApiModelProperty(value = "实缴资本(元)", position = 7)
    private BigDecimal paidinCapital;

    /** 住所 */
    @ApiModelProperty(value = "住所", position = 8)
    private String address;

    /** 成立日期 */
    @ApiModelProperty(value = "成立日期", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date establishTime;

    /** 法定代表人 */
    @ApiModelProperty(value = "法定代表人", position = 10)
    private String legalRepresentative;

    /** 法定代表人身份信息 */
    @ApiModelProperty(value = "法定代表人身份信息", position = 11)
    private String legalRepresentativeId;

    /** 成立日期 */
    @ApiModelProperty(value = "经营期限-开始时间", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date businessTermStart;

    /** 成立日期 */
    @ApiModelProperty(value = "经营期限-结束时间", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date businessTermEnd;

    /** 股东信息JSON */
    @ApiModelProperty(value = "股东信息JSON", position = 14)
    private String shareholderInfoJson;

    /** 主要人员JSON */
    @ApiModelProperty(value = "主要人员JSON", position = 15)
    private String mainPersonnelJson;

    /** 工商变更记录JSON */
    @ApiModelProperty(value = "工商变更记录JSON", position = 16)
    private String icUpdateRecordJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 19)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 20)
    private String attachments;
}
