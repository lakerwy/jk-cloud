package vip.xiaonuo.bp.modular.platform.util;

import cn.dev33.satoken.stp.StpUtil;
import vip.xiaonuo.bp.modular.platform.vo.BpLoginUser;

/**
 * 招采平台 - 用户登录信息
 */
public class LoginBpUserUtil {

    /**
     * 获取当前登录用户
     **/
    public static BpLoginUser getLoginUser() {
        return (BpLoginUser) StpUtil.getTokenSession().get("bpLoginUser");
    }

}
