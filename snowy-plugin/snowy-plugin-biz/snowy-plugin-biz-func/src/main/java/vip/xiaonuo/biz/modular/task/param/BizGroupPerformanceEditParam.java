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
 * 业务表-绩效考核-集团经营业绩绩点分编辑参数
 *
 * @author yong.ye
 * @date  2023/08/11 14:07
 **/
@Getter
@Setter
public class BizGroupPerformanceEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 考核年月 */
    @ApiModelProperty(value = "考核年月", position = 2)
    private String assYear;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 3)
    private String memo;

    /** 集团经营业绩绩点分 */
    @ApiModelProperty(value = "集团经营业绩绩点分", position = 9)
    private Double groupScore;

    /** 集团经营业绩完成率 */
    @ApiModelProperty(value = "集团经营业绩完成率", position = 10)
    private Double groupRate;

}
