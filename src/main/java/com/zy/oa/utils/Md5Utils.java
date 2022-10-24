package com.zy.oa.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Utils {
    public static String md5Digest(String source) {
        return DigestUtils.md5Hex(source);
    }
}
