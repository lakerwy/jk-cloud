package vip.xiaonuo.dev.modular.epcMod.Controller;


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
import vip.xiaonuo.dev.modular.epcMod.param.*;
import vip.xiaonuo.dev.modular.epcMod.service.EpcConfigService;

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
    @PostMapping("/epc/create/dataBase")
    public CommonResult<String> createDataBase(@RequestBody @Valid DataBaseParam dataBaseParam) throws Exception{
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
    @PostMapping("/epc/create/table")
    public CommonResult<String> createTable(@RequestBody @Valid TableParam tableParam){
        epcConfigService.createTable(tableParam);
        return CommonResult.ok();
    }

    /**
     * yaml配置文件
     *
     * @author xuyuxiang
     * @date 2023/12/28 11:01
     **/
    @ApiOperationSupport(order = 3)
    @ApiOperation("yaml配置文件")
    @CommonLog("yaml配置文件")
    @PostMapping("/epc/create/yaml")
    public CommonResult<String> createYaml(@RequestBody @Valid YamlParam yamlParam){
        epcConfigService.createYaml(yamlParam);
        return CommonResult.ok();
    }


    /**
     * epc-根据导入的用户表，初始化 部门、角色、用户
     *
     * @author yong.ye
     * @date  2023/11/21 11:14
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("epc-根据导入的用户表，初始化 部门、角色、用户")
    @CommonLog("epc-根据导入的用户表，初始化 部门、角色、用户")
    @PostMapping("/epc/create/initData")
    public CommonResult<String> initData(@RequestBody @Valid InitUserParam initUserParam) {
        epcConfigService.initData(initUserParam);
        return CommonResult.ok();
    }

    /**
     * 服务器之间传文件
     *
     * @author xuyuxiang
     * @date 2023/12/28 08:35
     **/
    @ApiOperationSupport(order = 5)
    @ApiOperation("服务器之间传文件")
    @CommonLog("服务器之间传文件")
    @PostMapping("/epc/create/transferFile")
    public CommonResult<String> transferFile(@RequestBody @Valid TransferFileParam transferFileParam){
        epcConfigService.transferFile(transferFileParam);
        return CommonResult.ok();
    }
}
