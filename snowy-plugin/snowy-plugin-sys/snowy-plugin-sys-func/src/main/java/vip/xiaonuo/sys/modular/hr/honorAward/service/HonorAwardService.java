package vip.xiaonuo.sys.modular.hr.honorAward.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.honorAward.entity.HonorAward;
import vip.xiaonuo.sys.modular.hr.honorAward.param.HonorAwardAddParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.HonorAwardEditParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.HonorAwardIdParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.HonorAwardPageParam;

import java.util.List;

/**
 * 项目成果Service接口
 *
 * @author pzh
 * @date  2024/10/16 15:18
 **/
public interface HonorAwardService extends IService<HonorAward> {

    /**
     * 获取项目成果分页
     *
     * @author pzh
     * @date  2024/10/16 15:18
     */
    Page<HonorAward> page(HonorAwardPageParam honorAwardPageParam);

    /**
     * 添加项目成果
     *
     * @author pzh
     * @date  2024/10/16 15:18
     */
    void add(HonorAwardAddParam honorAwardAddParam);

    /**
     * 编辑项目成果
     *
     * @author pzh
     * @date  2024/10/16 15:18
     */
    void edit(HonorAwardEditParam honorAwardEditParam);

    /**
     * 删除项目成果
     *
     * @author pzh
     * @date  2024/10/16 15:18
     */
    void delete(List<HonorAwardIdParam> honorAwardIdParamList);

    /**
     * 获取项目成果详情
     *
     * @author pzh
     * @date  2024/10/16 15:18
     */
    HonorAward detail(HonorAwardIdParam honorAwardIdParam);

    /**
     * 获取项目成果详情
     *
     * @author pzh
     * @date  2024/10/16 15:18
     **/
    HonorAward queryEntity(String id);
}
