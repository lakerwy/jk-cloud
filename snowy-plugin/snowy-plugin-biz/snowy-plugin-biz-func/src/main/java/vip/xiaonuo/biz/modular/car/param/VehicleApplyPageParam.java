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
package vip.xiaonuo.biz.modular.car.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * CAR-用车申请单查询参数
 *
 * @author yong.ye
 * @date  2023/11/08 16:04
 **/
@Getter
@Setter
public class VehicleApplyPageParam {

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

    /** SN */
    @ApiModelProperty(value = "SN")
    private String sn;

    /** 申请人 */
    @ApiModelProperty(value = "申请人")
    private String opt;

    /** 用车人 */
    @ApiModelProperty(value = "用车人")
    private String users;

    /** 所属部门 */
    @ApiModelProperty(value = "所属部门")
    private String dept;

    /** 指派车辆 */
    @ApiModelProperty(value = "指派车辆")
    private String vehicle;

    /** 指派司机 */
    @ApiModelProperty(value = "指派司机")
    private String driver;



    /** 用户UserId【用车人+审批人+派车员】 */
    @ApiModelProperty(value = "用户UserId【用车人+审批人+派车员】")
    private String userId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

    /** 用车申请单状态（1-待审核 2-待派车 3-已派车 4-已完成 5-已取消 6-已拒绝 */
    @ApiModelProperty(value = "用车申请单状态（1-待审核 2-待派车 3-已派车 4-已完成 5-已取消 6-已拒绝）")
    private String applyStatus;

    /** 针对不同数据范围，传入状态集合 */
    @ApiModelProperty(value = "针对不同数据范围，传入状态集合")
    private List<String> filterStatusIn;

    /** 针对不同数据范围，传入排除的状态集合 */
    @ApiModelProperty(value = "针对不同数据范围，传入排除的状态集合")
    private List<String> filterStatusNotIn;

    /** 用于查看，我的申请单 ApplyUserId【用车人】 */
    @ApiModelProperty(value = "用于查看，我的申请单 ApplyUserId【用车人】")
    private String applyUserId;
}
