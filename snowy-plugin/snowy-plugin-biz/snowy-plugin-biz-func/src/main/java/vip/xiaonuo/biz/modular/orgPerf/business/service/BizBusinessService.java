package vip.xiaonuo.biz.modular.orgPerf.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.business.entity.BizBusiness;
import vip.xiaonuo.biz.modular.orgPerf.business.param.*;
import vip.xiaonuo.common.pojo.ImportResult;

import java.util.List;

/**
 * 业务情况Service接口
 *
 * @author pzh
 * @date  2025/06/17 14:48
 **/
public interface BizBusinessService extends IService<BizBusiness> {

    /**
     * 获取业务情况分页
     *
     * @author pzh
     * @date  2025/06/17 14:48
     */
    Page<BizBusiness> page(BizBusinessPageParam bizBusinessPageParam);

    /**
     * 添加业务情况
     *
     * @author pzh
     * @date  2025/06/17 14:48
     */
    void add(BizBusinessAddParam bizBusinessAddParam);

    /**
     * 编辑业务情况
     *
     * @author pzh
     * @date  2025/06/17 14:48
     */
    void edit(BizBusinessEditParam bizBusinessEditParam);

    /**
     * 删除业务情况
     *
     * @author pzh
     * @date  2025/06/17 14:48
     */
    void delete(List<BizBusinessIdParam> bizBusinessIdParamList);

    /**
     * 获取业务情况详情
     *
     * @author pzh
     * @date  2025/06/17 14:48
     */
    BizBusiness detail(BizBusinessIdParam bizBusinessIdParam);

    /**
     * 获取业务情况详情
     *
     * @author pzh
     * @date  2025/06/17 14:48
     **/
    BizBusiness queryEntity(String id);

    /**
     * 导入业务情况
     *
     * @author pzh
     * @date  2025/06/17 14:48
     */
    ImportResult doImport(ImportBusinessParam importParam);
}
