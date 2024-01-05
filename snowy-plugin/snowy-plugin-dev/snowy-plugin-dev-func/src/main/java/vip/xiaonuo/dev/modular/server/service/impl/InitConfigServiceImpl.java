package vip.xiaonuo.dev.modular.server.service.impl;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yunpian.sdk.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.common.util.CommonResponseUtil;
import vip.xiaonuo.dev.modular.dict.entity.DevDict;
import vip.xiaonuo.dev.modular.dict.service.DevDictService;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.dev.modular.file.service.DevFileService;
import vip.xiaonuo.dev.modular.server.entity.SystemConfExec;
import vip.xiaonuo.dev.modular.server.entity.SystemServer;
import vip.xiaonuo.dev.modular.server.service.InitConfigService;
import vip.xiaonuo.dev.modular.server.service.SystemConfExecService;
import vip.xiaonuo.dev.modular.server.service.SystemServerService;
import vip.xiaonuo.dev.modular.server.util.DatabaseCreator;
import vip.xiaonuo.dev.modular.server.util.YamlUtil;
import vip.xiaonuo.dev.modular.server.param.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InitConfigServiceImpl implements InitConfigService {

    private final String MYSQL_DOCKER_NAME = "MYSQL_DOCKER_NAME";
    private final String DUMP_FILE_PATH = "DUMP_FILE_PATH";
    private final String DUMP_SQL_SOURCE_PATH = "DUMP_SQL_SOURCE_PATH";
    private final String DUMP_SQL_TARGET_PATH = "DUMP_SQL_TARGET_PATH";
    private final String YAML_OUT_CONFIG_PATH = "YAML_OUT_CONFIG_PATH";
    private final String YAML_CONFIG_PATH = "YAML_CONFIG_PATH";
    private final String YML_CONFIG_PATH = "YML_CONFIG_PATH";
    private final String YML_OUT_CONFIG_PATH = "YML_OUT_CONFIG_PATH";
    private final String SYSTEM_SOURCE_IP = "SYSTEM_SOURCE_IP";
    private final String SINGLE_CONFIG_PATH = "SINGLE_CONFIG_PATH";
    @Resource
    private DatabaseCreator databaseCreator;
    @Resource
    private YamlUtil yamlUtil;
    @Resource
    SystemConfExecService systemConfExecService;
    @Resource
    DevFileService devFileService;
    @Resource
    DevDictService devDictService;
    @Resource
    private SystemServerService systemServerService;

    @Override
    public void createDataBase(DataBaseParam dataBaseParam) throws Exception{
        databaseCreator.createDatabase(dataBaseParam);
        String datebaseJson = JSONUtil.toJsonStr(dataBaseParam);
        if(ObjectUtil.isEmpty(dataBaseParam.getSystemId())){
            log.error("系统服务ID不能为空");
            throw new CommonException("系统服务systemId不能为空。");
        }
        List<SystemConfExec> confExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,dataBaseParam.getSystemId()));
        SystemConfExec systemConfExec = CollectionUtil.isNotEmpty(confExecList) ? confExecList.get(0) : new SystemConfExec();
        systemConfExec.setDataBaseJson(datebaseJson);
        systemConfExec.setSystemId(dataBaseParam.getSystemId());
        systemConfExecService.saveOrUpdate(systemConfExec);
        updateSystemServer(dataBaseParam.getSystemId(),1);
    }

    @Override
    public void createTable(TableParam tableParam){
        if(ObjectUtil.isEmpty(tableParam.getSystemId())){
            log.error("系统服务ID不能为空");
            throw new CommonException("系统服务ID不能为空");
        }
        List<SystemConfExec> systemConfExecs = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,tableParam.getSystemId()));
        if(CollectionUtil.isEmpty(systemConfExecs) || StringUtils.isEmpty(systemConfExecs.get(0).getDataBaseJson())){
            throw new CommonException("未找到系统服务操作记录！");
        }
        SystemConfExec systemConfExec = systemConfExecs.get(0);
        DataBaseParam dataBaseParam = BeanUtil.toBean(systemConfExec.getDataBaseJson(),DataBaseParam.class);
        tableParam.setSshHost(dataBaseParam.getDbIp());
        tableParam.setSshPort(22);
        tableParam.setDatabaseName(dataBaseParam.getDbName());
        tableParam.setDatabaseUsername(dataBaseParam.getUsername());
        tableParam.setDatabasePassword(dataBaseParam.getPassword());
        String dumpFilePath = devDictService.list(new LambdaQueryWrapper<DevDict>().eq(DevDict::getDictLabel,DUMP_FILE_PATH)).get(0).getDictValue();
        tableParam.setDumpFilePath(dumpFilePath);
        String dockerName = devDictService.list(new LambdaQueryWrapper<DevDict>().eq(DevDict::getDictLabel,MYSQL_DOCKER_NAME)).get(0).getDictValue();
        tableParam.setDockerMyName(dockerName);


        String command = String.format(" docker exec -i %s mysql -u%s -p%s  %s < %s ",tableParam.getDockerMyName(),tableParam.getDatabaseUsername(),
                tableParam.getDatabasePassword(),tableParam.getDatabaseName(),tableParam.getDumpFilePath());
        tableParam.setCommand(command);
        UnixCommandParam unixCommandParam = BeanUtil.toBean(tableParam,UnixCommandParam.class);
        databaseCreator.importDatabase(unixCommandParam);
        String tableJson = JSONUtil.toJsonStr(tableParam);
        systemConfExec.setTableJson(tableJson);
        systemConfExec.setSystemId(tableParam.getSystemId());
        systemConfExecService.saveOrUpdate(systemConfExec);
        updateSystemServer(tableParam.getSystemId(),3);
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
        String configYamlPath = devDictService.list(new LambdaQueryWrapper<DevDict>().eq(DevDict::getDictLabel,YAML_CONFIG_PATH)).get(0).getDictValue();
        yamlParam.setConfigPath(configYamlPath);
        String outYamlPath = devDictService.list(new LambdaQueryWrapper<DevDict>().eq(DevDict::getDictLabel,YAML_OUT_CONFIG_PATH)).get(0).getDictValue();
        yamlParam.setOutYamlPath(outYamlPath);
        try{
            yamlUtil.yamlData(yamlParam);
            String yamlJson = JSONUtil.toJsonStr(yamlParam);
            if(ObjectUtil.isEmpty(yamlParam.getSystemId())){
                log.error("系统服务ID不能为空");
                throw new CommonException("系统服务systemId不能为空。");
            }
            List<SystemConfExec> confExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,yamlParam.getSystemId()));
            SystemConfExec systemConfExec = CollectionUtil.isNotEmpty(confExecList) ? confExecList.get(0) : new SystemConfExec();
            systemConfExec.setApplYamlJson(yamlJson);
            systemConfExec.setSystemId(yamlParam.getSystemId());
            systemConfExecService.saveOrUpdate(systemConfExec);
        }catch (Exception e){
            log.error("创建yaml文件失败，e:"+e.getMessage());
            throw new CommonException("创建yaml文件失败,e:"+e.getMessage());
        }

        String configYmlPath = devDictService.list(new LambdaQueryWrapper<DevDict>().eq(DevDict::getDictLabel,YML_CONFIG_PATH)).get(0).getDictValue();
        yamlParam.setConfigPath(configYmlPath);
        String outYmlPath = devDictService.list(new LambdaQueryWrapper<DevDict>().eq(DevDict::getDictLabel,YML_OUT_CONFIG_PATH)).get(0).getDictValue();
        yamlParam.setOutYamlPath(outYmlPath);
        try{
            yamlUtil.yamlData(yamlParam);
            String yamlJson = JSONUtil.toJsonStr(yamlParam);
            if(ObjectUtil.isEmpty(yamlParam.getSystemId())){
                log.error("系统服务ID不能为空");
                throw new CommonException("系统服务systemId不能为空。");
            }
            List<SystemConfExec> confExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,yamlParam.getSystemId()));
            SystemConfExec systemConfExec = CollectionUtil.isNotEmpty(confExecList) ? confExecList.get(0) : new SystemConfExec();
            systemConfExec.setDockYamlJson(yamlJson);
            systemConfExec.setSystemId(yamlParam.getSystemId());
            systemConfExecService.saveOrUpdate(systemConfExec);
        }catch (Exception e){
            log.error("创建yaml文件失败，e:"+e.getMessage());
            throw new CommonException("创建yaml文件失败,e:"+e.getMessage());
        }
    }

    /**
     * 配置文件迁移前置工作：传送到目标服务器
     * */
    @Override
    public void singleConf(SingleConfParam singleConfParam){
        if(ObjectUtil.isEmpty(singleConfParam.getSystemId())){
            log.error("系统服务ID不能为空");
            throw new CommonException("系统服务systemId不能为空。");
        }
         List<SystemConfExec> systemConfExecs = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,singleConfParam.getSystemId()));
        if(CollectionUtil.isEmpty(systemConfExecs) || StringUtils.isEmpty(systemConfExecs.get(0).getDataBaseJson())){
            throw new CommonException("未找到系统服务操作记录！");
        }
        SystemConfExec systemConfExec = systemConfExecs.get(0);
        TransferFileParam transferFile = BeanUtil.toBean(systemConfExec.getTransferSqlJson(),TransferFileParam.class);
        BeanUtil.copyProperties(transferFile,singleConfParam);

        String singleConfigPath = devDictService.list(new LambdaQueryWrapper<DevDict>().eq(DevDict::getDictLabel,SINGLE_CONFIG_PATH)).get(0).getDictValue();
        singleConfParam.setConfigPath(singleConfigPath);
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
        String remoteYamlJson = JSONUtil.toJsonStr(singleConfParam);
        systemConfExec.setRemoteYamlJson(remoteYamlJson);
        systemConfExec.setSystemId(singleConfParam.getSystemId());
        systemConfExecService.saveOrUpdate(systemConfExec);
    }

    @Override
    public  void transferSqlFile(TransferFileParam transferFileParam){
        if(ObjectUtil.isEmpty(transferFileParam.getSystemId())){
            log.error("系统服务ID不能为空");
            throw new CommonException("系统服务systemId不能为空。");
        }
        String systemSourceIp =  devDictService.list(new LambdaQueryWrapper<DevDict>().eq(DevDict::getDictLabel,SYSTEM_SOURCE_IP)).get(0).getDictValue();
        transferFileParam.setSourceHost(systemSourceIp);
        String sourceFilePath = devDictService.list(new LambdaQueryWrapper<DevDict>().eq(DevDict::getDictLabel,DUMP_SQL_SOURCE_PATH)).get(0).getDictValue();
        transferFileParam.setSourceFilePath(sourceFilePath);
        String targetFilePath = devDictService.list(new LambdaQueryWrapper<DevDict>().eq(DevDict::getDictLabel,DUMP_SQL_TARGET_PATH)).get(0).getDictValue();
        transferFileParam.setTargetFilePath(targetFilePath);
        File file = new File(transferFileParam.getSourceFilePath());
        if(!file.exists()){
            throw new CommonException("该文件不存在！");
        }
        transferFileParam.setFileName(file.getName());
        yamlUtil.transferFile(transferFileParam);

        //保存操作记录
        String transferSqlJson = JSONUtil.toJsonStr(transferFileParam);
        List<SystemConfExec> confExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,transferFileParam.getSystemId()));
        SystemConfExec systemConfExec = CollectionUtil.isNotEmpty(confExecList) ? confExecList.get(0) : new SystemConfExec();
        systemConfExec.setTransferSqlJson(transferSqlJson);
        systemConfExec.setSystemId(transferFileParam.getSystemId());
        systemConfExecService.saveOrUpdate(systemConfExec);
        updateSystemServer(transferFileParam.getSystemId(),2);
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
         /*List<SystemConfExec> systemConfExecs = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,tableParam.getSystemId()));
        if(CollectionUtil.isEmpty(systemConfExecs) || StringUtils.isEmpty(systemConfExecs.get(0).getDataBaseJson())){
            throw new CommonException("未找到系统服务操作记录！");
        }
        SystemConfExec systemConfExec = systemConfExecs.get(0);
        DataBaseParam dataBaseParam = BeanUtil.toBean(systemConfExec.getDataBaseJson(),DataBaseParam.class);
        BeanUtil.copyProperties(dataBaseParam,tableParam);*/
        try{
            databaseCreator.importDatabase(unixCommandParam);
            String remoteExecJson = JSONUtil.toJsonStr(unixCommandParam);
            if(ObjectUtil.isEmpty(unixCommandParam.getSystemId())){
                log.error("系统服务ID不能为空");
                throw new CommonException("系统服务systemId不能为空。");
            }
            List<SystemConfExec> confExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,unixCommandParam.getSystemId()));
            SystemConfExec systemConfExec = CollectionUtil.isNotEmpty(confExecList) ? confExecList.get(0) : new SystemConfExec();
            systemConfExec.setRemoteExecJson(remoteExecJson);
            systemConfExec.setSystemId(unixCommandParam.getSystemId());
            systemConfExecService.saveOrUpdate(systemConfExec);
        }catch (Exception e){
            log.error("执行脚本失败！command:"+unixCommandParam.getCommand()+",host:"+unixCommandParam.getSshHost());
            throw new CommonException("执行command失败！e:"+e.getMessage());
        }
    }

    @Override
    public JSONObject importUser(ImportUserParam importUserParam) {
        if(ObjectUtil.isEmpty(importUserParam.getSystemId())){
            log.error("系统服务ID不能为空");
            throw new CommonException("系统服务systemId不能为空。");
        }
         List<SystemConfExec> systemConfExecs = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,importUserParam.getSystemId()));
        if(CollectionUtil.isEmpty(systemConfExecs) || StringUtils.isEmpty(systemConfExecs.get(0).getDataBaseJson())){
            throw new CommonException("未找到系统服务操作记录！");
        }
        SystemConfExec systemConfExec = systemConfExecs.get(0);
        DataBaseParam dataBaseParam = BeanUtil.toBean(systemConfExec.getDataBaseJson(),DataBaseParam.class);
        importUserParam.setIp(dataBaseParam.getDbIp());
        importUserParam.setDbName(dataBaseParam.getDbName());
        importUserParam.setPort(dataBaseParam.getPort());
        importUserParam.setUsername(dataBaseParam.getUsername());
        importUserParam.setPassword(dataBaseParam.getPassword());
        try {
            DevFile devFile = devFileService.getById(importUserParam.getFileId());
            if(ObjectUtil.isEmpty(devFile) || !FileUtil.file(devFile.getStoragePath()).exists()){
                throw new CommonException("文件不存在！fileId："+importUserParam.getFileId());
            }
            File file = FileUtil.file(devFile.getStoragePath());
            // 创建临时文件
            File tempFile = FileUtil.writeBytes(getFileBytes(file), FileUtil.file(FileUtil.getTmpDir() +
                    FileUtil.FILE_SEPARATOR + "serverUserTemplate.xlsx"));
            // 读取excel
            List<UserParam> sysUserImportParamList =  EasyExcel.read(tempFile).head(UserParam.class).sheet()
                    .headRowNumber(2).doReadSync();
            InitUserParam initUserParam = BeanUtil.toBean(importUserParam,InitUserParam.class);
            initUserParam.setUserParamList(sysUserImportParamList);
            initData(initUserParam);
            String remoteExecJson = JSONUtil.toJsonStr(importUserParam);

            systemConfExec.setInitUserJson(remoteExecJson);
            systemConfExec.setSystemId(importUserParam.getSystemId());
            systemConfExecService.saveOrUpdate(systemConfExec);
            return JSONUtil.createObj().set("totalCount", sysUserImportParamList.size());
        } catch (Exception e) {
            log.error(">>> 用户导入失败：", e);
            throw new CommonException("用户导入失败");
        }
    }
    public byte[] getFileBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            return bytes;
        }
    }

    @Override
    public void downloadImportUserTemplate(HttpServletResponse response) throws IOException {
        try {
            InputStream inputStream = POICacheManager.getFile("serverUserTemplate.xlsx");
            byte[] bytes = IoUtil.readBytes(inputStream);
            CommonDownloadUtil.download("EPC人员导入模板.xlsx", bytes, response);
        } catch (Exception e) {
            log.error(">>> 下载用户导入模板失败：", e);
            CommonResponseUtil.renderError(response, "下载用户导入模板失败");
        }
    }

    @Override
    public void connectDataBase(ConnectDataBaseParam connectDataBaseParam){
        databaseCreator.connectDataBase(connectDataBaseParam);
    }

    /**
     * 更新保存SystemServer
     * */
    private void updateSystemServer(String systemId,Integer systemStatus){
        SystemServer systemServer = systemServerService.getById(systemId);
        systemServer.setSystemStatus(systemStatus);
        systemServerService.updateById(systemServer);
    }
}
