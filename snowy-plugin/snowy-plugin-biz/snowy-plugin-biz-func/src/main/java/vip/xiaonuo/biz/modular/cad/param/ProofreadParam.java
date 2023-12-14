package vip.xiaonuo.biz.modular.cad.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class ProofreadParam {
    /** 流程类型：提资|校审 */
    @ApiModelProperty(value = "流程类型：提资|校审", position = 6)
    private String type;

    /** 审批类别：校审|校对|审批  */
    @ApiModelProperty(value = "审批类别：校审|校对|审批 ", position = 7)
    private String auditType;

//    /** 状态：校审|校对|审批 - 通过|退回 */
//    @ApiModelProperty(value = "状态：校审|校对|审批 - 通过|退回", position = 7)
//    private String status;

    /** 校审人 */
    @ApiModelProperty(value = "校审人", position = 8)
    private String auditor;

//    /** 校审时间 */
//    @ApiModelProperty(value = "校审时间", position = 9)
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date auditTime;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;
}
