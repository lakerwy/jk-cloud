package vip.xiaonuo.biz.modular.prj.fin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataManage;
import vip.xiaonuo.biz.modular.prj.fin.param.*;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;

import java.util.List;

/**
 * 财务数据汇总台账 - 转入管理费Service接口
 *
 * @author yy
 * @date  2025/06/05 17:25
 **/
public interface FinDataManageService extends IService<FinDataManage> {

    /**
     * 获取财务数据汇总台账 - 转入管理费分页
     *
     * @author yy
     * @date  2025/06/05 17:25
     */
    Page<FinDataManage> page(FinDataManagePageParam finDataManagePageParam);

    /**
     * 添加财务数据汇总台账 - 转入管理费
     *
     * @author yy
     * @date  2025/06/05 17:25
     */
    void add(FinDataManageAddParam finDataManageAddParam);

    /**
     * 编辑财务数据汇总台账 - 转入管理费
     *
     * @author yy
     * @date  2025/06/05 17:25
     */
    void edit(FinDataManageEditParam finDataManageEditParam);

    /**
     * 删除财务数据汇总台账 - 转入管理费
     *
     * @author yy
     * @date  2025/06/05 17:25
     */
    void delete(List<FinDataManageIdParam> finDataManageIdParamList);

    /**
     * 获取财务数据汇总台账 - 转入管理费详情
     *
     * @author yy
     * @date  2025/06/05 17:25
     */
    FinDataManage detail(FinDataManageIdParam finDataManageIdParam);

    /**
     * 获取财务数据汇总台账 - 转入管理费详情
     *
     * @author yy
     * @date  2025/06/05 17:25
     **/
    FinDataManage queryEntity(String id);

    ImportResult doImport(ImportParam<ImportFinDataManage> importParam);
}
