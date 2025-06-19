package vip.xiaonuo.biz.modular.prj.por.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.por.entity.PorMsg;
import vip.xiaonuo.biz.modular.prj.por.param.PorMsgAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorMsgEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorMsgIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorMsgPageParam;

import java.util.List;

/**
 * 项目产值台账 - 收款提醒Service接口
 *
 * @author yy
 * @date  2025/06/18 16:13
 **/
public interface PorMsgService extends IService<PorMsg> {

    /**
     * 获取项目产值台账 - 收款提醒分页
     *
     * @author yy
     * @date  2025/06/18 16:13
     */
    Page<PorMsg> page(PorMsgPageParam porMsgPageParam);

    /**
     * 添加项目产值台账 - 收款提醒
     *
     * @author yy
     * @date  2025/06/18 16:13
     */
    void add(PorMsgAddParam porMsgAddParam);

    /**
     * 编辑项目产值台账 - 收款提醒
     *
     * @author yy
     * @date  2025/06/18 16:13
     */
    void edit(PorMsgEditParam porMsgEditParam);

    /**
     * 删除项目产值台账 - 收款提醒
     *
     * @author yy
     * @date  2025/06/18 16:13
     */
    void delete(List<PorMsgIdParam> porMsgIdParamList);

    /**
     * 获取项目产值台账 - 收款提醒详情
     *
     * @author yy
     * @date  2025/06/18 16:13
     */
    PorMsg detail(PorMsgIdParam porMsgIdParam);

    /**
     * 获取项目产值台账 - 收款提醒详情
     *
     * @author yy
     * @date  2025/06/18 16:13
     **/
    PorMsg queryEntity(String id);
}
