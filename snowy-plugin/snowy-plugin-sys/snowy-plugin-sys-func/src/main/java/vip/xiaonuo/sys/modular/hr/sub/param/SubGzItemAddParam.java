package vip.xiaonuo.sys.modular.hr.sub.param;

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
 * 分公司-工资发放 - 工资|奖金明细添加参数
 *
 * @author yong.ye
 * @date  2025/02/06 09:11
 **/
@Getter
@Setter
public class SubGzItemAddParam {

    /** 工资发放流程主键id */
    @ApiModelProperty(value = "工资发放流程主键id", position = 2)
    private String subGzId;

    /** 明细类别：工资|奖金|经济补偿金 */
    @ApiModelProperty(value = "明细类别：工资|奖金|经济补偿金", position = 3)
    private String type;

    /** 所属月份 */
    @ApiModelProperty(value = "所属月份", position = 4)
    private String yM;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 5)
    private String userName;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 6)
    private String idCardNumber;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 7)
    private String orgName;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 8)
    private String duty;

    /** 用工性质 */
    @ApiModelProperty(value = "用工性质", position = 9)
    private String nature;

    /** 应发工资合计|应发奖金合计|应发金额 */
    @ApiModelProperty(value = "应发工资合计|应发奖金合计|应发金额", position = 10)
    private BigDecimal yf;

    /** 五险（个人部分） */
    @ApiModelProperty(value = "五险（个人部分）", position = 11)
    private BigDecimal ins;

    /** 公积金（个人部分） */
    @ApiModelProperty(value = "公积金（个人部分）", position = 12)
    private BigDecimal gjj;

    /** 本月应扣税额|应扣税额|个税 */
    @ApiModelProperty(value = "本月应扣税额|应扣税额|个税", position = 13)
    private BigDecimal yk;

    /** 实发工资|实发奖金|实际补偿金额 */
    @ApiModelProperty(value = "实发工资|实发奖金|实际补偿金额", position = 14)
    private BigDecimal sf;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 15)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 16)
    private String attach;

    /** 入司时间（格式：yyyy-MM-dd） */
    @ApiModelProperty(value = "入司时间（格式：yyyy-MM-dd）", position = 40)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 补偿基数 */
    @ApiModelProperty(value = "补偿基数", position = 41)
    private BigDecimal bcjs;

    /** 补偿基数 */
    @ApiModelProperty(value = "补偿系数", position = 42)
    private BigDecimal bcxs;

}
