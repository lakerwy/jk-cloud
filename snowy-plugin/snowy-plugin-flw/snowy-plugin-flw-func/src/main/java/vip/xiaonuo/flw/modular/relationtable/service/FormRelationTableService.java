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
package vip.xiaonuo.flw.modular.relationtable.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.flw.modular.relationtable.entity.FormRelationTable;
import vip.xiaonuo.flw.modular.relationtable.param.FormRelationTableAddParam;
import vip.xiaonuo.flw.modular.relationtable.param.FormRelationTableEditParam;
import vip.xiaonuo.flw.modular.relationtable.param.FormRelationTableIdParam;
import vip.xiaonuo.flw.modular.relationtable.param.FormRelationTablePageParam;

import java.util.List;
import java.util.Map;

/**
 * 表单与表的关系Service接口
 *
 * @author mayike
 * @date  2023/06/08 11:20
 **/
public interface FormRelationTableService extends IService<FormRelationTable> {

    /**
     * 获取表单与表的关系分页
     *
     * @author mayike
     * @date  2023/06/08 11:20
     */
    Page<FormRelationTable> page(FormRelationTablePageParam formRelationTablePageParam);

    /**
     * 添加表单与表的关系
     *
     * @author mayike
     * @date  2023/06/08 11:20
     */
    void add(FormRelationTableAddParam formRelationTableAddParam);

    /**
     * 编辑表单与表的关系
     *
     * @author mayike
     * @date  2023/06/08 11:20
     */
    void edit(FormRelationTableEditParam formRelationTableEditParam);

    /**
     * 删除表单与表的关系
     *
     * @author mayike
     * @date  2023/06/08 11:20
     */
    void delete(List<FormRelationTableIdParam> formRelationTableIdParamList);

    /**
     * 获取表单与表的关系详情
     *
     * @author mayike
     * @date  2023/06/08 11:20
     */
    FormRelationTable detail(FormRelationTableIdParam formRelationTableIdParam);

    /**
     * 获取表单与表的关系详情
     *
     * @author mayike
     * @date  2023/06/08 11:20
     **/
    FormRelationTable queryEntity(String id);

    void creatTable(String formName, List<JSONObject> formWidgets, String modelId);

    void updateTable(String formName, List<JSONObject> formWidgets,String modelId);

    Map<String,Object> saveData(Map<String, Object> map, String tableName, String summary, String deployId, String parentTablePrimaryId);

    /**
     * 获取表单与表的关系详情
     *
     * @author mayike
     * @date  2023/06/08 11:20
     */
    Map<String,Object> getTableName(FormRelationTablePageParam formRelationTablePageParam);
}
