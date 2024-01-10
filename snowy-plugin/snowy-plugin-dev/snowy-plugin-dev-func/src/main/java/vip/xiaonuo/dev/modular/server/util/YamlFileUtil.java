package vip.xiaonuo.dev.modular.server.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/1/9 11:46
 */
@Component
@Slf4j
public class YamlFileUtil {
    public void updateYamlFile(String originalFilePath, String newFilePath, Map<String, Object> keyValues) {
        try {
            // 读取原始YAML文件
            byte[] bytes = Files.readAllBytes(Paths.get(originalFilePath));
            String yamlContent = new String(bytes, StandardCharsets.UTF_8);

            // 解析YAML文件
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(yamlContent);

            // 更新YAML数据
            keyValues.forEach((key, newValue) -> updateMapValue(yamlData, key.split(":"), newValue));

            // 写入新的YAML文件
            writeYamlFile(newFilePath, yamlData);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static void updateMapValue(Map<String, Object> map, String[] keys, Object newValue) {
        for (int i = 0; i < keys.length - 1; i++) {
            String currentKey = keys[i];
            if (map.containsKey(currentKey) && map.get(currentKey) instanceof Map) {
                map = (Map<String, Object>) map.get(currentKey);
            } else {
                return; // 如果键路径不存在，直接返回
            }
        }
        map.put(keys[keys.length - 1], newValue);
    }

    private static void writeYamlFile(String filePath, Map<String, Object> yamlData) {
        try {
            // 创建DumperOptions，设置YAML文件输出格式选项
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);
            options.setIndent(2);

            // 创建YAML对象
            Yaml yaml = new Yaml(options);

            // 将YAML数据写入新的YAML文件
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(filePath)), StandardCharsets.UTF_8))) {
                yaml.dump(yamlData, writer);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
