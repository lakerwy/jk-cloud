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
package vip.xiaonuo.sys.modular.hr.magic.magicConfig.service.impl;

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
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.entity.HrMagicConfig;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.mapper.HrMagicConfigMapper;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.param.HrMagicConfigAddParam;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.param.HrMagicConfigEditParam;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.param.HrMagicConfigIdParam;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.param.HrMagicConfigPageParam;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.service.HrMagicConfigService;

import java.util.List;

/**
 * 魔点请求参数配置表Service接口实现类
 *
 * @author ljy
 * @date  2023/10/18 16:44
 **/
@Service
public class HrMagicConfigServiceImpl extends ServiceImpl<HrMagicConfigMapper, HrMagicConfig> implements HrMagicConfigService {

    @Override
    public Page<HrMagicConfig> page(HrMagicConfigPageParam hrMagicConfigPageParam) {
        QueryWrapper<HrMagicConfig> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(hrMagicConfigPageParam.getSortField(), hrMagicConfigPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrMagicConfigPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrMagicConfigPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrMagicConfigPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrMagicConfig::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrMagicConfigAddParam hrMagicConfigAddParam) {
        HrMagicConfig hrMagicConfig = BeanUtil.toBean(hrMagicConfigAddParam, HrMagicConfig.class);
        this.save(hrMagicConfig);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrMagicConfigEditParam hrMagicConfigEditParam) {
        HrMagicConfig hrMagicConfig = this.queryEntity(hrMagicConfigEditParam.getId());
        BeanUtil.copyProperties(hrMagicConfigEditParam, hrMagicConfig);
        this.updateById(hrMagicConfig);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrMagicConfigIdParam> hrMagicConfigIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrMagicConfigIdParamList, HrMagicConfigIdParam::getId));
    }

    @Override
    public HrMagicConfig detail(HrMagicConfigIdParam hrMagicConfigIdParam) {
        return this.queryEntity(hrMagicConfigIdParam.getId());
    }

    @Override
    public HrMagicConfig queryEntity(String id) {
        HrMagicConfig hrMagicConfig = this.getById(id);
        if(ObjectUtil.isEmpty(hrMagicConfig)) {
            throw new CommonException("魔点请求参数配置表不存在，id值为：{}", id);
        }
        return hrMagicConfig;
    }
}
