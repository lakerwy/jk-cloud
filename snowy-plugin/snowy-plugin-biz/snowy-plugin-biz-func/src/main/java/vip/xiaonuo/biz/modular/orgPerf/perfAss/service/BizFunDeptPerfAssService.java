package vip.xiaonuo.biz.modular.orgPerf.perfAss.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.entity.BizFunDeptPerfAss;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssAddParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssEditParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssIdParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssPageParam;

import java.util.List;

/**
 * 职能部门-绩效评估Service接口
 *
 * @author pzh
 * @date  2025/06/30 10:41
 **/
public interface BizFunDeptPerfAssService extends IService<BizFunDeptPerfAss> {

    /**
     * 获取职能部门-绩效评估分页
     *
     * @author pzh
     * @date  2025/06/30 10:41
     */
    Page<BizFunDeptPerfAss> page(BizFunDeptPerfAssPageParam bizFunDeptPerfAssPageParam);

    /**
     * 添加职能部门-绩效评估
     *
     * @author pzh
     * @date  2025/06/30 10:41
     */
    void add(BizFunDeptPerfAssAddParam bizFunDeptPerfAssAddParam);

    /**
     * 编辑职能部门-绩效评估
     *
     * @author pzh
     * @date  2025/06/30 10:41
     */
    void edit(BizFunDeptPerfAssEditParam bizFunDeptPerfAssEditParam);

    /**
     * 删除职能部门-绩效评估
     *
     * @author pzh
     * @date  2025/06/30 10:41
     */
    void delete(List<BizFunDeptPerfAssIdParam> bizFunDeptPerfAssIdParamList);

    /**
     * 获取职能部门-绩效评估详情
     *
     * @author pzh
     * @date  2025/06/30 10:41
     */
    BizFunDeptPerfAss detail(BizFunDeptPerfAssIdParam bizFunDeptPerfAssIdParam);

    /**
     * 获取职能部门-绩效评估详情
     *
     * @author pzh
     * @date  2025/06/30 10:41
     **/
    BizFunDeptPerfAss queryEntity(String id);
}
