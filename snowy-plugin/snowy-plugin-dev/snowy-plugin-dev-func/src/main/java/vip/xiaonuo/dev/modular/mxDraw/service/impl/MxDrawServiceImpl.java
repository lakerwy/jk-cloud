package vip.xiaonuo.dev.modular.mxDraw.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vip.xiaonuo.dev.modular.mxDraw.param.CadToParam;
import vip.xiaonuo.dev.modular.mxDraw.param.ConvertData;
import vip.xiaonuo.dev.modular.mxDraw.service.MxDrawService;

import java.util.HashMap;
import java.util.Map;

/**
 * 梦想云图服务
 */
@Service
public class MxDrawServiceImpl implements MxDrawService {

    @Value("${cad.mxDraw}")
    private String mxDrawUrl;

    /**
     * https://help.mxdraw.com/?pid=115
     * DWG文件转换编辑格式mxweb
     * @param convertData
     * @return
     */
    @Override
    public String convert(ConvertData convertData) {
        String url = mxDrawUrl + "/mxcad/convert";
        Map<String,Object> mapParam = new HashMap<>();
        mapParam.put("srcpath",convertData.getSrcpath());
        String data = HttpUtil.post(url,mapParam);
        JSONObject objResult = JSONUtil.parseObj(data);
        return null;
    }

    @Override
    public String cadToPdf(CadToParam cadToPdfParam) {
        String url = mxDrawUrl + "/users/tools";
        Map<String,Object> mapParam = new HashMap<>();
        mapParam.put("cmd","cadtopdf");
        mapParam.put("param","file="+cadToPdfParam.getFilePath());
        String data = HttpUtil.post(url,mapParam);
        JSONObject objResult = JSONUtil.parseObj(data);
        return null;
    }

    @Override
    public String cadToJpg(CadToParam cadToPdfParam) {
        String url = mxDrawUrl + "/users/tools";
        Map<String,Object> mapParam = new HashMap<>();
        mapParam.put("cmd","cadtojpg");
        mapParam.put("param","file="+cadToPdfParam.getFilePath());
        String data = HttpUtil.post(url,mapParam);
        JSONObject objResult = JSONUtil.parseObj(data);
        return null;
    }

    @Override
    public String convertWgh(CadToParam cadToParam) {
        String url = mxDrawUrl + "/users";
        Map<String,Object> mapParam = new HashMap<>();
        mapParam.put("cmd","convert");
        mapParam.put("file",cadToParam.getFilePath());
        String data = HttpUtil.post(url,mapParam);
        JSONObject objResult = JSONUtil.parseObj(data);
        return null;
    }


}
