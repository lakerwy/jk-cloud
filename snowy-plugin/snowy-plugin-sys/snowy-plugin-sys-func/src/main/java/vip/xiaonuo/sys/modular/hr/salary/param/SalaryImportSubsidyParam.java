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
package vip.xiaonuo.sys.modular.hr.salary.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 工资系统-补贴导入
 *
 * @author yong.ye
 * @date  2024/02/26 09:31
 **/
@Getter
@Setter
public class SalaryImportSubsidyParam {

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 4)
    private String no;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 5)
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 6)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 7)
    private String userName;

    /** 值班日期|课程日期|开工日期 */
    @ApiModelProperty(value = "值班日期|课程日期|开工日期", position = 8)
    private String btDate;

    /** 内部讲师级别|合同编号 */
    @ApiModelProperty(value = "内部讲师级别|合同编号", position = 9)
    private String btNo;

    /** 所讲课程名称|项目名称 */
    @ApiModelProperty(value = "所讲课程名称|项目名称", position = 10)
    private String btName;

    /** 参训人数（人）|项目所在地 */
    @ApiModelProperty(value = "参训人数（人）|项目所在地", position = 11)
    private String btContent;

    /** 评估分数（分） */
    @ApiModelProperty(value = "评估分数（分）", position = 12)
    private String btScore;

    /** 课时（精确到0.5小时）|外业天数 */
    @ApiModelProperty(value = "课时（精确到0.5小时）|外业天数", position = 13)
    private Float btNum;

    /** 竣工日期 */
    @ApiModelProperty(value = "竣工日期", position = 14)
    private String btFinishDate;

    /** 开始日期 */
    @ApiModelProperty(value = "开始日期", position = 15)
    private String btStartDate;

    /** 结束日期 */
    @ApiModelProperty(value = "结束日期", position = 16)
    private String btEndDate;

    /** 津贴标准（元/h）|伙食补贴标准 */
    @ApiModelProperty(value = "津贴标准（元/h）|伙食补贴标准", position = 17)
    private BigDecimal btStandard1;

    /** 伙食补贴小计 */
    @ApiModelProperty(value = "伙食补贴小计", position = 18)
    private BigDecimal btStandard1Total;

    /** 交通补贴标准 */
    @ApiModelProperty(value = "交通补贴标准", position = 19)
    private BigDecimal btStandard2;

    /** 交通补贴小计 */
    @ApiModelProperty(value = "交通补贴小计", position = 20)
    private BigDecimal btStandard2Total;

    /** 当月注册补贴标准 */
    @ApiModelProperty(value = "合计-应缴总金额|差额|合计|当月注册补贴标准|值班补助金额|讲师津贴总计|外业补贴合计", position = 21)
    private BigDecimal amount;

    /** 当月变动|备注 */
    @ApiModelProperty(value = "备注", position = 22)
    private String memo;
}
