package vip.xiaonuo.biz.modular.cmb.util;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 招商银行银企直联国密免前置/SaaS对接示例，本示例仅供参考，不保证各种异常场景运行，请勿直接使用，如有错漏请联系对接人员。运行时，请使用所获取的测试资源替换 用户编号、公私钥、对称密钥、服务商编号等信息。
 *
 * @author cmb.firmbank
 * @date 2023/7/20
 */
public class ApiDemo {

    private static final int BOUND_START = 1000000;
    private static final int BOUND_END = 9000000;
    // 测试地址，生产需要替换
    private static String url = "http://cdctest.cmburl.cn/cdcserver/api/v2";
    // 生产地址
    //private static String url = "https://cdc.cmbchina.com/cdcserver/api/v2";
    // 银行公钥，生产需要替换
    private static String publicKey = "BNsIe9U0x8IeSe4h/dxUzVEz9pie0hDSfMRINRXc7s1UIXfkExnYECF4QqJ2SnHxLv3z/99gsfDQrQ6dzN5lZj0=";
    // 生产环境银行公钥
    // private static String publicKey = "BEynMEZOjNpwZIiD9jXtZSGr3Ecpwn7r+m+wtafXHb6VIZTnugfuxhcKASq3hX+KX9JlHODDl9/RDKQv4XLOFak=";
    // 客户私钥，生产需要替换
    private static String privateKey = "NBtl7WnuUtA2v5FaebEkU0/Jj1IodLGT6lQqwkzmd2E=";
    // 对称密钥，生产需要替换
    private static String symKey = "VuAzSWQhsoNqzn0K";

    // 企业网银用户号，生产需要替换
    private static String uid = "U006557895";

    private static Random random = new Random();

    private ApiDemo() {
    }

    public static void main(String[] args) throws GeneralSecurityException, IOException, CryptoException {
        // 装载BC库,必须在应用的启动类中调用此函数
        Security.addProvider(new BouncyCastleProvider());
        System.setProperty("sun.net.http.retryPost", "false");

        // 业务接口名，这里是查询业务模式接口，生产请替换为对应接口名
        String funcode = "DCLISMOD";
        // 准备接口数据，生产请替换为具体接口请求报文,包含所需的请求字段
        String currentDatetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String reqid = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()) + (BOUND_START + random.nextInt(BOUND_END));
        String data = "{\"request\":{\"body\":{\"buscod\":\"N02030\"},\"head\":{\"funcode\":\"" + funcode + "\",\"userid\":\"" + uid + "\",\"reqid"
                + "\":\"" + reqid + "\"}},\"signature\":{\"sigdat\":\"__signature_sigdat__\",\"sigtim\":\"" + currentDatetime + "\"}}";
        data = "{\"signature\":{\"sigdat\":\"__signature_sigdat__\",\"sigtim\":\"20250206115613\"},\"request\":{\"head\":{\"funcode\":\"DCLISMOD\",\"userid\":\"U006557895\",\"reqid\":\"2025020611561307639248264\"},\"body\":{\"buscod\":\"N02030\"}}}";
        DcHelper dchelper = new DcHelper(url, uid, privateKey, publicKey, symKey);
        String response = dchelper.sendRequest(data, funcode);
        process("响应报文:\r\n" + response);
    }

    private static void process(String response) {
        System.out.println(response);
    }
}
