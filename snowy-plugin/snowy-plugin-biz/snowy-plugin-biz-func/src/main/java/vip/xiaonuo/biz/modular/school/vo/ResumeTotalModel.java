package vip.xiaonuo.biz.modular.school.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResumeTotalModel {

    /** 简历收集 */
    @ApiModelProperty(value = "简历收集")
    private Integer collectNum ;

    /** 有效简历 */
    @ApiModelProperty(value = "有效简历")
    private Integer effectiveNum ;

    /** 简历初筛 */
    @ApiModelProperty(value = "简历初筛")
    private Integer filterNum ;

    /** 面试通知 */
    @ApiModelProperty(value = "面试通知")
    private Integer notifiedNum ;

    /** 面试考察 */
    @ApiModelProperty(value = "面试考察")
    private Integer checkNum ;

    /** 面试通过 */
    @ApiModelProperty(value = "面试通过")
    private Integer passNum ;

    /** 签约录取 */
    @ApiModelProperty(value = "签约录取")
    private Integer offerNum ;
}
