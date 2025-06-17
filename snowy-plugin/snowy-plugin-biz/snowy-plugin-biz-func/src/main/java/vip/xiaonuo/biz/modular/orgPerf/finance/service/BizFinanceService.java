package vip.xiaonuo.biz.modular.orgPerf.finance.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.finance.entity.BizFinance;
import vip.xiaonuo.biz.modular.orgPerf.finance.param.*;
import vip.xiaonuo.common.pojo.ImportResult;

import java.util.List;

/**
 * 财务情况Service接口
 *
 * @author pzh
 * @date  2025/06/17 11:47
 **/
public interface BizFinanceService extends IService<BizFinance> {

    /**
     * 获取财务情况分页
     *
     * @author pzh
     * @date  2025/06/17 11:47
     */
    Page<BizFinance> page(BizFinancePageParam bizFinancePageParam);

    /**
     * 添加财务情况
     *
     * @author pzh
     * @date  2025/06/17 11:47
     */
    void add(BizFinanceAddParam bizFinanceAddParam);

    /**
     * 编辑财务情况
     *
     * @author pzh
     * @date  2025/06/17 11:47
     */
    void edit(BizFinanceEditParam bizFinanceEditParam);

    /**
     * 删除财务情况
     *
     * @author pzh
     * @date  2025/06/17 11:47
     */
    void delete(List<BizFinanceIdParam> bizFinanceIdParamList);

    /**
     * 获取财务情况详情
     *
     * @author pzh
     * @date  2025/06/17 11:47
     */
    BizFinance detail(BizFinanceIdParam bizFinanceIdParam);

    /**
     * 获取财务情况详情
     *
     * @author pzh
     * @date  2025/06/17 11:47
     **/
    BizFinance queryEntity(String id);

    /**
     * 导入财务情况
     *
     * @author pzh
     * @date  2025/06/17 11:47
     **/
    ImportResult doImport(ImportFinanceParam importParam);
}
