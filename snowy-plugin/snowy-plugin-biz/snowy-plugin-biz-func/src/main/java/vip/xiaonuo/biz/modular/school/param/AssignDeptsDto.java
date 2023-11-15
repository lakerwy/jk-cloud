package vip.xiaonuo.biz.modular.school.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AssignDeptsDto {

    /** 简历Id集合 */
    @ApiModelProperty(value = "简历Id集合", position = 1)
    private List<String> ids;

    /** 部门推荐|部门取消推荐  操作记录 */
    @ApiModelProperty(value = "部门推荐|部门取消推荐  操作记录", position = 2)
    private ResumeRecordAddParam deptRecord;

}

