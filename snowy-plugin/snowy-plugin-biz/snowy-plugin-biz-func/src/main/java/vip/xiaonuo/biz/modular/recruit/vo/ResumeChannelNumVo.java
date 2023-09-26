package vip.xiaonuo.biz.modular.recruit.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/8/29 15:57
 */

@Getter
@Setter
public class ResumeChannelNumVo {
    /** 简历来源 - 渠道 */
    @ApiModelProperty(value = "简历来源 - 渠道 ", position = 1)
    private String resumeResource;

    /** 简历投递量 */
    @ApiModelProperty(value = "简历投递量", position = 2)
    private Long touDiNum;

    /** 有效简历量 */
    @ApiModelProperty(value = "有效简历量", position = 3)
    private Long youXiaoNum;

    /** 视频/面试人数 */
    @ApiModelProperty(value = "视频/面试人数", position = 4)
    private Long faceNum;

    /** 初试通过数 */
    @ApiModelProperty(value = "初试通过数", position = 5)
    private Long firstPassNum;

    /** 面试通过人数 */
    @ApiModelProperty(value = "面试通过人数", position = 6)
    private Long facePassNum;

    /** 试用人数 */
    @ApiModelProperty(value = "试用人数", position = 7)
    private Long tryNum;

}
