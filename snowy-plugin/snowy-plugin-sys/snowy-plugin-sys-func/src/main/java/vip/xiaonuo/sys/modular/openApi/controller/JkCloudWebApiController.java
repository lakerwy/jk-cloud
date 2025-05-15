package vip.xiaonuo.sys.modular.openApi.controller;


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
import vip.xiaonuo.sys.modular.hr.certificationSystem.entity.CertificationSystem;
import vip.xiaonuo.sys.modular.hr.certificationSystem.param.CertificationSystemIdParam;
import vip.xiaonuo.sys.modular.hr.certificationSystem.param.CertificationSystemPageParam;
import vip.xiaonuo.sys.modular.hr.certificationSystem.service.CertificationSystemService;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.entity.CreditEvaluation;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.param.CreditEvaluationIdParam;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.param.CreditEvaluationPageParam;
import vip.xiaonuo.sys.modular.hr.creditEvaluation.service.CreditEvaluationService;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.entity.FinancialAuditReport;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.param.FinancialAuditReportIdParam;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.param.FinancialAuditReportPageParam;
import vip.xiaonuo.sys.modular.hr.financialAuditReport.service.FinancialAuditReportService;
import vip.xiaonuo.sys.modular.hr.financialSheet.entity.FinancialSheet;
import vip.xiaonuo.sys.modular.hr.financialSheet.param.FinancialSheetIdParam;
import vip.xiaonuo.sys.modular.hr.financialSheet.param.FinancialSheetPageParam;
import vip.xiaonuo.sys.modular.hr.financialSheet.service.FinancialSheetService;
import vip.xiaonuo.sys.modular.hr.honorAward.entity.EnterpriseHonor;
import vip.xiaonuo.sys.modular.hr.honorAward.entity.HonorAward;
import vip.xiaonuo.sys.modular.hr.honorAward.param.EnterpriseHonorPageParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.HonorAwardPageParam;
import vip.xiaonuo.sys.modular.hr.honorAward.service.EnterpriseHonorService;
import vip.xiaonuo.sys.modular.hr.honorAward.service.HonorAwardService;
import vip.xiaonuo.sys.modular.hr.personnel.param.PersonnelIdParam;
import vip.xiaonuo.sys.modular.hr.personnel.param.PersonnelPageParam;
import vip.xiaonuo.sys.modular.hr.personnel.service.PersonnelService;
import vip.xiaonuo.sys.modular.hr.personnel.vo.PersonnelDetailVo;
import vip.xiaonuo.sys.modular.hr.reputation.entity.Reputation;
import vip.xiaonuo.sys.modular.hr.reputation.param.ReputationIdParam;
import vip.xiaonuo.sys.modular.hr.reputation.param.ReputationPageParam;
import vip.xiaonuo.sys.modular.hr.reputation.service.ReputationService;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.entity.TaxGradeEvaluation;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param.TaxGradeEvaluationIdParam;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param.TaxGradeEvaluationPageParam;
import vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.service.TaxGradeEvaluationService;
import vip.xiaonuo.sys.modular.hr.techAchive.entity.*;
import vip.xiaonuo.sys.modular.hr.techAchive.param.*;
import vip.xiaonuo.sys.modular.hr.techAchive.service.*;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.entity.ZizhiCertificate;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.param.ZizhiCertificateIdParam;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.param.ZizhiCertificatePageParam;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.service.ZizhiCertificateService;
import vip.xiaonuo.sys.modular.openApi.param.PersonnelSearchParam;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.param.SysOrgPageParam;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private TaxGradeEvaluationService taxGradeEvaluationService;

    @Resource
    private CertificationSystemService certificationSystemService;

    @Resource
    private ReputationService reputationService;

    @Resource
    private CreditEvaluationService creditEvaluationService;

    @Resource
    private FinancialAuditReportService financialAuditReportService;

    @Resource
    private FinancialSheetService financialSheetService;

    @Resource
    private ZizhiCertificateService zizhiCertificateService;

    /**
     * 获取业务表-人事档案分页(对外免token接口)
     *
     * @author pzh
     * @date 2024/10/21 15:30
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-人事档案分页")
//    @SaCheckPermission("/openApi/sys/personnel/pageList")
    @GetMapping("/openApi/sys/personnel/pageList")
    public CommonResult<Page<PersonnelDetailVo>> page(PersonnelPageParam personnelPageParam) {
        return CommonResult.data(personnelService.pageListWithDetail(personnelPageParam));
    }

    /**
     * 获取业务表-人事档案分页(对外免token接口)
     *
     * @author pzh
     * @date 2024/10/21 15:30
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-人事档案详情")
//    @SaCheckPermission("/openApi/sys/personnel/detail")
    @GetMapping("/openApi/sys/personnel/detail")
    public CommonResult<PersonnelDetailVo> detail(PersonnelIdParam personnelIdParam) {
        return CommonResult.data(personnelService.detail2(personnelIdParam));
    }

    /**
     * 获取组织分页
     *
     * @author pzh
     * @date 2024/10/21 15:30
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
     * @date 2024/10/28 11:10
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
     * @date 2024/11/19 11:01
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
     * @date 2024/11/19 11:10
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
     * @date 2024/11/19 15:00
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
     * @date 2024/11/19 16:26
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
     * @date 2024/11/20 08:40
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
     * @date 2024/11/20 09:35
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("获取查新分页")
    // @SaCheckPermission("/openApi/sys/techAchive/noveltySearch/page")
    @GetMapping("/openApi/sys/techAchive/noveltySearch/page")
    public CommonResult<Page<NoveltySearch>> page(NoveltySearchPageParam noveltySearchPageParam) {
        return CommonResult.data(noveltySearchService.page(noveltySearchPageParam));
    }

    /**
     * 获取其他（科技成果）分页
     *
     * @author pzh
     * @date 2024/11/20 10:00
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("获取其他（科技成果）分页")
    // @SaCheckPermission("/openApi/sys/techAchive/otherTechAchive/page")
    @GetMapping("/openApi/sys/techAchive/otherTechAchive/page")
    public CommonResult<Page<OtherTechAchive>> page(OtherTechAchivePageParam otherTechAchivePageParam) {
        return CommonResult.data(otherTechAchiveService.page(otherTechAchivePageParam));
    }

    /**
     * 获取纳税等级评价分页
     *
     * @author pzh
     * @date 2024/11/27 15:02
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("获取纳税等级评价分页")
    // @SaCheckPermission("/openApi/sys/taxGradeEvaluation/page")
    @GetMapping("/openApi/sys/taxGradeEvaluation/page")
    public CommonResult<Page<TaxGradeEvaluation>> page(TaxGradeEvaluationPageParam taxGradeEvaluationPageParam) {
        return CommonResult.data(taxGradeEvaluationService.page(taxGradeEvaluationPageParam));
    }

    /**
     * 获取纳税等级评价详情
     *
     * @author pzh
     * @date 2024/11/27 15:02
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("获取纳税等级评价详情")
    // @SaCheckPermission("/openApi/sys/taxGradeEvaluation/detail")
    @GetMapping("/openApi/sys/taxGradeEvaluation/detail")
    public CommonResult<TaxGradeEvaluation> detail(TaxGradeEvaluationIdParam taxGradeEvaluationIdParam) {
        return CommonResult.data(taxGradeEvaluationService.detail(taxGradeEvaluationIdParam));
    }

    /**
     * 获取认证体系分页
     *
     * @author pzh
     * @date 2024/11/27 15:02
     */
    @ApiOperationSupport(order = 20)
    @ApiOperation("获取认证体系分页")
    // @SaCheckPermission("/openApi/sys/certificationSystem/page")
    @GetMapping("/openApi/sys/certificationSystem/page")
    public CommonResult<Page<CertificationSystem>> page(CertificationSystemPageParam certificationSystemPageParam) {
        return CommonResult.data(certificationSystemService.page(certificationSystemPageParam));
    }

    /**
     * 获取认证体系详情
     *
     * @author pzh
     * @date 2024/11/27 15:02
     */
    @ApiOperationSupport(order = 21)
    @ApiOperation("获取认证体系详情")
    // @SaCheckPermission("/openApi/sys/certificationSystem/detail")
    @GetMapping("/openApi/sys/certificationSystem/detail")
    public CommonResult<CertificationSystem> detail(CertificationSystemIdParam certificationSystemIdParam) {
        return CommonResult.data(certificationSystemService.detail(certificationSystemIdParam));
    }


    /**
     * 获取信誉情况分页
     *
     * @author pzh
     * @date 2024/11/28 09:02
     */
    @ApiOperationSupport(order = 22)
    @ApiOperation("获取信誉情况分页")
    // @SaCheckPermission("/openApi/sys/reputation/page")
    @GetMapping("/openApi/sys/reputation/page")
    public CommonResult<Page<Reputation>> page(ReputationPageParam reputationPageParam) {
        return CommonResult.data(reputationService.page(reputationPageParam));
    }

    /**
     * 获取认证体系详情
     *
     * @author pzh
     * @date 2024/11/28 09:02
     */
    @ApiOperationSupport(order = 23)
    @ApiOperation("获取信誉情况详情")
    // @SaCheckPermission("/openApi/sys/reputation/detail")
    @GetMapping("/openApi/sys/reputation/detail")
    public CommonResult<Reputation> detail(ReputationIdParam reputationIdParam) {
        return CommonResult.data(reputationService.detail(reputationIdParam));
    }

    /**
     * 获取信用评价分页
     *
     * @author pzh
     * @date 2024/11/28 09:02
     */
    @ApiOperationSupport(order = 23)
    @ApiOperation("获取信用评价分页")
    // @SaCheckPermission("/openApi/sys/creditEvaluation/page")
    @GetMapping("/openApi/sys/creditEvaluation/page")
    public CommonResult<Page<CreditEvaluation>> page(CreditEvaluationPageParam creditEvaluationPageParam) {
        return CommonResult.data(creditEvaluationService.page(creditEvaluationPageParam));
    }

    /**
     * 获取信用评价详情
     *
     * @author pzh
     * @date 2024/11/28 09:02
     */
    @ApiOperationSupport(order = 24)
    @ApiOperation("获取信用评价详情")
    // @SaCheckPermission("/openApi/sys/creditEvaluation/detail")
    @GetMapping("/openApi/sys/creditEvaluation/detail")
    public CommonResult<CreditEvaluation> detail(CreditEvaluationIdParam creditEvaluationIdParam) {
        return CommonResult.data(creditEvaluationService.detail(creditEvaluationIdParam));
    }

    /**
     * 获取财务审计报告分页
     *
     * @author pzh
     * @date 2024/11/28 09:02
     */
    @ApiOperationSupport(order = 25)
    @ApiOperation("获取财务审计报告分页")
    // @SaCheckPermission("/openApi/sys/financialAuditReport/page")
    @GetMapping("/openApi/sys/financialAuditReport/page")
    public CommonResult<Page<FinancialAuditReport>> page(FinancialAuditReportPageParam financialAuditReportPageParam) {
        return CommonResult.data(financialAuditReportService.page(financialAuditReportPageParam));
    }

    /**
     * 获取财务审计报告详情
     *
     * @author pzh
     * @date 2024/11/28 09:02
     */
    @ApiOperationSupport(order = 26)
    @ApiOperation("获取财务审计报告详情")
    // @SaCheckPermission("/openApi/sys/financialAuditReport/detail")
    @GetMapping("/openApi/sys/financialAuditReport/detail")
    public CommonResult<FinancialAuditReport> detail(FinancialAuditReportIdParam financialAuditReportIdParam) {
        return CommonResult.data(financialAuditReportService.detail(financialAuditReportIdParam));
    }

    /**
     * 获取财务状况表分页
     *
     * @author pzh
     * @date 2024/11/28 09:02
     */
    @ApiOperationSupport(order = 27)
    @ApiOperation("获取财务状况表分页")
    // @SaCheckPermission("/openApi/sys/financialSheet/page")
    @GetMapping("/openApi/sys/financialSheet/page")
    public CommonResult<Page<FinancialSheet>> page(FinancialSheetPageParam financialSheetPageParam) {
        return CommonResult.data(financialSheetService.page(financialSheetPageParam));
    }

    /**
     * 获取财务状况表详情
     *
     * @author pzh
     * @date 2024/11/28 09:02
     */
    @ApiOperationSupport(order = 28)
    @ApiOperation("获取财务状况表详情")
    // @SaCheckPermission("/openApi/sys/financialSheet/detail")
    @GetMapping("/openApi/sys/financialSheet/detail")
    public CommonResult<FinancialSheet> detail(FinancialSheetIdParam financialSheetIdParam) {
        return CommonResult.data(financialSheetService.detail(financialSheetIdParam));
    }

    /**
     * 获取资质证照分页
     *
     * @author pzh
     * @date 2024/11/28 09:02
     */
    @ApiOperationSupport(order = 29)
    @ApiOperation("获取资质证照分页")
    // @SaCheckPermission("/openApi/sys/zizhiCertificate/page")
    @GetMapping("/openApi/sys/zizhiCertificate/page")
    public CommonResult<Page<ZizhiCertificate>> page(ZizhiCertificatePageParam zizhiCertificatePageParam) {
        return CommonResult.data(zizhiCertificateService.page(zizhiCertificatePageParam));
    }

    /**
     * 获取资质证照详情
     *
     * @author pzh
     * @date 2024/11/28 09:02
     */
    @ApiOperationSupport(order = 30)
    @ApiOperation("获取资质证照详情")
    // @SaCheckPermission("/openApi/sys/zizhiCertificate/detail")
    @GetMapping("/openApi/sys/zizhiCertificate/detail")
    public CommonResult<ZizhiCertificate> detail(ZizhiCertificateIdParam zizhiCertificateIdParam) {
        return CommonResult.data(zizhiCertificateService.detail(zizhiCertificateIdParam));
    }


    /**
     * 人员信息高级搜索
     *
     * @author pzh
     * @date 2025/05/14 17:15
     */
    @ApiOperationSupport(order = 40)
    @ApiOperation("人员信息高级搜索")
    @GetMapping("/openApi/sys/personnel/search")
    public CommonResult<List<String>> personnelSearch(PersonnelSearchParam param) {
        return CommonResult.data(personnelService.personnelSearch(param));
    }
}