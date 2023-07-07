package vip.xiaonuo.sys.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetuserinfoResponse;
import com.fhs.common.utils.JsonUtils;
import com.fhs.common.utils.StringUtil;
import com.fhs.trans.service.impl.TransService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.auth.api.AuthThirdApi;
import vip.xiaonuo.auth.api.LoginThirdApi;
import vip.xiaonuo.auth.core.pojo.SysLoginUser;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.util.CommonCryptogramUtil;
import vip.xiaonuo.common.util.CommonDingDingUtil;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.enums.AuthThirdCategoryEnum;
import vip.xiaonuo.sys.modular.user.mapper.SysUserMapper;
import vip.xiaonuo.sys.modular.user.param.*;
import vip.xiaonuo.sys.modular.user.service.SysUserService;
import vip.xiaonuo.sys.modular.user.service.ThirdUserService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yong.ye
 * @date 2023/6/15 16:05
 */
@Service
@Slf4j
public class ThirdUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ThirdUserService {
    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private TransService transService;

    @Resource
    private AuthThirdApi authThirdApi;

    @Resource
    private LoginThirdApi loginThirdApi;

    @Resource
    private SysUserService sysUserService;

    @Override
    public String toDoDingDingApplication(DingDingLoginParam dingDingLoginParam) {
        try {
            //获取token
            String token = getDingDingToken(dingDingLoginParam.getAppKey(), dingDingLoginParam.getAppSecret());
            //获取钉钉用户信息
            OapiV2UserGetuserinfoResponse.UserGetByCodeResponse info = CommonDingDingUtil.getUserInfo(token, dingDingLoginParam.getCode());
            log.info("钉钉结果："+ JsonUtils.object2json(info));
            //根据钉钉用户Id，获取系统用户信息 category = 2 代表钉钉
            String userId = authThirdApi.getUserIdByThirdId(info.getUserid(), AuthThirdCategoryEnum.DingDing.getValue());
            //逻辑判断：若当前系统中不存在dingdinguserid的记录，则新增一条记录
            if(StringUtil.isEmpty(userId)){
                OapiV2UserGetResponse.UserGetResponse dingUser = CommonDingDingUtil.getUserById(token, info.getUserid());
                SysLoginUser tmpUser = sysUserService.getUserByPhone(dingUser.getMobile());
                log.info("钉钉用户详情：" + JsonUtils.object2json(dingUser));
                if(ObjectUtil.isNotEmpty(tmpUser)) {
                    userId = tmpUser.getId();
                    log.info("钉钉用户匹配的系统用户userId：" + userId);
                    authThirdApi.addAuthThird(userId, dingUser.getUserid(), AuthThirdCategoryEnum.DingDing.getValue(), tmpUser.getName());
                }
            }
            //绑定系统用户，并返回token
            return loginThirdApi.doLoginThirdById(userId, "", "");
        }catch (Exception ex){
            log.info("钉钉："+ JsonUtils.object2json(ex));
            return "";
        }
    }

    @Override
    public List<Map<String,String>> getAllDingDingIds() {
        return authThirdApi.getKeyValueByCategory(AuthThirdCategoryEnum.DingDing.getValue());
    }

    /**
     * 根据钉钉应用参数，获取钉钉内部应用Token
     *
     * @param appKey
     * @param appSecret
     * @return
     */
    public String getDingDingToken(String appKey,String appSecret){
        Object existDingDingToken = commonCacheOperator.get(appKey);
        if(ObjectUtil.isNotEmpty(existDingDingToken)){
            return existDingDingToken.toString();
        }
        String dingDingToken = CommonDingDingUtil.getToken(appKey,appSecret);
        commonCacheOperator.put(appKey,dingDingToken,7200);
        return dingDingToken;
    }

    @Override
    public String bindECard(WeChatBindEcardParam weChatBindEcardParam) {
        // 解密，并做哈希值 CommonCryptogramUtil.doSm2Decrypt(weChatBindEcardParam.getPwd())
        String passwordHash = CommonCryptogramUtil.doHashValue(weChatBindEcardParam.getPwd());
        // 根据Account获取用户
        SysUser sysUser = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, weChatBindEcardParam.getAccount()));
        // 校验
        if (ObjectUtil.isEmpty(sysUser)) {
            throw new CommonException("用户不存在，Account值为：{}",weChatBindEcardParam.getAccount());
        }
        if (!sysUser.getPassword().equals(passwordHash)) {
            throw new CommonException("密码错误");
        }
        // 根据userid获取微信名片小程序openid
        String thirdId = authThirdApi.getThirdIdByUserId(sysUser.getId(),AuthThirdCategoryEnum.WeChat_ECard.getValue());
        // 已绑定过，不一致
        if(ObjectUtil.isNotEmpty(thirdId) && !thirdId.equals(weChatBindEcardParam.getOpenid())){
            throw new CommonException("已绑定其他微信号，请先解绑或更换微信登录");
        }
        // 若未绑定，则绑定
        if(ObjectUtil.isEmpty(thirdId)){
            authThirdApi.addAuthThird(sysUser.getId(),weChatBindEcardParam.getOpenid(),AuthThirdCategoryEnum.WeChat_ECard.getValue(), sysUser.getName());
        }
        //绑定系统用户，并返回token
        return loginThirdApi.doLoginThirdById(sysUser.getId(),"","");
    }

    @Override
    public SysLoginUser getUserByOpenId(WeChatUserParam weChatUserParam) {
        String userId = authThirdApi.getUserIdByThirdId(weChatUserParam.getOpenId(),AuthThirdCategoryEnum.WeChat_ECard.getValue());
        SysUser sysUser = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getId, userId));
        if (ObjectUtil.isNotEmpty(sysUser)) {
            transService.transOne(sysUser);
            return BeanUtil.copyProperties(sysUser, SysLoginUser.class);
        }
        return null;
    }

    @Override
    public Boolean unBindECard(WeChatUnBindParam weChatUnBindParam) {
        return authThirdApi.delAuthThird(weChatUnBindParam.getUserId(),AuthThirdCategoryEnum.WeChat_ECard.getValue());
    }
}
