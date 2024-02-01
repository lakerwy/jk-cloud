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
package vip.xiaonuo.sys.modular.person.controller;

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
import vip.xiaonuo.sys.modular.person.entity.UserSurveyCollect;
import vip.xiaonuo.sys.modular.person.param.*;
import vip.xiaonuo.sys.modular.person.service.UserSurveyCollectService;
import vip.xiaonuo.sys.modular.person.service.UserSurveyRecordService;
import vip.xiaonuo.sys.modular.person.vo.UserSurveyCollectVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户问卷收集-问题|意见控制器
 *
 * @author yong.ye
 * @date  2024/01/25 10:44
 */
@Api(tags = "用户问卷收集-问题|意见控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class UserSurveyCollectController {

    @Resource
    private UserSurveyCollectService userSurveyCollectService;

    @Resource
    private UserSurveyRecordService userSurveyRecordService;

    /**
     * 获取用户问卷收集-问题|意见分页
     *
     * @author yong.ye
     * @date  2024/01/25 10:44
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取用户问卷收集-问题|意见分页")
    @SaCheckPermission("/sys/person/survey/page")
    @GetMapping("/sys/person/survey/page")
    public CommonResult<Page<UserSurveyCollect>> page(UserSurveyCollectPageParam userSurveyCollectPageParam) {
        return CommonResult.data(userSurveyCollectService.page(userSurveyCollectPageParam));
    }

    /**
     * 添加用户问卷收集-问题|意见
     *
     * @author yong.ye
     * @date  2024/01/25 10:44
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加用户问卷收集-问题|意见")
    @CommonLog("添加用户问卷收集-问题|意见")
//    @SaCheckPermission("/sys/person/survey/add")
    @PostMapping("/sys/person/survey/add")
    public CommonResult<String> add(@RequestBody @Valid UserSurveyCollectAddParam userSurveyCollectAddParam) {
        userSurveyCollectService.add(userSurveyCollectAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑用户问卷收集-问题|意见
     *
     * @author yong.ye
     * @date  2024/01/25 10:44
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑用户问卷收集-问题|意见")
    @CommonLog("编辑用户问卷收集-问题|意见")
//    @SaCheckPermission("/sys/person/survey/edit")
    @PostMapping("/sys/person/survey/edit")
    public CommonResult<String> edit(@RequestBody @Valid UserSurveyCollectEditParam userSurveyCollectEditParam) {
        userSurveyCollectService.edit(userSurveyCollectEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除用户问卷收集-问题|意见
     *
     * @author yong.ye
     * @date  2024/01/25 10:44
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除用户问卷收集-问题|意见")
    @CommonLog("删除用户问卷收集-问题|意见")
//    @SaCheckPermission("/sys/person/survey/delete")
    @PostMapping("/sys/person/survey/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<UserSurveyCollectIdParam> userSurveyCollectIdParamList) {
        userSurveyCollectService.delete(userSurveyCollectIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取用户问卷收集-问题|意见详情
     *
     * @author yong.ye
     * @date  2024/01/25 10:44
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取用户问卷收集-问题|意见详情")
//    @SaCheckPermission("/sys/person/survey/detail")
    @GetMapping("/sys/person/survey/detail")
    public CommonResult<UserSurveyCollectVo> detail(@Valid UserSurveyCollectIdParam userSurveyCollectIdParam) {
        return CommonResult.data(userSurveyCollectService.detail(userSurveyCollectIdParam));
    }

    /**
     * 操作：回复|关闭|删除
     *
     * @author yong.ye
     * @date  2024/01/25 10:44
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("操作：回复|关闭|删除")
    @CommonLog("操作：回复|关闭|删除")
    @PostMapping("/sys/person/survey/doAction")
    public CommonResult<String> doAction(@RequestBody @Valid ActionParam actionParam) {
        userSurveyCollectService.doAction(actionParam);
        return CommonResult.ok();
    }

    /**
     * 根据id,删除用户问卷收集-问题|意见 - 沟通记录
     *
     * @author yong.ye
     * @date  2024/01/25 10:44
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("根据id,删除用户问卷收集-问题|意见 - 沟通记录")
    @CommonLog("根据id,删除用户问卷收集-问题|意见 - 沟通记录")
//    @SaCheckPermission("/sys/person/survey/record/delete")
    @PostMapping("/sys/person/survey/record/delete")
    public CommonResult<String> delRecordById(String id) {
        userSurveyRecordService.removeById(id);
        return CommonResult.ok();
    }

    /**
     * 编辑用户问卷收集-问题|意见 - 沟通记录
     *
     * @author yong.ye
     * @date  2024/01/25 10:44
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("编辑用户问卷收集-问题|意见 - 沟通记录")
    @CommonLog("编辑用户问卷收集-问题|意见 - 沟通记录")
//    @SaCheckPermission("/sys/person/survey/edit")
    @PostMapping("/sys/person/survey/record/edit")
    public CommonResult<String> recordEdit(@RequestBody @Valid UserSurveyRecordEditParam userSurveyRecordEditParam) {
        userSurveyRecordService.edit(userSurveyRecordEditParam);
        return CommonResult.ok();
    }
}
