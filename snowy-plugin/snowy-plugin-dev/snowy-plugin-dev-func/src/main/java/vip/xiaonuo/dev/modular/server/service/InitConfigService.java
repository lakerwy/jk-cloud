package vip.xiaonuo.dev.modular.server.service;

import vip.xiaonuo.dev.modular.server.param.*;

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
     * 服务器之间传文件
     * */
    void transferFile(TransferFileParam transferFileParam);
}
