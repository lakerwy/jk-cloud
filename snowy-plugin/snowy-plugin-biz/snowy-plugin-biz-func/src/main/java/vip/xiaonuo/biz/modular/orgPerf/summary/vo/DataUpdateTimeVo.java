package vip.xiaonuo.biz.modular.orgPerf.summary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据更新时间查询
 *
 * @author pzh
 * @date  2025/07/03 15:16
 **/
@Getter
@Setter
public class DataUpdateTimeVo {

    /** 数据更新时间描述信息 */
    @ApiModelProperty(value = "数据更新时间描述信息")
    private String desc;

}
