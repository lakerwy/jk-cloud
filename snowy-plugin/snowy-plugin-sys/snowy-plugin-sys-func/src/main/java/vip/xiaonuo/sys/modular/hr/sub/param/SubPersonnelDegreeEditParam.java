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

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * HR-分公司-学历信息编辑参数
 *
 * @author yong.ye
 * @date  2023/11/21 11:17
 **/
@Getter
@Setter
public class SubPersonnelDegreeEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 外键 - 档案ID（HR_SUB_PERSONNEL.Id） */
    @ApiModelProperty(value = "外键 - 档案ID（HR_SUB_PERSONNEL.Id）", position = 2)
    private String fileId;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 3)
    private String degree;

    /** 学历类型 */
    @ApiModelProperty(value = "学历类型", position = 4)
    private String degreeClass;

    /** 毕业时间 */
    @ApiModelProperty(value = "毕业时间", position = 5)
    private String graduateDate;

    /** 毕业院校 */
    @ApiModelProperty(value = "毕业院校", position = 6)
    private String graduateSchool;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 7)
    private String major;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String note;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 9)
    private String extJson;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 16)
    private String dataScopeId;

}
