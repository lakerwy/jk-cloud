package vip.xiaonuo.flw.core.context.sys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.sys.feign.DingTalkFeign;
import vip.xiaonuo.sys.api.DingTalkApi;

import javax.annotation.Resource;

@Slf4j
@RequiredArgsConstructor
@Component
public class DingTalkApiContextBean implements DingTalkApi {

    @Resource
    private final DingTalkFeign dingTalkFeign;

    @Override
    public void syncLastMonthAttend() {
        dingTalkFeign.syncLastMonthAttend();
    }
}
