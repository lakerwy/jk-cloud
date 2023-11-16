package vip.xiaonuo.biz.modular.school.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ResumeReportViewModel {

    /** 简历Id */
    @ApiModelProperty(value = "简历Id")
    private String resumesId ;

    /** 信息来源|渠道 */
    @ApiModelProperty(value = "信息来源|渠道")
    private String infoSource ;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name ;

    /** 性别 */
    @ApiModelProperty(value = "性别")
    private String sex ;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String mobile ;

    /** 学历 */
    @ApiModelProperty(value = "学历")
    private Integer academic ;

    /** 学校 */
    @ApiModelProperty(value = "学校")
    private String school ;

    /** 专业 */
    @ApiModelProperty(value = "专业")
    private String subject ;

    /** 推荐部门 */
    @ApiModelProperty(value = "推荐部门")
    private String belongsDepts ;

    /** 是否通过简历初筛 */
    @ApiModelProperty(value = "是否通过简历初筛")
    private String isPassFilter ;

    /** 面试日期 */
    @ApiModelProperty(value = "面试日期")
    private Date actionTime ;

    /** 是否到面 */
    @ApiModelProperty(value = "是否到面")
    private String isFace ;

    /** 面试意见 */
    @ApiModelProperty(value = "面试意见")
    private String faceResult ;

    /** Offer接收情况 */
    @ApiModelProperty(value = "Offer接收情况")
    private String isOffer ;

    /** 拒绝原因 */
    @ApiModelProperty(value = "拒绝原因")
    private String reason ;

    /** 简历投递时间 */
    @ApiModelProperty(value = "简历投递时间")
    private Date createTime ;
}
