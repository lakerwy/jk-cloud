/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.cad.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.cad.entity.CadProject;
import vip.xiaonuo.biz.modular.cad.param.*;
import vip.xiaonuo.biz.modular.cad.vo.CadFileTreeVo;
import vip.xiaonuo.biz.modular.cad.vo.CadProjectVo;
import vip.xiaonuo.biz.modular.cad.vo.StageSubItemVo;

import java.util.List;

/**
 * CAD-专业表Service接口
 *
 * @author yong.ye
 * @date  2023/11/29 10:36
 **/
public interface CadProjectService extends IService<CadProject> {

    /**
     * 获取CAD-专业表分页
     *
     * @author yong.ye
     * @date  2023/11/29 10:36
     */
    Page<CadProjectVo> page(CadProjectPageParam cadProjectPageParam);

    /**
     * 添加CAD-专业表
     *
     * @author yong.ye
     * @date  2023/11/29 10:36
     */
    void add(CadProjectAddParam cadProjectAddParam);

    /**
     * 编辑CAD-专业表
     *
     * @author yong.ye
     * @date  2023/11/29 10:36
     */
    void edit(CadProjectEditParam cadProjectEditParam);

    /**
     * 删除CAD-专业表
     *
     * @author yong.ye
     * @date  2023/11/29 10:36
     */
    void delete(List<CadProjectIdParam> cadProjectIdParamList);

    /**
     * 获取CAD-专业表详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:36
     */
    CadProjectVo detail(CadProjectIdParam cadProjectIdParam);

    /**
     * 获取CAD-专业表详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:36
     **/
    CadProject queryEntity(String id);

    /**
     *  根据项目Id,获取当前项目的阶段-子项两级树状结构
     * @param prjId
     * @return
     */
    List<StageSubItemVo> stageSubItem(String prjId);

    /**
     * 根据CAD用户Id,获取当前用户所参与的所有项目列表（包括项目管理者）
     * @param cadUserId
     * @return
     */
    List<CadProjectVo> projectsByUserId(String cadUserId);

    /**
     * 根据项目Id,当前用户Id,获取当前项目当前人树状结构
     * @param myFileTreeParam
     * @return
     */
    List<Tree<String>> myFileTree(MyFileTreeParam myFileTreeParam);

}
