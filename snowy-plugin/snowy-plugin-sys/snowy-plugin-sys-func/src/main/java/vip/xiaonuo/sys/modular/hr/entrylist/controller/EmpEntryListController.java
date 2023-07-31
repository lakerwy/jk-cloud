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
package vip.xiaonuo.sys.modular.hr.entrylist.controller;

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
import vip.xiaonuo.sys.modular.hr.entrylist.entity.EmpEntryList;
import vip.xiaonuo.sys.modular.hr.entrylist.param.EmpEntryListAddParam;
import vip.xiaonuo.sys.modular.hr.entrylist.param.EmpEntryListEditParam;
import vip.xiaonuo.sys.modular.hr.entrylist.param.EmpEntryListIdParam;
import vip.xiaonuo.sys.modular.hr.entrylist.param.EmpEntryListPageParam;
import vip.xiaonuo.sys.modular.hr.entrylist.service.EmpEntryListService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 入职名单控制器
 *
 * @author yong.ye
 * @date  2023/07/31 09:25
 */
@Api(tags = "入职名单控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EmpEntryListController {

    @Resource
    private EmpEntryListService empEntryListService;

    /**
     * 获取入职名单分页
     *
     * @author yong.ye
     * @date  2023/07/31 09:25
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取入职名单分页")
    @GetMapping("/sys/entrylist/page")
    public CommonResult<Page<EmpEntryList>> page(EmpEntryListPageParam empEntryListPageParam) {
        return CommonResult.data(empEntryListService.page(empEntryListPageParam));
    }

    /**
     * 添加入职名单
     *
     * @author yong.ye
     * @date  2023/07/31 09:25
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加入职名单")
    @CommonLog("添加入职名单")
    @PostMapping("/sys/entrylist/add")
    public CommonResult<String> add(@RequestBody @Valid EmpEntryListAddParam empEntryListAddParam) {
        empEntryListService.add(empEntryListAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑入职名单
     *
     * @author yong.ye
     * @date  2023/07/31 09:25
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑入职名单")
    @CommonLog("编辑入职名单")
    @PostMapping("/sys/entrylist/edit")
    public CommonResult<String> edit(@RequestBody @Valid EmpEntryListEditParam empEntryListEditParam) {
        empEntryListService.edit(empEntryListEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除入职名单
     *
     * @author yong.ye
     * @date  2023/07/31 09:25
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除入职名单")
    @CommonLog("删除入职名单")
    @PostMapping("/sys/entrylist/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<EmpEntryListIdParam> empEntryListIdParamList) {
        empEntryListService.delete(empEntryListIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取入职名单详情
     *
     * @author yong.ye
     * @date  2023/07/31 09:25
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取入职名单详情")
    @GetMapping("/sys/entrylist/detail")
    public CommonResult<EmpEntryList> detail(@Valid EmpEntryListIdParam empEntryListIdParam) {
        return CommonResult.data(empEntryListService.detail(empEntryListIdParam));
    }
}
