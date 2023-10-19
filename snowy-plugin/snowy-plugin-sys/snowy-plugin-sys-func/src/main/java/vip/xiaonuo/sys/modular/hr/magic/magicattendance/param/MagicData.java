package vip.xiaonuo.sys.modular.hr.magic.magicattendance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MagicData {

    @ApiModelProperty(value = "人员ID", position = 1)
    private Long personId;
    @ApiModelProperty(value = "设备SN", position = 2)
    private String deviceSn;
    @ApiModelProperty(value = "人员类型 1：机构成员 3：外部联系人", position = 3)
    private Integer personType;
    @ApiModelProperty(value = "识别时间戳，毫秒", position = 4)
    private Long recognizeTime;
    @ApiModelProperty(value = "20-人脸，300-指纹，400-密码，500-卡片，600-扫码开门", position = 5)
    private Integer recognizeType;
    @ApiModelProperty(value = "是否在识别组内", position = 6)
    private Boolean recognizeGroupFlag;
    @ApiModelProperty(value = "抓拍照图片在oss对应bucket根目录下的文件名称", position = 7)
    private String fileKey;
    @ApiModelProperty(value = "第三方人员ID", position = 8)
    private String tpId;
}
