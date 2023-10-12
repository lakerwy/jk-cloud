package vip.xiaonuo.biz.modular.visit.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;
@Getter
@Setter
public class BizVisitListEditParam {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;
    @ApiModelProperty(value = "外键", position = 10)
    private String key;
    /**
     * 计划时间
     */
    @ApiModelProperty(value = "计划时间", position = 6)
    private Date planDate;

    /**
     * 拜访单位
     */
    @ApiModelProperty(value = "拜访单位", position = 7)
    private String visitUnit;

    /**
     * 主题
     */
    @ApiModelProperty(value = "主题", position = 8)
    private String theme;

    /**
     * 主要人员
     */
    @ApiModelProperty(value = "主要人员", position = 9)
    private String mainPerson;
}
