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
import vip.xiaonuo.biz.modular.task.entity.BizDeptAssSynergy;
import vip.xiaonuo.biz.modular.task.param.BizDeptAssSynergyAddParam;
import vip.xiaonuo.biz.modular.task.param.BizDeptAssSynergyEditParam;
import vip.xiaonuo.biz.modular.task.param.BizDeptAssSynergyIdParam;
import vip.xiaonuo.biz.modular.task.param.BizDeptAssSynergyPageParam;

import java.util.List;

/**
 * 业务表-绩效考核-部门协同分Service接口
 *
 * @author yong.ye
 * @date  2023/08/11 14:22
 **/
public interface BizDeptAssSynergyService extends IService<BizDeptAssSynergy> {

    /**
     * 获取业务表-绩效考核-部门协同分分页
     *
     * @author yong.ye
     * @date  2023/08/11 14:22
     */
    Page<BizDeptAssSynergy> page(BizDeptAssSynergyPageParam bizDeptAssSynergyPageParam);

    /**
     * 添加业务表-绩效考核-部门协同分
     *
     * @author yong.ye
     * @date  2023/08/11 14:22
     */
    void add(BizDeptAssSynergyAddParam bizDeptAssSynergyAddParam);

    /**
     * 编辑业务表-绩效考核-部门协同分
     *
     * @author yong.ye
     * @date  2023/08/11 14:22
     */
    void edit(BizDeptAssSynergyEditParam bizDeptAssSynergyEditParam);

    /**
     * 删除业务表-绩效考核-部门协同分
     *
     * @author yong.ye
     * @date  2023/08/11 14:22
     */
    void delete(List<BizDeptAssSynergyIdParam> bizDeptAssSynergyIdParamList);

    /**
     * 获取业务表-绩效考核-部门协同分详情
     *
     * @author yong.ye
     * @date  2023/08/11 14:22
     */
    BizDeptAssSynergy detail(BizDeptAssSynergyIdParam bizDeptAssSynergyIdParam);

    /**
     * 获取业务表-绩效考核-部门协同分详情
     *
     * @author yong.ye
     * @date  2023/08/11 14:22
     **/
    BizDeptAssSynergy queryEntity(String id);
}
