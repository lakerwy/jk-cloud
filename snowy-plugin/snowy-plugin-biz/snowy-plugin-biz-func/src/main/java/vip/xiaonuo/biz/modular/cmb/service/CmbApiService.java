package vip.xiaonuo.biz.modular.cmb.service;

import cn.hutool.json.JSONObject;
import vip.xiaonuo.biz.modular.cmb.param.*;

/**
 * 招商银行API
 * @author pzh
 * @date 2024/02/06 10:30
 */
public interface CmbApiService {

    /**
     * @param param 请求参数
     */
    JSONObject sendRequest(CmbApiParam param);

    /**
     * @param funcode 接口编码
     * @param param 请求参数
     */
    JSONObject sendRequest(String funcode, Object param);

    /**
     * 账务查询：1.可经办业务模式查询DCLISMOD
     */
    JSONObject dclismod(CmbDclismodParam param);

    /**
     * 账务查询：2.查询可经办的账户列表DCLISACC
     */
    JSONObject dclisacc(CmbDclisaccParam param);

    /**
     * 账务查询：3.账户详细信息查询NTQACINF
     */
    JSONObject ntqacinf(CmbNtqacinfParam param);

    /**
     * 账务查询：4.查询账户历史余额NTQABINF
     */
    JSONObject ntqabinf(CmbNtqabinfParam param);

    /**
     * 账务查询：5.查询分行号信息NTACCBBK
     */
    JSONObject ntaccbbk(CmbNtaccbbkParam param);

    /**
     * 账务查询：6.批量查询余额NTQADINF
     */
    JSONObject ntqadinf(CmbNtqadinfParam param);

    /**
     * 账务查询：7.账户交易信息查询trsQryByBreakPoint
     */
    JSONObject trsQryByBreakPoint(CmbTrsQryByBreakPointParam param);

    /**
     * 账务查询：8.PDF文件对账单获取DCTRSPDF
     */
    JSONObject dctrspdf(CmbDctrspdfParam param);

    void callback(String request);
}
