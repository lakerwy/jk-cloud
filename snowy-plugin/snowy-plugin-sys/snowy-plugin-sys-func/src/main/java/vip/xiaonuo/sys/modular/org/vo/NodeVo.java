package vip.xiaonuo.sys.modular.org.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/2/19 9:05
 */
@Getter
@Setter
public class NodeVo {

    /** id */
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 2)
    private String tenantId;

    /** 父id */
    @ApiModelProperty(value = "父id", position = 3)
    private String parentId;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 4)
    private String name;

    /** 分类 */
    @ApiModelProperty(value = "分类", position = 5)
    private String category;

    /** 排序码 */
    @ApiModelProperty(value = "排序码", position = 6)
    private Integer sortCode;

    @ApiModelProperty(value = "头像", position = 7)
    private String avatar;

    @ApiModelProperty(value = "头像Url", position = 7)
    private String avatarUrl;
}
