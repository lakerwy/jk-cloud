package vip.xiaonuo.pan.modular.fold.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.dev.modular.file.enums.DevFileEngineTypeEnum;
import vip.xiaonuo.dev.modular.file.param.DevFileListParam;
import vip.xiaonuo.pan.modular.fold.param.DevFileAddParam;
import vip.xiaonuo.pan.modular.fold.param.DevFoldListParam;
import vip.xiaonuo.pan.modular.fold.service.FoldService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/8/25 14:24
 */
@Api(tags = "云盘文件夹控制器")
@ApiSupport(author = "jk_team", order = 10)
@RestController
@Validated
public class FoldController {

    @Resource
    FoldService foldService;

    /**
     * 本地文件创建文件夹
     *
     **/
    @ApiOperationSupport(order = 1)
    @ApiOperation("本地文件创建文件夹")
    @CommonLog("本地文件创建文件夹")
    @PostMapping("/pan/fold/createFold")
    public CommonResult<String> createFold(@Valid @RequestBody DevFileAddParam devFileAddParam) {
        //devFileService.mkDir(DevFileEngineTypeEnum.LOCAL.getValue(),dirPath);
        //判断文件夹是否存在
        Boolean dirExist = foldService.isDirExist(devFileAddParam);
        if(!dirExist){
            return  CommonResult.error("同名文件夹已存在");
        }
        foldService.createFold(DevFileEngineTypeEnum.LOCAL.getValue(),devFileAddParam);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation("获取文件分页列表")
    @CommonLog("获取文件分页列表")
    @PostMapping("/pan/fold/pageList")
    public CommonResult<List<DevFile>> pageList(DevFoldListParam devFoldListParam) {
        return CommonResult.data(foldService.pageList(devFoldListParam));
    }

}
