package vip.xiaonuo.biz.core.context.flw;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.api.FlwProcessMonitorApi;
import vip.xiaonuo.flw.feign.FlwProcessMonitorFeign;

@Slf4j
@RequiredArgsConstructor
@Component
public class FlwProcessMonitorApiContextBean implements FlwProcessMonitorApi {

    private final FlwProcessMonitorFeign flwProcessMonitorFeign;

    @Override
    public JSONObject monitorDetail(String processInstanceId) {
        return flwProcessMonitorFeign.monitorDetail(processInstanceId);
    }
}
