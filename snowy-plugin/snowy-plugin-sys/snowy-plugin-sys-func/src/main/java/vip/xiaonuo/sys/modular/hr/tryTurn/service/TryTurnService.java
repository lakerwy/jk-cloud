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
package vip.xiaonuo.sys.modular.hr.tryTurn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.tryTurn.entity.TryTurn;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryTurnAddParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryTurnEditParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryTurnIdParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryTurnPageParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.vo.TryTurnVo;

import java.util.List;

/**
 * HR - 试用期转正考核Service接口
 *
 * @author yong.ye
 * @date  2024/06/12 10:14
 **/
public interface TryTurnService extends IService<TryTurn> {

    /**
     * 获取HR - 试用期转正考核分页
     *
     * @author yong.ye
     * @date  2024/06/12 10:14
     */
    Page<TryTurnVo> page(TryTurnPageParam tryTurnPageParam);

    /**
     * 添加HR - 试用期转正考核
     *
     * @author yong.ye
     * @date  2024/06/12 10:14
     */
    void add(TryTurnAddParam tryTurnAddParam);

    /**
     * 编辑HR - 试用期转正考核
     *
     * @author yong.ye
     * @date  2024/06/12 10:14
     */
    void edit(TryTurnEditParam tryTurnEditParam);

    /**
     * 删除HR - 试用期转正考核
     *
     * @author yong.ye
     * @date  2024/06/12 10:14
     */
    void delete(List<TryTurnIdParam> tryTurnIdParamList);

    /**
     * 获取HR - 试用期转正考核详情
     *
     * @author yong.ye
     * @date  2024/06/12 10:14
     */
    TryTurnVo detail(TryTurnIdParam tryTurnIdParam);

    /**
     * 获取HR - 试用期转正考核详情
     *
     * @author yong.ye
     * @date  2024/06/12 10:14
     **/
    TryTurn queryEntity(String id);
}
