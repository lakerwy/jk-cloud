package vip.xiaonuo.biz.modular.car.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class H5CarUserInfoDto {

    /** 用户UserId（SysUser.UserId），例如：司机Id */
    @ApiModelProperty(value = "用户UserId（SysUser.UserId），例如：司机Id", position = 1)
    private String userId ;

    /** 用户姓名 与UserId对应 */
    @ApiModelProperty(value = "用户姓名 与UserId对应", position = 1)
    private String userName ;

    /** 司机驾龄 */
    @ApiModelProperty(value = "司机驾龄", position = 1)
    private String drivingYears ;

    /** 行驶里程数（KM) */
    @ApiModelProperty(value = "行驶里程数（KM)", position = 1)
    private BigDecimal mileages ;

    /** 出车次数 */
    @ApiModelProperty(value = "出车次数", position = 1)
    private Integer outCarCounts ;

    /** 出险次数 */
    @ApiModelProperty(value = "出险次数", position = 1)
    private Integer dangerCounts ;

    /** 违章次数 */
    @ApiModelProperty(value = "违章次数", position = 1)
    private Integer breaksCounts ;

}
