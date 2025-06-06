package vip.xiaonuo.biz.modular.prj.fin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataSign;
import vip.xiaonuo.biz.modular.prj.fin.param.*;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;

import java.util.List;

/**
 * 财务数据汇总台账 - 转入签字费Service接口
 *
 * @author yy
 * @date  2025/06/05 17:27
 **/
public interface FinDataSignService extends IService<FinDataSign> {

    /**
     * 获取财务数据汇总台账 - 转入签字费分页
     *
     * @author yy
     * @date  2025/06/05 17:27
     */
    Page<FinDataSign> page(FinDataSignPageParam finDataSignPageParam);

    /**
     * 添加财务数据汇总台账 - 转入签字费
     *
     * @author yy
     * @date  2025/06/05 17:27
     */
    void add(FinDataSignAddParam finDataSignAddParam);

    /**
     * 编辑财务数据汇总台账 - 转入签字费
     *
     * @author yy
     * @date  2025/06/05 17:27
     */
    void edit(FinDataSignEditParam finDataSignEditParam);

    /**
     * 删除财务数据汇总台账 - 转入签字费
     *
     * @author yy
     * @date  2025/06/05 17:27
     */
    void delete(List<FinDataSignIdParam> finDataSignIdParamList);

    /**
     * 获取财务数据汇总台账 - 转入签字费详情
     *
     * @author yy
     * @date  2025/06/05 17:27
     */
    FinDataSign detail(FinDataSignIdParam finDataSignIdParam);

    /**
     * 获取财务数据汇总台账 - 转入签字费详情
     *
     * @author yy
     * @date  2025/06/05 17:27
     **/
    FinDataSign queryEntity(String id);

    ImportResult doImport(ImportParam<ImportFinDataSign> importParam);
}
