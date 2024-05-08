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
package vip.xiaonuo.bp.modular.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.bp.modular.platform.entity.Clarify;
import vip.xiaonuo.bp.modular.platform.param.ClarifyAddParam;
import vip.xiaonuo.bp.modular.platform.param.ClarifyEditParam;
import vip.xiaonuo.bp.modular.platform.param.ClarifyIdParam;
import vip.xiaonuo.bp.modular.platform.param.ClarifyPageParam;
import vip.xiaonuo.bp.modular.platform.vo.ClarifyVo;

import java.util.List;

/**
 * 招采管理 - 竞价澄清|询价澄清|招标澄清Service接口
 *
 * @author yong.ye
 * @date  2024/05/07 16:37
 **/
public interface ClarifyService extends IService<Clarify> {

    /**
     * 获取招采管理 - 竞价澄清|询价澄清|招标澄清分页
     *
     * @author yong.ye
     * @date  2024/05/07 16:37
     */
    Page<ClarifyVo> page(ClarifyPageParam clarifyPageParam);

    /**
     * 添加招采管理 - 竞价澄清|询价澄清|招标澄清
     *
     * @author yong.ye
     * @date  2024/05/07 16:37
     */
    void add(ClarifyAddParam clarifyAddParam);

    /**
     * 编辑招采管理 - 竞价澄清|询价澄清|招标澄清
     *
     * @author yong.ye
     * @date  2024/05/07 16:37
     */
    void edit(ClarifyEditParam clarifyEditParam);

    /**
     * 删除招采管理 - 竞价澄清|询价澄清|招标澄清
     *
     * @author yong.ye
     * @date  2024/05/07 16:37
     */
    void delete(List<ClarifyIdParam> clarifyIdParamList);

    /**
     * 获取招采管理 - 竞价澄清|询价澄清|招标澄清详情
     *
     * @author yong.ye
     * @date  2024/05/07 16:37
     */
    ClarifyVo detail(ClarifyIdParam clarifyIdParam);

    /**
     * 获取招采管理 - 竞价澄清|询价澄清|招标澄清详情
     *
     * @author yong.ye
     * @date  2024/05/07 16:37
     **/
    Clarify queryEntity(String id);
}
