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

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-FGS-建筑业产值明细表编辑参数
 *
 * @author yong.ye
 * @date  2023/08/02 10:30
 **/
@Getter
@Setter
public class OutputEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 填报年 */
    @ApiModelProperty(value = "填报年", position = 2)
    private Integer year;

    /** 填报月 */
    @ApiModelProperty(value = "填报月", position = 3)
    private Integer month;

    /** 合同编号|项目编号 */
    @ApiModelProperty(value = "合同编号|项目编号", position = 4)
    private String contractNo;

    /** 合同名称|项目名称 */
    @ApiModelProperty(value = "合同名称|项目名称", position = 5)
    private String projectName;

    /** 合同甲方名称 */
    @ApiModelProperty(value = "合同甲方名称", position = 6)
    private String counterparty;

    /** 责任部门 */
    @ApiModelProperty(value = "责任部门", position = 7)
    private String companyOrDepartment;

    /** 项目所在省份 */
    @ApiModelProperty(value = "项目所在省份", position = 8)
    private String province;

    /** 项目类型 */
    @ApiModelProperty(value = "项目类型", position = 9)
    private String secondLevel;

    /** 合同盖章日期 */
    @ApiModelProperty(value = "合同盖章日期", position = 10)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sealDate;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 11)
    private BigDecimal contractAmount;

    /** 项目竣工日期（预计） */
    @ApiModelProperty(value = "项目竣工日期（预计）", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date preCompleDate;

    /** 项目竣工日期 */
    @ApiModelProperty(value = "项目竣工日期", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date compleDate;

    /** 累计已完成产值 */
    @ApiModelProperty(value = "累计已完成产值", position = 14)
    private BigDecimal accCompletedOutput;

    /** 本季完成产值 */
    @ApiModelProperty(value = "本季完成产值", position = 15)
    private BigDecimal tmpCompletedOutput;

    /** 填报人 */
    @ApiModelProperty(value = "填报人", position = 16)
    private String operator;

    /** 组织Id (填报人所属分公司 orgId) */
    @ApiModelProperty(value = "组织Id (填报人所属分公司 orgId) ", position = 17)
    private String orgId;

}
