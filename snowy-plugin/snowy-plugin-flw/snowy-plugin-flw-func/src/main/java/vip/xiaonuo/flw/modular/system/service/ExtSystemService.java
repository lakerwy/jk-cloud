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
package vip.xiaonuo.flw.modular.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.flw.modular.system.entity.ExtSystem;
import vip.xiaonuo.flw.modular.system.param.ExtSystemIdParam;
import vip.xiaonuo.flw.modular.system.param.ExtSystemPageParam;

/**
 * 各系统扩展数据表Service接口
 *
 * @author hxw
 * @date  2024/05/30 16:01
 **/
public interface ExtSystemService extends IService<ExtSystem> {

    /**
     * 获取各系统扩展数据表分页
     *
     * @author hxw
     * @date  2024/05/30 16:01
     */
    Page<ExtSystem> page(ExtSystemPageParam extSystemPageParam);

    /**
     * 获取各系统扩展数据表详情
     *
     * @author hxw
     * @date  2024/05/30 16:01
     */
    ExtSystem detail(ExtSystemIdParam extSystemIdParam);

    /**
     * 获取各系统扩展数据表详情
     *
     * @author hxw
     * @date  2024/05/30 16:01
     **/
    ExtSystem queryEntity(String id);
}
