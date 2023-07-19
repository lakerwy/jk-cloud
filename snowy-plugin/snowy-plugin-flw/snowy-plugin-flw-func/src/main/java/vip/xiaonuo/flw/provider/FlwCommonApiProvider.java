package vip.xiaonuo.flw.provider;

import org.springframework.stereotype.Service;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.flw.modular.process.service.FlwProcessService;

import javax.annotation.Resource;

@Service
public class FlwCommonApiProvider implements FlwCommonApi {

    @Resource
    private FlwProcessService flwProcessService;
    @Override
    public String getSnByModelId(String modelId) {
        return flwProcessService.getSn(modelId);
    }
}
