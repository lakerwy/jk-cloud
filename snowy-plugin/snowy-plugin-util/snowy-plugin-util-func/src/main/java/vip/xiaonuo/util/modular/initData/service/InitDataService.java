package vip.xiaonuo.util.modular.initData.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.util.modular.initData.entity.InitData;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/10 9:49
 */
public interface InitDataService extends IService<InitData> {

    boolean getInitStatusByType(String type);
}
