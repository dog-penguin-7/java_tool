package EncrypUtils;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

public class Sort {
    public static void main (String[] args) {
        Map<String, String> dic = new HashMap<>();
        dic.put("app_key", "WDGJ");
        dic.put("customerId", "CU201908146370");
        dic.put("format", "xml");
        dic.put("method", "yunji.esb.singleitem.synchronize");
        dic.put("sign_method", "md5");
        dic.put("timestamp", "2019-08-20 17:25:20");
        dic.put("v", "2.0");

//        String body = "<?xmlversion=\"1.0\"encoding=\"utf-8\"?><request><actionType>add</actionType><warehouseCode>OTHER</warehouseCode><ownerCode>420001_JKFX_692786614796124800</ownerCode><item><itemCode>0814002</itemCode><goodsCode>fx001</goodsCode><itemName>奇门分销420001供应商</itemName><barCode>0814002</barCode><length>0</length><width>0</width><height>0</height><volume>0</volume><grossWeight>0</grossWeight><netWeight>0</netWeight><safetyStock>0</safetyStock><itemType>ZC</itemType></item></request>";
        String body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<request>\n" +
                "  <actionType>add</actionType>\n" +
                "  <warehouseCode>OTHER</warehouseCode>\n" +
                "  <ownerCode>420001_JKFX_692786614796124800</ownerCode>\n" +
                "  <item>\n" +
                "    <itemCode>0814003</itemCode>\n" +
                "    <goodsCode>fx001</goodsCode>\n" +
                "    <itemName>奇门分销420001供应商</itemName>\n" +
                "    <barCode>0814003</barCode>\n" +
                "    <length>0</length>\n" +
                "    <width>0</width>\n" +
                "    <height>0</height>\n" +
                "    <volume>0</volume>\n" +
                "    <grossWeight>0</grossWeight>\n" +
                "    <netWeight>0</netWeight>\n" +
                "    <safetyStock>0</safetyStock>\n" +
                "    <itemType>ZC</itemType>\n" +
                "  </item>\n" +
                "</request>";

        String secret = "123";
        try {
            String sign = signRequest(dic, body, secret);
            out.println(sign);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public static String signRequest(Map<String,String> params, String body, String secretKey) throws Exception {
        // 1. 第一步，确保参数已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        // 2. 第二步，把所有参数名和参数值拼接在一起(包含body体)
        String joinedParams = joinRequestParams(params, body, secretKey, keys);
        // 3. 第三步，使用加密算法进行加密（目前仅支持md5算法）
        byte[] abstractMesaage = digest(joinedParams);
        // 4. 把二进制转换成大写的十六进制
        String md5 = byte2Hex(abstractMesaage);
        return md5;
    }

    private static String byte2Hex(byte[] bytes) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        int j = bytes.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (byte byte0 : bytes) {
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
    /**
     *
     * @param message
     * @return
     * @throws Exception
     */
    private static byte[] digest(String message) throws Exception {
        MessageDigest md5Instance = MessageDigest.getInstance("md5");
        md5Instance.update(message.getBytes("UTF-8"));
        return md5Instance.digest();
    }

    private static String joinRequestParams(Map<String, String> params, String body, String secretKey, String[] sortedKes) {
        StringBuilder sb = new StringBuilder(secretKey); // 前面加上secretKey
        for (String key : sortedKes) {
            if ("sign".equals(key)) {
                continue; // 签名时不计算sign本身
            } else {
                String value = params.get(key);
                if (key != null && null != value) {
                    sb.append(key).append(value);
                }
            }
        }
        sb.append(body); // 拼接body体
        sb.append(secretKey); // 最后加上secretKey
        return sb.toString();
    }
}