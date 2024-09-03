package vip.xiaonuo.dev.modular.log.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dbs.api.DbsApi;
import vip.xiaonuo.util.modular.initData.entity.InitData;
import vip.xiaonuo.util.modular.initData.service.InitDataService;
import vip.xiaonuo.util.modular.log.service.DevLogService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/9/2 11:09
 * 日志归档任务
 */
@Component
@Slf4j
public class LogArchivingTask {

    private static final String CACHEKEY = "TABLE_KEY:";

    @Resource
    private DevLogService devLogService;

    @Resource
    private InitDataService initDataService;


//    @PostConstruct
//    public void onStartUp(){
//        action();
//    }


    /**
     * 每季度归档一次
     * 归档完成以后会删除原日志表
     */
    @Scheduled(cron = "0 0 1 1 1,4,7,10 ?")
    @Transactional
    public  void action() {
        Date date = new Date();
        int year = DateUtil.year(date);
        int quarter = DateUtil.quarter(date);
        String tableName = "DEV_LOG_" + year + "_" + quarter;
        boolean status = initDataService.getInitStatusByType(CACHEKEY + tableName);
        if(status){
            return;
        }
        createHistoryLogTable(tableName);
    }

    private void createHistoryLogTable(String tableName) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName+ " LIKE DEV_LOG" ;
        Connection conn = null;
        Statement stmt = null;
        boolean antoFlag = false;
        try {
            DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
            conn = dbsApi.getCurrentDataSource().getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.execute(sql);
            String copyDataSql = "INSERT INTO " + tableName + " SELECT * FROM DEV_LOG " ;
            stmt.executeUpdate(copyDataSql);
            String deleteDataSql = "DELETE FROM DEV_LOG ";
            stmt.execute(deleteDataSql);
            conn.commit();
        }catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                }catch (SQLException e1) {
                    throw new CommonException("Error during table creation and data archiving: " + e.getMessage());
                }
            }
            antoFlag = true;
        }finally {
            DbUtil.close(stmt);
            DbUtil.close(conn);
        }
        if(!antoFlag){
            InitData initData = new InitData();
            initData.setInitStatus(true);
            initData.setType(CACHEKEY+tableName);
            initData.setRemark("日志归档记录");
            initDataService.saveOrUpdate(initData,new LambdaQueryWrapper<InitData>().eq(InitData::getType,initData.getType()));
        }
    }
}
