package vip.xiaonuo.sys.modular.epcMod.service;

import vip.xiaonuo.sys.modular.epcMod.param.DataBaseParam;
import vip.xiaonuo.sys.modular.epcMod.param.TableParam;

public interface EpcConfigService {

    /**
     * 建库
     * */
    void createDataBase(DataBaseParam dataBaseParam) throws Exception;

    /**
     * 建表
     * */
    void createTable(TableParam tableParam);
}
