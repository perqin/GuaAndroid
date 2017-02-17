package com.perqin.gua.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Author   : perqin
 * Date     : 17-2-16
 */

public class Md5UtilsUnitTest {
    @Test
    public void getHashedIntFromStringTest() throws Exception {
        int k = 0xCE539F68;
        assertTrue(Md5Utils.getHashedIntFromString("16230004162002") == -833380504);
    }
}
