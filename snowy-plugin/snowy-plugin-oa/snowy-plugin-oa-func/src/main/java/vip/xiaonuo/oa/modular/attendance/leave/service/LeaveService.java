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
package vip.xiaonuo.oa.modular.attendance.leave.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.oa.modular.attendance.leave.entity.Leave;
import vip.xiaonuo.oa.modular.attendance.leave.param.LeaveAddParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.LeaveEditParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.LeaveIdParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.LeavePageParam;

import java.util.List;

/**
 * 请假记录表Service接口
 *
 * @author mayike
 * @date  2024/06/24 16:55
 **/
public interface LeaveService extends IService<Leave> {

    /**
     * 获取请假记录表分页
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    Page<Leave> page(LeavePageParam leavePageParam);

    /**
     * 添加请假记录表
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    void add(LeaveAddParam leaveAddParam);

    /**
     * 编辑请假记录表
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    void edit(LeaveEditParam leaveEditParam);

    /**
     * 删除请假记录表
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    void delete(List<LeaveIdParam> leaveIdParamList);

    /**
     * 获取请假记录表详情
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    Leave detail(LeaveIdParam leaveIdParam);

    /**
     * 获取请假记录表详情
     *
     * @author mayike
     * @date  2024/06/24 16:55
     **/
    Leave queryEntity(String id);
}
