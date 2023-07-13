package vip.xiaonuo.biz.modular.cms.article;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.cms.article.entity.ArticleAuth;

import java.util.Date;
import java.util.List;

/**
 * @author yong.ye
 * @date 2023/7/12 17:35
 */

@Getter
@Setter
public class ArticleInfoVo {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 流水号SN */
    @ApiModelProperty(value = "流水号SN", position = 2)
    private String sn;

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 3)
    private String title;

    /** 副标题 */
    @ApiModelProperty(value = "副标题", position = 4)
    private String subTitle;

    /** 栏目分类 */
    @ApiModelProperty(value = "栏目分类", position = 5)
    private String type;

    /** 文档标签 */
    @ApiModelProperty(value = "文档标签", position = 6)
    private String tags;

    /** 封面图片 */
    @ApiModelProperty(value = "封面图片", position = 7)
    private String coverPhoto;

    /** 内容详情 */
    @ApiModelProperty(value = "内容详情", position = 8)
    private String content;

    /** 点击量 */
    @ApiModelProperty(value = "点击量", position = 9)
    private Integer hits;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 10)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 权限配置集合 */
    @ApiModelProperty(value = "权限配置", position = 15)
    private List<ArticleAuth> articleAuths;

    /** 是否有权限 */
    @ApiModelProperty(value = "是否有权限", position = 16)
    private Integer isAuth;

    /** 是否上架，0: 未上架 1: 已上架 */
    @ApiModelProperty(value = "是否上架，0: 未上架 1: 已上架", position = 17)
    private Integer isShelf;
}
