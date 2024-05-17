package vip.xiaonuo.im.listenter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vip.xiaonuo.im.modular.chat.service.ImService;
import vip.xiaonuo.sys.api.SysUserApi;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/9 16:41
 */
@Component
@Slf4j
public class SyncUserListener {

    @Resource
    SysUserApi sysUserApi;

    @Resource
    ImService imService;

    @PostConstruct
    public void init() {
        syncUserToIm();
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void syncUserToIm(){
        log.info("sync user start ------------------");
        List<Map<String, String>> maps = sysUserApi.syncUserToIm();
        List<JSONObject> jsonObjects = maps.stream().map(JSONUtil::parseObj).collect(Collectors.toList());
        imService.syncUserToIm(jsonObjects);
        log.info("sync user end ------------------ sync number----{}",maps.size());
    }
}
