package vip.xiaonuo.sys.modular.epcMod.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.sys.modular.epcMod.param.DataBaseParam;
import vip.xiaonuo.sys.modular.epcMod.param.InitUserParam;
import vip.xiaonuo.sys.modular.epcMod.param.TableParam;
import vip.xiaonuo.sys.modular.epcMod.param.UserParam;
import vip.xiaonuo.sys.modular.epcMod.service.EpcConfigService;
import vip.xiaonuo.sys.modular.epcMod.util.DatabaseCreator;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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


}
