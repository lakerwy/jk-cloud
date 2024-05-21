package vip.xiaonuo.biz.modular.sealMessage.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;;

/**
 *
 *@Author hxw
 *@Date 2024/5/17 15:19
 */
@Getter
@Setter
public class UsedSealParam {
    /** 印章ID */
    @ApiModelProperty(value = "印章ID", position = 2)
    private String sealId;

    /** 用印数量 */
    @ApiModelProperty(value = "用印数量", position = 3)
    private String applyCount;

    /** 骑缝章次数 */
    @ApiModelProperty(value = "骑缝章次数", position = 4)
    private String applyCrossPageSealCount;

    /** 实际用印人账号 */
    @ApiModelProperty(value = "实际用印人账号", position = 5)
    private String sealerUserName;

    /** 是否外带 */
    @ApiModelProperty(value = "是否外带", position = 6)
    private String takeOut;

    /** 预计外带开始时间 */
    @ApiModelProperty(value = "预计外带开始时间", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outStartDate;

    /** 预计外带结束时间 */
    @ApiModelProperty(value = "预计外带结束时间", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outEndDate;

    /** 外带详细地址 */
    @ApiModelProperty(value = "外带详细地址", position = 9)
    private String outAddress;

    /** 外带城市 */
    @ApiModelProperty(value = "外带城市", position = 10)
    private String city;

    /** 外带地点的经度 */
    @ApiModelProperty(value = "外带地点的经度", position = 11)
    private String longitude;
}
