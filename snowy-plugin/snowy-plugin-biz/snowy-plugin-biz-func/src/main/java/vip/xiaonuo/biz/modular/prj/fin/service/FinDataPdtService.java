package vip.xiaonuo.biz.modular.prj.fin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataPdt;
import vip.xiaonuo.biz.modular.prj.fin.param.*;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;

import java.util.List;

/**
 * 财务数据汇总台账 - 生产院Service接口
 *
 * @author yy
 * @date  2025/06/05 17:26
 **/
public interface FinDataPdtService extends IService<FinDataPdt> {

    /**
     * 获取财务数据汇总台账 - 生产院分页
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    Page<FinDataPdt> page(FinDataPdtPageParam finDataPdtPageParam);

    /**
     * 添加财务数据汇总台账 - 生产院
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    void add(FinDataPdtAddParam finDataPdtAddParam);

    /**
     * 编辑财务数据汇总台账 - 生产院
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    void edit(FinDataPdtEditParam finDataPdtEditParam);

    /**
     * 删除财务数据汇总台账 - 生产院
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    void delete(List<FinDataPdtIdParam> finDataPdtIdParamList);

    /**
     * 获取财务数据汇总台账 - 生产院详情
     *
     * @author yy
     * @date  2025/06/05 17:26
     */
    FinDataPdt detail(FinDataPdtIdParam finDataPdtIdParam);

    /**
     * 获取财务数据汇总台账 - 生产院详情
     *
     * @author yy
     * @date  2025/06/05 17:26
     **/
    FinDataPdt queryEntity(String id);

    ImportResult doImport(ImportParam<ImportFinDataPdt> importParam);
}
