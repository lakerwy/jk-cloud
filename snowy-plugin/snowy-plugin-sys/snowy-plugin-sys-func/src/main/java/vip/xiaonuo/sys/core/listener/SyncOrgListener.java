package vip.xiaonuo.sys.core.listener;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vip.xiaonuo.sys.modular.org.entity.ExtendTree;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;

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
public class SyncOrgListener {

    @Resource
    SysOrgService sysOrgService;

    @Value("${playEdu.url}")
    private  String playEduUrl;

    @PostConstruct
    public void init() {
        syncResource();
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void syncResource(){
        log.info("sync org start ------------------");
        List<ExtendTree<String>> extendTrees = sysOrgService.convertToTree();
        String json = JSONUtil.toJsonStr(extendTrees);
        //异步执行
        HttpResponse execute = HttpUtil.createPost(playEduUrl+"/api/backend/v1/department/syncDept").body(json).execute(true);

        log.info("sync user end ------------------ sync number----");
    }
}
