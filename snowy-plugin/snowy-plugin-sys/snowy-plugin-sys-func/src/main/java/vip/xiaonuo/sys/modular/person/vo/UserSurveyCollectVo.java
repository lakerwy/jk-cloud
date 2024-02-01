package vip.xiaonuo.sys.modular.person.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.person.entity.UserSurveyCollect;
import vip.xiaonuo.sys.modular.person.entity.UserSurveyRecord;

import java.util.List;

/**
 * 详情
 */

@Getter
@Setter
public class UserSurveyCollectVo extends UserSurveyCollect {

    /** 操作人姓名 */
    @ApiModelProperty(value = "操作人姓名", position = 16)
    private List<UserSurveyRecord> recordList;
}
