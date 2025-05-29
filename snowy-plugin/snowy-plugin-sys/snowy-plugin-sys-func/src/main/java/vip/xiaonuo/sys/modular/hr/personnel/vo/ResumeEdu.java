package vip.xiaonuo.sys.modular.hr.personnel.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 简历-eduJson实体
 *
 * @author pzh
 * @date  2025/05/29 10:08
 */
@Getter
@Setter
public class ResumeEdu {

    /** 起止时间 */
    @ApiModelProperty(value = "起止时间", position = 1)
    private String timedOut;

    /** 学校名称 */
    @ApiModelProperty(value = "学校名称", position = 2)
    private String schoolName;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 3)
    private String specialized;

    /** 学历 */
    @ApiModelProperty(value = "学历（本科，专科，硕士等）", position = 4)
    private String degree;

    /** 学校类别 */
    @ApiModelProperty(value = "学校类别（一本、二本等）", position = 5)
    private String schoolAtegory;

    /** 证明人和联系电话 */
    @ApiModelProperty(value = "是否统招", position = 6)
    private String isMap;

}
