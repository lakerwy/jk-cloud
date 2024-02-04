package vip.xiaonuo.sys.modular.hr.certManage.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 混合搜索 实体结构
 */

@Getter
@Setter
public class HybridSearchParam {
    /** 混合搜索： 关键词 */
    @ApiModelProperty(value = "混合搜索： 关键词")
    private String searchKey;

    /** 混合搜索： 类别|等级|专业联动 */
    @ApiModelProperty(value = "混合搜索： 类别|等级|专业联动")
    private List<ThreeParam> threeParams;

    /** 混合搜索：  发证日期开始 */
    @ApiModelProperty(value = "混合搜索：  发证日期开始")
    private String startIssueDate;

    /** 混合搜索：  发证日期结束 */
    @ApiModelProperty(value = "混合搜索：  发证日期结束")
    private String endIssueDate;

    /** 混合搜索：  注册单位 */
    @ApiModelProperty(value = "混合搜索：  注册单位 ")
    private String unit;

    /** 证书模块：注册证书|培训证书 */
    @ApiModelProperty(value = "证书类别：注册类别|培训类别")
    private String type;
}
