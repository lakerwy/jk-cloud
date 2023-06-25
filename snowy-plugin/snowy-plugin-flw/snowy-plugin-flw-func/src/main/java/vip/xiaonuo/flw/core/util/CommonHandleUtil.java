package vip.xiaonuo.flw.core.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.handler.RsHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.extra.spring.SpringUtil;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dbs.api.DbsApi;
import vip.xiaonuo.dbs.core.consts.DbsConstant;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class CommonHandleUtil {

    /**
     * 执行查询sql
     *
     * @author xuyuxiang
     * @date 2022/6/16 10:24
     **/
    public static List<Map<String, String>> newHandleSqlExecution(String tableName) {
        //StringBuilder stringBuilder = new StringBuilder("select COLUMN_NAME,DATA_TYPE ,COLUMN_COMMENT from information_schema.COLUMNS  where ");
        final String sql = "select COLUMN_NAME,DATA_TYPE ,COLUMN_COMMENT from information_schema.COLUMNS  where  TABLE_SCHEMA = ? and table_name = ?";
        String dbName = "jk-cloud-biz";
            Connection conn;
            try {
                DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
                dbsApi.changeDataSource(DbsConstant.SLAVE_1_DATASOURCE_NAME);
                conn = dbsApi.getCurrentDataSource().getConnection();
                dbName = conn.getCatalog();
                String test = conn.getSchema();
            } catch (SQLException e) {
                throw new CommonException(e.getMessage());
            }
            try {
                AtomicReference<List<Map<String, String>>> result = new AtomicReference<>(new ArrayList<>());
                conn.setAutoCommit(false);
                String finalDbName = dbName;
                StrUtil.split(sql.trim(), ";").forEach(sqlItem -> {
                    if(ObjectUtil.isNotEmpty(sqlItem)) {
                        try {
                            // SqlExecutor.execute(conn, sqlItem);
                            result.set(SqlExecutor.query(conn, sql, new RsHandler<List<Map<String, String>>>() {
                                @Override
                                public List<Map<String, String>> handle(ResultSet resultSet) throws SQLException {
                                    List<Map<String, String>> list = new ArrayList<>();
                                    //获取
                                    //ResultSetMetaData meta = resultSet.getMetaData();
                                    while (resultSet.next()) {
                                        Map<String, String> map = new HashMap<>();
                                        String columnName = resultSet.getString("COLUMN_NAME");
                                        String columnComment = resultSet.getString("DATA_TYPE");
                                        map.put("COLUMN_NAME", columnName);
                                        map.put("DATA_TYPE", columnComment);
                                        list.add(map);
//                                         for (int i = 1; i <= meta.getColumnCount(); i++) {
//
//                                         }
                                    }
                                    return list;
                                }
                            }, finalDbName,tableName));

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
            }
        return new ArrayList<>();
    }


    public static String newHandleSqlIdExecution(String tableName) {
        if(ObjectUtil.isNotEmpty(tableName)) {
            Connection conn;
            try {
                DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
                dbsApi.changeDataSource(DbsConstant.SLAVE_1_DATASOURCE_NAME);
                conn = dbsApi.getCurrentDataSource().getConnection();
            } catch (SQLException e) {
                throw new CommonException(e.getMessage());
            }
            try {
                AtomicReference<String> result = new AtomicReference<>();
                conn.setAutoCommit(false);
                String finalSql= " SELECT sn FROM " + tableName + " WHERE DATE_FORMAT( create_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) ORDER BY id DESC LIMIT 1";
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

    /**
     * 执行SQL
     *
     **/
    public static void handleSqlExecution(String sql) {
        if(ObjectUtil.isNotEmpty(sql)) {
            Connection conn;
            DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
            try {
                dbsApi.changeDataSource(DbsConstant.SLAVE_1_DATASOURCE_NAME);
                conn = dbsApi.getCurrentDataSource().getConnection();
            } catch (SQLException e) {
                throw new CommonException(e.getMessage());
            }
            try {
                conn.setAutoCommit(false);
                StrUtil.split(sql.trim(), ";").forEach(sqlItem -> {
                    if(ObjectUtil.isNotEmpty(sqlItem)) {
                        try {
                            SqlExecutor.execute(conn, sqlItem);
                        } catch (SQLException e) {
                            dbsApi.changeDataSource("master");
                            throw new CommonException(e.getMessage());
                        }
                    }
                });
                conn.commit();
            } catch (SQLException sqlException) {
                dbsApi.changeDataSource("master");
                sqlException.printStackTrace();
            } finally {
                DbUtil.close(conn);
                dbsApi.changeDataSource("master");
            }
        }
    }
}
