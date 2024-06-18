package vip.xiaonuo.oa.modular.attendance.group.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/4 17:27
 *
 */
@Getter
@Setter
public class GroupWorkDayProperties {

    @ApiModelProperty("周日")
    private GroupWorkDay sunday;
    @ApiModelProperty("周一")
    private GroupWorkDay monday;
    @ApiModelProperty("周二")
    private GroupWorkDay tuesday;
    @ApiModelProperty("周三")
    private GroupWorkDay wednesday;
    @ApiModelProperty("周四")
    private GroupWorkDay thursday;
    @ApiModelProperty("周五")
    private GroupWorkDay friday;
    @ApiModelProperty("周六")
    private GroupWorkDay saturday;


}
