package vip.xiaonuo.sys.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import vip.xiaonuo.common.consts.FeignConstant;

/**
 * 配置Feign接口
 *
 */
@FeignClient(name= FeignConstant.WEB_APP, contextId = "DingTalkFeign")
public interface DingTalkFeign {
    @RequestMapping("/feign/dev/dingtalk/syncLastMonthAttend")
    void syncLastMonthAttend();

}
