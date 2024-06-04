package vip.xiaonuo.dev.modular.es.controller;

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
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.dev.modular.es.entity.FileData;
import vip.xiaonuo.dev.modular.es.param.EsSearchParam;
import vip.xiaonuo.dev.modular.es.service.FileDataService;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.dev.modular.file.param.DevFileListParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/3 9:55
 */
@Api(tags = "es服务控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 4)
@RestController
@Validated
public class EsController {

    @Resource
    FileDataService fileDataService;

    @ApiOperationSupport(order = 1)
    @ApiOperation("文件高级搜索")
    @PostMapping("/es/search")
    public CommonResult<Page<FileData>> list(@RequestBody EsSearchParam esSearchParam) {
        return CommonResult.data(fileDataService.searchFullKey(esSearchParam));
    }
}
