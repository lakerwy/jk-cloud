package vip.xiaonuo.biz.modular.prj.por.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.hetong.vo.HetongDetailVo;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.Subcontract;
import vip.xiaonuo.biz.modular.pis.entity.PisAllotment;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataPpc;
import vip.xiaonuo.biz.modular.prj.por.entity.*;
import vip.xiaonuo.biz.modular.prj.por.param.*;
import vip.xiaonuo.biz.modular.prj.por.vo.*;

import java.util.List;

/**
 * 项目产值台账 - 项目管理Service接口
 *
 * @author yy
 * @date  2025/06/03 09:39
 **/
public interface PorProjectService extends IService<PorProject> {

    /**
     * 获取项目产值台账 - 项目管理分页
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    Page<PorProjectVo> page(PorProjectPageParam porProjectPageParam);

    /**
     * 添加项目产值台账 - 项目管理
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    void add(PorProjectAddParam porProjectAddParam);

    /**
     * 编辑项目产值台账 - 项目管理
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    void edit(PorProjectEditParam porProjectEditParam);

    /**
     * 删除项目产值台账 - 项目管理
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    void delete(List<PorProjectIdParam> porProjectIdParamList);

    /**
     * 获取项目产值台账 - 项目管理详情
     *
     * @author yy
     * @date  2025/06/03 09:39
     */
    PorProjectVo detail(PorProjectIdParam porProjectIdParam);

    /**
     * 获取项目产值台账 - 项目管理详情
     *
     * @author yy
     * @date  2025/06/03 09:39
     **/
    PorProject queryEntity(String id);

    Page<PorProjectVo> pageList(PorProjectPageParam porProjectPageParam);

    List<HetongDetailVo> getHtListByPrjId(PorProjectParam param);

    List<RecData> getRecListByPrjId(PorProjectParam param);

    List<PorOwnerSettleVo> getOwnerListByPrjId(PorProjectParam param);

    List<FinDataPpc> getPpcListByPrjId(PorProjectParam param);

    List<Subcontract> getSubListByPrjId(PorProjectParam param);

    List<PorReport> getReportListByPrjId(PorProjectParam param);

    List<PorTask> getTaskListByPrjId(PorProjectParam param);

    List<PorSubSettleVo> getSubSettleListByPrjId(PorProjectParam param);

    List<PisData> getPisListByPrjId(PorProjectParam param);


    List<PisData> getPisData(List<PisData> result, List<PisAllotment> pisAllotmentList, List<Subcontract> subcontractList);

    List<PorRpmVo> getRpmListByPrjId(PorProjectParam param);

    List<PorSignVo> getSignListByPrjId(PorProjectParam param);

    List<PorSiteFeeVo> getSiteListByPrjId(PorProjectParam param);

    List<PorMember> getMemberListByPrjId(PorPrjParam param);

    List<PorWarn> getWarnListByPrjId(PorPrjParam param);

    void updateRec(RecParam param);

    void updatePorWarn(RecParam param);
}
