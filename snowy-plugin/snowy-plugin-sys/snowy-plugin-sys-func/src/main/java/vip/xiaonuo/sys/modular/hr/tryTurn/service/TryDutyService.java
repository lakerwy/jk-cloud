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
import vip.xiaonuo.sys.modular.hr.tryTurn.entity.TryDuty;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryDutyAddParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryDutyEditParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryDutyIdParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryDutyPageParam;

import java.util.List;

/**
 * HR - 试用期考核责任Service接口
 *
 * @author yong.ye
 * @date  2024/06/12 10:13
 **/
public interface TryDutyService extends IService<TryDuty> {

    /**
     * 获取HR - 试用期考核责任分页
     *
     * @author yong.ye
     * @date  2024/06/12 10:13
     */
    Page<TryDuty> page(TryDutyPageParam tryDutyPageParam);

    /**
     * 添加HR - 试用期考核责任
     *
     * @author yong.ye
     * @date  2024/06/12 10:13
     */
    void add(TryDutyAddParam tryDutyAddParam);

    /**
     * 编辑HR - 试用期考核责任
     *
     * @author yong.ye
     * @date  2024/06/12 10:13
     */
    void edit(TryDutyEditParam tryDutyEditParam);

    /**
     * 删除HR - 试用期考核责任
     *
     * @author yong.ye
     * @date  2024/06/12 10:13
     */
    void delete(List<TryDutyIdParam> tryDutyIdParamList);

    /**
     * 获取HR - 试用期考核责任详情
     *
     * @author yong.ye
     * @date  2024/06/12 10:13
     */
    TryDuty detail(TryDutyIdParam tryDutyIdParam);

    /**
     * 获取HR - 试用期考核责任详情
     *
     * @author yong.ye
     * @date  2024/06/12 10:13
     **/
    TryDuty queryEntity(String id);
}
