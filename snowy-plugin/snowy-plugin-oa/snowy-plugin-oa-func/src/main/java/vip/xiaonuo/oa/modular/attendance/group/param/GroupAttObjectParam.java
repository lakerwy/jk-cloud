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
package vip.xiaonuo.oa.modular.attendance.group.param;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.oa.core.handler.AttObjectHandler;
import vip.xiaonuo.oa.core.handler.GroupWorkDayHandler;
import vip.xiaonuo.oa.core.handler.WorkPlaceHandler;
import vip.xiaonuo.oa.modular.attendance.group.entity.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 考勤-考勤组编辑参数
 *
 * @author mayike
 * @date  2024/06/05 15:51
 **/
@Getter
@Setter
public class GroupAttObjectParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 考勤打卡对象，存储为 JSON 字符串 */
    @ApiModelProperty(value = "考勤打卡对象，存储为 JSON 字符串", position = 6)
    @TableField(value = "PARTICIPATES", typeHandler = AttObjectHandler.class)
    private List<AttObject> participates;

    /** 无需考勤打卡的人员，存储为 JSON 字符串 */
    @ApiModelProperty(value = "无需考勤打卡的人员，存储为 JSON 字符串", position = 7)
    @TableField(value = "UN_PARTICIPATES", typeHandler = AttObjectHandler.class)
    private List<AttObject> unParticipates;

}
