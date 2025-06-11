package vip.xiaonuo.biz.modular.prj.fin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataPpc;
import vip.xiaonuo.biz.modular.prj.fin.param.*;
import vip.xiaonuo.biz.modular.prj.fin.vo.FinDataPpcPageTotal;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;

import java.util.List;

/**
 * 财务数据汇总台账 - 项目回款Service接口
 *
 * @author yy
 * @date  2025/06/05 17:26
 **/
public interface FinDataPpcService extends IService<FinDataPpc> {

    /**
     * 获取财务数据汇总台账 - 项目回款分页
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    Page<FinDataPpc> page(FinDataPpcPageParam finDataPpcPageParam);

    /**
     * 添加财务数据汇总台账 - 项目回款
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    void add(FinDataPpcAddParam finDataPpcAddParam);

    /**
     * 编辑财务数据汇总台账 - 项目回款
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    void edit(FinDataPpcEditParam finDataPpcEditParam);

    /**
     * 删除财务数据汇总台账 - 项目回款
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    void delete(List<FinDataPpcIdParam> finDataPpcIdParamList);

    /**
     * 获取财务数据汇总台账 - 项目回款详情
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    FinDataPpc detail(FinDataPpcIdParam finDataPpcIdParam);

    /**
     * 获取财务数据汇总台账 - 项目回款详情
     *
     * @author yy
     * @date  2025/06/05 17:26
     **/
    FinDataPpc queryEntity(String id);

    ImportResult doImport(ImportParam<ImportFinDataPpc> importParam);

    FinDataPpcPageTotal pageTotal(FinDataPpcPageParam finDataPpcPageParam);
}
