package org.lgq.iot.sdk.mqtt.utils;

import lombok.extern.slf4j.Slf4j;
import org.lgq.iot.sdk.mqtt.client.DeviceInfo;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class MqttUtil {

    /**
     * 加载SSL证书
     * @param certPath 证书存放的相对路径
     * @return
     */
    public static SocketFactory getOptionSocketFactory(String certPath) {
        SSLContext sslContext;
        InputStream stream = null;
        try {
            stream = Files.newInputStream(Paths.get(certPath));
            sslContext = SSLContext.getInstance("TLS");
            KeyStore ts = KeyStore.getInstance("JKS");
            ts.load(stream, null);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ts);
            TrustManager[] tm = tmf.getTrustManagers();
            sslContext.init(null, tm, new SecureRandom());
        } catch (Exception e) {
            if (e instanceof InvalidPathException) {
                log.error(e.getMessage());
            } else {
                log.error("e = {}", ExceptionUtil.getBriefStackTrace(e));
            }
            return null;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    log.error("e = {}", ExceptionUtil.getBriefStackTrace(e));
                }
            }
        }
        return sslContext.getSocketFactory();
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
}
