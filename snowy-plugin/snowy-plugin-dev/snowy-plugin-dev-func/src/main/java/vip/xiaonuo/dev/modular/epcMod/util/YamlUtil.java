package vip.xiaonuo.dev.modular.epcMod.util;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.modular.epcMod.param.TransferFileParam;
import vip.xiaonuo.dev.modular.epcMod.param.YamlParam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
                List<String> keys = Arrays.asList(param.getKey().split(":"));
                updateValueMap(data, keys.get(0), keys.get(1), param.getValue());
            });
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);
            options.setIndent(4);
            options.setIndicatorIndent(0);

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

    public void transferFile(TransferFileParam transferFileParam) {
        try {
            JSch jsch = new JSch();
            Session sourceSession = jsch.getSession(transferFileParam.getSourceUsername(), transferFileParam.getSourceHost(), 22);//连接到源服务器
            sourceSession.setPassword(transferFileParam.getSourcePassword());
            sourceSession.setConfig("StrictHostKeyChecking", "no");//不信任的服务器也连接
            sourceSession.connect();

            ChannelSftp sourceSftpChannel = (ChannelSftp) sourceSession.openChannel("sftp");
            sourceSftpChannel.connect();
            sourceSftpChannel.get(transferFileParam.getSourceFilePath(), transferFileParam.getFileName());//从源服务器下载文件到本地临时文件存储，并命名为getFileName
            sourceSftpChannel.disconnect();
            sourceSession.disconnect();

            // 连接到目标服务器
            Session targetSession = jsch.getSession(transferFileParam.getTargetUsername(), transferFileParam.getTargetHost(), 22);
            targetSession.setPassword(transferFileParam.getTargetPassword());
            targetSession.setConfig("StrictHostKeyChecking", "no");
            targetSession.connect();

            ChannelSftp targetSftpChannel = (ChannelSftp) targetSession.openChannel("sftp");
            targetSftpChannel.connect();

            targetSftpChannel.put(transferFileParam.getFileName(), transferFileParam.getTargetFilePath());//上传本地临时文件到目标服务器，文件命名：getFileName
            targetSftpChannel.disconnect();
            targetSession.disconnect();

            log.info("文件传输完成。");
        } catch (Exception e) {
            log.error("文件传输过程中发生错误: " + e.getMessage());
            e.printStackTrace();
            throw new CommonException("文件传输过程中发生错误");
        }
    }

}
