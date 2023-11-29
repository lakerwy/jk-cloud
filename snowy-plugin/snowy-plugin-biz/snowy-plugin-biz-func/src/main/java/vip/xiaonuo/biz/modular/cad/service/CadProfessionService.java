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
import vip.xiaonuo.biz.modular.cad.entity.CadProfession;
import vip.xiaonuo.biz.modular.cad.param.CadProfessionAddParam;
import vip.xiaonuo.biz.modular.cad.param.CadProfessionEditParam;
import vip.xiaonuo.biz.modular.cad.param.CadProfessionIdParam;
import vip.xiaonuo.biz.modular.cad.param.CadProfessionPageParam;

import java.util.List;

/**
 * CAD-专业表Service接口
 *
 * @author yong.ye
 * @date  2023/11/29 10:35
 **/
public interface CadProfessionService extends IService<CadProfession> {

    /**
     * 获取CAD-专业表分页
     *
     * @author yong.ye
     * @date  2023/11/29 10:35
     */
    Page<CadProfession> page(CadProfessionPageParam cadProfessionPageParam);

    /**
     * 添加CAD-专业表
     *
     * @author yong.ye
     * @date  2023/11/29 10:35
     */
    void add(CadProfessionAddParam cadProfessionAddParam);

    /**
     * 编辑CAD-专业表
     *
     * @author yong.ye
     * @date  2023/11/29 10:35
     */
    void edit(CadProfessionEditParam cadProfessionEditParam);

    /**
     * 删除CAD-专业表
     *
     * @author yong.ye
     * @date  2023/11/29 10:35
     */
    void delete(List<CadProfessionIdParam> cadProfessionIdParamList);

    /**
     * 获取CAD-专业表详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:35
     */
    CadProfession detail(CadProfessionIdParam cadProfessionIdParam);

    /**
     * 获取CAD-专业表详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:35
     **/
    CadProfession queryEntity(String id);
}
