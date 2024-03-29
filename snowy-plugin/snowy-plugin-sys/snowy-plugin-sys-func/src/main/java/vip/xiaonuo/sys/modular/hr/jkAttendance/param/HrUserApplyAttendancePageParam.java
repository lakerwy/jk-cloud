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
package vip.xiaonuo.sys.modular.hr.jkAttendance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 补卡或远程打卡查询参数
 *
 * @author ljy
 * @date  2023/09/12 09:17
 **/
@Getter
@Setter
public class HrUserApplyAttendancePageParam {

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

    /** 申请用户ID */
    @ApiModelProperty(value = "申请用户ID")
    private String applyUserId;

    /** 申请类型：是否加班考勤  1 ，是否弹性   2，是否异地   3，是否免打卡  4，申请补卡 5 */
    @ApiModelProperty(value = "申请类型：是否加班考勤  1 ，是否弹性   2，是否异地   3，是否免打卡  4，申请补卡 5")
    private Integer applyType;

    /** 申请时间 */
    @ApiModelProperty(value = "申请时间")
    private String applyAttendanceTime;

    /** 申请时间开始 */
    @ApiModelProperty(value = "申请时间开始")
    private String startApplyTime;

    /** 申请时间结束 */
    @ApiModelProperty(value = "申请时间结束")
    private String endApplyTime;

    /** 申请开始时间开始 */
    @ApiModelProperty(value = "申请开始时间开始")
    private String startApplyBeginTime;

    /** 申请开始时间结束 */
    @ApiModelProperty(value = "申请开始时间结束")
    private String endApplyBeginTime;

    /** 申请结束时间开始 */
    @ApiModelProperty(value = "申请结束时间开始")
    private String startApplyEngTime;

    /** 申请结束时间结束 */
    @ApiModelProperty(value = "申请结束时间结束")
    private String endApplyEngTime;

    /** 模型ID */
    @ApiModelProperty(value = "模型ID")
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id")
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

    @ApiModelProperty(value = "申请理由", position = 19)
    private String applyReason;
}
