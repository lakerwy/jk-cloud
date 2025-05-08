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
package vip.xiaonuo.dev.modular.sms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.modular.sms.entity.DevSms;
import vip.xiaonuo.dev.modular.sms.enums.DevSmsEngineTypeEnum;
import vip.xiaonuo.dev.modular.sms.mapper.DevSmsMapper;
import vip.xiaonuo.dev.modular.sms.param.*;
import vip.xiaonuo.dev.modular.sms.service.DevSmsService;
import vip.xiaonuo.dev.modular.sms.util.DevSmsAliyunUtil;
import vip.xiaonuo.dev.modular.sms.util.DevSmsTencentUtil;
import vip.xiaonuo.dev.modular.sms.util.DevSmsYunPianUtil;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.List;

/**
 * 短信Service接口实现类
 *
 * @author xuyuxiang
 * @date 2022/2/23 18:43
 **/
@Service
public class DevSmsServiceImpl extends ServiceImpl<DevSmsMapper, DevSms> implements DevSmsService {

    private static final String PAGE_VALID_CODE_CACHE_KEY = "page-validCode:";

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendAliyun(DevSmsSendAliyunParam devSmsSendAliyunParam) {
        validPhone(devSmsSendAliyunParam.getPhoneNumbers());
        String receiptInfo = DevSmsAliyunUtil.sendSms(devSmsSendAliyunParam.getPhoneNumbers(), devSmsSendAliyunParam.getSignName(),
                devSmsSendAliyunParam.getTemplateCode(), devSmsSendAliyunParam.getTemplateParam());
        DevSms devSms = new DevSms();
        BeanUtil.copyProperties(devSmsSendAliyunParam, devSms);
        devSms.setEngine(DevSmsEngineTypeEnum.ALIYUN.getValue());
        devSms.setReceiptInfo(receiptInfo);
        this.save(devSms);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendTencent(DevSmsSendTencentParam smsSendTencentParam) {
        validPhone(smsSendTencentParam.getPhoneNumbers());
        String receiptInfo =DevSmsTencentUtil.sendSms(smsSendTencentParam.getSdkAppId(), smsSendTencentParam.getPhoneNumbers(),
                smsSendTencentParam.getSignName(), smsSendTencentParam.getTemplateCode(), smsSendTencentParam.getTemplateParam());
        DevSms devSms = new DevSms();
        BeanUtil.copyProperties(smsSendTencentParam, devSms);
        devSms.setEngine(DevSmsEngineTypeEnum.TENCENT.getValue());
        devSms.setReceiptInfo(receiptInfo);
        this.save(devSms);
    }

    @Override
    public Page<DevSms> page(DevSmsPageParam devSmsPageParam) {
        QueryWrapper<DevSms> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(devSmsPageParam.getEngine())) {
            queryWrapper.lambda().eq(DevSms::getEngine, devSmsPageParam.getEngine());
        }
        if(ObjectUtil.isNotEmpty(devSmsPageParam.getSearchKey())) {
            queryWrapper.lambda().like(DevSms::getPhoneNumbers, devSmsPageParam.getSearchKey());
        }
        if(ObjectUtil.isAllNotEmpty(devSmsPageParam.getSortField(), devSmsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(devSmsPageParam.getSortOrder());
            queryWrapper.orderBy(true, devSmsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(devSmsPageParam.getSortField()));
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public void delete(List<DevSmsIdParam> devSmsIdParamList) {
        this.removeByIds(CollStreamUtil.toList(devSmsIdParamList, DevSmsIdParam::getId));
    }

    @Override
    public DevSms detail(DevSmsIdParam devSmsIdParam) {
        return this.queryEntity(devSmsIdParam.getId());
    }

    @Override
    public DevSms queryEntity(String id) {
        DevSms devSms = this.getById(id);
        if(ObjectUtil.isEmpty(devSms)) {
            throw new CommonException("短信不存在，id值为：{}", id);
        }
        return devSms;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendYunPian(DevSmsSendYunPianParam smsSendYunPianParam) {
        validPhone(smsSendYunPianParam.getPhoneNumbers());
        String receiptInfo = DevSmsYunPianUtil.sendSms(smsSendYunPianParam.getApiKey(),
                smsSendYunPianParam.getPhoneNumbers(),
                smsSendYunPianParam.getTemplateCode(),
                smsSendYunPianParam.getTemplateParam());
        DevSms devSms = new DevSms();
        BeanUtil.copyProperties(smsSendYunPianParam, devSms);
        devSms.setEngine(DevSmsEngineTypeEnum.YUNPIAN.getValue());
        devSms.setReceiptInfo(receiptInfo);
        this.save(devSms);
    }

    @Override
    public String sendYunPianRetrun(DevSmsSendYunPianParam param) {
        validPhone(param.getPhoneNumbers());

        String phoneValidCode = null;
        if(ObjectUtil.isEmpty(param.getTemplateParam())){
            // 生成手机验证码的值，随机6为数字
            phoneValidCode = RandomUtil.randomNumbers(6);
            String tpl_Value = URLEncoder.encode("#code#") + "="  + URLEncoder.encode(phoneValidCode);
            param.setTemplateParam(tpl_Value);
        }

        String receiptInfo = DevSmsYunPianUtil.sendSms(param.getApiKey(),
                param.getPhoneNumbers(),
                param.getTemplateCode(),
                param.getTemplateParam());
        DevSms devSms = new DevSms();
        BeanUtil.copyProperties(param, devSms);
        devSms.setEngine(DevSmsEngineTypeEnum.YUNPIAN.getValue());
        devSms.setReceiptInfo(receiptInfo);
        this.save(devSms);

        JSONObject smsResult = JSONUtil.parseObj(receiptInfo);
        Integer code = smsResult.getInt("code");
        if(0 != code){
            throw new CommonException("短信发送失败，" + smsResult.getStr("detail"));
        }
        if (ObjectUtil.isNotEmpty(phoneValidCode) && 0 == code ) {
            commonCacheOperator.put(PAGE_VALID_CODE_CACHE_KEY + param.getPhoneNumbers(), phoneValidCode, 24 * 60 * 60L);
        }
        return phoneValidCode;
    }

    @Override
    public Object getPagePhoneCode(String phone) {
        return commonCacheOperator.get(PAGE_VALID_CODE_CACHE_KEY + phone);
    }


    /**
     * 校验手机格式
     *
     * @author xuyuxiang
     * @date 2022/8/15 13:32
     **/
    private void validPhone(String phones) {
        StrUtil.split(phones, StrUtil.COMMA).forEach(phone -> {
            if(!PhoneUtil.isMobile(phone)) {
                throw new CommonException("手机号码：{}格式错误", phone);
            }
        });
    }

//    @Override
//    public void sendUniPush(){
//        PushReqBean bean = new PushReqBean();
//        bean.setUser("abd531f623f5e6af71b8890c08b0306b");
//        bean.setTitle("新的测试发送88888");
//        bean.setContent("新的测试发送88888");
//    }
}
