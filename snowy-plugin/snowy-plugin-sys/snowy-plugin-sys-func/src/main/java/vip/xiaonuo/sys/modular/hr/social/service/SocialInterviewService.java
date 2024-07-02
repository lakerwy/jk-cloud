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
package vip.xiaonuo.sys.modular.hr.social.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.social.entity.SocialInterview;
import vip.xiaonuo.sys.modular.hr.social.param.SocialInterviewAddParam;
import vip.xiaonuo.sys.modular.hr.social.param.SocialInterviewEditParam;
import vip.xiaonuo.sys.modular.hr.social.param.SocialInterviewIdParam;
import vip.xiaonuo.sys.modular.hr.social.param.SocialInterviewPageParam;
import vip.xiaonuo.sys.modular.hr.social.vo.SocialInterviewVo;

import java.util.List;

/**
 * 社招 - （应聘者）- 面试入职流程Service接口
 *
 * @author yong.ye
 * @date  2024/07/01 09:36
 **/
public interface SocialInterviewService extends IService<SocialInterview> {

    /**
     * 获取社招 - （应聘者）- 面试入职流程分页
     *
     * @author yong.ye
     * @date  2024/07/01 09:36
     */
    Page<SocialInterviewVo> page(SocialInterviewPageParam socialInterviewPageParam);

    /**
     * 添加社招 - （应聘者）- 面试入职流程
     *
     * @author yong.ye
     * @date  2024/07/01 09:36
     */
    void add(SocialInterviewAddParam socialInterviewAddParam);

    /**
     * 编辑社招 - （应聘者）- 面试入职流程
     *
     * @author yong.ye
     * @date  2024/07/01 09:36
     */
    void edit(SocialInterviewEditParam socialInterviewEditParam);

    /**
     * 删除社招 - （应聘者）- 面试入职流程
     *
     * @author yong.ye
     * @date  2024/07/01 09:36
     */
    void delete(List<SocialInterviewIdParam> socialInterviewIdParamList);

    /**
     * 获取社招 - （应聘者）- 面试入职流程详情
     *
     * @author yong.ye
     * @date  2024/07/01 09:36
     */
    SocialInterviewVo detail(SocialInterviewIdParam socialInterviewIdParam);

    /**
     * 获取社招 - （应聘者）- 面试入职流程详情
     *
     * @author yong.ye
     * @date  2024/07/01 09:36
     **/
    SocialInterview queryEntity(String id);
}
