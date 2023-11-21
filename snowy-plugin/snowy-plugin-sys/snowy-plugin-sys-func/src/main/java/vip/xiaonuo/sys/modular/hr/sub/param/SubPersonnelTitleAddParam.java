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
package vip.xiaonuo.sys.modular.hr.sub.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * HR-分公司-职称信息添加参数
 *
 * @author yong.ye
 * @date  2023/11/21 11:19
 **/
@Getter
@Setter
public class SubPersonnelTitleAddParam {

    /** 外键 - 档案ID（HR_SUB_PERSONNEL.Id） */
    @ApiModelProperty(value = "外键 - 档案ID（HR_SUB_PERSONNEL.Id）", position = 2)
    private String fileId;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 3)
    private String name;

    /** 职称专业 */
    @ApiModelProperty(value = "职称专业", position = 4)
    private String titleProfession;

    /** 职称等级 */
    @ApiModelProperty(value = "职称等级", position = 5)
    private String titleLever;

    /** 职称编号 */
    @ApiModelProperty(value = "职称编号", position = 6)
    private String titleNo;

    /** 发证机关 */
    @ApiModelProperty(value = "发证机关", position = 7)
    private String reviewOrgan;

    /** 发证日期 */
    @ApiModelProperty(value = "发证日期", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date certificateDate;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String note;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 10)
    private String attachments;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 11)
    private String extJson;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 18)
    private String dataScopeId;

}
