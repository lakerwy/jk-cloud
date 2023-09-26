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
package vip.xiaonuo.sys.modular.hr.userApplyAttendance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 补卡或远程打卡添加参数
 *
 * @author ljy
 * @date  2023/09/12 09:17
 **/
@Getter
@Setter
public class HrUserApplyAttendanceAddParam {

    /** 申请用户ID */
    @ApiModelProperty(value = "申请用户ID", position = 3)
    private String applyUserId;

    /** 申请类型：是否加班考勤  1 ，是否弹性   2，是否异地   3，是否免打卡  4，申请补卡 5 */
    @ApiModelProperty(value = "申请类型：是否加班考勤  1 ，是否弹性   2，是否异地   3，是否免打卡  4，申请补卡 5，申请异常 -1 (必传)", position = 4,required = true)
    private Integer applyType;

    /** 申请时间 */
    @ApiModelProperty(value = "申请时间：年-月-日，(若申请补卡，则传补卡当日时间)", position = 5)
    private String applyTime;

    /** 申请开始时间 */
    @ApiModelProperty(value = "申请开始时间：年-月-日 时-分-秒 ", position = 6)
    private String applyBeginTime;

    /** 申请结束时间 */
    @ApiModelProperty(value = "申请结束时间：年-月-日 时-分-秒", position = 7)
    private String applyEngTime;

    /** 模型ID */
    @ApiModelProperty(value = "模型ID", position = 8)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 9)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 10)
    private String status;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 11)
    private String extJson;

    /** 补卡申请时间 */
    @ApiModelProperty(value = "补卡申请开始时间：时-分-秒 (若申请补卡，则必传)", position = 17)
    private Integer resubmitAttendanceBeginTime;

    /** 补卡申请结束时间 */
    @ApiModelProperty(value = "补卡申请结束时间：时-分-秒 (若申请补卡，则必传)", position = 18)
    private String resubmitAttendanceEndTime;

    @ApiModelProperty(value = "申请理由", position = 19)
    private String applyReason;
}
