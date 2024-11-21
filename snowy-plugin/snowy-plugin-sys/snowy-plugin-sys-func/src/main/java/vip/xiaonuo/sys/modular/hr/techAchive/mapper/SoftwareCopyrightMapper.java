package vip.xiaonuo.sys.modular.hr.techAchive.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.sys.modular.hr.techAchive.entity.SoftwareCopyright;

/**
 * 软著信息Mapper接口
 *
 * @author pzh
 * @date  2024/10/17 10:53
 **/
public interface SoftwareCopyrightMapper extends BaseMapper<SoftwareCopyright> {

    Page<SoftwareCopyright> getPageList(Page<SoftwareCopyright> page, @Param(Constants.WRAPPER) Wrapper<SoftwareCopyright> queryWrapper);

    SoftwareCopyright getDetailById(@Param("id") String id);
}
