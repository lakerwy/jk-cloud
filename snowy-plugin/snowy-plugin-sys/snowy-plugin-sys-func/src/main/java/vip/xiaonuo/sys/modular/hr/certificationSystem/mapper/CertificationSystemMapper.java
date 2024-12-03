package vip.xiaonuo.sys.modular.hr.certificationSystem.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.sys.modular.hr.certificationSystem.entity.CertificationSystem;

/**
 * 认证体系Mapper接口
 *
 * @author pzh
 * @date  2024/10/18 10:01
 **/
public interface CertificationSystemMapper extends BaseMapper<CertificationSystem> {

    Page<CertificationSystem> getPageList(Page<CertificationSystem> page, @Param(Constants.WRAPPER) Wrapper<CertificationSystem> queryWrapper);

}
