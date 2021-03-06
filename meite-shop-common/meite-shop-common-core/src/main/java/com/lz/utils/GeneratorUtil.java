package com.lz.utils;


import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class GeneratorUtil {


    /**
     * 得到from到to的随机数，包括to
     *
     * @param from
     * @param to
     * @return
     */
    public static int getRandomNumber(int from, int to) {
        float a = from + (to - from) * (new Random().nextFloat());
        int b = (int) a;
        return ((a - b) > 0.5 ? 1 : 0) + b;
    }

    public static String getNonceString(int len) {
        String seed = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuffer tmp = new StringBuffer();
        for (int i = 0; i < len; i++) {
            tmp.append(seed.charAt(getRandomNumber(0, 61)));
        }
        return tmp.toString();
    }

    public static String getNonceNumberString(int len) {
        String seed = "1234567890";
        StringBuffer tmp = new StringBuffer();
        for (int i = 0; i < len; i++) {
            tmp.append(seed.charAt(getRandomNumber(0, 9)));
        }
        return tmp.toString();
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    public static String getOrderId(String prefix) {
        String body = String.valueOf(new Date().getTime());
        return prefix + body + getRandomNumber(10, 99);
    }

    /**
     * 生成随机盐
     */
//    public static String randomSalt() {
//        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
//        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
//        return secureRandom.nextBytes(3).toHex();
//    }

}