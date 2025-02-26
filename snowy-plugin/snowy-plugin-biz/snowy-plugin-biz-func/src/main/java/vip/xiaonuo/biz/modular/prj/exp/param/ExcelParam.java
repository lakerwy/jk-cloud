package vip.xiaonuo.biz.modular.prj.exp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.exp.vo.FlwComment;

import javax.validation.constraints.NotBlank;
import java.util.List;


@Getter
@Setter
public class ExcelParam {

    /** 类型：FEE 费用报销  TRAVEL 差旅费用报销 CASH 借支单 */
    @ApiModelProperty(value = "类型：FEE 费用报销  TRAVEL 差旅费用报销 CASH 借支单",required = true)
    @NotBlank(message = "类型不能为空")
    private String excelType;


    /** 请求参数json字符串 后端直接解析为对应实体 */
    @ApiModelProperty(value = "请求参数json字符串 后端直接解析为对应实体",required = true)
    @NotBlank(message = "参数不能为空")
    private String paramJson;


    /** 审批意见 - 集合 */
    @ApiModelProperty(value = "审批意见 - 集合")
    private List<FlwComment> commentList;

}
