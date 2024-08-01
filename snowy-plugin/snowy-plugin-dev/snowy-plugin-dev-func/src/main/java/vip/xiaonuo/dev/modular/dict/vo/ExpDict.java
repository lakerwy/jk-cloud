package vip.xiaonuo.dev.modular.dict.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 报销业务字段 明细科目 列表
 */

@Getter
@Setter
public class ExpDict {
    /** 父id - 一级内容 */
    @ApiModelProperty(value = "父id - 一级内容 ", position = 1)
    private String parentId;

    /** id - 二级内容 如：业务招待费|项目招待费 */
    @ApiModelProperty(value = "id - 二级内容 如：业务招待费|项目招待费", position = 4)
    private String id;

    /** 字典文字 - 二级内容  */
    @ApiModelProperty(value = "字典文字 - 二级内容 ", position = 5)
    private String dictLabel;

    /** 字典值 - 二级内容  */
    @ApiModelProperty(value = "字典值 - 二级内容 ", position = 6)
    private String dictValue;

    /** 分类 - 二级内容  */
    @ApiModelProperty(value = "分类 - 二级内容 ", position = 7)
    private String category;

    /** 排序码 - 二级内容 */
    @ApiModelProperty(value = "排序码 - 二级内容 ", position = 8)
    private Integer sortCode;

    /** 扩展信息 - 二级内容 说明（气泡提示内容）*/
    @ApiModelProperty(value = "扩展信息 - 二级内容 说明（气泡提示内容）", position = 9)
    private String extJson;

    /** 父-字典文字 - 一级内容 */
    @ApiModelProperty(value = "父-字典文字 - 一级内容 ", position = 10)
    private String parentDictLabel;

    /** 父-字典值 - 一级内容 */
    @ApiModelProperty(value = "父-字典值 - 一级内容 ", position = 11)
    private String parentDictValue;

    /** 排序码 - 二级内容 */
    @ApiModelProperty(value = "排序码 - 二级内容 ", position = 12)
    private Integer parentSortCode;
}
