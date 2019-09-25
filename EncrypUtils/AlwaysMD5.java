package EncrypUtils;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;

public class AlwaysMD5 {

    public static void main (String[] args) {
        String content = "{\"userId\":\"asdfghjkl\",\"itemId\":\"6921001822894\",\"storeCodes\":[\"123\"]}";

        String sign = doSign(content, "utf-8", "xMrNopr40598s9z7748V6L2B8Hu2550X");
        System.out.println(sign);
    }
    public static String doSign(String content, String charset, String keys) {
        String sign = "";
        content = content + keys;

        try {

            byte[] bytee = content.getBytes(charset);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes(charset));
            sign = new String(Base64.encodeBase64(md.digest()), charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return sign;
    }
}
