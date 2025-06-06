package vip.xiaonuo.biz.modular.prj.fin.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ImportParam<T> {

    /** 导入数据集 */
    @ApiModelProperty(value = "导入数据集", position = 1)
    private List<T> importList;

}
