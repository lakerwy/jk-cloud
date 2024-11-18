package vip.xiaonuo.sys.modular.hr.honorAward.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.sys.modular.hr.honorAward.entity.EnterpriseHonor;

/**
 * 企业荣誉Mapper接口
 *
 * @author pzh
 * @date  2024/10/16 11:17
 **/
public interface EnterpriseHonorMapper extends BaseMapper<EnterpriseHonor> {
    Page<EnterpriseHonor> getPageList(Page<EnterpriseHonor> page, @Param(Constants.WRAPPER) Wrapper<EnterpriseHonor> queryWrapper);

    EnterpriseHonor getDetailById(@Param("id") String id);
}
