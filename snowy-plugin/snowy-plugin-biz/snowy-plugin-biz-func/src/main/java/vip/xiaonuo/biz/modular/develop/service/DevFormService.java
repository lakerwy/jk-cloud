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
package vip.xiaonuo.biz.modular.develop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.develop.entity.DevForm;
import vip.xiaonuo.biz.modular.develop.param.DevFormAddParam;
import vip.xiaonuo.biz.modular.develop.param.DevFormEditParam;
import vip.xiaonuo.biz.modular.develop.param.DevFormIdParam;
import vip.xiaonuo.biz.modular.develop.param.DevFormPageParam;
import vip.xiaonuo.biz.modular.develop.vo.DevFormVo;

import java.util.List;

/**
 * 需求单 - 发起流程Service接口
 *
 * @author yong.ye
 * @date  2024/05/27 16:08
 **/
public interface DevFormService extends IService<DevForm> {

    /**
     * 获取需求单 - 发起流程分页
     *
     * @author yong.ye
     * @date  2024/05/27 16:08
     */
    Page<DevFormVo> page(DevFormPageParam devFormPageParam);

    /**
     * 添加需求单 - 发起流程
     *
     * @author yong.ye
     * @date  2024/05/27 16:08
     */
    void add(DevFormAddParam devFormAddParam);

    /**
     * 编辑需求单 - 发起流程
     *
     * @author yong.ye
     * @date  2024/05/27 16:08
     */
    void edit(DevFormEditParam devFormEditParam);

    /**
     * 删除需求单 - 发起流程
     *
     * @author yong.ye
     * @date  2024/05/27 16:08
     */
    void delete(List<DevFormIdParam> devFormIdParamList);

    /**
     * 获取需求单 - 发起流程详情
     *
     * @author yong.ye
     * @date  2024/05/27 16:08
     */
    DevFormVo detail(DevFormIdParam devFormIdParam);

    /**
     * 获取需求单 - 发起流程详情
     *
     * @author yong.ye
     * @date  2024/05/27 16:08
     **/
    DevForm queryEntity(String id);
}
