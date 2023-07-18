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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.cms.article.vo.ArticleInfoVo;
import vip.xiaonuo.biz.modular.cms.article.entity.ArticleAuth;
import vip.xiaonuo.biz.modular.cms.article.param.*;
import vip.xiaonuo.biz.modular.cms.article.service.ArticleAuthService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cms.article.entity.Article;
import vip.xiaonuo.biz.modular.cms.article.mapper.ArticleMapper;
import vip.xiaonuo.biz.modular.cms.article.service.ArticleService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 业务表-CMS-文章管理Service接口实现类
 *
 * @author yong.ye
 * @date  2023/07/10 14:36
 **/

@DS("slave_1")
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private ArticleAuthService articleAuthService;
    @Resource
    private ArticleMapper articleMapper;
    @Override
    public Page<Article> page(ArticlePageParam articlePageParam) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        /* 外部查询 */
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
        if(ObjectUtil.isNotEmpty(articlePageParam.getIsAuth())) {
            queryWrapper.lambda().eq(Article::getIsAuth, articlePageParam.getIsAuth());
        }
        if(ObjectUtil.isNotEmpty(articlePageParam.getIsShelf())) {
            queryWrapper.lambda().eq(Article::getIsShelf, articlePageParam.getIsShelf());
        }
        if(ObjectUtil.isNotEmpty(articlePageParam.getIsTop())) {
            queryWrapper.lambda().eq(Article::getIsTop, articlePageParam.getIsTop());
        }
        if(ObjectUtil.isAllNotEmpty(articlePageParam.getSortField(), articlePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(articlePageParam.getSortOrder());
            queryWrapper.orderBy(true, articlePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(articlePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Article::getIsTop).orderByDesc(Article::getTopTime).orderByDesc(Article::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ArticleAddParam articleAddParam) {
        Article article = BeanUtil.toBean(articleAddParam, Article.class);
        this.save(article);
        //新增对应关系
        List<ArticleAuthAddParam> articleAuthAddParams = articleAddParam.getArticleAuths();
        if(CollUtil.isNotEmpty(articleAuthAddParams)){
            List<ArticleAuth> articleAuths = articleAuthAddParams.stream().map(articleAuthAddParam -> BeanUtil.toBean(articleAuthAddParam, ArticleAuth.class)).collect(Collectors.toList());
            articleAuths.forEach(articleAuth -> articleAuth.setArticleId(article.getId()));
            articleAuthService.saveBatch(articleAuths);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ArticleEditParam articleEditParam) {
        Article article = this.queryEntity(articleEditParam.getId());
        BeanUtil.copyProperties(articleEditParam, article);
        this.updateById(article);

        //处理 文章权限
        List<ArticleAuth> articleAuths = articleEditParam.getArticleAuths();
        if(CollUtil.isNotEmpty(articleAuths)){
            //查询 数据库 获取历史设置
            QueryWrapper<ArticleAuth> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(ArticleAuth::getArticleId, article.getId());
            List<ArticleAuth> list = articleAuthService.list(wrapper);
            //处理数据，赋予外键
            articleAuths.forEach(articleAuth->{ articleAuth.setArticleId(article.getId());  });
            //主键暂存区（已设置的数据库值）
            Set<String> ids = new HashSet<>();
            if(CollUtil.isNotEmpty(list)){
                ids = list.stream().map(ArticleAuth::getId).collect(Collectors.toSet());
            }
            //逻辑（新增+更新+删除）
            List<ArticleAuth> updateArticleAuth = new ArrayList<>();
            updateArticleAuth = articleAuths.stream().filter(articleAuth -> StrUtil.isNotBlank(articleAuth.getId())).collect(Collectors.toList());
            //排除update的id
            Set<String> collect = updateArticleAuth.stream().map(ArticleAuth::getId).collect(Collectors.toSet());
            ids.removeAll(collect);
            articleAuths.removeAll(updateArticleAuth);
            //数据更新处理逻辑
            if(CollUtil.isNotEmpty(updateArticleAuth)){
                articleAuthService.updateBatchById(updateArticleAuth);
            }
            if(CollUtil.isNotEmpty(articleAuths)){
                articleAuthService.saveBatch(articleAuths);
            }
            if(CollUtil.isNotEmpty(ids)){
                articleAuthService.removeByIds(ids);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ArticleIdParam> articleIdParamList) {
        // 执行删除
        List<String> list = CollStreamUtil.toList(articleIdParamList, ArticleIdParam::getId);
        this.removeByIds(list);
        QueryWrapper<ArticleAuth> wrapper = new QueryWrapper<>();
        wrapper.in("ARTICLE_ID",list);
        articleAuthService.remove(wrapper);
    }

    @Override
    public ArticleInfoVo detail(ArticleIdParam articleIdParam) {
        ArticleInfoVo articleInfoVo = new ArticleInfoVo();
        Article article = this.queryEntity(articleIdParam.getId());
        BeanUtil.copyProperties(article, articleInfoVo);
        List<ArticleAuth> articleAuthList = articleAuthService.list(new QueryWrapper<ArticleAuth>().lambda().eq(ArticleAuth::getArticleId,article.getId()));
        articleInfoVo.setArticleAuths(articleAuthList);
        return articleInfoVo;
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

    @Override
    public IPage<Article> listPage(ArticlePageParam articlePageParam) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(articlePageParam.getSearchKey())) {
            queryWrapper.like("a.SN", articlePageParam.getSearchKey()).or()
                    .like("a.TITLE", articlePageParam.getSearchKey()).or()
                    .like("a.SUB_TITLE", articlePageParam.getSearchKey()).or()
                    .like("a.TAGS", articlePageParam.getSearchKey()).or()
                    .like("a.TYPE", articlePageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(articlePageParam.getTitle())) {
            queryWrapper.like("a.TITLE", articlePageParam.getTitle());
        }
        if(ObjectUtil.isNotEmpty(articlePageParam.getSubTitle())) {
            queryWrapper.like("a.SUB_TITLE", articlePageParam.getSubTitle());
        }
        if(ObjectUtil.isNotEmpty(articlePageParam.getType())) {
            queryWrapper.eq("a.TYPE", articlePageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(articlePageParam.getTags())) {
            queryWrapper.like("a.TAGS", articlePageParam.getTags());
        }
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("a.IS_SHELF",1);
        queryWrapper.le("a.SHELF_TIME", new Date() );
        if(CollUtil.isNotEmpty(articlePageParam.getListIds())) {
            queryWrapper.and(q -> q.eq("a.IS_AUTH", 0).or(q1 -> q1.in("b.OBJ_ID", articlePageParam.getListIds())));
        }else{
            queryWrapper.eq("a.IS_AUTH",0);
        }
        if(ObjectUtil.isAllNotEmpty(articlePageParam.getSortField(), articlePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(articlePageParam.getSortOrder());
            queryWrapper.orderBy(true, articlePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(articlePageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.IS_TOP").orderByDesc("a.TOP_TIME").orderByDesc("a.CREATE_TIME");
        }

        return articleMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public void shelf(ArticleShelfParam articleShelfParam) {
        Article article = this.queryEntity(articleShelfParam.getId());
        article.setIsShelf(articleShelfParam.getIsShelf());
        this.updateById(article);
    }

    @Override
    public void top(ArticleTopParam articleTopParam) {
        Article article = this.queryEntity(articleTopParam.getId());
        //置顶
        if(articleTopParam.getIsTop() == 1){
            List<Article> articleList = this.list(new QueryWrapper<Article>().lambda().eq(Article::getIsTop,1));
            //置顶只能同时存在一个
            if(CollUtil.isNotEmpty(articleList)){
                articleList.forEach(r -> { r.setIsTop(0); r.setTopTime(null); }); //取消置顶
                this.updateBatchById(articleList);
            }
            //设置当前置顶
            article.setIsTop(articleTopParam.getIsTop());
            article.setTopTime(new Date());
            this.updateById(article);
        }
        //取消置顶
        else if(articleTopParam.getIsTop() == 0){
            article.setIsTop(articleTopParam.getIsTop());
            article.setTopTime(null);
            this.updateById(article);
        }else{
            throw new CommonException("业务表-CMS-文章管理(置顶|取消置顶)，操作类型异常");
        }
    }
}
