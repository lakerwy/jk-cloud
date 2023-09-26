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
import vip.xiaonuo.biz.modular.syncTest.entity.WebAuditListsDetails;
import vip.xiaonuo.biz.modular.syncTest.mapper.WebAuditListsDetailsMapper;
import vip.xiaonuo.biz.modular.syncTest.param.WebAuditListsDetailsAddParam;
import vip.xiaonuo.biz.modular.syncTest.param.WebAuditListsDetailsEditParam;
import vip.xiaonuo.biz.modular.syncTest.param.WebAuditListsDetailsIdParam;
import vip.xiaonuo.biz.modular.syncTest.param.WebAuditListsDetailsPageParam;
import vip.xiaonuo.biz.modular.syncTest.service.WebAuditListsDetailsService;

import java.util.List;

/**
 * 业务表-设计校审记录表Service接口实现类
 *
 * @author mayike
 * @date  2023/08/16 09:24
 **/
@Service
public class WebAuditListsDetailsServiceImpl extends ServiceImpl<WebAuditListsDetailsMapper, WebAuditListsDetails> implements WebAuditListsDetailsService {

    @Override
    public Page<WebAuditListsDetails> page(WebAuditListsDetailsPageParam webAuditListsDetailsPageParam) {
        QueryWrapper<WebAuditListsDetails> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(webAuditListsDetailsPageParam.getSortField(), webAuditListsDetailsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(webAuditListsDetailsPageParam.getSortOrder());
            queryWrapper.orderBy(true, webAuditListsDetailsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(webAuditListsDetailsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(WebAuditListsDetails::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(WebAuditListsDetailsAddParam webAuditListsDetailsAddParam) {
        WebAuditListsDetails webAuditListsDetails = BeanUtil.toBean(webAuditListsDetailsAddParam, WebAuditListsDetails.class);
        this.save(webAuditListsDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(WebAuditListsDetailsEditParam webAuditListsDetailsEditParam) {
        WebAuditListsDetails webAuditListsDetails = this.queryEntity(webAuditListsDetailsEditParam.getId());
        BeanUtil.copyProperties(webAuditListsDetailsEditParam, webAuditListsDetails);
        this.updateById(webAuditListsDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<WebAuditListsDetailsIdParam> webAuditListsDetailsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(webAuditListsDetailsIdParamList, WebAuditListsDetailsIdParam::getId));
    }

    @Override
    public WebAuditListsDetails detail(WebAuditListsDetailsIdParam webAuditListsDetailsIdParam) {
        return this.queryEntity(webAuditListsDetailsIdParam.getId());
    }

    @Override
    public WebAuditListsDetails queryEntity(String id) {
        WebAuditListsDetails webAuditListsDetails = this.getById(id);
        if(ObjectUtil.isEmpty(webAuditListsDetails)) {
            throw new CommonException("业务表-设计校审记录表不存在，id值为：{}", id);
        }
        return webAuditListsDetails;
    }
}
