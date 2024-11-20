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
import vip.xiaonuo.sys.modular.hr.assetManage.entity.JkAssetManage;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManagePageParam;
import vip.xiaonuo.sys.modular.hr.assetManage.service.JkAssetManageService;
import vip.xiaonuo.sys.modular.hr.honorAward.entity.EnterpriseHonor;
import vip.xiaonuo.sys.modular.hr.honorAward.entity.HonorAward;
import vip.xiaonuo.sys.modular.hr.honorAward.param.EnterpriseHonorPageParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.HonorAwardPageParam;
import vip.xiaonuo.sys.modular.hr.honorAward.service.EnterpriseHonorService;
import vip.xiaonuo.sys.modular.hr.honorAward.service.HonorAwardService;
import vip.xiaonuo.sys.modular.hr.personnel.param.PersonnelPageParam;
import vip.xiaonuo.sys.modular.hr.personnel.service.PersonnelService;
import vip.xiaonuo.sys.modular.hr.personnel.vo.PersonnelListDetailVo;
import vip.xiaonuo.sys.modular.hr.techAchive.entity.NoveltySearch;
import vip.xiaonuo.sys.modular.hr.techAchive.entity.PatentInfo;
import vip.xiaonuo.sys.modular.hr.techAchive.entity.SoftwareCopyright;
import vip.xiaonuo.sys.modular.hr.techAchive.entity.Standard;
import vip.xiaonuo.sys.modular.hr.techAchive.param.NoveltySearchPageParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.PatentInfoPageParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.SoftwareCopyrightPageParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.StandardPageParam;
import vip.xiaonuo.sys.modular.hr.techAchive.service.*;
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

    @Resource
    private JkAssetManageService jkAssetManageService;

    @Resource
    private EnterpriseHonorService enterpriseHonorService;

    @Resource
    private HonorAwardService honorAwardService;

    @Resource
    private PatentInfoService patentInfoService;

    @Resource
    private SoftwareCopyrightService softwareCopyrightService;

    @Resource
    private StandardService standardService;

    @Resource
    private NoveltySearchService noveltySearchService;

    @Resource
    private OtherTechAchiveService otherTechAchiveService;


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
    public CommonResult<Page<PersonnelListDetailVo>> pageList(PersonnelPageParam personnelPageParam) {
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

    /**
     * 获取建科资产管理表分页
     *
     * @author pzh
     * @date  2024/10/28 11:10
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("获取建科资产管理表分页")
//    @SaCheckPermission("/openApi/sys/assetManageFlow/page")
    @GetMapping("/openApi/sys/assetManageFlow/page")
    public CommonResult<Page<JkAssetManage>> page(JkAssetManagePageParam jkAssetManagePageParam) {
        return CommonResult.data(jkAssetManageService.page(jkAssetManagePageParam));
    }

    /**
     * 获取企业荣誉分页
     *
     * @author pzh
     * @date  2024/11/19 11:01
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("获取企业荣誉分页")
//    @SaCheckPermission("/openApi/sys/enterpriseHonor/page")
    @GetMapping("/openApi/sys/enterpriseHonor/page")
    public CommonResult<Page<EnterpriseHonor>> page(EnterpriseHonorPageParam enterpriseHonorPageParam) {
        return CommonResult.data(enterpriseHonorService.page(enterpriseHonorPageParam));
    }

    /**
     * 获取荣誉奖项分页
     *
     * @author pzh
     * @date  2024/11/19 11:10
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取荣誉奖项分页")
    // @SaCheckPermission("/openApi/sys/honorAward/page")
    @GetMapping("/openApi/sys/honorAward/page")
    public CommonResult<Page<HonorAward>> page(HonorAwardPageParam honorAwardPageParam) {
        return CommonResult.data(honorAwardService.page(honorAwardPageParam));
    }

    /**
     * 获取专利信息分页
     *
     * @author pzh
     * @date  2024/11/19 15:00
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取专利信息分页")
    // @SaCheckPermission("/openApi/sys/techAchive/patentInfo/page")
    @GetMapping("/openApi/sys/techAchive/patentInfo/page")
    public CommonResult<Page<PatentInfo>> page(PatentInfoPageParam patentInfoPageParam) {
        return CommonResult.data(patentInfoService.page(patentInfoPageParam));
    }

    /**
     * 获取软著信息分页
     *
     * @author pzh
     * @date  2024/11/19 16:26
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取软著信息分页")
    // @SaCheckPermission("/openApi/sys/techAchive/softwareCopyright/page")
    @GetMapping("/openApi/sys/techAchive/softwareCopyright/page")
    public CommonResult<Page<SoftwareCopyright>> page(SoftwareCopyrightPageParam softwareCopyrightPageParam) {
        return CommonResult.data(softwareCopyrightService.page(softwareCopyrightPageParam));
    }

    /**
     * 获取标准规范分页
     *
     * @author pzh
     * @date  2024/11/20 08:40
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("获取标准规范分页")
    // @SaCheckPermission("/openApi/sys/techAchive/standard/page")
    @GetMapping("/openApi/sys/techAchive/standard/page")
    public CommonResult<Page<Standard>> page(StandardPageParam standardPageParam) {
        return CommonResult.data(standardService.page(standardPageParam));
    }

    /**
     * 获取查新分页
     *
     * @author pzh
     * @date  2024/11/20 09:35
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("获取查新分页")
    // @SaCheckPermission("/openApi/sys/techAchive/noveltySearch/page")
    @GetMapping("/openApi/sys/techAchive/noveltySearch/page")
    public CommonResult<Page<NoveltySearch>> page(NoveltySearchPageParam noveltySearchPageParam) {
        return CommonResult.data(noveltySearchService.page(noveltySearchPageParam));
    }
}
