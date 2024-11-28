package vip.xiaonuo.sys.modular.hr.reputation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.reputation.entity.Reputation;
import vip.xiaonuo.sys.modular.hr.reputation.param.ReputationAddParam;
import vip.xiaonuo.sys.modular.hr.reputation.param.ReputationEditParam;
import vip.xiaonuo.sys.modular.hr.reputation.param.ReputationIdParam;
import vip.xiaonuo.sys.modular.hr.reputation.param.ReputationPageParam;

import java.util.List;

/**
 * 信誉情况Service接口
 *
 * @author pzh
 * @date  2024/10/18 16:52
 **/
public interface ReputationService extends IService<Reputation> {

    /**
     * 获取信誉情况分页
     *
     * @author pzh
     * @date  2024/10/18 16:52
     */
    Page<Reputation> page(ReputationPageParam reputationPageParam);

    /**
     * 添加信誉情况
     *
     * @author pzh
     * @date  2024/10/18 16:52
     */
    void add(ReputationAddParam reputationAddParam);

    /**
     * 编辑信誉情况
     *
     * @author pzh
     * @date  2024/10/18 16:52
     */
    void edit(ReputationEditParam reputationEditParam);

    /**
     * 删除信誉情况
     *
     * @author pzh
     * @date  2024/10/18 16:52
     */
    void delete(List<ReputationIdParam> reputationIdParamList);

    /**
     * 获取信誉情况详情
     *
     * @author pzh
     * @date  2024/10/18 16:52
     */
    Reputation detail(ReputationIdParam reputationIdParam);

    /**
     * 获取信誉情况详情
     *
     * @author pzh
     * @date  2024/10/18 16:52
     **/
    Reputation queryEntity(String id);
}
