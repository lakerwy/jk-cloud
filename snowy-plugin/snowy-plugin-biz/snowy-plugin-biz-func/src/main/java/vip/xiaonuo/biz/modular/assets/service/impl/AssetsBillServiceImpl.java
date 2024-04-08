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
package vip.xiaonuo.biz.modular.assets.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.assets.vo.AssetsBillVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.assets.entity.AssetsBill;
import vip.xiaonuo.biz.modular.assets.mapper.AssetsBillMapper;
import vip.xiaonuo.biz.modular.assets.param.AssetsBillAddParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsBillEditParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsBillIdParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsBillPageParam;
import vip.xiaonuo.biz.modular.assets.service.AssetsBillService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 固资申购 - 申请单 - 申购详情列表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/04/08 16:18
 **/
@Service
@DS("slave_1")
public class AssetsBillServiceImpl extends ServiceImpl<AssetsBillMapper, AssetsBill> implements AssetsBillService {

    @Resource
    private AssetsBillMapper assetsBillMapper;

    @Override
    public Page<AssetsBillVo> page(AssetsBillPageParam assetsBillPageParam) {
        QueryWrapper<AssetsBill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("b.DELETE_FLAG","NOT_DELETE");
        queryWrapper.in("b.STATUS", FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(assetsBillPageParam.getName())) {
            queryWrapper.like("a.NAME", assetsBillPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(assetsBillPageParam.getModel())) {
            queryWrapper.like("a.MODEL", assetsBillPageParam.getModel());
        }
        if(ObjectUtil.isNotEmpty(assetsBillPageParam.getSn())){
            queryWrapper.like("b.SN",assetsBillPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(assetsBillPageParam.getType())){
            queryWrapper.eq("b.TYPE",assetsBillPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(assetsBillPageParam.getHandle())){
            queryWrapper.eq("b.HANDLE",assetsBillPageParam.getHandle());
        }
        if(ObjectUtil.isNotEmpty(assetsBillPageParam.getOrgName())){
            queryWrapper.like("b.ORG_NAME",assetsBillPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(assetsBillPageParam.getUserName())){
            queryWrapper.like("b.USER_NAME",assetsBillPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(assetsBillPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("b.SN",assetsBillPageParam.getSearchKey())
                    .or().like("b.ORG_NAME",assetsBillPageParam.getSearchKey())
                    .or().like("b.USER_NAME",assetsBillPageParam.getSearchKey())
                    .or().like("a.NAME",assetsBillPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(assetsBillPageParam.getSortField(), assetsBillPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(assetsBillPageParam.getSortOrder());
            queryWrapper.orderBy(true, assetsBillPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(assetsBillPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(AssetsBill::getId);
        }
        return assetsBillMapper.getAssetsBillPage(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(AssetsBillAddParam assetsBillAddParam) {
        AssetsBill assetsBill = BeanUtil.toBean(assetsBillAddParam, AssetsBill.class);
        this.save(assetsBill);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(AssetsBillEditParam assetsBillEditParam) {
        AssetsBill assetsBill = this.queryEntity(assetsBillEditParam.getId());
        BeanUtil.copyProperties(assetsBillEditParam, assetsBill);
        this.updateById(assetsBill);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<AssetsBillIdParam> assetsBillIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(assetsBillIdParamList, AssetsBillIdParam::getId));
    }

    @Override
    public AssetsBill detail(AssetsBillIdParam assetsBillIdParam) {
        return this.queryEntity(assetsBillIdParam.getId());
    }

    @Override
    public AssetsBill queryEntity(String id) {
        AssetsBill assetsBill = this.getById(id);
        if(ObjectUtil.isEmpty(assetsBill)) {
            throw new CommonException("固资申购 - 申请单 - 申购详情列表不存在，id值为：{}", id);
        }
        return assetsBill;
    }
}
