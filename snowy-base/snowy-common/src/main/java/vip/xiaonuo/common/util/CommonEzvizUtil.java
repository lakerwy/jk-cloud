package vip.xiaonuo.common.util;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fhs.common.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public class CommonEzvizUtil {

    /**
     * 调用 EZVIZ 萤石开放平台API，获取token
     * @param appKey
     * @param appSecret
     * @return
     */
    public static String getToken(String appKey,String appSecret){
        try{
            //请求url
            String url = "https://open.ys7.com/api/lapp/token/get";
            Map<String,Object> mapParam = new HashMap<>();
            mapParam.put("appKey",appKey);
            mapParam.put("appSecret",appSecret);
            String data = HttpUtil.post(url,mapParam);
            JSONObject objResult = JSONUtil.parseObj(data);
            return objResult.getJSONObject("data").getStr("accessToken");
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 调用 EZVIZ 萤石开放平台API，获取设备列表
     * @param token
     * @param pageStart
     * @param pageSize
     * @return
     */
    public static JSONObject getDeviceList(String token,int pageStart, int pageSize){
        try{
            //请求url
            String url = "https://open.ys7.com/api/lapp/device/list";
            Map<String,Object> mapParam = new HashMap<>();
            mapParam.put("accessToken",token);
            mapParam.put("pageStart",pageStart);
            mapParam.put("pageSize",pageSize);
            String data = HttpUtil.post(url,mapParam);
            JSONObject objResult = JSONUtil.parseObj(data);
            return objResult;
        }catch (Exception e){
            return null;
        }
    }

}
