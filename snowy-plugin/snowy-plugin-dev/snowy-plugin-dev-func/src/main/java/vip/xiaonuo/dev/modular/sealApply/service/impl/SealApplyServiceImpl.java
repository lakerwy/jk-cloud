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
package vip.xiaonuo.dev.modular.sealApply.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.modular.sealApply.entity.SealApply;
import vip.xiaonuo.dev.modular.sealApply.entity.SealApplyParam;
import vip.xiaonuo.dev.modular.sealApply.entity.SealDevice;
import vip.xiaonuo.dev.modular.sealApply.entity.SealImpower;
import vip.xiaonuo.dev.modular.sealApply.service.SealApplyService;
import vip.xiaonuo.dev.modular.sealApply.vo.UserSealApplyVo;
import vip.xiaonuo.dev.modular.sealMessage.enums.SealMessageEnum;
import vip.xiaonuo.dev.modular.sealMessage.util.HttpUtil;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 印章基础信息表Service接口实现类
 *
 * @author hxw
 * @date  2024/05/17 08:55
 **/
@Slf4j
@Service
public class SealApplyServiceImpl implements SealApplyService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    /**
     * 创建申请单和授权
     */
    @Override
    public JSONObject getSealApplyFieldList(String id, String uuid, String contractCopiesCount) {

        //请求url
        String url = SealMessageEnum.YXSEALIPPOST.getValue();
        //请求固定参数
        SealApply sealApply = new SealApply();
        sealApply.setAppKey(SealMessageEnum.APPKEY.getValue());
        sealApply.setApiKey(SealMessageEnum.APIKEYAPPLY.getValue());
        sealApply.setTimestamp(new Date().getTime());
        sealApply.setBizId(UUID.randomUUID().toString());
        sealApply.setTenant(SealMessageEnum.TENANT.getValue());

        //调用创建申请单需要的参数
        SealApplyParam  sealApplyParam = new SealApplyParam();
        sealApplyParam.setId(id);//保证流程id和云玺申请单id是一个id值
        sealApplyParam.setTitle("创建申请单");
        sealApplyParam.setUuid(uuid);
        sealApplyParam.setContractCopiesNum(1);
        sealApplyParam.setContractCopiesCount(Integer.valueOf(contractCopiesCount));
        sealApplyParam.setApplicantId("2522642");
        sealApplyParam.setUsePeopleId("2522642");
        //组装数据 云玺需要的数据格式
        sealApply.setParams(JSONUtil.toJsonStr(sealApplyParam));

        JSONObject parseObj =null;
        try {
            //创建申请单
            JSONObject jSONObject = HttpUtil.sendPostRequest(url, JSONUtil.toJsonStr(sealApply));
            if(Integer.parseInt(jSONObject.get("code").toString()) != 0) {
                log.error("创建申请单失败！e:" + jSONObject.get("message"));
                throw new CommonException("创建申请单失败！");
            }

            //创建授权需要的参数
            sealApply.setApiKey(SealMessageEnum.APIKEY.getValue());
            SealImpower sealImpower = new SealImpower();
            sealImpower.setApplicationId(id);
            sealImpower.setUuid(uuid);
            sealImpower.setExpireTime(Long.valueOf("3600"));
            sealApply.setParams(JSONUtil.toJsonStr(sealImpower));
            parseObj = HttpUtil.sendPostRequest(url, JSONUtil.toJsonStr(sealApply));
        } catch (Exception e){
            log.error("创建授权失败！e:" + parseObj.get("message"));
            throw new CommonException("创建授权失败！");
        }

        return parseObj;
    }

    /**
     * 获取云玺所有印章信息
     */
    @Override
    public List<UserSealApplyVo> getSealApplyList() {
        List<UserSealApplyVo> userSealApplyVo = null;
        //获取redis云玺设备信息
        Object sealObject = commonCacheOperator.get(SealMessageEnum.SEALAPPLYLISTKEY.getValue());
        userSealApplyVo = BeanUtil.copyToList((Collection<?>) sealObject, UserSealApplyVo.class);
        //如果redis缓存数据为空，则调用云玺接口获取
        if(CollUtil.isEmpty(userSealApplyVo)){
            userSealApplyVo = queryAllSealApply();
        }
        return userSealApplyVo;
    }

    /**
     * 获取云玺所有的印章设备信息同步到redis
     * @return
     */
    public List<UserSealApplyVo> queryAllSealApply() {
        List<UserSealApplyVo> sealApplyVos = null;
        try{
            //请求url
            String url = SealMessageEnum.YXSEALIPPOST.getValue();
            //请求固定参数
            SealApply sealApply = new SealApply();
            sealApply.setAppKey(SealMessageEnum.APPKEY.getValue());
            sealApply.setApiKey(SealMessageEnum.APIKEYDEVICE.getValue());
            sealApply.setTimestamp(new Date().getTime());
            sealApply.setBizId(UUID.randomUUID().toString());
            sealApply.setTenant(SealMessageEnum.TENANT.getValue());
            SealDevice sealDevice = new SealDevice();
            sealApply.setParams(JSONUtil.toJsonStr(sealDevice));

            JSONObject jsonObject = HttpUtil.sendPostRequest(url, JSONUtil.toJsonStr(sealApply));
             Object objList = JSONUtil.parseObj(jsonObject.get("data").toString()).get("list");
            if(ObjectUtil.isNotEmpty(objList)){
                sealApplyVos = BeanUtil.copyToList((Collection<?>) objList, UserSealApplyVo.class);
                //将云玺用户信息存入redis缓存
                commonCacheOperator.put(SealMessageEnum.SEALAPPLYLISTKEY.getValue(),sealApplyVos);
            }
        }catch (Exception e){
            log.error("获取印章信息失败！e:"+e.getMessage());
            throw new CommonException("获取印章信息失败！");
        }
        return sealApplyVos;
    }
}
