package vip.xiaonuo.dev.modular.server.util;

import cn.hutool.core.util.StrUtil;
import com.jcraft.jsch.*;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.modular.server.consts.InitConfConstant;
import vip.xiaonuo.dev.modular.server.param.UnixCommandParam;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/1/10 10:58
 */
@Setter
@Getter
@Slf4j
public class SftpFileUploader {
    private String sourceHost;  // Server A
    private String sourceUserName;
    private String sourcePassword;
    private String sourceRemoteFolderPath;  // Remote folder path on Server A

    private String targetHost;  // Server B
    private String targetUserName;
    private String targetPassword;
    private String targetRemoteFolderPath;  // Remote folder path on Server B
    private String dockerPort;

    private ReentrantLock lock ;

    // 初始化块
    {
        this.lock = new ReentrantLock();
    }
    public SftpFileUploader(String sourceHost, String sourceUserName, String sourcePassword, String sourceRemoteFolderPath){
        this.sourceHost = sourceHost;
        this.sourceUserName = sourceUserName;
        this.sourcePassword = sourcePassword;
        this.sourceRemoteFolderPath = sourceRemoteFolderPath;
    }
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

//    public SftpFileUploader() {
//    }

    public void setSourceHost(String sourceHost) {
        lockAndExecute(() -> this.sourceHost = sourceHost);
    }

    public void setSourceUserName(String sourceUserName) {
        lockAndExecute(() -> this.sourceUserName = sourceUserName);
    }

    public void setSourcePassword(String sourcePassword) {
        lockAndExecute(() -> this.sourcePassword = sourcePassword);
    }

    public void setSourceRemoteFolderPath(String sourceRemoteFolderPath) {
        lockAndExecute(() -> this.sourceRemoteFolderPath = sourceRemoteFolderPath);
    }

    public void setTargetHost(String targetHost) {
        lockAndExecute(() -> this.targetHost = targetHost);
    }

    public void setTargetUserName(String targetUserName) {
        lockAndExecute(() -> this.targetUserName = targetUserName);
    }

    public void setTargetPassword(String targetPassword) {
        lockAndExecute(() -> this.targetPassword = targetPassword);
    }

    public void setTargetRemoteFolderPath(String targetRemoteFolderPath) {
        lockAndExecute(() -> this.targetRemoteFolderPath = targetRemoteFolderPath);
    }

    private void lockAndExecute(Runnable task) {
        if(null == lock){
            this.lock = new ReentrantLock();
        }
        lock.lock();
        try {
            task.run();
        } finally {
            lock.unlock();
        }
    }

    public void uploadFolderToRemote() {
        JSch jsch = new JSch();
        Session sourceSession = null;
        Session targetSession = null;

        try {
            sourceSession = connectToServer(jsch, sourceHost, sourceUserName, sourcePassword);
            targetSession = connectToServer(jsch, targetHost, targetUserName, targetPassword);

            uploadFolder(sourceSession, targetSession, sourceRemoteFolderPath, targetRemoteFolderPath);
        } catch (JSchException | SftpException | IOException e) {
            e.printStackTrace();
        } finally {
            if (sourceSession != null && sourceSession.isConnected()) {
                sourceSession.disconnect();
            }
            if (targetSession != null && targetSession.isConnected()) {
                targetSession.disconnect();
            }
        }
    }

    public void execBulidRemote(String scriptPath,String path) {
        JSch jsch = new JSch();
        Session session = null;
        ChannelExec channelExec = null;
        try {
            session = connectToServer(jsch, sourceHost, sourceUserName, sourcePassword);

            ChannelSftp targetChannel = (ChannelSftp) session.openChannel("sftp");
            targetChannel.connect();
            targetChannel.chmod(755, path);

            channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(scriptPath);
            channelExec.connect();
            Integer exitStatus =  channelExec.getExitStatus();
            if(exitStatus != 0){
                log.error("更新EPC代码失败！exitStatus:"+exitStatus);
            }
        } catch (JSchException e) {
            e.printStackTrace();
            log.error("更新EPC代码失败！e:"+e.getMessage());
        } finally {
            if (channelExec != null && channelExec.isConnected()) {
                channelExec.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
            return;
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
                        // Upload files without additional character encoding conversion
                        try (InputStream inputStream = sourceChannel.get(entry.getFilename())) {
                            targetChannel.put(inputStream, targetFolderPath + "/" + entry.getFilename());
                        }
                        // Set file permissions (execute permission for owner, group, and others)
                        targetChannel.chmod(755, targetFolderPath + "/" + entry.getFilename());
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


    public void updateRemoteFile(String sourcePath, Map<String, Object> keyValues) {
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
            String remoteFilePath = targetRemoteFolderPath + StrUtil.SLASH + sourcePath;
            InputStream inputStream = channelSftp.get(remoteFilePath);

            // Read remote YAML content
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line);
                contentBuilder.append(System.lineSeparator());  // 使用 System.lineSeparator() 添加正确的行尾符
            }
            // Update YAML content
            String updatedYamlContent = applyYamlUpdates(contentBuilder.toString(), keyValues);
            // Check and adjust the 'ports' formatting

            //特殊处理，暂未好的方案
            if (keyValues.containsKey(InitConfConstant.DOCKER_PORT)) {
                String portsValue = keyValues.get(InitConfConstant.DOCKER_PORT).toString();
                //替换后会变成
                String portsValueTmp = "'"+portsValue+"'";

                // If the 'ports' format is not expected, adjust it
                if (updatedYamlContent.contains("ports:")) {
                    updatedYamlContent = updatedYamlContent.replace("ports:", "ports:\n" ).replace(portsValueTmp,portsValue);
                }

            }

            // Close outputStream before writing updated content
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Upload updated YAML content back to the remote server
            try (OutputStream outputStream = channelSftp.put(remoteFilePath, ChannelSftp.OVERWRITE)) {
                //set style  -- 保证正确的缩进格式
                outputStream.write(updatedYamlContent.getBytes(StandardCharsets.UTF_8));
            }

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
            keyValues.forEach((key, newValue) -> updateMapValue(yamlData, key.split("\\."), newValue));

            // Convert updated YAML data to string
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);  // 设置纯文本方式
            options.setPrettyFlow(true);
            options.setIndent(2);

            Yaml updatedYaml = new Yaml(options);
            return updatedYaml.dump(yamlData);
        } catch (Exception e) {
            e.printStackTrace();
            return originalContent;
        }
    }



    private void updateMapValue(Map<String, Object> map, String[] keys, Object newValue) {
        for (int i = 0; i < keys.length - 1; i++) {
            String currentKey = keys[i];
            if (map.containsKey(currentKey) && map.get(currentKey) instanceof Map) {
                map = (Map<String, Object>) map.get(currentKey);
            } else {
                throw new IllegalArgumentException("Key path does not match map structure");
            }
        }
        map.put(keys[keys.length - 1], newValue);
    }


    /**
     * 执行远程sh脚本
     * @param scriptPath
     */
    public boolean executeRemoteScript(String scriptPath) {
        JSch jsch = new JSch();
        Session session = null;
        ChannelExec channelExec = null;

        try {
            // Connect to the SSH server
            session = connectToServer(jsch, targetHost, targetUserName, targetPassword);

            // Open an exec channel
            channelExec = (ChannelExec) session.openChannel("exec");

            // Set the command to run the script
            channelExec.setCommand(scriptPath);
            System.out.println(scriptPath);

            // Connect to the channel
            channelExec.connect();

            //不能强行睡眠
            // Wait for the command to complete
            while (!channelExec.isClosed()) {
                // Add some delay or other logic here if needed
                // For example, Thread.sleep(100);
            }
            // Get the exit status
            int exitStatus = channelExec.getExitStatus();
            // Get the error stream
            //InputStream errorStream = channelExec.getErrStream();
            //String errorMessage = readStream(errorStream);
            // Print the error message
            //System.out.println("Error Message: " + errorMessage);
            // Process the result
            return (exitStatus == 0);

        } catch (JSchException e) {
            e.printStackTrace();
            return false; // Return false in case of exception
        } finally {
            // Disconnect from the server
            if (channelExec != null && channelExec.isConnected()) {
                channelExec.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    // Helper method to read the content of an InputStream
    private String readStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append("\n");
        }
        return result.toString();
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
