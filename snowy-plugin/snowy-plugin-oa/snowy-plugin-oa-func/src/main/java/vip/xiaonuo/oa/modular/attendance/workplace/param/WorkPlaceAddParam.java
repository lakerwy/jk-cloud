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
package vip.xiaonuo.oa.modular.attendance.workplace.param;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.core.constant.BaseConstants;
import vip.xiaonuo.oa.modular.attendance.workplace.entity.TransformPosition;
import vip.xiaonuo.oa.modular.attendance.workplace.util.BaiduLocationTransformHelper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 考勤打卡位置表添加参数
 *
 * @author mayike
 * @date  2024/06/11 11:58
 **/
@Getter
@Setter
public class WorkPlaceAddParam {

    /** 场所名称 */
    @ApiModelProperty(value = "场所名称", position = 3, required = true)
    @NotEmpty(message = "场所名称不能为空")
    private String placeName;

    /** 场所别名 */
    @ApiModelProperty(value = "场所别名", position = 4)
    private String placeAlias;

    /** 坐标类型，百度、高德 */
    @ApiModelProperty(value = "坐标类型，百度、高德", position = 5)
    private String positionType;

    /** 经度 */
    @ApiModelProperty(value = "经度", position = 6, required = true)
    @NotEmpty(message = "经度不能为空")
    private String longitude;

    /** 纬度 */
    @ApiModelProperty(value = "纬度", position = 7, required = true)
    @NotEmpty(message = "纬度不能为空")
    private String latitude;

    /** 经度, GPS */
    @ApiModelProperty(value = "经度, GPS", position = 8)
    private String gpsLng;

    /** 纬度, GPS */
    @ApiModelProperty(value = "纬度, GPS", position = 9)
    private String gpsLat;

    /** 误差范围 */
    @ApiModelProperty(value = "误差范围", position = 10)
    private Integer errorRange;

    /** 说明备注 */
    @ApiModelProperty(value = "说明备注", position = 11)
    private String description;

    public void buildGps(){
        double dLat = Double.parseDouble(latitude);
        double dLng = Double.parseDouble(longitude);
        if(StrUtil.isEmpty(positionType)){
            positionType = BaseConstants.POSITION_TYPE_BAIDU;
        }
        TransformPosition tp;
        if(positionType.equals(BaseConstants.POSITION_TYPE_AMAP)){
            tp = BaiduLocationTransformHelper.gcj02towgs84(dLng, dLat);
        }else {
            tp = BaiduLocationTransformHelper.bd09togcj02(dLng, dLat);
        }
        gpsLng = String.valueOf(tp.getLng());
        gpsLat = String.valueOf(tp.getLat());
    }

}
