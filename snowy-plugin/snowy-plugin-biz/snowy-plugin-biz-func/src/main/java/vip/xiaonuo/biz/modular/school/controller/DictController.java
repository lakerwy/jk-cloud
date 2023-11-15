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
package vip.xiaonuo.biz.modular.school.controller;

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
import vip.xiaonuo.biz.modular.school.entity.Dict;
import vip.xiaonuo.biz.modular.school.param.DictAddParam;
import vip.xiaonuo.biz.modular.school.param.DictEditParam;
import vip.xiaonuo.biz.modular.school.param.DictIdParam;
import vip.xiaonuo.biz.modular.school.param.DictPageParam;
import vip.xiaonuo.biz.modular.school.service.DictService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * 系统字典-自定义控制器
 *
 * @author yong.ye
 * @date  2023/11/15 08:51
 */
@Api(tags = "系统字典-自定义控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class DictController {

    @Resource
    private DictService dictService;

    /**
     * 获取系统字典-自定义分页
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取系统字典-自定义分页")
//    @SaCheckPermission("/biz/sys/dict/page")
    @GetMapping("/biz/sys/dict/page")
    public CommonResult<Page<Dict>> page(DictPageParam dictPageParam) {
        return CommonResult.data(dictService.page(dictPageParam));
    }

    /**
     * 添加系统字典-自定义
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加系统字典-自定义")
    @CommonLog("添加系统字典-自定义")
//    @SaCheckPermission("/biz/sys/dict/insertDict")
    @PostMapping("/biz/sys/dict/insertDict")
    public CommonResult<String> add(@RequestBody @Valid DictAddParam dictAddParam) {
        dictService.add(dictAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑系统字典-自定义
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑系统字典-自定义")
    @CommonLog("编辑系统字典-自定义")
//    @SaCheckPermission("/biz/sys/dict/updateDict")
    @PostMapping("/biz/sys/dict/updateDict")
    public CommonResult<String> edit(@RequestBody @Valid DictEditParam dictEditParam) {
        dictService.edit(dictEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除系统字典-自定义
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除系统字典-自定义")
    @CommonLog("删除系统字典-自定义")
//    @SaCheckPermission("/biz/sys/dict/deleteDict")
    @PostMapping("/biz/sys/dict/deleteDict")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<DictIdParam> dictIdParamList) {
        dictService.delete(dictIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取系统字典-自定义详情
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取系统字典-自定义详情")
//    @SaCheckPermission("/biz/sys/dict/detail")
    @GetMapping("/biz/sys/dict/detail")
    public CommonResult<Dict> detail(@Valid DictIdParam dictIdParam) {
        return CommonResult.data(dictService.detail(dictIdParam));
    }

    /**
     * 根据字典类型Type，获取字典集合
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("根据字典类型Type，获取字典集合")
//    @SaCheckPermission("/biz/sys/dict/getDictByType")
    @GetMapping("/biz/sys/dict/getDictByType")
    public CommonResult<List<Dict>> getDictByType(String type) {
        return CommonResult.data(dictService.getDictByType(type));
    }

    /**
     * 获取所有字典集合
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取所有字典集合")
//    @SaCheckPermission("/biz/sys/dict/getAllDicts")
    @GetMapping("/biz/sys/dict/getAllDicts")
    public CommonResult<List<Dict>> getAllDicts() {
        return CommonResult.data(dictService.list());
    }

    /**
     * 获取所有字典key-value集合
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("获取所有字典key-value集合")
//    @SaCheckPermission("/biz/sys/dict/getAllDictKeyVale")
    @GetMapping("/biz/sys/dict/getAllDictKeyVale")
    public CommonResult<Map<String,String>> getAllDictKeyVale() {
        return CommonResult.data(dictService.getAllDictKeyVale());
    }

    /**
     * 获取所有字典类型
     *
     * @author yong.ye
     * @date  2023/11/15 08:51
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("获取所有字典类型")
//    @SaCheckPermission("/biz/sys/dict/getAllDictType")
    @GetMapping("/biz/sys/dict/getAllDictType")
    public CommonResult<List<String>> getAllDictType() {
        return CommonResult.data(dictService.getAllDictType());
    }
}
