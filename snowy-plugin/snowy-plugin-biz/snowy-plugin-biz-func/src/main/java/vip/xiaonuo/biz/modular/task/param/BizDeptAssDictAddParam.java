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
package vip.xiaonuo.biz.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-部门绩效考核-数据字典添加参数
 *
 * @author yong.ye
 * @date  2023/08/08 17:19
 **/
@Getter
@Setter
public class BizDeptAssDictAddParam {

    /** 字典类型（加分项、减分项） */
    @ApiModelProperty(value = "字典类型（加分项、减分项）", position = 2)
    private String dictType;

    /** 加分|减分类型 */
    @ApiModelProperty(value = "加分|减分类型", position = 3)
    private String dictName;

    /** 分值 */
    @ApiModelProperty(value = "分值", position = 4)
    private Double dictScore;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 5)
    private String dictDescription;

}
