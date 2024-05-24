/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.dev.modular.sealMessage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.dev.modular.sealMessage.enums.SealMessageEnum;
import vip.xiaonuo.dev.modular.sealMessage.util.HttpUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.modular.sealMessage.service.SealMessageService;
import vip.xiaonuo.dev.modular.sealMessage.vo.UserSealBaseVo;

import javax.annotation.Resource;
import java.util.*;

/**
 * 印章基础信息表Service接口实现类
 *
 * @author hxw
 * @date  2024/05/17 08:55
 **/
@Slf4j
@Service
public class SealMessageServiceImpl implements SealMessageService {

   @Resource
   private CommonCacheOperator commonCacheOperator;

    /**
     * 获取群杰所有的印章信息同步到redis
     * @return
     */
    @Override
    public List<UserSealBaseVo> queryAllSealBase() {
        List<UserSealBaseVo> sealBaseVos = null;
        String token = null;
        try{
            //获取缓存的token
            token = getNewToken();
            //请求url
            String url = SealMessageEnum.SEALBASEIPPOST.getValue();
            String contentType = "application/json";
            JSONObject jsonObject = HttpUtil.sendPost(url, null, token, contentType);
            if(ObjectUtil.isNotEmpty(jsonObject.get("data"))){
                Object obj = jsonObject.get("data");
                sealBaseVos = BeanUtil.copyToList((Collection<?>) obj, UserSealBaseVo.class);
                //将群杰用户信息存入redis缓存
                commonCacheOperator.put(SealMessageEnum.SEALMESSAGELISTKEY.getValue(),sealBaseVos);
            }
        }catch (Exception e){
            log.error("获取群杰所有的印章信息失败！e:"+e.getMessage());
            throw new CommonException("获取群杰所有的印章信息失败！");
        }
        return sealBaseVos;
    }

    @Override
    public String sealAuthorize(String extJson){
        String token = null;
        //获取缓存的token
        token = getNewToken();
        //请求url
        String url = SealMessageEnum.SEALAUTHORIZEIPPOST.getValue();
        Map<String, Object> map = new HashMap<>();
        map.put("workflowApplyParam", extJson);
        JSONObject jsonObject = HttpUtil.sendPostRequest(url, map, token);
        if(Integer.parseInt(jsonObject.get("status").toString()) != 0){
            log.error("创建申请并授权失败！e:"+ jsonObject.get("message"));
            throw new CommonException("创建申请并授权失败！");
        }
        return jsonObject.toString();
    }

    @Override
    public List<UserSealBaseVo> getSealBaseList() {
        List<UserSealBaseVo> userSealBaseVos = null;
        //获取redis群杰用户信息
        Object sealObject = commonCacheOperator.get(SealMessageEnum.SEALMESSAGELISTKEY.getValue());
        userSealBaseVos = BeanUtil.copyToList((Collection<?>) sealObject, UserSealBaseVo.class);
        //如果redis缓存数据为空，则调用群杰接口获取
        if(CollUtil.isEmpty(userSealBaseVos)){
           userSealBaseVos = queryAllSealBase();
        }
        return userSealBaseVos;
    }

    public String getToken() {
        String token = null;
        try{
            //请求url
            String url = SealMessageEnum.GETTOKENIPPOST.getValue();
            Map<String, String> map = new HashMap<String, String>();
            //请求参数-- 账号/密码
            map.put(SealMessageEnum.GETTOKENACCOUNTKEY.getValue(), SealMessageEnum.GETTOKENACCOUNTVALUE.getValue());
            map.put(SealMessageEnum.GETTOKENPASSWORDKEY.getValue(),SealMessageEnum.GETTOKENPASSWORDVALUE.getValue());
            String contentType = "application/x-www-form-urlencoded";
            JSONObject jsonObject = HttpUtil.sendPost(url,map,null,contentType);
            if(ObjectUtil.isNotEmpty(jsonObject.getJSONObject("data"))){
                token = jsonObject.getJSONObject("data").getStr("token");
            }
        }catch (Exception e){
            log.error("获取群杰token失败！e:"+e.getMessage());
            throw new CommonException("获取群杰token失败！");
        }
        //token存入缓存 2小时过期
        commonCacheOperator.put(SealMessageEnum.SEALMESSAGETOKENKEY.getValue(),token,7200);
        return token;
    }

    public String getNewToken(){
        String newToken = null;
        newToken =  ObjectUtil.isEmpty(commonCacheOperator.get(SealMessageEnum.SEALMESSAGETOKENKEY.getValue())) ? "" : String.valueOf(commonCacheOperator.get(SealMessageEnum.SEALMESSAGETOKENKEY.getValue()));
        //如果没有取到缓存的token，则接口获取
        if(ObjectUtil.isEmpty(newToken)){
            newToken = getToken();
            if(ObjectUtil.isEmpty(newToken)) {
                throw new CommonException("token 异常");
            }
        }
        return newToken;
    }
}
