package vip.xiaonuo.im.modular.openIm.param;

import lombok.Data;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/26 14:08
 */
@Data
public class LoginParam {
    private int platform;
    private String phoneNumber;
    private String password;
    private String areaCode = "+86";
    private String verifyCode = "666666";  //用超级验证验证
    private String deviceId;
}
