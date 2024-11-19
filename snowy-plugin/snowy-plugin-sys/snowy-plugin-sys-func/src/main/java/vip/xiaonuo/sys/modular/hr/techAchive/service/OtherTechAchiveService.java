package vip.xiaonuo.sys.modular.hr.techAchive.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.techAchive.entity.OtherTechAchive;
import vip.xiaonuo.sys.modular.hr.techAchive.param.OtherTechAchiveAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.OtherTechAchiveEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.OtherTechAchiveIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.OtherTechAchivePageParam;

import java.util.List;

/**
 * 其他（科技成果）Service接口
 *
 * @author pzh
 * @date  2024/10/18 09:10
 **/
public interface OtherTechAchiveService extends IService<OtherTechAchive> {

    /**
     * 获取其他（科技成果）分页
     *
     * @author pzh
     * @date  2024/10/18 09:10
     */
    Page<OtherTechAchive> page(OtherTechAchivePageParam otherTechAchivePageParam);

    /**
     * 添加其他（科技成果）
     *
     * @author pzh
     * @date  2024/10/18 09:10
     */
    void add(OtherTechAchiveAddParam otherTechAchiveAddParam);

    /**
     * 编辑其他（科技成果）
     *
     * @author pzh
     * @date  2024/10/18 09:10
     */
    void edit(OtherTechAchiveEditParam otherTechAchiveEditParam);

    /**
     * 删除其他（科技成果）
     *
     * @author pzh
     * @date  2024/10/18 09:10
     */
    void delete(List<OtherTechAchiveIdParam> otherTechAchiveIdParamList);

    /**
     * 获取其他（科技成果）详情
     *
     * @author pzh
     * @date  2024/10/18 09:10
     */
    OtherTechAchive detail(OtherTechAchiveIdParam otherTechAchiveIdParam);

    /**
     * 获取其他（科技成果）详情
     *
     * @author pzh
     * @date  2024/10/18 09:10
     **/
    OtherTechAchive queryEntity(String id);
}
