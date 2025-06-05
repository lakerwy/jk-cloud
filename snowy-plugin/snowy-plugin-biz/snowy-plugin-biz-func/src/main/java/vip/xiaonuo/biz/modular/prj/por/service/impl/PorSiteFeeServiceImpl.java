package vip.xiaonuo.biz.modular.prj.por.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSiteFeeItem;
import vip.xiaonuo.biz.modular.prj.por.service.PorSiteFeeItemService;
import vip.xiaonuo.biz.modular.prj.por.vo.PorSiteFeeVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSiteFee;
import vip.xiaonuo.biz.modular.prj.por.mapper.PorSiteFeeMapper;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeeIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSiteFeePageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorSiteFeeService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目产值台账 - 项目管理 - 项目现场管理费用Service接口实现类
 *
 * @author yy
 * @date  2025/06/03 09:45
 **/
@Service
@DS("slave_1")
public class PorSiteFeeServiceImpl extends ServiceImpl<PorSiteFeeMapper, PorSiteFee> implements PorSiteFeeService {

    @Resource
    private PorSiteFeeMapper porSiteFeeMapper;

    @Resource
    private PorSiteFeeItemService porSiteFeeItemService;

    @Override
    public Page<PorSiteFeeVo> page(PorSiteFeePageParam porSiteFeePageParam) {
        QueryWrapper<PorSiteFee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(porSiteFeePageParam.getHetongId())) {
            queryWrapper.eq("a.HETONG_ID", porSiteFeePageParam.getHetongId());
        }
        if(ObjectUtil.isNotEmpty(porSiteFeePageParam.getFeeMonth())) {
            queryWrapper.like("a.FEE_MONTH", porSiteFeePageParam.getFeeMonth());
        }
        if(ObjectUtil.isNotEmpty(porSiteFeePageParam.getFeeOptName())) {
            queryWrapper.like("a.FEE_OPT_NAME", porSiteFeePageParam.getFeeOptName());
        }
        if(ObjectUtil.isAllNotEmpty(porSiteFeePageParam.getSortField(), porSiteFeePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porSiteFeePageParam.getSortOrder());
            queryWrapper.orderBy(true, porSiteFeePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porSiteFeePageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        Page<PorSiteFeeVo> result = porSiteFeeMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        if(CollUtil.isNotEmpty(result.getRecords())){
            result.getRecords().forEach(item->{
                List<PorSiteFeeItem> itemList = porSiteFeeItemService.list(new LambdaQueryWrapper<PorSiteFeeItem>().eq(PorSiteFeeItem::getPorSiteFeeId,item.getId()));
                item.setItemList(itemList);
            });
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PorSiteFeeAddParam porSiteFeeAddParam) {
        PorSiteFee porSiteFee = BeanUtil.toBean(porSiteFeeAddParam, PorSiteFee.class);
        this.save(porSiteFee);

        if(CollUtil.isNotEmpty(porSiteFeeAddParam.getItemList())){
            List<PorSiteFeeItem> addList = BeanUtil.copyToList(porSiteFeeAddParam.getItemList(),PorSiteFeeItem.class);
            addList.forEach(item->item.setPorSiteFeeId(porSiteFee.getId()));
            porSiteFeeItemService.saveBatch(addList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PorSiteFeeEditParam porSiteFeeEditParam) {
        PorSiteFee porSiteFee = this.queryEntity(porSiteFeeEditParam.getId());
        BeanUtil.copyProperties(porSiteFeeEditParam, porSiteFee);
        this.updateById(porSiteFee);

        //修改明细
        if(CollUtil.isNotEmpty(porSiteFeeEditParam.getItemList())){
            //本次传入的数据
            List<PorSiteFeeItem> itemList = porSiteFeeEditParam.getItemList();
            itemList.forEach(item->item.setPorSiteFeeId(porSiteFee.getId()));
            //获取历史数据
            List<PorSiteFeeItem> oldList = porSiteFeeItemService.list(
                    new QueryWrapper<PorSiteFeeItem>().lambda().eq(PorSiteFeeItem::getPorSiteFeeId,porSiteFee.getId()));
            List<String> oldIds = oldList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<PorSiteFeeItem> addList = itemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<PorSiteFeeItem> updateList = itemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<PorSiteFeeItem> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                porSiteFeeItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                porSiteFeeItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                porSiteFeeItemService.removeByIds(removeList);
            }
        }else{
            porSiteFeeItemService.remove(new QueryWrapper<PorSiteFeeItem>().lambda().eq(PorSiteFeeItem::getPorSiteFeeId,porSiteFee.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PorSiteFeeIdParam> porSiteFeeIdParamList) {
        List<String> ids = CollStreamUtil.toList(porSiteFeeIdParamList, PorSiteFeeIdParam::getId);
        // 执行删除
        this.removeByIds(ids);
        if(CollUtil.isNotEmpty(ids)){
            porSiteFeeItemService.remove(new LambdaQueryWrapper<PorSiteFeeItem>().in(PorSiteFeeItem::getPorSiteFeeId,ids));
        }
    }

    @Override
    public PorSiteFeeVo detail(PorSiteFeeIdParam porSiteFeeIdParam) {
        PorSiteFeeVo porSiteFeeVo = porSiteFeeMapper.getDetail(porSiteFeeIdParam.getId());
        List<PorSiteFeeItem> itemList = porSiteFeeItemService.list(new LambdaQueryWrapper<PorSiteFeeItem>().eq(PorSiteFeeItem::getPorSiteFeeId,porSiteFeeVo.getId()));
        porSiteFeeVo.setItemList(itemList);
        return porSiteFeeVo;
    }

    @Override
    public PorSiteFee queryEntity(String id) {
        PorSiteFee porSiteFee = this.getById(id);
        if(ObjectUtil.isEmpty(porSiteFee)) {
            throw new CommonException("项目产值台账 - 项目管理 - 项目现场管理费用不存在，id值为：{}", id);
        }
        return porSiteFee;
    }
}
