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
package vip.xiaonuo.dev.modular.supervisionPlatform.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 市场监管公共服务平台-企业业绩技术指标信息表编辑参数
 *
 * @author ljy
 * @date  2024/01/11 10:57
 **/
@Getter
@Setter
public class TBCorpperFormanceEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 业绩记录编号 */
    @ApiModelProperty(value = "业绩记录编号", position = 2)
    private String perfNum;

    /** 项目编号 */
    @ApiModelProperty(value = "项目编号", position = 3)
    private String prjNum;

    /** 工程项目名称 */
    @ApiModelProperty(value = "工程项目名称", position = 4)
    private String perfName;

    /** 企业名称 */
    @ApiModelProperty(value = "企业名称", position = 5)
    private String corpName;

    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码", position = 6)
    private String corpCode;

    /** 业绩类型 */
    @ApiModelProperty(value = "业绩类型", position = 7)
    private Integer perfTypeNum;

    /** 业绩对应资质及等级 */
    @ApiModelProperty(value = "业绩对应资质及等级", position = 8)
    private String aptitudeContent;

    /** 工程项目规模等级及详细技术指标 */
    @ApiModelProperty(value = "工程项目规模等级及详细技术指标", position = 9)
    private String techParamInfo;

    /** 工作开始时间/施工许可开工时间 */
    @ApiModelProperty(value = "工作开始时间/施工许可开工时间", position = 10)
    private Date bDate;

    /** 工作结束时间/竣工验收结束时间 */
    @ApiModelProperty(value = "工作结束时间/竣工验收结束时间", position = 11)
    private Date eDate;

    /** 信息审核部门 */
    @ApiModelProperty(value = "信息审核部门", position = 12)
    private String checkDepartName;

    /** 信息审核人 */
    @ApiModelProperty(value = "信息审核人", position = 13)
    private String checkPersonName;

    /** 数据等级 */
    @ApiModelProperty(value = "数据等级", position = 14)
    private String dataLevel;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 15)
    private String extJson;

}
