package vip.xiaonuo.dev.modular.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.modular.server.service.InitConfigService;
import vip.xiaonuo.dev.modular.server.util.DatabaseCreator;
import vip.xiaonuo.dev.modular.server.util.YamlUtil;
import vip.xiaonuo.dev.modular.server.param.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InitConfigServiceImpl implements InitConfigService {

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

    /**
     * 配置文件迁移前置工作：传送到
     * */
    @Override
    public void singleConf(SingleConfParam singleConfParam){
        File file = new File(singleConfParam.getConfigPath());
        List<Map<String,String>> fileList = new ArrayList<>();
        getFileDataList(file,fileList);//获取全部文件
        fileList.forEach(map -> {
            TransferFileParam transferFileParam = BeanUtil.toBean(singleConfParam,TransferFileParam.class);//singleConfParam.getTransferLocalFileParam();

            transferFileParam.setSourceFilePath(map.get("path"));
            transferFileParam.setFileName(map.get("name"));
            transferFileParam.setTargetFilePath(map.get("path"));
            try{
                yamlUtil.transferFile(transferFileParam);
            }catch (Exception e){
                log.error("上传文件失败！e:"+e.getMessage());
                throw new CommonException("上传文件失败！");
            }
        });
    }

    @Override
    public void transferFile(TransferFileParam transferFileParam){
        yamlUtil.transferFile(transferFileParam);
    }

    private void getFileDataList(File file, List<Map<String,String>> fileList){
        if (file == null) { return; }
        if (file.isDirectory()) {//如果是文件夹，就继续递归
            for (File tempFile : Objects.requireNonNull(file.listFiles())){getFileDataList(tempFile,fileList);}
        }
        //如果是文件，就解析一下，放到文件集合中
        if (file.isFile()){
            String absolutePath = file.getAbsolutePath();
            String fileName = file.getName();
            Map<String,String> map = new HashMap<>();
            map.put("name",fileName);
            map.put("path",absolutePath);
            fileList.add(map);
        }
    }

    @Override
    public void commandSh(UnixCommandParam unixCommandParam){
        try{
            databaseCreator.importDatabase(unixCommandParam);
        }catch (Exception e){
            log.error("执行脚本失败！command:"+unixCommandParam.getCommand()+",host:"+unixCommandParam.getSshHost());
            throw new CommonException("执行command失败！e:"+e.getMessage());
        }
    }
}
