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
package vip.xiaonuo.biz.modular.inslibrary.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-产业生态链-资源库-联络人添加参数
 *
 * @author yong.ye
 * @date  2023/08/15 15:27
 **/
@Getter
@Setter
public class BizInsLinkmanAddParam {

    /** 外键，资源库主键ID */
    @ApiModelProperty(value = "外键，资源库主键ID", position = 2)
    private String libraryId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 3)
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 4)
    private String sex;

    /** 职务 */
    @ApiModelProperty(value = "职务", position = 5)
    private String duty;

    /** 生日 */
    @ApiModelProperty(value = "生日", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date birthday;

    /** 联系方式 */
    @ApiModelProperty(value = "联系方式", position = 7)
    private String phone;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

}
