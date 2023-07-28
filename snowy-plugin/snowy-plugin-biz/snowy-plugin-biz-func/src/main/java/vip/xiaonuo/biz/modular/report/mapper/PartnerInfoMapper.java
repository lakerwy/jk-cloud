package vip.xiaonuo.biz.modular.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.xiaonuo.biz.modular.report.result.PartnerVo;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/7/28 8:02
 */
public interface PartnerInfoMapper extends BaseMapper {
    List<PartnerVo> getAll();
}
