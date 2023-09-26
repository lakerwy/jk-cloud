package vip.xiaonuo.biz.modular.recruit.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/8/29 10:43
 */

@Getter
@Setter
public class ResumeChannelStatisticVo {
    /** 所属部门 */
    @ApiModelProperty(value = "所属部门", position = 1)
    private String orgId;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 2)
    private String orgName;

    /** 简历来源 */
    @ApiModelProperty(value = "简历来源", position = 3)
    private String resumeResource;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 4)
    private String name;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 5)
    private String degree;

    /** 入职日期 */
    @ApiModelProperty(value = "入职日期", position = 6)
    private Date HIRE_DATE;

    /** 转正日期 */
    @ApiModelProperty(value = "转正日期", position = 7)
    private Date POSITIVE_TIME;

}
