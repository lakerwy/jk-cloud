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
package vip.xiaonuo.dev.modular.file.service.impl;

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
import vip.xiaonuo.dev.modular.file.entity.DevFileUnzip;
import vip.xiaonuo.dev.modular.file.mapper.DevFileUnzipMapper;
import vip.xiaonuo.dev.modular.file.param.DevFileUnzipAddParam;
import vip.xiaonuo.dev.modular.file.param.DevFileUnzipEditParam;
import vip.xiaonuo.dev.modular.file.param.DevFileUnzipIdParam;
import vip.xiaonuo.dev.modular.file.param.DevFileUnzipPageParam;
import vip.xiaonuo.dev.modular.file.service.DevFileUnzipService;

import java.util.List;

/**
 * 文件-附件压缩包解压列表Service接口实现类
 *
 * @author ljy
 * @date  2023/12/24 20:09
 **/
@Service
public class DevFileUnzipServiceImpl extends ServiceImpl<DevFileUnzipMapper, DevFileUnzip> implements DevFileUnzipService {

    @Override
    public Page<DevFileUnzip> page(DevFileUnzipPageParam devFileUnzipPageParam) {
        QueryWrapper<DevFileUnzip> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(devFileUnzipPageParam.getSortField(), devFileUnzipPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(devFileUnzipPageParam.getSortOrder());
            queryWrapper.orderBy(true, devFileUnzipPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(devFileUnzipPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(DevFileUnzip::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(DevFileUnzipAddParam devFileUnzipAddParam) {
        DevFileUnzip devFileUnzip = BeanUtil.toBean(devFileUnzipAddParam, DevFileUnzip.class);
        this.save(devFileUnzip);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(DevFileUnzipEditParam devFileUnzipEditParam) {
        DevFileUnzip devFileUnzip = this.queryEntity(devFileUnzipEditParam.getId());
        BeanUtil.copyProperties(devFileUnzipEditParam, devFileUnzip);
        this.updateById(devFileUnzip);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<DevFileUnzipIdParam> devFileUnzipIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(devFileUnzipIdParamList, DevFileUnzipIdParam::getId));
    }

    @Override
    public DevFileUnzip detail(DevFileUnzipIdParam devFileUnzipIdParam) {
        return this.queryEntity(devFileUnzipIdParam.getId());
    }

    @Override
    public DevFileUnzip queryEntity(String id) {
        DevFileUnzip devFileUnzip = this.getById(id);
        if(ObjectUtil.isEmpty(devFileUnzip)) {
            throw new CommonException("文件-附件压缩包解压列表不存在，id值为：{}", id);
        }
        return devFileUnzip;
    }
}
