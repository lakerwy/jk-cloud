package vip.xiaonuo.biz.modular.school.enums;


import lombok.Getter;

@Getter
public enum ResumeOprResultEnum {

    /** 通过|合格 */
    Pass("1"),

    /** 不通过|不合格 */
    NotPass("2"),

    /** 未到面 */
    NotFace("3"),

    /** offer-已接收 */
    Arrived("4"),

    /** offer-已拒绝 */
    Refuse("5"),

    /** 转发部门 */
    ReSend("6");

    private final String value;

    ResumeOprResultEnum(String value) {
        this.value = value;
    }

}
