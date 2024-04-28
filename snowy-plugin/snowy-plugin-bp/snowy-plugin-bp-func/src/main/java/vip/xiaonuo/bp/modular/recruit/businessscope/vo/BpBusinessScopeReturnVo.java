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
package vip.xiaonuo.bp.modular.recruit.businessscope.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 经营范围分类信息表返回参数
 *
 * @author hxw
 * @date  2024/04/17 15:26
 **/
@Getter
@Setter
public class BpBusinessScopeReturnVo {

    /** 一级分类ID */
    @ApiModelProperty(value = "一级分类ID")
    private String stairclassifyId;

    /** 一级分类名称 */
    @ApiModelProperty(value = "一级分类名称")
    private String stairclassifyName;

    /** 二级分类ID */
    @ApiModelProperty(value = "二级分类ID")
    private String accessclassifyId;

    /** 二级分类名称 */
    @ApiModelProperty(value = "二级分类名称")
    private String accessclassifyName;

    /** 三级分类ID */
    @ApiModelProperty(value = "三级分类ID")
    private String reclassifyId;

    /** 三级分类名称 */
    @ApiModelProperty(value = "三级分类名称")
    private String reclassifyName;

    /** 供应商ID */
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID")
    private String tenantId;

}
