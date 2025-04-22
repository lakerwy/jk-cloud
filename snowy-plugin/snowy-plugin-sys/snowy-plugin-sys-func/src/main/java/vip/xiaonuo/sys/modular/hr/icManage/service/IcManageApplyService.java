package vip.xiaonuo.sys.modular.hr.icManage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.icManage.entity.IcManageApply;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageApplyAddParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageApplyEditParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageApplyIdParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageApplyPageParam;

import java.util.List;

/**
 * 工商信息新增|变更|注销--流程申请Service接口
 *
 * @author yy
 * @date  2025/04/21 15:58
 **/
public interface IcManageApplyService extends IService<IcManageApply> {

    /**
     * 获取工商信息新增|变更|注销--流程申请分页
     *
     * @author yy
     * @date  2025/04/21 15:58
     */
    Page<IcManageApply> page(IcManageApplyPageParam icManageApplyPageParam);

    /**
     * 添加工商信息新增|变更|注销--流程申请
     *
     * @author yy
     * @date  2025/04/21 15:58
     */
    void add(IcManageApplyAddParam icManageApplyAddParam);

    /**
     * 编辑工商信息新增|变更|注销--流程申请
     *
     * @author yy
     * @date  2025/04/21 15:58
     */
    void edit(IcManageApplyEditParam icManageApplyEditParam);

    /**
     * 删除工商信息新增|变更|注销--流程申请
     *
     * @author yy
     * @date  2025/04/21 15:58
     */
    void delete(List<IcManageApplyIdParam> icManageApplyIdParamList);

    /**
     * 获取工商信息新增|变更|注销--流程申请详情
     *
     * @author yy
     * @date  2025/04/21 15:58
     */
    IcManageApply detail(IcManageApplyIdParam icManageApplyIdParam);

    /**
     * 获取工商信息新增|变更|注销--流程申请详情
     *
     * @author yy
     * @date  2025/04/21 15:58
     **/
    IcManageApply queryEntity(String id);
}
