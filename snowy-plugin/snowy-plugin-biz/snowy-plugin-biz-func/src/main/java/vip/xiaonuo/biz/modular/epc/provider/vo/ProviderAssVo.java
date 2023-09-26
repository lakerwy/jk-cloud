package vip.xiaonuo.biz.modular.epc.provider.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/9/25 17:05
 */

@Getter
@Setter
public class ProviderAssVo {

    /** 外键-供应商Id */
    @ApiModelProperty(value = "外键-供应商Id", position = 3)
    private String prvId;

    /** 供应商类别 */
    @ApiModelProperty(value = "供应商类别", position = 3)
    private String prvType;

    /** 供应商名称 */
    @ApiModelProperty(value = "供应商名称", position = 4)
    private String prvName;

    /** 年度(如：2023) */
    @ApiModelProperty(value = "年度(如：2023)", position = 4)
    private String year;

    /** 考核类型：1 供货 2 分包 */
    @ApiModelProperty(value = "考核类型：1 供货 2 分包", position = 6)
    private String assType;

    /** 第一季度 考核详情Json */
    @ApiModelProperty(value = "第一季度 考核详情Json", position = 7)
    private String assJson1;

    /** 第一季度 得分 */
    @ApiModelProperty(value = "第一季度 得分", position = 8)
    private Double assScore1;

    /** 第一季度 评估等级 */
    @ApiModelProperty(value = "第一季度 评估等级", position = 9)
    private String assLevel1;

    /** 第一季度 备注 */
    @ApiModelProperty(value = "第一季度 备注", position = 10)
    private String memo1;

    /** 第二季度 考核详情Json */
    @ApiModelProperty(value = "第二季度 考核详情Json", position = 7)
    private String assJson2;

    /** 第二季度 得分 */
    @ApiModelProperty(value = "第二季度 得分", position = 8)
    private Double assScore2;

    /** 第二季度 评估等级 */
    @ApiModelProperty(value = "第二季度 评估等级", position = 9)
    private String assLevel2;

    /** 第二季度 备注 */
    @ApiModelProperty(value = "第二季度 备注", position = 10)
    private String memo2;

    /** 第三季度 考核详情Json */
    @ApiModelProperty(value = "第三季度 考核详情Json", position = 7)
    private String assJson3;

    /** 第三季度 得分 */
    @ApiModelProperty(value = "第三季度 得分", position = 8)
    private Double assScore3;

    /** 第三季度 评估等级 */
    @ApiModelProperty(value = "第三季度 评估等级", position = 9)
    private String assLevel3;

    /** 第三季度 备注 */
    @ApiModelProperty(value = "第三季度 备注", position = 10)
    private String memo3;

    /** 第四季度 考核详情Json */
    @ApiModelProperty(value = "第四季度 考核详情Json", position = 7)
    private String assJson4;

    /** 第四季度 得分 */
    @ApiModelProperty(value = "第四季度 得分", position = 8)
    private Double assScore4;

    /** 第四季度 评估等级 */
    @ApiModelProperty(value = "第四季度 评估等级", position = 9)
    private String assLevel4;

    /** 第四季度 备注 */
    @ApiModelProperty(value = "第四季度 备注", position = 10)
    private String memo4;
}
