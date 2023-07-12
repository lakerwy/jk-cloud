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
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.crm.bidopen.entity.Bidopen;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cms.article.entity.Article;
import vip.xiaonuo.biz.modular.cms.article.mapper.ArticleMapper;
import vip.xiaonuo.biz.modular.cms.article.param.ArticleAddParam;
import vip.xiaonuo.biz.modular.cms.article.param.ArticleEditParam;
import vip.xiaonuo.biz.modular.cms.article.param.ArticleIdParam;
import vip.xiaonuo.biz.modular.cms.article.param.ArticlePageParam;
import vip.xiaonuo.biz.modular.cms.article.service.ArticleService;

import java.util.List;

/**
 * 业务表-CMS-文章管理Service接口实现类
 *
 * @author yong.ye
 * @date  2023/07/10 14:36
 **/

@DS("slave_1")
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public Page<Article> page(ArticlePageParam articlePageParam) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(articlePageParam.getSearchKey())) {
            queryWrapper.lambda().like(Article::getSn, articlePageParam.getSearchKey()).or()
                    .like(Article::getTitle, articlePageParam.getSearchKey()).or()
                    .like(Article::getSubTitle, articlePageParam.getSearchKey()).or()
                    .like(Article::getTags, articlePageParam.getSearchKey()).or()
                    .like(Article::getType, articlePageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(articlePageParam.getTitle())) {
            queryWrapper.lambda().like(Article::getTitle, articlePageParam.getTitle());
        }
        if(ObjectUtil.isNotEmpty(articlePageParam.getSubTitle())) {
            queryWrapper.lambda().like(Article::getSubTitle, articlePageParam.getSubTitle());
        }
        if(ObjectUtil.isNotEmpty(articlePageParam.getType())) {
            queryWrapper.lambda().eq(Article::getType, articlePageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(articlePageParam.getTags())) {
            queryWrapper.lambda().like(Article::getTags, articlePageParam.getTags());
        }
        if(ObjectUtil.isAllNotEmpty(articlePageParam.getSortField(), articlePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(articlePageParam.getSortOrder());
            queryWrapper.orderBy(true, articlePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(articlePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Article::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ArticleAddParam articleAddParam) {
        Article article = BeanUtil.toBean(articleAddParam, Article.class);
        this.save(article);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ArticleEditParam articleEditParam) {
        Article article = this.queryEntity(articleEditParam.getId());
        BeanUtil.copyProperties(articleEditParam, article);
        this.updateById(article);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ArticleIdParam> articleIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(articleIdParamList, ArticleIdParam::getId));
    }

    @Override
    public Article detail(ArticleIdParam articleIdParam) {
        return this.queryEntity(articleIdParam.getId());
    }

    @Override
    public Article queryEntity(String id) {
        Article article = this.getById(id);
        if(ObjectUtil.isEmpty(article)) {
            throw new CommonException("业务表-CMS-文章管理不存在，id值为：{}", id);
        }
        return article;
    }

    @Override
    public void click(ArticleIdParam articleIdParam) {
        Article article = this.queryEntity(articleIdParam.getId());
        int hits = article.getHits() == null ? 0 : article.getHits();
        article.setHits(hits+1);
        this.updateById(article);
    }
}
