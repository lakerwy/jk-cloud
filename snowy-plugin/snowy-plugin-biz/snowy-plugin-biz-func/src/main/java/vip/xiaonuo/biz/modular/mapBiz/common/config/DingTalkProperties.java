package vip.xiaonuo.biz.modular.mapBiz.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "dingtalk")
public class DingTalkProperties {
    /**

     * AgentId

     */

    private Long agentId;

    /**

     * AppKey

     */

    private String appKey;

    /**

     * AppSecret

     */

    private String appSecret;

    private String isSend;

}
