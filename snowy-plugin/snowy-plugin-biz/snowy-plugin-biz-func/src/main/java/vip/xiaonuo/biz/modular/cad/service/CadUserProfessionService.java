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
import vip.xiaonuo.biz.modular.cad.entity.CadUserProfession;
import vip.xiaonuo.biz.modular.cad.param.CadUserProfessionAddParam;
import vip.xiaonuo.biz.modular.cad.param.CadUserProfessionEditParam;
import vip.xiaonuo.biz.modular.cad.param.CadUserProfessionIdParam;
import vip.xiaonuo.biz.modular.cad.param.CadUserProfessionPageParam;

import java.util.List;

/**
 * CAD-用户专业关系表Service接口
 *
 * @author yong.ye
 * @date  2023/11/29 10:41
 **/
public interface CadUserProfessionService extends IService<CadUserProfession> {

    /**
     * 获取CAD-用户专业关系表分页
     *
     * @author yong.ye
     * @date  2023/11/29 10:41
     */
    Page<CadUserProfession> page(CadUserProfessionPageParam cadUserProfessionPageParam);

    /**
     * 添加CAD-用户专业关系表
     *
     * @author yong.ye
     * @date  2023/11/29 10:41
     */
    void add(CadUserProfessionAddParam cadUserProfessionAddParam);

    /**
     * 编辑CAD-用户专业关系表
     *
     * @author yong.ye
     * @date  2023/11/29 10:41
     */
    void edit(CadUserProfessionEditParam cadUserProfessionEditParam);

    /**
     * 删除CAD-用户专业关系表
     *
     * @author yong.ye
     * @date  2023/11/29 10:41
     */
    void delete(List<CadUserProfessionIdParam> cadUserProfessionIdParamList);

    /**
     * 获取CAD-用户专业关系表详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:41
     */
    CadUserProfession detail(CadUserProfessionIdParam cadUserProfessionIdParam);

    /**
     * 获取CAD-用户专业关系表详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:41
     **/
    CadUserProfession queryEntity(String id);
}
