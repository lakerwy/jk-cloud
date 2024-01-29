/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.sys.modular.hr.certManage.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.sys.modular.hr.certManage.entity.CertManage;
import vip.xiaonuo.sys.modular.hr.certManage.param.*;
import vip.xiaonuo.sys.modular.hr.certManage.service.CertManageService;
import vip.xiaonuo.sys.modular.hr.certManage.vo.PersonVo;
import vip.xiaonuo.sys.modular.hr.certManage.vo.SearchDetailVo;
import vip.xiaonuo.sys.modular.hr.certManage.vo.SearchVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 人员资质管理控制器
 *
 * @author yong.ye
 * @date  2024/01/23 17:23
 */
@Api(tags = "人员资质管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class CertManageController {

    @Resource
    private CertManageService certManageService;

    /**
     * 获取人员资质管理分页
     *
     * @author yong.ye
     * @date  2024/01/23 17:23
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取人员资质管理分页")
//    @SaCheckPermission("/sys/hr/cert/manage/page")
    @GetMapping("/sys/hr/cert/manage/page")
    public CommonResult<Page<CertManage>> page(CertManagePageParam certManagePageParam) {
        return CommonResult.data(certManageService.page(certManagePageParam));
    }

    /**
     * 添加人员资质管理
     *
     * @author yong.ye
     * @date  2024/01/23 17:23
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加人员资质管理")
    @CommonLog("添加人员资质管理")
//    @SaCheckPermission("/sys/hr/cert/manage/add")
    @PostMapping("/sys/hr/cert/manage/add")
    public CommonResult<String> add(@RequestBody @Valid CertManageAddParam certManageAddParam) {
        certManageService.add(certManageAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑人员资质管理
     *
     * @author yong.ye
     * @date  2024/01/23 17:23
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑人员资质管理")
    @CommonLog("编辑人员资质管理")
//    @SaCheckPermission("/sys/hr/cert/manage/edit")
    @PostMapping("/sys/hr/cert/manage/edit")
    public CommonResult<String> edit(@RequestBody @Valid CertManageEditParam certManageEditParam) {
        certManageService.edit(certManageEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除人员资质管理
     *
     * @author yong.ye
     * @date  2024/01/23 17:23
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除人员资质管理")
    @CommonLog("删除人员资质管理")
//    @SaCheckPermission("/sys/hr/cert/manage/delete")
    @PostMapping("/sys/hr/cert/manage/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<CertManageIdParam> certManageIdParamList) {
        certManageService.delete(certManageIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取人员资质管理详情
     *
     * @author yong.ye
     * @date  2024/01/23 17:23
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取人员资质管理详情")
//    @SaCheckPermission("/sys/hr/cert/manage/detail")
    @GetMapping("/sys/hr/cert/manage/detail")
    public CommonResult<CertManage> detail(@Valid CertManageIdParam certManageIdParam) {
        return CommonResult.data(certManageService.detail(certManageIdParam));
    }

    /**
     * 获取 【总部花名册 & 分公司花名册】人员信息，支持姓名搜索
     *
     * @author yong.ye
     * @date  2024/01/23 17:23
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取 【总部花名册 & 分公司花名册】人员信息，支持姓名搜索")
    @GetMapping("/sys/hr/person/list")
    public CommonResult<List<PersonVo>> list(@Valid PersonParam personParam) {
        return CommonResult.data(certManageService.list(personParam));
    }


    /**
     * 综合搜索 - 一个查询，分三个列表（注册|培训|职称）展示（不分页）
     *
     * @author yong.ye
     * @date  2024/01/23 17:23
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("综合搜索 - 一个查询，分三个列表（注册|培训|职称）展示（不分页）")
//    @SaCheckPermission("/sys/hr/cert/manage/searchPage")
    @GetMapping("/sys/hr/cert/manage/searchPage")
    public CommonResult<SearchVo> searchPage(CertSearchPageParam certSearchPageParam) {
        return CommonResult.data(certManageService.searchPage(certSearchPageParam));
    }


    /**
     * 综合搜索 - 详情页 - 分四块（注册|培训|职称|花名册）
     *
     * @author yong.ye
     * @date  2024/01/23 17:23
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("综合搜索 - 详情页 - 分四块（注册|培训|职称|花名册）")
//    @SaCheckPermission("/sys/hr/cert/manage/searchPage")
    @GetMapping("/sys/hr/cert/manage/searchDetail")
    public CommonResult<SearchDetailVo> searchDetail(String id) {
        return CommonResult.data(certManageService.searchDetail(id));
    }


}
