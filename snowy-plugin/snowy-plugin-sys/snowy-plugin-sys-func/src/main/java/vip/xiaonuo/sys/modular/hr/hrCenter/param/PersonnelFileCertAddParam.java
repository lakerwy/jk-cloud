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
package vip.xiaonuo.sys.modular.hr.hrCenter.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 业务表-证书信息添加参数
 *
 * @author mayike
 * @date  2023/07/10 16:53
 **/
@Getter
@Setter
public class PersonnelFileCertAddParam {

    /** 档案号ID */
    @ApiModelProperty(value = "档案号ID", position = 2)
    private String fileId;

    /** 证书类别 */
    @ApiModelProperty(value = "证书类别", position = 3)
    private String certType;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 4)
    private String name;

    /** 工种/专业 */
    @ApiModelProperty(value = "工种/专业", position = 5)
    private String major;

    /** 证书号 */
    @ApiModelProperty(value = "证书号", position = 6)
    private String certNo;

    /** 印章号 */
    @ApiModelProperty(value = "印章号", position = 7)
    private String stampNo;

    /** 有效期至 */
    @ApiModelProperty(value = "有效期至", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date invalidDate;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 9)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String note;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 11)
    private String extJson;

}
