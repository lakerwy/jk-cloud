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
package vip.xiaonuo.sys.modular.hr.fileMoves.controller;

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
import vip.xiaonuo.sys.modular.hr.fileMoves.entity.PersonnelFileMoves;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesIdParam;
import vip.xiaonuo.sys.modular.hr.fileMoves.service.PersonnelFileMovesService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesAddParam;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesEditParam;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesPageParam;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-人员异动主表控制器
 *
 * @author mayike
 * @date  2023/07/10 16:56
 */
@Api(tags = "业务表-人员异动主表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PersonnelFileMovesController {

    @Resource
    private PersonnelFileMovesService personnelFileMovesService;

    /**
     * 获取业务表-人员异动主表分页
     *
     * @author mayike
     * @date  2023/07/10 16:56
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-人员异动主表分页")
//    @SaCheckPermission("/biz/filemoves/page")
    @GetMapping("/sys/fileMoves/page")
    public CommonResult<Page<PersonnelFileMoves>> page(PersonnelFileMovesPageParam personnelFileMovesPageParam) {
        return CommonResult.data(personnelFileMovesService.page(personnelFileMovesPageParam));
    }

    /**
     * 添加业务表-人员异动主表
     *
     * @author mayike
     * @date  2023/07/10 16:56
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-人员异动主表")
    @CommonLog("添加业务表-人员异动主表")
//    @SaCheckPermission("/biz/filemoves/add")
    @PostMapping("/sys/fileMoves/add")
    public CommonResult<String> add(@RequestBody @Valid PersonnelFileMovesAddParam personnelFileMovesAddParam) {
        personnelFileMovesService.add(personnelFileMovesAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-人员异动主表
     *
     * @author mayike
     * @date  2023/07/10 16:56
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-人员异动主表")
    @CommonLog("编辑业务表-人员异动主表")
//    @SaCheckPermission("/biz/filemoves/edit")
    @PostMapping("/sys/fileMoves/edit")
    public CommonResult<String> edit(@RequestBody @Valid PersonnelFileMovesEditParam personnelFileMovesEditParam) {
        personnelFileMovesService.edit(personnelFileMovesEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-人员异动主表
     *
     * @author mayike
     * @date  2023/07/10 16:56
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-人员异动主表")
    @CommonLog("删除业务表-人员异动主表")
//    @SaCheckPermission("/biz/filemoves/delete")
    @PostMapping("/sys/fileMoves/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PersonnelFileMovesIdParam> personnelFileMovesIdParamList) {
        personnelFileMovesService.delete(personnelFileMovesIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-人员异动主表详情
     *
     * @author mayike
     * @date  2023/07/10 16:56
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-人员异动主表详情")
//    @SaCheckPermission("/biz/filemoves/detail")
    @GetMapping("/sys/fileMoves/detail")
    public CommonResult<PersonnelFileMoves> detail(@Valid PersonnelFileMovesIdParam personnelFileMovesIdParam) {
        return CommonResult.data(personnelFileMovesService.detail(personnelFileMovesIdParam));
    }
}
