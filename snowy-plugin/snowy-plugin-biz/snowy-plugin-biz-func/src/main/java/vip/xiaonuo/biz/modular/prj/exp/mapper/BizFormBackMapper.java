package vip.xiaonuo.biz.modular.prj.exp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormBack;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormCash;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormBackVo;

import java.util.List;

/**
 * 费用报销（二期）- 归还登记Mapper接口
 *
 * @author yong.ye
 * @date  2025/02/25 09:15
 **/
public interface BizFormBackMapper extends BaseMapper<BizFormBack> {

    /**
     * 获取分页
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<BizFormBackVo> getPageList(Page<BizFormBackVo> page, @Param(Constants.WRAPPER) Wrapper<BizFormBack> queryWrapper);

    /**
     * 连表查询 - 获取所有满足条件的归还登记记录
     * @param queryWrapper
     * @return
     */
    List<BizFormBackVo> getBackList(@Param(Constants.WRAPPER) Wrapper<BizFormBack> queryWrapper);

    /**
     * 连表查询 - 获取所有满足条件的归还登记记录 涉及到的借支单信息 （一个借支单，可能有多条归还记录,去重）
     * @param queryWrapper
     * @return
     */
    List<BizFormCash> getBackCashList(@Param(Constants.WRAPPER) Wrapper<BizFormBack> queryWrapper);

}
