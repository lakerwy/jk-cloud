package vip.xiaonuo.sys.modular.hr.reg.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.sys.modular.hr.reg.entity.RegSubsidyResult;
import vip.xiaonuo.sys.modular.hr.reg.vo.RegResultVo;

/**
 * 注册补贴（新版）- 结果反馈Mapper接口
 *
 * @author yong.ye
 * @date  2025/01/15 09:08
 **/
public interface RegSubsidyResultMapper extends BaseMapper<RegSubsidyResult> {

    /**
     * 分页查询
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<RegResultVo> getPageList(Page<RegResultVo> page, @Param(Constants.WRAPPER) Wrapper<RegSubsidyResult> queryWrapper);
}
