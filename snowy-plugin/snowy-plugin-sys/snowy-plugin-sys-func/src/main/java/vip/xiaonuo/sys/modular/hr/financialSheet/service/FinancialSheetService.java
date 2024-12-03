package vip.xiaonuo.sys.modular.hr.financialSheet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.financialSheet.entity.FinancialSheet;
import vip.xiaonuo.sys.modular.hr.financialSheet.param.FinancialSheetAddParam;
import vip.xiaonuo.sys.modular.hr.financialSheet.param.FinancialSheetEditParam;
import vip.xiaonuo.sys.modular.hr.financialSheet.param.FinancialSheetIdParam;
import vip.xiaonuo.sys.modular.hr.financialSheet.param.FinancialSheetPageParam;

import java.util.List;

/**
 * 财务状况表Service接口
 *
 * @author pzh
 * @date  2024/10/18 15:53
 **/
public interface FinancialSheetService extends IService<FinancialSheet> {

    /**
     * 获取财务状况表分页
     *
     * @author pzh
     * @date  2024/10/18 15:53
     */
    Page<FinancialSheet> page(FinancialSheetPageParam financialSheetPageParam);

    /**
     * 添加财务状况表
     *
     * @author pzh
     * @date  2024/10/18 15:53
     */
    void add(FinancialSheetAddParam financialSheetAddParam);

    /**
     * 编辑财务状况表
     *
     * @author pzh
     * @date  2024/10/18 15:53
     */
    void edit(FinancialSheetEditParam financialSheetEditParam);

    /**
     * 删除财务状况表
     *
     * @author pzh
     * @date  2024/10/18 15:53
     */
    void delete(List<FinancialSheetIdParam> financialSheetIdParamList);

    /**
     * 获取财务状况表详情
     *
     * @author pzh
     * @date  2024/10/18 15:53
     */
    FinancialSheet detail(FinancialSheetIdParam financialSheetIdParam);

    /**
     * 获取财务状况表详情
     *
     * @author pzh
     * @date  2024/10/18 15:53
     **/
    FinancialSheet queryEntity(String id);
}
