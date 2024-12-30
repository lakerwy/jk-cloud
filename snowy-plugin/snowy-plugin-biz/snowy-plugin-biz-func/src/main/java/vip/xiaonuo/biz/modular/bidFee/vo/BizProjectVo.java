package vip.xiaonuo.biz.modular.bidFee.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 立项Vo
 */
@Getter
@Setter
public class BizProjectVo {

    @ApiModelProperty(value = "立项主键id", position = 1)
    private String id;

    /** 立项主体 */
    @ApiModelProperty(value = "立项主体", position = 2)
    private String isHead;

    /** 所属大区 */
    @ApiModelProperty(value = "所属大区", position = 3)
    private String region;

    /** 立项编号 */
    @ApiModelProperty(value = "立项编号", position = 4)
    private String projectSn;

    /** 立项单据流水号 */
    @ApiModelProperty(value = "立项单据流水号", position = 5)
    private String sn;

    /** 立项流程实例id */
    @ApiModelProperty(value = "立项流程实例id", position = 6)
    private String processInstanceId;

    /** 合作方负责人 */
    @ApiModelProperty(value = "合作方负责人", position = 7)
    private String leader;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 8)
    private String name;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 9)
    private String dept;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 10)
    private String deptId;

    /** 投标专员 */
    @ApiModelProperty(value = "投标专员", position = 18)
    private String bidManger;

    /** 投标专员ID */
    @ApiModelProperty(value = "投标专员ID", position = 19)
    private String bidMangerId;

    /** 部门/分公司负责人 */
    @ApiModelProperty(value = "部门/分公司负责人", position = 24)
    private String manager;

    /** 部门/分公司负责人ID */
    @ApiModelProperty(value = "部门/分公司负责人ID", position = 25)
    private String managerId;
}
