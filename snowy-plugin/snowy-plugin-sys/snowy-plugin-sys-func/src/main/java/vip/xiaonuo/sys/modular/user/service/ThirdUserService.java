package vip.xiaonuo.sys.modular.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.auth.core.pojo.SysLoginUser;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.param.*;
import vip.xiaonuo.sys.modular.user.vo.EpcRoleVo;

import java.util.List;
import java.util.Map;

/**
 * 处理第三方用户和系统用户相关操作接口
 *
 * @author yong.ye
 * @date 2023/6/15 16:03
 */
public interface ThirdUserService extends IService<SysUser> {


    /**
     * 钉钉内部应用跳转（免登）
     * @return
     */
    String toDoDingDingApplication(DingDingLoginParam dingDingLoginParam);

    /**
     * 根据系统用户Id，获取钉钉用户Id
     * @return
     */
    List<Map<String,String>> getAllDingDingIds();

    /**
     * 电子名片，通过账号密码，绑定系统用户，并记录微信openid
     * @return
     */
    String bindECard(WeChatBindEcardParam weChatBindEcardParam);

    /**
     * 电子名片，通过微信小程序openid，获取用户信息
     * @return
     */
    SysLoginUser getUserByOpenId(WeChatUserParam weChatUserParam);

    /**
     * 电子名片，根据系统用户Id，解绑电子名片微信小程序
     * @return
     */
    Boolean unBindECard(WeChatUnBindParam weChatUnBindParam);

    /**
     * 添加系统服务操作记录表
     *
     * @author ljy
     * @date  2024/01/03 16:07
     */
    void putUserToEpc(SystemEpcParam systemEpcParam);

    List<EpcRoleVo> getEpcRole();
}
