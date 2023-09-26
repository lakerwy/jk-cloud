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
import vip.xiaonuo.biz.modular.task.entity.BizGroupPerformance;
import vip.xiaonuo.biz.modular.task.param.BizGroupPerformanceAddParam;
import vip.xiaonuo.biz.modular.task.param.BizGroupPerformanceEditParam;
import vip.xiaonuo.biz.modular.task.param.BizGroupPerformanceIdParam;
import vip.xiaonuo.biz.modular.task.param.BizGroupPerformancePageParam;

import java.util.List;

/**
 * 业务表-绩效考核-集团经营业绩绩点分Service接口
 *
 * @author yong.ye
 * @date  2023/08/11 14:07
 **/
public interface BizGroupPerformanceService extends IService<BizGroupPerformance> {

    /**
     * 获取业务表-绩效考核-集团经营业绩绩点分分页
     *
     * @author yong.ye
     * @date  2023/08/11 14:07
     */
    Page<BizGroupPerformance> page(BizGroupPerformancePageParam bizGroupPerformancePageParam);

    /**
     * 添加业务表-绩效考核-集团经营业绩绩点分
     *
     * @author yong.ye
     * @date  2023/08/11 14:07
     */
    void add(BizGroupPerformanceAddParam bizGroupPerformanceAddParam);

    /**
     * 编辑业务表-绩效考核-集团经营业绩绩点分
     *
     * @author yong.ye
     * @date  2023/08/11 14:07
     */
    void edit(BizGroupPerformanceEditParam bizGroupPerformanceEditParam);

    /**
     * 删除业务表-绩效考核-集团经营业绩绩点分
     *
     * @author yong.ye
     * @date  2023/08/11 14:07
     */
    void delete(List<BizGroupPerformanceIdParam> bizGroupPerformanceIdParamList);

    /**
     * 获取业务表-绩效考核-集团经营业绩绩点分详情
     *
     * @author yong.ye
     * @date  2023/08/11 14:07
     */
    BizGroupPerformance detail(BizGroupPerformanceIdParam bizGroupPerformanceIdParam);

    /**
     * 获取业务表-绩效考核-集团经营业绩绩点分详情
     *
     * @author yong.ye
     * @date  2023/08/11 14:07
     **/
    BizGroupPerformance queryEntity(String id);
}
