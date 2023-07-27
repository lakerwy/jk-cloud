package vip.xiaonuo.flw.provider;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vip.xiaonuo.auth.api.BaseCommonApi;
import vip.xiaonuo.flw.modular.process.service.FlwProcessService;

import javax.annotation.Resource;

/**
 * @PACKAGE_NAME: vip.xiaonuo.flw.provider
 * @NAME: BaseCommonApiProvider
 * @USER: jack
 * @DATE: 7/26/2023
 * @PROJECT_NAME: jk-cloud
 **/

@Component
public class BaseCommonApiProvider implements BaseCommonApi {

    @Resource
    FlwProcessService flwProcessService;

    @Override
    public String getTemplateSn(String modelId) {
        return flwProcessService.getSn(modelId);
    }
}
