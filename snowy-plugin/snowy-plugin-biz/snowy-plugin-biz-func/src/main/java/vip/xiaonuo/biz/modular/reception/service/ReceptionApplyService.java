package vip.xiaonuo.biz.modular.reception.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.reception.entity.ReceptionApply;
import vip.xiaonuo.biz.modular.reception.param.ReceptionApplyAddParam;
import vip.xiaonuo.biz.modular.reception.param.ReceptionApplyEditParam;
import vip.xiaonuo.biz.modular.reception.param.ReceptionApplyIdParam;
import vip.xiaonuo.biz.modular.reception.param.ReceptionApplyPageParam;

import java.util.List;

/**
 * 接待申请Service接口
 *
 * @author pzh
 * @date  2025/05/28 10:15
 **/
public interface ReceptionApplyService extends IService<ReceptionApply> {

    /**
     * 获取接待申请分页
     *
     * @author pzh
     * @date  2025/05/28 10:15
     */
    Page<ReceptionApply> page(ReceptionApplyPageParam receptionApplyPageParam);

    /**
     * 添加接待申请
     *
     * @author pzh
     * @date  2025/05/28 10:15
     */
    void add(ReceptionApplyAddParam receptionApplyAddParam);

    /**
     * 编辑接待申请
     *
     * @author pzh
     * @date  2025/05/28 10:15
     */
    void edit(ReceptionApplyEditParam receptionApplyEditParam);

    /**
     * 删除接待申请
     *
     * @author pzh
     * @date  2025/05/28 10:15
     */
    void delete(List<ReceptionApplyIdParam> receptionApplyIdParamList);

    /**
     * 获取接待申请详情
     *
     * @author pzh
     * @date  2025/05/28 10:15
     */
    ReceptionApply detail(ReceptionApplyIdParam receptionApplyIdParam);

    /**
     * 获取接待申请详情
     *
     * @author pzh
     * @date  2025/05/28 10:15
     **/
    ReceptionApply queryEntity(String id);
}
