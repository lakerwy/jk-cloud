package vip.xiaonuo.web.core.provider.dev.log;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.dev.feign.DevLogFeign;
import vip.xiaonuo.dev.modular.log.provider.DevLogApiProvider;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/7 9:32
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Configuration
public class DevLogFeignProvider implements DevLogFeign {

    @Resource
    DevLogApiProvider devLogApiProvider;
    @Override
    @RequestMapping("/feign/devLog/executeSaveLog")
    public void executeSaveLog(JSONObject jsonObject) {
        devLogApiProvider.executeSaveLog(jsonObject);
    }
}
