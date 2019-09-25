package jackyun;

import com.google.gson.Gson;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExportAPIType {
    public static final String URL = "jdbc:mysql://192.168.3.2/differjh2016v5?useUnicode=true&characterEncoding=utf8";
    public static final String NAME = "com.mysql.cj.jdbc.Driver";
    public static final String USER = "developer";
    public static final String PASSWORD = "dev";

    public static void main (String[] args) {
        String queryStr = "SELECT p.name,p.platvalue,GROUP_CONCAT(a.apitype) AS apiType FROM api_plat p \n" +
                "JOIN api_platopenapi ap ON p.platid = ap.platid\n" +
                "JOIN api_openapi a ON ap.openapiid = a.openapiid\n" +
                "GROUP BY p.name,p.platvalue";
        String data = "{\"name\":\"91拼团\",\"platvalue\":4,\"apiType\":\"10,12,25\"}";

//        try {
//            GetDataFromDB(queryStr);
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }

        Map<String, String> dic = new HashMap<>();
        // polyapi----jackyun
//        dic.put("10", "12");
//        dic.put("13", "15");
//        dic.put("12", "22");
//        dic.put("26", "23");
//        dic.put("31", "24");
//        dic.put("22", "25");
//        dic.put("32", "32");
//        dic.put("57", "33");
//        dic.put("301", "35");
//        dic.put("325", "36");
//        dic.put("323", "37");
//        dic.put("326", "38");
//        dic.put("324", "39");
//        dic.put("308", "40");
//        dic.put("306", "41");
//        dic.put("329", "42");
//        dic.put("322", "43");
//        dic.put("0", "17");
//        dic.put("1", "18");
//        dic.put("3", "19");

        // jackyun----polyapi
        dic.put("12", "10");
        dic.put("15", "13");
        dic.put("22", "12");
        dic.put("23", "26");
        dic.put("24", "31");
        dic.put("25", "22");
        dic.put("32", "32");
        dic.put("33", "57");
        dic.put("35", "301");
        dic.put("36", "335");
        dic.put("37", "323");
        dic.put("38", "326");
        dic.put("39", "324");
        dic.put("40", "308");
        dic.put("41", "306");
        dic.put("42", "329");
        dic.put("43", "322");
        dic.put("17", "0");
        dic.put("18", "1");
        dic.put("19", "3");

        // http://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.0/
        // Gson jar下载地址。
        Gson gson = new Gson();
//        Model single_model = gson.fromJson(data, Model.class);
        Model[] model = gson.fromJson(AllInfo, Model[].class);
        int count = model.length;
        List<ResponseModel> jackAPITypeList = new ArrayList<>();

        for (Model item : model) {
            String[] arr = item.getApiType().split(",");
            ResponseModel responseModel = new ResponseModel();
            StringBuffer sBuffer = new StringBuffer();

            for (String str : arr) {
//                for (String change : dic.values()) {
//                    if (change.equalsIgnoreCase(str)) {
//                        System.out.println(str);
//                        sBuffer.append(dic + ",");
//                        break;
//                    }
//
//                }
                for (Map.Entry<String, String> entry : dic.entrySet()) {
                    if (entry.getValue().equalsIgnoreCase(str)) {
                        sBuffer.append(entry.getKey() + ",");
                        break;
                    }
                }
//                System.out.println(str);
            }

            String sBufferStr = sBuffer.toString();
            String deleteLastChar = "";
            int sBufferLen = sBuffer.length();
            int sBufferStrLen = sBufferStr.length();

            if (sBufferStrLen > 1) {
                deleteLastChar = sBufferStr.substring(0, sBufferStr.length() - 1);
            }

            responseModel.setApiType(deleteLastChar);
            responseModel.setName(item.getName());
//            responseModel.setPlatValue((item.getApiType()));
            jackAPITypeList.add(responseModel);
        }

        System.out.println(gson.toJson(jackAPITypeList));
    }

    static void GetDataFromDB(String queryStr) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            Class.forName(NAME);//指定连接类型
            conn = DriverManager.getConnection(URL, USER, PASSWORD);//获取连接
            pst = conn.prepareStatement(queryStr);//准备执行语句
        }
        catch (Exception ex) {
//            ex.printStackTrace();
        }
        finally {
            conn.close();
        }
    }
}