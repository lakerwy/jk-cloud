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
package vip.xiaonuo.biz.modular.cms.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.cms.article.entity.ArticleAuth;
import vip.xiaonuo.biz.modular.cms.article.mapper.ArticleAuthMapper;
import vip.xiaonuo.biz.modular.cms.article.param.ArticleAuthAddParam;
import vip.xiaonuo.biz.modular.cms.article.param.ArticleAuthEditParam;
import vip.xiaonuo.biz.modular.cms.article.param.ArticleAuthIdParam;
import vip.xiaonuo.biz.modular.cms.article.param.ArticleAuthPageParam;
import vip.xiaonuo.biz.modular.cms.article.service.ArticleAuthService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * 业务表-CMS-文章管理-权限配置Service接口实现类
 *
 * @author yong.ye
 * @date  2023/07/12 16:25
 **/
@Service
public class ArticleAuthServiceImpl extends ServiceImpl<ArticleAuthMapper, ArticleAuth> implements ArticleAuthService {

    @Override
    public Page<ArticleAuth> page(ArticleAuthPageParam articleAuthPageParam) {
        QueryWrapper<ArticleAuth> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(articleAuthPageParam.getType())) {
            queryWrapper.lambda().eq(ArticleAuth::getType, articleAuthPageParam.getType());
        }
        if(ObjectUtil.isAllNotEmpty(articleAuthPageParam.getSortField(), articleAuthPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(articleAuthPageParam.getSortOrder());
            queryWrapper.orderBy(true, articleAuthPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(articleAuthPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(ArticleAuth::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ArticleAuthAddParam articleAuthAddParam) {
        ArticleAuth articleAuth = BeanUtil.toBean(articleAuthAddParam, ArticleAuth.class);
        this.save(articleAuth);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ArticleAuthEditParam articleAuthEditParam) {
        ArticleAuth articleAuth = this.queryEntity(articleAuthEditParam.getId());
        BeanUtil.copyProperties(articleAuthEditParam, articleAuth);
        this.updateById(articleAuth);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ArticleAuthIdParam> articleAuthIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(articleAuthIdParamList, ArticleAuthIdParam::getId));
    }

    @Override
    public ArticleAuth detail(ArticleAuthIdParam articleAuthIdParam) {
        return this.queryEntity(articleAuthIdParam.getId());
    }

    @Override
    public ArticleAuth queryEntity(String id) {
        ArticleAuth articleAuth = this.getById(id);
        if(ObjectUtil.isEmpty(articleAuth)) {
            throw new CommonException("业务表-CMS-文章管理-权限配置不存在，id值为：{}", id);
        }
        return articleAuth;
    }
}
