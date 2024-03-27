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
package vip.xiaonuo.sys.modular.dept.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 部门报销公示编辑参数
 *
 * @author yong.ye
 * @date  2024/03/27 09:04
 **/
@Getter
@Setter
public class ExpenseNoticeEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 经营单位 */
    @ApiModelProperty(value = "经营单位", position = 2)
    private String unitName;

    /** 时间范围 */
    @ApiModelProperty(value = "时间范围", position = 3)
    private String timeRange;

    /** 办公费 */
    @ApiModelProperty(value = "办公费", position = 4)
    private String officeAmount;

    /** 人力成本 */
    @ApiModelProperty(value = "人力成本", position = 5)
    private String laborAmount;

    /** 交通费 */
    @ApiModelProperty(value = "交通费", position = 6)
    private String trafficAmount;

    /** 招待费 */
    @ApiModelProperty(value = "招待费", position = 7)
    private String enterAmount;

    /** 评审费/服务费 */
    @ApiModelProperty(value = "评审费/服务费", position = 8)
    private String serviceAmount;

    /** 其他费用 */
    @ApiModelProperty(value = "其他费用", position = 9)
    private String otherAmount;

    /** 合计 */
    @ApiModelProperty(value = "合计", position = 10)
    private String totalAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String memo;

    /** 数据权限控制字段【经营单位 - 系统组织架构 - orgId】 */
    @ApiModelProperty(value = "数据权限控制字段【经营单位 - 系统组织架构 - orgId】", position = 18)
    private String dataScopeId;

    /** 留痕备注 */
    @ApiModelProperty(value = "留痕备注", position = 19)
    private String note;

}
