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
import vip.xiaonuo.biz.modular.cad.entity.CadStorage;
import vip.xiaonuo.biz.modular.cad.param.CadStorageAddParam;
import vip.xiaonuo.biz.modular.cad.param.CadStorageEditParam;
import vip.xiaonuo.biz.modular.cad.param.CadStorageIdParam;
import vip.xiaonuo.biz.modular.cad.param.CadStoragePageParam;

import java.util.List;

/**
 * CAD-提资|校审|成品文件存储目录结构Service接口
 *
 * @author yong.ye
 * @date  2023/12/12 15:14
 **/
public interface CadStorageService extends IService<CadStorage> {

    /**
     * 获取CAD-提资|校审|成品文件存储目录结构分页
     *
     * @author yong.ye
     * @date  2023/12/12 15:14
     */
    Page<CadStorage> page(CadStoragePageParam cadStoragePageParam);

    /**
     * 添加CAD-提资|校审|成品文件存储目录结构
     *
     * @author yong.ye
     * @date  2023/12/12 15:14
     */
    void add(CadStorageAddParam cadStorageAddParam);

    /**
     * 编辑CAD-提资|校审|成品文件存储目录结构
     *
     * @author yong.ye
     * @date  2023/12/12 15:14
     */
    void edit(CadStorageEditParam cadStorageEditParam);

    /**
     * 删除CAD-提资|校审|成品文件存储目录结构
     *
     * @author yong.ye
     * @date  2023/12/12 15:14
     */
    void delete(List<CadStorageIdParam> cadStorageIdParamList);

    /**
     * 获取CAD-提资|校审|成品文件存储目录结构详情
     *
     * @author yong.ye
     * @date  2023/12/12 15:14
     */
    CadStorage detail(CadStorageIdParam cadStorageIdParam);

    /**
     * 获取CAD-提资|校审|成品文件存储目录结构详情
     *
     * @author yong.ye
     * @date  2023/12/12 15:14
     **/
    CadStorage queryEntity(String id);
}
