package org.lgq.iot.sdk.mqtt.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * Certificate acquisition: https://support.huaweicloud.com/devg-iothub/iot_02_1004.html#section3
 */
public class CertPath {

    public static Map<String, String> CERTPATH = new HashMap<>(10, 1f);

    public static final String CN_NORTH_4_SHARE_INSTANCE_CERTPATH;
    public static final String CN_NORTH_4_STANDARD_INSTANCE_CERTPATH;
    public static final String AP_SOUTHEAST_1_STANDARD_INSTANCE_CERTPATH;
    public static final String AP_SOUTHEAST_2_STANDARD_INSTANCE_CERTPATH;
    public static final String AP_SOUTHEAST_3_STANDARD_INSTANCE_CERTPATH;

    /**
     * The certificate needs to be placed in the base directory.
     * If it is currently a Windows system, you need to create this directory in the current drive letter.
     * If your iot device endpoint project file is on drive D,
     * you need to create the directory D:\iot\iot-device-web\cert\
     */
    private static final String BASE_CERT_PATH = "/iot/iot-device-web/cert/";

    static {
        CN_NORTH_4_SHARE_INSTANCE_CERTPATH = BASE_CERT_PATH + "DigiCertGlobalRootCA.jks";
        CERTPATH.put("121.36.42.100", CN_NORTH_4_SHARE_INSTANCE_CERTPATH);

        CN_NORTH_4_STANDARD_INSTANCE_CERTPATH = BASE_CERT_PATH + "cn-north-4-device-client-rootcert.jks";
        CERTPATH.put("117.78.5.125", CN_NORTH_4_STANDARD_INSTANCE_CERTPATH);
        CERTPATH.put("124.70.218.131", CN_NORTH_4_STANDARD_INSTANCE_CERTPATH);
        CERTPATH.put("116.205.178.237", CN_NORTH_4_STANDARD_INSTANCE_CERTPATH);
        CERTPATH.put("123.60.224.23", CN_NORTH_4_STANDARD_INSTANCE_CERTPATH);

        AP_SOUTHEAST_1_STANDARD_INSTANCE_CERTPATH = BASE_CERT_PATH + "ap-southeast-1-device-client-rootcert.jks";
        CERTPATH.put("182.160.7.139", AP_SOUTHEAST_1_STANDARD_INSTANCE_CERTPATH);

        AP_SOUTHEAST_2_STANDARD_INSTANCE_CERTPATH = BASE_CERT_PATH + "ap-southeast-2-device-client-rootcert.jks";
        CERTPATH.put("43.255.105.212", AP_SOUTHEAST_2_STANDARD_INSTANCE_CERTPATH);

        AP_SOUTHEAST_3_STANDARD_INSTANCE_CERTPATH = BASE_CERT_PATH + "ap-southeast-3-device-client-rootcert.jks";
        CERTPATH.put("119.8.186.222", AP_SOUTHEAST_3_STANDARD_INSTANCE_CERTPATH);
    }
}
