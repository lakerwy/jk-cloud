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
package vip.xiaonuo.sys.modular.hr.certManage.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 人员资质管理查询参数
 *
 * @author yong.ye
 * @date  2024/01/23 17:23
 **/
@Getter
@Setter
public class CertManagePageParam {

    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 模块类别：注册证书|培训证书 */
    @ApiModelProperty(value = "模块类别：注册证书|培训证书")
    private String type;

    /** 证书类别：注册类别|培训类别 */
    @ApiModelProperty(value = "证书类别：注册类别|培训类别")
    private String certType;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name;

    /** 注册专业|专业 */
    @ApiModelProperty(value = "注册专业|专业")
    private String major;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idNumber;

    /** 证书编号 */
    @ApiModelProperty(value = "证书编号")
    private String certNo;

    /** 执业印章号 */
    @ApiModelProperty(value = "执业印章号")
    private String certSealNo;

    /** 发证日期开始 */
    @ApiModelProperty(value = "发证日期开始")
    private String startIssueDate;

    /** 发证日期结束 */
    @ApiModelProperty(value = "发证日期结束")
    private String endIssueDate;

    /** 有效期开始 */
    @ApiModelProperty(value = "有效期开始")
    private String startValidity;

    /** 有效期结束 */
    @ApiModelProperty(value = "有效期结束")
    private String endValidity;

    /** 存管部门 */
    @ApiModelProperty(value = "存管部门")
    private String depositAddress;

    /** 注册单位|发证单位 */
    @ApiModelProperty(value = "注册单位|发证单位")
    private String unit;

    /** 证书名称 */
    @ApiModelProperty(value = "证书名称")
    private String certName;

    /** 职务工种 */
    @ApiModelProperty(value = "职务工种")
    private String jobType;

    /** 技术职称 */
    @ApiModelProperty(value = "技术职称")
    private String technicalTitle;

    /** 企业名称 */
    @ApiModelProperty(value = "企业名称")
    private String companyName;

    /** 证书状态：有效|无效 */
    @ApiModelProperty(value = "证书状态：有效|无效")
    private String certStatus;

    /** 用户类型：总部|分公司|外部人员 */
    @ApiModelProperty(value = "用户类型：总部|分公司|外部人员")
    private String userType;

}
