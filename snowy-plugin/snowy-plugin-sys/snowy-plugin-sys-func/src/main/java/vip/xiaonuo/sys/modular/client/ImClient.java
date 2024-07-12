package vip.xiaonuo.sys.modular.client;

import cn.hutool.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vip.xiaonuo.common.consts.FeignConstant;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/26 15:07
 */
@FeignClient(name = FeignConstant.WEB_APP)
public interface ImClient {
    @PostMapping("/im/updateImUser")
    String updateImUser(@RequestBody JSONObject userInfo);

    @PostMapping("/tencentIm/updateImUser")
    String tencentUpdateImUser(@RequestBody JSONObject userInfo);
}
