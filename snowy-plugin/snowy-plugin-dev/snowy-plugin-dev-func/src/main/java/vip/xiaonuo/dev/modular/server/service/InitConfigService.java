package vip.xiaonuo.dev.modular.server.service;

import cn.hutool.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.dev.modular.server.entity.SystemConfExec;
import vip.xiaonuo.dev.modular.server.entity.SystemServer;
import vip.xiaonuo.dev.modular.server.param.*;
import vip.xiaonuo.dev.modular.server.util.SftpFileUploader;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface InitConfigService {

    /**
     * 建库
     * */
//    Boolean createDataBase(SystemConfExec systemConfExec) throws Exception;

    CompletableFuture<Boolean> createDataBaseAsync(SystemConfExec systemConfExec);

    /**
     * 建表
     * */
    void createTable(TableParam tableParam);

    /**
     * 输出yaml配置文件
     * */
    Boolean createYaml(SystemConfExec systemConfExec);

    /**
     * 初始化用户数据
     * @param initUserParam
     */
    void initData(InitUserParam initUserParam);

    /**
     * 配置文件迁移前置工作：文件传递
     * */
    void singleConf(SingleConfParam singleConfParam) ;

    /**
     * 服务器之间传文件
     * */
    void transferFile(TransferFileParam transferFileParam);

    /**
     * 执行服务器命令
     * */
    void commandSh(UnixCommandParam unixCommandParam);

    /**
     * 初始化EPC用户信息
     * */
    JSONObject importUser(ImportUserParam importUserParam);

    /**
     * 下载用户导入模板
     *
     * @author ljy
     * @date 2024/01/04 10:30
     **/
    void downloadImportUserTemplate(HttpServletResponse response) throws IOException;

    String connectDataBase(ConnectDataBaseParam connectDataBaseParam);

    CompletableFuture<Boolean> transferSqlFile(TransferFileParam transferFileParam);

    /**
     * 初始化系统
     * @param systemConfExecIdParam
     */
    void initSystem(SystemConfExecIdParam systemConfExecIdParam);

    CompletableFuture<Boolean> uploadConfAsync(SftpFileUploader sftpFileUploader,SystemConfExec systemConfExec,Integer status);


    CompletableFuture<Boolean> updateConfAsync(SystemConfExec systemConfExec, SftpFileUploader sftpFileUploader, Integer status, SystemServer systemServer);

    CompletionStage<Boolean> executeRemoteScriptAsync(SftpFileUploader sftpFileUploader, SystemConfExec systemConfExec, Integer status);

    /**
     * 初始化系统,定时更新代码-定时任务
     */
    void initSystemJob();
}
