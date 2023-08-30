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
package vip.xiaonuo.biz.modular.recruit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.recruit.entity.BizRecruitResume;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitResumeAddParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitResumeEditParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitResumeIdParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitResumePageParam;
import vip.xiaonuo.biz.modular.recruit.param.dto.*;
import vip.xiaonuo.biz.modular.recruit.vo.BizRecruitResumeVo;
import vip.xiaonuo.biz.modular.recruit.vo.ResumeChannelStatisticVo;
import vip.xiaonuo.biz.modular.recruit.vo.ResumeChannelTotalVo;

import java.util.List;

/**
 * 业务表-招聘-简历库Service接口
 *
 * @author yong.ye
 * @date  2023/08/23 17:08
 **/
public interface BizRecruitResumeService extends IService<BizRecruitResume> {

    /**
     * 获取业务表-招聘-简历库分页
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    IPage<BizRecruitResumeVo> page(BizRecruitResumePageParam bizRecruitResumePageParam);

    /**
     * 添加业务表-招聘-简历库
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    void add(BizRecruitResumeAddParam bizRecruitResumeAddParam);

    /**
     * 编辑业务表-招聘-简历库
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    void edit(BizRecruitResumeEditParam bizRecruitResumeEditParam);

    /**
     * 删除业务表-招聘-简历库
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    void delete(List<BizRecruitResumeIdParam> bizRecruitResumeIdParamList);

    /**
     * 获取业务表-招聘-简历库详情
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    BizRecruitResume detail(BizRecruitResumeIdParam bizRecruitResumeIdParam);

    /**
     * 获取业务表-招聘-简历库详情
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     **/
    BizRecruitResume queryEntity(String id);

    /**
     * 添加业务表-招聘-简历库(新增简历-自动解析信息)
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    void addAuto(ResumeAddDto resumeAddDto);

    /**
     * 业务表-招聘-简历库(解析失败-重新解析)
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    void resumeParser(ResumeParserDto resumeParserDto);

    /**
     * 业务表-招聘-简历库(应聘登记表提交保存)
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    void registerSave(ResumeRegistSaveDto resumeRegistSaveDto);

    /**
     * 业务表-招聘-简历库(应聘登记表详情获取)
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    String registerDetail(BizRecruitResumeIdParam bizRecruitResumeIdParam);

    /**
     * 业务表-招聘-简历库(更新字段)
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    void update(ResumeUpdateDto resumeUpdateDto);

    /**
     * 渠道图示统计
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    ResumeChannelTotalVo channelTotal(ResumeChannelTotalDto resumeChannelTotalDto);

    /**
     * 业务表-招聘-简历库(拒绝offer)
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    void refuse(ResumeRefuseDto resumeRefuseDto);
}
