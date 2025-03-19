package vip.xiaonuo.biz.modular.crm.hetong.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;


@Getter
@Setter
public class HtParam {
    /** (D)承包期内实际签订合同额评分 - 当期考核日期范围 - 开始 */
    @ApiModelProperty(value = "(D)承包期内实际签订合同额评分 - 当期考核日期范围 - 开始", position = 1)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dKhFrom;

    /** (D)承包期内实际签订合同额评分 - 当期考核日期范围 - 结束 */
    @ApiModelProperty(value = "(D)承包期内实际签订合同额评分 - 当期考核日期范围 - 结束", position = 2)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dKhTo;

    /** 分公司id */
    @ApiModelProperty(value = "分公司id", required = true, position = 3)
    @NotBlank(message = "分公司id不可为空")
    private String branchId;

    /** 分公司名称 */
    @ApiModelProperty(value = "分公司名称", required = true, position = 4)
    @NotBlank(message = "分公司名称不可为空")
    private String branchName;
}
