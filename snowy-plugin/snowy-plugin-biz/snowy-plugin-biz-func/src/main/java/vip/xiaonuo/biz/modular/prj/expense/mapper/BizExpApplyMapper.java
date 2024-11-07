package vip.xiaonuo.biz.modular.prj.expense.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpApply;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 申请流程Mapper接口
 *
 * @author yong.ye
 * @date  2024/07/30 15:26
 **/
public interface BizExpApplyMapper extends BaseMapper<BizExpApply> {
    Page<BizExpApply> getPageList(Page<BizExpApply> page, @Param(Constants.WRAPPER) Wrapper<BizExpApply> queryWrapper,
            @Param("abstractDesc") String abstractDesc);
}
