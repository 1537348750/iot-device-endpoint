package org.log.iot.codec.test;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.m2m.cig.tup.modules.protocol_adapter.IProtocolAdapter;
import org.junit.Before;
import org.junit.Test;
import org.lgq.iot.codec.ProtocolAdapterImpl;
import org.lgq.iot.codec.utils.HexEncodeUtil;
import org.lgq.iot.codec.utils.Utilty;

public class ReportPrcossTest {

    private IProtocolAdapter protocolAdapter;

    @Before
    public void setProtocolAdapter() {
        this.protocolAdapter = new ProtocolAdapterImpl();
    }

    /**
     * ServiceId = LgqService上报属性解码测试，
     * 有三个属性：
     * int id,  double id_d,  String id_s
     */
    @Test
    public void testLgqServiceReport() {
        try {
            String hex = HexEncodeUtil.encodeLgqService(1089, 1.00808d, "Hello World !!!");
            byte[] payload = Utilty.hexStrToBytes(hex);
            System.out.println("LgqServiceHexStr => " + hex);

            ObjectNode decode = protocolAdapter.decode(payload);
            System.out.println("decode result => " + decode.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
