package vip.xiaonuo.sys.modular.epcMod.service.impl;

import org.springframework.stereotype.Service;
import vip.xiaonuo.sys.modular.epcMod.param.DataBaseParam;
import vip.xiaonuo.sys.modular.epcMod.param.TableParam;
import vip.xiaonuo.sys.modular.epcMod.service.EpcConfigService;
import vip.xiaonuo.sys.modular.epcMod.util.DatabaseCreator;

import javax.annotation.Resource;

@Service
public class EpcConfigServiceImpl implements EpcConfigService {

    @Resource
    private DatabaseCreator databaseCreator;

    @Override
    public void createDataBase(DataBaseParam dataBaseParam) throws Exception{
        databaseCreator.createDatabase(dataBaseParam);
    }

    @Override
    public void createTable(TableParam tableParam){
        databaseCreator.importDatabase(tableParam);
    }

}
