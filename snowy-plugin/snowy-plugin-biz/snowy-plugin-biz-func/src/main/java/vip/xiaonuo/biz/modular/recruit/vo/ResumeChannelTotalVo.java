package vip.xiaonuo.biz.modular.recruit.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/8/29 14:17
 */

@Getter
@Setter
public class ResumeChannelTotalVo {
    /** 各渠道各状态简历量统计表 */
    @ApiModelProperty(value = "各渠道各状态简历量统计表", position = 1)
    private List<ResumeChannelNumVo> channelNumVoList;

    /** 漏斗-汇总 */
    @ApiModelProperty(value = "漏斗-汇总", position = 1)
    private List<ResumeChannel_LD_HZ> ld_HZ;

    /** 漏斗-转化率 */
    @ApiModelProperty(value = "漏斗-转化率", position = 1)
    private List<ResumeChannel_LD_ZHL> ld_ZHL;

    /** 饼状图-渠道占比 */
    @ApiModelProperty(value = "饼状图-渠道占比", position = 1)
    private List<ResumeChannel_BT> bT_Channel;

    /** 柱状图-有效简历占比 */
    @ApiModelProperty(value = "柱状图-有效简历占比", position = 1)
    private ResumeChannel_ZZT zzt_YX;

    /** 饼图-面试人数占比 */
    @ApiModelProperty(value = "饼图-面试人数占比", position = 1)
    private List<ResumeChannel_BT> bt_face;

    /** 柱状图-使用人数占比 */
    @ApiModelProperty(value = "柱状图-使用人数占比", position = 1)
    private ResumeChannel_ZZT zzt_try;

    /** 柱状图 招聘未达成-原因 */
    @ApiModelProperty(value = "柱状图 招聘未达成-原因", position = 1)
    private ResumeChannel_ZZT zzt_refuse;

    /** 柱状图 招聘简历来源 - 试用 */
    @ApiModelProperty(value = "柱状图 招聘简历来源 - 试用 ", position = 1)
    private ResumeChannel_ZZT zzt_resource;

    /** 柱状图 部门招聘统计 */
    @ApiModelProperty(value = "柱状图 部门招聘统计", position = 1)
    private ResumeChannel_ZZT zzt_dept;


}
