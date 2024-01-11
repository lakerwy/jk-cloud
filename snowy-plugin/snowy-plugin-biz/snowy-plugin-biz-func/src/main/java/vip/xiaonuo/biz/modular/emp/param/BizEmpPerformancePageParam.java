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
package vip.xiaonuo.biz.modular.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-员工绩效考核-季度评分审批流程查询参数
 *
 * @author yong.ye
 * @date  2023/09/19 08:45
 **/
@Getter
@Setter
public class BizEmpPerformancePageParam {

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

    /** 用户Id */
    @ApiModelProperty(value = "用户Id")
    private String userId;

    /** 用户名 */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /** 组织Id */
    @ApiModelProperty(value = "组织Id")
    private String orgId;

    /** 年度(如：2023) */
    @ApiModelProperty(value = "年度(如：2023)")
    private String year;

    /** 季度(如：2) */
    @ApiModelProperty(value = "季度(如：2)")
    private String season;

    /** 员工季度-绩效等级 */
    @ApiModelProperty(value = "员工季度-绩效等级")
    private String empSeasonLevel;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

    /**一级部门id*/
    @ApiModelProperty(value = "一级部门Id", position = 15)
    private String firstOrgId;

}
