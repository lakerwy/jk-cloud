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
package vip.xiaonuo.biz.modular.crm.rollinout.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.rollinout.entity.Rollinout;
import vip.xiaonuo.biz.modular.crm.rollinout.param.*;
import vip.xiaonuo.biz.modular.crm.rollinout.vo.RollinoutInfoVo;

import java.util.List;

/**
 * 业务表-CRM-合作伙伴人员转入转出Service接口
 *
 * @author yong.ye
 * @date  2023/06/30 16:56
 **/
public interface RollinoutService extends IService<Rollinout> {

    /**
     * 获取业务表-CRM-合作伙伴人员转入转出分页
     *
     * @author yong.ye
     * @date  2023/06/30 16:56
     */
    Page<Rollinout> page(RollinoutPageParam rollinoutPageParam);

    /**
     * 添加业务表-CRM-合作伙伴人员转入转出
     *
     * @author yong.ye
     * @date  2023/06/30 16:56
     */
    void add(RollinoutAddParam rollinoutAddParam);

    /**
     * 编辑业务表-CRM-合作伙伴人员转入转出
     *
     * @author yong.ye
     * @date  2023/06/30 16:56
     */
    void edit(RollinoutEditParam rollinoutEditParam);

    /**
     * 删除业务表-CRM-合作伙伴人员转入转出
     *
     * @author yong.ye
     * @date  2023/06/30 16:56
     */
    void delete(List<RollinoutIdParam> rollinoutIdParamList);

    /**
     * 获取业务表-CRM-合作伙伴人员转入转出详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:56
     */
    Rollinout detail(RollinoutIdParam rollinoutIdParam);

    /**
     * 获取业务表-CRM-合作伙伴人员转入转出详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:56
     **/
    Rollinout queryEntity(String id);

    /**
     * 获取人员转入转出列表（分页）-携带人员信息
     * @param rollinoutInfoPageParam
     * @return
     */
    IPage<RollinoutInfoVo> pageList(RollinoutInfoPageParam rollinoutInfoPageParam);

    /**
     * 根据主键ID获取人员转入转出列表（分页）-携带人员信息
     * @param rollinoutIdParam
     * @return
     */
    RollinoutInfoVo detailById(RollinoutIdParam rollinoutIdParam);
}
