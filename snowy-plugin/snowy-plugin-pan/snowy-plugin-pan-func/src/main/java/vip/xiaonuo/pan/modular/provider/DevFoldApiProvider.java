package vip.xiaonuo.pan.modular.provider;

import org.springframework.stereotype.Service;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.pan.api.DevFoldApi;
import vip.xiaonuo.pan.modular.fold.service.DevFoldService;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/8/29 16:51
 */
@Service
public class DevFoldApiProvider implements DevFoldApi {
    @Resource
    DevFoldService devFoldService;

    @Override
    public void createSelfFold() {
        devFoldService.createSelfFold();
    }

    @Override
    public void createGroupFold() {
        devFoldService.createGroupFold();
    }
}
