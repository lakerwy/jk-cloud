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
package vip.xiaonuo.bp.modular.recruit.supplier.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.bp.modular.recruit.supplier.entity.BpSupplier;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierAddParam;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierEditParam;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierIdParam;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierPageParam;
import vip.xiaonuo.bp.modular.recruit.supplier.vo.BpSupplierReturnVo;
import vip.xiaonuo.bp.modular.recruit.supplier.vo.BpSupplierVo;

import java.util.List;

/**
 * 供应商基本信息表Service接口
 *
 * @author hxw
 * @date  2024/04/17 15:18
 **/
public interface BpSupplierService extends IService<BpSupplier> {

    /**
     * 获取供应商基本信息表分页
     *
     * @author hxw
     * @date  2024/04/17 15:18
     */
    Page<BpSupplierVo> page(BpSupplierPageParam bpSupplierPageParam);

    /**
     * 添加供应商基本信息表
     *
     * @author hxw
     * @date  2024/04/17 15:18
     */
    void add(BpSupplierAddParam bpSupplierAddParam);

    /**
     * 编辑供应商基本信息表
     *
     * @author hxw
     * @date  2024/04/17 15:18
     */
    void blackEdit(BpSupplierEditParam bpSupplierEditParam);


    /**
     * 删除供应商基本信息表
     *
     * @author hxw
     * @date  2024/04/17 15:18
     */
    void delete(List<BpSupplierIdParam> bpSupplierIdParamList);

    /**
     * 获取供应商基本信息表详情
     *
     * @author hxw
     * @date  2024/04/17 15:18
     */
    BpSupplierReturnVo detail(BpSupplierIdParam bpSupplierIdParam);

    /**
     * 获取供应商基本信息表详情
     *
     * @author hxw
     * @date  2024/04/17 15:18
     **/
    BpSupplierReturnVo queryEntity(String id);
}
