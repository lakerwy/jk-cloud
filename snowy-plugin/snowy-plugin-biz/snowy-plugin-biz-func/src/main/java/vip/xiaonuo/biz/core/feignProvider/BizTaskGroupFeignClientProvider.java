package vip.xiaonuo.biz.core.feignProvider;

import com.alibaba.fastjson.JSONArray;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.BizTaskGroupFeignClient;
import vip.xiaonuo.biz.modular.teambition.service.BizTaskInfoService;

import javax.annotation.Resource;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BizTaskGroupFeignClientProvider implements BizTaskGroupFeignClient {

    @Resource
    private BizTaskInfoService bizTaskInfoService;

    @Override
    public Boolean addTaskInfo(@RequestParam String jsonObject){
        return bizTaskInfoService.addTaskInfo(jsonObject);
    }
    @Override
    public JSONArray getTaskInfoPage(@RequestParam String proId, @RequestParam String proType){
        return bizTaskInfoService.thirdPage(proId,proType);
    }
}
