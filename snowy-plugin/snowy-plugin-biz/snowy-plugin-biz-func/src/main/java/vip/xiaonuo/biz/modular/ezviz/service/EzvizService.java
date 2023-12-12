package vip.xiaonuo.biz.modular.ezviz.service;

import cn.hutool.json.JSONObject;

public interface EzvizService {

    /**
     * 萤石token
     * @return
     */
    String getToken();

    /**
     * 萤石设备列表
     * @param pageStart
     * @param pageSize
     * @return
     */
    Object deviceList(Integer pageStart,Integer pageSize);

    /**
     * 萤石设备详情
     * @param deviceSerial
     * @return
     */
    Object deviceInfo(String deviceSerial);


    /**
     * 获取视频通道列表
     * @param pageStart
     * @param pageSize
     * @return
     */
    Object cameraList(Integer pageStart, Integer pageSize);

    /**
     * 获取某设备的通道列表
     * @param deviceSerial
     * @return
     */
    Object deviceCameraList(String deviceSerial);
}
