package vip.xiaonuo.im.modular.openIm.param;

import lombok.Data;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/25 16:56
 */
@Data
public class OpenImUser {
    private String verifyCode = "666666";
    private Boolean autoLogin = false;
    private int platform = 5;
    private UserBean user;
}
