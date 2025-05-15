package vip.xiaonuo.sys.modular.openApi.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 人员信息高级搜索参数
 *
 * @author pzh
 * @date 2025/05/14 17:15
 */
@Getter
@Setter
public class PersonnelSearchParam {

    @ApiModelProperty(value = "职称等级", position = 1)
    private String titleLever;

    @ApiModelProperty(value = "职称专业", position = 2)
    private String titleProfession;

    @ApiModelProperty(value = "证书类别", position = 3)
    private String certType;

    @ApiModelProperty(value = "工种/专业", position = 4)
    private String major;

    @ApiModelProperty(value = "学历", position = 5)
    private String degree;

    @ApiModelProperty(value = "荣誉成果（名称）", position = 6)
    private String honorAward;

}
