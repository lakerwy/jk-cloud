package vip.xiaonuo.biz.modular.school.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.school.entity.Resume;
import vip.xiaonuo.biz.modular.school.entity.ResumeRecord;

import java.util.List;

@Getter
@Setter
public class ResumeVo extends Resume {
    /** 简历操作记录 */
    @ApiModelProperty(value = "简历操作记录")
    List<ResumeRecord> resumeRecords;
}
