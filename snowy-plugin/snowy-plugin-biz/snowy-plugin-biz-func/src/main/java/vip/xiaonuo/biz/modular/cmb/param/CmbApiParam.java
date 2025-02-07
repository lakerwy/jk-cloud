package vip.xiaonuo.biz.modular.cmb.param;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmbApiParam {
    @ApiModelProperty(value = "接口编码（例如：DCLISMOD）", position = 1)
    private String funcode;

    @ApiModelProperty(value = "请求参数body", position = 2)
    private JSONObject body;
}
