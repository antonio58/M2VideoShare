package ClientSide;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by fernando on 20-04-2017.
 */
public class DataHandler {

    public static String getSHA1Hex(String inputString) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(inputString.getBytes());
        byte[] digest = md.digest();
        return convertByteToHex(digest);
    }

    private static String convertByteToHex(byte[] byteData) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
