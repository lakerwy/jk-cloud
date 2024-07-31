package vip.xiaonuo.im.modular.openIm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dbs.api.DbsApi;
import vip.xiaonuo.im.modular.openIm.param.LoginParam;
import vip.xiaonuo.im.modular.openIm.param.OpenImUser;
import vip.xiaonuo.im.modular.openIm.param.UserBean;
import vip.xiaonuo.im.modular.openIm.config.OpenImProperties;
import vip.xiaonuo.im.modular.openIm.contant.Route;
import vip.xiaonuo.im.modular.openIm.service.OpenImService;
import vip.xiaonuo.im.modular.tencent.client.WebServerClient;
import vip.xiaonuo.util.modular.initData.entity.InitData;
import vip.xiaonuo.util.modular.initData.service.InitDataService;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/22 14:37
 */
@Service
@Slf4j
public class OpenImServiceImpl implements OpenImService {

    @Resource
    OpenImProperties openImProperties;

    @Resource
    private InitDataService initDataService;

    @Resource
    private WebServerClient webServerClient;

    @Override
    public void initUser() {
        try {
            boolean tencnetImUser = initDataService.getInitStatusByType("OPEN_IM_USER");
            if(tencnetImUser){
                return;
            }
            Map<String,Object> mapParam = new HashMap<>();
            mapParam.put("parentId","0");
            String org = webServerClient.getOrgByMap(mapParam);
            JSONObject jsonObject = JSONUtil.parseObj(org);
            String data = jsonObject.getStr("data");
            JSONObject sysOrg = JSONUtil.parseObj(data);
            String childrenUser = webServerClient.getChildrenUser(sysOrg.getStr("id"));
            JSONObject userJson = JSONUtil.parseObj(childrenUser);
            JSONArray jsonArray = userJson.getJSONArray("data");
            String httpsUrl = openImProperties.getUrl() + Route.USER_REGISTER;
            HttpRequest httpPost = HttpUtil.createPost(httpsUrl);
            OpenImUser openImUser = new OpenImUser();
            UserBean imUser;
            for (Object object : jsonArray) {
                JSONObject user = (JSONObject) object;
                String phoneNumber = user.getStr("account");
                if(!StrUtil.isNumeric(phoneNumber)){
                    continue;
                }
                //初始化文件
                imUser = new UserBean();
                imUser.setPhoneNumber(phoneNumber);
                String name = user.getStr("name");
                imUser.setNickName(name);
                Object o = user.get("extJson");
                if(ObjectUtil.isNotEmpty(o)){
                    JSONObject extJson = JSONUtil.parseObj(o);
                    String avatarUrl = extJson.getStr("avatarUrl");
                    if(StrUtil.isNotEmpty(avatarUrl)){
                        imUser.setFaceURL(avatarUrl);
                    }
                }else {
                    imUser.setFaceURL("");
                }
//                String gender = user.getStr("gender");
//                String intGender = "1";
//                if(StrUtil.isNotBlank(gender)){
//                    if(gender.equals("女")){
//                        intGender = "0";
//                    }
//                }
//                imUser.setGender(intGender);
                openImUser.setUser(imUser);
                httpPost.header("Operationid", UUID.randomUUID().toString());
                httpPost.body(JSONUtil.toJsonPrettyStr(openImUser));
                try {
                    HttpResponse execute = httpPost.execute();
                    JSONObject resultJson = JSONUtil.parseObj(execute);
                    if(resultJson.getInt("status") == 200){
                        String body = execute.body();
                        JSONObject jsonBody = JSONUtil.parseObj(body);
                        Integer errCode = jsonBody.getInt("errCode");
                        if(errCode==0){
                            JSONObject result = jsonBody.getJSONObject("data");
                            String imUserId = result.getStr("userID");
                            handleSqlExecution(imUserId,user.getStr("id"));
                        }
                    }
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                }
            }
            InitData initData = new InitData();
            initData.setInitStatus(true);
            initData.setType("OPEN_IM_USER");
            initData.setRemark("OPEM_IM的用户初始化");
            initDataService.saveOrUpdate(initData,new LambdaQueryWrapper<InitData>().eq(InitData::getType,initData.getType()));
        }catch (Exception e){
            log.error("初始化IM 用户失败{}", e);
        }
    }


    public static void handleSqlExecution(String imUserId, String userId) {
        if (StrUtil.isEmpty(imUserId) || StrUtil.isBlank(userId)) {
            return;
        }

        String sql = "UPDATE SYS_USER " +
                "SET EXT_JSON = JSON_SET(COALESCE(EXT_JSON, '{}'), '$.imUserId', ?) " +
                "WHERE ID = ?";

        DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
        try (Connection conn = dbsApi.getCurrentDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            stmt.setString(1, imUserId);
            stmt.setString(2, userId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new CommonException("No rows affected. Check the provided userId.");
            }

            conn.commit();
        } catch (SQLException e) {
            throw new CommonException("Failed to execute SQL: " + e.getMessage());
        }
    }

    /**
     * openIm 自动登录
     * @return
     */
    @Override
    public JSONObject autoLogin(JSONObject userJson){
        String httpsUrl = openImProperties.getUrl() + Route.USER_LOGIN;
        HttpRequest httpPost = HttpUtil.createPost(httpsUrl);
        httpPost.header("Operationid", UUID.randomUUID().toString());
        LoginParam loginParam = new LoginParam();
        String account = userJson.getStr("account");
        loginParam.setPhoneNumber(account);
        int platform = userJson.getInt("platform");
        loginParam.setPlatform(platform);
        String deviceId = userJson.getStr("deviceId");
        loginParam.setDeviceId(deviceId);
        httpPost.body(JSONUtil.toJsonPrettyStr(loginParam));
        HttpResponse execute = httpPost.execute();
        JSONObject resultJson = JSONUtil.parseObj(execute);
        if(resultJson.getInt("status") == 200){
            String body = execute.body();
            JSONObject jsonBody = JSONUtil.parseObj(body);
            Integer errCode = jsonBody.getInt("errCode");
            if(errCode==0){
                return jsonBody.getJSONObject("data");
            }
        }else {
            throw new CommonException("Im login failed. Check the provided userId.");
        }
        return null;
    }


    @Override
    public void test() {
        try {
//            boolean tencnetImUser = initDataService.getInitStatusByType("OPEN_IM_USER");
//            if(tencnetImUser){
//                return;
//            }
            Map<String,Object> mapParam = new HashMap<>();
            mapParam.put("parentId","1666651352106315777");
            String org = webServerClient.getOrgByMap(mapParam);
            JSONObject jsonObject = JSONUtil.parseObj(org);
            String data = jsonObject.getStr("data");
            JSONObject sysOrg = JSONUtil.parseObj(data);
            String childrenUser = webServerClient.getChildrenUser(sysOrg.getStr("id"));
            JSONObject userJson = JSONUtil.parseObj(childrenUser);
            JSONArray jsonArray = userJson.getJSONArray("data");
            String httpsUrl = openImProperties.getUrl() + Route.USER_REGISTER;
            HttpRequest httpPost = HttpUtil.createPost(httpsUrl);
            OpenImUser openImUser = new OpenImUser();
            UserBean imUser;
            Object o1 = jsonArray.get(3);
            JSONObject user = (JSONObject) o1;
            String phoneNumber = user.getStr("account");
                //初始化文件
            imUser = new UserBean();
                imUser.setPhoneNumber(phoneNumber);
                String name = user.getStr("name");
                imUser.setNickName(name);
                Object o = user.get("extJson");
                if(ObjectUtil.isNotEmpty(o)){
                    JSONObject extJson = JSONUtil.parseObj(o);
                    String avatarUrl = extJson.getStr("avatarUrl");
                    if(StrUtil.isNotEmpty(avatarUrl)){
                        imUser.setFaceURL(avatarUrl);
                    }
                }else {
                    imUser.setFaceURL("");
                }
//                String gender = user.getStr("gender");
//                String intGender = "1";
//                if(StrUtil.isNotBlank(gender)){
//                    if(gender.equals("女")){
//                        intGender = "0";
//                    }
//                }
//                imUser.setGender(intGender);
                openImUser.setUser(imUser);
                httpPost.header("Operationid", UUID.randomUUID().toString());
                httpPost.body(JSONUtil.toJsonPrettyStr(openImUser));
                try {
                    HttpResponse execute = httpPost.execute();
                    JSONObject resultJson = JSONUtil.parseObj(execute);
                    if(resultJson.getInt("status") == 200){
                        String body = execute.body();
                        JSONObject jsonBody = JSONUtil.parseObj(body);
                        Integer errCode = jsonBody.getInt("errCode");
                        if(errCode==0){
                            JSONObject result = jsonBody.getJSONObject("data");
                            String imUserId = result.getStr("userID");
                            handleSqlExecution(imUserId,user.getStr("id"));
                        }
                    }
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                }

        }catch (Exception e){
            log.error("初始化IM 用户失败{}", e);
        }
    }
}
