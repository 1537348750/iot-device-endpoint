package org.lgq.iot.codec.utils;


public class Utilty {

    private static Utilty instance = new Utilty();

    public static Utilty getInstance() {
        return instance;
    }

    public static final int MIN_MID_VALUE = 1;
    public static final int MAX_MID_VALUE = 65535;

    public int bytes2Int(byte[] b, int start, int length) {
        int sum = 0;
        int end = start + length;
        for (int k = start; k < end; k++) {
            int n = ((int) b[k]) & 0xff;
            n <<= (--length) * 8;
            sum += n;
        }
        return sum;
    }


    public byte[] int2Bytes(int value, int length) {
        byte[] b = new byte[length];
        for (int k = 0; k < length; k++) {
            b[length - k - 1] = (byte) ((value >> 8 * k) & 0xff);
        }
        return b;
    }

    public boolean isValidofMid(int mId) {
        if (mId < MIN_MID_VALUE || mId > MAX_MID_VALUE) {
            return false;
        }
        return true;
    }

    public static String bytes2HexStr(byte[] bytes) {
        if (null == bytes || bytes.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 简洁写法 16进制字符串转成byte数组
     * @param hex 16进制字符串，支持大小写
     * @return byte数组
     */
    public static byte[] hexStrToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }
}
