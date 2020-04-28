package net.scandicraft.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Checksum {

    public static String getSHA1(String fileContent) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] array = md.digest(fileContent.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
