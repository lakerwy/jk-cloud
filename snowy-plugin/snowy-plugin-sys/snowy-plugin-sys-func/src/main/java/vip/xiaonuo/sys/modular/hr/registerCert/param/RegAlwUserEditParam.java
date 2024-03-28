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
package vip.xiaonuo.sys.modular.hr.registerCert.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 注册补贴-用户基本信息编辑参数
 *
 * @author ljy
 * @date  2024/03/27 15:22
 **/
@Getter
@Setter
public class RegAlwUserEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 用户名称 */
    @ApiModelProperty(value = "用户名称", position = 2)
    private String name;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 3)
    private String position;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 4)
    private String idCardNumber;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 5)
    private String firstOrgId;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 6)
    private String secOrgId;

    /** 入职时间 */
    @ApiModelProperty(value = "入职时间", position = 7)
    private Date hireDate;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 8)
    private String education;

    /** 毕业院校 */
    @ApiModelProperty(value = "毕业院校", position = 9)
    private String college;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 10)
    private String subject;

    /** 毕业时间 */
    @ApiModelProperty(value = "毕业时间", position = 11)
    private Date gradTime;

    /** 职称等级 */
    @ApiModelProperty(value = "职称等级", position = 12)
    private String titleLever;

    /** 职称专业 */
    @ApiModelProperty(value = "职称专业", position = 13)
    private String titleProfession;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 14)
    private String extJson;

}
