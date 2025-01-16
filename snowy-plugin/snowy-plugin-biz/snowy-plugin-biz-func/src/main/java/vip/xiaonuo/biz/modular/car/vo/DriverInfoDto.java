package vip.xiaonuo.biz.modular.car.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/11/9 9:24
 */

@Getter
@Setter
public class DriverInfoDto {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 司机姓名 */
    @ApiModelProperty(value = "司机姓名", position = 4)
    private String name;

    /** 驾照类别 */
    @ApiModelProperty(value = "驾照类别", position = 5)
    private String licenseType;

    /** 驾龄 */
    @ApiModelProperty(value = "驾龄", position = 6)
    private String drivingYears;

    /** 司机UserId */
    @ApiModelProperty(value = "司机UserId", position = 13)
    private String driverUserId;

    /** 派遣类型(公派|私派) */
    @ApiModelProperty(value = "派遣类型(公派|私派)", position = 20)
    private String dispatchType;

    /** 状态 */
    @ApiModelProperty(value = "状态（1-空闲，2-出车，3-请假，4-离职）", position = 21)
    private String status;
}
