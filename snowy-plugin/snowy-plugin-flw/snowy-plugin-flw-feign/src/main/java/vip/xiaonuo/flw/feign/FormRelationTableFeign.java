package vip.xiaonuo.flw.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vip.xiaonuo.common.consts.FeignConstant;

/**
 * @author mayike
 * @date 2023/07/30 3:57
 * 业务表和流程关系Feign提供者
 */
@FeignClient(name= FeignConstant.FLW_APP, contextId = "FormRelationTableFeign")
public interface FormRelationTableFeign {
}
