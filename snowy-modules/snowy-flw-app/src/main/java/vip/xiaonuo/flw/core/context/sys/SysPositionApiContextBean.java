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
package vip.xiaonuo.flw.core.context.sys;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.sys.api.SysPositionApi;
import vip.xiaonuo.sys.feign.SysPositionFeign;

import java.util.List;

/**
 * 职位API上下文Bean
 *
 * @author dongxiayu
 * @date 2022/11/23 0:38
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SysPositionApiContextBean implements SysPositionApi {

    private final SysPositionFeign sysPositionFeign;

    /**
     * 根据id获取名称
     *
     * @param positionId
     * @author xuyuxiang
     * @date 2022/8/4 10:13
     */
    @Override
    public String getNameById(String positionId) {
        return sysPositionFeign.getNameById(positionId);
    }

    /**
     * 获取职位选择器
     *
     * @param orgId
     * @param searchKey
     * @author xuyuxiang
     * @date 2022/7/22 14:47
     */
    @Override
    public List<JSONObject> positionSelector(String orgId, String searchKey) {
        String feignResp = sysPositionFeign.positionSelector(orgId, searchKey);
        JSONArray jsonArray = new JSONArray(feignResp);
        List<JSONObject> resp = jsonArray.toList(JSONObject.class);
        return resp;
    }
}