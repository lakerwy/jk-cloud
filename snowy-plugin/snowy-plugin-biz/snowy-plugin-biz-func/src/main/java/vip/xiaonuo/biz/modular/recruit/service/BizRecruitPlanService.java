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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.recruit.entity.BizRecruitPlan;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitPlanEditParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitPlanIdParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitPlanPageParam;
import vip.xiaonuo.biz.modular.recruit.param.dto.RecruitPlanFlwDto;
import vip.xiaonuo.biz.modular.recruit.param.dto.RecruitPlanPageDto;
import vip.xiaonuo.biz.modular.recruit.vo.BizRecruitPlanVo;

import java.util.List;

/**
 * 业务表-招聘-部门年度计划表Service接口
 *
 * @author yong.ye
 * @date  2023/08/23 09:02
 **/
public interface BizRecruitPlanService extends IService<BizRecruitPlan> {

    /**
     * 获取业务表-招聘-部门年度计划表分页
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    List<BizRecruitPlanVo> page(RecruitPlanPageDto recruitPlanPageDto);

    /**
     * 添加业务表-招聘-部门年度计划表
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    void add(RecruitPlanFlwDto recruitPlanFlwDto);

    /**
     * 编辑业务表-招聘-部门年度计划表
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    void edit(BizRecruitPlanEditParam bizRecruitPlanEditParam);

    /**
     * 删除业务表-招聘-部门年度计划表
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    void delete(List<BizRecruitPlanIdParam> bizRecruitPlanIdParamList);

    /**
     * 获取业务表-招聘-部门年度计划表详情
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    BizRecruitPlan detail(BizRecruitPlanIdParam bizRecruitPlanIdParam);

    /**
     * 获取业务表-招聘-部门年度计划表详情
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     **/
    BizRecruitPlan queryEntity(String id);

    /**
     * 添加业务表-招聘-部门年度计划表(流程中添加)
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    void flwAdd(RecruitPlanFlwDto recruitPlanFlwDto);

    /**
     * 编辑业务表-招聘-部门年度计划表(流程中修改)
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    void flwEdit(RecruitPlanFlwDto recruitPlanFlwDto);

    /**
     * 获取业务表-招聘-部门年度计划表分页
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    Page<BizRecruitPlan> planSelect(BizRecruitPlanPageParam bizRecruitPlanPageParam);
}
