package vip.xiaonuo.dev.modular.dingDingSchedule.Controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.dev.modular.dingDingSchedule.param.DingDingScheduleParam;
import vip.xiaonuo.dev.modular.dingDingSchedule.service.DingDingScheduleService;
import vip.xiaonuo.dev.modular.dingDingSchedule.vo.DingDingScheduleVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "钉钉日程")
@ApiSupport(author = "SNOWY_TEAM", order = 4)
@RestController
@Validated
public class DingDingScheduleController {
    @Resource
    DingDingScheduleService dingDingScheduleService;


    @ApiOperationSupport(order = 1)
    @ApiOperation("新增钉钉日程")
    @CommonLog("新增钉钉日程")
    @PostMapping("/dev/file/addDingDingSchedule")
    public CommonResult<String> addDingDingSchedule() throws Exception{
        List<DingDingScheduleParam> dingDingScheduleParamList = new ArrayList<>();
        DingDingScheduleVo dingDingScheduleVo = new DingDingScheduleVo();
        String createUser = "";
        dingDingScheduleService.addDingDingSchedule(dingDingScheduleParamList,dingDingScheduleVo,createUser);
        return CommonResult.ok();
    }

}
