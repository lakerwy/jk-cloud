package vip.xiaonuo.biz.modular.orgPerf.perfAss.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.entity.BizOrgPerfAss;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizOrgPerfAssAddParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizOrgPerfAssEditParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizOrgPerfAssIdParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizOrgPerfAssPageParam;

import java.util.List;

/**
 * 子公司及生产部门绩效考核-绩效评估Service接口
 *
 * @author pzh
 * @date  2025/06/25 15:01
 **/
public interface BizOrgPerfAssService extends IService<BizOrgPerfAss> {

    /**
     * 获取子公司及生产部门绩效考核-绩效评估分页
     *
     * @author pzh
     * @date  2025/06/25 15:01
     */
    Page<BizOrgPerfAss> page(BizOrgPerfAssPageParam bizOrgPerfAssPageParam);

    /**
     * 添加子公司及生产部门绩效考核-绩效评估
     *
     * @author pzh
     * @date  2025/06/25 15:01
     */
    void add(BizOrgPerfAssAddParam bizOrgPerfAssAddParam);

    /**
     * 编辑子公司及生产部门绩效考核-绩效评估
     *
     * @author pzh
     * @date  2025/06/25 15:01
     */
    void edit(BizOrgPerfAssEditParam bizOrgPerfAssEditParam);

    /**
     * 删除子公司及生产部门绩效考核-绩效评估
     *
     * @author pzh
     * @date  2025/06/25 15:01
     */
    void delete(List<BizOrgPerfAssIdParam> bizOrgPerfAssIdParamList);

    /**
     * 获取子公司及生产部门绩效考核-绩效评估详情
     *
     * @author pzh
     * @date  2025/06/25 15:01
     */
    BizOrgPerfAss detail(BizOrgPerfAssIdParam bizOrgPerfAssIdParam);

    /**
     * 获取子公司及生产部门绩效考核-绩效评估详情
     *
     * @author pzh
     * @date  2025/06/25 15:01
     **/
    BizOrgPerfAss queryEntity(String id);
}
