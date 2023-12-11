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
import vip.xiaonuo.biz.modular.teambition.entity.BizTaskGroupRes;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupResAddParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupResEditParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupResIdParam;
import vip.xiaonuo.biz.modular.teambition.param.BizTaskGroupResPageParam;

import java.util.List;

/**
 * 任务分组关联表（人或部门）Service接口
 *
 * @author ljy
 * @date  2023/12/08 09:00
 **/
public interface BizTaskGroupResService extends IService<BizTaskGroupRes> {

    /**
     * 获取任务分组关联表（人或部门）分页
     *
     * @author ljy
     * @date  2023/12/08 09:00
     */
    Page<BizTaskGroupRes> page(BizTaskGroupResPageParam bizTaskGroupResPageParam);

    /**
     * 添加任务分组关联表（人或部门）
     *
     * @author ljy
     * @date  2023/12/08 09:00
     */
    void add(BizTaskGroupResAddParam bizTaskGroupResAddParam);

    /**
     * 编辑任务分组关联表（人或部门）
     *
     * @author ljy
     * @date  2023/12/08 09:00
     */
    void edit(BizTaskGroupResEditParam bizTaskGroupResEditParam);

    /**
     * 删除任务分组关联表（人或部门）
     *
     * @author ljy
     * @date  2023/12/08 09:00
     */
    void delete(List<BizTaskGroupResIdParam> bizTaskGroupResIdParamList);

    /**
     * 获取任务分组关联表（人或部门）详情
     *
     * @author ljy
     * @date  2023/12/08 09:00
     */
    BizTaskGroupRes detail(BizTaskGroupResIdParam bizTaskGroupResIdParam);

    /**
     * 获取任务分组关联表（人或部门）详情
     *
     * @author ljy
     * @date  2023/12/08 09:00
     **/
    BizTaskGroupRes queryEntity(String id);
}
