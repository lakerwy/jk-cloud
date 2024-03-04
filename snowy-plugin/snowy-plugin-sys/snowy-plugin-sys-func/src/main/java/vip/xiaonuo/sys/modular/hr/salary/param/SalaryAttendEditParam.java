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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工资系统-月度考勤表编辑参数
 *
 * @author yong.ye
 * @date  2024/02/26 09:26
 **/
@Getter
@Setter
public class SalaryAttendEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 2)
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 3)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 4)
    private String userName;

    /** 建科云用户Id */
    @ApiModelProperty(value = "建科云用户Id", position = 5)
    private String userId;

    /** 花名册Id */
    @ApiModelProperty(value = "花名册Id", position = 5)
    private String personnelId;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 6)
    private String firstOrgName;

    /** 一级部门Id */
    @ApiModelProperty(value = "一级部门Id", position = 7)
    private String firstOrgId;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 8)
    private String orgName;

    /** 二级部门Id */
    @ApiModelProperty(value = "二级部门Id", position = 9)
    private String orgId;

    /** 职位|岗位 */
    @ApiModelProperty(value = "职位|岗位", position = 10)
    private String position;

    /** 职位Id */
    @ApiModelProperty(value = "职位Id", position = 11)
    private String positionId;

    /** 入职日期 */
    @ApiModelProperty(value = "入职日期", position = 12)
    private String hireDate;

    /** 离职日期 */
    @ApiModelProperty(value = "离职日期", position = 13)
    private String leaveDate;

    /** 当月未打卡次数 */
    @ApiModelProperty(value = "当月未打卡次数", position = 14)
    private Integer missNum;

    /** 迟到次数-A类 */
    @ApiModelProperty(value = "迟到次数-A类", position = 15)
    private Integer lateNumA;

    /** 迟到次数-B类 */
    @ApiModelProperty(value = "迟到次数-B类", position = 16)
    private Integer lateNumB;

    /** 迟到次数-C类 */
    @ApiModelProperty(value = "迟到次数-C类", position = 17)
    private Integer lateNumC;

    /** 病假天数 */
    @ApiModelProperty(value = "病假天数", position = 18)
    private Float sickDay;

    /** 事假天数 */
    @ApiModelProperty(value = "事假天数", position = 19)
    private Float workDay;

    /** 矿工天数 */
    @ApiModelProperty(value = "矿工天数", position = 20)
    private Float minerDay;

    /** 年假天数 */
    @ApiModelProperty(value = "年假天数", position = 21)
    private Float yearDay;

    /** 产假天数 */
    @ApiModelProperty(value = "产假天数", position = 22)
    private Float materDay;

    /** 婚假天数 */
    @ApiModelProperty(value = "婚假天数", position = 23)
    private Float marryDay;

    /** 丧假天数 */
    @ApiModelProperty(value = "丧假天数", position = 24)
    private Float funDay;

    /** 考勤金额 */
    @ApiModelProperty(value = "考勤金额", position = 25)
    private BigDecimal amount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 26)
    private String memo;

}
