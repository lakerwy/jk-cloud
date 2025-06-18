package vip.xiaonuo.biz.modular.orgPerf.teamAss.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 班子考核记录Id参数
 *
 * @author pzh
 * @date  2025/06/17 17:08
 **/
@Getter
@Setter
public class BizTeamAssRecordIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
