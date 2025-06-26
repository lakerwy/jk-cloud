package vip.xiaonuo.biz.modular.prj.fin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataPdt;
import vip.xiaonuo.biz.modular.prj.fin.vo.FinDataResult;

/**
 * 财务数据汇总台账 - 生产院Mapper接口
 *
 * @author yy
 * @date  2025/06/05 17:26
 **/
public interface FinDataPdtMapper extends BaseMapper<FinDataPdt> {

    Page<FinDataResult> getPageList(Page<FinDataResult> page, @Param(Constants.WRAPPER) Wrapper<Hetong> queryWrapper);

}
