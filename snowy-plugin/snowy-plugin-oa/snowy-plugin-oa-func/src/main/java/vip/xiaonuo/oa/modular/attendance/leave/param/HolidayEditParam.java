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
package vip.xiaonuo.oa.modular.attendance.leave.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 公司员工假期数据表编辑参数
 *
 * @author mayike
 * @date  2024/06/24 17:33
 **/
@Getter
@Setter
public class HolidayEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 3)
    private String userId;

    /** 用户姓名 */
    @ApiModelProperty(value = "用户姓名", position = 4)
    private String userName;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 5)
    private String deparId;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 6)
    private String deparName;

    /** 入职时间 */
    @ApiModelProperty(value = "入职时间", position = 7)
    private Date entryWorkTime;

    /** 首次工作时间 */
    @ApiModelProperty(value = "首次工作时间", position = 8)
    private Date firstWorkTime;

    /** 事假 */
    @ApiModelProperty(value = "事假", position = 9)
    private String personalLeave;

    /** 病假 */
    @ApiModelProperty(value = "病假", position = 10)
    private String sickLeave;

    /** 婚假 */
    @ApiModelProperty(value = "婚假", position = 11)
    private String marriageLeave;

    /** 产假 */
    @ApiModelProperty(value = "产假", position = 12)
    private String maternityLeave;

    /** 丧假 */
    @ApiModelProperty(value = "丧假", position = 13)
    private String bereavementLeave;

    /** 年假 */
    @ApiModelProperty(value = "年假", position = 14)
    private String annualLeave;

    /** 调休 */
    @ApiModelProperty(value = "调休", position = 15)
    private String compensatoryLeave;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 16)
    private String extJson;

}
