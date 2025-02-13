package vip.xiaonuo.dev.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import vip.xiaonuo.common.consts.FeignConstant;

/**
 * 配置Feign接口
 *
 * @author dongxiayu
 * @date 2022/11/22 11:10
 */
@FeignClient(name= FeignConstant.WEB_APP, contextId = "DingTalkFeign")
public interface DingTalkFeign {
    @RequestMapping("/feign/dev/dingtalk/syncLastMonthAttend")
    void syncLastMonthAttend();

}
