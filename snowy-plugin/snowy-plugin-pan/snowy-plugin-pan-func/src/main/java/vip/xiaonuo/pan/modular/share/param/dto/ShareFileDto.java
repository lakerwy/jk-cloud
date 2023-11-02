package vip.xiaonuo.pan.modular.share.param.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/7 16:59
 */

@Getter
@Setter
public class ShareFileDto {

//    /** 租户Id */
//    @ApiModelProperty(value = "租户Id", position = 2)
//    private String tenantId;
//
//    /** 用户Id */
//    @ApiModelProperty(value = "用户Id", position = 3)
//    private String userId;

//    /** 分享时间 */
//    @ApiModelProperty(value = "分享时间", position = 4)
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date shareTime;

    /** 失效时间 */
    @ApiModelProperty(value = "失效时间", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

//    /** 提取码 */
//    @ApiModelProperty(value = "提取码", position = 6)
//    private String extractionCode;

//    /** 分享批次号 */
//    @ApiModelProperty(value = "分享批次号", position = 7)
//    private String shareBatchNum;

    /** 分享类型(0公共,1私密,2好友) */
    @ApiModelProperty(value = "分享类型(0公共,1私密,2好友)", position = 8)
    private Integer shareType;

//    /** 分享状态(0正常,1已失效,2已撤销) */
//    @ApiModelProperty(value = "分享状态(0正常,1已失效,2已撤销)", position = 9)
//    private Integer shareStatus;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

    /** 分享的文件Id集合 */
    @ApiModelProperty(value = "分享的文件Id集合", position = 11)
    private List<String> userFileIds;

    @ApiModelProperty(value = "权限校验类型：GROUP 群组  COMMON 共享  注：（不传或不在指定范围） 不做权限校验")
    private String rightType;

}
