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
package vip.xiaonuo.biz.modular.bid.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 投标决策 - 申请流程查询参数
 *
 * @author yong.ye
 * @date  2024/06/21 10:30
 **/
@Getter
@Setter
public class BidDecisionPageParam {

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

    /** 项目名称：来源于立项 */
    @ApiModelProperty(value = "项目名称：来源于立项")
    private String prjName;

    /** 立项申请信息：来源于立项 */
    @ApiModelProperty(value = "立项申请信息：来源于立项")
    private String prjSn;

    /** 立项申请人：来源于立项 */
    @ApiModelProperty(value = "立项申请人：来源于立项")
    private String prjApplyUser;

    /** 项目承接类型(自营|零星合作|分公司) */
    @ApiModelProperty(value = "项目承接类型(自营|零星合作|分公司)")
    private String contractType;

    /** 是否我方牵头：是|否 */
    @ApiModelProperty(value = "是否我方牵头：是|否")
    private String isHead;

    /** 流程 SN */
    @ApiModelProperty(value = "流程 SN")
    private String sn;

    /** 流程 状态 */
    @ApiModelProperty(value = "流程 状态")
    private String status;

    /** 申请部门 */
    @ApiModelProperty(value = "申请部门")
    private String dept;

    /** 项目承接部门 */
    @ApiModelProperty(value = "项目承接部门")
    private String acceptDept;

    /** 项目来源 */
    @ApiModelProperty(value = "项目来源")
    private String prjSource;

}
