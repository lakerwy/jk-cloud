package vip.xiaonuo.biz.modular.cmb.util;

import cn.hutool.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CmbApiParamUtil {

    private static final int BOUND_START = 1000000;
    private static final int BOUND_END = 9000000;
    private static final Random random = new Random();

    public static JSONObject getBaseParam(String funcode, String userid){
        String currentDatetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String reqid = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()) + (BOUND_START + random.nextInt(BOUND_END));
        JSONObject param = new JSONObject();
        JSONObject request = new JSONObject();
        JSONObject head = new JSONObject();
        JSONObject signature = new JSONObject();
        signature.set("sigdat","__signature_sigdat__");
        signature.set("sigtim",currentDatetime);
        head.set("funcode", funcode);
        head.set("userid", userid);
        head.set("reqid",reqid);
        request.set("head", head);
        param.set("signature", signature);
        param.set("request",request);
        return param;
    }
}
