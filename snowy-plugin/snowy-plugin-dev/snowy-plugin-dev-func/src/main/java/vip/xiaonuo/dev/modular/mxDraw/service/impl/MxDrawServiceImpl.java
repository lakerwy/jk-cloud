package vip.xiaonuo.dev.modular.mxDraw.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;
import vip.xiaonuo.dev.modular.mxDraw.param.ConvertData;
import vip.xiaonuo.dev.modular.mxDraw.service.MxDrawService;

import java.util.HashMap;
import java.util.Map;

/**
 * 梦想云图服务
 */
@Service
public class MxDrawServiceImpl implements MxDrawService {

    @Override
    public String convert(ConvertData convertData) {
        String url = "http://127.0.0.1:1337/mxcad/convert";
        Map<String,Object> mapParam = new HashMap<>();
        mapParam.put("srcpath",convertData.getSrcpath());
        String data = HttpUtil.post(url,mapParam);
        JSONObject objResult = JSONUtil.parseObj(data);
        return objResult.getJSONObject("data").getStr("accessToken");
    }
}
