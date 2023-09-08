package vip.xiaonuo.pan.modular.provider;

import org.springframework.stereotype.Service;
import vip.xiaonuo.pan.api.DevFoldApi;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/8/29 16:51
 */
@Service
public class DevFoldApiProvider implements DevFoldApi {
    @Resource
    UserFileService userFileService;

    @Override
    public void createSelfFold() {
        userFileService.createSelfFold();
    }

    @Override
    public void createGroupFold() {
        userFileService.createGroupFold();
    }
}
