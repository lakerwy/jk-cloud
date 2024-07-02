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
import vip.xiaonuo.sys.modular.hr.social.entity.SocialResume;
import vip.xiaonuo.sys.modular.hr.social.param.*;
import vip.xiaonuo.sys.modular.hr.social.vo.SocialResumeVo;

import java.util.List;

/**
 * 社招 - （应聘者）简历库信息Service接口
 *
 * @author yong.ye
 * @date  2024/07/01 09:35
 **/
public interface SocialResumeService extends IService<SocialResume> {

    /**
     * 获取社招 - （应聘者）简历库信息分页
     *
     * @author yong.ye
     * @date  2024/07/01 09:35
     */
    Page<SocialResumeVo> page(SocialResumePageParam socialResumePageParam);

    /**
     * 添加社招 - （应聘者）简历库信息
     *
     * @author yong.ye
     * @date  2024/07/01 09:35
     */
    void add(SocialResumeAddParam socialResumeAddParam);

    /**
     * 编辑社招 - （应聘者）简历库信息
     *
     * @author yong.ye
     * @date  2024/07/01 09:35
     */
    void edit(SocialResumeEditParam socialResumeEditParam);

    /**
     * 删除社招 - （应聘者）简历库信息
     *
     * @author yong.ye
     * @date  2024/07/01 09:35
     */
    void delete(List<SocialResumeIdParam> socialResumeIdParamList);

    /**
     * 获取社招 - （应聘者）简历库信息详情
     *
     * @author yong.ye
     * @date  2024/07/01 09:35
     */
    SocialResume detail(SocialResumeIdParam socialResumeIdParam);

    /**
     * 获取社招 - （应聘者）简历库信息详情
     *
     * @author yong.ye
     * @date  2024/07/01 09:35
     **/
    SocialResume queryEntity(String id);

    /**
     * 后台 - 附件上传 - 提交简历到系统
     * @param uploadParam
     */
    void save(UploadParam uploadParam);

    /**
     * 发送 面试 短信，填写基本信息
     * @param idParam
     */
    void send(SocialResumeIdParam idParam);


    /**
     * 简历个人信息提交
     * @param resumeParam
     */
    void submit(ResumeParam resumeParam);
}
