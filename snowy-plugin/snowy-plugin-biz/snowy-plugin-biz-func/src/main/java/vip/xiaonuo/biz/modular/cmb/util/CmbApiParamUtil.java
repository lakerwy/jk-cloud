package vip.xiaonuo.biz.modular.cmb.util;

import cn.hutool.json.JSONObject;
import vip.xiaonuo.biz.modular.cmb.param.CmbApiParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CmbApiParamUtil {

    private static final int BOUND_START = 1000000;
    private static final int BOUND_END = 9000000;
    private static final Random random = new Random();

    public static JSONObject getRequestParam(String funcode, String userid, CmbApiParam param){
        String currentDatetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String reqid = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()) + (BOUND_START + random.nextInt(BOUND_END));
        JSONObject reqParam = new JSONObject();
        JSONObject request = new JSONObject();
        JSONObject head = new JSONObject();
        JSONObject signature = new JSONObject();
        signature.set("sigdat","__signature_sigdat__");
        signature.set("sigtim",currentDatetime);
        head.set("funcode", funcode);
        head.set("userid", userid);
        head.set("reqid",reqid);
        request.set("head", head);
        request.set("body", param.getBody());
        reqParam.set("signature", signature);
        reqParam.set("request",request);
        return reqParam;
    }

    public static JSONObject getParam(String funcode, String userid, Object param){
        String currentDatetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String reqid = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()) + (BOUND_START + random.nextInt(BOUND_END));
        JSONObject reqParam = new JSONObject();
        JSONObject request = new JSONObject();
        JSONObject head = new JSONObject();
        JSONObject signature = new JSONObject();
        signature.set("sigdat","__signature_sigdat__");
        signature.set("sigtim",currentDatetime);
        head.set("funcode", funcode);
        head.set("userid", userid);
        head.set("reqid",reqid);
        request.set("head", head);
        request.set("body", param);
        reqParam.set("signature", signature);
        reqParam.set("request",request);
        return reqParam;
    }
}
