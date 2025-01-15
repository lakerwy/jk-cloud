package vip.xiaonuo.biz.modular.car.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/11/9 9:21
 */

@Getter
@Setter
public class VehicleInfoDto {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 车牌号码 */
    @ApiModelProperty(value = "车牌号码", position = 6)
    private String licenseNo;

    /** 车辆品牌 */
    @ApiModelProperty(value = "车辆品牌", position = 7)
    private String vehicleBrand;

    /** 车辆类型 */
    @ApiModelProperty(value = "车辆类型", position = 8)
    private String vehicleType;

    /** 乘坐人数 */
    @ApiModelProperty(value = "乘坐人数", position = 9)
    private String seats;

    /** 车辆颜色 */
    @ApiModelProperty(value = "车辆颜色", position = 11)
    private String color;

    /** 适用燃油标号 92 95 98 */
    @ApiModelProperty(value = "适用燃油标号 92 95 98", position = 14)
    private String fuel;

    /** 油卡号码 */
    @ApiModelProperty(value = "油卡号码", position = 15)
    private String oilCardNumber;

    /** 照片 */
    @ApiModelProperty(value = "照片", position = 22)
    private String photo;

    /** 行驶证有效期 */
    @ApiModelProperty(value = "行驶证有效期", position = 23)
    private Date licenseValid;

    /** 用车类型(公用|私用) */
    @ApiModelProperty(value = "用车类型(公用|私用)", position = 30)
    private String useType;

    /** 车辆状态 可用 出车 维保 其他 */
    @ApiModelProperty(value = "车辆状态(1-可用 2-出车 3-维保 4-其他)", position = 31)
    private String status;

}
