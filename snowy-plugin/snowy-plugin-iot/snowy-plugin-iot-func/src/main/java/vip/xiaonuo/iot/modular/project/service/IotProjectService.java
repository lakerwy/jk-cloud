package vip.xiaonuo.iot.modular.project.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.iot.modular.project.entity.IotProject;
import vip.xiaonuo.iot.modular.project.param.*;
import vip.xiaonuo.iot.modular.project.vo.NodeVo;

import java.util.List;

/**
 * 物联网-项目Service接口
 *
 * @author mayike
 * @date  2024/08/08 14:03
 **/
public interface IotProjectService extends IService<IotProject> {

    /**
     * 获取物联网-项目分页
     *
     * @author mayike
     * @date  2024/08/08 14:03
     */
    Page<IotProject> page(IotProjectPageParam iotProjectPageParam);

    /**
     * 添加物联网-项目
     *
     * @author mayike
     * @date  2024/08/08 14:03
     */
    void add(IotProjectAddParam iotProjectAddParam);

    /**
     * 编辑物联网-项目
     *
     * @author mayike
     * @date  2024/08/08 14:03
     */
    void edit(IotProjectEditParam iotProjectEditParam);

    /**
     * 删除物联网-项目
     *
     * @author mayike
     * @date  2024/08/08 14:03
     */
    void delete(List<IotProjectIdParam> iotProjectIdParamList);

    /**
     * 获取物联网-项目详情
     *
     * @author mayike
     * @date  2024/08/08 14:03
     */
    IotProject detail(IotProjectIdParam iotProjectIdParam);

    /**
     * 获取物联网-项目详情
     *
     * @author mayike
     * @date  2024/08/08 14:03
     **/
    IotProject queryEntity(String id);

    NodeVo getIotTrees(IotProjectListParam iotProjectListParam);
}
