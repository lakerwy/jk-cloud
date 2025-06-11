package vip.xiaonuo.biz.modular.prj.fin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataDbd;
import vip.xiaonuo.biz.modular.prj.fin.param.*;
import vip.xiaonuo.biz.modular.prj.fin.vo.FinDataDbdPageTotal;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;

import java.util.List;

/**
 * 财务数据汇总台账 - 拨付明细Service接口
 *
 * @author yy
 * @date  2025/06/05 17:23
 **/
public interface FinDataDbdService extends IService<FinDataDbd> {

    /**
     * 获取财务数据汇总台账 - 拨付明细分页
     *
     * @author yy
     * @date  2025/06/05 17:23
     */
    Page<FinDataDbd> page(FinDataDbdPageParam finDataDbdPageParam);

    /**
     * 添加财务数据汇总台账 - 拨付明细
     *
     * @author yy
     * @date  2025/06/05 17:23
     */
    void add(FinDataDbdAddParam finDataDbdAddParam);

    /**
     * 编辑财务数据汇总台账 - 拨付明细
     *
     * @author yy
     * @date  2025/06/05 17:23
     */
    void edit(FinDataDbdEditParam finDataDbdEditParam);

    /**
     * 删除财务数据汇总台账 - 拨付明细
     *
     * @author yy
     * @date  2025/06/05 17:23
     */
    void delete(List<FinDataDbdIdParam> finDataDbdIdParamList);

    /**
     * 获取财务数据汇总台账 - 拨付明细详情
     *
     * @author yy
     * @date  2025/06/05 17:23
     */
    FinDataDbd detail(FinDataDbdIdParam finDataDbdIdParam);

    /**
     * 获取财务数据汇总台账 - 拨付明细详情
     *
     * @author yy
     * @date  2025/06/05 17:23
     **/
    FinDataDbd queryEntity(String id);

    ImportResult doImport(ImportParam<ImportFinDataDbd> importParam);

    FinDataDbdPageTotal pageTotal(FinDataDbdPageParam finDataDbdPageParam);
}
