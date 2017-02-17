package com.perqin.gua.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author   : perqin
 * Date     : 17-2-15
 */

public class Md5Utils {
    public static int getHashedIntFromString(String data) {
        int res = 0;
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(data.getBytes("UTF-8"));
            res = digest[15] & 0xFF | ((digest[14] & 0xFF) << 8) | ((digest[13] & 0xFF) << 16) | ((digest[12] & 0xFF) << 24);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }
}
