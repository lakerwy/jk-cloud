package vip.xiaonuo.dev.modular.server.controller;


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
import vip.xiaonuo.dev.modular.server.entity.SystemServer;
import vip.xiaonuo.dev.modular.server.service.InitConfigService;
import vip.xiaonuo.dev.modular.server.param.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "系统环境配置类")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class InitController {

    @Resource
    InitConfigService initConfigService;

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
        initConfigService.createDataBase(dataBaseParam);
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
        initConfigService.createTable(tableParam);
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
        initConfigService.createYaml(yamlParam);
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
        initConfigService.initData(initUserParam);
        return CommonResult.ok();
    }

    /**
     * 服务器之间传文件
     *
     * @author xuyuxiang
     * @date 2023/12/28 08:35
     **/
    @ApiOperationSupport(order = 5)
    @ApiOperation("服务器之间传文件-非EPC接口")
    @CommonLog("服务器之间传文件-非EPC接口")
    @PostMapping("/epc/create/transferFile")
    public CommonResult<String> transferFile(@RequestBody @Valid TransferFileParam transferFileParam){
        initConfigService.transferFile(transferFileParam);
        return CommonResult.ok();
    }

    /**
     * 单体服务配置文件
     *
     * @author ljy
     * @date  2024/01/02 11:31
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("单体服务配置文件")
    @PostMapping("/sys/server/singleConf")
    public CommonResult<String> singleConf(@RequestBody @Valid SingleConfParam singleConfParam){
        initConfigService.singleConf(singleConfParam);
        return CommonResult.ok();
    }

    /**
     * 执行command命令
     *
     * @author ljy
     * @date  2024/01/02 22:31
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("执行command命令")
    @PostMapping("/sys/server/commandSh")
    public CommonResult<String> commandSh(@RequestBody @Valid UnixCommandParam unixCommandParam){
        initConfigService.commandSh(unixCommandParam);
        return CommonResult.ok();
    }
}
