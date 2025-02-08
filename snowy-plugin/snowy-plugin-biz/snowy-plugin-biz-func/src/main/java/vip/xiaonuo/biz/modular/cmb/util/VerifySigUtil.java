package vip.xiaonuo.biz.modular.cmb.util;

import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithID;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.Base64;

/**
 *
 **/
public class VerifySigUtil {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    // [解密对称公钥]，每个网银企业单独配置，在ubank上进行操作
    private final static String CORP_KEY = "ec9fe202398b4990b269de245a47aa98";

    // [验签公钥]
    private final static String CMB_SIGNATURE_PUBLIC_KEY = "BNRhE10qHce4PRt8hCxAPfTmMDxW0Htw9SZHoUWn7U0Qj4GbU2Tgic4EmQSFjTcTdbDvNVmoSzwQvUkfzpRC9+k=";

    private static final int IV_LENGTH = 16;
    private static final int DECRYPT_MODE = 2;
    private static final String ALGORITHM = "SM4/CTR/NoPadding";

    public static void main(String[] args) throws Exception {
        // 接收到的[加密报文]
        String receiveEncryptMsg = "{\"corpNbr\":\"S0080014\",\"enptData\":\"MTMaxjlGAlZnCu44U6ZPbTBnSCidJgbiYhvfIKr61H6YIoFWoWW4DqESF5HNFSxozkUkmIdI2n5hXs3psB65yylYVrwaFo8fJQdBeMPRhXJ199HV861/nWkc4hDNyahB3c9xWzslR52DM7TwosCWgU2FVaYzptg91TR3gOw1bixmFaC40AFfPlUxwMRUnIucDNm6vbcci2W3AiqlbEbSekSUmydIv+SjPwBpofTP/L9RXeWDXTB64idFPtiSRwu99x6wZtYRtgFkJoLIUjvmD5dKn8t1Blf1TiVIMA3XgH2N8rBpZchBxyMRmZjIZa5RbXbjeAhwz0gXYQH4cCxcKlPeZJEecD0Q0oBJpRpCFUyjCekIvGYKQ3gZvHjnT2LK8P+oTkN6wz1OtuqkebrEekwzYL+AMemzzAakH0e6GuQragJftlZjQU4nigiOrX1WkH51tO/ZlnmCTbHQrQ1IL+2B0AnySmdSsrurLnS58ilSD9+Vk5lc77ODfgEv3/rXMDRXv1xydAUvQ/WdBsWhGdi/DIDTxS6v7q4THWsowpAyt8m4qpclpWcSlP5pCfqASdaqqm57quxlEvc+sAyhAI1k+oJ8ytBqgvKyNExkOxFGyMQwZDJks+hRFvN7sBZnOGcIADVKIzS2cmISTMCEIGotOQKAWxu+3vStZSYpeIZdsTwb7J8iiFNFyxc4GzKu+I7L2KAWOS057lJmA/UGj21a/8KKwkkLlnzxjZ6d1Z4mzNa/y1buhAwdDpaVTkrpmdLun0xkBdAdYWaIIzaatd03RTgNchx5t0WNU1K/oxv2AfBJlcl2UvoJLPL2lSBFUVyl9UygKTjTT4WKtGI/ggwIyUHRw9ZjCuvOVjzlhwJQyjwXUpJhjYlqw1tY9ughzF9M5dZkD0Tu+56RQ/UkVOtbq4wpS8m0r3+W9T52fXhfTqOgdktcoSZ5fPmn3vzW2zWrwK+Ma4w9GLlv4ATcHTic6tTiasL++FFmR47cn/b5c5B6KXw6ikQNVGumP4Z5Z7Z5PGxVZRGqsj268Bh2tvWlpLLn5MuqTH8wJ1Hkak79tInJfj9cKxRBheYIN1669Sy5fEs=\"}";

        // 将接收到的[加密报文]转换成json对象，并取出[企业编号]和[加密数据]
        JSONObject receiveMsgJsonObject = JSONObject.parseObject(receiveEncryptMsg);
        String corpNbr = receiveMsgJsonObject.getString("corpNbr");
        String enptData = receiveMsgJsonObject.getString("enptData");

        // 根据[企业编号]确定配置的[解密对称密钥]，使用[企业编号]为向量值，对[加密数据]进行解密，获得[带签名的发送报文]
        String sendData = decryptBase64(enptData, CORP_KEY, corpNbr);

        // 将[带签名的发送报文]转换成json对象，并取出[签名值]
        JSONObject sendDataJsonObject = JSONObject.parseObject(sendData);
        String sigDat = sendDataJsonObject.getString("sigdat");

        // 将[带签名的发送报文]中的[签名值]替换为固定常量，保存为[不带签名的发送报文]
        String sendDataNoSign = sendData.replace(sigDat, "__signature_sigdat__");

        // 使用[验签公钥]、[不带签名的发送报文]、签名值，进行验签
        cmbSM2Verify(CMB_SIGNATURE_PUBLIC_KEY, sendDataNoSign, sigDat);
    }

    public static String decryptBase64(String source, String secretKey, String corpNbr) throws Exception {
        byte[] decryptBytes = cmbSm4Crypt(Hex.decode(secretKey), getIv(corpNbr),
                java.util.Base64.getDecoder().decode(source),
                DECRYPT_MODE);
        return new String(decryptBytes);
    }

    private static byte[] getIv(String corpNbr) {
        return (corpNbr + "0000000000000000").substring(0, IV_LENGTH).getBytes();
    }

    private static byte[] cmbSm4Crypt(byte[] key, byte[] iv, byte[] input, int mode) throws GeneralSecurityException {
        SecretKeySpec spec = new SecretKeySpec(key, "SM4");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(mode, spec, ivParameterSpec);
        return cipher.doFinal(input);
    }

    /**
     * 使用SM2公钥进行验证签名
     *
     * @return
     */
    public static void cmbSM2Verify(String sPubKey, String strToSign, String strSign) {
        byte[] useId = "1234567812345678".getBytes();
        try {
            byte[] pubKeys = Base64.getDecoder().decode(sPubKey);
            byte[] byteBuffer = strToSign.getBytes("UTF-8");
            byte[] signature = Base64.getDecoder().decode(strSign);
            ECPublicKeyParameters publicKey = encodePublicKey(pubKeys);
            SM2Signer signer = new SM2Signer();
            ParametersWithID parameters = new ParametersWithID(publicKey, useId);
            signer.init(false, parameters);
            signer.update(byteBuffer, 0, byteBuffer.length);
            if (!signer.verifySignature(encodeDERSignature(signature))) {
                throw new Exception("请求签名校验不通过");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static byte[] encodeDERSignature(byte[] signature) throws Exception {
        byte[] r = new byte[32];
        byte[] s = new byte[32];
        System.arraycopy(signature, 0, r, 0, 32);
        System.arraycopy(signature, 32, s, 0, 32);
        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(new ASN1Integer(new BigInteger(1, r)));
        vector.add(new ASN1Integer(new BigInteger(1, s)));

        try {
            return (new DERSequence(vector)).getEncoded();
        } catch (Exception e) {
            throw new Exception("签名数据不正常");
        }
    }

    private static ECPublicKeyParameters encodePublicKey(byte[] value) {
        byte[] x = new byte[32];
        byte[] y = new byte[32];
        System.arraycopy(value, 1, x, 0, 32);
        System.arraycopy(value, 33, y, 0, 32);
        BigInteger iX = new BigInteger(1, x);
        BigInteger iY = new BigInteger(1, y);
        ECPoint ecQ = getSM2Curve().createPoint(iX, iY);
        return new ECPublicKeyParameters(ecQ, getECDomainParameters());
    }

    private static ECCurve getSM2Curve() {
        ECParameterSpec spec = ECNamedCurveTable.getParameterSpec("sm2p256v1");
        return spec.getCurve();
    }

    private static ECDomainParameters getECDomainParameters() {
        ECParameterSpec spec = ECNamedCurveTable.getParameterSpec("sm2p256v1");
        return new ECDomainParameters(spec.getCurve(), spec.getG(), spec.getN(), spec.getH(), spec.getSeed());
    }
}