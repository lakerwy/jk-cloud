package vip.xiaonuo.oa.modular.attendance.group.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/11 10:47
 * 考勤对象类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttObject {
    @ApiModelProperty(value = "考勤对象ID", required = true)
    private String objectId;

    @ApiModelProperty(value = "考勤对象名称",required = true)
    private String objectName;

    @ApiModelProperty(value = "考勤对象类（0 公司 1 部门 2 员工 3 角色 4、考勤组 默认2）",required = true)
    private int type = 2;

}
