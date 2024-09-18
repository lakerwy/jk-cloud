package vip.xiaonuo.biz.modular.car.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yong.ye
 * @date 2023/11/9 10:34
 *
 * 科科用车：用车申请 - 钉钉应用配置
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "dingding.users")
public class UsersProperties {

    /**
     * 【用车管理】钉钉应用的AgentId
     */
    private String agentId;

    /**
     * 【用车管理】钉钉应用的唯一标识key
     */
    private String appKey;

    /**
     * 【用车管理】应用的密钥。AppKey和AppSecret可在钉钉开发者后台的应用详情页面获取。
     */
    private String appSecret;

    /**
     *  应用跳转链接前缀
     */
    private String linkUrl;

    /**
     *  徐煦的钉钉userid  0235496007787414
     */
    private String carAdminDingDingUserId;
}
