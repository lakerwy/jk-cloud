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
package vip.xiaonuo.oa.modular.attendance.shift.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Rule;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 班次表，用于存储班次的基本信息编辑参数
 *
 * @author mayike
 * @date  2024/06/11 09:32
 **/
@Getter
@Setter
public class ShiftEditParam {

    /** 主键，唯一标识一个班次 */
    @ApiModelProperty(value = "主键，唯一标识一个班次", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 2)
    private String tenantId;

    /** 班次名称 */
    @ApiModelProperty(value = "班次名称", position = 3)
    private String shiftName;

    /** 备注信息 */
    @ApiModelProperty(value = "备注信息", position = 4)
    private String remark;

    /** 打卡次数 */
    @ApiModelProperty(value = "打卡次数(和clockInRules 的数量必须一直)", position = 6)
    private Integer checkNum;

    /** 存储班次上下班打卡时间详情的JSON字段 */
    @ApiModelProperty(value = "存储班次上下班打卡时间详情的JSON字段", position = 5)
    private List<Rule> rules;

    /** 工作时长，以分钟为单位 */
    @ApiModelProperty(value = "工作时长，以分钟为单位", position = 6)
    private Integer workTime;

    /** 扩展信息，JSON格式存储 */
    @ApiModelProperty(value = "扩展信息，JSON格式存储", position = 10)
    private String extJson;

    @ApiModelProperty(value = "是否为默认班次", position = 16)
    private Boolean defaultFlag;

}
