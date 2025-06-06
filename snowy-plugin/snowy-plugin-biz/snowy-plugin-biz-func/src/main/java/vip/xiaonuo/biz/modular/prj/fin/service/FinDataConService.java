package vip.xiaonuo.biz.modular.prj.fin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataCon;
import vip.xiaonuo.biz.modular.prj.fin.param.*;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;

import java.util.List;

/**
 * 财务数据汇总台账 - 施工Service接口
 *
 * @author yy
 * @date  2025/06/05 17:22
 **/
public interface FinDataConService extends IService<FinDataCon> {

    /**
     * 获取财务数据汇总台账 - 施工分页
     *
     * @author yy
     * @date  2025/06/05 17:22
     */
    Page<FinDataCon> page(FinDataConPageParam finDataConPageParam);

    /**
     * 添加财务数据汇总台账 - 施工
     *
     * @author yy
     * @date  2025/06/05 17:22
     */
    void add(FinDataConAddParam finDataConAddParam);

    /**
     * 编辑财务数据汇总台账 - 施工
     *
     * @author yy
     * @date  2025/06/05 17:22
     */
    void edit(FinDataConEditParam finDataConEditParam);

    /**
     * 删除财务数据汇总台账 - 施工
     *
     * @author yy
     * @date  2025/06/05 17:22
     */
    void delete(List<FinDataConIdParam> finDataConIdParamList);

    /**
     * 获取财务数据汇总台账 - 施工详情
     *
     * @author yy
     * @date  2025/06/05 17:22
     */
    FinDataCon detail(FinDataConIdParam finDataConIdParam);

    /**
     * 获取财务数据汇总台账 - 施工详情
     *
     * @author yy
     * @date  2025/06/05 17:22
     **/
    FinDataCon queryEntity(String id);

    ImportResult doImport(ImportParam<ImportFinDataCon> importParam);
}
