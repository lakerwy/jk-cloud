package vip.xiaonuo.biz.modular.prj.exp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormCash;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormCashVo;

/**
 * 费用报销（二期）- 借支单Mapper接口
 *
 * @author yong.ye
 * @date  2025/02/24 15:12
 **/
public interface BizFormCashMapper extends BaseMapper<BizFormCash> {

    /**
     * 获取分页
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<BizFormCashVo> getPageList(Page<BizFormCashVo> page, @Param(Constants.WRAPPER) Wrapper<BizFormCash> queryWrapper);
}
