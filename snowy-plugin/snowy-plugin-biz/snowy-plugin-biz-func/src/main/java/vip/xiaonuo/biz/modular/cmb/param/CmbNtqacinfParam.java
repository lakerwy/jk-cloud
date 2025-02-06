package vip.xiaonuo.biz.modular.cmb.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CmbNtqacinfParam {

    @ApiModelProperty(value = "ntqacinfx", position = 1)
    private List<Ntqacinfx> ntqacinfx;

    @Getter
    @Setter
    static class Ntqacinfx{
        @ApiModelProperty(value = "分行号", position = 1)
        private String bbknbr;

        @ApiModelProperty(value = "账号", position = 2)
        private String accnbr;

        @ApiModelProperty(value = "币种", position = 3)
        private String ccynbr;
    }
}
