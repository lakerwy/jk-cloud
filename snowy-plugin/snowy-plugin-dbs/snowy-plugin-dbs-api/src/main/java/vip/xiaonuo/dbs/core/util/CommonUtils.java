package vip.xiaonuo.dbs.core.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.handler.RsHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.extra.spring.SpringUtil;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dbs.api.DbsApi;
import vip.xiaonuo.dbs.core.consts.DbsConstant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 通用公共方法工具累
 * @author mayike
 * @date  2023/06/30 16:43
 */
public class CommonUtils {

    /**
     * 表名
     * @param bizTableCommon 表名
     * 该方法适合 xb2023050001 其余规则慎用
     * @return
     */
    public static String getTableSn(BizTableCommon bizTableCommon,String type){
        String id = newHandleSqlIdExecution(bizTableCommon.getBizTable(),type);
        String idPre = "";
        String newId = "";
        if(StrUtil.isNotBlank(id)){
            //xb2023050001
            idPre = id.substring(0,8);
            //0001
            newId = String.format("%04d", Integer.parseInt(id.substring(8)) + 1);
        }else {
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyyMM");
            idPre = bizTableCommon.getSnPrefix() +format.format(date);
            newId = String.format("%04d", 1);
        }
        return idPre+newId;
    }

    public static String getTableSn(BizTableCommon bizTableCommon){
        return getTableSn(bizTableCommon,null);
    }

    private static String newHandleSqlIdExecution(String tableName,String type) {
        if(ObjectUtil.isNotEmpty(tableName)) {
            Connection conn;
            try {
                DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
                if(StrUtil.isNotBlank(type)){
                    dbsApi.changeDataSource(type);
                }
                conn = dbsApi.getCurrentDataSource().getConnection();
            } catch (SQLException e) {
                throw new CommonException(e.getMessage());
            }
            try {
                AtomicReference<String> result = new AtomicReference<>();
                conn.setAutoCommit(false);
                String finalSql= " SELECT sn FROM " + tableName + " WHERE DATE_FORMAT( CREATE_TIME, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) ORDER BY CREATE_TIME DESC LIMIT 1";
                //String finalSql = sql;
                if(ObjectUtil.isNotEmpty(finalSql)) {
                    try {
                        //sql = " SELECT id FROM '" + tableName + "'  WHERE DATE_FORMAT( create_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) ORDER BY id DESC LIMIT 1";
                        // SqlExecutor.execute(conn, sqlItem);
                        result.set(SqlExecutor.query(conn, finalSql, new RsHandler<String>() {
                            @Override
                            public String handle(ResultSet resultSet) throws SQLException {
                                //获取

                                while(resultSet.next()) {
                                    return resultSet.getString("sn");
                                }

                                return "";
                            }
                        }));

                    } catch (SQLException e) {
                        throw new CommonException(e.getMessage());
                    }
                }
                conn.commit();
                return result.get();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            } finally {
                DbUtil.close(conn);
            }
        }
        return "";
    }

    public final static String MAJOR_TYPE = "MAJOR_";

    public final static String MAJOR_TYPE_USER = "MAJOR_USER_";

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
