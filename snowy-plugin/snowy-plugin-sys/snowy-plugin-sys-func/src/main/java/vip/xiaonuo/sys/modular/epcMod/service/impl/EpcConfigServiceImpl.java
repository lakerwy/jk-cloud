package vip.xiaonuo.sys.modular.epcMod.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.sys.modular.epcMod.param.DataBaseParam;
import vip.xiaonuo.sys.modular.epcMod.param.TableParam;
import vip.xiaonuo.sys.modular.epcMod.param.YamlParam;
import vip.xiaonuo.sys.modular.epcMod.service.EpcConfigService;
import vip.xiaonuo.sys.modular.epcMod.util.DatabaseCreator;
import vip.xiaonuo.sys.modular.epcMod.util.YamlUtil;

import javax.annotation.Resource;

@Slf4j
@Service
public class EpcConfigServiceImpl implements EpcConfigService {

    @Resource
    private DatabaseCreator databaseCreator;
    @Resource
    private YamlUtil yamlUtil;

    @Override
    public void createDataBase(DataBaseParam dataBaseParam) throws Exception{
        databaseCreator.createDatabase(dataBaseParam);
    }

    @Override
    public void createTable(TableParam tableParam){
        databaseCreator.importDatabase(tableParam);
    }

    @Override
    public void createYaml(YamlParam yamlParam){
        try{
            yamlUtil.yamlData(yamlParam);
        }catch (Exception e){
            log.error("创建yaml文件失败，e:"+e.getMessage());
            throw new CommonException("创建yaml文件失败,e:"+e.getMessage());
        }
    }

}
