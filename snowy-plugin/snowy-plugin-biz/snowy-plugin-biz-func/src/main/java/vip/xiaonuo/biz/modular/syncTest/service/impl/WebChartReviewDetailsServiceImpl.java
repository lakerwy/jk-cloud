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
package vip.xiaonuo.biz.modular.syncTest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.syncTest.entity.WebChartReviewDetails;
import vip.xiaonuo.biz.modular.syncTest.mapper.WebChartReviewDetailsMapper;
import vip.xiaonuo.biz.modular.syncTest.param.WebChartReviewDetailsAddParam;
import vip.xiaonuo.biz.modular.syncTest.param.WebChartReviewDetailsEditParam;
import vip.xiaonuo.biz.modular.syncTest.param.WebChartReviewDetailsIdParam;
import vip.xiaonuo.biz.modular.syncTest.param.WebChartReviewDetailsPageParam;
import vip.xiaonuo.biz.modular.syncTest.service.WebChartReviewDetailsService;

import java.util.List;

/**
 * 业务表-图审详情记录表Service接口实现类
 *
 * @author mayike
 * @date  2023/08/16 09:30
 **/
@Service
public class WebChartReviewDetailsServiceImpl extends ServiceImpl<WebChartReviewDetailsMapper, WebChartReviewDetails> implements WebChartReviewDetailsService {

    @Override
    public Page<WebChartReviewDetails> page(WebChartReviewDetailsPageParam webChartReviewDetailsPageParam) {
        QueryWrapper<WebChartReviewDetails> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(webChartReviewDetailsPageParam.getSortField(), webChartReviewDetailsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(webChartReviewDetailsPageParam.getSortOrder());
            queryWrapper.orderBy(true, webChartReviewDetailsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(webChartReviewDetailsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(WebChartReviewDetails::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(WebChartReviewDetailsAddParam webChartReviewDetailsAddParam) {
        WebChartReviewDetails webChartReviewDetails = BeanUtil.toBean(webChartReviewDetailsAddParam, WebChartReviewDetails.class);
        this.save(webChartReviewDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(WebChartReviewDetailsEditParam webChartReviewDetailsEditParam) {
        WebChartReviewDetails webChartReviewDetails = this.queryEntity(webChartReviewDetailsEditParam.getId());
        BeanUtil.copyProperties(webChartReviewDetailsEditParam, webChartReviewDetails);
        this.updateById(webChartReviewDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<WebChartReviewDetailsIdParam> webChartReviewDetailsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(webChartReviewDetailsIdParamList, WebChartReviewDetailsIdParam::getId));
    }

    @Override
    public WebChartReviewDetails detail(WebChartReviewDetailsIdParam webChartReviewDetailsIdParam) {
        return this.queryEntity(webChartReviewDetailsIdParam.getId());
    }

    @Override
    public WebChartReviewDetails queryEntity(String id) {
        WebChartReviewDetails webChartReviewDetails = this.getById(id);
        if(ObjectUtil.isEmpty(webChartReviewDetails)) {
            throw new CommonException("业务表-图审详情记录表不存在，id值为：{}", id);
        }
        return webChartReviewDetails;
    }
}
