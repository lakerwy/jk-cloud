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
import vip.xiaonuo.biz.modular.cad.entity.CadProofread;
import vip.xiaonuo.biz.modular.cad.param.CadProofreadAddParam;
import vip.xiaonuo.biz.modular.cad.param.CadProofreadEditParam;
import vip.xiaonuo.biz.modular.cad.param.CadProofreadIdParam;
import vip.xiaonuo.biz.modular.cad.param.CadProofreadPageParam;

import java.util.List;

/**
 * CAD-提资|校审记录表Service接口
 *
 * @author yong.ye
 * @date  2023/12/01 15:36
 **/
public interface CadProofreadService extends IService<CadProofread> {

    /**
     * 获取CAD-提资|校审记录表分页
     *
     * @author yong.ye
     * @date  2023/12/01 15:36
     */
    Page<CadProofread> page(CadProofreadPageParam cadProofreadPageParam);

    /**
     * 添加CAD-提资|校审记录表
     *
     * @author yong.ye
     * @date  2023/12/01 15:36
     */
    void add(CadProofreadAddParam cadProofreadAddParam);

    /**
     * 编辑CAD-提资|校审记录表
     *
     * @author yong.ye
     * @date  2023/12/01 15:36
     */
    void edit(CadProofreadEditParam cadProofreadEditParam);

    /**
     * 删除CAD-提资|校审记录表
     *
     * @author yong.ye
     * @date  2023/12/01 15:36
     */
    void delete(List<CadProofreadIdParam> cadProofreadIdParamList);

    /**
     * 获取CAD-提资|校审记录表详情
     *
     * @author yong.ye
     * @date  2023/12/01 15:36
     */
    CadProofread detail(CadProofreadIdParam cadProofreadIdParam);

    /**
     * 获取CAD-提资|校审记录表详情
     *
     * @author yong.ye
     * @date  2023/12/01 15:36
     **/
    CadProofread queryEntity(String id);
}
