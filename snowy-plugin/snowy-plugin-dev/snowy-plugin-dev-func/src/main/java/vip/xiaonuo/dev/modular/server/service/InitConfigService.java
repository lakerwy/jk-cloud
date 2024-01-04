package vip.xiaonuo.dev.modular.server.service;

import cn.hutool.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.dev.modular.server.param.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface InitConfigService {

    /**
     * 建库
     * */
    void createDataBase(DataBaseParam dataBaseParam) throws Exception;

    /**
     * 建表
     * */
    void createTable(TableParam tableParam);

    /**
     * 输出yaml配置文件
     * */
    void createYaml(YamlParam yamlParam);

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

    void connectDataBase(ConnectDataBaseParam connectDataBaseParam);
}
