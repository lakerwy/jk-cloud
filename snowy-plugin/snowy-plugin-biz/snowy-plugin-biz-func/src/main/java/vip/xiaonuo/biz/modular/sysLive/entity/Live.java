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
package vip.xiaonuo.biz.modular.sysLive.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 直播地址表实体
 *
 * @author mayike
 * @date  2024/01/17 14:10
 **/
@Getter
@Setter
@TableName("LIVE")
public class Live {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 唯一编码号 */
    @ApiModelProperty(value = "唯一编码号", position = 2)
    private String liveNo;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 3)
    private String liveName;

    /** RTMP格式推流地址 */
    @ApiModelProperty(value = "RTMP格式推流地址", position = 4)
    private String rtmpUrl;

    /** RLV 格式推流地址 */
    @ApiModelProperty(value = "RLV 格式推流地址", position = 5)
    private String flvUrl;

    /** 直接观看地址 */
    @ApiModelProperty(value = "直接观看地址", position = 6)
    private String liveUrl;

    /** 删除标记 */
    @ApiModelProperty(value = "删除标记", position = 7)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改人 */
    @ApiModelProperty(value = "修改人", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 12)
    private Date startTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间", position = 13)
    private Date endTime;
}
