package vip.xiaonuo.common.util;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;

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
    public static Object getDeviceList(String token,int pageStart, int pageSize){
        try{
            //请求url
            String url = "https://open.ys7.com/api/lapp/device/list";
            Map<String,Object> mapParam = new HashMap<>();
            mapParam.put("accessToken",token);
            mapParam.put("pageStart",pageStart);
            mapParam.put("pageSize",pageSize);
            String result = HttpUtil.post(url,mapParam);
            return JSON.parseObject(result);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 调用 EZVIZ 萤石开放平台API，获取设备详情
     * @param token
     * @param deviceSerial
     * @return
     */
    public static Object getDeviceInfo(String token,String deviceSerial){
        try{
            //请求url
            String url = "https://open.ys7.com/api/lapp/device/info";
            Map<String,Object> mapParam = new HashMap<>();
            mapParam.put("accessToken",token);
            mapParam.put("deviceSerial",deviceSerial);
            String result = HttpUtil.post(url,mapParam);
            return JSON.parseObject(result);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 调用 EZVIZ 萤石开放平台API，获取视频通道列表
     * @param token
     * @param pageStart
     * @param pageSize
     * @return
     */
    public static Object getCameraList(String token, int pageStart, int pageSize){
        try{
            //请求url
            String url = "https://open.ys7.com/api/lapp/camera/list";
            Map<String,Object> mapParam = new HashMap<>();
            mapParam.put("accessToken",token);
            mapParam.put("pageStart",pageStart);
            mapParam.put("pageSize",pageSize);
            String result = HttpUtil.post(url,mapParam);
            return JSON.parseObject(result);
        }catch (Exception e){
            return null;
        }
    }


    /**
     * 调用 EZVIZ 萤石开放平台API，获取指定设备的通道列表
     * @param token
     * @param deviceSerial
     * @return
     */
    public static Object getDeviceCameraList(String token,String deviceSerial){
        try{
            //请求url
            String url = "https://open.ys7.com/api/lapp/device/camera/list";
            Map<String,Object> mapParam = new HashMap<>();
            mapParam.put("accessToken",token);
            mapParam.put("deviceSerial",deviceSerial);
            String result = HttpUtil.post(url,mapParam);
            return JSON.parseObject(result);
        }catch (Exception e){
            return null;
        }
    }

}
