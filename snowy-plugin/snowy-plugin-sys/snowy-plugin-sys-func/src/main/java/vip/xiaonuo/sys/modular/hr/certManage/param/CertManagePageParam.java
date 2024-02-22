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

import java.util.List;

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

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idNumber;

    /** 注册单位|发证单位 */
    @ApiModelProperty(value = "注册单位|发证单位")
    private String unit;

    /** 社保所在地 */
    @ApiModelProperty(value = "社保所在地")
    private String insUnit;

    /** 学校专业 */
    @ApiModelProperty(value = "学校专业")
    private String eduMajor;

//    /** 混合搜索： 类别|等级|专业联动 */
//    @ApiModelProperty(value = "混合搜索： 类别|等级|专业联动")
//    private List<ThreeParam> threeParams;

    /** 发证日期开始 */
    @ApiModelProperty(value = "发证日期开始")
    private String startIssueDate;

    /** 发证日期结束 */
    @ApiModelProperty(value = "发证日期结束")
    private String endIssueDate;

    /** 用户类型：总部|分公司|外部人员 */
    @ApiModelProperty(value = "用户类型：总部|分公司|外部人员")
    private String userType;

    /** 证书有效期标识（已过期|临近一个月|临近六个月） */
    @ApiModelProperty(value = "证书有效期标识（已过期|临近一个月|临近六个月）")
    private String certStage;

    /** （证书是否失效，总部|分公司人员离职即证书失效，外部人员证书是否失效由页面自行维护） 状态  0 :有效  1：失效 */
    @ApiModelProperty(value = "（证书是否失效，总部|分公司人员离职即证书失效，外部人员证书是否失效由页面自行维护） 状态  0 :有效  1：失效 ")
    private String userStatus;

}
