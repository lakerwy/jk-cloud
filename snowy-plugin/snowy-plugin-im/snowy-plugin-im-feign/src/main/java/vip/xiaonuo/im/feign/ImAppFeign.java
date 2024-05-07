package vip.xiaonuo.im.feign;

import org.springframework.cloud.openfeign.FeignClient;
import vip.xiaonuo.common.consts.FeignConstant;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/6 16:28
 */
@FeignClient(name= FeignConstant.WEB_APP, contextId = "ImAppFeign")
public interface ImAppFeign {
}
