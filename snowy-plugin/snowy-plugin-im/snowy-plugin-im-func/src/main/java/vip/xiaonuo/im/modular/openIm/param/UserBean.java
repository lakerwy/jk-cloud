package vip.xiaonuo.im.modular.openIm.param;

import lombok.Data;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/26 10:03
 */
@Data
public class UserBean {
    private String nickName;
    private String faceURL;
    private String gender;
    private String phoneNumber;
    private String password="e10adc3949ba59abbe56e057f20f883e";
    private String areaCode = "+86";
}
