package org.lgq.iot.codec;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.m2m.cig.tup.modules.protocol_adapter.IProtocolAdapter;
import org.lgq.iot.codec.down.CmdProcess;
import org.lgq.iot.codec.up.ReportProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


public class ProtocolAdapterImpl implements IProtocolAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ProtocolAdapterImpl.class);

    // 厂商名称
    private static final String MANU_FACTURERID = "LGQ";
    // 模型
    private static final String MODEL = "NBDevice";

    @Override
    public String getManufacturerId() {
        return MANU_FACTURERID;
    }

    @Override
    public String getModel() {
        return MODEL;
    }

    public byte[] encode(ObjectNode input) {
        try {
            logger.info("lgq:start encode: " + input.toString());
            CmdProcess cmdProcess = new CmdProcess(input);
            byte[] byteNode = cmdProcess.toByte();
            logger.info("lgq:encode success: encode result=" + Arrays.toString(byteNode));
            return byteNode;
        } catch (Exception e) {
            logger.error("lgq:encode fail, e={}", e.getMessage());
            return null;
        }
    }

    public ObjectNode decode(byte[] binaryData) {
        logger.info("lgq:start decode: 正确的插件包");
        try {
            logger.info("lgq:start decode: " + Arrays.toString(binaryData));
            ReportProcess reportProcess = new ReportProcess(binaryData);
            ObjectNode objectNode = reportProcess.toJsonNode();
            logger.info("lgq:decode success: " + objectNode.toString());
            return objectNode;
        } catch (Exception e) {
            logger.error("lgq:decode fail, e={}", e.getMessage());
            return null;
        }
    }

}
