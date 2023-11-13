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
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.card.config.AppletCardProperties;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.card.entity.Ecards;
import vip.xiaonuo.biz.modular.card.mapper.EcardsMapper;
import vip.xiaonuo.biz.modular.card.param.EcardsAddParam;
import vip.xiaonuo.biz.modular.card.param.EcardsEditParam;
import vip.xiaonuo.biz.modular.card.param.EcardsIdParam;
import vip.xiaonuo.biz.modular.card.param.EcardsPageParam;
import vip.xiaonuo.biz.modular.card.service.EcardsService;

import javax.annotation.Resource;
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
        this.removeByIds(CollStreamUtil.toList(ecardsIdParamList, EcardsIdParam::getCardId));
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
    public Ecards detailByUserId(String userId) {
        QueryWrapper<Ecards> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(q->q.eq(Ecards::getUserId,userId).or().eq(Ecards::getOldUserId,userId));
        queryWrapper.last("limit 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public Ecards detailByOpenId(String openId) {
        QueryWrapper<Ecards> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Ecards::getOpenId,openId);
        queryWrapper.last("limit 1");
        return this.getOne(queryWrapper);
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
}
