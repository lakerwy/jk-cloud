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
package vip.xiaonuo.bp.modular.recruit.supplier.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.bp.modular.recruit.supplier.entity.BpSupplier;
import vip.xiaonuo.bp.modular.recruit.supplier.vo.BpSupplierVo;

import java.util.List;

/**
 * 供应商基本信息表Mapper接口
 *
 * @author hxw
 * @date  2024/04/17 15:18
 **/
public interface BpSupplierMapper extends BaseMapper<BpSupplier> {

    Page<BpSupplierVo> getPageList(Page<Object> defaultPage, @Param(Constants.WRAPPER) QueryWrapper<BpSupplier> queryWrapper);

    /**
     * 根据注册手机号，判断供应商是否已提交过注册信息
     * @return
     */
    List<BpSupplier> getSupplierByPhone(String phone);
}
