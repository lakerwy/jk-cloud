package vip.xiaonuo.biz.modular.ezviz.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.ezviz.config.EzvizProperties;
import vip.xiaonuo.biz.modular.ezviz.service.EzvizService;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.util.CommonEzvizUtil;

import javax.annotation.Resource;

@Service
public class EzvizServiceImpl implements EzvizService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private EzvizProperties ezvizProperties;
    @Override
    public String getToken() {
        Object token = commonCacheOperator.get(ezvizProperties.getAppKey());
        if(ObjectUtil.isNotEmpty(token)){
            return String.valueOf(token);
        }
        String accessToken = CommonEzvizUtil.getToken(ezvizProperties.getAppKey(),ezvizProperties.getAppSecret());
        if(ObjectUtil.isNotEmpty(accessToken)){
            commonCacheOperator.put(ezvizProperties.getAppKey(),accessToken,6*24*60*60L);
        }
        return accessToken;
    }

    @Override
    public Object deviceList(Integer pageStart, Integer pageSize) {
        String token = this.getToken();
        return CommonEzvizUtil.getDeviceList(token,pageStart,pageSize);
    }

    @Override
    public Object deviceInfo(String deviceSerial) {
        String token = this.getToken();
        return CommonEzvizUtil.getDeviceInfo(token,deviceSerial);
    }

    @Override
    public Object cameraList(Integer pageStart, Integer pageSize) {
        String token = this.getToken();
        return CommonEzvizUtil.getCameraList(token,pageStart,pageSize);
    }

    @Override
    public Object deviceCameraList(String deviceSerial) {
        String token = this.getToken();
        return CommonEzvizUtil.getDeviceCameraList(token,deviceSerial);
    }
}
