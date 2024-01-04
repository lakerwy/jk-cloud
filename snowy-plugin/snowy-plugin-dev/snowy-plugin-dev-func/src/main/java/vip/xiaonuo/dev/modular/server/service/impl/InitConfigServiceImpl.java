package vip.xiaonuo.dev.modular.server.service.impl;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.common.util.CommonResponseUtil;
import vip.xiaonuo.dev.modular.server.entity.SystemConfExec;
import vip.xiaonuo.dev.modular.server.service.InitConfigService;
import vip.xiaonuo.dev.modular.server.service.SystemConfExecService;
import vip.xiaonuo.dev.modular.server.util.DatabaseCreator;
import vip.xiaonuo.dev.modular.server.util.YamlUtil;
import vip.xiaonuo.dev.modular.server.param.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InitConfigServiceImpl implements InitConfigService {

    @Resource
    private DatabaseCreator databaseCreator;
    @Resource
    private YamlUtil yamlUtil;
    @Resource
    SystemConfExecService systemConfExecService;
    @Override
    public void createDataBase(DataBaseParam dataBaseParam) throws Exception{
        databaseCreator.createDatabase(dataBaseParam);
        dataBaseParam.setPassword("");//密码不保存
        String datebaseJson = JSONUtil.toJsonStr(dataBaseParam);
        if(ObjectUtil.isEmpty(dataBaseParam.getSystemId())){
            log.error("系统服务ID不能为空");
            return;
        }
        List<SystemConfExec> confExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,dataBaseParam.getSystemId()));
        SystemConfExec systemConfExec = CollectionUtil.isNotEmpty(confExecList) ? confExecList.get(0) : new SystemConfExec();
        systemConfExec.setDataBaseJson(datebaseJson);
        systemConfExecService.saveOrUpdate(systemConfExec);
    }

    @Override
    public void createTable(TableParam tableParam){
        String command = String.format(" docker exec -i %s mysql -u%s -p%s  %s < %s ",tableParam.getDockerMyName(),tableParam.getDatabaseUsername(),
                tableParam.getDatabasePassword(),tableParam.getDatabaseName(),tableParam.getDumpFilePath());
        tableParam.setCommand(command);
        UnixCommandParam unixCommandParam = BeanUtil.toBean(tableParam,UnixCommandParam.class);
        databaseCreator.importDatabase(unixCommandParam);
        tableParam.setSshPassword("");
        tableParam.setDatabasePassword("");
        String tableJson = JSONUtil.toJsonStr(tableParam);
        if(ObjectUtil.isEmpty(tableParam.getSystemId())){
            log.error("系统服务ID不能为空");
            return;
        }
        List<SystemConfExec> confExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,tableParam.getSystemId()));
        SystemConfExec systemConfExec = CollectionUtil.isNotEmpty(confExecList) ? confExecList.get(0) : new SystemConfExec();
        systemConfExec.setTableJson(tableJson);
        systemConfExecService.saveOrUpdate(systemConfExec);
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
            String yamlJson = JSONUtil.toJsonStr(yamlParam);
            if(ObjectUtil.isEmpty(yamlParam.getSystemId())){
                log.error("系统服务ID不能为空");
                return;
            }
            List<SystemConfExec> confExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,yamlParam.getSystemId()));
            SystemConfExec systemConfExec = CollectionUtil.isNotEmpty(confExecList) ? confExecList.get(0) : new SystemConfExec();
            if(yamlParam.getConfigPath().contains("yaml")){
                systemConfExec.setApplYamlJson(yamlJson);
            }else if(yamlParam.getConfigPath().contains("yml")){
                systemConfExec.setDockYamlJson(yamlJson);
            }
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
        singleConfParam.setSourcePassword("");
        singleConfParam.setTargetPassword("");
        if(ObjectUtil.isEmpty(singleConfParam.getSystemId())){
            log.error("系统服务ID不能为空");
            return;
        }
        List<SystemConfExec> confExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,singleConfParam.getSystemId()));
        SystemConfExec systemConfExec = CollectionUtil.isNotEmpty(confExecList) ? confExecList.get(0) : new SystemConfExec();
        systemConfExec.setRemoteYamlJson(remoteYamlJson);
        systemConfExecService.saveOrUpdate(systemConfExec);
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
            String remoteExecJson = JSONUtil.toJsonStr(unixCommandParam);
            unixCommandParam.setSshPassword("");
            if(ObjectUtil.isEmpty(unixCommandParam.getSystemId())){
                log.error("系统服务ID不能为空");
                return;
            }
            List<SystemConfExec> confExecList = systemConfExecService.list(new LambdaQueryWrapper<SystemConfExec>().eq(SystemConfExec::getSystemId,unixCommandParam.getSystemId()));
            SystemConfExec systemConfExec = CollectionUtil.isNotEmpty(confExecList) ? confExecList.get(0) : new SystemConfExec();
            systemConfExec.setRemoteExecJson(remoteExecJson);
            systemConfExecService.saveOrUpdate(systemConfExec);
        }catch (Exception e){
            log.error("执行脚本失败！command:"+unixCommandParam.getCommand()+",host:"+unixCommandParam.getSshHost());
            throw new CommonException("执行command失败！e:"+e.getMessage());
        }
    }

    @Override
    public JSONObject importUser(ImportUserParam importUserParam) {
        try {
            // 创建临时文件
            File tempFile = FileUtil.writeBytes(importUserParam.getFile().getBytes(), FileUtil.file(FileUtil.getTmpDir() +
                    FileUtil.FILE_SEPARATOR + "serverUserTemplate.xlsx"));
            // 读取excel
            List<UserParam> sysUserImportParamList =  EasyExcel.read(tempFile).head(UserParam.class).sheet()
                    .headRowNumber(2).doReadSync();
            InitUserParam initUserParam = BeanUtil.toBean(importUserParam,InitUserParam.class);
            initUserParam.setUserParamList(sysUserImportParamList);
            initData(initUserParam);
            return JSONUtil.createObj().set("totalCount", sysUserImportParamList.size());
        } catch (Exception e) {
            log.error(">>> 用户导入失败：", e);
            throw new CommonException("用户导入失败");
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
}
