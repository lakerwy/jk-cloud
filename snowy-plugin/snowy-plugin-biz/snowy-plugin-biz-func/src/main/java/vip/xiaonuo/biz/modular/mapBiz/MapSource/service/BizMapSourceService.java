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
package vip.xiaonuo.biz.modular.mapBiz.MapSource.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.mapBiz.MapSource.entity.BizMapSource;
import vip.xiaonuo.biz.modular.mapBiz.MapSource.param.BizMapSourceAddParam;
import vip.xiaonuo.biz.modular.mapBiz.MapSource.param.BizMapSourceEditParam;
import vip.xiaonuo.biz.modular.mapBiz.MapSource.param.BizMapSourceIdParam;
import vip.xiaonuo.biz.modular.mapBiz.MapSource.param.BizMapSourcePageParam;

import java.util.List;

/**
 * 图审 - 原始资料录入 Service接口
 *
 * @author yong.ye
 * @date  2024/06/11 17:36
 **/
public interface BizMapSourceService extends IService<BizMapSource> {

    /**
     * 获取图审 - 原始资料录入 分页
     *
     * @author yong.ye
     * @date  2024/06/11 17:36
     */
    Page<BizMapSource> page(BizMapSourcePageParam bizMapSourcePageParam);

    /**
     * 添加图审 - 原始资料录入 
     *
     * @author yong.ye
     * @date  2024/06/11 17:36
     */
    void add(BizMapSourceAddParam bizMapSourceAddParam);

    /**
     * 编辑图审 - 原始资料录入 
     *
     * @author yong.ye
     * @date  2024/06/11 17:36
     */
    void edit(BizMapSourceEditParam bizMapSourceEditParam);

    /**
     * 删除图审 - 原始资料录入 
     *
     * @author yong.ye
     * @date  2024/06/11 17:36
     */
    void delete(List<BizMapSourceIdParam> bizMapSourceIdParamList);

    /**
     * 获取图审 - 原始资料录入 详情
     *
     * @author yong.ye
     * @date  2024/06/11 17:36
     */
    BizMapSource detail(BizMapSourceIdParam bizMapSourceIdParam);

    /**
     * 获取图审 - 原始资料录入 详情
     *
     * @author yong.ye
     * @date  2024/06/11 17:36
     **/
    BizMapSource queryEntity(String id);
}
