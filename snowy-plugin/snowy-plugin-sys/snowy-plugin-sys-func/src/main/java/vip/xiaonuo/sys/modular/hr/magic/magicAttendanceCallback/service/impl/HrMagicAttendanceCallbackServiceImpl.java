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
package vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.service.impl;

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
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.entity.HrMagicAttendanceCallback;
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.mapper.HrMagicAttendanceCallbackMapper;
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.param.HrMagicAttendanceCallbackAddParam;
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.param.HrMagicAttendanceCallbackEditParam;
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.param.HrMagicAttendanceCallbackIdParam;
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.param.HrMagicAttendanceCallbackPageParam;
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.service.HrMagicAttendanceCallbackService;

import java.util.List;

/**
 * 魔点打卡回调数据表Service接口实现类
 *
 * @author ljy
 * @date  2023/10/20 10:40
 **/
@Service
public class HrMagicAttendanceCallbackServiceImpl extends ServiceImpl<HrMagicAttendanceCallbackMapper, HrMagicAttendanceCallback> implements HrMagicAttendanceCallbackService {

    @Override
    public Page<HrMagicAttendanceCallback> page(HrMagicAttendanceCallbackPageParam hrMagicAttendanceCallbackPageParam) {
        QueryWrapper<HrMagicAttendanceCallback> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(hrMagicAttendanceCallbackPageParam.getSortField(), hrMagicAttendanceCallbackPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrMagicAttendanceCallbackPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrMagicAttendanceCallbackPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrMagicAttendanceCallbackPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrMagicAttendanceCallback::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrMagicAttendanceCallbackAddParam hrMagicAttendanceCallbackAddParam) {
        HrMagicAttendanceCallback hrMagicAttendanceCallback = BeanUtil.toBean(hrMagicAttendanceCallbackAddParam, HrMagicAttendanceCallback.class);
        this.save(hrMagicAttendanceCallback);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrMagicAttendanceCallbackEditParam hrMagicAttendanceCallbackEditParam) {
        HrMagicAttendanceCallback hrMagicAttendanceCallback = this.queryEntity(hrMagicAttendanceCallbackEditParam.getId());
        BeanUtil.copyProperties(hrMagicAttendanceCallbackEditParam, hrMagicAttendanceCallback);
        this.updateById(hrMagicAttendanceCallback);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrMagicAttendanceCallbackIdParam> hrMagicAttendanceCallbackIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrMagicAttendanceCallbackIdParamList, HrMagicAttendanceCallbackIdParam::getId));
    }

    @Override
    public HrMagicAttendanceCallback detail(HrMagicAttendanceCallbackIdParam hrMagicAttendanceCallbackIdParam) {
        return this.queryEntity(hrMagicAttendanceCallbackIdParam.getId());
    }

    @Override
    public HrMagicAttendanceCallback queryEntity(String id) {
        HrMagicAttendanceCallback hrMagicAttendanceCallback = this.getById(id);
        if(ObjectUtil.isEmpty(hrMagicAttendanceCallback)) {
            throw new CommonException("魔点打卡回调数据表不存在，id值为：{}", id);
        }
        return hrMagicAttendanceCallback;
    }
}
