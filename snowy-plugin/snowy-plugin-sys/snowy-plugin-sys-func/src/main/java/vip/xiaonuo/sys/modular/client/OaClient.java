package vip.xiaonuo.sys.modular.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vip.xiaonuo.common.consts.FeignConstant;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/9 9:40
 */
@FeignClient(name = FeignConstant.OA_APP)
public interface OaClient {

    @GetMapping("/oa/Holiday/init")
    String init(@RequestParam(value = "userId") String userId);

    @PostMapping("/oa/Holiday/init")
    void deleteByIds(@RequestBody List<String> ids);
}
