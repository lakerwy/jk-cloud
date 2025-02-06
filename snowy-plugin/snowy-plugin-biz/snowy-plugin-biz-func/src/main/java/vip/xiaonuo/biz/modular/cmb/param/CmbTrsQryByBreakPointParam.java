package vip.xiaonuo.biz.modular.cmb.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CmbTrsQryByBreakPointParam {

    @ApiModelProperty(value = "输入接口", position = 1)
    @JsonProperty(value = "TRANSQUERYBYBREAKPOINT_X1")
    private List<CmbTrsQryByBreakPointParam.TRANSQUERYBYBREAKPOINT_X1> TRANSQUERYBYBREAKPOINT_X1;

    @ApiModelProperty(value = "输入接口", position = 2)
    @JsonProperty(value = "TRANSQUERYBYBREAKPOINT_Y1")
    private List<CmbTrsQryByBreakPointParam.TRANSQUERYBYBREAKPOINT_Y1> TRANSQUERYBYBREAKPOINT_Y1;

    @Getter
    @Setter
    static class TRANSQUERYBYBREAKPOINT_X1 {
        @ApiModelProperty(value = "户口号", position = 1)
        private String cardNbr ;

        @ApiModelProperty(value = "开始日期", position = 2)
        private String beginDate;

        @ApiModelProperty(value = "结束日期", position = 3)
        private String endDate;

        @ApiModelProperty(value = "起始记账序号", position = 4)
        private String transactionSequence;

        @ApiModelProperty(value = "币种", position = 5)
        private String currencyCode;

        @ApiModelProperty(value = "继续查询账号", position = 6)
        private String queryAcctNbr;

        @ApiModelProperty(value = "保留字段", position = 7)
        private String reserve;

        @ApiModelProperty(value = "借贷码", position = 8)
        private String loanCode;
    }

    @Getter
    @Setter
    static class TRANSQUERYBYBREAKPOINT_Y1 {
        @ApiModelProperty(value = "账号", position = 1)
        private String acctNbr ;

        @ApiModelProperty(value = "交易日期", position = 2)
        private String transDate;

        @ApiModelProperty(value = "期望下一记账序号", position = 3)
        private String expectNextSequence;
    }
}
