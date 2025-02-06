package vip.xiaonuo.biz.modular.cmb.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.cmb.config.CmbProperties;
import vip.xiaonuo.biz.modular.cmb.param.*;
import vip.xiaonuo.biz.modular.cmb.service.CmbApiService;
import vip.xiaonuo.biz.modular.cmb.util.CmbApiParamUtil;
import vip.xiaonuo.biz.modular.cmb.util.DcHelper;
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

    @Override
    public JSONObject dclismod(CmbDclismodParam param){
        // 业务接口名
        String funcode = "DCLISMOD";
        try {
            // 构建基本请求参数
            JSONObject reqParam = CmbApiParamUtil.getBaseParam(funcode, cmbProperties.getUid());
            // 构建body
            JSONObject body = new JSONObject();
            body.set("buscod",param.getBuscod());
            reqParam.getJSONObject("request").set("body", body);
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
    public JSONObject dclisacc(CmbDclisaccParam param) {
        // 业务接口名
        String funcode = "DCLISACC";
        try {
            // 构建基本请求参数
            JSONObject reqParam = CmbApiParamUtil.getBaseParam(funcode, cmbProperties.getUid());
            // 构建body
            JSONObject body = new JSONObject();
            body.set("buscod",param.getBuscod());
            body.set("busmod",param.getBusmod());
            reqParam.getJSONObject("request").set("body", body);
            String data = reqParam.toString();
            DcHelper dchelper = new DcHelper(cmbProperties.getUrl(), cmbProperties.getUid(),
                    cmbProperties.getPrivateKey(), cmbProperties.getPublicKey(), cmbProperties.getSymKey());
            String response = dchelper.sendRequest(data, funcode);
            return JSONUtil.parseObj(response);
        }catch (Exception e){
            throw new CommonException("调用招商银行【{}】接口失败：{}", funcode, e.getMessage());
        }
    }

    @Override
    public JSONObject ntqacinf(CmbNtqacinfParam param) {
        // 业务接口名
        String funcode = "NTQACINF";
        try {
            // 构建基本请求参数
            JSONObject reqParam = CmbApiParamUtil.getBaseParam(funcode, cmbProperties.getUid());
            // 构建body
            JSONObject body = new JSONObject();
            body.set("ntqacinfx", param.getNtqacinfx());
            reqParam.getJSONObject("request").set("body", body);
            String data = reqParam.toString();
            DcHelper dchelper = new DcHelper(cmbProperties.getUrl(), cmbProperties.getUid(),
                    cmbProperties.getPrivateKey(), cmbProperties.getPublicKey(), cmbProperties.getSymKey());
            String response = dchelper.sendRequest(data, funcode);
            return JSONUtil.parseObj(response);
        }catch (Exception e){
            throw new CommonException("调用招商银行【{}】接口失败：{}", funcode, e.getMessage());
        }
    }

    @Override
    public JSONObject ntqabinf(CmbNtqabinfParam param) {
        // 业务接口名
        String funcode = "NTQABINF";
        try {
            // 构建基本请求参数
            JSONObject reqParam = CmbApiParamUtil.getBaseParam(funcode, cmbProperties.getUid());
            // 构建body
            JSONObject body = new JSONObject();
            body.set("ntqabinfy", param.getNtqabinfy());
            reqParam.getJSONObject("request").set("body", body);
            String data = reqParam.toString();
            DcHelper dchelper = new DcHelper(cmbProperties.getUrl(), cmbProperties.getUid(),
                    cmbProperties.getPrivateKey(), cmbProperties.getPublicKey(), cmbProperties.getSymKey());
            String response = dchelper.sendRequest(data, funcode);
            return JSONUtil.parseObj(response);
        }catch (Exception e){
            throw new CommonException("调用招商银行【{}】接口失败：{}", funcode, e.getMessage());
        }
    }

    @Override
    public JSONObject ntaccbbk(CmbNtaccbbkParam param) {
        // 业务接口名
        String funcode = "NTACCBBK";
        try {
            // 构建基本请求参数
            JSONObject reqParam = CmbApiParamUtil.getBaseParam(funcode, cmbProperties.getUid());
            // 构建body
            JSONObject body = new JSONObject();
            body.set("fctval", param.getFctval());
            reqParam.getJSONObject("request").set("body", body);
            String data = reqParam.toString();
            DcHelper dchelper = new DcHelper(cmbProperties.getUrl(), cmbProperties.getUid(),
                    cmbProperties.getPrivateKey(), cmbProperties.getPublicKey(), cmbProperties.getSymKey());
            String response = dchelper.sendRequest(data, funcode);
            return JSONUtil.parseObj(response);
        }catch (Exception e){
            throw new CommonException("调用招商银行【{}】接口失败：{}", funcode, e.getMessage());
        }
    }

    @Override
    public JSONObject ntqadinf(CmbNtqadinfParam param) {
        // 业务接口名
        String funcode = "NTQADINF";
        try {
            // 构建基本请求参数
            JSONObject reqParam = CmbApiParamUtil.getBaseParam(funcode, cmbProperties.getUid());
            // 构建body
            JSONObject body = new JSONObject();
            body.set("ntqadinfx", param.getNtqadinfx());
            reqParam.getJSONObject("request").set("body", body);
            String data = reqParam.toString();
            DcHelper dchelper = new DcHelper(cmbProperties.getUrl(), cmbProperties.getUid(),
                    cmbProperties.getPrivateKey(), cmbProperties.getPublicKey(), cmbProperties.getSymKey());
            String response = dchelper.sendRequest(data, funcode);
            return JSONUtil.parseObj(response);
        }catch (Exception e){
            throw new CommonException("调用招商银行【{}】接口失败：{}", funcode, e.getMessage());
        }
    }

    @Override
    public JSONObject trsQryByBreakPoint(CmbTrsQryByBreakPointParam param) {
        // 业务接口名
        String funcode = "trsQryByBreakPoint";
        try {
            // 构建基本请求参数
            JSONObject reqParam = CmbApiParamUtil.getBaseParam(funcode, cmbProperties.getUid());
            // 构建body
            JSONObject body = new JSONObject();
            body.set("TRANSQUERYBYBREAKPOINT_X1", param.getTRANSQUERYBYBREAKPOINT_X1());
            body.set("TRANSQUERYBYBREAKPOINT_Y1", param.getTRANSQUERYBYBREAKPOINT_Y1());
            reqParam.getJSONObject("request").set("body", body);
            String data = reqParam.toString();
            DcHelper dchelper = new DcHelper(cmbProperties.getUrl(), cmbProperties.getUid(),
                    cmbProperties.getPrivateKey(), cmbProperties.getPublicKey(), cmbProperties.getSymKey());
            String response = dchelper.sendRequest(data, funcode);
            return JSONUtil.parseObj(response);
        }catch (Exception e){
            throw new CommonException("调用招商银行【{}】接口失败：{}", funcode, e.getMessage());
        }
    }

}
