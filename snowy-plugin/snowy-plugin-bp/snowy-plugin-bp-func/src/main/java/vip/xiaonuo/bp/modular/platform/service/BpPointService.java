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
package vip.xiaonuo.bp.modular.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.bp.modular.platform.entity.BpPoint;
import vip.xiaonuo.bp.modular.platform.param.point.BpPointAddParam;
import vip.xiaonuo.bp.modular.platform.param.point.BpPointEditParam;
import vip.xiaonuo.bp.modular.platform.param.point.BpPointIdParam;
import vip.xiaonuo.bp.modular.platform.param.point.BpPointPageParam;

import java.util.List;

/**
 * 招采管理 - 操作指引Service接口
 *
 * @author yong.ye
 * @date  2024/05/29 16:22
 **/
public interface BpPointService extends IService<BpPoint> {

    /**
     * 获取招采管理 - 操作指引分页
     *
     * @author yong.ye
     * @date  2024/05/29 16:22
     */
    Page<BpPoint> page(BpPointPageParam bpPointPageParam);

    /**
     * 添加招采管理 - 操作指引
     *
     * @author yong.ye
     * @date  2024/05/29 16:22
     */
    void add(BpPointAddParam bpPointAddParam);

    /**
     * 编辑招采管理 - 操作指引
     *
     * @author yong.ye
     * @date  2024/05/29 16:22
     */
    void edit(BpPointEditParam bpPointEditParam);

    /**
     * 删除招采管理 - 操作指引
     *
     * @author yong.ye
     * @date  2024/05/29 16:22
     */
    void delete(List<BpPointIdParam> bpPointIdParamList);

    /**
     * 获取招采管理 - 操作指引详情
     *
     * @author yong.ye
     * @date  2024/05/29 16:22
     */
    BpPoint detail(BpPointIdParam bpPointIdParam);

    /**
     * 获取招采管理 - 操作指引详情
     *
     * @author yong.ye
     * @date  2024/05/29 16:22
     **/
    BpPoint queryEntity(String id);
}
