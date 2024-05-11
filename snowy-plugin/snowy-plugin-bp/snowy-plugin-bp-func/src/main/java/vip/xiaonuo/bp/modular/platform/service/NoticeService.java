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
import vip.xiaonuo.bp.modular.platform.entity.Notice;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeAddParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeEditParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticeIdParam;
import vip.xiaonuo.bp.modular.platform.param.notice.NoticePageParam;
import vip.xiaonuo.bp.modular.platform.vo.NoticeVo;

import java.util.List;

/**
 * 招采管理 - 竞价公告|询价公告|招标公告Service接口
 *
 * @author yong.ye
 * @date  2024/05/07 16:36
 **/
public interface NoticeService extends IService<Notice> {

    /**
     * 获取招采管理 - 竞价公告|询价公告|招标公告分页
     *
     * @author yong.ye
     * @date  2024/05/07 16:36
     */
    Page<NoticeVo> page(NoticePageParam noticePageParam);

    /**
     * 添加招采管理 - 竞价公告|询价公告|招标公告
     *
     * @author yong.ye
     * @date  2024/05/07 16:36
     */
    void add(NoticeAddParam noticeAddParam);

    /**
     * 编辑招采管理 - 竞价公告|询价公告|招标公告
     *
     * @author yong.ye
     * @date  2024/05/07 16:36
     */
    void edit(NoticeEditParam noticeEditParam);

    /**
     * 删除招采管理 - 竞价公告|询价公告|招标公告
     *
     * @author yong.ye
     * @date  2024/05/07 16:36
     */
    void delete(List<NoticeIdParam> noticeIdParamList);

    /**
     * 获取招采管理 - 竞价公告|询价公告|招标公告详情
     *
     * @author yong.ye
     * @date  2024/05/07 16:36
     */
    NoticeVo detail(NoticeIdParam noticeIdParam);

    /**
     * 获取招采管理 - 竞价公告|询价公告|招标公告详情
     *
     * @author yong.ye
     * @date  2024/05/07 16:36
     **/
    Notice queryEntity(String id);
}
