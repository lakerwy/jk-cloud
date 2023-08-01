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
package vip.xiaonuo.sys.modular.hr.personnel.controller;

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
import vip.xiaonuo.sys.modular.hr.personnel.param.*;
import vip.xiaonuo.sys.modular.hr.personnel.vo.PersonnelListVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.sys.modular.hr.personnel.entity.Personnel;
import vip.xiaonuo.sys.modular.hr.personnel.service.PersonnelService;
import vip.xiaonuo.sys.modular.hr.personnel.vo.PersonnelStatsVo;
import vip.xiaonuo.sys.modular.hr.personnel.vo.PersonnelVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * 业务表-人事档案控制器
 *
 * @author mayike
 * @date  2023/07/10 17:28
 */
@Api(tags = "业务表-人事档案控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PersonnelController {

    @Resource
    private PersonnelService personnelService;

    /**
     * 获取业务表-人事档案分页
     *
     * @author mayike
     * @date  2023/07/10 17:28
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-人事档案分页")
//    @SaCheckPermission("/biz/personnel/page")
    @GetMapping("/sys/personnel/page")
    public CommonResult<Page<Personnel>> page(PersonnelPageParam personnelPageParam) {
        return CommonResult.data(personnelService.page(personnelPageParam));
    }

    /**
     * 添加业务表-人事档案
     *
     * @author mayike
     * @date  2023/07/10 17:28
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-人事档案")
    @CommonLog("添加业务表-人事档案")
//    @SaCheckPermission("/biz/personnel/add")
    @PostMapping("/sys/personnel/add")
    public CommonResult<String> add(@RequestBody @Valid PersonnelAddParam personnelAddParam) {
        personnelService.add(personnelAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-人事档案
     *
     * @author mayike
     * @date  2023/07/10 17:28
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-人事档案")
    @CommonLog("编辑业务表-人事档案")
//    @SaCheckPermission("/biz/personnel/edit")
    @PostMapping("/sys/personnel/edit")
    public CommonResult<String> edit(@RequestBody @Valid PersonnelEditParam personnelEditParam) {
        personnelService.edit(personnelEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-人事档案
     *
     * @author mayike
     * @date  2023/07/10 17:28
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-人事档案")
    @CommonLog("删除业务表-人事档案")
//    @SaCheckPermission("/biz/personnel/delete")
    @PostMapping("/sys/personnel/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PersonnelIdParam> personnelIdParamList) {
        personnelService.delete(personnelIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-人事档案详情
     *
     * @author mayike
     * @date  2023/07/10 17:28
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-人事档案详情")
//    @SaCheckPermission("/biz/personnel/detail")
    @GetMapping("/sys/personnel/detail")
    public CommonResult<PersonnelVo> detail(@Valid PersonnelIdParam personnelIdParam) {
        return CommonResult.data(personnelService.detail(personnelIdParam));
    }

    /**
     * 获取业务表-人事档案分页
     *
     * @author mayike
     * @date  2023/07/10 17:28
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取业务表-新的人事档案分页")
//    @SaCheckPermission("/biz/personnel/page")
    @GetMapping("/sys/personnel/pageList")
    public CommonResult<Page<PersonnelListVo>> pageList(PersonnelPageParam personnelPageParam) {
        return CommonResult.data(personnelService.pageList(personnelPageParam));
    }


    @ApiOperationSupport(order = 7)
    @ApiOperation("获取业务表-人事月报统计")
//    @SaCheckPermission("/biz/personnel/page")
    @GetMapping("/sys/personnel/getStatsResult")
    public CommonResult<List<PersonnelStatsVo>> getStatsResult(PersonnelStatsParam param) {
        return CommonResult.data(personnelService.getStatsResult(param));
    }

    /**
     * 获取业务表-人事档案详情
     *
     * @author mayike
     * @date  2023/07/10 17:28
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-人事档案详情")
//    @SaCheckPermission("/biz/personnel/detail")
    @GetMapping("/sys/personnel/info")
    public CommonResult<PersonnelVo> info(@Valid PersonnelIdParam personnelIdParam) {
        return CommonResult.data(personnelService.info(personnelIdParam));
    }
}
