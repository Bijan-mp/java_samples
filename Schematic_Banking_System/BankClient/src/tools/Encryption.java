package tools;

import java.security.MessageDigest;

public class Encryption {
    public static String getMD5(String value)throws Exception{
        //String password = "amirsam";

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(value.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
