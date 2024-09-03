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
package vip.xiaonuo.util.modular.log.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.handler.RsHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.Master;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.api.DbsApi;
import vip.xiaonuo.util.modular.log.entity.DevLog;
import vip.xiaonuo.util.modular.log.enums.DevLogCategoryEnum;
import vip.xiaonuo.util.modular.log.mapper.DevLogMapper;
import vip.xiaonuo.util.modular.log.param.DevLogDeleteParam;
import vip.xiaonuo.util.modular.log.param.DevLogPageParam;
import vip.xiaonuo.util.modular.log.param.HistoryDevLogSearchParam;
import vip.xiaonuo.util.modular.log.result.DevLogOpBarChartDataResult;
import vip.xiaonuo.util.modular.log.result.DevLogOpPieChartDataResult;
import vip.xiaonuo.util.modular.log.result.DevLogVisLineChartDataResult;
import vip.xiaonuo.util.modular.log.result.DevLogVisPieChartDataResult;
import vip.xiaonuo.util.modular.log.service.DevLogService;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 日志Service接口实现类
 *
 * @author xuyuxiang
 * @date 2022/9/2 15:05
 */
@Master
@Service
public class DevLogServiceImpl extends ServiceImpl<DevLogMapper, DevLog> implements DevLogService{

    //过滤的日志的黑名单
    private String[] blackList = new String[]{"/dev/file/download"};

    @Override
    public Page<DevLog> page(DevLogPageParam devLogPageParam) {
        QueryWrapper<DevLog> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(devLogPageParam.getCategory())) {
            queryWrapper.lambda().eq(DevLog::getCategory, devLogPageParam.getCategory());
        }
        if(ObjectUtil.isNotEmpty(devLogPageParam.getModelTag())){
            queryWrapper.lambda().like(DevLog::getModelTag,devLogPageParam.getModelTag());
        }
        if(ObjectUtil.isNotEmpty(devLogPageParam.getName())){
            queryWrapper.lambda().like(DevLog::getName,devLogPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(devLogPageParam.getStartTime()) && ObjectUtil.isNotEmpty(devLogPageParam.getEndTime())) {
            queryWrapper.lambda().between(DevLog::getCreateTime,devLogPageParam.getStartTime(), devLogPageParam.getEndTime());
        }
        if(ObjectUtil.isNotEmpty(devLogPageParam.getSearchKey())) {
            queryWrapper.lambda().and(q->q.like(DevLog::getName, devLogPageParam.getSearchKey())
                    .or().like(DevLog::getModelTag,devLogPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(devLogPageParam.getSortField(), devLogPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(devLogPageParam.getSortOrder());
            queryWrapper.orderBy(true, devLogPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(devLogPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(DevLog::getCreateTime);
        }
        queryWrapper.lambda().notIn(DevLog::getReqUrl,blackList);
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public void delete(DevLogDeleteParam devLogDeleteParam) {
        this.remove(new LambdaQueryWrapper<DevLog>().eq(DevLog::getCategory, devLogDeleteParam.getCategory()));
    }

    @Override
    public List<DevLogVisLineChartDataResult> visLogLineChartData() {
        DateTime lastWeek = DateUtil.lastWeek();
        DateTime now = DateTime.now();
        Map<String, List<JSONObject>> listMap = this.list(new LambdaQueryWrapper<DevLog>().in(DevLog::getCategory, DevLogCategoryEnum.LOGIN.getValue(),
                DevLogCategoryEnum.LOGOUT.getValue()).between(DevLog::getOpTime, lastWeek, now).orderByAsc(DevLog::getOpTime))
                .stream().map(devLog -> JSONUtil.parseObj(devLog).set("date", DateUtil.formatDate(devLog.getOpTime())))
                .collect(Collectors.groupingBy(jsonObject -> jsonObject.getStr("date")));
        long between = DateUtil.between(lastWeek, now, DateUnit.DAY);
        List<DevLogVisLineChartDataResult> resultList = CollectionUtil.newArrayList();
        for(int i = 1; i<= between; i++) {
            DevLogVisLineChartDataResult devLogVisLineChartDataResult = new DevLogVisLineChartDataResult();
            String date = DateUtil.formatDate(DateUtil.offsetDay(lastWeek, i));
            devLogVisLineChartDataResult.setDate(date);
            List<JSONObject> jsonObjectList = listMap.get(date);
            if(ObjectUtil.isNotEmpty(jsonObjectList)) {
                devLogVisLineChartDataResult.setLoginCount(jsonObjectList.stream().filter(jsonObject -> jsonObject.getStr("category")
                        .equals(DevLogCategoryEnum.LOGIN.getValue())).count());
                devLogVisLineChartDataResult.setLogoutCount(jsonObjectList.stream().filter(jsonObject -> jsonObject.getStr("category")
                        .equals(DevLogCategoryEnum.LOGOUT.getValue())).count());
            } else {
                devLogVisLineChartDataResult.setLoginCount(0L);
                devLogVisLineChartDataResult.setLogoutCount(0L);
            }
            resultList.add(devLogVisLineChartDataResult);
        }
        return resultList;
    }

    @Override
    public List<DevLogVisPieChartDataResult> visLogPieChartData() {
        List<DevLogVisPieChartDataResult> resultList = CollectionUtil.newArrayList();
        DevLogVisPieChartDataResult devLogLoginPieChartDataResult = new DevLogVisPieChartDataResult();
        devLogLoginPieChartDataResult.setType("登录");
        devLogLoginPieChartDataResult.setValue(Convert.toLong(this.count(new LambdaQueryWrapper<DevLog>()
                .eq(DevLog::getCategory, DevLogCategoryEnum.LOGIN.getValue()))));
        resultList.add(devLogLoginPieChartDataResult);

        DevLogVisPieChartDataResult devLogLogoutPieChartDataResult = new DevLogVisPieChartDataResult();
        devLogLogoutPieChartDataResult.setType("登出");
        devLogLogoutPieChartDataResult.setValue(Convert.toLong(this.count(new LambdaQueryWrapper<DevLog>()
                .eq(DevLog::getCategory, DevLogCategoryEnum.LOGOUT.getValue()))));
        resultList.add(devLogLogoutPieChartDataResult);
        return resultList;
    }

    @Override
    public List<DevLogOpBarChartDataResult> opLogBarChartData() {
        DateTime lastWeek = DateUtil.lastWeek();
        DateTime now = DateTime.now();
        Map<String, List<JSONObject>> listMap = this.list(new LambdaQueryWrapper<DevLog>().in(DevLog::getCategory, DevLogCategoryEnum.OPERATE.getValue(),
                DevLogCategoryEnum.EXCEPTION.getValue()).between(DevLog::getOpTime, lastWeek, now).orderByAsc(DevLog::getOpTime))
                .stream().map(devLog -> JSONUtil.parseObj(devLog).set("date", DateUtil.formatDate(devLog.getOpTime())))
                .collect(Collectors.groupingBy(jsonObject -> jsonObject.getStr("date")));
        long between = DateUtil.between(lastWeek, now, DateUnit.DAY);
        List<DevLogOpBarChartDataResult> resultList = CollectionUtil.newArrayList();
        for(int i = 1; i<= between; i++) {
            String date = DateUtil.formatDate(DateUtil.offsetDay(lastWeek, i));
            DevLogOpBarChartDataResult devLogOperateBarChartDataResult = new DevLogOpBarChartDataResult();
            devLogOperateBarChartDataResult.setDate(date);
            devLogOperateBarChartDataResult.setName("操作日志");
            DevLogOpBarChartDataResult devLogExceptionBarChartDataResult = new DevLogOpBarChartDataResult();
            devLogExceptionBarChartDataResult.setDate(date);
            devLogExceptionBarChartDataResult.setName("异常日志");
            List<JSONObject> jsonObjectList = listMap.get(date);
            if(ObjectUtil.isNotEmpty(jsonObjectList)) {
                devLogOperateBarChartDataResult.setCount(jsonObjectList.stream().filter(jsonObject -> jsonObject.getStr("category")
                        .equals(DevLogCategoryEnum.OPERATE.getValue())).count());
                devLogExceptionBarChartDataResult.setCount(jsonObjectList.stream().filter(jsonObject -> jsonObject.getStr("category")
                        .equals(DevLogCategoryEnum.EXCEPTION.getValue())).count());
            } else {
                devLogOperateBarChartDataResult.setCount(0L);
                devLogExceptionBarChartDataResult.setCount(0L);
            }
            resultList.add(devLogOperateBarChartDataResult);
            resultList.add(devLogExceptionBarChartDataResult);
        }
        return resultList;
    }

    @Override
    public List<DevLogOpPieChartDataResult> opLogPieChartData() {
        List<DevLogOpPieChartDataResult> resultList = CollectionUtil.newArrayList();
        DevLogOpPieChartDataResult devLogOperatePieChartDataResult = new DevLogOpPieChartDataResult();
        devLogOperatePieChartDataResult.setType("操作日志");
        devLogOperatePieChartDataResult.setValue(Convert.toLong(this.count(new LambdaQueryWrapper<DevLog>()
                .eq(DevLog::getCategory, DevLogCategoryEnum.OPERATE.getValue()))));
        resultList.add(devLogOperatePieChartDataResult);

        DevLogOpPieChartDataResult devLogExceptionPieChartDataResult = new DevLogOpPieChartDataResult();
        devLogExceptionPieChartDataResult.setType("异常日志");
        devLogExceptionPieChartDataResult.setValue(Convert.toLong(this.count(new LambdaQueryWrapper<DevLog>()
                .eq(DevLog::getCategory, DevLogCategoryEnum.EXCEPTION.getValue()))));
        resultList.add(devLogExceptionPieChartDataResult);
        return resultList;
    }

    @Override
    public Page<DevLog> fileActionLogPage(DevLogPageParam devLogPageParam) {
        QueryWrapper<DevLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DevLog::getCategory, DevLogCategoryEnum.FILE_ACTION.getValue());
        if(ObjectUtil.isNotEmpty(devLogPageParam.getFileId())){
            queryWrapper.lambda().eq(DevLog::getParamJson,devLogPageParam.getFileId());
        }
        if(ObjectUtil.isNotEmpty(devLogPageParam.getSearchKey())) {
            queryWrapper.lambda().like(DevLog::getName, devLogPageParam.getSearchKey());
        }
        if(ObjectUtil.isAllNotEmpty(devLogPageParam.getSortField(), devLogPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(devLogPageParam.getSortOrder());
            queryWrapper.orderBy(true, devLogPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(devLogPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(DevLog::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public List<String> getDevLogTables() {
        List<String> logTableNames = new ArrayList<>();
        Connection conn = null;
        try {
            DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
            conn = dbsApi.getCurrentDataSource().getConnection();
           // conn.setAutoCommit(false);
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "DEV_LOG_%", new String[]{"TABLE"});
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                logTableNames.add(tableName);
            }
        }catch (Exception e) {
            if(conn != null) {
                throw new CommonException("get Log tables error", e.getMessage());
            }
        }finally {
            DbUtil.close(conn);
        }

        return logTableNames;
    }

    @Override
    public Page<DevLog> historyDevLog(HistoryDevLogSearchParam historyDevLogSearchParam) {
        List<String> devLogTables = getDevLogTables();
        if(CollectionUtil.isNotEmpty(devLogTables)){
            boolean contains = devLogTables.contains(historyDevLogSearchParam.getTableName());
            if(!contains){
                throw new CommonException("不存在该历史表");
            }
        }
        return getDevLogPage(historyDevLogSearchParam);
    }

    public Page<DevLog>  getDevLogPage(HistoryDevLogSearchParam historyDevLogSearchParam) {

        String sql = "SELECT * FROM `" + historyDevLogSearchParam.getTableName() + "` ";
        String searchSql = "";
        if (StrUtil.isNotEmpty(historyDevLogSearchParam.getSearchKey())){
            String searchKey = "%"+historyDevLogSearchParam.getSearchKey()+"%";
            searchSql += " where  'CATEGORY' like " + searchKey + " `NAME` like " + searchKey + " 'METHOD_NAME' like " + searchKey + " 'PARAM_JSON' like " + searchKey;
        }
        sql+=searchSql;
        if (ObjectUtil.isAllNotEmpty(historyDevLogSearchParam.getSize(),historyDevLogSearchParam.getCurrent())) {
            sql += " limit " + historyDevLogSearchParam.getSize() + " OFFSET " + (historyDevLogSearchParam.getCurrent()-1)*historyDevLogSearchParam.getSize();
        }

        Connection conn = null;
        try {
            DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
            conn = dbsApi.getCurrentDataSource().getConnection();
            conn.setAutoCommit(false);
            List<DevLog> devLogs = SqlExecutor.query(conn, sql, new RsHandler<List<DevLog>>() {
                @Override
                public List<DevLog> handle(ResultSet rs) throws SQLException {
                    List<DevLog> list = new ArrayList<>();
                    while (rs.next()) {
                        DevLog devLog = new DevLog();
                        devLog.setId(rs.getString("id"));
                        devLog.setTenantId(rs.getString("TENANT_ID"));
                        devLog.setCategory(rs.getString("CATEGORY"));
                        devLog.setName(rs.getString("NAME"));
                        devLog.setExeStatus(rs.getString("EXE_STATUS"));
                        devLog.setExeMessage(rs.getString("EXE_MESSAGE"));
                        devLog.setOpIp(rs.getString("OP_IP"));
                        devLog.setOpAddress(rs.getString("OP_ADDRESS"));
                        devLog.setOpBrowser(rs.getString("OP_BROWSER"));
                        devLog.setOpOs(rs.getString("OP_OS"));
                        devLog.setClassName(rs.getString("CLASS_NAME"));
                        devLog.setMethodName(rs.getString("METHOD_NAME"));
                        devLog.setReqMethod(rs.getString("REQ_METHOD"));
                        devLog.setReqUrl(rs.getString("REQ_URL"));
                        devLog.setParamJson(rs.getString("PARAM_JSON"));
                        devLog.setResultJson(rs.getString("RESULT_JSON"));
                        devLog.setOpTime(rs.getTimestamp("OP_TIME"));
                        devLog.setOpUser(rs.getString("OP_USER"));
                        devLog.setSignData(rs.getString("SIGN_DATA"));
                        devLog.setCreateTime(rs.getTimestamp("CREATE_TIME"));
                        devLog.setCreateUser(rs.getString("CREATE_USER"));
                        devLog.setUpdateTime(rs.getTimestamp("UPDATE_TIME"));
                        devLog.setUpdateUser(rs.getString("UPDATE_USER"));
                        devLog.setModelTag(rs.getString("MODEL_TAG"));
                        list.add(devLog);
                    }
                    return list;
                }
            });

            // 查询总记录数
            String countSql = "SELECT COUNT(`ID`) as total FROM `" + historyDevLogSearchParam.getTableName() + "` " + searchSql;
            int totalRecords = SqlExecutor.query(conn, countSql, new RsHandler<Integer>() {

                @Override
                public Integer handle(ResultSet rs) throws SQLException {
                    if (rs.next()) {
                        return rs.getInt("total");
                    }
                    return 0;
                }
            });
            conn.commit();
            // 构建 Page 对象返回结果
            Page<DevLog> page = new Page<>(historyDevLogSearchParam.getCurrent(), historyDevLogSearchParam.getSize());
            page.setRecords(devLogs);
            page.setTotal(totalRecords);
            return page;
        } catch (SQLException e) {
            throw new CommonException(e.getMessage());
        }finally {
            if(conn != null){
                DbUtil.close(conn);
            }
        }
    }
}
