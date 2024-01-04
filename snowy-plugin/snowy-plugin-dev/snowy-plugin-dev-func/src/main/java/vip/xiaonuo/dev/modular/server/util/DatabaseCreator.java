package vip.xiaonuo.dev.modular.server.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.auth.core.enums.SysUserStatusEnum;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.util.CommonCryptogramUtil;
import vip.xiaonuo.dev.modular.server.enums.InitRelationCategoryEnum;
import vip.xiaonuo.dev.modular.server.param.*;
import vip.xiaonuo.dev.modular.server.vo.ObjInfo;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DatabaseCreator {

    /**
     * 数据库连接测试
     * */
    public void connectDataBase(ConnectDataBaseParam connectDataBaseParam){
        // 数据库连接参数
        //String unicode = "?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useInformationSchema=true";
        //String url =String.format("jdbc:mysql://%s%s%s%s",connectDataBaseParam.getDbIp(),":",connectDataBaseParam.getPort(),unicode);

        try {
          /*  Class.forName("com.mysql.cj.jdbc.Driver");//加载MySQL JDBC驱动
            // 建立数据库连接
            Connection connection = DriverManager.getConnection(url, connectDataBaseParam.getUsername(), connectDataBaseParam.getPassword());*/
            String unicode = "?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useInformationSchema=true";
            String url =String.format("jdbc:mysql://%s%s%s%s",connectDataBaseParam.getDbIp(),":",connectDataBaseParam.getPort(),unicode);
            log.info("url-connectSql:"+url);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, connectDataBaseParam.getUsername(), connectDataBaseParam.getPassword());//建立连接
            if (connection != null) {
                log.info("连接数据库成功！");
                connection.close(); // 关闭连接
            } else {
                throw new CommonException("连接数据库失败");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new CommonException("连接数据库失败");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CommonException("连接数据库失败");
        }
    }

    public void createDatabase(DataBaseParam dataBaseParam) throws Exception{
        try {
            //jdbc:mysql://10.0.33.81:3306?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useInformationSchema=true
            String unicode = "?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useInformationSchema=true";
            String url =String.format("jdbc:mysql://%s%s%s%s",dataBaseParam.getDbIp(),":",dataBaseParam.getPort(),unicode);
            log.info("url-connectSql:"+url);
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

    public void importDatabase(UnixCommandParam unixCommandParam) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(unixCommandParam.getSshUsername(), unixCommandParam.getSshHost(), unixCommandParam.getSshPort());
            session.setPassword(unixCommandParam.getSshPassword());

            session.setConfig("StrictHostKeyChecking", "no"); //允许连接不信任的主机
            session.connect();
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(unixCommandParam.getCommand());
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

    /**
     * 初始化用户数据
     * @param initUserParam
     */
    public void initUser(InitUserParam initUserParam) {
        try {
            if(CollUtil.isNotEmpty(initUserParam.getUserParamList())){
                Class.forName("com.mysql.cj.jdbc.Driver");
                //1. 拼接数据库连接串
                String url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useInformationSchema=true",initUserParam.getIp(),initUserParam.getPort(),initUserParam.getDbName());
                //2. 数据处理
                List<UserParam> newUserParam = dealData(initUserParam.getUserParamList(),url,initUserParam.getUsername(),initUserParam.getPassword());
                //3. 生成批量处理sql,并执行
                try(Connection conn = DriverManager.getConnection(url, initUserParam.getUsername(), initUserParam.getPassword())){
                    conn.setAutoCommit(false);
                    //新增用户
                    String userSql = " INSERT INTO SYS_USER(ID,`NAME`,ACCOUNT,`PASSWORD`,GENDER,PHONE,ORG_ID,POSITION_ID,SORT_CODE,USER_STATUS,DELETE_FLAG,CREATE_TIME,CREATE_USER) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                    //新增用户角色对应
                    String relationSql = " INSERT INTO SYS_RELATION(ID,OBJECT_ID,TARGET_ID,CATEGORY) VALUES(?,?,?,?)  ";
                    try(PreparedStatement userStmt = conn.prepareStatement(userSql);
                        PreparedStatement relationStmt = conn.prepareStatement(relationSql)) {
                        for (UserParam item:newUserParam) {
                            userStmt.setString(1,item.getId());
                            userStmt.setString(2,item.getName());
                            userStmt.setString(3,item.getPhone());
                            userStmt.setString(4,CommonCryptogramUtil.doHashValue(item.getPassword()));
                            userStmt.setString(5,item.getGender());
                            userStmt.setString(6,CommonCryptogramUtil.doSm4CbcEncrypt(item.getPhone()));
                            userStmt.setString(7,item.getOrgId());
                            userStmt.setString(8,item.getPositionId());
                            userStmt.setString(9,"1");
                            userStmt.setString(10,SysUserStatusEnum.ENABLE.getValue());
                            userStmt.setString(11,"NOT_DELETE");
                            userStmt.setString(12,DateTime.now().toString());
                            userStmt.setString(13,StpLoginUserUtil.getLoginUser().getId());
                            userStmt.addBatch();

                            relationStmt.setString(1,IdWorker.getIdStr());
                            relationStmt.setString(2,item.getId());
                            relationStmt.setString(3,item.getRoleId());
                            relationStmt.setString(4, InitRelationCategoryEnum.SYS_USER_HAS_ROLE.getValue());
                            relationStmt.addBatch();
                        }
                        int[] userCount = userStmt.executeBatch();
                        int[] relationCount = relationStmt.executeBatch();
                        conn.commit();
                    }catch (SQLException e){
                        conn.rollback();
                        log.debug(e.getMessage());
                    }
                }catch (SQLException e){
                    log.debug(e.getMessage());
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
        }

    }

    /**
     * 查询数据
     * @param url 数据库连接
     * @param user 用户名
     * @param password 密码
     * @param sql sql语句
     * @return
     */
    public List<ObjInfo> selectData(String url, String user, String password, String sql){
        List<ObjInfo> infoList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try(Connection conn = DriverManager.getConnection(url, user, password)){
                try(Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)){
                    while (rs.next()){
                        ObjInfo objInfo = new ObjInfo();
                        objInfo.setId(rs.getString("id"));
                        objInfo.setName(rs.getString("name"));
                        if(sql.contains("SYS_POSITION")){
                            objInfo.setOrgId(rs.getString("orgId"));
                        }
                        infoList.add(objInfo);
                    }
                }
            } catch (SQLException e) {
                log.debug(e.getMessage());
            }
        }catch (Exception e){
            log.debug(e.getMessage());
        }
        return infoList;
    }

    /**
     * 将导入的数据进行处理，关联组织id|角色id|职位id
     * @param userParamList 导入的信息
     * @return
     */
    public List<UserParam> dealData(List<UserParam> userParamList,String url, String user, String password){
        if(CollUtil.isNotEmpty(userParamList)){
            //处理组织|角色|职位
            String orgSql = " SELECT ID,`NAME` FROM `SYS_ORG` where DELETE_FLAG='NOT_DELETE' ";
            String roleSql = " SELECT ID,`NAME` FROM `SYS_ROLE` where DELETE_FLAG='NOT_DELETE' ";
            String positionSql = " SELECT ID,`NAME`,ORG_ID FROM `SYS_POSITION` where DELETE_FLAG='NOT_DELETE' ";
            List<ObjInfo> orgList = selectData(url,user,password,orgSql);
            List<ObjInfo> roleList = selectData(url,user,password,roleSql);
            List<ObjInfo> positionList = selectData(url,user,password,positionSql);
            userParamList.forEach(x->{
                ObjInfo orgInfo = orgList.stream().filter(o -> x.getOrgName().equals(o.getName())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(orgInfo)){
                    x.setOrgId(orgInfo.getId());
                }
                ObjInfo roleInfo = roleList.stream().filter(o -> x.getRoleName().equals(o.getName())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(roleInfo)){
                    x.setRoleId(roleInfo.getId());
                }
                ObjInfo positionInfo = positionList.stream().filter(o -> x.getRoleName().equals(o.getName()) && x.getOrgId().equals(o.getOrgId())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(positionInfo)){
                    x.setPositionId(positionInfo.getId());
                }
                x.setId(IdWorker.getIdStr());
                x.setPassword("123456");
            });
        }
        return userParamList;
    }

}
