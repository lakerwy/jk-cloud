package vip.xiaonuo.biz.modular.cmb.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CmbDctrspdfParam {

    @ApiModelProperty(value = "输入接口", position = 1)
    private List<CmbDctrspdfParam.Sdktsinfx> sdktsinfx;

    @ApiModelProperty(value = "续传接口", position = 2)
    private List<CmbDctrspdfParam.Ntqacctny> ntqacctny;

    @Getter
    @Setter
    static class Sdktsinfx {
        @ApiModelProperty(value = "分行号", position = 1)
        private String bbknbr ;

        @ApiModelProperty(value = "账号", position = 2)
        private String accnbr;

        @ApiModelProperty(value = "起始日期", position = 3)
        private String bgndat;

        @ApiModelProperty(value = "结束日期", position = 4)
        private String enddat;

        @ApiModelProperty(value = "最小金额", position = 5)
        private String lowamt;

        @ApiModelProperty(value = "最大金额", position = 6)
        private String hghamt;

        @ApiModelProperty(value = "借贷码", position = 7)
        private String amtcdr;

        @ApiModelProperty(value = "通知地址URL类型", position = 8)
        private String genble;
    }

    @Getter
    @Setter
    static class Ntqacctny {
        @ApiModelProperty(value = "续传字段", position = 1)
        private String ctndta ;

        @ApiModelProperty(value = "保留字段", position = 2)
        private String pagnbr;
    }
}
