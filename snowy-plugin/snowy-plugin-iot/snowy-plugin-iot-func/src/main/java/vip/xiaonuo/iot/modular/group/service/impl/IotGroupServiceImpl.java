package vip.xiaonuo.iot.modular.group.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.iot.modular.device.entity.IotDevice;
import vip.xiaonuo.iot.modular.device.service.IotDeviceService;
import vip.xiaonuo.iot.modular.group.entity.IotGroup;
import vip.xiaonuo.iot.modular.group.mapper.IotGroupMapper;
import vip.xiaonuo.iot.modular.group.param.IotGroupAddParam;
import vip.xiaonuo.iot.modular.group.param.IotGroupEditParam;
import vip.xiaonuo.iot.modular.group.param.IotGroupIdParam;
import vip.xiaonuo.iot.modular.group.param.IotGroupPageParam;
import vip.xiaonuo.iot.modular.group.service.IotGroupService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设备分组Service接口实现类
 *
 * @author mayike
 * @date  2024/08/06 08:55
 **/
@Service
public class IotGroupServiceImpl extends ServiceImpl<IotGroupMapper, IotGroup> implements IotGroupService {

    @Resource
    private IotDeviceService iotDeviceService;

    @Override
    public Page<IotGroup> page(IotGroupPageParam iotGroupPageParam) {
        QueryWrapper<IotGroup> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(iotGroupPageParam.getSortField(), iotGroupPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(iotGroupPageParam.getSortOrder());
            queryWrapper.orderBy(true, iotGroupPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(iotGroupPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(IotGroup::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(IotGroupAddParam iotGroupAddParam) {
        IotGroup iotGroup = BeanUtil.toBean(iotGroupAddParam, IotGroup.class);
        IotGroup one = getOne(new LambdaQueryWrapper<IotGroup>().eq(IotGroup::getName, iotGroup.getName()));
        if(one != null){
            throw new CommonException("该群组已创建");
        }
        this.save(iotGroup);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(IotGroupEditParam iotGroupEditParam) {
        IotGroup iotGroup = this.queryEntity(iotGroupEditParam.getId());
        BeanUtil.copyProperties(iotGroupEditParam, iotGroup);
        IotGroup one = getOne(new LambdaQueryWrapper<IotGroup>().eq(IotGroup::getName, iotGroup.getName()));
        if(one != null && one.getId() != iotGroup.getId()){
            throw new CommonException("该群组已创建");
        }
        this.updateById(iotGroup);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<IotGroupIdParam> iotGroupIdParamList) {
        for (IotGroupIdParam iotGroupIdParam : iotGroupIdParamList) {
            IotGroup iotGroup = queryEntity(iotGroupIdParam.getId());
            List<IotDevice> iotDevices = iotDeviceService.listByGroupId(iotGroup.getId());
            if(iotDevices != null && iotDevices.size() > 0){
                throw new CommonException("{}该分组下存在设备", iotGroup.getName());
            }
        }
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(iotGroupIdParamList, IotGroupIdParam::getId));
    }

    @Override
    public IotGroup detail(IotGroupIdParam iotGroupIdParam) {
        return this.queryEntity(iotGroupIdParam.getId());
    }

    @Override
    public IotGroup queryEntity(String id) {
        IotGroup iotGroup = this.getById(id);
        if(ObjectUtil.isEmpty(iotGroup)) {
            throw new CommonException("设备分组不存在，id值为：{}", id);
        }
        return iotGroup;
    }
}
