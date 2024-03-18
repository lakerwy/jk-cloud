package vip.xiaonuo.biz;

import com.alibaba.fastjson.JSONArray;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vip.xiaonuo.common.consts.FeignConstant;

@FeignClient(name= FeignConstant.BIZ_APP, contextId = "BizTaskGroupFeignClient")
public interface BizTaskGroupFeignClient {

    @PostMapping("/feign/biz/taskInfo/addTaskInfo")
    Boolean addTaskInfo(@RequestParam String jsonObject);

    @PostMapping("/feign/biz/taskInfo/taskInfoPage")
    JSONArray getTaskInfoPage(@RequestParam String proId,@RequestParam String proType);
}
