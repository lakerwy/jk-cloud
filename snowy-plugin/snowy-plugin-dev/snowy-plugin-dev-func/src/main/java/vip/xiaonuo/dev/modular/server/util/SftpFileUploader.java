package vip.xiaonuo.dev.modular.server.util;

import cn.hutool.core.util.StrUtil;
import com.jcraft.jsch.*;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Vector;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/1/10 10:58
 */
@Setter
@Getter
public class SftpFileUploader {
    private String sourceHost;  // Server A
    private String sourceUserName;
    private String sourcePassword;
    private String sourceRemoteFolderPath;  // Remote folder path on Server A

    private String targetHost;  // Server B
    private String targetUserName;
    private String targetPassword;
    private String targetRemoteFolderPath;  // Remote folder path on Server B

    public SftpFileUploader(String sourceHost, String sourceUserName, String sourcePassword, String sourceRemoteFolderPath,
                            String targetHost, String targetUserName, String targetPassword, String targetRemoteFolderPath) {
        this.sourceHost = sourceHost;
        this.sourceUserName = sourceUserName;
        this.sourcePassword = sourcePassword;
        this.sourceRemoteFolderPath = sourceRemoteFolderPath;

        this.targetHost = targetHost;
        this.targetUserName = targetUserName;
        this.targetPassword = targetPassword;
        this.targetRemoteFolderPath = targetRemoteFolderPath;
    }

    public SftpFileUploader() {
    }

    public void uploadFolderToRemote() {
        JSch jsch = new JSch();
        Session sourceSession = null;
        Session targetSession = null;

        try {
            // Connect to source server (Server A)
            sourceSession = connectToServer(jsch, sourceHost, sourceUserName, sourcePassword);

            // Connect to target server (Server B)
            targetSession = connectToServer(jsch, targetHost, targetUserName, targetPassword);

            // Upload folder and its contents from source server (Server A) to target server (Server B)
            uploadFolder(sourceSession, targetSession, sourceRemoteFolderPath, targetRemoteFolderPath);
        } catch (JSchException | SftpException | IOException e) {
            e.printStackTrace();
        } finally {
            // Disconnect from servers
            if (sourceSession != null && sourceSession.isConnected()) {
                sourceSession.disconnect();
            }
            if (targetSession != null && targetSession.isConnected()) {
                targetSession.disconnect();
            }
        }
    }

    private Session connectToServer(JSch jsch, String host, String username, String password) throws JSchException {
        Session session = jsch.getSession(username, host, 22);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
    }

    private void uploadFolder(Session sourceSession, Session targetSession, String sourceFolderPath, String targetFolderPath) throws JSchException, SftpException, IOException {
        ChannelSftp sourceChannel = (ChannelSftp) sourceSession.openChannel("sftp");
        sourceChannel.connect();

        ChannelSftp targetChannel = (ChannelSftp) targetSession.openChannel("sftp");
        targetChannel.connect();

        try {
            // Ensure that the target folder exists
            createRemoteFolder(targetChannel, targetFolderPath);

            // Change to source folder on source server (Server A)
            sourceChannel.cd(sourceFolderPath);

            // Get list of files and subdirectories in the source folder
            @SuppressWarnings("unchecked")
            Vector<ChannelSftp.LsEntry> entries = sourceChannel.ls(".");
            for (ChannelSftp.LsEntry entry : entries) {
                if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                    if (entry.getAttrs().isDir()) {
                        // Recursively upload subdirectories
                        uploadFolder(sourceSession, targetSession, sourceFolderPath + "/" + entry.getFilename(), targetFolderPath + "/" + entry.getFilename());
                    } else {
                        // Upload files
                        try (InputStream inputStream = sourceChannel.get(entry.getFilename())) {
                            targetChannel.put(inputStream, targetFolderPath + "/" + entry.getFilename());
                        }
                    }
                }
            }
        } finally {
            sourceChannel.disconnect();
            targetChannel.disconnect();
        }
    }

    private void createRemoteFolder(ChannelSftp channel, String folderPath) throws SftpException {
        // Create remote folder on target server (Server B) if it doesn't exist
        try {
            channel.mkdir(folderPath);
        } catch (SftpException e) {
            // Check if the directory already exists
            SftpATTRS attrs = null;
            try {
                attrs = channel.stat(folderPath);
            } catch (Exception ignored) {
                // Directory does not exist, create it
                channel.mkdir(folderPath);
                return;
            }

            // Directory exists, do nothing
            if (attrs != null && attrs.isDir()) {
                return;
            }

            // Re-throw the original exception if it's not a "directory already exists" exception
            throw e;
        }
    }

    public void downloadRemoteFile(String sourcePath,String fileName) {
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            // Connect to the remote server
            session = connectToServer(jsch, targetHost, targetUserName, targetPassword);
            // Open SFTP channel
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            String filePath = targetRemoteFolderPath+ StrUtil.SLASH+sourcePath;
            // Download remote file to local
            String localFilePath = "local-"+fileName;
            OutputStream outputStream = Files.newOutputStream(Paths.get(localFilePath));
            channelSftp.get(filePath, outputStream);

        } catch (JSchException | SftpException | IOException e) {
            e.printStackTrace();
        } finally {
            // Disconnect from the server
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }


    public void updateRemoteFile(String sourcePath,Map<String, Object> keyValues) {
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            // Connect to the SSH server
            session = connectToServer(jsch, targetHost, targetUserName, targetPassword);
            // Open SFTP channel
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            // Download remote file to local
            // String remoteFilePath = REMOTE_FILE_PATH;
            String remoteFilePath = targetRemoteFolderPath + StrUtil.SLASH + sourcePath;
            InputStream inputStream = channelSftp.get(remoteFilePath);


            // Read remote YAML content
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }

            // Update YAML content
            String updatedYamlContent = applyYamlUpdates(contentBuilder.toString(),keyValues);

            // Upload updated YAML content back to the remote server
            OutputStream outputStream = channelSftp.put(remoteFilePath);
            outputStream.write(updatedYamlContent.getBytes(StandardCharsets.UTF_8));

        } catch (JSchException | SftpException | IOException e) {
            e.printStackTrace();
        } finally {
            // Disconnect from the server
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }


    private String applyYamlUpdates(String originalContent, Map<String, Object> keyValues) {
        try {
            // Parse YAML content
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(originalContent);

            // Update YAML data
            keyValues.forEach((key, newValue) -> updateMapValue(yamlData, key.split(":"), newValue));

            // Convert updated YAML data to string
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);
            options.setIndent(2);

            Yaml updatedYaml = new Yaml(options);
            return updatedYaml.dump(yamlData);
        } catch (Exception e) {
            e.printStackTrace();
            return originalContent;
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

//    public static void main(String[] args) {
//        // Example usage
//        SftpFileUploader uploader = new SftpFileUploader(
//                "10.0.33.82", "root", "Jianke66", "/home/test",
//                "10.0.33.81", "root", "Jianke66", "/home/sql/test"
//        );
//        uploader.uploadFolderToRemote();
//    }
}
