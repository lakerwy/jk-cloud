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
package vip.xiaonuo.biz.modular.crm.rollinout.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CRM-合作伙伴人员转入转出查询参数
 *
 * @author yong.ye
 * @date  2023/06/30 16:56
 **/
@Getter
@Setter
public class RollinoutPageParam {

    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 合作伙伴人员信息Id主键（历史表RollInOut.FPID） */
    @ApiModelProperty(value = "合作伙伴人员信息Id主键（历史表RollInOut.FPID）")
    private String fpId;

    /** 流水号SN（历史表RollInOut.SN） */
    @ApiModelProperty(value = "流水号SN（历史表RollInOut.SN）")
    private String sn;

    /** 登记人（历史表RollInOut.Opt） */
    @ApiModelProperty(value = "登记人（历史表RollInOut.Opt）")
    private String opt;

    /** 登记日期（历史表RollInOut.OptDate）开始 */
    @ApiModelProperty(value = "登记日期（历史表RollInOut.OptDate）开始")
    private String startOptDate;

    /** 登记日期（历史表RollInOut.OptDate）结束 */
    @ApiModelProperty(value = "登记日期（历史表RollInOut.OptDate）结束")
    private String endOptDate;

    /** 操作（历史表RollInOut.Operate） */
    @ApiModelProperty(value = "操作（历史表RollInOut.Operate）")
    private String operate;

    /** 姓名（历史表RollInOut.Name） */
    @ApiModelProperty(value = "姓名（历史表RollInOut.Name）")
    private String name;

    /** 部门（历史表RollInOut.Dept） */
    @ApiModelProperty(value = "部门（历史表RollInOut.Dept）")
    private String dept;

    /** 职称（历史表RollInOut.Rank） */
    @ApiModelProperty(value = "职称（历史表RollInOut.Rank）")
    private String rank;

}
