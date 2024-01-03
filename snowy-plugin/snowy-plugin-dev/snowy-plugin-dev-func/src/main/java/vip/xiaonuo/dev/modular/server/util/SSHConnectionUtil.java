package vip.xiaonuo.dev.modular.server.util;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SSHConnectionUtil {

    private static final String USER = "your_username";
    private static final String SERVER_HOST = "your_server_ip_or_hostname";
    private static final int SERVER_PORT = 22; // 默认的SSH端口

    public void connectAndExecuteCommand(String host, int port, String user, String privateKeyPath) throws JSchException, IOException {
        JSch jsch = new JSch();

        // 加载私钥，并设置私钥密码为空（如果有的话）
        jsch.addIdentity(privateKeyPath, "");

        Session session = jsch.getSession(user, host, port);

        // 设置一些配置属性，比如信任主机等
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        // 建立连接
        session.connect();

        // 执行命令
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand("ls -l");

        InputStream in = channel.getInputStream();
        ((ChannelExec) channel).setErrStream(System.err);

        channel.connect();

        byte[] buffer = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int readBytes = in.read(buffer, 0, buffer.length);
                if (readBytes < 0) {
                    break;
                }
                System.out.print(new String(buffer, 0, readBytes));
            }
            if (channel.isClosed()) {
                if (in.available() > 0) continue;
                System.out.println("exit-status: " + channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        // 关闭通道和会话
        channel.disconnect();
        session.disconnect();
    }

    /*public static void main(String[] args) {
        String host = "10.0.33.82";
        String username = "root";
        String privateKeyPath = "/.ssh/id_rsa"; // 你的私钥文件路径

        try {
            JSch jsch = new JSch();

            // 添加私钥认证
            jsch.addIdentity(privateKeyPath);
            // 创建 SSH 会话
            Session session = jsch.getSession(username, host, 22);
            // 禁用密码认证
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();//连接到远程服务器

            // 创建SFTP通道
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // 指定远程路径和文件名
            String remotePath = "/home/sql/";
            String fileName = "example.txt";

            // 创建远程目录（如果不存在）
            try {
                channelSftp.cd(remotePath);
            } catch (SftpException e) {
                channelSftp.mkdir(remotePath);
                channelSftp.cd(remotePath);
            }

            // 创建文件
            SftpATTRS attrs = null;
            try {
                attrs = channelSftp.stat(fileName);
            } catch (Exception e) {
                // 文件不存在，创建文件
                channelSftp.put(new java.io.ByteArrayInputStream("Hello, World!".getBytes()), fileName);
            }

            // 关闭SFTP通道和SSH会话
            channelSftp.disconnect();
            session.disconnect();

            System.out.println("文件已成功创建在远程服务器上。");
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }*/

    public static void main(String[] args) {
        String param = "YadmDDGFasd";
        System.out.println(param.toLowerCase());
    }
}