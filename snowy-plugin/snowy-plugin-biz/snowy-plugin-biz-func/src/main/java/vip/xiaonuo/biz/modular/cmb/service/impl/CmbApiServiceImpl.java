package vip.xiaonuo.biz.modular.cmb.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.cmb.config.CmbProperties;
import vip.xiaonuo.biz.modular.cmb.entity.CmbCallback;
import vip.xiaonuo.biz.modular.cmb.param.*;
import vip.xiaonuo.biz.modular.cmb.service.CmbApiService;
import vip.xiaonuo.biz.modular.cmb.service.CmbCallbackService;
import vip.xiaonuo.biz.modular.cmb.util.CmbApiParamUtil;
import vip.xiaonuo.biz.modular.cmb.util.DcHelper;
import vip.xiaonuo.biz.modular.cmb.util.VerifySigUtil;
import vip.xiaonuo.common.exception.CommonException;

import javax.annotation.Resource;

/**
 * 招商银行API
 * @author pzh
 * @date 2024/02/06 10:30
 */
@Slf4j
@Service
@AllArgsConstructor
public class CmbApiServiceImpl implements CmbApiService {

    @Resource
    CmbProperties cmbProperties;

    @Resource
    CmbCallbackService cmbCallbackService;

    @Override
    public JSONObject sendRequest(CmbApiParam param){
        if(!cmbProperties.getEnabled()){
            throw new CommonException("招商银行API未启用！请检查配置");
        }
        String funcode = param.getFuncode();
        try {
            // 构建请求参数
            JSONObject reqParam = CmbApiParamUtil.getRequestParam(funcode, cmbProperties.getUid(), param);
            String data = reqParam.toString();
            // 发送请求
            DcHelper dchelper = new DcHelper(cmbProperties.getUrl(), cmbProperties.getUid(),
                    cmbProperties.getPrivateKey(), cmbProperties.getPublicKey(), cmbProperties.getSymKey());
            String response = dchelper.sendRequest(data, funcode);
            return JSONUtil.parseObj(response);
        }catch (Exception e){
            throw new CommonException("调用招商银行【{}】接口失败：{}", funcode, e.getMessage());
        }
    }

    @Override
    public void callback(String receiveEncryptMsg) {
        try {
            log.info("招商银行回调-原始报文：{}", receiveEncryptMsg);
            // 将接收到的[加密报文]转换成json对象，并取出[企业编号]和[加密数据]
            com.alibaba.fastjson.JSONObject receiveMsgJsonObject = com.alibaba.fastjson.JSONObject.parseObject(receiveEncryptMsg);
            String corpNbr = receiveMsgJsonObject.getString("corpNbr");
            String enptData = receiveMsgJsonObject.getString("enptData");
            // 根据[企业编号]确定配置的[解密对称密钥]，使用[企业编号]为向量值，对[加密数据]进行解密，获得[带签名的发送报文]
            String sendData = VerifySigUtil.decryptBase64(enptData, cmbProperties.getCorpKey(), corpNbr);
            log.info("招商银行回调-解密后报文：{}", sendData);
            // 将[带签名的发送报文]转换成json对象，并取出[签名值]
            com.alibaba.fastjson.JSONObject sendDataJsonObject = com.alibaba.fastjson.JSONObject.parseObject(sendData);
            String sigDat = sendDataJsonObject.getString("sigdat");
            // 将[带签名的发送报文]中的[签名值]替换为固定常量，保存为[不带签名的发送报文]
            String sendDataNoSign = sendData.replace(sigDat, "__signature_sigdat__");
            // 使用[验签公钥]、[不带签名的发送报文]、签名值，进行验签
            VerifySigUtil.cmbSM2Verify(cmbProperties.getCmbSignaturePublicKey(), sendDataNoSign, sigDat);
            // 保存回调记录
            CmbCallback cmbCallback = new CmbCallback();
            cmbCallback.setOrinResponse(receiveEncryptMsg);
            cmbCallback.setDecryptResponse(sendData);
            cmbCallback.setType(sendDataJsonObject.getString("nottyp"));
            cmbCallbackService.save(cmbCallback);
        }catch (Exception e){
            throw new CommonException("处理回调数据失败：{}", e.getMessage());
        }
    }

}
