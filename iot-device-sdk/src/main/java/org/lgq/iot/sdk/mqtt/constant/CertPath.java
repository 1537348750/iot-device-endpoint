package org.lgq.iot.sdk.mqtt.constant;


/**
 * Certificate acquisition: https://support.huaweicloud.com/devg-iothub/iot_02_1004.html#section3
 */
public class CertPath {

    public static final String CN_NORTH_4_SHARE_INSTANCE_CERTPATH;
    public static final String CN_NORTH_4_STANDARD_INSTANCE_CERTPATH;
    public static final String AP_SOUTHEASR_1_STANDARD_INSTANCE__CERTPATH;
    public static final String AP_SOUTHEASR_2_STANDARD_INSTANCE__CERTPATH;
    public static final String AP_SOUTHEASR_3_STANDARD_INSTANCE__CERTPATH;

    /**
     * The certificate needs to be placed in the base directory.
     * If it is currently a Windows system, you need to create this directory in the current drive letter.
     * If your iot device endpoint project file is on drive D,
     * you need to create the directory D:\iot\iot-device-web\cert\
     */
    private static final String BASE_CERT_PATH = "/iot/iot-device-web/cert/";

    static {
        CN_NORTH_4_SHARE_INSTANCE_CERTPATH = BASE_CERT_PATH + "DigiCertGlobalRootCA.jks";
        CN_NORTH_4_STANDARD_INSTANCE_CERTPATH = BASE_CERT_PATH + "cn-north-4-device-client-rootcert.jks";
        AP_SOUTHEASR_1_STANDARD_INSTANCE__CERTPATH = BASE_CERT_PATH + "ap-southeast-1-device-client-rootcert.jks";
        AP_SOUTHEASR_2_STANDARD_INSTANCE__CERTPATH = BASE_CERT_PATH + "ap-southeast-2-device-client-rootcert.jks";
        AP_SOUTHEASR_3_STANDARD_INSTANCE__CERTPATH = BASE_CERT_PATH + "ap-southeast-3-device-client-rootcert.jks";
    }
}
