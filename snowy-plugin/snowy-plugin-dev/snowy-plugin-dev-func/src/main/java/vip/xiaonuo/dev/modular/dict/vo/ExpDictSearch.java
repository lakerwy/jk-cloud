package vip.xiaonuo.dev.modular.dict.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 费用报销单明细科目搜索
 */

@Getter
@Setter
public class ExpDictSearch {

    /** 字典类别：费用报销科目 FINCOST 差旅费用报销科目 FINTRAVAL */
    @ApiModelProperty(value = "字典类别：费用报销科目 FINCOST 差旅费用报销科目 FINTRAVAL ",required = true)
    @NotBlank(message = "字典类别不能为空")
    private String type;

    /** 字典文字关键词 */
    @ApiModelProperty(value = "字典文字关键词")
    private String searchKey;
}
