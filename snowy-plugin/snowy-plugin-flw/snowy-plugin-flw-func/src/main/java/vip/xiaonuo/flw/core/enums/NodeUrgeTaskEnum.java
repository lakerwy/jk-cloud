package vip.xiaonuo.flw.core.enums;

import lombok.Getter;

@Getter
public enum NodeUrgeTaskEnum {
    /**
     *
     * */
    URGE_TEMPLATE("您发起的processName已被催办"),
    /**
     *
     * */
    URGE_CHANNEL("[\"MSG\"]");

    private final String value;
    NodeUrgeTaskEnum(String value) {
        this.value = value;
    }

}
