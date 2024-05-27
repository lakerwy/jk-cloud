package vip.xiaonuo.biz.modular.develop.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.develop.entity.DevForm;
import vip.xiaonuo.biz.modular.develop.entity.DevFormItem;

import java.util.List;


@Getter
@Setter
public class DevFormVo extends DevForm {

    /** 需求拆解 - 集合 */
    @ApiModelProperty(value = "需求拆解 - 集合", position = 100)
    private List<DevFormItem> formItemList;
}
