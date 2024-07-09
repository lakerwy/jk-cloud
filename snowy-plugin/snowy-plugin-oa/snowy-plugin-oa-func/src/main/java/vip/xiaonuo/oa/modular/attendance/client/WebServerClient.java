package vip.xiaonuo.oa.modular.attendance.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.common.consts.FeignConstant;

import java.util.Map;


/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/12 8:54
 */
@FeignClient(name = FeignConstant.WEB_APP)
public interface WebServerClient {
    @GetMapping("/sys/client/getChildrenUserNew")
    String getChildrenUser(@RequestParam(value = "orgId") String orgId);

    @PostMapping("/sys/client/getOrgByMap")
    String getOrgByMap(@RequestBody Map<String,Object> mapParam);
}

