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
import vip.xiaonuo.bp.modular.platform.entity.BpUserNotice;
import vip.xiaonuo.bp.modular.platform.mapper.BpUserNoticeMapper;
import vip.xiaonuo.bp.modular.platform.param.userBm.BpUserNoticeAddParam;
import vip.xiaonuo.bp.modular.platform.param.userBm.BpUserNoticeEditParam;
import vip.xiaonuo.bp.modular.platform.param.userBm.BpUserNoticeIdParam;
import vip.xiaonuo.bp.modular.platform.param.userBm.BpUserNoticePageParam;
import vip.xiaonuo.bp.modular.platform.service.BpUserNoticeService;

import java.util.List;

/**
 * 招采平台 - 用户 - （招采项目）报名信息表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/05/09 16:24
 **/
@Service
public class BpUserNoticeServiceImpl extends ServiceImpl<BpUserNoticeMapper, BpUserNotice> implements BpUserNoticeService {

    @Override
    public Page<BpUserNotice> page(BpUserNoticePageParam bpUserNoticePageParam) {
        QueryWrapper<BpUserNotice> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bpUserNoticePageParam.getSortField(), bpUserNoticePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bpUserNoticePageParam.getSortOrder());
            queryWrapper.orderBy(true, bpUserNoticePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bpUserNoticePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BpUserNotice::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BpUserNoticeAddParam bpUserNoticeAddParam) {
        BpUserNotice bpUserNotice = BeanUtil.toBean(bpUserNoticeAddParam, BpUserNotice.class);
        this.save(bpUserNotice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BpUserNoticeEditParam bpUserNoticeEditParam) {
        BpUserNotice bpUserNotice = this.queryEntity(bpUserNoticeEditParam.getId());
        BeanUtil.copyProperties(bpUserNoticeEditParam, bpUserNotice);
        this.updateById(bpUserNotice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BpUserNoticeIdParam> bpUserNoticeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bpUserNoticeIdParamList, BpUserNoticeIdParam::getId));
    }

    @Override
    public BpUserNotice detail(BpUserNoticeIdParam bpUserNoticeIdParam) {
        return this.queryEntity(bpUserNoticeIdParam.getId());
    }

    @Override
    public BpUserNotice queryEntity(String id) {
        BpUserNotice bpUserNotice = this.getById(id);
        if(ObjectUtil.isEmpty(bpUserNotice)) {
            throw new CommonException("招采平台 - 用户 - （招采项目）报名信息表不存在，id值为：{}", id);
        }
        return bpUserNotice;
    }
}
