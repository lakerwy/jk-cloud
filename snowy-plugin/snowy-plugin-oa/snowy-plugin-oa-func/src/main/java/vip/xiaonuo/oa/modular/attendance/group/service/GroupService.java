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
package vip.xiaonuo.oa.modular.attendance.group.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.oa.modular.attendance.group.entity.Group;
import vip.xiaonuo.oa.modular.attendance.group.param.*;
import vip.xiaonuo.oa.modular.attendance.group.entity.Group;
import vip.xiaonuo.oa.modular.attendance.group.param.GroupIdParam;
import vip.xiaonuo.oa.modular.attendance.group.param.GroupPageParam;
import vip.xiaonuo.oa.modular.attendance.group.param.GroupAddParam;
import vip.xiaonuo.oa.modular.attendance.group.vo.GroupDetailVo;

import java.util.List;

/**
 * 考勤-考勤组Service接口
 *
 * @author mayike
 * @date  2024/06/05 15:51
 **/
public interface GroupService extends IService<Group> {

    /**
     * 获取考勤-考勤组分页
     *
     * @author mayike
     * @date  2024/06/05 15:51
     */
    Page<Group> page(GroupPageParam groupPageParam);

    /**
     * 添加考勤-考勤组
     *
     * @author mayike
     * @date  2024/06/05 15:51
     */
    void add(GroupAddParam groupAddParam);

    /**
     * 编辑考勤-考勤组
     *
     * @author mayike
     * @date  2024/06/05 15:51
     */
    void edit(GroupEditParam groupEditParam);

    /**
     * 删除考勤-考勤组
     *
     * @author mayike
     * @date  2024/06/05 15:51
     */
    void delete(List<GroupIdParam> groupIdParamList);

    /**
     * 获取考勤-考勤组详情
     *
     * @author mayike
     * @date  2024/06/05 15:51
     */
    GroupDetailVo detail(GroupIdParam groupIdParam);

    /**
     * 获取考勤-考勤组详情
     *
     * @author mayike
     * @date  2024/06/05 15:51
     **/
    Group queryEntity(String id);

    Group getGroupByUserId(String userId);

    void setGroup(GroupAttObjectParam groupAttObjectParam);
}
