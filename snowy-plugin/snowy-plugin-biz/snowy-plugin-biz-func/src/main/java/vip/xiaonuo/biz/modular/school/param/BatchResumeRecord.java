package vip.xiaonuo.biz.modular.school.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BatchResumeRecord {
    /** 批量简历操作记录 */
    @ApiModelProperty(value = "批量简历操作记录", position = 1)
    private List<ResumeRecordAddParam> resumeRecordList;
}
