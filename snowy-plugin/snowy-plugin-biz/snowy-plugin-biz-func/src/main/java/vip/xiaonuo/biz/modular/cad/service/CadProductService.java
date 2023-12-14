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
package vip.xiaonuo.biz.modular.cad.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.cad.entity.CadProduct;
import vip.xiaonuo.biz.modular.cad.param.*;

import java.util.List;

/**
 * CAD-成品表Service接口
 *
 * @author yong.ye
 * @date  2023/12/01 15:35
 **/
public interface CadProductService extends IService<CadProduct> {

    /**
     * 获取CAD-成品表分页
     *
     * @author yong.ye
     * @date  2023/12/01 15:35
     */
    Page<CadProduct> page(CadProductPageParam cadProductPageParam);

    /**
     * 添加CAD-成品表
     *
     * @author yong.ye
     * @date  2023/12/01 15:35
     */
    void add(CadProductAddParam cadProductAddParam);

    /**
     * 编辑CAD-成品表
     *
     * @author yong.ye
     * @date  2023/12/01 15:35
     */
    void edit(CadProductEditParam cadProductEditParam);

    /**
     * 删除CAD-成品表
     *
     * @author yong.ye
     * @date  2023/12/01 15:35
     */
    void delete(List<CadProductIdParam> cadProductIdParamList);

    /**
     * 获取CAD-成品表详情
     *
     * @author yong.ye
     * @date  2023/12/01 15:35
     */
    CadProduct detail(CadProductIdParam cadProductIdParam);

    /**
     * 获取CAD-成品表详情
     *
     * @author yong.ye
     * @date  2023/12/01 15:35
     **/
    CadProduct queryEntity(String id);

    /**
     * 文件归档
     * @param filingParam
     */
    void filing(FilingParam filingParam);
}
