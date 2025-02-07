package vip.xiaonuo.biz.modular.cmb.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CmbNtqabinfParam {

    @ApiModelProperty(value = "ntqabinfy", position = 1)
    private List<Ntqabinfy> ntqabinfy;

    @Getter
    @Setter
    static class Ntqabinfy{
        @ApiModelProperty(value = "分行号", position = 1)
        private String bbknbr;

        @ApiModelProperty(value = "账号", position = 2)
        private String accnbr;

        @ApiModelProperty(value = "起始日期", position = 3)
        private String bgndat;

        @ApiModelProperty(value = "结束日期", position = 4)
        private String enddat;

        @ApiModelProperty(value = "币种", position = 5)
        private String ccynbr;
    }
}
