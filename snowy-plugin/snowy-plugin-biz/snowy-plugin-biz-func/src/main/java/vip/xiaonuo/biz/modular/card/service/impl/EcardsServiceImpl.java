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
package vip.xiaonuo.biz.modular.card.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.card.config.AppletCardProperties;
import vip.xiaonuo.biz.modular.card.entity.CardImages;
import vip.xiaonuo.biz.modular.card.entity.CardQrCode;
import vip.xiaonuo.biz.modular.card.param.*;
import vip.xiaonuo.biz.modular.card.service.CardImagesService;
import vip.xiaonuo.biz.modular.card.service.CardQrCodeService;
import vip.xiaonuo.biz.modular.card.vo.EcardVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.card.entity.Ecards;
import vip.xiaonuo.biz.modular.card.mapper.EcardsMapper;
import vip.xiaonuo.biz.modular.card.service.EcardsService;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

/**
 * Ecard-电子名片Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/13 11:22
 **/

@DS("slave_1")
@Service
public class EcardsServiceImpl extends ServiceImpl<EcardsMapper, Ecards> implements EcardsService {

    @Resource
    private AppletCardProperties appletCardProperties;

    @Resource
    private CardImagesService cardImagesService;

    @Resource
    private CardQrCodeService cardQrCodeService;

    @Override
    public Page<Ecards> page(EcardsPageParam ecardsPageParam) {
        QueryWrapper<Ecards> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(ecardsPageParam.getUserName())) {
            queryWrapper.lambda().like(Ecards::getUserName, ecardsPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(ecardsPageParam.getOpenId())) {
            queryWrapper.lambda().like(Ecards::getOpenId, ecardsPageParam.getOpenId());
        }
        if(ObjectUtil.isNotEmpty(ecardsPageParam.getPhone())) {
            queryWrapper.lambda().like(Ecards::getPhone, ecardsPageParam.getPhone());
        }
        if(ObjectUtil.isNotEmpty(ecardsPageParam.getEmail())) {
            queryWrapper.lambda().like(Ecards::getEmail, ecardsPageParam.getEmail());
        }
        if(ObjectUtil.isNotEmpty(ecardsPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(Ecards::getUserName,ecardsPageParam.getSearchKey())
                    .or().like(Ecards::getPhone,ecardsPageParam.getSearchKey())
                    .or().like(Ecards::getEmail,ecardsPageParam.getSearchKey())
                    .or().like(Ecards::getProHonors,ecardsPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(ecardsPageParam.getSortField(), ecardsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(ecardsPageParam.getSortOrder());
            queryWrapper.orderBy(true, ecardsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(ecardsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Ecards::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EcardsAddParam ecardsAddParam) {
        List<Ecards> ecardsList = this.list(new QueryWrapper<Ecards>().lambda().eq(Ecards::getUserId,ecardsAddParam.getUserId()));
        if(CollUtil.isNotEmpty(ecardsList)){
            throw new CommonException("当前用户已存在电子名片");
        }
        Ecards ecards = BeanUtil.toBean(ecardsAddParam, Ecards.class);
        this.save(ecards);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EcardsEditParam ecardsEditParam) {
        Ecards ecards = this.queryEntity(ecardsEditParam.getCardId());
        BeanUtil.copyProperties(ecardsEditParam, ecards);
        this.updateById(ecards);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EcardsIdParam> ecardsIdParamList) {
        // 执行删除
        List<String> cardIds = CollStreamUtil.toList(ecardsIdParamList, EcardsIdParam::getCardId);
        this.removeByIds(cardIds);
        //删除电子名片，同时删除该用户的私有图片资源
        if(CollUtil.isNotEmpty(cardIds)){
            cardImagesService.remove(new QueryWrapper<CardImages>().lambda().in(CardImages::getCardId,cardIds));
        }
    }

    @Override
    public Ecards detail(EcardsIdParam ecardsIdParam) {
        return this.queryEntity(ecardsIdParam.getCardId());
    }

    @Override
    public Ecards queryEntity(String id) {
        Ecards ecards = this.getById(id);
        if(ObjectUtil.isEmpty(ecards)) {
            throw new CommonException("Ecard-电子名片不存在，id值为：{}", id);
        }
        return ecards;
    }

    @Override
    public EcardVo detailByUserId(String userId) {
        QueryWrapper<Ecards> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(q->q.eq(Ecards::getUserId,userId).or().eq(Ecards::getOldUserId,userId));
        queryWrapper.last("limit 1");
        Ecards ecards = this.getOne(queryWrapper);
        EcardVo ecardVo = BeanUtil.copyProperties(ecards,EcardVo.class);
        if(ObjectUtil.isNotEmpty(ecards)){
            ecardVo.setImages(cardImagesService.getImagesById(ecards.getCardId()));
        }
        return ecardVo;
    }

    @Override
    public EcardVo detailByOpenId(String openId) {
        QueryWrapper<Ecards> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Ecards::getOpenId,openId);
        queryWrapper.last("limit 1");
        Ecards ecards = this.getOne(queryWrapper);
        EcardVo ecardVo = BeanUtil.copyProperties(ecards,EcardVo.class);
        if(ObjectUtil.isNotEmpty(ecards)){
            ecardVo.setImages(cardImagesService.getImagesById(ecards.getCardId()));
        }
        return ecardVo;
    }

    @Override
    public JSONObject openIdByCode(String code) {
        if(ObjectUtil.isEmpty(appletCardProperties.getAppId()) || ObjectUtil.isEmpty(appletCardProperties.getAppSecret())){
            throw new CommonException("电子名片小程序应用参数错误");
        }
        //此处填写自己小程序的appid和secret
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appletCardProperties.getAppId() + "&secret=" + appletCardProperties.getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";
        //请求get
        try {
            // 创建HttpClient实例
            HttpClient httpClient = HttpClientBuilder.create().build();
            // 创建HttpGet实例
            HttpGet httpGet = new HttpGet(url);
            // 执行请求并获取响应
            HttpResponse httpResponse = httpClient.execute(httpGet);
            // 从响应中获取状态码和响应体
            String responseBody = EntityUtils.toString(httpResponse.getEntity());
            // 输出状态码和响应体
            return JSONUtil.parseObj(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public EcardVo getByCardId(String cardId) {
        Ecards ecards = this.queryEntity(cardId);
        EcardVo ecardVo = BeanUtil.copyProperties(ecards,EcardVo.class);
        ecardVo.setImages(cardImagesService.getImagesById(cardId));
        return ecardVo;
    }

    @Override
    public String getAccessToken() {
        if(ObjectUtil.isEmpty(appletCardProperties.getAppId()) || ObjectUtil.isEmpty(appletCardProperties.getAppSecret())){
            throw new CommonException("电子名片小程序应用参数错误");
        }
        //此处填写自己小程序的appid和secret
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appletCardProperties.getAppId() + "&secret=" + appletCardProperties.getAppSecret();
        //请求get
        try {
            // 创建HttpClient实例
            HttpClient httpClient = HttpClientBuilder.create().build();
            // 创建HttpGet实例
            HttpGet httpGet = new HttpGet(url);
            // 执行请求并获取响应
            HttpResponse httpResponse = httpClient.execute(httpGet);
            // 从响应中获取状态码和响应体
            String responseBody = EntityUtils.toString(httpResponse.getEntity());
            // 输出状态码和响应体
            JSONObject objResult = JSONUtil.parseObj(responseBody);
            return objResult.getStr("access_token");
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean createQRCode(QRCodeParam qrCodeParam) {
        //0. 判断是否已开通（二维码）
        List<CardQrCode> qrCodeList = cardQrCodeService.list(new QueryWrapper<CardQrCode>().lambda().eq(CardQrCode::getUserId,qrCodeParam.getUserId()));
        if(CollUtil.isNotEmpty(qrCodeList)){
            throw new CommonException("已开通过电子名片");
        }
        //1. 获取接口调用凭证
        String accessToken = getAccessToken();
        if(ObjectUtil.isEmpty(accessToken)){
            throw new CommonException("获取AccessToken失败");
        }
        //2. post 请求生成二维码
        String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token="+accessToken;
        try{
            // 创建HttpClient实例
            HttpClient httpClient = HttpClientBuilder.create().build();
            // 创建HttpGet实例
            HttpPost httpPost = new HttpPost(url);
            // 设置请求接口参数
            JSONObject json = new JSONObject();
            json.put("path",  "pages/info/info?userId=" + qrCodeParam.getUserId());   // 参数
            StringEntity params = new StringEntity(json.toString());
            httpPost.setEntity(params);
            // 执行请求并获取响应
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            // 从响应中获取状态码和响应体  接口成功，处理buffer
            String contentType = httpEntity.getContentType().getValue();
            if(contentType.contains("image")) {
                byte[] bytes = EntityUtils.toByteArray(httpEntity);
                //转base64
                String qrCodeBase64 = URLUtil.getDataUri("image/jpg", "base64", Base64.getEncoder().encodeToString(bytes));
                CardQrCode cardQrCode = new CardQrCode();
                cardQrCode.setUserId(qrCodeParam.getUserId());
                cardQrCode.setUserName(qrCodeParam.getUserName());
                cardQrCode.setDeptName(qrCodeParam.getDeptName());
                cardQrCode.setQrCode(qrCodeBase64);
                cardQrCodeService.save(cardQrCode);

                //开通时，若不存在ECard记录，直接生成ECard记录
                List<Ecards> ecardsList = this.list(new QueryWrapper<Ecards>().lambda().and(q->q.eq(Ecards::getUserId,qrCodeParam.getUserId()).or().eq(Ecards::getOldUserId,qrCodeParam.getUserId())));
                if(CollUtil.isEmpty(ecardsList)){
                    Ecards eCardsAdd = new Ecards();
                    eCardsAdd.setUserId(qrCodeParam.getUserId());
                    eCardsAdd.setPhone(qrCodeParam.getPhone());
                    eCardsAdd.setUserName(qrCodeParam.getUserName());
                    this.save(eCardsAdd);
                }
                return true;
            }else{ //生成失败
                throw new CommonException("二维码生成失败");
            }
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    /**
     * 输入流转字节流
     * */
    private byte[] InputStreamToByte(InputStream in) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int ch;
        while ((ch = in.read(buffer)) != -1) {
            bytestream.write(buffer,0,ch);
        }
        byte data[] = bytestream.toByteArray();
        bytestream.close();
        return data;
    }
}
