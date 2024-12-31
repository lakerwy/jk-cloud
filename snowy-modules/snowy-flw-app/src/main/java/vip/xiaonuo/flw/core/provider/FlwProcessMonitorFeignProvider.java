package vip.xiaonuo.flw.core.provider;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.flw.feign.FlwProcessMonitorFeign;
import vip.xiaonuo.flw.provider.FlwProcessMonitorApiProvider;

/**
 * @author pzh
 * @date 2024/12/30 15:30
 * 流程监控Feign提供者
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class FlwProcessMonitorFeignProvider implements FlwProcessMonitorFeign {

    private final FlwProcessMonitorApiProvider flwProcessMonitorApiProvider;

    @Override
    public JSONObject monitorDetail(String processInstanceId) {
        return flwProcessMonitorApiProvider.monitorDetail(processInstanceId);
    }
}
