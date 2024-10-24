package vip.xiaonuo.sys.modular.openApi;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.sys.modular.hr.personnel.param.PersonnelPageParam;
import vip.xiaonuo.sys.modular.hr.personnel.service.PersonnelService;
import vip.xiaonuo.sys.modular.hr.personnel.vo.PersonnelListDetailVo;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.param.SysOrgPageParam;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;

import javax.annotation.Resource;

/**
 * 建科云-web模块对外提供查询接口
 *
 * @author pzh
 * @date  2024/10/21 15:30
 */
@Api(tags = "建科云-web模块对外提供查询接口")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class JkCloudWebApiController {


    @Resource
    private PersonnelService personnelService;

    @Resource
    private SysOrgService sysOrgService;

    /**
     * 获取业务表-人事档案分页(对外免token接口)
     *
     * @author pzh
     * @date  2024/10/21 15:30
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-人事档案分页")
//    @SaCheckPermission("/openApi/sys/personnel/pageList")
    @GetMapping("/openApi/sys/personnel/pageList")
    public CommonResult<Page<PersonnelListDetailVo>> publicPageList(PersonnelPageParam personnelPageParam) {
        return CommonResult.data(personnelService.pageListWithDetail(personnelPageParam));
    }

    /**
     * 获取组织分页
     *
     * @author pzh
     * @date  2024/10/21 15:30
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("获取组织分页")
//    @SaCheckPermission("/openApi/sys/org/page")
    @GetMapping("/openApi/sys/org/page")
    public CommonResult<Page<SysOrg>> page(SysOrgPageParam sysOrgPageParam) {
        return CommonResult.data(sysOrgService.page(sysOrgPageParam));
    }
}
