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
package vip.xiaonuo.biz.modular.teambition.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.teambition.entity.BizTaskGroup;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupAddParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupEditParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupIdParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupPageParam;

import java.util.List;

/**
 * 任务来源表Service接口
 *
 * @author ljy
 * @date  2023/12/08 08:58
 **/
public interface BizTaskGroupService extends IService<BizTaskGroup> {

    /**
     * 获取任务来源表分页
     *
     * @author ljy
     * @date  2023/12/08 08:58
     */
    Page<BizTaskGroup> page(BizTaskGroupPageParam bizTaskGroupPageParam);

    /**
     * 添加任务来源表
     *
     * @author ljy
     * @date  2023/12/08 08:58
     */
    void add(BizTaskGroupAddParam bizTaskGroupAddParam);

    /**
     * 编辑任务来源表
     *
     * @author ljy
     * @date  2023/12/08 08:58
     */
    void edit(BizTaskGroupEditParam bizTaskGroupEditParam);

    /**
     * 删除任务来源表
     *
     * @author ljy
     * @date  2023/12/08 08:58
     */
    void delete(List<BizTaskGroupIdParam> bizTaskGroupIdParamList);

    /**
     * 获取任务来源表详情
     *
     * @author ljy
     * @date  2023/12/08 08:58
     */
    BizTaskGroup detail(BizTaskGroupIdParam bizTaskGroupIdParam);

    /**
     * 获取任务来源表详情
     *
     * @author ljy
     * @date  2023/12/08 08:58
     **/
    BizTaskGroup queryEntity(String id);
}
