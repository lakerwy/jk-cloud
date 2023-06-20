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
package vip.xiaonuo.flw.modular.flwbiz.project.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-立项申请编辑参数
 *
 * @author mayike
 * @date  2023/06/20 14:26
 **/
@Getter
@Setter
public class BizProjectEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotNull(message = "id不能为空")
    private String id;

    /** sn值 */
    @ApiModelProperty(value = "sn值", position = 2)
    private String sn;

    /** 公司类型 */
    @ApiModelProperty(value = "公司类型", position = 3)
    private String isHead;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 4)
    private String name;

    /** 项目地址 */
    @ApiModelProperty(value = "项目地址", position = 5)
    private String address;

    /** 项目类型 */
    @ApiModelProperty(value = "项目类型", position = 6)
    private String type;

    /** 项目来源 */
    @ApiModelProperty(value = "项目来源", position = 7)
    private String head;

    /** 项目属性 */
    @ApiModelProperty(value = "项目属性", position = 8)
    private String source;

    /** 立项编号 */
    @ApiModelProperty(value = "立项编号", position = 9)
    private String approvalNo;

    /** 项目介绍 */
    @ApiModelProperty(value = "项目介绍", position = 10)
    private String introduce;

    /** 附件上传 */
    @ApiModelProperty(value = "附件上传", position = 11)
    private String attachment;

    /** 开标日期 */
    @ApiModelProperty(value = "开标日期", position = 12)
    private String startDate;

    /** 项目负责人 */
    @ApiModelProperty(value = "项目负责人", position = 13)
    private String projectHeader;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 14)
    private String dept;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 15)
    private String leader;

    /** 区域负责人 */
    @ApiModelProperty(value = "区域负责人", position = 16)
    private String areaLeader;

    /** 是否主投 */
    @ApiModelProperty(value = "是否主投", position = 17)
    private String isMain;

    /** 投标保证金 */
    @ApiModelProperty(value = "投标保证金", position = 18)
    private String bond;

    /** 投标限价 */
    @ApiModelProperty(value = "投标限价", position = 19)
    private String priceLimit;

    /** 招标代理机构 */
    @ApiModelProperty(value = "招标代理机构", position = 20)
    private String biddingAgency;

    /** 联系人 */
    @ApiModelProperty(value = "联系人", position = 21)
    private String biddingHeader;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", position = 22)
    private String biddingPhone;

    /** 建设单位 */
    @ApiModelProperty(value = "建设单位", position = 23)
    private String project;

    /** 联系人 */
    @ApiModelProperty(value = "联系人", position = 24)
    private String projectContacts;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", position = 25)
    private String projectPhone;

    /** 设计单位 */
    @ApiModelProperty(value = "设计单位", position = 26)
    private String designUnit;

    /** 监理单位 */
    @ApiModelProperty(value = "监理单位", position = 27)
    private String controlUnit;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 28)
    private String note;

    /** 计划附件 */
    @ApiModelProperty(value = "计划附件", position = 29)
    private String subAttachment;

    /** 摘要详情 */
    @ApiModelProperty(value = "摘要详情", position = 30)
    private String summary;

    /** 中标情况 */
    @ApiModelProperty(value = "中标情况", position = 31)
    private String states;

    /** 机会(可选) */
    @ApiModelProperty(value = "机会(可选)", position = 32)
    private String chance;

    /** 机会编号 */
    @ApiModelProperty(value = "机会编号", position = 33)
    private String chanceId;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 34)
    private String status;

    @ApiModelProperty(value = "流程实例ID", position = 60)
    private String processInstanceId;

}
