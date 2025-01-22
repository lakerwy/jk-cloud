package vip.xiaonuo.biz.modular.crm.subcontract.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CheckResult {

    /** 是否存在重复 */
    @ApiModelProperty(value = "是否存在重复")
    private Boolean isExist;

    /** 重复信息集合 */
    @ApiModelProperty(value = "重复信息集合")
    private List<String> msgList;

}
