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
package vip.xiaonuo.biz.modular.crm.notes.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import vip.xiaonuo.biz.modular.crm.notes.vo.NotesInfoVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.crm.notes.entity.Notes;
import vip.xiaonuo.biz.modular.crm.notes.param.NotesAddParam;
import vip.xiaonuo.biz.modular.crm.notes.param.NotesEditParam;
import vip.xiaonuo.biz.modular.crm.notes.param.NotesIdParam;
import vip.xiaonuo.biz.modular.crm.notes.param.NotesPageParam;
import vip.xiaonuo.biz.modular.crm.notes.service.NotesService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-CRM-备忘录登记控制器
 *
 * @author yong.ye
 * @date  2023/06/30 16:49
 */
@Api(tags = "业务表-CRM-备忘录登记控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class NotesController {

    @Resource
    private NotesService notesService;

    /**
     * 获取业务表-CRM-备忘录登记分页
     *
     * @author yong.ye
     * @date  2023/06/30 16:49
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-CRM-备忘录登记分页")
    @GetMapping("/biz/crm/notes/page")
    public CommonResult<Page<Notes>> page(NotesPageParam notesPageParam) {
        return CommonResult.data(notesService.page(notesPageParam));
    }

    /**
     * 添加业务表-CRM-备忘录登记
     *
     * @author yong.ye
     * @date  2023/06/30 16:49
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-CRM-备忘录登记  【api/crm/Finance/AddNotes】")
    @CommonLog("添加业务表-CRM-备忘录登记")
    @PostMapping("/biz/crm/notes/add")
    public CommonResult<String> add(@RequestBody @Valid NotesAddParam notesAddParam) {
        notesService.add(notesAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-CRM-备忘录登记
     *
     * @author yong.ye
     * @date  2023/06/30 16:49
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-CRM-备忘录登记  【api/crm/Finance/UpdateNotes】")
    @CommonLog("编辑业务表-CRM-备忘录登记")
    @PostMapping("/biz/crm/notes/edit")
    public CommonResult<String> edit(@RequestBody @Valid NotesEditParam notesEditParam) {
        notesService.edit(notesEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-CRM-备忘录登记
     *
     * @author yong.ye
     * @date  2023/06/30 16:49
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-CRM-备忘录登记  【api/crm/Finance/DeleteNotes】")
    @CommonLog("删除业务表-CRM-备忘录登记")
    @PostMapping("/biz/crm/notes/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<NotesIdParam> notesIdParamList) {
        notesService.delete(notesIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-CRM-备忘录登记详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:49
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-CRM-备忘录登记详情")
    @GetMapping("/biz/crm/notes/detail")
    public CommonResult<Notes> detail(@Valid NotesIdParam notesIdParam) {
        return CommonResult.data(notesService.detail(notesIdParam));
    }

    /**
     * 获取业务表-CRM-备忘录登记分页（携带合同、伙伴信息）
     *
     * @author yong.ye
     * @date  2023/07/03 14:04
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取业务表-CRM-备忘录登记分页（携带合同、伙伴信息） 【api/crm/Finance/GetNotesPage】")
    @GetMapping("/biz/crm/notes/listPage")
    public CommonResult<IPage<NotesInfoVo>> listPage(@Valid NotesPageParam notesPageParam) {
        return CommonResult.data(notesService.pageList(notesPageParam));
    }

    /**
     * 获取业务表-CRM-备忘录登记详情（携带合同、伙伴信息）
     *
     * @author yong.ye
     * @date  2023/06/30 16:49
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-CRM-备忘录登记详情  【api/crm/Finance/GetNotes】")
    @GetMapping("/biz/crm/notes/detailById")
    public CommonResult<NotesInfoVo> detailById(@Valid NotesIdParam notesIdParam) {
        return CommonResult.data(notesService.detailById(notesIdParam));
    }
}
