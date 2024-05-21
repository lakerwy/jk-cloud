package vip.xiaonuo.dev.modular.message.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
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

    private Boolean isSend;

    private String h5webUrl;

    /**
     * 用户离职审批通过时，是否同步删除钉钉用户
     */
    private Boolean isDelDingUser;

}
