package vip.xiaonuo.biz.modular.procure.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vip.xiaonuo.biz.modular.crm.subcontract.param.SupplierPageParam;
import vip.xiaonuo.biz.modular.crm.subcontract.vo.SupplierVo;

import java.util.List;

/**
 * 项目成果Service接口
 *
 * @author pzh
 * @date  2025/01/22 15:52
 **/
public interface ProcureApiService {
    /**
     * 招采平台：获取供应商信息分页
     */
    Page<SupplierVo> supplierPage(SupplierPageParam supplierPageParam);
}
