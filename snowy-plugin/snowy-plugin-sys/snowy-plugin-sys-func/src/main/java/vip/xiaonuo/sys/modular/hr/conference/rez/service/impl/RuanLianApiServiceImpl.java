package vip.xiaonuo.sys.modular.hr.conference.rez.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.RuanLianParam;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.RuanLianApiService;
import vip.xiaonuo.sys.modular.hr.conference.rez.util.HttpUtil;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.AttendanceTimeSlot;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RuanLianApiServiceImpl implements RuanLianApiService {
    private static final String RUANLIAN_COMPANY_ID = "bf1d4e7a5a5a2490";//企业编号
    private static final String RUANLIAN_URL = "https://app.rlyx.top";
    private static final String RUANLIAN_USER_KEY = "RUANLIAN_USER_KEY";  //软联人员信息缓存
    private static final String RUANLIAN_CJ_KEY = "RUANLIAN_CJ_KEY";  //软联场景面板信息缓存



    @Resource
    ConferenceRezService conferenceRezService;
    @Resource
    CommonCacheOperator commonCacheOperator;
    /** 获取软联人员信息*/
    @Override
    public JSONObject getuserinfo(){
        String route = "/Apiservice/Meetings/getuserinfo";
        String url = RUANLIAN_URL+route;
        Map<String,String> mapParam = new HashMap<>();
        mapParam.put("companyId",RUANLIAN_COMPANY_ID);
        return HttpUtil.httpGet(url,mapParam);
    }

    /** 获取面板场景信息*/
    @Override
    public JSONObject getcjinfo(){
        String route = "/Apiservice/Meetings/getcjinfo";
        String url = RUANLIAN_URL+route;
        Map<String,String> mapParam = new HashMap<>();
        mapParam.put("companyId",RUANLIAN_COMPANY_ID);
        return HttpUtil.httpGet(url,mapParam);
    }
    private void ruanLianCache(){
        JSONObject userinfo =getuserinfo();
        JSONObject cjinfo = getcjinfo();
        commonCacheOperator.put(RUANLIAN_USER_KEY, userinfo.toString()); //缓存
        commonCacheOperator.put(RUANLIAN_CJ_KEY, cjinfo.toString()); //缓存
    }

    private void putCache(RuanLianParam ruanLianParam){
        String cjname = ruanLianParam.getCjname();
        String mianbanName = ruanLianParam.getMianbanName();
        String userCache = String.valueOf(commonCacheOperator.get(RUANLIAN_USER_KEY));//取考勤缓存数据
        Map<String,String> userMap = new HashMap<>();
        if(StringUtils.isNotEmpty(userCache) && !userCache.equals("null")){
            userMap = (Map)JSON.parse(userCache);
        }
        JSONArray userArray = new JSONArray(userMap.get("datainfo"));
        List<JSONObject> userList = userArray.toList(JSONObject.class);
        userList.forEach(user -> {
           if(user.get("xmname").equals(cjname)){
               ruanLianParam.setUserid(user.get("id").toString());
           }
        });
        String cjCache = String.valueOf(commonCacheOperator.get(RUANLIAN_CJ_KEY));//取考勤缓存数据
        Map<String,String> cjMap = new HashMap<>();
        if(StringUtils.isNotEmpty(cjCache) && !cjCache.equals("null")){
            cjMap = (Map)JSON.parse(cjCache);
        }
        JSONArray jsonArray = new JSONArray(cjMap.get("datainfo"));
        List<JSONObject> cjList = jsonArray.toList(JSONObject.class);
        cjList.forEach(cj -> {
            if(cj.get("cjname").equals(mianbanName)){
                ruanLianParam.setCjid(cj.get("id").toString());
            }
        });
    }
    @Override
    public JSONObject setmeeting(RuanLianParam ruanLianParam) throws IOException {
        if(StringUtils.isEmpty(ruanLianParam.getRezId())){
            return null;
        }
        String userCache = String.valueOf(commonCacheOperator.get(RUANLIAN_USER_KEY));//取考勤缓存数据
        String cjCache = String.valueOf(commonCacheOperator.get(RUANLIAN_CJ_KEY));//软联场景面板信息数据
        if((StringUtils.isEmpty(userCache) || userCache.equals("null")) ||
                (StringUtils.isEmpty(cjCache) || cjCache.equals("null"))){
            ruanLianCache();
        }
        putCache(ruanLianParam);//获取缓存
        String route = "/Apiservice/Meetings/setmeeting";
        String url = RUANLIAN_URL+route;
        Map<String,String> mapParam = new HashMap<>();
        mapParam.put("companyId",RUANLIAN_COMPANY_ID);
        mapParam.put("userid",ruanLianParam.getUserid());
        mapParam.put("ztname",ruanLianParam.getZtname());
        mapParam.put("cjname",ruanLianParam.getCjname());
        mapParam.put("yytimes",ruanLianParam.getYytimes());
        mapParam.put("yytimee",ruanLianParam.getYytimee());
        mapParam.put("cjid",ruanLianParam.getCjid());
        JSONObject jsonObject = HttpUtil.sendPost(url,mapParam);
        String code = jsonObject.getStr("code");
        String yyid = jsonObject.getStr("yyid");
        ConferenceRez conferenceRez = conferenceRezService.getById(ruanLianParam.getRezId());
        if(code.equals("1")){
            conferenceRez.setExtJson(jsonObject.toString());
            conferenceRez.setIsPush(1);
            conferenceRez.setYyid(yyid);
        }else{
            conferenceRez.setExtJson(jsonObject.toString());
            conferenceRez.setPushSize(ObjectUtil.isNotEmpty(conferenceRez.getPushSize()) ? conferenceRez.getPushSize()+1 : 1);
        }
        conferenceRezService.updateById(conferenceRez);
        return jsonObject;
    }

    @Override
    public JSONObject delmeeting(String yyid){
        if(StringUtils.isEmpty(yyid)){
            return null;
        }
        String route = "/Apiservice/Meetings/delmeeting";
        String url = RUANLIAN_URL+route;
        Map<String,String> mapParam = new HashMap<>();
        mapParam.put("companyId",RUANLIAN_COMPANY_ID);
        mapParam.put("yyid",yyid);
        JSONObject jsonObject = HttpUtil.httpGet(url,mapParam);
        return jsonObject;
    }

    public static void main(String[] args) {
        try{
            String route = "/Apiservice/Meetings/setmeeting";
            String url = RUANLIAN_URL+route;
            Map<String,String> mapParam = new HashMap<>();
            mapParam.put("companyId",RUANLIAN_COMPANY_ID);
            mapParam.put("userid","48251");
            mapParam.put("ztname", URLDecoder.decode("软联会议预约测试","utf-8"));
            mapParam.put("cjname",URLDecoder.decode("刘见宇","utf-8"));
            mapParam.put("yytimes","2023-12-05 21:00:00");
            mapParam.put("yytimee","2023-12-05 22:00:00");
            mapParam.put("cjid","1502");
            JSONObject jsonObject =  HttpUtil.sendPost(url,mapParam);
            System.out.println(jsonObject.toString());
        }catch (Exception e){
        }
    }
}
