package vip.xiaonuo.flw.provider;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;
import vip.xiaonuo.flw.api.FlwProcessMonitorApi;
import vip.xiaonuo.flw.modular.process.param.FlwProcessIdParam;
import vip.xiaonuo.flw.modular.process.result.FlwProcessDetailResult;
import vip.xiaonuo.flw.modular.process.service.FlwProcessService;

import javax.annotation.Resource;

@Service
public class FlwProcessMonitorApiProvider implements FlwProcessMonitorApi {

    @Resource
    FlwProcessService flwProcessService;

    @Override
    public JSONObject monitorDetail(String processInstanceId) {
        FlwProcessIdParam flwProcessIdParam = new FlwProcessIdParam();
        flwProcessIdParam.setId(processInstanceId);
        FlwProcessDetailResult flwProcessDetailResult = flwProcessService.detail(flwProcessIdParam);
        return JSONUtil.parseObj(flwProcessDetailResult);
    }
}
