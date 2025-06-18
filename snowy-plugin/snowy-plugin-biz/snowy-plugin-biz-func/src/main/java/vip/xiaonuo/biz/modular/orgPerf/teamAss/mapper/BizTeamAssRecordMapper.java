package vip.xiaonuo.biz.modular.orgPerf.teamAss.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.entity.BizTeamAssRecord;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.vo.TeamAssRecordVo;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.vo.TeamAssScoreVo;
import vip.xiaonuo.biz.modular.task.vo.DeptAssRecordVo;
import vip.xiaonuo.biz.modular.task.vo.DeptAssScore;

/**
 * 班子考核记录Mapper接口
 *
 * @author pzh
 * @date  2025/06/17 17:08
 **/
public interface BizTeamAssRecordMapper extends BaseMapper<BizTeamAssRecord> {

    Page<TeamAssRecordVo> getPageList(Page<DeptAssRecordVo> page, @Param(Constants.WRAPPER) Wrapper<BizTeamAssRecord> queryWrapper);

    TeamAssScoreVo getTeamAssScore(String assId);
}
