package vip.xiaonuo.sys.modular.hr.honorAward.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.honorAward.entity.EnterpriseHonor;
import vip.xiaonuo.sys.modular.hr.honorAward.param.EnterpriseHonorAddParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.EnterpriseHonorEditParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.EnterpriseHonorIdParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.EnterpriseHonorPageParam;

import java.util.List;

/**
 * 企业荣誉Service接口
 *
 * @author pzh
 * @date  2024/10/16 11:17
 **/
public interface EnterpriseHonorService extends IService<EnterpriseHonor> {

    /**
     * 获取企业荣誉分页
     *
     * @author pzh
     * @date  2024/10/16 11:17
     */
    Page<EnterpriseHonor> page(EnterpriseHonorPageParam enterpriseHonorPageParam);

    /**
     * 添加企业荣誉
     *
     * @author pzh
     * @date  2024/10/16 11:17
     */
    void add(EnterpriseHonorAddParam enterpriseHonorAddParam);

    /**
     * 编辑企业荣誉
     *
     * @author pzh
     * @date  2024/10/16 11:17
     */
    void edit(EnterpriseHonorEditParam enterpriseHonorEditParam);

    /**
     * 删除企业荣誉
     *
     * @author pzh
     * @date  2024/10/16 11:17
     */
    void delete(List<EnterpriseHonorIdParam> enterpriseHonorIdParamList);

    /**
     * 获取企业荣誉详情
     *
     * @author pzh
     * @date  2024/10/16 11:17
     */
    EnterpriseHonor detail(EnterpriseHonorIdParam enterpriseHonorIdParam);

    /**
     * 获取企业荣誉详情
     *
     * @author pzh
     * @date  2024/10/16 11:17
     **/
    EnterpriseHonor queryEntity(String id);
}
