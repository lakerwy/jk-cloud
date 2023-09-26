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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.recruit.entity.BizResumePerser;
import vip.xiaonuo.biz.modular.recruit.param.BizResumePerserAddParam;
import vip.xiaonuo.biz.modular.recruit.param.BizResumePerserEditParam;
import vip.xiaonuo.biz.modular.recruit.param.BizResumePerserIdParam;
import vip.xiaonuo.biz.modular.recruit.param.BizResumePerserPageParam;

import java.util.List;

/**
 * 业务表-招聘-简历解析接口调用记录Service接口
 *
 * @author yong.ye
 * @date  2023/08/25 08:46
 **/
public interface BizResumePerserService extends IService<BizResumePerser> {

    /**
     * 获取业务表-招聘-简历解析接口调用记录分页
     *
     * @author yong.ye
     * @date  2023/08/25 08:46
     */
    Page<BizResumePerser> page(BizResumePerserPageParam bizResumePerserPageParam);

    /**
     * 添加业务表-招聘-简历解析接口调用记录
     *
     * @author yong.ye
     * @date  2023/08/25 08:46
     */
    void add(BizResumePerserAddParam bizResumePerserAddParam);

    /**
     * 编辑业务表-招聘-简历解析接口调用记录
     *
     * @author yong.ye
     * @date  2023/08/25 08:46
     */
    void edit(BizResumePerserEditParam bizResumePerserEditParam);

    /**
     * 删除业务表-招聘-简历解析接口调用记录
     *
     * @author yong.ye
     * @date  2023/08/25 08:46
     */
    void delete(List<BizResumePerserIdParam> bizResumePerserIdParamList);

    /**
     * 获取业务表-招聘-简历解析接口调用记录详情
     *
     * @author yong.ye
     * @date  2023/08/25 08:46
     */
    BizResumePerser detail(BizResumePerserIdParam bizResumePerserIdParam);

    /**
     * 获取业务表-招聘-简历解析接口调用记录详情
     *
     * @author yong.ye
     * @date  2023/08/25 08:46
     **/
    BizResumePerser queryEntity(String id);

    /**
     * 获取业务表-招聘-简历解析接口 剩余调用量
     *
     * @author yong.ye
     * @date  2023/08/25 08:46
     */
    BizResumePerser usageRemaining();
}
