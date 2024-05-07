package vip.xiaonuo.im.modular.chat.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    private String userId;
    private String imToken;
    private boolean register;
    private String userName;
    //肖像
    private String portrait;
    private String resetCode;
    private Boolean enable;
}
