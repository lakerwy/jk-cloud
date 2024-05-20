package vip.xiaonuo.biz.modular.sealMessage.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用印申请开关配置项（option）子表
 * @Author hxw
 * @Date 2024/5/17 15:42
 */
@Getter
@Setter
public class option {
    /** ocr开关配置项，0:关闭；1:开启 */
    @ApiModelProperty(value = "ocr开关配置项，0:关闭；1:开启", position = 2)
    private String ocr;

    /** ocr事前比对开关配置项，0:关闭；1:开启 */
    @ApiModelProperty(value = "ocr事前比对开关配置项，0:关闭；1:开启", position = 3)
    private String preventOcrCompare;

    /** 是否添加暗纹码，0:关闭；1:开启 */
    @ApiModelProperty(value = "是否添加暗纹码，0:关闭；1:开启", position = 4)
    private int addObscureCode;

    /** 1 非纯文本的文件页 2 所有文件页 */
    @ApiModelProperty(value = "1 非纯文本的文件页 2 所有文件页", position = 5)
    private int addOn;

    /** 水印开关配置项，0:关闭；1:开启 */
    @ApiModelProperty(value = "水印开关配置项，0:关闭；1:开启", position = 6)
    private String watermark;

    /** 水印事前开关配置项，0:关闭；1:开启 */
    @ApiModelProperty(value = "水印事前开关配置项，0:关闭；1:开启", position = 7)
    private String preventWaterMark;

    /** 远程授权开关配置项，0:关闭；1:开启 */
    @ApiModelProperty(value = "远程授权开关配置项，0:关闭；1:开启", position = 8)
    private String preventPower;

    /** 二维码事前开关配置项，0:关闭；1:开启 */
    @ApiModelProperty(value = "二维码事前开关配置项，0:关闭；1:开启", position = 9)
    private String prevent;

    /** 明文水印开关，0:关闭；1:开启 */
    @ApiModelProperty(value = "明文水印开关，0:关闭；1:开启", position = 10)
    private String plaintextMarkSwitch;

    /** 远程实时监控，0:关闭；1:开启 */
    @ApiModelProperty(value = "远程实时监控，0:关闭；1:开启", position = 11)
    private String remoteMonitoringSwitch;

    /** 水印模式配置项，1:隐形模式；2:兼容模式 */
    @ApiModelProperty(value = "水印模式配置项，1:隐形模式；2:兼容模式", position = 12)
    private String preventWatermarkModel;
}
