package vip.xiaonuo.biz.modular.prj.por.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.por.entity.PorProject;


@Getter
@Setter
public class PorProjectVo extends PorProject {

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

    /** 建设地点（BizProject.location） */
    @ApiModelProperty(value = "建设地点（BizProject.location）", position = 204)
    private String bizPrjLocation;

    /** 项目属性 （BizProject.source）*/
    @ApiModelProperty(value = "项目属性（BizProject.source）", position = 205)
    private String bizPrjSource;

}
