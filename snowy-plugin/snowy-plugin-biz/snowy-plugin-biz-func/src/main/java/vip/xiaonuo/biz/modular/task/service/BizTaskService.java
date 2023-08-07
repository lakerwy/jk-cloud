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
package vip.xiaonuo.biz.modular.task.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.task.entity.BizTask;
import vip.xiaonuo.biz.modular.task.param.*;

import java.util.List;

/**
 * 业务表-绩效考核-任务表Service接口
 *
 * @author yong.ye
 * @date  2023/08/04 14:20
 **/
public interface BizTaskService extends IService<BizTask> {

    /**
     * 获取业务表-绩效考核-任务表分页
     *
     * @author yong.ye
     * @date  2023/08/04 14:20
     */
    Page<BizTask> page(BizTaskPageParam bizTaskPageParam);

    /**
     * 添加业务表-绩效考核-任务表
     *
     * @author yong.ye
     * @date  2023/08/04 14:20
     */
    void add(BizTaskAddParam bizTaskAddParam);

    /**
     * 编辑业务表-绩效考核-任务表
     *
     * @author yong.ye
     * @date  2023/08/04 14:20
     */
    void edit(BizTaskEditParam bizTaskEditParam);

    /**
     * 删除业务表-绩效考核-任务表
     *
     * @author yong.ye
     * @date  2023/08/04 14:20
     */
    void delete(List<BizTaskIdParam> bizTaskIdParamList);

    /**
     * 获取业务表-绩效考核-任务表详情
     *
     * @author yong.ye
     * @date  2023/08/04 14:20
     */
    BizTask detail(BizTaskIdParam bizTaskIdParam);

    /**
     * 获取业务表-绩效考核-任务表详情
     *
     * @author yong.ye
     * @date  2023/08/04 14:20
     **/
    BizTask queryEntity(String id);

    /**
     * 添加业务表-年度重点任务记录(流程中发起 - 批量)
     *
     * @author yong.ye
     * @date  2023/08/04 14:20
     */
    void flwAdd(TaskFlwParam taskFlwParam);

    /**
     * 编辑业务表-年度重点任务记录(流程中修改 - 批量)
     *
     * @author yong.ye
     * @date  2023/08/04 14:20
     */
    void flwEdit(TaskFlwParam taskFlwParam);
}
