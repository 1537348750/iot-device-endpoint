package org.lgq.iot.sdk.mqtt.utils;

import lombok.extern.slf4j.Slf4j;
import org.lgq.iot.sdk.mqtt.client.DeviceInfo;
import org.lgq.iot.sdk.mqtt.constant.CertEnum;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MqttUtil {

    private static Map<String, SocketFactory> FACTORY_MAP = new ConcurrentHashMap<>(4);

    private static void initFactory(String certPath) {
        SSLContext sslContext;
        InputStream stream = null;
        try {
            stream = Files.newInputStream(Paths.get(certPath));
            sslContext = SSLContext.getInstance("TLS");
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(stream, null);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            sslContext.init(null, trustManagers, new SecureRandom());
            FACTORY_MAP.put(certPath, sslContext.getSocketFactory());
            log.info("=====> Init SocketFactory success, certPath={}", certPath);
        } catch (Exception e) {
            log.error("=====> Init SocketFactory fail, certPath={}, e = {}", certPath, ExceptionUtil.getBriefStackTrace(e));
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    log.error("close stream fail, e = {}", ExceptionUtil.getBriefStackTrace(e));
                }
            }
        }
    }

    /**
     * 加载SSL证书
     * @param certPath 证书存放的路径
     * @return
     */
    public static SocketFactory getOptionSocketFactory(String certPath) {
        SocketFactory factory = FACTORY_MAP.get(certPath);
        if (factory == null) {
            initFactory(certPath);
        }
        return factory;
    }

    public static String getCertPath(String serverIp) {
        String ip = IpUtil.parseIP(serverIp);
        String certPath;
        if (CertEnum.CN_NORTH_4_SHARE_INSTANCE.getIp().equals(ip)) {
            certPath = CertEnum.CN_NORTH_4_SHARE_INSTANCE.getCertPath();
        } else if (CertEnum.CN_NORTH_4_STANDARD_INSTANCE.getIp().equals(ip)) {
            certPath = CertEnum.CN_NORTH_4_STANDARD_INSTANCE.getCertPath();
        } else if (CertEnum.CN_EAST_3_STANDARD_INSTANCE.getIp().equals(ip)) {
            certPath = CertEnum.CN_EAST_3_STANDARD_INSTANCE.getCertPath();
        } else if (CertEnum.CN_SOUTH_1_STANDARD_INSTANCE.getIp().equals(ip)) {
            certPath = CertEnum.CN_SOUTH_1_STANDARD_INSTANCE.getCertPath();
        } else if (CertEnum.CN_SOUTH_4_STANDARD_INSTANCE.getIp().equals(ip)) {
            certPath = CertEnum.CN_SOUTH_4_STANDARD_INSTANCE.getCertPath();
        } else if (CertEnum.AP_SOUTHEASR_1_STANDARD_INSTANCE.getIp().equals(ip)) {
            certPath = CertEnum.AP_SOUTHEASR_1_STANDARD_INSTANCE.getCertPath();
        } else if (CertEnum.AP_SOUTHEASR_2_STANDARD_INSTANCE.getIp().equals(ip)) {
            certPath = CertEnum.AP_SOUTHEASR_2_STANDARD_INSTANCE.getCertPath();
        } else if (CertEnum.AP_SOUTHEASR_3_STANDARD_INSTANCE.getIp().equals(ip)) {
            certPath = CertEnum.AP_SOUTHEASR_3_STANDARD_INSTANCE.getCertPath();
        } else {
            certPath = null;
        }
        return certPath;
    }


    /**
     * 调用sha256算法进行哈希
     * @param message
     * @param tStamp
     * @return
     */
    public static String sha256_mac(String message, String tStamp) {
        String passWord = null;
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(tStamp.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            passWord = byteArrayToHexString(bytes);
        } catch (Exception e) {
            log.error("e = {}", ExceptionUtil.getBriefStackTrace(e));
        }
        return passWord;
    }

    /**
     * byte数组转16进制字符串
     * @param b
     * @return
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * 要求：10位数字
     * @return
     */
    private static String getTimeStamp() {
        return ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"))
                .format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
    }

    private static String getClientId(String deviceId, String timeStamp) {
        return deviceId + "_0_0_" + timeStamp;
    }

    private static String getPassword(String secret, String timeStamp) {
        return sha256_mac(secret, timeStamp);
    }

    public static DeviceInfo getDeviceInfo(String deviceId, String secret) {
        String timeStamp = getTimeStamp();
        String clientId = getClientId(deviceId, timeStamp);
        String password = getPassword(secret, timeStamp);
        return new DeviceInfo(clientId, password);
    }

    /**
     * 简洁写法 16进制字符串转成byte数组
     * @param hex 16进制字符串，支持大小写
     * @return byte数组
     */
    public static byte[] hexStringToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }

    /**
     * 简洁写法 byte数组转成16进制字符串
     * @param bytes byte数组
     * @return 16进制字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02X", b));
        }
        return builder.toString();
    }
}
