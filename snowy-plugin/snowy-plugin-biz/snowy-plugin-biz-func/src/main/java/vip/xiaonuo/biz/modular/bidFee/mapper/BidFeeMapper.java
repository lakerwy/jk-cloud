package vip.xiaonuo.biz.modular.bidFee.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.bidFee.entity.BidFee;
import vip.xiaonuo.biz.modular.bidFee.vo.BizProjectVo;

/**
 * 投标费用审批Mapper接口
 *
 * @author pzh
 * @date  2024/12/26 16:37
 **/
public interface BidFeeMapper extends BaseMapper<BidFee> {
    /**
     * 获取合同关联立项分页
     *
     * @return
     */
    Page<BizProjectVo> getProjectPage(Page<BizProjectVo> page, @Param(Constants.WRAPPER) Wrapper<BidFee> queryWrapper);
}
