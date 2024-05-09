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
package vip.xiaonuo.bp.modular.platform.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.enums.SaClientTypeEnum;
import vip.xiaonuo.auth.core.pojo.SaBaseClientLoginUser;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.bp.modular.platform.enums.BpUserStatusEnum;
import vip.xiaonuo.bp.modular.platform.param.user.*;
import vip.xiaonuo.bp.modular.platform.vo.PicValidCodeResult;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.bp.modular.platform.entity.BpUser;
import vip.xiaonuo.bp.modular.platform.mapper.BpUserMapper;
import vip.xiaonuo.bp.modular.platform.service.BpUserService;
import vip.xiaonuo.common.util.CommonCryptogramUtil;
import vip.xiaonuo.common.util.CommonEmailUtil;
import vip.xiaonuo.dev.api.DevConfigApi;
import vip.xiaonuo.dev.api.DevSmsApi;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.List;

/**
 * 招采平台 - 用户表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/05/09 08:41
 **/
@Service
@DS("slave_1")
public class BpUserServiceImpl extends ServiceImpl<BpUserMapper, BpUser> implements BpUserService {

    private static final String SNOWY_SYS_DEFAULT_CAPTCHA_OPEN_KEY = "SNOWY_SYS_DEFAULT_CAPTCHA_OPEN";

    private static final String BP_VALID_CODE_CACHE_KEY = "bp-validCode:";

    /* 云片短信：验证码短信模板 */
    private static final String SNOWY_SMS_YUNPIAN_TPL_CODE_ID = "SNOWY_SMS_YUNPIAN_TPL_CODE_ID";

    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private DevConfigApi devConfigApi;

    @Resource
    private DevSmsApi devSmsApi;



    @Override
    public Page<BpUser> page(BpUserPageParam bpUserPageParam) {
        QueryWrapper<BpUser> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bpUserPageParam.getName())) {
            queryWrapper.lambda().like(BpUser::getName, bpUserPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(bpUserPageParam.getAccount())) {
            queryWrapper.lambda().like(BpUser::getAccount, bpUserPageParam.getAccount());
        }
        if(ObjectUtil.isNotEmpty(bpUserPageParam.getUserStatus())) {
            queryWrapper.lambda().eq(BpUser::getUserStatus, bpUserPageParam.getUserStatus());
        }
        if(ObjectUtil.isNotEmpty(bpUserPageParam.getEmail())) {
            queryWrapper.lambda().like(BpUser::getEmail, bpUserPageParam.getEmail());
        }
        if(ObjectUtil.isNotEmpty(bpUserPageParam.getPhone())) {
            queryWrapper.lambda().like(BpUser::getPhone, bpUserPageParam.getPhone());
        }
        if(ObjectUtil.isAllNotEmpty(bpUserPageParam.getSortField(), bpUserPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bpUserPageParam.getSortOrder());
            queryWrapper.orderBy(true, bpUserPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bpUserPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BpUser::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BpUserAddParam bpUserAddParam) {
        BpUser bpUser = BeanUtil.toBean(bpUserAddParam, BpUser.class);
        this.save(bpUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BpUserEditParam bpUserEditParam) {
        BpUser bpUser = this.queryEntity(bpUserEditParam.getId());
        BeanUtil.copyProperties(bpUserEditParam, bpUser);
        this.updateById(bpUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BpUserIdParam> bpUserIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bpUserIdParamList, BpUserIdParam::getId));
    }

    @Override
    public BpUser detail(BpUserIdParam bpUserIdParam) {
        return this.queryEntity(bpUserIdParam.getId());
    }

    @Override
    public BpUser queryEntity(String id) {
        BpUser bpUser = this.getById(id);
        if(ObjectUtil.isEmpty(bpUser)) {
            throw new CommonException("招采平台 - 用户表不存在，id值为：{}", id);
        }
        return bpUser;
    }

    @Override
    public void register(RegisterParam registerParam) {
        //1. 密码 - 确认密码一致
        if(!registerParam.getPassword().equals(registerParam.getPasswordSure())){
            throw new CommonException("密码与确认密码不一致！");
        }
        //2. 短信验证码是否一致
        String phone = registerParam.getPhone();
        validPhoneValidCodeParam(phone, registerParam.getValidCode(), registerParam.getValidCodeReqNo());
        //3. 判断用户是否存在
        List<BpUser> bpUserList = this.list(new QueryWrapper<BpUser>().lambda().eq(BpUser::getAccount,phone));
        if(CollUtil.isNotEmpty(bpUserList)){
            throw new CommonException("账号已存在！");
        }
        //4. 创建账号
        BpUserAddParam bpUserAddParam = new BpUserAddParam();
        bpUserAddParam.setAccount(phone);
        bpUserAddParam.setName(registerParam.getName());
        bpUserAddParam.setPhone(phone);
        // SM2解密并获得前端传来的密码哈希值
        String passwordHash;
        try {
            // 解密，并做哈希值
            passwordHash = CommonCryptogramUtil.doHashValue(CommonCryptogramUtil.doSm2Decrypt(registerParam.getPassword()));
        } catch (Exception e) {
            throw new CommonException("密码解密失败");
        }
        bpUserAddParam.setPassword(passwordHash);
        bpUserAddParam.setUserStatus(BpUserStatusEnum.ENABLE.getValue());
        this.add(bpUserAddParam);
    }


    @Override
    public PicValidCodeResult getPicCaptcha() {
        // 生成验证码，随机4位字符
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(100, 38, 4, 10);
        // 定义返回结果
        PicValidCodeResult picValidCodeResult = new PicValidCodeResult();
        // 获取验证码的值
        String validCode = circleCaptcha.getCode();
        // 获取验证码的base64
        String validCodeBase64 = circleCaptcha.getImageBase64Data();
        // 生成请求号
        String validCodeReqNo = IdWorker.getIdStr();
        // 将base64返回前端
        picValidCodeResult.setValidCodeBase64(validCodeBase64);
        // 将请求号返回前端
        picValidCodeResult.setValidCodeReqNo(validCodeReqNo);
        // 将请求号作为key，验证码的值作为value放到redis，用于校验，5分钟有效
        commonCacheOperator.put(BP_VALID_CODE_CACHE_KEY + validCodeReqNo, validCode, 5 * 60L);
        return picValidCodeResult;

    }

    @Override
    public String getPhoneValidCode(GetPhoneValidCodeParam getPhoneValidCodeParam) {
        // 手机号
        String phone = getPhoneValidCodeParam.getPhone();
        // 验证码
        String validCode = getPhoneValidCodeParam.getValidCode();
        // 验证码请求号
        String validCodeReqNo = getPhoneValidCodeParam.getValidCodeReqNo();
        // 校验参数
        validPhoneValidCodeParam(null, validCode, validCodeReqNo);
        // 生成手机验证码的值，随机6为数字
        String phoneValidCode = RandomUtil.randomNumbers(6);
        // 生成手机验证码的请求号
        String phoneValidCodeReqNo = IdWorker.getIdStr();

        // TODO 使用云片执行发送验证码，将验证码作为短信内容的参数变量放入，
        String tpl_Id = devConfigApi.getValueByKey(SNOWY_SMS_YUNPIAN_TPL_CODE_ID);
        String tpl_Value = URLEncoder.encode("#code#") + "="  + URLEncoder.encode(phoneValidCode);
        devSmsApi.sendSmsYunPian("", phone, tpl_Id, tpl_Value);

        // 将请求号作为key，验证码的值作为value放到redis，用于校验，10分钟有效
        commonCacheOperator.put(BP_VALID_CODE_CACHE_KEY + phone + StrUtil.UNDERLINE + phoneValidCodeReqNo, phoneValidCode, 10 * 60L);
        // 返回请求号
        return phoneValidCodeReqNo;
    }

    @Override
    public Object doLogin(AccountPasswordLoginParam accountPasswordLoginParam) {
        // 获取账号
        String account = accountPasswordLoginParam.getAccount();
        // SM2解密并获得前端传来的密码哈希值
        String passwordHash;
        try {
            // 解密，并做哈希值
            passwordHash = CommonCryptogramUtil.doHashValue(CommonCryptogramUtil.doSm2Decrypt(accountPasswordLoginParam.getPassword()));
        } catch (Exception e) {
            throw new CommonException("密码解密失败");
        }
        List<BpUser> bpUserList = this.list(new QueryWrapper<BpUser>().lambda().eq(BpUser::getAccount,account));
        if(CollUtil.isEmpty(bpUserList)){
            throw new CommonException("账号不存在");
        }else if(bpUserList.size() > 1){
            throw new CommonException("账号异常，请联系管理员");
        }else{
            BpUser bpUser = bpUserList.get(0);
            if(!passwordHash.equals(bpUser.getPassword())){
                throw new CommonException("密码错误");
            }
            return bpUser;
        }
    }

    @Override
    public Object doLoginByPhone(PhoneValidCodeLoginParam phoneValidCodeLoginParam) {
        // 手机号
        String phone = phoneValidCodeLoginParam.getPhone();
        // 校验参数
        validPhoneValidCodeParam(phone, phoneValidCodeLoginParam.getValidCode(), phoneValidCodeLoginParam.getValidCodeReqNo());
        // 根据手机号获取用户信息
        List<BpUser> bpUserList = this.list(new QueryWrapper<BpUser>().lambda().eq(BpUser::getAccount,phone));
        if(CollUtil.isEmpty(bpUserList)){
            throw new CommonException("账号不存在");
        }else if(bpUserList.size() > 1){
            throw new CommonException("账号异常，请联系管理员");
        }else {
            BpUser bpUser = bpUserList.get(0);
            return bpUser;
        }
    }


    /**
     * 校验手机号与验证码等参数
     *
     **/
    private void validPhoneValidCodeParam(String phoneOrEmail, String validCode, String validCodeReqNo) {
        // 验证码正确则校验手机号格式
        if(ObjectUtil.isEmpty(phoneOrEmail)) {
            // 执行校验验证码
            validValidCode(null, validCode, validCodeReqNo);
        } else {
            if(!PhoneUtil.isMobile(phoneOrEmail) && !CommonEmailUtil.isEmail(phoneOrEmail)) {
                throw new CommonException("手机号格式错误");
            }
            // 执行校验验证码
            validValidCode(phoneOrEmail, validCode, validCodeReqNo);
        }
    }


    /**
     * 校验验证码方法
     *
     **/
    private void validValidCode(String phoneOrEmail, String validCode, String validCodeReqNo) {
        // 依据请求号，取出缓存中的验证码进行校验
        Object existValidCode;
        if(ObjectUtil.isEmpty(phoneOrEmail)) {
            existValidCode = commonCacheOperator.get(BP_VALID_CODE_CACHE_KEY + validCodeReqNo);
        } else {
            existValidCode = commonCacheOperator.get(BP_VALID_CODE_CACHE_KEY + phoneOrEmail + StrUtil.UNDERLINE + validCodeReqNo);
        }
        // 为空则直接验证码错误
        if(ObjectUtil.isEmpty(existValidCode)) {
            throw new CommonException("验证码错误");
        }
        // 移除该验证码
        if(ObjectUtil.isEmpty(phoneOrEmail)) {
            commonCacheOperator.remove(BP_VALID_CODE_CACHE_KEY + validCodeReqNo);
        } else {
            commonCacheOperator.remove(BP_VALID_CODE_CACHE_KEY + phoneOrEmail + StrUtil.UNDERLINE + validCodeReqNo);
        }
        // 不一致则直接验证码错误
        if (!validCode.equals(Convert.toStr(existValidCode).toLowerCase())) {
            throw new CommonException("验证码错误");
        }
    }
}
