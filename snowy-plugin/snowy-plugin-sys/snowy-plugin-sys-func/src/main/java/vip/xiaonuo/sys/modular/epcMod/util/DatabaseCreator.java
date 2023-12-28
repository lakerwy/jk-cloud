package vip.xiaonuo.sys.modular.epcMod.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.sys.modular.epcMod.param.DataBaseParam;
import vip.xiaonuo.sys.modular.epcMod.param.TableParam;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@Slf4j
public class DatabaseCreator {

    public void createDatabase(DataBaseParam dataBaseParam) throws Exception{
        try {
            //jdbc:mysql://10.0.33.81:3306?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useInformationSchema=true
            String unicode = ":3306?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useInformationSchema=true";
            String url =String.format("jdbc:mysql://%s%s",dataBaseParam.getDbIp(),unicode);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, dataBaseParam.getUsername(), dataBaseParam.getPassword());//建立连接
            Statement stmt = connection.createStatement();//创建Statement对象

            String sql = "CREATE DATABASE IF NOT EXISTS " + dataBaseParam.getDbName();//执行创建数据库的SQL命令
            stmt.executeUpdate(sql);
            stmt.close();
            connection.close();
            log.info("远程创建数据库成功，数据库名称："+dataBaseParam.getDbName());
        } catch (SQLException e) {
            log.error("远程创建数据库失败，数据库名称："+dataBaseParam.getDbName()+",e:"+e.toString());
            throw new CommonException("数据库创建失败！e:"+e.getMessage());
        }
    }

    public void importDatabase(TableParam tableParam) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(tableParam.getSshUsername(), tableParam.getSshHost(), tableParam.getSshPort());
            session.setPassword(tableParam.getSshPassword());

            session.setConfig("StrictHostKeyChecking", "no"); //允许连接不信任的主机
            session.connect();
            Channel channel = session.openChannel("exec");
            String command = String.format(" docker exec -i %s mysql -u%s -p%s  %s < %s ",tableParam.getDockerMyName(),tableParam.getDatabaseUsername(),
                    tableParam.getDatabasePassword(),tableParam.getDatabaseName(),tableParam.getDumpFilePath());
            ((ChannelExec) channel).setCommand(command);
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    log.info(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) continue;
                    if(channel.getExitStatus() != 0){
                        log.error("建表失败，请检查连接信息！exit-status: " + channel.getExitStatus());
                    }
                    log.info("建表成功-exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            log.error("执行远程mysql建表命令时发生错误：" + e.getMessage());
            e.printStackTrace();
            throw new CommonException("执行远程mysql建表命令时发生错误：" + e.getMessage());
        }
    }
}
