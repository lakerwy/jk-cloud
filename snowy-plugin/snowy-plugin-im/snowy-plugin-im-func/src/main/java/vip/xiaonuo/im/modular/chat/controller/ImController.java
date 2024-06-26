package vip.xiaonuo.im.modular.chat.controller;

import cn.hutool.json.JSONObject;
import cn.wildfirechat.pojos.OutputGetUserList;
import cn.wildfirechat.pojos.PojoGroupInfos;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.im.modular.chat.service.ImService;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/20 16:25
 *
 *Im 即使通信控制期
 */
@Api(tags = "IM-即时通信控制期")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ImController {

    @Resource
    ImService imService;
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取所有IM用户")
    @CommonLog("获取所有IM用户")
    @GetMapping("/im/getAllUsers")
    public CommonResult<OutputGetUserList> getAllUsers(@RequestParam(value = "current") Integer current ,@RequestParam(value = "size") Integer size) {
        OutputGetUserList allUsers = imService.getAllUsers(current, size);
        return CommonResult.data(allUsers);
    }

    @ApiOperationSupport(order =2)
    @ApiOperation("获取用户所有群组")
    @CommonLog("获取用户所有群组")
    @GetMapping("/im/getPojoGroupInfos")
    public CommonResult<PojoGroupInfos> getPojoGroupInfos(@RequestParam(value = "userId") String userId) {
        PojoGroupInfos pojoGroupInfos= imService.getPojoGroupInfosByUserId(userId);
        return CommonResult.data(pojoGroupInfos);
    }

    @ApiOperationSupport(order =2)
    @ApiOperation("更新Im的用户信息")
    @CommonLog("更新Im的用户信息")
    @PostMapping("/im/updateImUser")
    public CommonResult<String> updateImUser(@RequestBody JSONObject userInfo) {
        imService.updateImUser(userInfo);
        return CommonResult.ok();
    }
}
