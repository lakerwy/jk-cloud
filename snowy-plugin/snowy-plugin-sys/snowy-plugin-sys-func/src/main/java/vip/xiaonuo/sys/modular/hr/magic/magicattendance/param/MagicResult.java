package vip.xiaonuo.sys.modular.hr.magic.magicattendance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MagicResult {

    @ApiModelProperty(value = "响应码", position = 1)
    private String result;
    @ApiModelProperty(value = "响应信息", position = 1)
    private String message;

    public MagicResult(){}

    public MagicResult(String result,String message){
        this.result = result;
        this.message = message;
    }
}
