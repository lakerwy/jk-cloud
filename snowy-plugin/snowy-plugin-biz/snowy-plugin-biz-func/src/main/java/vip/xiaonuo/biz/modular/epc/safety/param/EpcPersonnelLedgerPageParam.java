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
package vip.xiaonuo.biz.modular.epc.safety.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * EPC-现场人员台账信息表查询参数
 *
 * @author yong.ye
 * @date  2023/09/05 14:54
 **/
@Getter
@Setter
public class EpcPersonnelLedgerPageParam {

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

    /** 租户Id */
    @ApiModelProperty(value = "租户Id")
    private String tenantId;

    /** 项目ID(外键) */
    @ApiModelProperty(value = "项目ID(外键)")
    private String projectId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    /** 性别 */
    @ApiModelProperty(value = "性别")
    private String gender;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /** 在职状态 */
    @ApiModelProperty(value = "在职状态")
    private String hireStatus;

    /** 所属单位 */
    @ApiModelProperty(value = "所属单位")
    private String belongCompany;

    /** 职务工种 */
    @ApiModelProperty(value = "职务工种")
    private String workType;

    /** 进场时间开始 */
    @ApiModelProperty(value = "进场时间开始")
    private String startEnterTime;

    /** 进场时间结束 */
    @ApiModelProperty(value = "进场时间结束")
    private String endEnterTime;

    /** 退场时间开始 */
    @ApiModelProperty(value = "退场时间开始")
    private String startOutTime;

    /** 退场时间结束 */
    @ApiModelProperty(value = "退场时间结束")
    private String endOutTime;

    /** 是否在保 */
    @ApiModelProperty(value = "是否在保")
    private String isInsured;

    /** 是否进行了三级安全教育 */
    @ApiModelProperty(value = "是否进行了三级安全教育")
    private String isSafety;

}
