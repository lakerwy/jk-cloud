package vip.xiaonuo.dev.modular.server.service.impl;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yunpian.sdk.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.common.util.CommonIpAddressUtil;
import vip.xiaonuo.common.util.CommonResponseUtil;
import vip.xiaonuo.common.util.CommonServletUtil;
import vip.xiaonuo.dev.modular.config.entity.DevConfig;
import vip.xiaonuo.dev.modular.config.service.DevConfigService;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.dev.modular.file.service.DevFileService;
import vip.xiaonuo.dev.modular.server.consts.InitConfConstant;
import vip.xiaonuo.dev.modular.server.entity.SystemConfExec;
import vip.xiaonuo.dev.modular.server.entity.SystemServer;
import vip.xiaonuo.dev.modular.server.service.InitConfigService;
import vip.xiaonuo.dev.modular.server.service.SystemConfExecService;
import vip.xiaonuo.dev.modular.server.service.SystemServerService;
import vip.xiaonuo.dev.modular.server.util.DatabaseCreator;
import vip.xiaonuo.dev.modular.server.util.SftpFileUploader;
import vip.xiaonuo.dev.modular.server.util.YamlFileUtil;
import vip.xiaonuo.dev.modular.server.util.YamlUtil;
import vip.xiaonuo.dev.modular.server.param.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InitConfigServiceImpl implements InitConfigService {

    private final String CONF = "conf";
    @Resource
    private DatabaseCreator databaseCreator;
    @Resource
    private YamlUtil yamlUtil;
    @Resource
    SystemConfExecService systemConfExecService;
    @Resource
    DevFileService devFileService;
    @Resource
    DevConfigService devConfigService;
    @Resource
    private SystemServerService systemServerService;

    @Resource
    CommonCacheOperator commonCacheOperator;

    @Resource
    YamlFileUtil yamlFileUtil;

    /**
     * 系统初始化
     * @param systemConfExecIdParam
     */
    @Override
    public void initSystem(SystemConfExecIdParam systemConfExecIdParam) {
        SystemConfExec systemConfExec = systemConfExecService.getById(systemConfExecIdParam.getId());
        if (ObjectUtil.isEmpty(systemConfExec)) {
            throw new CommonException("未找到系统配置");
        }
//
//        // 生成系统的唯一标识
//        generateNo();

        SftpFileUploader sftpFileUploader = JsonUtil.fromJson(systemConfExec.getSystemServerJson(), SftpFileUploader.class);

        CompletableFuture<Boolean> createDbFuture = createDataBaseAsync(systemConfExec);

        createDbFuture
                .thenComposeAsync(createDbFlag -> createDbFlag ? uploadConfAsync(sftpFileUploader,systemConfExec.getSystemId(),2) : CompletableFuture.completedFuture(false))
                .thenComposeAsync(uploadConfFlag -> uploadConfFlag ? updateConfAsync(systemConfExec, sftpFileUploader, 3) : CompletableFuture.completedFuture(false))
                .thenComposeAsync(executeConfFlag -> executeConfFlag ? executeRemoteScriptAsync(sftpFileUploader,systemConfExec.getSystemId(),4) : CompletableFuture.completedFuture(false));
    }


    @Override
    public CompletableFuture<Boolean> createDataBaseAsync(SystemConfExec systemConfExec) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String dataBaseJson = systemConfExec.getDataBaseJson();
                DataBaseParam dataBaseParam = JsonUtil.fromJson(dataBaseJson, DataBaseParam.class);
                String dataBaseName = "EPC" + systemConfExec.getSystemServerNo();
                dataBaseParam.setDbName(dataBaseName);

                Boolean flag = databaseCreator.createDatabase(dataBaseParam);
                if (!flag) {
                    log.error("创建数据库失败");
                    return false;
                }
                systemConfExecService.update(new LambdaUpdateWrapper<SystemConfExec>().set(SystemConfExec::getDataBaseJson,JSONUtil.toJsonStr(dataBaseParam)).eq(SystemConfExec::getId,systemConfExec.getId()));
                updateSystemServer(systemConfExec.getSystemId(), 1);
                return true;
            } catch (Exception e) {
                log.error("异步创建数据库失败", e);
                return false;
            }
        }).exceptionally(e -> {
            log.error("createDataBaseAsync failed", e);
            return false;
        });
    }


    @Override
    public void createTable(TableParam tableParam){
        List<SystemConfExec> systemConfExecs = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,tableParam.getSystemId()));
        if(CollectionUtil.isEmpty(systemConfExecs) || StringUtils.isEmpty(systemConfExecs.get(0).getDataBaseJson())){
            throw new CommonException("未找到系统服务操作记录！");
        }
        SystemConfExec systemConfExec = systemConfExecs.get(0);
        //DataBaseParam dataBaseParam = BeanUtil.toBean(systemConfExec.getDataBaseJson(),DataBaseParam.class);
        DataBaseParam dataBaseParam = JsonUtil.fromJson(systemConfExec.getDataBaseJson(), DataBaseParam.class);
//        TransferFileParam transferFileParam = JsonUtil.fromJson(systemConfExec.getTransferSqlJson(), TransferFileParam.class);
        tableParam.setSshHost(dataBaseParam.getDbIp());
        tableParam.setSshPort(22);
        tableParam.setDatabaseName(dataBaseParam.getDbName());
        tableParam.setDatabaseUsername(dataBaseParam.getUserName());
        tableParam.setDatabasePassword(dataBaseParam.getPassword());
        String dumpFilePathConf = devConfigService.getValueByKey(InitConfConstant.DUMP_SQL_SOURCE_PATH);
        if(StrUtil.isEmpty(dumpFilePathConf)){
            dumpFilePathConf = "/home/sql/snowy_ten_mysql.sql";
        }
//        String dumpFilePathConf = devConfigService.list(new LambdaQueryWrapper<DevConfig>().eq(DevConfig::getConfigKey,DUMP_SQL_TARGET_PATH)).get(0).getConfigValue();
//        tableParam.setDumpFilePath(dumpFilePathConf+transferFileParam.getFileName());
        tableParam.setDumpFilePath(dumpFilePathConf);
        String dockerName = devConfigService.getValueByKey(InitConfConstant.MYSQL_DOCKER_NAME);
//        String dockerName = devConfigService.list(new LambdaQueryWrapper<DevConfig>().eq(DevConfig::getConfigKey,InitConfConstant.MYSQL_DOCKER_NAME)).get(0).getConfigValue();
        if(StrUtil.isEmpty(dockerName)){
            dockerName = "epcMysql";
        }
        tableParam.setDockerMyName(dockerName);

        String command = String.format(" docker exec -i %s mysql -u%s -p%s  %s < %s ",tableParam.getDockerMyName(),tableParam.getDatabaseUsername(),
                tableParam.getDatabasePassword(),tableParam.getDatabaseName(),tableParam.getDumpFilePath());
        tableParam.setCommand(command);
        UnixCommandParam unixCommandParam = BeanUtil.toBean(tableParam,UnixCommandParam.class);
//        unixCommandParam.setSshPort(22);
//        databaseCreator.importDatabase(unixCommandParam);
        CompletableFuture<Boolean> completableFuture = databaseCreator.executeCommand(unixCommandParam);
        completableFuture.thenAccept(flag->{
            if(flag){
                String tableJson = JSONUtil.toJsonStr(tableParam);
//                systemConfExec.setTableJson(tableJson);
                systemConfExec.setSystemId(tableParam.getSystemId());
                //可能存在异常-----上下文context中无法获取到userId
                systemConfExecService.saveOrUpdate(systemConfExec);
                updateSystemServer(systemConfExec.getSystemId(),3);
            }
        });

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


//    public void createYaml2(YamlParam yamlParam){
//        List<SystemConfExec> systemConfExecs = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,yamlParam.getSystemId()));
//        if(CollectionUtil.isEmpty(systemConfExecs) || StringUtils.isEmpty(systemConfExecs.get(0).getDataBaseJson())){
//            throw new CommonException("未找到系统服务操作记录！");
//        }
//        String  currentTimeMillis = String.valueOf(System.currentTimeMillis());
//        String configYamlPath = devConfigService.getValueByKey(InitConfConstant.YAML_CONFIG_PATH);
//        if(StrUtil.isEmpty(configYamlPath)){
//            configYamlPath = "/home/epc/sh/conf/application.yaml";
//        }
////        String configYamlPath = devConfigService.list(new LambdaQueryWrapper<DevConfig>().eq(DevConfig::getConfigKey,InitConfConstant.YAML_CONFIG_PATH)).get(0).getConfigValue();
//        File configYamlFile = new File(configYamlPath);
//        if(!configYamlFile.exists()){
//            throw new CommonException("配置文件不存在！configYamlPath："+configYamlPath);
//        }
//        yamlParam.setConfigPath(configYamlPath);
//        String outYamlPath = devConfigService.getValueByKey(InitConfConstant.YAML_OUT_CONFIG_PATH);
////        String outYamlPath = devConfigService.list(new LambdaQueryWrapper<DevConfig>().eq(DevConfig::getConfigKey,InitConfConstant.YAML_OUT_CONFIG_PATH)).get(0).getConfigValue();
//        yamlParam.setOutYamlPath(outYamlPath);
//        try{
//            DataBaseParam dataBaseParam = JsonUtil.fromJson(systemConfExecs.get(0).getDataBaseJson(),DataBaseParam.class);
//            String url =String.format("jdbc:mysql://%s/%s%s%s%s",dataBaseParam.getDbIp(),dataBaseParam.getDbName(),":",dataBaseParam.getPort(),InitConfConstant.MYSQL_PARAMS);
//            Map<String,Object> paramMap = new HashMap<>();
//            paramMap.put("mysql.url",url);
//            Object  mysqlPassword = StringUtils.isNumeric(dataBaseParam.getPassword()) ? Integer.parseInt(dataBaseParam.getPassword()) : dataBaseParam.getPassword();
//            paramMap.put("mysqlpassword",mysqlPassword);
//            paramMap.put("mysqlusername",dataBaseParam.getUsername());
//
//            String exEpc = "exchange-epc-"+currentTimeMillis;
//            paramMap.put("exchange-epc-namex",exEpc);
//            String exEpcDelayed = "exchange-epc-delayed-"+currentTimeMillis;
//            paramMap.put("exchange-epc-delayed-namex",exEpcDelayed);
//            yamlParam.setParamMap(paramMap);
//            yamlUtil.yamlData(yamlParam);
//            yamlParam.setParamMap(null);
//            String yamlJson = JSONUtil.toJsonStr(yamlParam);
//            List<SystemConfExec> confExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,yamlParam.getSystemId()));
//            SystemConfExec systemConfExec = CollectionUtil.isNotEmpty(confExecList) ? confExecList.get(0) : new SystemConfExec();
//            systemConfExec.setApplYamlJson(yamlJson);
//            systemConfExec.setSystemId(yamlParam.getSystemId());
//            systemConfExecService.saveOrUpdate(systemConfExec);
//        }catch (Exception e){
//            log.error("创建yaml文件失败，e:"+e.getMessage());
//            throw new CommonException("创建yaml文件失败,e:"+e.getMessage());
//        }
//        String configYmlPath = devConfigService.getValueByKey(InitConfConstant.COMPOSE_YML_CONFIG_PATH);
//        if(StrUtil.isEmpty(configYmlPath)){
//            configYmlPath = "/home/epc/sh/docker-compose.yml";
//        }
////        String configYmlPath = devConfigService.list(new LambdaQueryWrapper<DevConfig>().eq(DevConfig::getConfigKey,InitConfConstant.COMPOSE_YML_CONFIG_PATH)).get(0).getConfigValue();
//        yamlParam.setConfigPath(configYmlPath);
//        String outYmlPath = devConfigService.getValueByKey(InitConfConstant.COMPOSE_YML_OUT_CONFIG_PATH);
//        if(StrUtil.isEmpty(outYmlPath)){
//            outYmlPath = "/home/epc/source/sh/docker-compose.yml";
//        }
//       // String outYmlPath = devConfigService.list(new LambdaQueryWrapper<DevConfig>().eq(DevConfig::getConfigKey,InitConfConstant.COMPOSE_YML_OUT_CONFIG_PATH)).get(0).getConfigValue();
//        yamlParam.setOutYamlPath(outYmlPath);
//        try{
//            Map<String,Object> paramMap = new HashMap<>();
//            String dockerComposePort = yamlParam.getDockerComposePort();
//            //- "${SYSTEM_PORT}:80"
//            paramMap.put("dockerComposeProt","- \""+dockerComposePort+":80\"");
//            String dockerName = "epc-"+ currentTimeMillis;
//            paramMap.put("system.container.name",dockerName);
//            yamlParam.setParamMap(paramMap);
//            yamlUtil.yamlData(yamlParam);
//            yamlParam.setParamMap(null);
//            String yamlJson = JSONUtil.toJsonStr(yamlParam);
//            List<SystemConfExec> confExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,yamlParam.getSystemId()));
//            SystemConfExec systemConfExec = CollectionUtil.isNotEmpty(confExecList) ? confExecList.get(0) : new SystemConfExec();
//            systemConfExec.setDockYamlJson(yamlJson);
//            systemConfExec.setSystemId(yamlParam.getSystemId());
//            systemConfExecService.saveOrUpdate(systemConfExec);
//        }catch (Exception e){
//            log.error("创建yaml文件失败，e:"+e.getMessage());
//            throw new CommonException("创建yaml文件失败,e:"+e.getMessage());
//        }
//    }


    @Override
    public Boolean createYaml(SystemConfExec systemConfExec) {
        Boolean flag = saveYamlInfoToDB(systemConfExec, "app");
        if(!flag){
            return false;
        }
        flag = saveYamlInfoToDB(systemConfExec, "docker");
        return flag;
    }

    private Boolean saveYamlInfoToDB(SystemConfExec systemConfExec, String yamlType) {
        int systemNo = getSystemNo();
        String configPathKey = getConfigPathKey(yamlType);
        String outYamlPathKey = getOutYamlPathKey(yamlType);
        String configPath = getConfigPath(configPathKey, yamlType.equals("app")?"/home/epc/sh/conf/application.yaml":"/home/epc/sh/docker-compose.yml");
        String outYamlPath = getOutYamlPath(outYamlPathKey, yamlType.equals("app")?"/home/epc/source/sh/conf/application.yaml":"/home/epc/source/sh/docker-compose.yml");

//        yamlParam.setConfigPath(configPath);
//        yamlParam.setOutYamlPath(outYamlPath);
        Map<String, Object> paramMap = new HashMap<>();
        try {
            DataBaseParam dataBaseParam = JsonUtil.fromJson(systemConfExec.getDataBaseJson(), DataBaseParam.class);
            YamlParam yamlParam = JsonUtil.fromJson(systemConfExec.getSystemServerJson(), YamlParam.class);

            if ("app".equals(yamlType)) {
                paramMap.put(InitConfConstant.MYSQL_RUL, String.format("jdbc:mysql://%s/%s:%s%s", dataBaseParam.getDbIp(), dataBaseParam.getDbName(), dataBaseParam.getPort(), InitConfConstant.MYSQL_PARAMS));
                Object mysqlPassword = StringUtils.isNumeric(dataBaseParam.getPassword()) ? Integer.parseInt(dataBaseParam.getPassword()) : dataBaseParam.getPassword();
                paramMap.put(InitConfConstant.MYSQL_PASSWORD, mysqlPassword);
                paramMap.put(InitConfConstant.MYSQL_USERNAME, dataBaseParam.getUserName());
                paramMap.put(InitConfConstant.RABBIT_MQ_TOPIC_DESTINATION_1,InitConfConstant.STREAM_TOPIC_EXCHANGE_PRE+systemNo);
                paramMap.put(InitConfConstant.RABBIT_MQ_TOPIC_DESTINATION_2,InitConfConstant.STREAM_TOPIC_EXCHANGE_PRE+systemNo);
                paramMap.put(InitConfConstant.RABBIT_MQ_DELAYED_DESTINATION_1,InitConfConstant.STREAM_DELAYED_EXCHANGE_PRE+systemNo);
                paramMap.put(InitConfConstant.RABBIT_MQ_DELAYED_DESTINATION_2,InitConfConstant.STREAM_DELAYED_EXCHANGE_PRE+systemNo);
                String snowyConfigUrl = yamlParam.getSystemDomain();
                if(StrUtil.isEmpty(snowyConfigUrl)){
                    if(StrUtil.isAllNotEmpty(yamlParam.getSystemIp(),yamlParam.getSystemPort())){
                        snowyConfigUrl = String.format("http://%s:%s",yamlParam.getSystemIp(),yamlParam.getSystemPort());
                    }else {
                        throw new CommonException("端口或ip为空");
                    }
                }
                paramMap.put(InitConfConstant.SNOWY_CONFIG_COMMON_FRONT_RUL,snowyConfigUrl);
                paramMap.put(InitConfConstant.SNOWY_CONFIG_COMMON_BACKEND_RUL,snowyConfigUrl);
            } else if ("docker".equals(yamlType)) {
                String dockerComposePort = yamlParam.getSystemDockerPort();
                //- "${SYSTEM_PORT}:80"
                paramMap.put(InitConfConstant.DOCKER_PORT,"- \"" + dockerComposePort + ":80\"");
                String dockerName = "epc-" + systemNo;
                paramMap.put(InitConfConstant.DOCKER_CONTAINER_NAME, dockerName);
            } else {
                log.error("Unsupported yamlType: " + yamlType);
                return false;
            }
            yamlFileUtil.updateYamlFile(configPath,outYamlPath,paramMap);

            systemConfExec.setSystemId(yamlParam.getSystemId());
            systemConfExecService.saveOrUpdate(systemConfExec);

        } catch (Exception e) {
            log.error("创建yaml文件失败，e: {}", e.getMessage(), e);
   //         throw new CommonException("创建yaml文件失败,e: " + e.getMessage());
            return false;
        }
        return true;
    }

    private String getConfigPath(String key, String defaultValue) {
        String configPath = devConfigService.getValueByKey(key);
        return StrUtil.isEmpty(configPath) ? defaultValue : configPath;
    }

    private String getOutYamlPath(String key, String defaultValue) {
        String outYamlPath = devConfigService.getValueByKey(key);
        return StrUtil.isEmpty(outYamlPath) ? defaultValue : outYamlPath;
    }

    private String getConfigPathKey(String yamlType) {
        return "YAML_" + yamlType.toUpperCase() + "_CONFIG_PATH";
    }

    private String getOutYamlPathKey(String yamlType) {
        return "YAML_" + yamlType.toUpperCase() + "_OUT_CONFIG_PATH";
    }

    /**
     * 配置文件迁移前置工作：传送到目标服务器
     * */
    @Override
    public void singleConf(SingleConfParam singleConfParam){
//        if(StrUtil.isEmpty(singleConfParam.getSystemId())){
//            log.error("系统服务ID不能为空");
//            throw new CommonException("系统服务systemId不能为空。");
//        }
         List<SystemConfExec> systemConfExecs = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,singleConfParam.getSystemId()));
        if(CollectionUtil.isEmpty(systemConfExecs) || StringUtils.isEmpty(systemConfExecs.get(0).getDataBaseJson())){
            throw new CommonException("未找到系统服务操作记录！");
        }
        SystemConfExec systemConfExec = systemConfExecs.get(0);
//        TransferFileParam transferFile = JsonUtil.fromJson(systemConfExec.getTransferSqlJson(),TransferFileParam.class);
        //BeanUtil.copyProperties(transferFile,singleConfParam);
//        singleConfParam.setSourceUsername(transferFile.getSourceUsername());
//        singleConfParam.setSourcePassword(transferFile.getSourcePassword());
        String systemSourceIp = devConfigService.list(new LambdaQueryWrapper<DevConfig>().eq(DevConfig::getConfigKey,InitConfConstant.SYSTEM_SOURCE_IP)).get(0).getConfigValue();
        singleConfParam.setSourceHost(systemSourceIp);

        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        String singleTargetConfigPath = devConfigService.list(new LambdaQueryWrapper<DevConfig>().eq(DevConfig::getConfigKey,InitConfConstant.SINGLE_TARGET_CONFIG_PATH)).get(0).getConfigValue()+"/"+currentTimeMillis+"/";

        String singleConfigPath = devConfigService.list(new LambdaQueryWrapper<DevConfig>().eq(DevConfig::getConfigKey,InitConfConstant.SINGLE_SOURCE_CONFIG_PATH)).get(0).getConfigValue();
        singleConfParam.setConfigPath(singleConfigPath);
        File file = new File(singleConfParam.getConfigPath());
        List<Map<String,String>> fileList = new ArrayList<>();
        getFileDataList(file,fileList);//获取全部文件
        File file1 = new File("/home/epc/source/sh/conf/application.yaml");
        log.info("singleConfigPath:SINGLE_SOURCE_CONFIG_PATH:"+singleConfigPath+",fileList.size():"+fileList.size()+",file.isEx:"+file.exists()+",file1.isEx:"+file1.exists());
        fileList.forEach(map -> {
            TransferFileParam transferFileParam = BeanUtil.toBean(singleConfParam,TransferFileParam.class);//singleConfParam.getTransferLocalFileParam();

            transferFileParam.setSourceFilePath(map.get("path"));
            String filePath = map.get("path");
            transferFileParam.setTargetFilePath(singleTargetConfigPath);
            if(filePath.contains(CONF)){
                transferFileParam.setTargetFilePath(singleTargetConfigPath+"conf/");
            }
            transferFileParam.setFileName(map.get("name"));
            log.info("SINGLE_SOURCE_CONFIG_PATH,singleConf,配置文件路径,path："+map.get("path")+",name:"+map.get("name"));
            try{
                yamlUtil.transferFile(transferFileParam);
            }catch (Exception e){
                log.error("上传文件失败！e:"+e.getMessage());
                throw new CommonException("上传文件失败！");
            }
        });
        String remoteYamlJson = JSONUtil.toJsonStr(singleConfParam);
//        systemConfExec.setRemoteYamlJson(remoteYamlJson);
        systemConfExec.setSystemId(singleConfParam.getSystemId());
        systemConfExecService.saveOrUpdate(systemConfExec);
    }

   @Override
    public CompletableFuture<Boolean> transferSqlFile(TransferFileParam transferFileParam) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String systemSourceIp = devConfigService.getValueByKey(InitConfConstant.SYSTEM_SOURCE_IP);
                if (StrUtil.isNotEmpty(systemSourceIp)) {
                    HttpServletRequest request = CommonServletUtil.getRequest();
                    systemSourceIp = CommonIpAddressUtil.getIp(request);
                }
                transferFileParam.setSourceHost(systemSourceIp);

                String sourceFilePath = devConfigService.getValueByKey(InitConfConstant.DUMP_SQL_SOURCE_PATH);
                if (StrUtil.isEmpty(sourceFilePath)) {
                    sourceFilePath = "/home/sql/epc-single-server.sql";
                }
                transferFileParam.setSourceFilePath(sourceFilePath);

                String targetFilePath = devConfigService.getValueByKey(InitConfConstant.DUMP_SQL_TARGET_PATH);
                if (StrUtil.isEmpty(targetFilePath)) {
                    targetFilePath = "/home/sql/";
                }
                transferFileParam.setTargetFilePath(targetFilePath);

                File file = new File(transferFileParam.getSourceFilePath());

                transferFileParam.setFileName(file.getName());

                yamlUtil.transferFile(transferFileParam);

                // 保存操作记录
                transferFileParam.setTargetFilePath(transferFileParam.getTargetFilePath() + "/" + file.getName());
                String transferSqlJson = JSONUtil.toJsonStr(transferFileParam);

                List<SystemConfExec> confExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId, transferFileParam.getSystemId()));
                SystemConfExec systemConfExec = CollectionUtil.isNotEmpty(confExecList) ? confExecList.get(0) : new SystemConfExec();
//                systemConfExec.setTransferSqlJson(transferSqlJson);
                systemConfExec.setSystemId(transferFileParam.getSystemId());
                systemConfExecService.saveOrUpdate(systemConfExec);

                updateSystemServer(systemConfExec.getSystemId(), 2);

                return true;
            } catch (Exception e) {
                log.error("数据库脚本文件上传失败" + e);
                return false;
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
        if(ObjectUtil.isEmpty(unixCommandParam.getSystemId())){
            log.error("系统服务ID不能为空");
            throw new CommonException("系统服务systemId不能为空。");
        }
         List<SystemConfExec> systemConfExecs = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,unixCommandParam.getSystemId()));
        if(CollectionUtil.isEmpty(systemConfExecs) || StringUtils.isEmpty(systemConfExecs.get(0).getDataBaseJson())){
            throw new CommonException("未找到系统服务操作记录！");
        }
        SystemConfExec systemConfExec = systemConfExecs.get(0);
//        TransferFileParam transferFileParam = BeanUtil.toBean(systemConfExec.getTransferSqlJson(),TransferFileParam.class);
//        unixCommandParam.setSshHost(transferFileParam.getTargetHost());
//        unixCommandParam.setSshUsername(transferFileParam.getTargetUsername());
//        unixCommandParam.setSshPassword(transferFileParam.getTargetPassword());
//        unixCommandParam.setSshPort(22);

        try{
            databaseCreator.importDatabase(unixCommandParam);
            String remoteExecJson = JSONUtil.toJsonStr(unixCommandParam);
            if(ObjectUtil.isEmpty(unixCommandParam.getSystemId())){
                log.error("系统服务ID不能为空");
                throw new CommonException("系统服务systemId不能为空。");
            }
//            systemConfExec.setRemoteExecJson(remoteExecJson);
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
        importUserParam.setUsername(dataBaseParam.getUserName());
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

//            systemConfExec.setInitUserJson(remoteExecJson);
            systemConfExec.setSystemId(importUserParam.getSystemId());
            systemConfExecService.saveOrUpdate(systemConfExec);
            updateSystemServer(systemConfExec.getSystemId(),4);
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
    public Boolean connectDataBase(ConnectDataBaseParam connectDataBaseParam){
       return databaseCreator.connectDataBase(connectDataBaseParam);
    }

    /**
     * 更新保存SystemServer
     * */
    private void updateSystemServer(String systemId,Integer systemStatus){
        SystemServer systemServer = systemServerService.getById(systemId);
        systemServer.setSystemStatus(systemStatus);
        systemServerService.updateById(systemServer);
    }

    /**
     * 获取系统的编号
     * @return
     */
    public int getSystemNo(){
        Object o = commonCacheOperator.get(InitConfConstant.SYSTEM_NO_KEY);
        if(ObjectUtil.isNotEmpty(o)){
            return Integer.parseInt(String.valueOf(commonCacheOperator.get(InitConfConstant.SYSTEM_NO_KEY)));
        }
        return 1000;
    }

    /**
     * 自动生成系统编号
     * @return
     */
    public void generateNo(){
        Object o = commonCacheOperator.get(InitConfConstant.SYSTEM_NO_KEY);
        if(ObjectUtil.isNotEmpty(o)){
            commonCacheOperator.put(InitConfConstant.SYSTEM_NO_KEY,Integer.parseInt(String.valueOf(commonCacheOperator.get(InitConfConstant.SYSTEM_NO_KEY)))+1);
        }else {
            commonCacheOperator.put(InitConfConstant.SYSTEM_NO_KEY,1000);
        }
    }

    @Override
    public CompletableFuture<Boolean> uploadConfAsync(SftpFileUploader sftpFileUploader,String systemId,Integer status) {
        return CompletableFuture.supplyAsync(() -> {
//            SftpFileUploader sftpFileUploader = JsonUtil.fromJson(systemConfExec.getSystemServerJson(), SftpFileUploader.class);
            sftpFileUploader.setTargetRemoteFolderPath(sftpFileUploader.getTargetRemoteFolderPath() + StrUtil.SLASH + "epc" + getSystemNo());
            String systemSourceIp = devConfigService.getValueByKey(InitConfConstant.SYSTEM_SOURCE_IP);
            if (StrUtil.isEmpty(systemSourceIp)) {
                systemSourceIp = "10.0.33.82";
            }
            sftpFileUploader.setSourceHost(systemSourceIp);
            String sourceServerName = devConfigService.getValueByKey(InitConfConstant.SOURCE_SERVER_NAME);
            if (StrUtil.isEmpty(sourceServerName)) {
                sourceServerName = "root";
            }
            sftpFileUploader.setSourceUserName(sourceServerName);
            String sourceServerPs = devConfigService.getValueByKey(InitConfConstant.SOURCE_SERVER_PS);
            if (StrUtil.isEmpty(sourceServerPs)) {
                sourceServerPs = "Jianke66";
            }
            sftpFileUploader.setSourcePassword(sourceServerPs);

            String sourceServerConfPath = devConfigService.getValueByKey(InitConfConstant.SINGLE_SOURCE_CONFIG_PATH);
            if (StrUtil.isEmpty(sourceServerConfPath)) {
                sourceServerConfPath = "/home/source/conf";
            }
            sftpFileUploader.setSourceRemoteFolderPath(sourceServerConfPath);

            try {
                sftpFileUploader.uploadFolderToRemote();
                updateSystemServer(systemId, status);
                return true;
            } catch (Exception e) {
                return false;
            }
        }).exceptionally(e -> {
            log.error("uploadConfAsync failed", e);
            return false;
        });
    }


    @Override
    public CompletableFuture<Boolean> updateConfAsync(SystemConfExec systemConfExec, SftpFileUploader sftpFileUploader, Integer status) {
        return CompletableFuture.supplyAsync(() -> {
            String systemServerJson = systemConfExec.getSystemServerJson();
            // SftpFileUploader sftpFileUploader = JsonUtil.fromJson(systemServerJson, SftpFileUploader.class);
            String systemDomain = (String) JSONUtil.parseObj(systemServerJson).get("systemDomain");
            String dockerPort = (String) JSONUtil.parseObj(systemServerJson).get("dockerPort");
            String sourceFile = CONF + StrUtil.SLASH + InitConfConstant.APPLICATION_YML_NAME;
            int systemNo = systemConfExec.getSystemServerNo();
            Map<String, Object> paramMap = new HashMap<>();
            try {
                DataBaseParam dataBaseParam = JsonUtil.fromJson(systemConfExec.getDataBaseJson(), DataBaseParam.class);
                paramMap.put(InitConfConstant.MYSQL_RUL, String.format("jdbc:mysql://%s/%s:%s%s", dataBaseParam.getDbIp(), dataBaseParam.getDbName(), dataBaseParam.getPort(), InitConfConstant.MYSQL_PARAMS));
                Object mysqlPassword = StringUtils.isNumeric(dataBaseParam.getPassword()) ? Integer.parseInt(dataBaseParam.getPassword()) : dataBaseParam.getPassword();
                paramMap.put(InitConfConstant.MYSQL_PASSWORD, mysqlPassword);
                paramMap.put(InitConfConstant.MYSQL_USERNAME, dataBaseParam.getUserName());
                paramMap.put(InitConfConstant.RABBIT_MQ_TOPIC_DESTINATION_1, InitConfConstant.STREAM_TOPIC_EXCHANGE_PRE + systemNo);
                paramMap.put(InitConfConstant.RABBIT_MQ_TOPIC_DESTINATION_2, InitConfConstant.STREAM_TOPIC_EXCHANGE_PRE + systemNo);
                paramMap.put(InitConfConstant.RABBIT_MQ_DELAYED_DESTINATION_1, InitConfConstant.STREAM_DELAYED_EXCHANGE_PRE + systemNo);
                paramMap.put(InitConfConstant.RABBIT_MQ_DELAYED_DESTINATION_2, InitConfConstant.STREAM_DELAYED_EXCHANGE_PRE + systemNo);
                if (StrUtil.isEmpty(systemDomain)) {
                    if (StrUtil.isAllNotEmpty(sftpFileUploader.getTargetHost(), dockerPort)) {
                        systemDomain = String.format("http://%s:%s", sftpFileUploader.getTargetHost(), dockerPort);
                    } else {
                        throw new CommonException("端口或ip为空");
                    }
                }
                paramMap.put(InitConfConstant.SNOWY_CONFIG_COMMON_FRONT_RUL, systemDomain);
                paramMap.put(InitConfConstant.SNOWY_CONFIG_COMMON_BACKEND_RUL, systemDomain);
                sftpFileUploader.updateRemoteFile(sourceFile, paramMap);

                paramMap.clear();
                // String dockerComposePort = yamlParam.getSystemDockerPort();
                // - "${SYSTEM_PORT}:80"
                paramMap.put(InitConfConstant.DOCKER_PORT, "     - " + dockerPort + ":80");
                String dockerName = "epc-" + systemNo;
                paramMap.put(InitConfConstant.DOCKER_CONTAINER_NAME, dockerName);
                sourceFile = InitConfConstant.DOCKER_YML_NAME;
                sftpFileUploader.updateRemoteFile(sourceFile, paramMap);
                updateSystemServer(systemConfExec.getSystemId(), status);
                return true;
            } catch (Exception e) {
                log.error("修改配置文件错误", e);
                return false;
            }
        }).exceptionally(e -> {
            log.error("updateConfAsync failed", e);
            return false;
        });
    }

    @Override
    public CompletionStage<Boolean> executeRemoteScriptAsync(SftpFileUploader sftpFileUploader, String systemId, Integer status) {
        return CompletableFuture.supplyAsync(() -> {
            String script = "sh " +sftpFileUploader.getTargetRemoteFolderPath() + StrUtil.SLASH + InitConfConstant.BUILD_SH;
            try {
                boolean b = sftpFileUploader.executeRemoteScript(script);
                if(b){
                    updateSystemServer(systemId, status);
                }
            }catch (Exception e){
                log.error("系统初始化失败", e);
                return false;
            }
            return true;
        }).exceptionally(e -> {
            log.error("executeRemoteScriptAsync failed", e);
            return false;
        });
    }

}
