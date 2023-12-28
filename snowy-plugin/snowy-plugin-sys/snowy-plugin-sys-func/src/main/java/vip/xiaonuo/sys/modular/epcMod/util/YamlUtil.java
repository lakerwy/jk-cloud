package vip.xiaonuo.sys.modular.epcMod.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Slf4j
public class YamlUtil {

    public  void yamlData(String configPath,String outYamlPath,Map<String,String> paramMap){
        try {
            //读取文本文件的内容
            byte[] bytes = Files.readAllBytes(Paths.get(configPath));
            String externalConfig = new String(bytes, StandardCharsets.UTF_8);

            Yaml yaml = new Yaml();//格式转换
            Map<String, Object> data = yaml.load(externalConfig);
            data.entrySet().forEach(map -> {
                LinkedHashMap<Object, Object> linkMap = new LinkedHashMap<>();
                map.getValue();
                map.getKey();
            });
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);
            options.setIndent(4);
            options.setIndicatorIndent(0);

            Path tempYamlPath = Paths.get(outYamlPath);
            if (!Files.exists(tempYamlPath.getParent())) {
                Files.createDirectories(tempYamlPath.getParent());
            }
            if (!Files.exists(tempYamlPath)) { Files.createFile(tempYamlPath);}
            Yaml yamlNew = new Yaml(new Representer(), options);//Representer：创建自定义Representer以保留特殊对象的标签
            writeYamlFile(outYamlPath, yamlNew.dump(data));//文件写入，将数据写入格式化的YAML文件
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
}
