package vip.xiaonuo.biz.modular.recruit.enums;

import lombok.Getter;
import vip.xiaonuo.common.exception.CommonException;

/**
 * @author yong.ye
 * @date 2023/8/29 10:27
 */
@Getter
public enum BizRecruitResumeStageEnum {
    /** 初筛阶段 */
    FITER_STAGE("1"),

    /** 初试阶段 */
    FIRST_STAGE("2"),

    /** 复试阶段 */
    RETEST_STAGE("3"),

    /** 录取阶段 */
    PEDING_STAGE("4");

    private final String value;

    BizRecruitResumeStageEnum(String value) {
        this.value = value;
    }

    public static void validate(String value) {
        boolean flag = FITER_STAGE.getValue().equals(value) || FIRST_STAGE.getValue().equals(value)
                || RETEST_STAGE.getValue().equals(value) || PEDING_STAGE.getValue().equals(value);
        if(!flag) {
            throw new CommonException("不支持的阶段类型：{}", value);
        }
    }
}
