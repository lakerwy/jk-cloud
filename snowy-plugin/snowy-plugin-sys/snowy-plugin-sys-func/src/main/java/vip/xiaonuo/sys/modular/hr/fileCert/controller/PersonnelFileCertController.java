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
package vip.xiaonuo.sys.modular.hr.fileCert.controller;

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
import vip.xiaonuo.sys.modular.hr.fileCert.service.PersonnelFileCertService;
import vip.xiaonuo.sys.modular.hr.fileCert.param.PersonnelFileCertAddParam;
import vip.xiaonuo.sys.modular.hr.fileCert.param.PersonnelFileCertEditParam;
import vip.xiaonuo.sys.modular.hr.fileCert.param.PersonnelFileCertIdParam;
import vip.xiaonuo.sys.modular.hr.fileCert.param.PersonnelFileCertPageParam;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.sys.modular.hr.fileCert.entity.PersonnelFileCert;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-证书信息控制器
 *
 * @author mayike
 * @date  2023/07/10 16:53
 */
@Api(tags = "业务表-证书信息控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PersonnelFileCertController {

    @Resource
    private PersonnelFileCertService personnelFileCertService;

    /**
     * 获取业务表-证书信息分页
     *
     * @author mayike
     * @date  2023/07/10 16:53
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-证书信息分页")
//    @SaCheckPermission("/biz/filecert/page")
    @GetMapping("/sys/fileCert/page")
    public CommonResult<Page<PersonnelFileCert>> page(PersonnelFileCertPageParam personnelFileCertPageParam) {
        return CommonResult.data(personnelFileCertService.page(personnelFileCertPageParam));
    }

    /**
     * 添加业务表-证书信息
     *
     * @author mayike
     * @date  2023/07/10 16:53
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-证书信息")
    @CommonLog("添加业务表-证书信息")
//    @SaCheckPermission("/biz/filecert/add")
    @PostMapping("/sys/fileCert/add")
    public CommonResult<String> add(@RequestBody @Valid PersonnelFileCertAddParam personnelFileCertAddParam) {
        personnelFileCertService.add(personnelFileCertAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-证书信息
     *
     * @author mayike
     * @date  2023/07/10 16:53
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-证书信息")
    @CommonLog("编辑业务表-证书信息")
//    @SaCheckPermission("/biz/filecert/edit")
    @PostMapping("/sys/fileCert/edit")
    public CommonResult<String> edit(@RequestBody @Valid PersonnelFileCertEditParam personnelFileCertEditParam) {
        personnelFileCertService.edit(personnelFileCertEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-证书信息
     *
     * @author mayike
     * @date  2023/07/10 16:53
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-证书信息")
    @CommonLog("删除业务表-证书信息")
//    @SaCheckPermission("/biz/filecert/delete")
    @PostMapping("/sys/fileCert/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PersonnelFileCertIdParam> personnelFileCertIdParamList) {
        personnelFileCertService.delete(personnelFileCertIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-证书信息详情
     *
     * @author mayike
     * @date  2023/07/10 16:53
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-证书信息详情")
//    @SaCheckPermission("/biz/filecert/detail")
    @GetMapping("/sys/fileCert/detail")
    public CommonResult<PersonnelFileCert> detail(@Valid PersonnelFileCertIdParam personnelFileCertIdParam) {
        return CommonResult.data(personnelFileCertService.detail(personnelFileCertIdParam));
    }
}
