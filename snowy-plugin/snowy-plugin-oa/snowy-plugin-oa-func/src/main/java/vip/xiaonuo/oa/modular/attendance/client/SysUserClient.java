package vip.xiaonuo.oa.modular.attendance.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vip.xiaonuo.common.consts.FeignConstant;


/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/12 8:54
 */
@FeignClient(name = FeignConstant.WEB_APP)
public interface SysUserClient {
    @GetMapping("/sys/org/getChildrenUserNew")
    String getChildrenUser(@RequestParam(value = "orgId") String orgId);
}

