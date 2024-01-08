package vip.xiaonuo.dev.modular.server.util;

import cn.hutool.core.util.ObjectUtil;
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.modular.server.param.TransferFileParam;
import vip.xiaonuo.dev.modular.server.param.TransferLocalFileParam;
import vip.xiaonuo.dev.modular.server.param.YamlParam;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
@Slf4j
public class YamlUtil {

    public void yamlData(YamlParam yamlParam){
        try {
            //读取文本文件的内容
            byte[] bytes = Files.readAllBytes(Paths.get(yamlParam.getConfigPath()));
            String externalConfig = new String(bytes, StandardCharsets.UTF_8);

            Yaml yaml = new Yaml();//格式转换
            Map<String, Object> data = yaml.load(externalConfig);
            yamlParam.getParamMap().entrySet().forEach(param -> {
                //updateValueMap(data,param.getKey(),param.getValue());
                //List<String> keys = Arrays.asList(param.getKey().split(":"));
                //updateValueMap(data, keys.get(0), keys.get(1), param.getValue());
                Object newValue = param.getValue();
                if (param.getValue().getClass() == String.class) {
                    newValue =param.getValue().toString().trim();
                }
                findMapValue(data,param.getKey().trim(),newValue);
            });
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);
            options.setIndent(4);
            options.setIndicatorIndent(0);
            options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN); // 设置字符串默认样式为普通样式，即不加任何引号

            Path tempYamlPath = Paths.get(yamlParam.getOutYamlPath());
            if (!Files.exists(tempYamlPath.getParent())) {
                Files.createDirectories(tempYamlPath.getParent());
            }
            if (!Files.exists(tempYamlPath)) { Files.createFile(tempYamlPath);}
            Yaml yamlNew = new Yaml(new Representer(), options);//Representer：创建自定义Representer以保留特殊对象的标签
            writeYamlFile(yamlParam.getOutYamlPath(), yamlNew.dump(data));//文件写入，将数据写入格式化的YAML文件
        } catch (IOException e) {
            log.error(("创建yaml文件失败" + e.getMessage()));
        }
    }

    public static void writeYamlFile(String filePath, String content) throws IOException {
        File file = new File(filePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
        writer.close();
    }

    //读取resources目录下面的资源文件
    private static File getResourceFile(String filePath) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(filePath);

        if (resource != null) {
            return new File(resource.getFile());
        } else {
            throw new CommonException("未找到该文件！" + filePath);
        }
    }

    /**
     * map递归更新配置文件的参数值
     * */
    public static boolean updateValueMap(Map<String, Object> map, String firstKey, String secondKey, Object newValue) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String currentKey = entry.getKey();
            Object value = entry.getValue();

            if (currentKey.equals(firstKey)) {
                if (value instanceof Map) {//如果当前key与第一个key匹配，检查值是否Map类型
                    // 如果是Map类型，递归查找第二个key并更新值
                    return updateValueInNestedMap((Map<String, Object>) value, secondKey, newValue);
                } else {
                    return false;//如果不是Map类型，没有找到第二个key，返回false
                }
            }
        }
        return false;
    }

    private static boolean updateValueInNestedMap(Map<String, Object> nestedMap, String key, Object newValue) {
        for (Map.Entry<String, Object> entry : nestedMap.entrySet()) {
            String currentKey = entry.getKey();
            Object value = entry.getValue();

            if (currentKey.equals(key)) {
                nestedMap.put(key, newValue);//找到第二个key，更新值
                return true;
            } else if (value instanceof Map) {// 判断是否为Map类型
                boolean result = updateValueInNestedMap((Map<String, Object>) value, key, newValue);
                if (result) {
                    return true;//Map中找到并更新了目标值，返回true
                }
            }
        }
        return false;//未找到第二个key，返回false
    }

    /** A服务器传输文件到服务器B */
    public void transferFile(TransferFileParam transferFileParam) {
        JSch jsch = new JSch();
        Session sourceSession = null;
        ChannelSftp sourceSftpChannel = null;
        Session targetSession = null;
        ChannelSftp targetSftpChannel = null;

        try {
            // 连接到源服务器
            sourceSession = jsch.getSession(transferFileParam.getSourceUsername(), transferFileParam.getSourceHost(), 22);
            sourceSession.setPassword(transferFileParam.getSourcePassword());
            sourceSession.setConfig("StrictHostKeyChecking", "no");
            sourceSession.connect();

            sourceSftpChannel = (ChannelSftp) sourceSession.openChannel("sftp");
            sourceSftpChannel.connect();
            sourceSftpChannel.get(transferFileParam.getSourceFilePath(), transferFileParam.getFileName());

            // 创建目标服务器路径（如果不存在）
            createDirectoryIfNotExists(targetSession, targetSftpChannel, transferFileParam.getTargetFilePath(),transferFileParam);

            // 连接到目标服务器
            targetSession = jsch.getSession(transferFileParam.getTargetUsername(), transferFileParam.getTargetHost(), 22);
            targetSession.setPassword(transferFileParam.getTargetPassword());
            targetSession.setConfig("StrictHostKeyChecking", "no");
            targetSession.connect();

            targetSftpChannel = (ChannelSftp) targetSession.openChannel("sftp");
            targetSftpChannel.connect();

            targetSftpChannel.put(transferFileParam.getFileName(), transferFileParam.getTargetFilePath());

        } catch (Exception e) {
            log.error("文件传输过程中发生错误: {}", e.getMessage());
            e.printStackTrace();
            throw new CommonException("文件传输过程中发生错误");
        } finally {
            if (sourceSftpChannel != null && sourceSftpChannel.isConnected()) {
                sourceSftpChannel.disconnect();
            }
            if (sourceSession != null && sourceSession.isConnected()) {
                sourceSession.disconnect();
            }
            if (targetSftpChannel != null && targetSftpChannel.isConnected()) {
                targetSftpChannel.disconnect();
            }
            if (targetSession != null && targetSession.isConnected()) {
                targetSession.disconnect();
            }
        }
        log.info("文件传输完成。");
    }

    private void createDirectoryIfNotExists(Session session, ChannelSftp sftpChannel, String dirPath,TransferFileParam transferFileParam) throws Exception {
        if (session == null || sftpChannel == null) {
            // 如果session或sftpChannel未初始化，则先连接和打开通道
            session = initSession(transferFileParam.getTargetUsername(), transferFileParam.getTargetHost(), transferFileParam.getTargetPassword());
            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
        }

        // 路径拆分并递归创建目录
        String[] dirs = dirPath.split("/");
        StringBuilder currentDir = new StringBuilder();
        for (String dir : dirs) {
            if (!dir.isEmpty()) {
                currentDir.append("/").append(dir);
                try {
                    sftpChannel.stat(currentDir.toString()); // 检查目录是否存在
                } catch (SftpException e) {
                    if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) { // 如果目录不存在
                        sftpChannel.mkdir(currentDir.toString()); // 创建目录
                    } else {
                        log.error("创建目录失败！");
                        throw e; // 其他异常抛出
                    }
                }
            }
        }
    }
    private Session initSession(String username, String host, String password) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, 22);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
    }

    public static void findMapValue(Map<String, Object> map, Object oldValue, Object newValue) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(ObjectUtil.isEmpty(value)){
                continue;
            }
            if (value.equals(oldValue)) {
                map.put(key, newValue);// 如果找到目标值，则直接替换
            } else if (value instanceof Map) {
                // 如果value是另一个Map，递归调用以处理嵌套Map
                findMapValue((Map<String, Object>) value, oldValue, newValue);
            }
        }
    }

}
