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
package vip.xiaonuo.dev.modular.dict.provider;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;
import vip.xiaonuo.dev.api.DevDictApi;
import vip.xiaonuo.dev.api.DevMessageApi;
import vip.xiaonuo.dev.modular.dict.param.DevDictListParam;
import vip.xiaonuo.dev.modular.dict.service.DevDictService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典API接口实现类
 *
 * @author xuyuxiang
 * @date 2022/9/2 16:05
 */
@Service
public class DevDictApiProvider implements DevDictApi {

    @Resource
    private DevDictService devDictService;

    @Resource
    private DevMessageApi devMessageApi;

    @Override
    public List<JSONObject> list(String parentId, String category) {
        DevDictListParam devDictListParam = new DevDictListParam();
        devDictListParam.setParentId(parentId);
        devDictListParam.setCategory(category);
        List<JSONObject> list = devDictService.list(devDictListParam).stream().map(JSONUtil::parseObj).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(list)) {
            return list.stream().map(JSONUtil::parseObj).collect(Collectors.toList());
        }
        return CollectionUtil.newArrayList();
    }

    @Override
    public void sendMessageWithContent(JSONObject jsonObject) {
        try {
            List<String> receiverIdList = jsonObject.getBeanList("receiverIdList",java.lang.String.class);
            String category = jsonObject.getStr("category");
            String subject = jsonObject.getStr("subject");
            String content = jsonObject.getStr("content");
            Map<String, String> taskMap = jsonObject.get("taskMap", Map.class);
            devMessageApi.sendMessageWithContent(receiverIdList,category,subject,content,taskMap);
        }catch (Exception e){
        }
    }
}
