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
package vip.xiaonuo.biz.modular.crm.notes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.equipmenttrans.param.EquipmenttransInfoPageParam;
import vip.xiaonuo.biz.modular.crm.equipmenttrans.vo.EquipmenttransInfoVo;
import vip.xiaonuo.biz.modular.crm.notes.entity.Notes;
import vip.xiaonuo.biz.modular.crm.notes.param.NotesAddParam;
import vip.xiaonuo.biz.modular.crm.notes.param.NotesEditParam;
import vip.xiaonuo.biz.modular.crm.notes.param.NotesIdParam;
import vip.xiaonuo.biz.modular.crm.notes.param.NotesPageParam;
import vip.xiaonuo.biz.modular.crm.notes.vo.NotesInfoVo;
import vip.xiaonuo.biz.modular.crm.parameters.param.ParametersIdParam;
import vip.xiaonuo.biz.modular.crm.parameters.vo.ParametersInfoVo;

import java.util.List;

/**
 * 业务表-CRM-备忘录登记Service接口
 *
 * @author yong.ye
 * @date  2023/06/30 16:49
 **/
public interface NotesService extends IService<Notes> {

    /**
     * 获取业务表-CRM-备忘录登记分页
     *
     * @author yong.ye
     * @date  2023/06/30 16:49
     */
    Page<Notes> page(NotesPageParam notesPageParam);

    /**
     * 添加业务表-CRM-备忘录登记
     *
     * @author yong.ye
     * @date  2023/06/30 16:49
     */
    void add(NotesAddParam notesAddParam);

    /**
     * 编辑业务表-CRM-备忘录登记
     *
     * @author yong.ye
     * @date  2023/06/30 16:49
     */
    void edit(NotesEditParam notesEditParam);

    /**
     * 删除业务表-CRM-备忘录登记
     *
     * @author yong.ye
     * @date  2023/06/30 16:49
     */
    void delete(List<NotesIdParam> notesIdParamList);

    /**
     * 获取业务表-CRM-备忘录登记详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:49
     */
    Notes detail(NotesIdParam notesIdParam);

    /**
     * 获取业务表-CRM-备忘录登记详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:49
     **/
    Notes queryEntity(String id);

    /**
     * 获取业务表-CRM-备忘录登记分页(携带合同、伙伴信息)
     * @param notesPageParam
     * @return
     */
    IPage<NotesInfoVo> pageList(NotesPageParam notesPageParam);

    /**
     * 根据主键ID获取业务表-CRM-备忘录登记详情(携带合同、伙伴信息)
     * @param notesIdParam
     * @return
     */
    NotesInfoVo detailById(NotesIdParam notesIdParam);
}
