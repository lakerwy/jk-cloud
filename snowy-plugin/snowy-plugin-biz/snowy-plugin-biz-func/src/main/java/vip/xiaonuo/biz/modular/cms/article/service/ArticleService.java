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
package vip.xiaonuo.biz.modular.cms.article.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.cms.article.entity.Article;
import vip.xiaonuo.biz.modular.cms.article.param.ArticleAddParam;
import vip.xiaonuo.biz.modular.cms.article.param.ArticleEditParam;
import vip.xiaonuo.biz.modular.cms.article.param.ArticleIdParam;
import vip.xiaonuo.biz.modular.cms.article.param.ArticlePageParam;

import java.util.List;

/**
 * 业务表-CMS-文章管理Service接口
 *
 * @author yong.ye
 * @date  2023/07/10 14:36
 **/
public interface ArticleService extends IService<Article> {

    /**
     * 获取业务表-CMS-文章管理分页
     *
     * @author yong.ye
     * @date  2023/07/10 14:36
     */
    Page<Article> page(ArticlePageParam articlePageParam);

    /**
     * 添加业务表-CMS-文章管理
     *
     * @author yong.ye
     * @date  2023/07/10 14:36
     */
    void add(ArticleAddParam articleAddParam);

    /**
     * 编辑业务表-CMS-文章管理
     *
     * @author yong.ye
     * @date  2023/07/10 14:36
     */
    void edit(ArticleEditParam articleEditParam);

    /**
     * 删除业务表-CMS-文章管理
     *
     * @author yong.ye
     * @date  2023/07/10 14:36
     */
    void delete(List<ArticleIdParam> articleIdParamList);

    /**
     * 获取业务表-CMS-文章管理详情
     *
     * @author yong.ye
     * @date  2023/07/10 14:36
     */
    Article detail(ArticleIdParam articleIdParam);

    /**
     * 获取业务表-CMS-文章管理详情
     *
     * @author yong.ye
     * @date  2023/07/10 14:36
     **/
    Article queryEntity(String id);

    /**
     * 点击获取业务表-CMS-文章（点击量）
     *
     * @author yong.ye
     * @date  2023/07/10 14:36
     */
    void click(ArticleIdParam articleIdParam);
}
