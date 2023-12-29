package vip.xiaonuo.sys.modular.epcMod.service;

import vip.xiaonuo.sys.modular.epcMod.param.*;

public interface EpcConfigService {

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
