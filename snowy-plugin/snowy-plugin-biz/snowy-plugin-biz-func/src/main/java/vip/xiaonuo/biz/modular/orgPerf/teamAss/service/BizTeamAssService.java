package vip.xiaonuo.biz.modular.orgPerf.teamAss.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.entity.BizTeamAss;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssAddParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssEditParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssIdParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssPageParam;

import java.util.List;

/**
 * 班子考核Service接口
 *
 * @author pzh
 * @date  2025/06/17 16:23
 **/
public interface BizTeamAssService extends IService<BizTeamAss> {

    /**
     * 获取班子考核分页
     *
     * @author pzh
     * @date  2025/06/17 16:23
     */
    Page<BizTeamAss> page(BizTeamAssPageParam bizTeamAssPageParam);

    /**
     * 添加班子考核
     *
     * @author pzh
     * @date  2025/06/17 16:23
     */
    void add(BizTeamAssAddParam bizTeamAssAddParam);

    /**
     * 编辑班子考核
     *
     * @author pzh
     * @date  2025/06/17 16:23
     */
    void edit(BizTeamAssEditParam bizTeamAssEditParam);

    /**
     * 删除班子考核
     *
     * @author pzh
     * @date  2025/06/17 16:23
     */
    void delete(List<BizTeamAssIdParam> bizTeamAssIdParamList);

    /**
     * 获取班子考核详情
     *
     * @author pzh
     * @date  2025/06/17 16:23
     */
    BizTeamAss detail(BizTeamAssIdParam bizTeamAssIdParam);

    /**
     * 获取班子考核详情
     *
     * @author pzh
     * @date  2025/06/17 16:23
     **/
    BizTeamAss queryEntity(String id);
}
