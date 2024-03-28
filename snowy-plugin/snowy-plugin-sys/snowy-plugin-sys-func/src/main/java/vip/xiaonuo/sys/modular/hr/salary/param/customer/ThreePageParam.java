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
package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 工资系统-月度工资标准 - 三个列表 - 查询参数
 *
 * @author yong.ye
 * @date  2024/02/26 09:36
 **/
@Getter
@Setter
public class ThreePageParam {

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月")
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String userName;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门")
    private String firstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门")
    private String orgName;

    /** 职位 */
    @ApiModelProperty(value = "职位")
    private String position;

    /** 员工类别：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类别：总部|分公司|外部")
    private String userType;

    /** 是否标记 */
    @ApiModelProperty(value = "是否标记")
    private String isSpec;

    /** 是否实习 */
    @ApiModelProperty(value = "是否实习")
    private String isTry;
}
