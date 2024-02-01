package vip.xiaonuo.sso.modular.center.core;

import cn.dev33.satoken.sso.SaSsoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/1/29 11:56
 */
@Component
public class CustomSaSsoTemplate extends SaSsoTemplate {
    @Override
    public String createTicket(Object loginId, String client) {
        return super.createTicket(loginId, client);
    }
}
