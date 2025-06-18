package vip.xiaonuo.biz.modular.orgPerf.teamAss.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.entity.BizTeamAssRecord;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssRecordAddParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssRecordEditParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssRecordIdParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssRecordPageParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.vo.TeamAssRecordVo;

import java.util.List;

/**
 * 班子考核记录Service接口
 *
 * @author pzh
 * @date  2025/06/17 17:08
 **/
public interface BizTeamAssRecordService extends IService<BizTeamAssRecord> {

    /**
     * 获取班子考核记录分页
     *
     * @author pzh
     * @date 2025/06/17 17:08
     */
    Page<TeamAssRecordVo> page(BizTeamAssRecordPageParam bizTeamAssRecordPageParam);

    /**
     * 添加班子考核记录
     *
     * @author pzh
     * @date  2025/06/17 17:08
     */
    void add(BizTeamAssRecordAddParam bizTeamAssRecordAddParam);

    /**
     * 编辑班子考核记录
     *
     * @author pzh
     * @date  2025/06/17 17:08
     */
    void edit(BizTeamAssRecordEditParam bizTeamAssRecordEditParam);

    /**
     * 删除班子考核记录
     *
     * @author pzh
     * @date  2025/06/17 17:08
     */
    void delete(List<BizTeamAssRecordIdParam> bizTeamAssRecordIdParamList);

    /**
     * 获取班子考核记录详情
     *
     * @author pzh
     * @date  2025/06/17 17:08
     */
    BizTeamAssRecord detail(BizTeamAssRecordIdParam bizTeamAssRecordIdParam);

    /**
     * 获取班子考核记录详情
     *
     * @author pzh
     * @date  2025/06/17 17:08
     **/
    BizTeamAssRecord queryEntity(String id);
}
