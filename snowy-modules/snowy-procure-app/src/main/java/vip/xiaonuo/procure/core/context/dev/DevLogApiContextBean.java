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
package vip.xiaonuo.procure.core.context.dev;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.dev.api.DevLogApi;
import vip.xiaonuo.dev.feign.DevLogFeign;

import java.util.List;

/**
 * 开发工具模块综合API上下文Bean
 *
 * @author mayike
 * @date 2024/05/07 09:54
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DevLogApiContextBean implements DevLogApi {

    private final DevLogFeign devLogFeign;

    @Override
    public void executeLoginLog(String userName) {

    }

    @Override
    public void executeLogoutLog(String userName) {

    }

    @Override
    public List<JSONObject> currentUserVisLogList() {
        return null;
    }

    @Override
    public List<JSONObject> currentUserOpLogList() {
        return null;
    }

    @Override
    public void executeFileActionLog(String userName, String actionType, String message, List<String> userFileIds) {

    }

    @Override
    public void asynExecuteFileActionLog(String userName, String actionType, String message, List<String> userFileIds) {

    }

    @Override
    public void executeSaveLog(JSONObject jsonObject) {
        devLogFeign.executeSaveLog(jsonObject);
    }
}