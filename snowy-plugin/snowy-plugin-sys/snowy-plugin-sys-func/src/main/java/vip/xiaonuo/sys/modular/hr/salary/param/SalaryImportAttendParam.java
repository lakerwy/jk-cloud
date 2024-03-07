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

/**
 * 工资系统-考勤导入
 *
 * @author yong.ye
 * @date  2024/02/26 09:31
 **/
@Getter
@Setter
public class SalaryImportAttendParam {

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

    /** 当月未打卡次数 */
    @ApiModelProperty(value = "当月未打卡次数", position = 22)
    private Integer missNum;

    /** 迟到次数-A类 */
    @ApiModelProperty(value = "迟到次数-A类", position = 23)
    private Integer lateNumA;

    /** 迟到次数-B类 */
    @ApiModelProperty(value = "迟到次数-B类", position = 24)
    private Integer lateNumB;

    /** 迟到次数-C类 */
    @ApiModelProperty(value = "迟到次数-C类", position = 25)
    private Integer lateNumC;

    /** 病假天数 */
    @ApiModelProperty(value = "病假天数", position = 26)
    private Float sickDay;

    /** 事假天数 */
    @ApiModelProperty(value = "事假天数", position = 27)
    private Float workDay;

    /** 矿工天数 */
    @ApiModelProperty(value = "矿工天数", position = 28)
    private Float minerDay;

    /** 年假天数 */
    @ApiModelProperty(value = "年假天数", position = 29)
    private Float yearDay;

    /** 产假天数 */
    @ApiModelProperty(value = "产假天数", position = 30)
    private Float materDay;

    /** 婚假天数 */
    @ApiModelProperty(value = "婚假天数", position = 31)
    private Float marryDay;

    /** 丧假天数 */
    @ApiModelProperty(value = "丧假天数", position = 32)
    private Float funDay;

    /** 当月变动|备注 */
    @ApiModelProperty(value = "备注", position = 54)
    private String memo;

    /** 是否全勤：是 否*/
    @ApiModelProperty(value = "是否全勤：是 否", position = 33)
    private String isFull;

}
