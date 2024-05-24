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
package vip.xiaonuo.dev.modular.sealMessage.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.dev.modular.sealMessage.service.SealMessageService;
import vip.xiaonuo.dev.modular.sealMessage.vo.UserSealBaseVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * 印章基础信息表控制器
 *
 * @author hxw
 * @date  2024/05/17 08:55
 */
@Api(tags = "印章基础信息表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SealMessageController {

    @Resource
    private SealMessageService sealMessageService;

    /**
     * 获取群杰所有印章信息
     *
     * @author hxw
     * @date  2024/05/17 08:55
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("获取redis群杰所有印章信息")
    @CommonLog("获取redis群杰所有印章信息")
    //@SaCheckPermission("/api/seal/queryAllSealBase")
    @GetMapping("/biz/seal/getSealBaseList")
    public CommonResult<List<UserSealBaseVo>> getSealBaseList() {
        return CommonResult.data(sealMessageService.getSealBaseList());
    }
}
