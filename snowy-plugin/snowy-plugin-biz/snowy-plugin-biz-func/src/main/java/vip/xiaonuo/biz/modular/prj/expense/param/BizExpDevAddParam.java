package vip.xiaonuo.biz.modular.prj.expense.param;

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
 * 财务管理 - 费用报销 - 研发项目信息表添加参数
 *
 * @author yong.ye
 * @date  2024/08/01 11:22
 **/
@Getter
@Setter
public class BizExpDevAddParam {

    /** 部门Id */
    @ApiModelProperty(value = "部门Id", position = 2)
    private String deptId;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 3)
    private String dept;

    /** 编号 */
    @ApiModelProperty(value = "编号", position = 4)
    private String code;

    /** 在研课题 */
    @ApiModelProperty(value = "在研课题", position = 5)
    private String subject;

    /** 起-时间 */
    @ApiModelProperty(value = "起-时间", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 止-时间 */
    @ApiModelProperty(value = "止-时间", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 9)
    private String attach;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 17)
    private String extJson;

}
