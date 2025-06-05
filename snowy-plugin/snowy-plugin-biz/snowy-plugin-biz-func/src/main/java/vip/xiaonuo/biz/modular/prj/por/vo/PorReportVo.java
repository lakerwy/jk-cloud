package vip.xiaonuo.biz.modular.prj.por.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.por.entity.PorReport;

@Getter
@Setter
public class PorReportVo extends PorReport {

    /*********************************** 来源-立项 ******************************************/

    /** 项目名称（BizProject.name） */
    @ApiModelProperty(value = "项目名称", position = 201)
    private String bizPrjName;

    /** 项目类型（BizProject.type） */
    @ApiModelProperty(value = "项目类型（BizProject.type）", position = 202)
    private String bizPrjType;

    /** 项目编号（BizProject.sn） */
    @ApiModelProperty(value = "项目编号（BizProject.sn）", position = 203)
    private String bizPrjSn;


    /*********************************** 来源 - 项目管理 - 我的项目 ******************************************/

    /** 责任部门Id */
    @ApiModelProperty(value = "责任部门Id", position = 301)
    private String dutyDeptId;

    /** 责任部门 */
    @ApiModelProperty(value = "责任部门", position = 302)
    private String dutyDeptName;

    /** 责任人Id */
    @ApiModelProperty(value = "责任人Id", position = 303)
    private String dutyId;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 304)
    private String dutyName;

}
