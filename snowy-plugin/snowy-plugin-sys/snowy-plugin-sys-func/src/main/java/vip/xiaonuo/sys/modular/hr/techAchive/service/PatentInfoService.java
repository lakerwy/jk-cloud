package vip.xiaonuo.sys.modular.hr.techAchive.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.techAchive.entity.PatentInfo;
import vip.xiaonuo.sys.modular.hr.techAchive.param.PatentInfoAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.PatentInfoEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.PatentInfoIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.PatentInfoPageParam;

import java.util.List;

/**
 * 专利信息Service接口
 *
 * @author pzh
 * @date  2024/10/17 10:22
 **/
public interface PatentInfoService extends IService<PatentInfo> {

    /**
     * 获取专利信息分页
     *
     * @author pzh
     * @date  2024/10/17 10:22
     */
    Page<PatentInfo> page(PatentInfoPageParam patentInfoPageParam);

    /**
     * 添加专利信息
     *
     * @author pzh
     * @date  2024/10/17 10:22
     */
    void add(PatentInfoAddParam patentInfoAddParam);

    /**
     * 编辑专利信息
     *
     * @author pzh
     * @date  2024/10/17 10:22
     */
    void edit(PatentInfoEditParam patentInfoEditParam);

    /**
     * 删除专利信息
     *
     * @author pzh
     * @date  2024/10/17 10:22
     */
    void delete(List<PatentInfoIdParam> patentInfoIdParamList);

    /**
     * 获取专利信息详情
     *
     * @author pzh
     * @date  2024/10/17 10:22
     */
    PatentInfo detail(PatentInfoIdParam patentInfoIdParam);

    /**
     * 获取专利信息详情
     *
     * @author pzh
     * @date  2024/10/17 10:22
     **/
    PatentInfo queryEntity(String id);
}
