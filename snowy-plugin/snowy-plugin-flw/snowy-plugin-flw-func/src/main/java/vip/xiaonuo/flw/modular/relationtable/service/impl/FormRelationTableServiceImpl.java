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
package vip.xiaonuo.flw.modular.relationtable.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.id.NanoId;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.handler.RsHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.api.DbsApi;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.core.constants.VariableConstants;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;
import vip.xiaonuo.flw.core.util.NodeRuntimeUtil;
import vip.xiaonuo.flw.modular.flwbiz.common.BizCommon;
import vip.xiaonuo.flw.modular.flwbiz.common.HandleExecutionUtil;
import vip.xiaonuo.flw.modular.model.entity.FlwModel;
import vip.xiaonuo.flw.modular.relationtable.entity.DbTable;
import vip.xiaonuo.flw.modular.relationtable.entity.Field;
import vip.xiaonuo.flw.modular.relationtable.entity.FormRelationTable;
import vip.xiaonuo.flw.modular.relationtable.mapper.FormRelationTableMapper;
import vip.xiaonuo.flw.modular.relationtable.param.FormRelationTableAddParam;
import vip.xiaonuo.flw.modular.relationtable.param.FormRelationTableEditParam;
import vip.xiaonuo.flw.modular.relationtable.param.FormRelationTableIdParam;
import vip.xiaonuo.flw.modular.relationtable.param.FormRelationTablePageParam;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 表单与表的关系Service接口实现类
 *
 * @author mayike
 * @date  2023/06/08 11:20
 **/
@Service
public class FormRelationTableServiceImpl extends ServiceImpl<FormRelationTableMapper, FormRelationTable> implements FormRelationTableService {

    private final String ID_PRE = "LC";

    @Override
    public Page<FormRelationTable> page(FormRelationTablePageParam formRelationTablePageParam) {
        QueryWrapper<FormRelationTable> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(formRelationTablePageParam.getSortField(), formRelationTablePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(formRelationTablePageParam.getSortOrder());
            queryWrapper.orderBy(true, formRelationTablePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(formRelationTablePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(FormRelationTable::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(FormRelationTableAddParam formRelationTableAddParam) {
        FormRelationTable formRelationTable = BeanUtil.toBean(formRelationTableAddParam, FormRelationTable.class);
        this.save(formRelationTable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FormRelationTableEditParam formRelationTableEditParam) {
        FormRelationTable formRelationTable = this.queryEntity(formRelationTableEditParam.getId());
        BeanUtil.copyProperties(formRelationTableEditParam, formRelationTable);
        this.updateById(formRelationTable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FormRelationTableIdParam> formRelationTableIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(formRelationTableIdParamList, FormRelationTableIdParam::getId));
    }

    @Override
    public FormRelationTable detail(FormRelationTableIdParam formRelationTableIdParam) {
        return this.queryEntity(formRelationTableIdParam.getId());
    }

    @Override
    public FormRelationTable queryEntity(String id) {
        FormRelationTable formRelationTable = this.getById(id);
        if(ObjectUtil.isEmpty(formRelationTable)) {
            throw new CommonException("表单与表的关系不存在，id值为：{}", id);
        }
        return formRelationTable;
    }


    @Override
    public void creatTable(String formName, List<JSONObject> formWidgets, String modelId) {
        /**
         * CREATE TABLE IF NOT EXISTS `${tableName}`
         *         (
         *             `id`       int(0)      NOT NULL AUTO_INCREMENT COMMENT '主键',
         *             `group_id` int(0)      NULL DEFAULT NULL COMMENT '组号',
         *             `username` varchar(20) NULL DEFAULT NULL COMMENT '用户名',
         *             `password` varchar(20) NULL DEFAULT NULL COMMENT '密码',
         *             PRIMARY KEY (`id`)
         *             ) ENGINE = InnoDB
         *             AUTO_INCREMENT = 9
         *             CHARACTER SET = utf8mb4 COMMENT ='用于测试的用户表';
         */

        DbTable dbTable = buildTable(formName, formWidgets);
        //主键
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sql.append(dbTable.getTableName())
                .append("(`" )
                .append(dbTable.getField().getName())
                .append("` ")
                .append(dbTable.getField().getTableFieldType())
                .append(" NOT NULL COMMENT '主键' ,  ")
                .append("`")
                .append("sn")
                .append("` ")
                .append("varchar(255)")
                .append(" COMMENT '")
                .append("sn值")
                .append("' ,");
        List<Field> fields = dbTable.getFields();
        for (Field field : fields) {
            sql.append("`")
                    .append(field.getName())
                    .append("` ")
                    .append(field.getTableFieldType())
                    .append(" COMMENT '")
                    .append(field.getLabel())
                    .append("' ,");
        }
        //共有字段
        sql.append("`")
                .append("deployId")
                .append("` ")
                .append("varchar(255)")
                .append(" COMMENT '")
                .append("流程发布ID")
                .append("' ,")

                .append("`")
                .append("summary")
                .append("` ")
                .append("varchar(255)")
                .append(" COMMENT '")
                .append("摘要详情")
                .append("' ,")

                .append("`")
                .append("create_time")
                .append("` ")
                .append("datetime")
                .append(" COMMENT '")
                .append("创建时间")
                .append("' ,")

                .append("`")
                .append("create_By")
                .append("` ")
                .append("varchar(255)")
                .append(" COMMENT '")
                .append("创建人")
                .append("' ,")

                .append("`")
                .append("status")
                .append("` ")
                .append("varchar(20)")
                .append(" COMMENT '")
                .append("流程状态")
                .append("' ,")

                .append("primary key (")
                .append(dbTable.getField().getName())
                .append("))");
        // return  sql.toString();
//        Map<String, String> params = new HashMap<>();
//        params.put("tableName", dbTable.getTableName());
//        params.put("sql", sql.toString());
//        params.put("remark", dbTable.getDescription());
//        tableMapper.createTable(params);
        //DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
        //dbsApi.changeDataSource(DbsConstant.SLAVE_1_DATASOURCE_NAME);
        NodeRuntimeUtil.handleSqlExecution(sql.toString());
//        DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
//        dbsApi.changeDataSource("master");
        FormRelationTable formRelationTable = new FormRelationTable();
        formRelationTable.setActModelId(modelId);
        formRelationTable.setTableName(dbTable.getTableName());
        save(formRelationTable);
    }

    @Override
    public void updateTable(String formName, List<JSONObject> formWidgets, String modelId) {
        FormRelationTable formRelationTable1 = new FormRelationTable();
        formRelationTable1.setActModelId(modelId);
        FormRelationTable formRelationTable = getOne(new QueryWrapper<>(formRelationTable1));
        if(null != formRelationTable){
            List<Map<String, String>> fields = getFieldsByTableName(formRelationTable.getTableName());
            List<String> collect = fields.stream().map(map -> map.get("COLUMN_NAME")).collect(Collectors.toList());
            List<Field> fields1 = buildFields(formWidgets);
            //需要新增的
            List<Field> collect1 = fields1.stream().filter(field -> !collect.contains(field.getName().trim())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(collect1)){
                updateColumn(formRelationTable.getTableName(),collect1);
            }
        }else {
            creatTable(formName,formWidgets,modelId);
        }
    }

    /**
     * @param remark   备注
     * @return
     */
    public  DbTable buildTable(String remark, List<JSONObject> formWidgets) {
        DbTable dbTable = new DbTable();
        //表名称自动生成
        String tableName = "Auto_"+ NanoId.randomNanoId(5) + "_table";
        dbTable.setTableName(tableName);
        dbTable.setDescription(remark);
        dbTable.setFields(buildFields(formWidgets));
        Field field = new Field();
        field.setPrimary(true);
        field.setName("id");
        field.setTableFieldType("varchar(30)");
        field.setLabel("主键id");
        dbTable.setField(field);
        return dbTable;
    }

    /**
     * 从form表单中构建表明
     *
     * @param
     * @return
     */
    public  List<Field> buildFields(List<JSONObject> formWidgets) {
        List<Field> fieldList = new ArrayList<>();
        //JSONObject ja = JSONObject.parseObject(jsonObject.getString(FIELDS_KEY), JSONObject.class);
        Field field = null;
        //List<JSONObject> jsonObjects = JSONArray.parseArray(formWidgets, JSONObject.class);

        for (JSONObject jsonObject : formWidgets) {
            field = new Field();
            String type = jsonObject.getString("type");
            field.setType(type);
            JSONObject fieldJson = jsonObject.getJSONObject("field");
            JSONObject options = fieldJson.getJSONObject("options");
            String name = options.getString("name");
            field.setName(name);
            String label = options.getString("label");
            field.setLabel(label);
            field.setTableFieldType(convertedType(field.getType()));
            fieldList.add(field);
            //String type = options.getString("type");
        }
//        JSONArray fields = JSON.parseArray(jsonObject.getString(FIELDS_KEY));
//        List<Field> fieldList = new ArrayList<>();
//        fields.stream().forEach(fd -> {
//            JSONObject jb = (JSONObject) fd;
//            Field field = new Field();
//            JSONObject config = jb.getJSONObject(CONFIG_KEY);
//            field.setLabel(config.getString(LABEL_KEY));
//            field.setType(config.getString(TYPE_KEY));
//            //String string = jb.getString(TYPE_KEY);
//            field.setName(config.getString(MODEL_KEY));
//            field.setTableFieldType(convertedType(jb.getString(TYPE_KEY)));
//            fieldList.add(field);
//        });
        return fieldList;
    }

    private static String convertedType(String formType) {
        String dbType;
        switch (formType) {
            case "time":
                dbType = "datetime";
                break;
            case "input":
                dbType = "varchar(255)";
                break;
            case "number":
                dbType = "bigint(20)";
                break;
            default:
                dbType = "varchar(255)";
                break;
        }
        return dbType;

    }

    @Override
    public Map<String,Object> saveData(Map<String, Object> map, String tableName, String summary, String deployId,String parentTablePrimaryId) {
        try {
            List<Map<String, String>> fieldsByTableName = getFieldsByTableName(tableName);

            StringBuilder sqlkey = new StringBuilder();
            StringBuilder sqlValue = new StringBuilder();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            Set<String> collect = new HashSet<>() ;
            if(StringUtils.isNotEmpty(summary)){
                String[] split = summary.split("\\|");
                collect = Arrays.stream(split).collect(Collectors.toSet());
            }
            StringBuilder summaryValue = new StringBuilder();
            for (Map<String, String> stringMap : fieldsByTableName) {
                //字段名称
                String fieldName = stringMap.get("COLUMN_NAME");
                if (fieldName.equalsIgnoreCase("id")) {
                    sqlkey.append("`id`").append(",");
                    sqlValue.append("'").append(parentTablePrimaryId).append("', ");
                }


                if (fieldName.equalsIgnoreCase("sn")) {
                    sqlkey.append("`sn`").append(",");
                    sqlValue.append("'").append(HandleExecutionUtil.getTableSn(tableName)).append("', ");
                }
                //字段类型 （暂时广泛的定义三个 int varchar datetime）
                String fieldType = stringMap.get("DATA_TYPE");
                Object o = map.get(fieldName);
                if (ObjectUtil.isNotEmpty(o)) {
                    sqlkey.append("`");
                    sqlkey.append(fieldName);
                    sqlkey.append("`");
                    sqlkey.append(",");
                    if (fieldType.equalsIgnoreCase("varchar")) {
                        sqlValue.append("'");
                        sqlValue.append(o);
                        sqlValue.append("'");
                    } else if (fieldType.equalsIgnoreCase("datetime")) {
                        Date date = null;
                        try {
                            date = ft.parse((String) o);
                        } catch (ParseException e) {
                            // throw new RuntimeException(e);
                        }
                        sqlValue.append(date);
                    } else if (fieldType.equalsIgnoreCase("bigint")) {
                        sqlValue.append(o);
                    } else {
                        sqlValue.append("'");
                        sqlValue.append(o);
                        sqlValue.append("'");
                    }
                    sqlValue.append(",");
                }

                if(collect.contains(fieldName)){
                    summaryValue.append("|").append(o).append("|");
                }
            }
            //公共字段deployId
            if(StringUtils.isNotEmpty(deployId)){
                sqlkey.append("`deployId` ,");
                sqlValue.append("'").append(deployId).append("',");
            }
            if(StringUtils.isNotEmpty(summaryValue.toString())){
                sqlkey.append("`summary` ,");
                sqlValue.append("'").append(summaryValue.substring(summaryValue.indexOf("|"), summaryValue.lastIndexOf("|"))).append("',");
            }

            sqlkey.append("`status` ,");
            sqlValue.append(" '0' ,");

            sqlkey.append("`create_time`");
            sqlValue.append("'").append(DateUtil.dateNew(new Date())).append("'");

            //动态拼接插入字段
            //String skey = sqlkey.substring(0, sqlkey.lastIndexOf(","));
            //动态拼接插入字段值
            // String sValue = sqlValue.substring(0, sqlValue.lastIndexOf(","));
            Map<String, Object> params = new HashMap<>();
            params.put(VariableConstants.BIZ_TABLE_VAR_TABLE_NAME, tableName);
            params.put("sqlKey", sqlkey.toString());
            params.put("sqlValue", sqlValue.toString());
            String sb = "insert into " +
                    tableName +
                    " (" +
                    sqlkey.toString() +
                    " ) " +
                    " values (" +
                    sqlValue +
                    ")";
            NodeRuntimeUtil.handleSqlExecution(sb);
            //tableMapper.commonInsert(params);
            params.put(VariableConstants.BIZ_TABLE_VAR_FIELDS,fieldsByTableName);
            return params;
        }catch (Exception e){
            log.error("执行保存{}表业务数据失败",e);
        }
        return null;
    }

    @Override
    public Map<String,Object> getTableName(FormRelationTablePageParam param) {
        FormRelationTable formRelationTable = new FormRelationTable();
        formRelationTable.setActModelId(param.getModelId());
        formRelationTable = getOne(new QueryWrapper<>(formRelationTable));
        //FormRelationTable formRelationTable = this.queryEntity(param.getModelId());
        if(null == formRelationTable){
            throw new CommonException("模型id为{}所对应的业务表不存在", param.getModelId() );
        }
        String tableName = formRelationTable.getTableName();
        List<Map<String, String>> fields = getFieldsByTableName(tableName);
        StringBuilder sb = new StringBuilder();
        StringBuilder toSql = new StringBuilder();
        sb.append("select * from ").append(tableName);
        toSql.append("select count(1) as total from ").append(tableName);
        //String sql = "select * from " + tableName + "where sn like '%" + param.getSearchKey() + "%' limit"+(param.getCurrent()-1)*param.getSize()+","+param.getSize() +"order by create_time";
        if(null!=param.getFields() && param.getFields().length > 0){
            StringBuilder where = new StringBuilder(" WHERE ");
            String[] list = param.getFields();
            for (String s : list) {
                where.append(s).append(" like '%").append(param.getSearchKey()).append("%' or");
            }
            sb.append(where.substring(0, where.lastIndexOf("or")));
            toSql.append(where.substring(0, where.lastIndexOf("or")));
        }
        sb.append(" order by ");
        if(StrUtil.isNotBlank(param.getSortField())){
            sb.append(param.getSortField()).append(" ");
            if(StrUtil.isNotBlank(param.getSortOrder())){
                sb.append(param.getSortOrder());
            }else {
                sb.append("desc");
            }
        }else {
            sb.append(" create_time desc");
        }
        sb.append(" limit ").append((param.getCurrent()-1)*param.getSize()).append(",").append(param.getSize());
        List<Map<String, Object>> maps = handPageExecution(sb.toString());
        List<Map<String, Object>> mapList = handPageExecution(toSql.toString());
        Map<String, Object> map = mapList.get(0);
        Map<String,Object> data = new HashMap<>();
        data.put("fields",fields);
        data.put("dataTable",maps);
        data.put("total",map.get("total"));
        return data;
    }

    private static List<Map<String, String>> getFieldsByTableName(String tableName) {
        final String sql = "select COLUMN_NAME,DATA_TYPE ,COLUMN_COMMENT from information_schema.COLUMNS  where table_name = ?";
        List<Map<String, String>> fieldsByTableName = CommonHandleUtil.newHandleSqlExecution(sql, tableName);
        return fieldsByTableName;
    }

//    public  String getTableId(String tableName){
//        String id = CommonHandleUtil.newHandleSqlIdExecution(tableName);
//        String idPre = "";
//        String newId = "";
//        if(StrUtil.isNotBlank(id)){
//            //xb2023050001
//            idPre = id.substring(0,8);
//            //0001
//            newId = String.format("%04d", Integer.parseInt(id.substring(8)) + 1);
//        }else {
//            Date date = new Date();
//            DateFormat format = new SimpleDateFormat("yyyyMM");
//            idPre = ID_PRE +format.format(date);
//            newId = String.format("%04d", 1);
//        }
//        return idPre+newId;
//    }

    public void  updateColumn(String tableName,List<Field> fields){
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            sb.append("ADD `").append(field.getName().trim()).append("` ").append(field.getTableFieldType()).append(" COMMENT '").append(field.getLabel()).append("' ,");
        }
        String substring = sb.substring(0, sb.lastIndexOf(","));
//        Map<String, String> params = new HashMap<>();
//        params.put("tableName", tableName);
//        params.put("columns", substring);
        String stringBuilder = "ALTER TABLE  " +
                tableName +" "+ substring;
        NodeRuntimeUtil.handleSqlExecution(stringBuilder);
        //tableMapper.updateColumn(params);
    }

    /**
     * 执行查询sql
     *
     * @author xuyuxiang
     * @date 2022/6/16 10:24
     **/
    public static List<Map<String, Object>> handPageExecution(String sql) {
        if(ObjectUtil.isNotEmpty(sql)) {
            Connection conn;
            try {
                DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
                dbsApi.changeDataSource(DbsConstant.SLAVE_1_DATASOURCE_NAME);
                conn = dbsApi.getCurrentDataSource().getConnection();
            } catch (SQLException e) {
                throw new CommonException(e.getMessage());
            }
            try {
                AtomicReference<List<Map<String, Object>>> result = new AtomicReference<>(new ArrayList<>());
                conn.setAutoCommit(false);
                StrUtil.split(sql.trim(), ";").forEach(sqlItem -> {
                    if(ObjectUtil.isNotEmpty(sqlItem)) {
                        try {
                            result.set(SqlExecutor.query(conn, sql, new RsHandler<List<Map<String, Object>>>() {
                                @Override
                                public List<Map<String, Object>> handle(ResultSet resultSet) throws SQLException {
                                    List<Map<String, Object>> list = new ArrayList<>();
                                    ResultSetMetaData metaData = resultSet.getMetaData();
                                    int columnCount = metaData.getColumnCount();
                                    while (resultSet.next()) {
                                        Map<String, Object> row = new HashMap<>();
                                        for (int i = 1; i <= columnCount; i++) {
                                            String columnName = metaData.getColumnName(i);
                                            Object value = resultSet.getObject(i);
                                            row.put(columnName, value);
                                        }
                                        list.add(row);
                                    }
                                    return list;
                                }
                            }));

                        } catch (SQLException e) {
                            throw new CommonException(e.getMessage());
                        }
                    }
                });
                conn.commit();
                return result.get();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            } finally {
                DbUtil.close(conn);
                DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
                dbsApi.changeDataSource("master");
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void saveByFlwModel(FlwModel flwModel) {
        if(ObjectUtil.isNotEmpty(flwModel)){
            FormRelationTable formRelationTable = new FormRelationTable();
            formRelationTable.setActModelId(flwModel.getId());
            formRelationTable.setDeploymentId(flwModel.getDeploymentId());
            formRelationTable = getOne(new QueryWrapper<>(formRelationTable));
            //更新
            String processJson = flwModel.getProcessJson();
            cn.hutool.json.JSONObject entries = JSONUtil.parseObj(processJson);
            cn.hutool.json.JSONObject properties = entries.getJSONObject("properties");
            if(null!=properties){
                cn.hutool.json.JSONObject configInfo = properties.getJSONObject("configInfo");
                if(null!=configInfo){
                    String str = configInfo.getStr("processStartTaskFormUrl");
                    formRelationTable.setFormUrl(str);
                }
            }
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                //更新
//                String processJson = flwModel.getProcessJson();
//                cn.hutool.json.JSONObject entries = JSONUtil.parseObj(processJson);
//                cn.hutool.json.JSONObject properties = entries.getJSONObject("properties");
//                if(null!=properties){
//                    cn.hutool.json.JSONObject configInfo = properties.getJSONObject("configInfo");
//                    if(null!=configInfo){
//                        String str = configInfo.getStr("processStartTaskFormUrl");
//                        formRelationTable.setFormUrl(str);
//                    }
//                }
            updateById(formRelationTable);
            }else {
                formRelationTable.setDefinitionId(flwModel.getDefinitionId());
                save(formRelationTable);
            }

        }
    }

    @Override
    public void updateTable(String modelId, String tableName){
        if(StrUtil.isNotBlank(modelId)){
            FormRelationTable formRelationTable = new FormRelationTable();
            formRelationTable.setActModelId(modelId);
            List<FormRelationTable> list = list(new QueryWrapper<>(formRelationTable));
            if(CollUtil.isNotEmpty(list)){
                formRelationTable = list.get(0);
            }
            formRelationTable.setTableName(BizCommon.PROJECT_REGISTER.getBizTable());
            updateById(formRelationTable);
        }
    }

}
