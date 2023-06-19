package vip.xiaonuo.sys.modular.user.enums;

import lombok.Getter;
import vip.xiaonuo.common.exception.CommonException;

/**
 * 定义第三方类型
 *
 * @author yong.ye
 * @date 2023/6/15 9:04
 */
@Getter
public enum AuthThirdCategoryEnum {
    /** 钉钉 */
    DingDing("DingDing"),

    /** 微信小程序-电子名片 */
    WeChat_ECard("WeChat_ECard");

    private final String value;

    AuthThirdCategoryEnum(String value) {
        this.value = value;
    }

    public static void validate(String value) {
        boolean flag = DingDing.getValue().equals(value) || WeChat_ECard.getValue().equals(value);
        if(!flag) {
            throw new CommonException("不支持的第三方应用分类：{}", value);
        }
    }
}
