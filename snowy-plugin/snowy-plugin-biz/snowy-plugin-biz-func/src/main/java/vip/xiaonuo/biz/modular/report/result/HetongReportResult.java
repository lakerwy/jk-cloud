package vip.xiaonuo.biz.modular.report.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author yong.ye
 * @date 2023/7/10 9:50
 */

@Getter
@Setter
public class HetongReportResult {
    /** 表头配置  */
    @ApiModelProperty(value = "表头配置 ")
    private List<HetongReportColumnsItem> columns;

    /** 表数据  */
    @ApiModelProperty(value = "表数据 ")
    public List<Map<String,String>> tableDataList;

}

