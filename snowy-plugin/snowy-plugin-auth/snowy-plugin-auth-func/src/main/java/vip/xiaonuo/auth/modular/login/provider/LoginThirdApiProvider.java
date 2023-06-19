package vip.xiaonuo.auth.modular.login.provider;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Service;
import vip.xiaonuo.auth.api.LoginThirdApi;
import vip.xiaonuo.auth.core.enums.SaClientTypeEnum;
import vip.xiaonuo.auth.modular.login.enums.AuthDeviceTypeEnum;
import vip.xiaonuo.auth.modular.login.service.AuthService;

import javax.annotation.Resource;

/**
 * @author yong.ye
 * @date 2023/6/15 8:45
 */

@Service
public class LoginThirdApiProvider implements LoginThirdApi {

    @Resource
    private AuthService authService;

    /**
     * 根据用户id和客户端类型登录，用于第三方免登（钉钉免登、微信名片小程序绑定系统用户，实现免登）
     *
     * @param userId 系统用户Id
     * @param device 登录设备类型
     * @param type 客户端类型
     * @return
     */
    @Override
    public String doLoginThirdById(String userId, String type,String device) {
        if(ObjectUtil.isEmpty(type)){
            type = SaClientTypeEnum.B.getValue();
        }
        if(ObjectUtil.isEmpty(device)){
            device = AuthDeviceTypeEnum.PC.getValue();
        }
        return authService.doLoginById(userId,device,type);
    }
}
