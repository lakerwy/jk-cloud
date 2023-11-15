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
package vip.xiaonuo.biz.modular.school.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 校招-简历查询参数
 *
 * @author yong.ye
 * @date  2023/11/15 08:54
 **/
@Getter
@Setter
public class ResumePageParam {

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

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别")
    private String sex;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /** 学校 */
    @ApiModelProperty(value = "学校")
    private String school;

    /** 专业 */
    @ApiModelProperty(value = "专业")
    private String subject;

    /** 岗位类别 */
    @ApiModelProperty(value = "岗位类别")
    private String positionType;

    /** 信息来源 */
    @ApiModelProperty(value = "信息来源")
    private String infoSource;

    /** 简历状态 */
    @ApiModelProperty(value = "简历状态")
    private String status;

    /** 学历 */
    @ApiModelProperty(value = "学历")
    private Integer academic;

    /** 创建时间 - 起 */
    @ApiModelProperty(value = "创建时间 - 起")
    private Date startCreateTime ;

    /** 创建时间 - 止 */
    @ApiModelProperty(value = "创建时间 - 止")
    private Date endCreateTime ;

    /** 针对不同列表，过滤简历状态筛选   简历公海：投递成功、初筛不合格 */
    @ApiModelProperty(value = "针对不同列表，过滤简历状态筛选   简历公海：投递成功、初筛不合格")
    private List<String> filterStatusIn ;

    /** 针对不同列表，过滤简历状态筛选   合格简历：即：投递成功、初筛不合格 之外的状态*/
    @ApiModelProperty(value = "针对不同列表，过滤简历状态筛选   合格简历：即：投递成功、初筛不合格 之外的状态")
    private List<String> filterStatusNotIn ;
}
