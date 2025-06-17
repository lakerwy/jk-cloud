package vip.xiaonuo.biz.modular.orgPerf.product.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImportProductParam {

    /** 产值情况-集合 */
    @ApiModelProperty(value = "产值情况-集合", position = 1)
    private List<ImportProductData> importData;

}
