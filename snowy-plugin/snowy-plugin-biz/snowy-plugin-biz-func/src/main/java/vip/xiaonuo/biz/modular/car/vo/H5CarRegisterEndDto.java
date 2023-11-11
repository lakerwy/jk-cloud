package vip.xiaonuo.biz.modular.car.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class H5CarRegisterEndDto {

    /** (派车单)用车申请单id（主键Id） */
    @ApiModelProperty(value = "(派车单)用车申请单id（主键Id）", position = 1)
    private String id ;

    /** 实际还车时间 */
    @ApiModelProperty(value = "实际还车时间", position = 1)
    private Date endTime ;

    /** 还车里程 */
    @ApiModelProperty(value = "还车里程", position = 1)
    private BigDecimal endMileage ;

    /** 用车人 对应的 -- 钉钉系统中的UserId  */
    @ApiModelProperty(value = "用车人 对应的 -- 钉钉系统中的UserId ", position = 1)
    private String userDingDingUserId ;

    /** 司机备注特殊情况 */
    @ApiModelProperty(value = "司机备注特殊情况", position = 1)
    private String driverMemo ;

    /** 用车监管人员钉钉UserId,多个用英文逗号分隔。如：李总 */
    @ApiModelProperty(value = "用车监管人员钉钉UserId,多个用英文逗号分隔。如：李总", position = 1)
    private String bossDingDingUserId ;

}
