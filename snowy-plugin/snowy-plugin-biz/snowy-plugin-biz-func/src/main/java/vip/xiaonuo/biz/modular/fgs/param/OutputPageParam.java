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
package vip.xiaonuo.biz.modular.fgs.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 业务表-FGS-建筑业产值明细表查询参数
 *
 * @author yong.ye
 * @date  2023/08/02 10:30
 **/
@Getter
@Setter
public class OutputPageParam {

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

    /** 填报年 */
    @ApiModelProperty(value = "填报年")
    private Integer year;

    /** 填报月 */
    @ApiModelProperty(value = "填报月")
    private Integer month;

    /** 合同编号|项目编号 */
    @ApiModelProperty(value = "合同编号|项目编号")
    private String contractNo;

    /** 合同名称|项目名称 */
    @ApiModelProperty(value = "合同名称|项目名称")
    private String projectName;

    /** 合同甲方名称 */
    @ApiModelProperty(value = "合同甲方名称")
    private String counterparty;

    /** 责任部门 */
    @ApiModelProperty(value = "责任部门")
    private String companyOrDepartment;

    /** 项目所在省份 */
    @ApiModelProperty(value = "项目所在省份")
    private String province;

    /** 项目类型 */
    @ApiModelProperty(value = "项目类型")
    private String secondLevel;

    /** 合同盖章日期开始 */
    @ApiModelProperty(value = "合同盖章日期开始")
    private String startSealDate;

    /** 合同盖章日期结束 */
    @ApiModelProperty(value = "合同盖章日期结束")
    private String endSealDate;

    /** 填报人 */
    @ApiModelProperty(value = "填报人")
    private String operator;

    /** 组织Id集合 (填报人所属分公司 orgId) */
    @ApiModelProperty(value = "组织Id集合 (填报人所属分公司 orgId) ")
    private List<String> orgId;
}
