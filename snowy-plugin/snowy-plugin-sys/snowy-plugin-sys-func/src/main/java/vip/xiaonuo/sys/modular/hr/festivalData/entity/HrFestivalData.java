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
package vip.xiaonuo.sys.modular.hr.festivalData.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 法定节假日数据实体
 *
 * @author ljy
 * @date  2023/10/23 17:55
 **/
@Getter
@Setter
@TableName("HR_FESTIVAL_DATA")
public class HrFestivalData {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 日期类别：1 法定节假日，2 法定加班日 */
    @ApiModelProperty(value = "日期类别：1 法定节假日，2 法定加班日", position = 2)
    private Integer holidayType;

    /** HOLIDAY_TIME */
    @ApiModelProperty(value = "HOLIDAY_TIME", position = 3)
    private Date holidayTime;

    /** 扩展信息：存假期数据 */
    @ApiModelProperty(value = "扩展信息：存假期数据", position = 4)
    private String extJson;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 5)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 6)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 7)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 8)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 9)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
