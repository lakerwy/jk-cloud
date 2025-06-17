package vip.xiaonuo.biz.modular.prj.por.controller;

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
import vip.xiaonuo.biz.modular.prj.por.param.*;
import vip.xiaonuo.biz.modular.prj.por.vo.PisData;
import vip.xiaonuo.biz.modular.prj.por.vo.PorSignVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.por.service.PorSignService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 签字审图费管理控制器
 *
 * @author yy
 * @date  2025/06/03 09:54
 */
@Api(tags = "项目产值台账 - 项目管理 - 签字审图费管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PorSignController {

    @Resource
    private PorSignService porSignService;

    /**
     * 获取项目产值台账 - 项目管理 - 签字审图费管理分页
     *
     * @author yy
     * @date  2025/06/03 09:54
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目产值台账 - 项目管理 - 签字审图费管理分页")
//    @SaCheckPermission("/biz/prj/por/sign/page")
    @GetMapping("/biz/prj/por/sign/page")
    public CommonResult<Page<PorSignVo>> page(PorSignPageParam porSignPageParam) {
        return CommonResult.data(porSignService.page(porSignPageParam));
    }

    /**
     * 添加项目产值台账 - 项目管理 - 签字审图费管理
     *
     * @author yy
     * @date  2025/06/03 09:54
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目产值台账 - 项目管理 - 签字审图费管理")
    @CommonLog("添加项目产值台账 - 项目管理 - 签字审图费管理")
//    @SaCheckPermission("/biz/prj/por/sign/add")
    @PostMapping("/biz/prj/por/sign/add")
    public CommonResult<String> add(@RequestBody @Valid PorSignAddParam porSignAddParam) {
        porSignService.add(porSignAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目产值台账 - 项目管理 - 签字审图费管理
     *
     * @author yy
     * @date  2025/06/03 09:54
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目产值台账 - 项目管理 - 签字审图费管理")
    @CommonLog("编辑项目产值台账 - 项目管理 - 签字审图费管理")
//    @SaCheckPermission("/biz/prj/por/sign/edit")
    @PostMapping("/biz/prj/por/sign/edit")
    public CommonResult<String> edit(@RequestBody @Valid PorSignEditParam porSignEditParam) {
        porSignService.edit(porSignEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目产值台账 - 项目管理 - 签字审图费管理
     *
     * @author yy
     * @date  2025/06/03 09:54
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目产值台账 - 项目管理 - 签字审图费管理")
    @CommonLog("删除项目产值台账 - 项目管理 - 签字审图费管理")
//    @SaCheckPermission("/biz/prj/por/sign/delete")
    @PostMapping("/biz/prj/por/sign/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PorSignIdParam> porSignIdParamList) {
        porSignService.delete(porSignIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目产值台账 - 项目管理 - 签字审图费管理详情
     *
     * @author yy
     * @date  2025/06/03 09:54
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目产值台账 - 项目管理 - 签字审图费管理详情")
//    @SaCheckPermission("/biz/prj/por/sign/detail")
    @GetMapping("/biz/prj/por/sign/detail")
    public CommonResult<PorSignVo> detail(@Valid PorSignIdParam porSignIdParam) {
        return CommonResult.data(porSignService.detail(porSignIdParam));
    }




    /**
     * 根据合同id,获取签字审图费用收取明细
     */
    @ApiOperationSupport(order = 21)
    @ApiOperation("根据合同id,获取签字审图费用收取明细")
    @GetMapping("/biz/prj/por/sign/getListByHtId")
    public CommonResult<List<PisData>> getListByHtId(@Valid ContractParam param) {
        return CommonResult.data(porSignService.getListByHtId(param));
    }
}
