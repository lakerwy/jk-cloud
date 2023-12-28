package vip.xiaonuo.sys.modular.epcMod.Controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.sys.modular.epcMod.param.DataBaseParam;
import vip.xiaonuo.sys.modular.epcMod.param.TableParam;
import vip.xiaonuo.sys.modular.epcMod.service.EpcConfigService;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "EPC环境配置类")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EpcController {

    @Resource
    EpcConfigService epcConfigService;

    /**
     * epc建库
     *
     * @author ljy
     * @date 2023/12/28 11:01
     **/
    @ApiOperationSupport(order = 1)
    @ApiOperation("epc建库")
    @CommonLog("epc建库")
    @GetMapping("/epc/create/dataBase")
    public CommonResult<String> createDataBase(@Valid DataBaseParam dataBaseParam) throws Exception{
        epcConfigService.createDataBase(dataBaseParam);
        return CommonResult.ok();
    }

    /**
     * epc建表
     *
     * @author xuyuxiang
     * @date 2023/12/28 11:01
     **/
    @ApiOperationSupport(order = 2)
    @ApiOperation("epc建表")
    @CommonLog("epc建表")
    @GetMapping("/epc/create/table")
    public CommonResult<String> createTable(@Valid TableParam tableParam){
        epcConfigService.createTable(tableParam);
        return CommonResult.ok();
    }
}
