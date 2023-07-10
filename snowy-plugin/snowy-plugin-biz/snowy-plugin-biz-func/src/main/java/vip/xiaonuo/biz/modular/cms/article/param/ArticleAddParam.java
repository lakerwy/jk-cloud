/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.cms.article.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CMS-文章管理添加参数
 *
 * @author yong.ye
 * @date  2023/07/10 14:36
 **/
@Getter
@Setter
public class ArticleAddParam {

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

}
