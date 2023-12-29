package vip.xiaonuo.sys.modular.epcMod.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.sys.modular.epcMod.param.*;
import vip.xiaonuo.sys.modular.epcMod.service.EpcConfigService;
import vip.xiaonuo.sys.modular.epcMod.util.DatabaseCreator;
import vip.xiaonuo.sys.modular.epcMod.util.YamlUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
        String command = String.format(" docker exec -i %s mysql -u%s -p%s  %s < %s ",tableParam.getDockerMyName(),tableParam.getDatabaseUsername(),
                tableParam.getDatabasePassword(),tableParam.getDatabaseName(),tableParam.getDumpFilePath());
        tableParam.setCommand(command);
        UnixCommandParam unixCommandParam = BeanUtil.toBean(tableParam,UnixCommandParam.class);
        databaseCreator.importDatabase(unixCommandParam);
    }

    @Override
    public void initData(InitUserParam initUserParam){
        checkInitData(initUserParam);
        databaseCreator.initUser(initUserParam);
    }

    /**
     * 校验导入数据
     * @param initUserParam
     */
    public void checkInitData(InitUserParam initUserParam){
        if(CollUtil.isNotEmpty(initUserParam.getUserParamList())){
           List<String> phoneList = initUserParam.getUserParamList().stream().map(x->x.getPhone()).distinct().collect(Collectors.toList());
           if(phoneList.size()!=initUserParam.getUserParamList().size()){
               throw new CommonException("手机号存在重复");
           }
        }
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

    @Override
    public void transferFile(TransferFileParam transferFileParam){
        yamlUtil.transferFile(transferFileParam);
    }

}
