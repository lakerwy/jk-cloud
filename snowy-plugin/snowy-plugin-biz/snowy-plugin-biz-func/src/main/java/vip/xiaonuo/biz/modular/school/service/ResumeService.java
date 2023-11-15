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
package vip.xiaonuo.biz.modular.school.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;
import vip.xiaonuo.biz.modular.school.entity.Resume;
import vip.xiaonuo.biz.modular.school.param.*;
import vip.xiaonuo.biz.modular.school.vo.ResumeVo;

import javax.validation.Valid;
import java.util.List;

/**
 * 校招-简历Service接口
 *
 * @author yong.ye
 * @date  2023/11/15 08:54
 **/
public interface ResumeService extends IService<Resume> {

    /**
     * 获取校招-简历分页
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    Page<Resume> page(ResumePageParam resumePageParam);

    /**
     * 添加校招-简历
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    void add(ResumeAddParam resumeAddParam);

    /**
     * 编辑校招-简历
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    void edit(ResumeEditParam resumeEditParam);

    /**
     * 删除校招-简历
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    void delete(List<ResumeIdParam> resumeIdParamList);

    /**
     * 获取校招-简历详情
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    ResumeVo detail(ResumeIdParam resumeIdParam);

    /**
     * 获取校招-简历详情
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     **/
    Resume queryEntity(String id);

    /***
     * 简历操作 针对 HR筛选|部门筛选|面试通知|面试登记|Offer登记
     * @param resumeRecordAddParam
     */
    void doResumeAction(ResumeRecordAddParam resumeRecordAddParam);

    /**
     * 批量简历操作 针对 HR筛选|部门筛选|面试通知|面试登记|Offer登记
     * @param batchResumeRecord
     */
    void doResumeActionBatch(BatchResumeRecord batchResumeRecord);

    /**
     * 投递简历 - 保存简历
     * @param resumeAddParam
     */
    void saveResume(ResumeAddParam resumeAddParam);

    /**
     * 简历部门推荐 【批量操作部门推荐】
     * @param assignDept
     */
    void assignDept(@RequestBody @Valid AssignDeptsDto assignDept);

    /**
     * 根据当前用户信息，获取推荐至本部门的简历
     * @param resumePageParam
     * @return
     */
    Page<Resume> pageByUserId(ResumePageParam resumePageParam);
}
