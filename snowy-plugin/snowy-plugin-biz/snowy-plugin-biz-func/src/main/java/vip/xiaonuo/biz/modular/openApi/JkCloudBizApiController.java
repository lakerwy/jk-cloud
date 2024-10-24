package vip.xiaonuo.biz.modular.openApi;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.crm.hetong.param.HetongPageParam;
import vip.xiaonuo.biz.modular.crm.hetong.service.HetongService;
import vip.xiaonuo.biz.modular.crm.hetong.vo.HetongDetailVo;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;

/**
 * 建科云-biz模块对外提供查询接口
 *
 * @author pzh
 * @date  2024/10/24 09:30
 */
@Api(tags = "建科云-biz模块对外提供查询接口")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class JkCloudBizApiController {

    @Resource
    private HetongService hetongService;

    /**
     * 获取组织分页
     *
     * @author pzh
     * @date  2024/10/24 09:30
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-CRM-合同分页")
//    @SaCheckPermission("/openApi/biz/crm/hetong/page")
    @GetMapping("/openApi/biz/crm/hetong/page")
    public CommonResult<IPage<HetongDetailVo>> page(HetongPageParam hetongPageParam) {
        return CommonResult.data(hetongService.page(hetongPageParam));
    }
}
